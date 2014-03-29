package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class InstruccionCargarInmediato extends Instruccion {
    private ComplexNumber numeroRegistro;
    private String patronACargar;

    public InstruccionCargarInmediato(ComplexNumber numeroRegistro, String patronACargar) {
        this.numeroRegistro = numeroRegistro;
        this.patronACargar = patronACargar;
    }

    @Override
    public String operacion(Simulador simulador, MaquinaGenerica maquina) {
        maquina.escribirEnRegistro(numeroRegistro,patronACargar);
        return null;
    }
}
