package main.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class Compilador {

    FabricaTraductor fabricaTraductor;
    Traductor traductorOpsCdes;
    Traductor traductorRegCodes;
    Traductor traductorInmCodes;
    Map<String, Integer> cantParamsOp;

    public Compilador() {
        this.fabricaTraductor = new FabricaTraductor();
        this.traductorOpsCdes = fabricaTraductor.crearTraductorOpsCdes();
        this.traductorRegCodes = fabricaTraductor.crearTraductorRegCodes();
        this.traductorInmCodes = fabricaTraductor.crearTraductorInmCodes();
        this.cantParamsOp = fabricaTraductor.crearMapaCantParamsOp();
    }

    public String compilar(String rutaArchivoASM) {
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
            String line = null;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                String error = chequearSyntaxisDeLinea(rutaArchivoASM, line, i);
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

    private String lineNumberToBytes(Integer i) {
        Integer byteCount = 2 * i;
        if (byteCount < 10) {
            return "0" + byteCount.toString();
        }
        return byteCount.toString();
    }

    private boolean isRegisterParam(String param) {
        return param.charAt(0) == 'r';
    }

    private String registerParamToString(String registerParam, String opCode) {
        String replaced = registerParam.replaceFirst("r", "");
        if (opCode.equals("4")) {
            return "0" + replaced;
        }
        return replaced;
    }

    private String immediateParamToString(String immediateParam, String opCode) {
        Integer paramInteger = Integer.parseInt(immediateParam);
        if (opCode.equals("A") || paramInteger < 10) {
            return "0" + immediateParam;
        }
        return immediateParam;
    }

    private String traducirLineaALenguajeMaquina(String line) {
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

        String translatedInstruction = "";

        translatedInstruction += opCode;
        for (String param : parsedParams) {
            translatedInstruction += param;
        }

        return translatedInstruction;
    }

    private String chequearSyntaxisDeLinea(String rutaArchivoASM, String line, int nLinea) {
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

    private String validarParametrosOperacion(int nLinea, String op, String params) {
        // TODO: validamos el tipo de parametros que le llega (registro, valor)? Habria que hacer otro tipo de map ademas del de cantidad.
        String[] paramSplit = params.split("\\s*,\\s*");
        if (cantParamsOp.get(op) != paramSplit.length)
            return "Error de sintaxis - Linea " + nLinea + " - Numero de parametros incorrectos";
        return null;
    }

    private String validarOperacion(int nLinea, String op) {
        if (!traductorOpsCdes.existeValorKey(op))
            return "Error de syntaxis - Linea " + nLinea + " - Operación desconocida";
        return null;
    }

    private String validarComentarios(int nLinea, String comments) {
        if (comments.charAt(0) != ';')
            return "Error de syntaxis - Linea " + nLinea + " - Exceso de caracteres en linea, posible falta de caracter comentario ';'";
        return null;
    }
}
