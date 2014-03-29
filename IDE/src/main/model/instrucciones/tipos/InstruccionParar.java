package main.model.instrucciones.tipos;

import main.model.MaquinaGenerica;
import main.model.Simulador;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class InstruccionParar extends Instruccion {
    @Override
    public String operacion(Simulador simulador, MaquinaGenerica maquina) {
        simulador.pararSimulacion();
        return null;
    }
}
