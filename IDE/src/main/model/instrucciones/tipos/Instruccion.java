package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public abstract class Instruccion {

    private ComplexNumber posEnMemoria;
    private String comentario;
    private String lineaCodigo;

    public abstract String operacion(Simulador simulador, MaquinaGenerica maquina);

    public ComplexNumber getPosEnMemoria() {
        return posEnMemoria;
    }

    public void setPosEnMemoria(ComplexNumber posEnMemoria) {
        this.posEnMemoria = posEnMemoria;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getLineaCodigo() {
        return lineaCodigo;
    }

    public void setLineaCodigo(String lineaCodigo) {
        this.lineaCodigo = lineaCodigo;
    }
}
