package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class InstruccionCopiar extends Instruccion {
    private ComplexNumber numeroRegistroFuente;
    private ComplexNumber numeroRegistroDestino;

    public InstruccionCopiar(ComplexNumber numeroRegistroFuente, ComplexNumber numeroRegistroDestino) {
        this.numeroRegistroFuente = numeroRegistroFuente;
        this.numeroRegistroDestino = numeroRegistroDestino;
    }

    @Override
    public void operacion(Simulador simulador, MaquinaGenerica maquina) {
        String hexa = maquina.leerRegistro(numeroRegistroFuente);
        maquina.escribirEnRegistro(numeroRegistroDestino,hexa);
    }
}
