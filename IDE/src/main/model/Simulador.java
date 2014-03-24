package main.model;

import main.model.instrucciones.FabricaInstrucciones;
import main.model.instrucciones.tipos.Instruccion;

import java.io.*;
import java.util.*;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class Simulador {

    private MaquinaGenerica maquinaGenerica;

    private Map<Integer,Instruccion> instrucciones;

    private FabricaInstrucciones fabricaInstrucciones;

    private Instruccion instruccionActual;

    private Iterator<Instruccion> iteratorInstrucciones;

    private boolean simulando;

    public Simulador() {
        maquinaGenerica = new MaquinaGenerica();
        fabricaInstrucciones = new FabricaInstrucciones();
        instrucciones = new LinkedHashMap<Integer, Instruccion>();
        simulando = false;
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
            int i = 0;
            while ((line = reader.readLine()) != null) {
                Instruccion instruccion = fabricaInstrucciones.crearInstruccion(line);
                //Agrego las instrucciones null, despues en la ejecución habra un runtime exception por instruccion desconocida
                instrucciones.put(i,instruccion);
                i++;
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } catch (Exception e) {

        }

    }

    public void iniciarSimulacionCompleta(){
        iniciarSimulacionPasoAPaso();
        while (simulando) ejecutarSiguienteInstruccion();

    }

    public void iniciarSimulacionPasoAPaso(){
        if (instrucciones.isEmpty()){
            System.out.println("Ejecución finalizada");
            return;
        }
        iteratorInstrucciones = instrucciones.values().iterator();
        simulando = true;
        instruccionActual = iteratorInstrucciones.next();
        if (instruccionActual == null) {
            System.out.println("La instrucción es desconocida y no se puede ejecutar el código");
            pararSimulacion();
            return;
        }
        maquinaGenerica.ejecutarInstruccion(this,instruccionActual);
    }

    public void ejecutarSiguienteInstruccion(){
        if (iteratorInstrucciones.hasNext()){
            instruccionActual = iteratorInstrucciones.next();
            if (instruccionActual == null) {
                System.out.println("La instrucción es desconocida y no se puede ejecutar el código");
                pararSimulacion();
                return;
            }
            maquinaGenerica.ejecutarInstruccion(this,instruccionActual);
        } else {
            pararSimulacion();
        }
    }


    public void pararSimulacion() {
        simulando = false;
        System.out.println("Ejecución finalizada");
    }

    public void setearProximaInstruccionSegunNumero(ComplexNumber numeroCeldaMemoria,String instruccionAComparar) {
        if (numeroCeldaMemoria.getDecimalNumber() % 2 != 0){
            System.out.println("Instruccion destino inválida");
            pararSimulacion();
        }
        iteratorInstrucciones = instrucciones.values().iterator();
        if (numeroCeldaMemoria.getDecimalNumber() == 0) instruccionActual = iteratorInstrucciones.next();
        else {
            for (int i= 0 ; i < (numeroCeldaMemoria.getDecimalNumber()/2)+1; i++){
                if(iteratorInstrucciones.hasNext()){
                    instruccionActual = iteratorInstrucciones.next();
                } else {
                    System.out.println("No existe una instruccion en la celda de memoria indicada");
                    pararSimulacion();
                }
            }
            if(simulando && !instruccionActual.getLineaCodigo().equals(instruccionAComparar)){
                System.out.println("La instruccion en memoria es distinta a la de la posicion que se quizo saltar en el simulador");
                pararSimulacion();
            }
        }
    }

    public void mostrarEstadoSimulacion() {
        EstadoMaquina estadoMaquina = maquinaGenerica.obtenerEstado();
        System.out.println("Alu bits: "+ estadoMaquina.getAluControlBits());
        System.out.println("% Mem Used: "+ estadoMaquina.getPorcentajeMemoriaUtilizada());
        System.out.println("% Regs Used: "+ estadoMaquina.getPorcentajeRegistrosUtilizados());
    }
}
