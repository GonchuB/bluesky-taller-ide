package main.model;

import main.apis.HEXAConversionAPI;
import main.model.instrucciones.tipos.Instruccion;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class Compilador {

    Traductor traductorOpsCdes;
    Traductor traductorRegCodes;
    Traductor traductorInmCodes;

    public Compilador() {
        this.traductorOpsCdes = crearTraductorOpsCdes();
        this.traductorRegCodes = crearTraductorRegCodes();
        this.traductorInmCodes = crearTraductorInmCodes();
    }

    private Traductor crearTraductorInmCodes() {
        Traductor traductor = new Traductor();
        for(int i=0 ; i< 256 ;i++){
            traductor.agregarValor(String.valueOf(i), HEXAConversionAPI.decimal_to_hex(i));
        }
        return traductor;
    }

    private Traductor crearTraductorRegCodes() {
        Traductor traductor = new Traductor();
        for(int i=0 ; i< 15 ;i++){
            traductor.agregarValor("r"+i, HEXAConversionAPI.decimal_to_hex(i));
        }
        return traductor;
    }

    private Traductor crearTraductorOpsCdes() {
        Traductor traductor = new Traductor();
        for(int i=1 ; i< 13 ;i++){
            traductor.agregarValor(getOpStringByCode(i), HEXAConversionAPI.decimal_to_hex(i));
        }
        return traductor;
    }

    private String getOpStringByCode(int i) {
        switch (i) {
            case 1: return "ldm";
            case 2: return "ldi";
            case 3: return "stm";
            case 4: return "cpy";
            case 5: return "add";
            case 6: return "addf";
            case 7: return "oor";
            case 8: return "and";
            case 9: return "xor";
            case 10: return "rrr";
            case 11: return "jpz";
            case 12: return "stp";
            default: return null;
        }
    }

    public String compilar(String rutaArchivoASM){
        return validarYTraducirLineasDeCodigo(rutaArchivoASM);
    }

    private String validarYTraducirLineasDeCodigo(String rutaArchivoASM) {
        String rutaArchivoMAQ = rutaArchivoASM;
        rutaArchivoMAQ = rutaArchivoMAQ.replace(".asm",".maq");
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(rutaArchivoASM));
            writer = new BufferedWriter(new FileWriter(rutaArchivoMAQ));
            String line = null;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String error = chequearSyntaxisDeLinea(rutaArchivoASM,line);
                if (error != null){
                    File archMAQ = new File(rutaArchivoMAQ);
                    if (archMAQ.exists()) archMAQ.delete();
                    return error;
                }
                String lineaTraducida = traducirLineaALenguajeMaquina(line);
                writer.write(lineaTraducida);
                writer.newLine();
                i++;
            }
            if (i>0) return "Compilación exitosa - Se creó el archivo " + rutaArchivoMAQ;
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        } catch (Exception e) {

        }
        return "ERROR - El archivo " + rutaArchivoASM + " está vacio";
    }

    private String traducirLineaALenguajeMaquina(String line) {
        return null;
    }

    private String chequearSyntaxisDeLinea(String rutaArchivoASM, String line) {
        return null;
    }
}
