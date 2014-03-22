package main.model.instrucciones.factorys;

import main.model.ComplexNumber;
import main.model.instrucciones.tipos.Instruccion;
import main.model.instrucciones.tipos.InstruccionParar;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class CreadorInstruccionParar implements CreadorInstruccion {
    @Override
    public Instruccion factoryMethod(String posMemoria, String lineaCodigoHexa, String comentario) {
        Instruccion instruccion = new InstruccionParar();
        instruccion.setComentario(comentario);
        instruccion.setLineaCodigo(lineaCodigoHexa);
        instruccion.setPosEnMemoria(new ComplexNumber(posMemoria));
        return instruccion;
    }
}
