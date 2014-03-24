package main.model.instrucciones.tipos;

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
    public void operacion(Simulador simulador, MaquinaGenerica maquina) {
        String valor1 = HEXAConversionAPI.hex_to_decimal(maquina.leerRegistro(registro1), HEXAConversionAPI.ConversionType.FLOATINGPOINT);
        String valor2 = HEXAConversionAPI.hex_to_decimal(maquina.leerRegistro(registro2), HEXAConversionAPI.ConversionType.FLOATINGPOINT);
        Float f1 = new Float(valor1);
        Float f2 = new Float(valor2);
        Float resultadoFinal = maquina.getAluControl().addTwoNumbers(f1, f2);
        String valorAGuardar = HEXAConversionAPI.fp_decimal_to_hex(resultadoFinal);
        maquina.escribirEnRegistro(registroDestino, valorAGuardar);

    }
}
