package main.model.instrucciones.tipos;

import main.apis.HEXAConversionAPI;
import main.apis.bitvector.BitVector;
import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class InstruccionXOR extends Instruccion {
    private ComplexNumber registro1;
    private ComplexNumber registro2;
    private ComplexNumber registroDestino;

    public InstruccionXOR(ComplexNumber registro1, ComplexNumber registro2, ComplexNumber registroDestino) {
        this.registro1 = registro1;
        this.registro2 = registro2;
        this.registroDestino = registroDestino;
    }

    @Override
    public String operacion(Simulador simulador, MaquinaGenerica maquina) {
        String h1 = maquina.leerRegistro(registro1);
        String h2 = maquina.leerRegistro(registro2);
        BitVector bv = HEXAConversionAPI.hex_to_bitvector(h1);
        bv.xorVector(HEXAConversionAPI.hex_to_bitvector(h2));
        String hResult = HEXAConversionAPI.bitvector_to_hex(bv);
        maquina.escribirEnRegistro(registroDestino,hResult);
        return null;
    }
}
