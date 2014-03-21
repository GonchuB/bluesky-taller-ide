package model;

import model.instrucciones.tipos.Instruccion;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class Simulador {
    File archivoASimular;

    MaquinaGenerica maquinaGenerica;

    ArrayList<Instruccion> instrucciones;

    Instruccion instruccionActual;

    public Simulador(String rutaArchivo) {
        maquinaGenerica = new MaquinaGenerica();
        generarInstruccionesDesdeArchivo(rutaArchivo);
    }

    private void generarInstruccionesDesdeArchivo(String rutaArchivo) {

    }

    public void iniciarSimulacionCompleta(){

    }

    public void iniciarSimulacionPasoAPaso(){

    }



}
