package model.instrucciones.factorys;

import model.instrucciones.tipos.Instruccion;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public interface CreadorInstruccion {
    public abstract Instruccion factoryMethod(String lineaCodigoHexa);
}
