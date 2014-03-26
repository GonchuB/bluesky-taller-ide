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
    Map<String,Integer> cantParamsSOp;

    public Compilador() {
        this.traductorOpsCdes = crearTraductorOpsCdes();
        this.traductorRegCodes = crearTraductorRegCodes();
        this.traductorInmCodes = crearTraductorInmCodes();
        this.cantParamsSOp = crearMapaCantParamsSOp();
    }

    private Map<String, Integer> crearMapaCantParamsSOp() {
        Map<String,Integer> map = new HashMap<String, Integer>();
        for(int i=1 ; i< 13 ;i++){
            map.put(getOpStringByCode(i), getOpParamsCountByCode(i));
        }
        return map;
    }

    private Integer getOpParamsCountByCode(int i) {
        switch (i) {
            case 1: return 2;
            case 2: return 2;
            case 3: return 2;
            case 4: return 2;
            case 5: return 3;
            case 6: return 3;
            case 7: return 3;
            case 8: return 3;
            case 9: return 3;
            case 10: return 2;
            case 11: return 2;
            case 12: return 0;
            default: return null;
        }
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
                String error = chequearSyntaxisDeLinea(rutaArchivoASM,line,i);
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

    private String traducirLineaALenguajeMaquina(String line){
        String[] split = line.split("\\s+");
        return null;
    }

    private String chequearSyntaxisDeLinea(String rutaArchivoASM, String line,int nLinea) {
        String[] split = line.split("\\s+");
        String error = null;

        if (split.length == 0) return null;

        if (split.length == 1 && !split[0].equals("stp")){
            error = "Error de syntaxis - Linea " + nLinea + " - Formato instrucción inválido";
        }

        if (error == null && split.length > 2){
            error = validarComentarios(nLinea,split[2]);
        }

        if (error == null) {
            error = validarOperacion(nLinea,split[0]);
        }

        if (error == null){
            error = validarParametrosSOperacion(nLinea,split[0],split[1]);
        }

        return error;
    }

    private String validarParametrosSOperacion(int nLinea,String op, String params) {
        return null;
    }

    private String validarOperacion(int nLinea,String op) {
        if (traductorOpsCdes.existeValorKey(op)) return "Error de syntaxis - Linea " + nLinea + " - Operación desconocida";
        return null;
    }

    private String validarComentarios(int nLinea,String comments) {
        if (comments.charAt(0) != ';') return "Error de syntaxis - Linea " + nLinea + " - Exceso de caracteres en linea, posible falta de caracter comentario ';'";
        return null;
    }
}
