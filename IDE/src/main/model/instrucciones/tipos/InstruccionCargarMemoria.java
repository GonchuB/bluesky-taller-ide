package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;
import main.ui.VistaEntradaSalida;
import main.ui.VistaMemoria;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class InstruccionCargarMemoria extends Instruccion {
    private ComplexNumber numeroRegistro;
    private ComplexNumber numeroCeldaMemoria;
    private VistaEntradaSalida vistaConversionHexa;

    public InstruccionCargarMemoria(ComplexNumber numeroRegistro, ComplexNumber numeroCeldaMemoria) {
        this.numeroRegistro = numeroRegistro;
        this.numeroCeldaMemoria = numeroCeldaMemoria;
        vistaConversionHexa = new VistaEntradaSalida();
    }

    @Override
    public String operacion(Simulador simulador, MaquinaGenerica maquina) {
        int decimalNumber = numeroCeldaMemoria.getDecimalNumber();
        String hexa = "";
        VistaMemoria v = new VistaMemoria(simulador.mostrarEstadoSimulacion());
        if (decimalNumber == 253) {
        	hexa = ingresarHexaPorPantalla(hexa);
        	String error = maquina.escribirEnMemoria(new ComplexNumber(decimalNumber),hexa);
        	v.buildMemoryRam(simulador.mostrarEstadoSimulacion());
        	v.showMemoryRam("");
            if (error != null) return error;
            error = maquina.escribirEnMemoria(new ComplexNumber(decimalNumber - 1), "01");
            v.buildMemoryRam(simulador.mostrarEstadoSimulacion());
            v.showMemoryRam("El bit de control de entrada se ha puesto en 1");
            if (error != null) return error;
            //hexa = ingresarHexaPorPantalla(hexa);
            //error = maquina.escribirEnMemoria(new ComplexNumber(decimalNumber - 1), "00");
            //v.buildMemoryRam(simulador.mostrarEstadoSimulacion());
            //v.showMemoryRam("El bit de control de entrada se ha puesto en 0");
            //if (error != null) return error;
        } else {
            hexa = maquina.leerMemoria(numeroCeldaMemoria);
        }
        maquina.escribirEnRegistro(numeroRegistro, hexa);
        String error = maquina.escribirEnMemoria(new ComplexNumber(decimalNumber), "00");
        if (error != null) return error;
        error = maquina.escribirEnMemoria(new ComplexNumber(decimalNumber - 1), "00");
        v.buildMemoryRam(simulador.mostrarEstadoSimulacion());
        v.showMemoryRam("El bit de control de entrada se ha puesto en 0");
        if (error != null) return error;
        return null;
    }

    private String ingresarHexaPorPantalla(String hexa) {
        return vistaConversionHexa.mostrarEntradaHexa(hexa);
    }
}
