package main.model.instrucciones.factorys;

import main.model.ComplexNumber;
import main.model.instrucciones.tipos.Instruccion;
import main.model.instrucciones.tipos.InstruccionAlmacenar;
import main.model.instrucciones.tipos.InstruccionAlmacenarReg;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class CreadorInstruccionAlmacenarReg implements CreadorInstruccion {
    @Override
    public Instruccion factoryMethod(String posMemoria, String lineaCodigoHexa, String comentario) {
        ComplexNumber registroFuente = new ComplexNumber(""+lineaCodigoHexa.charAt(2));
        ComplexNumber posMemoriaDestino = new ComplexNumber(""+lineaCodigoHexa.charAt(3));
        Instruccion instruccion = new InstruccionAlmacenarReg(registroFuente,posMemoriaDestino);
        instruccion.setComentario(comentario);
        instruccion.setLineaCodigo(lineaCodigoHexa);
        instruccion.setPosEnMemoria(new ComplexNumber(posMemoria));
        return instruccion;
    }


}
