package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;
import main.ui.VistaEntradaSalida;
import main.ui.VistaMemoria;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class InstruccionAlmacenar extends Instruccion {
    private ComplexNumber numeroRegistro;
    private ComplexNumber numeroDeCelda;
    private VistaEntradaSalida vistaConversionHexa;

    public InstruccionAlmacenar(ComplexNumber numeroRegistro, ComplexNumber numeroDeCelda) {
        this.numeroRegistro = numeroRegistro;
        this.numeroDeCelda = numeroDeCelda;
        this.vistaConversionHexa = new VistaEntradaSalida();
    }

    @Override
    public String operacion(Simulador simulador, MaquinaGenerica maquina) {
        String hexa = maquina.leerRegistro(numeroRegistro);
        String error = maquina.escribirEnMemoria(numeroDeCelda,hexa);
        VistaMemoria v = new VistaMemoria(simulador.mostrarEstadoSimulacion());
        if(error != null) return error;
        if (numeroDeCelda.getDecimalNumber() == 255){
            /*error = maquina.escribirEnMemoria(new ComplexNumber(numeroDeCelda.getDecimalNumber() - 1),"00");
            v.buildMemoryRam(simulador.mostrarEstadoSimulacion());
            v.showMemoryRam("El bit de control de salida se ha puesto en 0");
            if(error != null) return error;*/
            error = maquina.escribirEnMemoria(new ComplexNumber(numeroDeCelda.getDecimalNumber()),hexa);
            v.buildMemoryRam(simulador.mostrarEstadoSimulacion());
            v.showMemoryRam("");
            if(error != null) return error;
            //vistaConversionHexa.mostrarSalidaHexa(hexa);
            error = maquina.escribirEnMemoria(new ComplexNumber(numeroDeCelda.getDecimalNumber() - 1),"01");
            v.buildMemoryRam(simulador.mostrarEstadoSimulacion());
            v.showMemoryRam("El bit de control de salida se ha puesto en 1");
            if(error != null) return error;
            vistaConversionHexa.mostrarSalidaHexa(hexa);
            error = maquina.escribirEnMemoria(new ComplexNumber(numeroDeCelda.getDecimalNumber()),"00");
            if(error != null) return error;
            error = maquina.escribirEnMemoria(new ComplexNumber(numeroDeCelda.getDecimalNumber() - 1),"00");
            v.buildMemoryRam(simulador.mostrarEstadoSimulacion());
            v.showMemoryRam("El bit de control de salida se ha puesto en 0");
            if(error != null) return error;
        }
        return null;
    }
}
