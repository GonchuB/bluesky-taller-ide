package main.model.instrucciones.factorys;

import main.apis.HEXAConversionAPI;
import main.model.ComplexNumber;
import main.model.instrucciones.tipos.Instruccion;
import main.model.instrucciones.tipos.InstruccionRotar;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class CreadorInstruccionRotar implements CreadorInstruccion {
    @Override
    public Instruccion factoryMethod(String posMemoria, String lineaCodigoHexa, String comentario) {
        ComplexNumber registroFuente = new ComplexNumber(lineaCodigoHexa.charAt(1));
        int cantRotaciones = Integer.parseInt(HEXAConversionAPI.hex_to_decimal(String.valueOf(lineaCodigoHexa.charAt(3))));
        Instruccion instruccion = new InstruccionRotar(cantRotaciones,registroFuente);
        instruccion.setComentario(comentario);
        instruccion.setLineaCodigo(lineaCodigoHexa);
        instruccion.setPosEnMemoria(new ComplexNumber(posMemoria));
        return instruccion;
    }
}
