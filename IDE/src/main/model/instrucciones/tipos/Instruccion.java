package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public abstract class Instruccion {

    private ComplexNumber posEnMemoria;
    private String comentario;
    private String lineaCodigo;

    public abstract void operacion(MaquinaGenerica maquina);

    public ComplexNumber getPosEnMemoria() {
        return posEnMemoria;
    }

    protected void setPosEnMemoria(ComplexNumber posEnMemoria) {
        this.posEnMemoria = posEnMemoria;
    }

    public String getComentario() {
        return comentario;
    }

    protected void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getLineaCodigo() {
        return lineaCodigo;
    }

    protected void setLineaCodigo(String lineaCodigo) {
        this.lineaCodigo = lineaCodigo;
    }
}
