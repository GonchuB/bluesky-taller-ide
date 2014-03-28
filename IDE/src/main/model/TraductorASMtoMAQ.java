package main.model;

import main.apis.HEXAConversionAPI;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Juan-Asus on 27/03/2014.
 */
public class TraductorASMtoMAQ {
    private Compilador compilador;
    private FabricaTraductor fabricaTraductor;
    private Traductor traductorOpsCdes;


    public TraductorASMtoMAQ() {
        this.fabricaTraductor = new FabricaTraductor();
        this.compilador = new Compilador();
        this.traductorOpsCdes = fabricaTraductor.crearTraductorOpsCdes();
    }

    /**
     * Valida y traduce el archivo cuya ruta se recibe por parametro. Devuelve un String
     * con el error que ocurrio durante la traducción o null si no hubo error. Al traducir,
     * el archivo MAQ tiene el mismo nombre (y ruta) que el ASM, pero difiere en su extension.
     *
     * @param rutaArchivoASM la ruta del archivo ASM a traducir.
     * @return error ocurrido durante la traduccion.
     */
    public String traducir(String rutaArchivoASM) {
        return validarYTraducirLineasDeCodigo(rutaArchivoASM);
    }

    /**
     * Valida y traduce cada una de las lineas del archivo especificado por parametro.
     * Devuelve un String con el error que ocurrio al traducir la linea o null si no
     * hubo error.
     *
     * @param rutaArchivoASM la ruta del archivo ASM a traducir.
     * @return error ocurrido durante la traduccion.
     */
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
                String error = compilador.chequearSyntaxisDeLineaASM(line, i);
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

    /**
     * Traduce el numero de linea recibido por parametro a su expresion
     * en hexadecimal. El hexadecimal devuelto tiene 2 digitos (se agrega
     * un 0 adelante del numero si este es menor a 0xF.
     *
     * @param i numero de linea.
     * @return expresion hexadecimal del numero de linea (byte count).
     */
    public String lineNumberToBytes(Integer i) {
        String posMemoria = HEXAConversionAPI.decimal_to_hex(i * 2);
        if (posMemoria.length() == 1) posMemoria = "0" + posMemoria;

        return posMemoria;
    }

    /**
     * Chequea si el parametro recibido corresponde a un registro.
     *
     * @param param parametro a chequear.
     * @return true si el parametro es un registro. false si no lo es.
     */
    public boolean isRegisterParam(String param) {
        return param.charAt(0) == 'r';
    }

    /**
     * Convierte un parametro que es un registro (ASM) a su notacion
     * en MAQ.
     *
     * @param registerParam el registro parametro a traducir.
     * @param opCode        el codigo de operacion en la que se usa el registro.
     * @return representacion del registro para ser utilizada en una instruccion MAQ.
     */
    public String registerParamToString(String registerParam, String opCode) {
        String replaced = registerParam.replaceFirst("r", "");
        if (opCode.equals("4")) {
            return "0" + HEXAConversionAPI.decimal_to_hex(Integer.parseInt(replaced));
        }
        return HEXAConversionAPI.decimal_to_hex(Integer.parseInt(replaced));
    }


    /**
     * Convierte un parametro inmediato (ASM) a su notacion en MAQ.
     *
     * @param immediateParam el parametro inmediato a traducir.
     * @param opCode         el codigo de operacion en la que se usa el registro.
     * @return representacion del registro para ser utilizada en una instruccion MAQ.
     */
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



}
