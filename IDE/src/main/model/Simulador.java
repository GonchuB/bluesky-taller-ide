package main.model;

import main.model.instrucciones.FabricaInstrucciones;
import main.model.instrucciones.tipos.Instruccion;

import javax.swing.*;
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
        resetearMaquina();
        if (instrucciones.isEmpty()){
            JOptionPane.showMessageDialog(null, "Ejecución finalizada", "Simulador", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        iteratorInstrucciones = instrucciones.values().iterator();
        simulando = true;
        instruccionActual = iteratorInstrucciones.next();
        if (instruccionActual == null) {
            JOptionPane.showMessageDialog(null, "La instrucción es desconocida y no se puede ejecutar el código", "Simulador", JOptionPane.ERROR_MESSAGE);
            pararSimulacion();
            return;
        }
        String error = maquinaGenerica.ejecutarInstruccion(this,instruccionActual);
        if(error != null){
            JOptionPane.showMessageDialog(null, error, "Simulador", JOptionPane.ERROR_MESSAGE);
            pararSimulacion();
            return;
        }
    }

    public void ejecutarSiguienteInstruccion(){
        if (iteratorInstrucciones.hasNext()){
            instruccionActual = iteratorInstrucciones.next();
            if (instruccionActual == null) {
                JOptionPane.showMessageDialog(null, "La instrucción es desconocida y no se puede ejecutar el código", "Simulador", JOptionPane.ERROR_MESSAGE);
                pararSimulacion();
                return;
            }
            String error = maquinaGenerica.ejecutarInstruccion(this,instruccionActual);
            if(error != null){
                JOptionPane.showMessageDialog(null, error, "Simulador", JOptionPane.ERROR_MESSAGE);
                pararSimulacion();
                return;
            }

        } else {
            pararSimulacion();
        }
    }


    public void pararSimulacion() {
        if(simulando){
            simulando = false;
            JOptionPane.showMessageDialog(null, "Ejecución finalizada", "Simulador", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void setearProximaInstruccionSegunNumero(ComplexNumber numeroCeldaMemoria,String instruccionAComparar) {
        if (numeroCeldaMemoria.getDecimalNumber() % 2 != 0){
            JOptionPane.showMessageDialog(null, "Instruccion destino inválida", "Simulador", JOptionPane.ERROR_MESSAGE);
            pararSimulacion();
        }
        iteratorInstrucciones = instrucciones.values().iterator();
        if (numeroCeldaMemoria.getDecimalNumber() != 0) {
            //La proxima instruccion se setea en el metodo ejecutarSiguienteInstruccion(),
            //por lo que aqui debemos dejar la instruccion actual en la posicion anterior a la q se va a ejecutar
            for (int i= 0 ; i < (numeroCeldaMemoria.getDecimalNumber()/2); i++){
                if(iteratorInstrucciones.hasNext()){
                    instruccionActual = iteratorInstrucciones.next();
                } else {
                    JOptionPane.showMessageDialog(null, "No existe una instruccion en la celda de memoria indicada", "Simulador", JOptionPane.ERROR_MESSAGE);
                    pararSimulacion();
                }
            }
            //Puede ser que la instruccion no este cargada a memoria todavia! Esa validación no es valida
            /*if(simulando && !instruccionActual.getLineaCodigo().equals(instruccionAComparar)){
                JOptionPane.showMessageDialog(null, "La instruccion en memoria es distinta a la de la posicion que se quizo saltar en el simulador", "Simulador", JOptionPane.ERROR_MESSAGE);
                pararSimulacion();
            }*/
        }
    }

    public EstadoMaquina mostrarEstadoSimulacion() {
        EstadoMaquina estadoMaquina = maquinaGenerica.obtenerEstado();
        return estadoMaquina;
    }

    private void resetearMaquina() {
        maquinaGenerica = new MaquinaGenerica();
    }

    public boolean isSimulando() {
        return simulando;
    }

    public int getPositionInstruction()
    {
        Set entrySet = instrucciones.entrySet();
        Iterator it = entrySet.iterator();
        for (int i = 0 ; i < instrucciones.size(); i++)
        {
            if (instrucciones.get(i).toString().equals(instruccionActual.toString()))
            {
                return i;
            }
        }

        return -1;
    }
}
