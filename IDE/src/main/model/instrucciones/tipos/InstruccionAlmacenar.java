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
            //Muestro que se almacena el dato en memoria
            v.buildMemoryRam(simulador.mostrarEstadoSimulacion());
            v.showMemoryRam("Se escribió el valor "+ hexa+ " en el puerto de salia (Memoria - Posicion " + numeroDeCelda.getHexaNumber() + ")");

            //Seteo bit de control en 1 para indicar que se debe consumir el dato desde afuera
            error = maquina.escribirEnMemoria(new ComplexNumber(numeroDeCelda.getDecimalNumber() - 1),"01");
            v.buildMemoryRam(simulador.mostrarEstadoSimulacion());
            v.showMemoryRam("Bit de control en 1 para indicar que se debe consumir desde puerto de salida");
            if(error != null) return error;

            //El dato se consume
            vistaConversionHexa.mostrarSalidaHexa(hexa);

            //Se setea el bit de control en 0 para indicar que el dato fue consumido externamente
            error = maquina.escribirEnMemoria(new ComplexNumber(numeroDeCelda.getDecimalNumber() - 1),"00");
            v.buildMemoryRam(simulador.mostrarEstadoSimulacion());
            v.showMemoryRam("Bit de control en 0 para indicar que el dato ya fue consumido de manera externa del puerto de salida");
            if(error != null) return error;
        }
        return null;
    }
}
