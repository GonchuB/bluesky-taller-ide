package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class InstruccionAlmacenar extends Instruccion {
    private ComplexNumber numeroRegistro;
    private ComplexNumber numeroDeCelda;

    public InstruccionAlmacenar(ComplexNumber numeroRegistro, ComplexNumber numeroDeCelda) {
        this.numeroRegistro = numeroRegistro;
        this.numeroDeCelda = numeroDeCelda;
    }

    @Override
    public void operacion(MaquinaGenerica maquina) {
        String hexa = maquina.leerRegistro(numeroRegistro);
        maquina.escribirEnMemoria(numeroDeCelda,hexa);
    }
}
