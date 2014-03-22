package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class InstruccionCargarMemoria extends Instruccion {
    private ComplexNumber numeroRegistro;
    private ComplexNumber numeroCeldaMemoria;

    public InstruccionCargarMemoria(ComplexNumber numeroRegistro, ComplexNumber numeroCeldaMemoria) {
        this.numeroRegistro = numeroRegistro;
        this.numeroCeldaMemoria = numeroCeldaMemoria;
    }

    @Override
    public void operacion(Simulador simulador, MaquinaGenerica maquina) {
        String hexa = maquina.leerMemoria(numeroCeldaMemoria);
        maquina.escribirEnRegistro(numeroRegistro,hexa);
    }
}
