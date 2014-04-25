package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class InstruccionSaltarSinCarry extends Instruccion {
    private ComplexNumber numeroCeldaMemoria;

    public InstruccionSaltarSinCarry(ComplexNumber numeroCeldaMemoria) {
        this.numeroCeldaMemoria = numeroCeldaMemoria;
    }

    @Override
    public String operacion(Simulador simulador, MaquinaGenerica maquina) {
        if(!maquina.getAluControl().isCarry()){
            String parte1Instruccion = maquina.leerMemoria(numeroCeldaMemoria);
            String parte2Instruccion = maquina.leerMemoria(new ComplexNumber(numeroCeldaMemoria.getDecimalNumber()+1));
            String instruccion = parte1Instruccion + parte2Instruccion;
            simulador.setearProximaInstruccionSegunNumero(numeroCeldaMemoria,instruccion);
        }
        return null;
    }
}
