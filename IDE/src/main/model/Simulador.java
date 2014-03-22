package main.model;

import main.model.instrucciones.FabricaInstrucciones;
import main.model.instrucciones.tipos.Instruccion;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class Simulador {

    MaquinaGenerica maquinaGenerica;

    ArrayList<Instruccion> instrucciones;

    FabricaInstrucciones fabricaInstrucciones;

    Instruccion instruccionActual;

    public Simulador() {
        maquinaGenerica = new MaquinaGenerica();
        fabricaInstrucciones = new FabricaInstrucciones();
        instrucciones = new ArrayList<Instruccion>();
    }

    public void init(String rutaArchivoHexa){
        //ChekarCompilación aca o que se haga antes??
        generarInstruccionesDesdeArchivo(rutaArchivoHexa);
    }

    private void generarInstruccionesDesdeArchivo(String rutaArchivo) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(rutaArchivo));
            String line = null;
            while ((line = reader.readLine()) != null) {
                Instruccion instruccion = fabricaInstrucciones.crearInstruccion(line);
                //Agrego las instrucciones null, despues en la ejecución habra un runtime exception por instruccion desconocida
                instrucciones.add(instruccion);
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } catch (Exception e) {

        }

    }

    public void iniciarSimulacionCompleta(){

    }

    public void iniciarSimulacionPasoAPaso(){

    }



}
