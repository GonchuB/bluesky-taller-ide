package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class InstruccionSumarPF extends Instruccion {
    private ComplexNumber registro1;
    private ComplexNumber registro2;
    private ComplexNumber registroDestino;

    public InstruccionSumarPF(ComplexNumber registro1, ComplexNumber registro2, ComplexNumber registroDestino) {
        this.registro1 = registro1;
        this.registro2 = registro2;
        this.registroDestino = registroDestino;
    }

    @Override
    public void operacion(Simulador simulador, MaquinaGenerica maquina) {

    }
}
