package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;
import main.ui.VistaEntradaSalida;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class InstruccionAlmacenarReg extends Instruccion {
    private ComplexNumber numeroRegistro;
    private ComplexNumber numeroRegistroMem;
    private VistaEntradaSalida vistaConversionHexa;

    public InstruccionAlmacenarReg(ComplexNumber numeroRegistro, ComplexNumber numeroRegistroMem) {
        this.numeroRegistro = numeroRegistro;
        this.numeroRegistroMem = numeroRegistroMem;
        this.vistaConversionHexa = new VistaEntradaSalida();
    }

    @Override
    public String operacion(Simulador simulador, MaquinaGenerica maquina) {
        String hexa = maquina.leerRegistro(numeroRegistro);
        String numeroCelda = maquina.leerRegistro(numeroRegistroMem);
        ComplexNumber numeroDeCelda = new ComplexNumber(numeroCelda);
        String error = maquina.escribirEnMemoria(numeroDeCelda,hexa);
        if(error != null) return error;
        if (numeroDeCelda.getDecimalNumber() == 255){
            error = maquina.escribirEnMemoria(new ComplexNumber(numeroDeCelda.getDecimalNumber()-1),"00");
            if(error != null) return error;
            vistaConversionHexa.mostrarEntradaHexa(hexa);
            error = maquina.escribirEnMemoria(new ComplexNumber(numeroDeCelda.getDecimalNumber()-1),"01");
            if(error != null) return error;
        }
        return null;
    }
}
