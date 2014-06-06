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
            //Seteo bit de control en 1 para comenzar a leer desde el puerto
            String error = maquina.escribirEnMemoria(new ComplexNumber(decimalNumber - 1), "01");
            v.buildMemoryRam(simulador.mostrarEstadoSimulacion());
            v.showMemoryRam("Bit de control en 1 para comenzar a leer desde puerto de entrada");
            if (error != null) return error;

            //Ingreso valor en puerto de entrada y guardo en memoria
            hexa = ingresarHexaPorPantalla();
            error = maquina.escribirEnMemoria(numeroCeldaMemoria,hexa);
        	v.buildMemoryRam(simulador.mostrarEstadoSimulacion());
        	v.showMemoryRam("Se guardo el valor " + hexa + " en la posición " + numeroCeldaMemoria.getHexaNumber() + " que corresponde al puerto de entrada");
            if (error != null) return error;

            //Bit de control en 0 indicando que se termino la lectura desde el puerto de entrada
            error = maquina.escribirEnMemoria(new ComplexNumber(decimalNumber - 1), "00");
            v.buildMemoryRam(simulador.mostrarEstadoSimulacion());
            v.showMemoryRam("Bit de control en 0 indicando que se terminó de leer desde puerto de entrada");
            if (error != null) return error;
        } else {
            hexa = maquina.leerMemoria(numeroCeldaMemoria);
        }
        maquina.escribirEnRegistro(numeroRegistro, hexa);

        return null;
    }

    private String ingresarHexaPorPantalla() {
        return vistaConversionHexa.mostrarEntradaHexa();
    }
}
