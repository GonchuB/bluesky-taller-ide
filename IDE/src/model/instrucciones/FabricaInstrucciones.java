package model.instrucciones;

import model.instrucciones.factorys.CreadorInstruccion;
import model.instrucciones.factorys.CreadorInstruccionCargarInmediato;
import model.instrucciones.tipos.Instruccion;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class FabricaInstrucciones {
    public Instruccion crearInstruccion(String lineaCodigo){
        Boolean esCodigoDeMaquina = chekearTipoCodigo(lineaCodigo);

        String lineaCodigoHexa = lineaCodigo;

        if(!esCodigoDeMaquina){
            lineaCodigoHexa = traducirEnsambladorACM(lineaCodigo);
        }

        char codigoOp = lineaCodigoHexa.charAt(0);

        CreadorInstruccion creadorInstruccion = obtenerCreadorInstruccionSCodigoOP(codigoOp);

        return creadorInstruccion.factoryMethod(lineaCodigoHexa);
    }

    private CreadorInstruccion obtenerCreadorInstruccionSCodigoOP(char codigoOp) {
        return null;
    }

    private String traducirEnsambladorACM(String lineaCodigo) {
        return null;
    }

    private Boolean chekearTipoCodigo(String lineaCodigo) {
        return null;
    }
}
