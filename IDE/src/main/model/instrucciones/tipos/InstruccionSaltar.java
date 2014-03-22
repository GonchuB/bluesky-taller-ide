package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class InstruccionSaltar extends Instruccion {
    private ComplexNumber numeroCeldaMemoria;
    private ComplexNumber numeroRegistro;

    public InstruccionSaltar(ComplexNumber numeroCeldaMemoria, ComplexNumber numeroRegistro) {
        this.numeroCeldaMemoria = numeroCeldaMemoria;
        this.numeroRegistro = numeroRegistro;
    }

    @Override
    public void operacion(Simulador simulador, MaquinaGenerica maquina) {
        String patronNumeroRegistro = maquina.leerRegistro(numeroRegistro);
        String patronRegistroCero = maquina.leerRegistro(new ComplexNumber(0));
        if(patronNumeroRegistro.equals(patronRegistroCero)){
            String parte1Instruccion = maquina.leerMemoria(numeroCeldaMemoria);
            String parte2Instruccion = maquina.leerMemoria(new ComplexNumber(numeroCeldaMemoria.getDecimalNumber()+1));
            String instruccion = parte1Instruccion + parte2Instruccion;
            simulador.setearProximaInstruccionSegunNumero(numeroCeldaMemoria,instruccion);
        }
    }
}
