package main.model;

import main.model.instrucciones.tipos.Instruccion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class MaquinaGenerica {
    private BancoRegistros bancoRegistros;
    private MemoriaPrincipal memoriaPrincipal;
    private Boolean enFuncionamiento;

    public MaquinaGenerica() {
        memoriaPrincipal = new MemoriaPrincipal();
        bancoRegistros = new BancoRegistros();
        enFuncionamiento = true;
    }


    public void ejecutarInstruccion(Simulador simulador, Instruccion instruccion) {
        if (!enFuncionamiento) return;
        memoriaPrincipal.setValor(instruccion.getPosEnMemoria(), instruccion.getLineaCodigo());
        instruccion.operacion(simulador, this);
    }

    public void escribirEnMemoria(ComplexNumber numeroCelda, String hexa) {
        if (!enFuncionamiento) return;
        memoriaPrincipal.setValor(numeroCelda, hexa);
    }

    public void escribirEnRegistro(ComplexNumber numeroRegistro, String hexa) {
        if (!enFuncionamiento) return;
        bancoRegistros.escribirEnRegistro(numeroRegistro, hexa);
    }

    public String leerMemoria(ComplexNumber numeroCelda) {
        return memoriaPrincipal.leerCelda(numeroCelda);
    }

    public String leerRegistro(ComplexNumber numeroRegistro) {
        return bancoRegistros.leerRegistro(numeroRegistro);
    }

    public EstadoMaquina obtenerEstado() {
        EstadoMaquina estadoActual = new EstadoMaquina(bancoRegistros, memoriaPrincipal, "");
        estadoActual.calcularEstadoMaquina();
        return estadoActual;
    }
}
