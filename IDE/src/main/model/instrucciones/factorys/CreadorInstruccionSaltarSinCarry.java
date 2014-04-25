package main.model.instrucciones.factorys;

import main.model.ComplexNumber;
import main.model.instrucciones.tipos.Instruccion;
import main.model.instrucciones.tipos.InstruccionSaltarSinCarry;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class CreadorInstruccionSaltarSinCarry implements CreadorInstruccion {
    @Override
    public Instruccion factoryMethod(String posMemoria, String lineaCodigoHexa, String comentario) {
        ComplexNumber posMemoriaInst = new ComplexNumber(""+lineaCodigoHexa.charAt(2)+lineaCodigoHexa.charAt(3));
        Instruccion instruccion = new InstruccionSaltarSinCarry(posMemoriaInst);
        instruccion.setComentario(comentario);
        instruccion.setLineaCodigo(lineaCodigoHexa);
        instruccion.setPosEnMemoria(new ComplexNumber(posMemoria));
        return instruccion;
    }
}
