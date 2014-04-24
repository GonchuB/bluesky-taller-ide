package main.model;

import main.apis.HEXAConversionAPI;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gonchub on 3/26/14.
 */
public class FabricaTraductor {

    public Map<String, Integer> crearMapaCantParamsOp() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 1; i < 16; i++) {
            map.put(getOpStringByCode(i), getOpParamsCountByCode(i));
        }
        return map;
    }

    public Traductor crearTraductorOpsCdes() {
        Traductor traductor = new Traductor();
        for (int i = 1; i < 16; i++) {
            traductor.agregarValor(getOpStringByCode(i), HEXAConversionAPI.decimal_to_hex(i));
        }
        return traductor;
    }

    public Traductor crearTraductorInmCodes() {
        Traductor traductor = new Traductor();
        for (int i = 0; i < 256; i++) {
            traductor.agregarValor(String.valueOf(i), HEXAConversionAPI.decimal_to_hex(i));
        }
        return traductor;
    }

    public Traductor crearTraductorRegCodes() {
        Traductor traductor = new Traductor();
        for (int i = 0; i < 15; i++) {
            traductor.agregarValor("r" + i, HEXAConversionAPI.decimal_to_hex(i));
        }
        return traductor;
    }

    public Traductor crearTraductorParamRegex() {
        Traductor traductor = new Traductor();
        for (int i = 1; i < 16; i++) {
            traductor.agregarValor(getOpStringByCode(i), getParamRegexByCode(i));
        }
        return traductor;
    }

    private String getOpStringByCode(int i) {
        switch (i) {
            case 1:
                return "ldm";
            case 2:
                return "ldi";
            case 3:
                return "stm";
            case 4:
                return "cpy";
            case 5:
                return "add";
            case 6:
                return "addf";
            case 7:
                return "oor";
            case 8:
                return "and";
            case 9:
                return "xor";
            case 10:
                return "rrr";
            case 11:
                return "jpz";
            case 12:
                return "stp";
            case 13:
                return "mulf";
            case 14:
                return "jnc";
            //TOdo - se puede agregar una funcion
            case 15:
                return "";
            default:
                return null;
        }
    }

    private String getParamRegexByCode(int i) {
        String regRegex = "(r([0-9]|1[0-5]))";
        String memRegex = "([0-9]|[0-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])";
        String immRegex = memRegex;
        switch (i) {
            case 1:
                return "^" + regRegex + "," + memRegex +"$";
            case 2:
                return "^" + regRegex + "," + immRegex +"$";
            case 3:
                return "^" + regRegex + "," + memRegex +"$";
            case 4:
                return "^" + regRegex + "," + regRegex +"$";
            case 5:
                return "^" + regRegex + "," + regRegex + ","+ regRegex + "$";
            case 6:
                return "^" + regRegex + "," + regRegex + ","+ regRegex + "$";
            case 7:
                return "^" + regRegex + "," + regRegex + ","+ regRegex + "$";
            case 8:
                return "^" + regRegex + "," + regRegex + ","+ regRegex + "$";
            case 9:
                return "^" + regRegex + "," + regRegex + ","+ regRegex + "$";
            case 10:
                return "^" + regRegex + "," + "([0-9]|1[0-5])" + "$";
            case 11:
                return "^" + regRegex + "," + memRegex + "$";
            case 12:
                return "^$";
            //Todo - Agregar Instrucciones
            case 13:
                return "^" + regRegex + "," + regRegex + ","+ regRegex + "$";
            case 14:
                return "^" + memRegex +"$";
            case 15:
                return "";
            default:
                return null;
        }
    }

    private Integer getOpParamsCountByCode(int i) {
        switch (i) {
            case 1:
                return 2;
            case 2:
                return 2;
            case 3:
                return 2;
            case 4:
                return 2;
            case 5:
                return 3;
            case 6:
                return 3;
            case 7:
                return 3;
            case 8:
                return 3;
            case 9:
                return 3;
            case 10:
                return 2;
            case 11:
                return 2;
            case 12:
                return 0;
            case 13:
                return 3;
            case 14:
                return 1;
            //Todo - Agregar Instrucciones
            case 15:
                return 0;
            default:
                return null;
        }
    }
}
