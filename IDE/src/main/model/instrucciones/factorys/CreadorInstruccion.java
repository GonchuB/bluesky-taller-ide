package main.model.instrucciones.factorys;

import main.model.instrucciones.tipos.Instruccion;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public interface CreadorInstruccion {
    public abstract Instruccion factoryMethod(String posMemoria, String lineaCodigoHexa,String comentario);
}
