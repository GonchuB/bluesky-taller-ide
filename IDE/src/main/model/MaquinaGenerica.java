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
    private ALUControl aluControl;

    public MaquinaGenerica() {
        memoriaPrincipal = new MemoriaPrincipal();
        bancoRegistros = new BancoRegistros();
        aluControl = new ALUControl();
        enFuncionamiento = true;
    }

    public String ejecutarInstruccion(Simulador simulador, Instruccion instruccion) {
        if (!enFuncionamiento) return null;
        String error = memoriaPrincipal.setValor(instruccion.getPosEnMemoria(), instruccion.getLineaCodigo(), true);
        if (error == null) error = instruccion.operacion(simulador, this);
        return error;
    }

    public String escribirEnMemoria(ComplexNumber numeroCelda, String hexa) {
        if (!enFuncionamiento) return null;
        String error = memoriaPrincipal.setValor(numeroCelda, hexa, false);
        return error;
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
        EstadoMaquina estadoActual = new EstadoMaquina(bancoRegistros, memoriaPrincipal, aluControl);
        estadoActual.calcularEstadoMaquina();
        return estadoActual;
    }

    public ALUControl getAluControl() {
        return aluControl;
    }
}
