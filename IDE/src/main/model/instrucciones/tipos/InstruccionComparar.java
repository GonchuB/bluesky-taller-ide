package main.model.instrucciones.tipos;

import main.apis.HEXAConversionAPI;
import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class InstruccionComparar extends Instruccion {
    private ComplexNumber registro1;
    private ComplexNumber registro2;
    private ComplexNumber registroDestino;

    public InstruccionComparar(ComplexNumber registro1, ComplexNumber registro2, ComplexNumber registroDestino) {
        this.registro1 = registro1;
        this.registro2 = registro2;
        this.registroDestino = registroDestino;
    }

    @Override
    public String operacion(Simulador simulador, MaquinaGenerica maquina) {
        String valor1 = HEXAConversionAPI.hex_to_decimal(maquina.leerRegistro(registro1), HEXAConversionAPI.ConversionType.A2COMPLEMENT);
        String valor2 = HEXAConversionAPI.hex_to_decimal(maquina.leerRegistro(registro2), HEXAConversionAPI.ConversionType.A2COMPLEMENT);
        Integer int1 = new Integer(valor1);
        Integer int2 = new Integer(valor2);
        Integer resultadoFinal = null;
        if (int1 > int2) resultadoFinal = 2;
        else if (int1 == int2) resultadoFinal = 1;
        else if (int1 < int2) resultadoFinal = 0;
        String valorAGuardar = HEXAConversionAPI.a2_decimal_to_hex(resultadoFinal);
        maquina.escribirEnRegistro(registroDestino, valorAGuardar);
        return null;
    }
}
