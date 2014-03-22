package main.model;

import main.model.instrucciones.tipos.Instruccion;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class MaquinaGenerica {
    private static final int TAM_REG = 16;
    private Map<ComplexNumber,Registro> registros;
    private MemoriaPrincipal memoriaPrincipal;
    private Boolean enFuncionamiento;

    public MaquinaGenerica() {
        memoriaPrincipal = new MemoriaPrincipal();
        registros = new HashMap<ComplexNumber, Registro>();
        enFuncionamiento = true;
        for (int i=0; i< TAM_REG; i++){
            ComplexNumber key = new ComplexNumber(i);
            registros.put(key,new Registro(key));
        }
    }



    public void ejecutarInstruccion(Simulador simulador,Instruccion instruccion){
        if (!enFuncionamiento) return;
        memoriaPrincipal.setValor(instruccion.getPosEnMemoria(),instruccion.getLineaCodigo());
        instruccion.operacion(simulador, this);
    }

    public void escribirEnMemoria(ComplexNumber numeroCelda,String hexa){
        if (!enFuncionamiento) return;
        memoriaPrincipal.setValor(numeroCelda,hexa);
    }

    public void escribirEnRegistro(ComplexNumber numeroRegistro,String hexa){
        if (!enFuncionamiento) return;
        registros.get(numeroRegistro).setValor(hexa);
    }

    public String leerMemoria(ComplexNumber numeroCelda){
        return memoriaPrincipal.leerCelda(numeroCelda);
    }

    public String leerRegistro(ComplexNumber numeroRegistro){
        return registros.get(numeroRegistro).getValorHexa();
    }

    public String obtenerEstado(){
        return "";
    }
}
