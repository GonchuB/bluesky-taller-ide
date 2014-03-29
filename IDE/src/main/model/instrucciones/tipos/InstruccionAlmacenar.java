package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;

import javax.swing.*;

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
    public String operacion(Simulador simulador, MaquinaGenerica maquina) {
        String hexa = maquina.leerRegistro(numeroRegistro);
        String error = maquina.escribirEnMemoria(numeroDeCelda,hexa);
        if(error != null) return error;
        if (numeroDeCelda.getDecimalNumber() == 255){
            error = maquina.escribirEnMemoria(new ComplexNumber(numeroDeCelda.getDecimalNumber()-1),"00");
            if(error != null) return error;
            JOptionPane.showMessageDialog(null, "Sale: " + hexa, "Instruccion Almacenar", JOptionPane.INFORMATION_MESSAGE);
            error = maquina.escribirEnMemoria(new ComplexNumber(numeroDeCelda.getDecimalNumber()-1),"01");
            if(error != null) return error;
        }
        return null;
    }
}
