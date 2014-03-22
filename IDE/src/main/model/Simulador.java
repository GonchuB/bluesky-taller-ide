package main.model;

import main.model.instrucciones.FabricaInstrucciones;
import main.model.instrucciones.tipos.Instruccion;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class Simulador {

    MaquinaGenerica maquinaGenerica;

    ArrayList<Instruccion> instrucciones;

    FabricaInstrucciones fabricaInstrucciones;

    Instruccion instruccionActual;

    Iterator<Instruccion> iteratorInstrucciones;

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
        for (Instruccion instruccion : instrucciones){
            if (instruccion == null) throw  new RuntimeException("La instrucción es desconocida y no se puede ejecutar el código");
            maquinaGenerica.ejecutarInstruccion(instruccion);
        }
        System.out.println("Ejecución finalizada");
    }

    public void iniciarSimulacionPasoAPaso(){
        if (instrucciones.isEmpty()){
            System.out.println("Ejecución finalizada");
            return;
        }
        iteratorInstrucciones = instrucciones.iterator();
        instruccionActual = iteratorInstrucciones.next();
        if (instruccionActual == null) throw  new RuntimeException("La instrucción es desconocida y no se puede ejecutar el código");
        maquinaGenerica.ejecutarInstruccion(instruccionActual);
    }

    public void ejecutarSiguienteInstruccion(){
        if(instruccionActual == null) return;
        if (iteratorInstrucciones.hasNext()){
            instruccionActual = iteratorInstrucciones.next();
            if (instruccionActual == null) throw  new RuntimeException("La instrucción es desconocida y no se puede ejecutar el código");
            maquinaGenerica.ejecutarInstruccion(instruccionActual);
        } else {
            System.out.println("Ejecución finalizada");
        }
    }



}
