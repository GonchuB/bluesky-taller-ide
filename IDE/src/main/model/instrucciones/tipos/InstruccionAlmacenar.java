package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;

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
    public void operacion(Simulador simulador, MaquinaGenerica maquina) {
        String hexa = maquina.leerRegistro(numeroRegistro);
        maquina.escribirEnMemoria(numeroDeCelda,hexa);
        if (numeroDeCelda.getDecimalNumber() == 255){
            maquina.escribirEnMemoria(new ComplexNumber(numeroDeCelda.getDecimalNumber()-1),"00");
            System.out.println("Sale: " + hexa); //TODO - Cambiar por un JOptionPane.showMessageDialog(tpEditor.getJFrame(), error, title, msgType);
            maquina.escribirEnMemoria(new ComplexNumber(numeroDeCelda.getDecimalNumber()-1),"01");
        }
    }
}
