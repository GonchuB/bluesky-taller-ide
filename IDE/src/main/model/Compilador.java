package main.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class Compilador {

    FabricaTraductor fabricaTraductor;
    Traductor traductorOpsCdesASM;
    Traductor traductorParamRegexASM;
    Map<String, Integer> cantParamsOpASM;


    public Compilador() {
        this.fabricaTraductor = new FabricaTraductor();
        this.traductorOpsCdesASM = fabricaTraductor.crearTraductorOpsCdes();
        this.traductorParamRegexASM = fabricaTraductor.crearTraductorParamRegex();
        this.cantParamsOpASM = fabricaTraductor.crearMapaCantParamsOp();
    }

    public String compilar(String rutaDeArchivo) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(rutaDeArchivo));
            boolean isAsmFile = rutaDeArchivo.contains(".asm") || rutaDeArchivo.contains(".ASM");
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {

                String error = null;

                if (isAsmFile){
                    error = chequearSyntaxisDeLineaASM(line, i);
                } else {
                    error = chequearSyntaxisDeLineaMAQ(line, i);
                }

                if (error != null) {
                    reader.close();
                    return error;
                }
                i++;
            }
            if (i > 0) {
                reader.close();
            }
        } catch (FileNotFoundException e) {
            return "ERROR - El archivo " + rutaDeArchivo + " no existe.";
        } catch (IOException e) {
            return "ERROR - No se pudo abrir el archivo " + rutaDeArchivo;
        } catch (Exception e) {
            return "ERROR - error en la compilación del archivo  " + rutaDeArchivo;
        }
        return null;
    }

    public String chequearSyntaxisDeLineaMAQ(String line, int nLinea) {
        String[] split = line.split("\\s+");
        String error = null;

        if (split.length == 0) return null;

        if (split.length == 1) {
            error = "Error de syntaxis - Linea " + nLinea + " - Formato instrucción inválido";
        }

        if (error == null ) {
            error = validarPosMemoriaMAQ(nLinea,split[0]);
        }

        if (error == null ) {
            error = validarInstruccionMAQ(nLinea, split[1]);
        }

        return error;
    }

    private String validarInstruccionMAQ(int nLinea, String s) {
        if (s.length() != 4) return "Error de syntaxis - Linea " + nLinea + " - La instrucción no posee 2 bytes";
        String ops = "0123456789ABC";
        if (!ops.contains("" + s.charAt(0))) return "Error de syntaxis - Linea " + nLinea + " - La instrucción no posee un código válido";
        String digits = "0123456789ABCDEF";
        if (!digits.contains("" + s.charAt(1)) || !digits.contains("" + s.charAt(2)) || !digits.contains("" + s.charAt(3))) return "Error de syntaxis - Linea " + nLinea + " - La instrucción no esta en hexadecimal";
        return checkearContenidoInstSCodigo(nLinea,s);
    }

    private String checkearContenidoInstSCodigo(int nLinea, String s) {
        switch (s.charAt(0)){
            case '4':
                if(s.charAt(1) != '0') return "Error de syntaxis - Linea " + nLinea + " - La instrucción posee un fomato inválido";
                break;
            case 'A':
                if(s.charAt(2) != '0') return "Error de syntaxis - Linea " + nLinea + " - La instrucción posee un fomato inválido";
                break;
            case 'C':
                if(s.charAt(1) != '0' || s.charAt(2) != '0' || s.charAt(3) != '0') return "Error de syntaxis - Linea " + nLinea + " - La instrucción posee un fomato inválido";
                break;
            case 'E':
                if(s.charAt(1) != '0') return "Error de syntaxis - Linea " + nLinea + " - La instrucción posee un fomato inválido";
                break;
        }
        return null;
    }

    private String validarPosMemoriaMAQ(int nLinea, String s) {
        if (s.length() != 2) return "Error de syntaxis - Linea " + nLinea + " - La posición de memoria no tiene 2 digitos";
        String digits = "0123456789ABCDEF";
        if (!digits.contains("" + s.charAt(0)) || !digits.contains("" + s.charAt(1))) return "Error de syntaxis - Linea " + nLinea + " - La posición de memoria tiene digitos inválidos";
        return null;
    }

    public String chequearSyntaxisDeLineaASM(String line, int nLinea) {
        String[] split = line.split("\\s+");
        String error = null;

        if (split.length == 0) return null;

        if (isCommentLine(split[0])) return null;

        if (split.length == 1){
            if(!split[0].equals("stp")) {
                    error = "Error de syntaxis - Linea " + nLinea + " - Formato instrucción inválido";
            }
            else{
                return null;
            }
        }

        int comments_value = 2;
        if(split[0].equals("stp")) comments_value = 1;
        if (error == null && split.length > comments_value) {
            error = validarComentariosASM(nLinea, split[comments_value]);
        }

        if (error == null) {
            error = validarOperacionASM(nLinea, split[0]);
        }

        if (error == null && !split[0].equals("stp")) {
            error = validarParametrosOperacionASM(nLinea, split[0], split[1]);
        }

        return error;
    }

    private boolean isCommentLine(String s) {
        return s.charAt(0) == ';';
    }

    private String validarParametrosOperacionASM(int nLinea, String op, String params) {
        String[] paramSplit = params.split("\\s*,\\s*");
        String regex = traductorParamRegexASM.obtenerTraduccion(op);
        if (cantParamsOpASM.get(op) != paramSplit.length)
            return "Error de sintaxis - Linea " + nLinea + " - Numero de parametros incorrectos";
        else if (!params.matches(regex))
            return "Error de sintaxis - Linea " + nLinea + " - Tipo de parametros incorrectos. Se esperaba: " + regex;
        return null;
    }

    private String validarOperacionASM(int nLinea, String op) {
        if (!traductorOpsCdesASM.existeValorKey(op))
            return "Error de syntaxis - Linea " + nLinea + " - Operación desconocida";
        return null;
    }

    private String validarComentariosASM(int nLinea, String comments) {
        if (comments.charAt(0) != ';')
            return "Error de syntaxis - Linea " + nLinea + " - Exceso de caracteres en linea, posible falta de caracter comentario ';'";
        return null;
    }
}
