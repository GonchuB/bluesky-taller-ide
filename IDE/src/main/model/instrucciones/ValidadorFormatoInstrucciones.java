package main.model.instrucciones;

import main.model.instrucciones.factorys.*;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class ValidadorFormatoInstrucciones {

    public static final String REGEX_LINEA_CODIGO = "\\s+";

    private String[] split;

    public ValidadorFormatoInstrucciones(String lineaCodigo) {
        split = lineaCodigo.split(REGEX_LINEA_CODIGO);
    }

    public boolean esLineaValida(){
        if (split.length < 2) return false;
        return true;
    }

    public String obtenerPorcionInstruccion() {
        return split[1];
    }

    public String obtenerComentario() {
        if (split.length > 2){
            StringBuilder sb = new StringBuilder();
            for (int i=2; i < split.length;i++){
                sb.append(split[i]);
                if (i != split.length-1) sb.append(" ");
            }
            String r = sb.toString();
            return r;
        }
        return "";
    }

    public String obtenerPosMemoria() {
        return split[0];
    }

    public char obtenerCodigoOp() {
        String instruccion = obtenerPorcionInstruccion();
        return instruccion.charAt(0);
    }


}
