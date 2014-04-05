package main.model.instrucciones.tipos;

import main.apis.FPConversionAPI;
import main.apis.HEXAConversionAPI;
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
    public String operacion(Simulador simulador, MaquinaGenerica maquina) {
        Float f1 = FPConversionAPI.binaryToFloat(HEXAConversionAPI.hex_to_binary(maquina.leerRegistro(registro1)));
        Float f2 = FPConversionAPI.binaryToFloat(HEXAConversionAPI.hex_to_binary(maquina.leerRegistro(registro2)));
        Float resultadoFinal = maquina.getAluControl().addTwoNumbers(f1, f2);
        String valorAGuardar = FPConversionAPI.floatToBinary(resultadoFinal);
        maquina.escribirEnRegistro(registroDestino, valorAGuardar);
        return null;
    }
}
