package main.model.instrucciones.factorys;

import main.model.ComplexNumber;
import main.model.instrucciones.tipos.Instruccion;
import main.model.instrucciones.tipos.InstruccionOR;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class CreadorInstruccionOR implements CreadorInstruccion {
    @Override
    public Instruccion factoryMethod(String posMemoria, String lineaCodigoHexa, String comentario) {
        ComplexNumber registroDestino = new ComplexNumber(lineaCodigoHexa.charAt(1));
        ComplexNumber registroFuente1 = new ComplexNumber(lineaCodigoHexa.charAt(2));
        ComplexNumber registroFuente2 = new ComplexNumber(lineaCodigoHexa.charAt(3));
        Instruccion instruccion = new InstruccionOR(registroFuente1,registroFuente2,registroDestino);
        instruccion.setComentario(comentario);
        instruccion.setLineaCodigo(lineaCodigoHexa);
        instruccion.setPosEnMemoria(new ComplexNumber(posMemoria));
        return instruccion;
    }
}
