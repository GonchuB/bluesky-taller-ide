package main.model.instrucciones.tipos;

import main.apis.FPConversionAPI;
import main.apis.HEXAConversionAPI;
import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;

/**
 * Created by JuanMarchese on 23/04/2014.
 */
public class InstruccionMultiplicarPF extends Instruccion {

    private ComplexNumber registro1;
    private ComplexNumber registro2;
    private ComplexNumber registroDestino;

    public InstruccionMultiplicarPF(ComplexNumber registroFuente1, ComplexNumber registroFuente2, ComplexNumber registroDestino) {
        this.registro1 = registroFuente1;
        this.registro2 = registroFuente2;
        this.registroDestino = registroDestino;
    }

    @Override
    public String operacion(Simulador simulador, MaquinaGenerica maquina) {
        Float f1 = FPConversionAPI.binaryToFloat(HEXAConversionAPI.hex_to_binary(maquina.leerRegistro(registro1)));
        Float f2 = FPConversionAPI.binaryToFloat(HEXAConversionAPI.hex_to_binary(maquina.leerRegistro(registro2)));
        Float resultadoFinal = maquina.getAluControl().mulTwoNumbers(f1, f2);
        String valorAGuardar = FPConversionAPI.floatToHexa(resultadoFinal);
        maquina.escribirEnRegistro(registroDestino, valorAGuardar);
        return null;
    }
}
