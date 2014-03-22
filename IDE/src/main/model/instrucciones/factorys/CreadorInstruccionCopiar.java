package main.model.instrucciones.factorys;

import main.model.ComplexNumber;
import main.model.instrucciones.tipos.Instruccion;
import main.model.instrucciones.tipos.InstruccionCopiar;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class CreadorInstruccionCopiar implements CreadorInstruccion {
    @Override
    public Instruccion factoryMethod(String posMemoria, String lineaCodigoHexa, String comentario) {
        ComplexNumber registroFuente = new ComplexNumber(lineaCodigoHexa.charAt(2));
        ComplexNumber registroDestino = new ComplexNumber(lineaCodigoHexa.charAt(3));
        Instruccion instruccion = new InstruccionCopiar(registroFuente,registroDestino);
        instruccion.setComentario(comentario);
        instruccion.setLineaCodigo(lineaCodigoHexa);
        instruccion.setPosEnMemoria(new ComplexNumber(posMemoria));
        return instruccion;
    }
}
