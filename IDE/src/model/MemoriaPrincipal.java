package model;

import java.util.HashMap;
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
            celdasMemoria.put(key,new CeldaMemoria(key,"00"));
        }
        puertoEntradaControl = celdasMemoria.get(new ComplexNumber(252));
        puertoEntrada = celdasMemoria.get(new ComplexNumber(253));
        puertoSalidaControl = celdasMemoria.get(new ComplexNumber(254));
        puertoSalida = celdasMemoria.get(new ComplexNumber(255));

    }


}
