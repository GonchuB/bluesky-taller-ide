package main.model;

import main.apis.HEXAConversionAPI;
import main.apis.bitvector.BitVector;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class MemoriaPrincipal {
    private static final int TAM_MP = 256;
    private static final int TAM_MP_INSTRUCCIONES = 192;

    private Map<ComplexNumber, CeldaMemoria> celdasMemoria;

    private CeldaMemoria puertoEntradaControl;
    private CeldaMemoria puertoEntrada;
    private CeldaMemoria puertoSalidaControl;
    private CeldaMemoria puertoSalida;


    private Map<ComplexNumber, CeldaMemoria> celdasInstrucciones;
    private Map<ComplexNumber, CeldaMemoria> celdasDatos;

    public MemoriaPrincipal() {
        celdasMemoria = new HashMap<ComplexNumber, CeldaMemoria>();
        celdasInstrucciones = new HashMap<ComplexNumber, CeldaMemoria>();
        celdasDatos = new HashMap<ComplexNumber, CeldaMemoria>();
        for (int i = 0; i < TAM_MP; i++) {
            ComplexNumber key = new ComplexNumber(i);
            celdasMemoria.put(key, new CeldaMemoria(key));
            if (i >= TAM_MP_INSTRUCCIONES){
                //MEMORIA PARA DATOS
                celdasDatos.put(key, new CeldaMemoria(key));
            } else {
                //MEMORIA PARA INSTRUCCIONES
                celdasInstrucciones.put(key, new CeldaMemoria(key));
            }
        }
        puertoEntradaControl = celdasMemoria.get(new ComplexNumber(252));
        puertoEntrada = celdasMemoria.get(new ComplexNumber(253));
        puertoSalidaControl = celdasMemoria.get(new ComplexNumber(254));
        puertoSalida = celdasMemoria.get(new ComplexNumber(255));

    }

    public String leerCelda(ComplexNumber numeroCelda) {
        return celdasMemoria.get(numeroCelda).getValorHexa();
    }

    public String setValor(ComplexNumber pos, String hexa, boolean isInstruccion) {
        return setValor(pos, HEXAConversionAPI.hex_to_bitvector(hexa),isInstruccion);
    }

    public String setValor(ComplexNumber pos, BitVector bits, boolean isInstruccion) {
        if (isInstruccion && !celdasInstrucciones.keySet().contains(pos)){
            return "ERROR DE SEGMENTACION - Se intentó guardar en memoria una instruccion en un espacio de datos";
        }

        if (!isInstruccion && !celdasDatos.keySet().contains(pos)){
            return "ERROR DE SEGMENTACION - Se intentó guardar en memoria un dato en un espacio de instrucciones";

        }
        int cantCeldasNecesarias = cantCeldasNecesarias(bits);
        if (cantCeldasNecesarias == 0) return null;
        ArrayList<CeldaMemoria> celdasAEscribir = new ArrayList<CeldaMemoria>();
        for (int i = 0; i < cantCeldasNecesarias; i++) {
            ComplexNumber key = new ComplexNumber(pos.getDecimalNumber() + i);
            CeldaMemoria celda = celdasMemoria.get(key);
            celdasAEscribir.add(celda);
        }

        if(isInstruccion &&  !CollectionUtils.intersection(celdasAEscribir,celdasDatos.values()).isEmpty()){
            return "ERROR DE SEGMENTACION - Se intentó guardar en memoria una instruccion en un espacio de datos";
        }

        if (!isInstruccion && !CollectionUtils.intersection(celdasAEscribir,celdasInstrucciones.values()).isEmpty()){
            return "ERROR DE SEGMENTACION - Se intentó guardar en memoria un dato en un espacio de instrucciones";
        }

        int cantBitsEscritos = 0;
        Iterator<CeldaMemoria> iterator = celdasAEscribir.iterator();
        CeldaMemoria celdaEscritura = iterator.next();
        for (int i = 0; i < bits.size(); i++) {
            celdaEscritura.setValor(cantBitsEscritos, bits.getBit(i));
            cantBitsEscritos++;
            if (cantBitsEscritos == 8 && iterator.hasNext()) {
                celdaEscritura = iterator.next();
                cantBitsEscritos = 0;
            }
        }
        return null;

    }

    private int cantCeldasNecesarias(BitVector bv) {
        if (bv.size() == 0) return 0;
        if (bv.size() % 8 == 0) return bv.size() / 8;
        else return (bv.size() + 8) / 8;
    }

    public Map<ComplexNumber, CeldaMemoria> getCeldasMemoria() {
        return celdasMemoria;
    }

    public Float obtenerPorcentajeUtilizado() {
        Float celdasUtilizadas = 0f;
        for (Map.Entry<ComplexNumber, CeldaMemoria> entry : celdasMemoria.entrySet()) {
            if (entry.getValue().getModificada()) {
                celdasUtilizadas += 1f;
            }
        }
        celdasUtilizadas = celdasUtilizadas / TAM_MP;
        return 100 * celdasUtilizadas;
    }

}
