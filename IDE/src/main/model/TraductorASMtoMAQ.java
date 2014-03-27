package main.model;

import main.apis.HEXAConversionAPI;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Juan-Asus on 27/03/2014.
 */
public class TraductorASMtoMAQ {
    FabricaTraductor fabricaTraductor;
    Traductor traductorOpsCdes;
    Traductor traductorRegCodes;
    Traductor traductorInmCodes;
    Traductor traductorParamRegex;
    Map<String, Integer> cantParamsOp;

    public TraductorASMtoMAQ() {
        this.fabricaTraductor = new FabricaTraductor();
        this.traductorOpsCdes = fabricaTraductor.crearTraductorOpsCdes();
        this.traductorRegCodes = fabricaTraductor.crearTraductorRegCodes();
        this.traductorInmCodes = fabricaTraductor.crearTraductorInmCodes();
        this.traductorParamRegex = fabricaTraductor.crearTraductorParamRegex();
        this.cantParamsOp = fabricaTraductor.crearMapaCantParamsOp();
    }

    public String traducir(String rutaArchivoASM) {
        return validarYTraducirLineasDeCodigo(rutaArchivoASM);
    }

    private String validarYTraducirLineasDeCodigo(String rutaArchivoASM) {
        String rutaArchivoMAQ = rutaArchivoASM;
        rutaArchivoMAQ = rutaArchivoMAQ.replace(".asm", ".maq");
        BufferedReader reader;
        BufferedWriter writer;
        try {
            reader = new BufferedReader(new FileReader(rutaArchivoASM));
            writer = new BufferedWriter(new FileWriter(rutaArchivoMAQ));
            String line;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String error = chequearSyntaxisDeLinea(line, i);
                if (error != null) {
                    File archMAQ = new File(rutaArchivoMAQ);
                    if (archMAQ.exists()) archMAQ.delete();
                    return error;
                }

                String lineaTraducida = traducirLineaALenguajeMaquina(line);
                writer.write(lineNumberToBytes(i));
                writer.write(" ");
                writer.write(lineaTraducida);
                writer.newLine();

                i++;
            }
            if (i > 0) {
                writer.close();
                reader.close();
                return null;
            }
        } catch (FileNotFoundException e) {
            return "ERROR - El archivo " + rutaArchivoASM + " no existe.";
        } catch (IOException e) {
            return "ERROR - No se pudo abrir el archivo " + rutaArchivoASM;
        } catch (Exception e) {
            return "ERROR - error en la compilación del archivo  " + rutaArchivoASM;
        }
        return "ERROR - El archivo " + rutaArchivoASM + " está vacio";
    }

    public String lineNumberToBytes(Integer i) {
        String posMemoria = HEXAConversionAPI.decimal_to_hex(i * 2);
        if (posMemoria.length() == 1) posMemoria = "0" + posMemoria;

        return posMemoria;
    }

    public boolean isRegisterParam(String param) {
        return param.charAt(0) == 'r';
    }

    public String registerParamToString(String registerParam, String opCode) {
        String replaced = registerParam.replaceFirst("r", "");
        if (opCode.equals("4")) {
            return "0" + HEXAConversionAPI.decimal_to_hex(Integer.parseInt(replaced));
        }
        return HEXAConversionAPI.decimal_to_hex(Integer.parseInt(replaced));
    }

    public String immediateParamToString(String immediateParam, String opCode) {
        Integer paramInteger = Integer.parseInt(immediateParam);
        if (opCode.equals("A") || paramInteger < 16) {
            return "0" + HEXAConversionAPI.decimal_to_hex(paramInteger);
        }
        return HEXAConversionAPI.decimal_to_hex(paramInteger);
    }

    public String traducirLineaALenguajeMaquina(String line) {
        String[] split = line.split("\\s+");

        String opName = split[0];
        String opCode = traductorOpsCdes.obtenerTraduccion(opName);
        String[] params = split[1].split("\\s*,\\s*");
        ArrayList<String> parsedParams = new ArrayList<String>();

        for (String param : params) {
            if (isRegisterParam(param)) {
                parsedParams.add(registerParamToString(param, opCode));
            } else {
                parsedParams.add(immediateParamToString(param, opCode));
            }
        }

        String comments = "";

        if (split.length > 2) {
            comments = split[2].replace(";", "");
            for (int i = 3; i < split.length; i++) {
                comments += " " + split[i];
            }
        }

        String translatedInstruction = "";

        translatedInstruction += opCode;
        for (String param : parsedParams) {
            translatedInstruction += param;
        }

        translatedInstruction += " " + comments;

        return translatedInstruction;
    }

    public String chequearSyntaxisDeLinea(String line, int nLinea) {
        String[] split = line.split("\\s+");
        String error = null;

        if (split.length == 0) return null;

        if (split.length == 1 && !split[0].equals("stp")) {
            error = "Error de syntaxis - Linea " + nLinea + " - Formato instrucción inválido";
        }

        if (error == null && split.length > 2) {
            error = validarComentarios(nLinea, split[2]);
        }

        if (error == null) {
            error = validarOperacion(nLinea, split[0]);
        }

        if (error == null) {
            error = validarParametrosOperacion(nLinea, split[0], split[1]);
        }

        return error;
    }

    public String validarParametrosOperacion(int nLinea, String op, String params) {
        // TODO: matchear el string de parametros con traductorParamRegex. Actualizar valores en FabricaTraductor.
        String[] paramSplit = params.split("\\s*,\\s*");
        String regex = traductorParamRegex.obtenerTraduccion(op);
        if (cantParamsOp.get(op) != paramSplit.length)
            return "Error de sintaxis - Linea " + nLinea + " - Numero de parametros incorrectos";
        else if (!params.matches(regex))
            return "Error de sintaxis - Linea " + nLinea + " - Tipo de parametros incorrectos. Se esperaba: " + regex;
        return null;
    }

    public String validarOperacion(int nLinea, String op) {
        if (!traductorOpsCdes.existeValorKey(op))
            return "Error de syntaxis - Linea " + nLinea + " - Operación desconocida";
        return null;
    }

    public String validarComentarios(int nLinea, String comments) {
        if (comments.charAt(0) != ';')
            return "Error de syntaxis - Linea " + nLinea + " - Exceso de caracteres en linea, posible falta de caracter comentario ';'";
        return null;
    }
}
