package main.model;

import main.apis.HEXAConversionAPI;
import main.apis.bitvector.BitVector;
import main.model.instrucciones.tipos.Instruccion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class MemoriaPrincipal {
    private static final int TAM_MP = 256;

    Map<ComplexNumber,CeldaMemoria> celdasMemoria;

    CeldaMemoria puertoEntradaControl;
    CeldaMemoria puertoEntrada;
    CeldaMemoria puertoSalidaControl;
    CeldaMemoria puertoSalida;

    public MemoriaPrincipal() {
        celdasMemoria = new HashMap<ComplexNumber, CeldaMemoria>();
        for (int i=0; i< TAM_MP; i++){
            ComplexNumber key = new ComplexNumber(i);
            celdasMemoria.put(key,new CeldaMemoria(key));
        }
        puertoEntradaControl = celdasMemoria.get(new ComplexNumber(252));
        puertoEntrada = celdasMemoria.get(new ComplexNumber(253));
        puertoSalidaControl = celdasMemoria.get(new ComplexNumber(254));
        puertoSalida = celdasMemoria.get(new ComplexNumber(255));

    }

    public String leerCelda(ComplexNumber numeroCelda){
        return celdasMemoria.get(numeroCelda).getValorHexa();
    }

    public void setValor(ComplexNumber pos, String hexa){
        setValor(pos,HEXAConversionAPI.hex_to_bitvector(hexa));
    }

    public void setValor(ComplexNumber pos, BitVector bits){
        int cantCeldasNecesarias = cantCeldasNecesarias(bits);
        if (cantCeldasNecesarias == 0) return;
        ArrayList<CeldaMemoria> celdasAEscribir = new ArrayList<CeldaMemoria>();
        for (int i = 0; i< cantCeldasNecesarias; i++){
            ComplexNumber key = new ComplexNumber(pos.getDecimalNumber() + i);
            CeldaMemoria celda = celdasMemoria.get(key);
            celdasAEscribir.add(celda);
        }

        int cantBitsEscritos = 0;
        Iterator<CeldaMemoria> iterator = celdasAEscribir.iterator();
        CeldaMemoria celdaEscritura = iterator.next();
        for (int i= 0; i< bits.size(); i++) {
            celdaEscritura.setValor(cantBitsEscritos,bits.getBit(i));
            cantBitsEscritos++;
            if(cantBitsEscritos == 8 && iterator.hasNext()){
                celdaEscritura = iterator.next();
                cantBitsEscritos = 0;
            }
        }


    }

    private int cantCeldasNecesarias(BitVector bv){
        if (bv.size() == 0) return 0;
        if (bv.size() % 8 == 0) return bv.size() / 8;
        else return (bv.size()+8) / 8;
    }

}
