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
        for (int i = 1; i < 13; i++) {
            map.put(getOpStringByCode(i), getOpParamsCountByCode(i));
        }
        return map;
    }

    public Traductor crearTraductorOpsCdes() {
        Traductor traductor = new Traductor();
        for (int i = 1; i < 13; i++) {
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
        for (int i = 0; i < 15; i++) {
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
            default:
                return null;
        }
    }

    private String getParamRegexByCode(int i) {
        switch (i) {
            case 1:
                // "^r[0-15],[0-15]{1,2}$"
                return "R,XY";
            case 2:
                return "^r[0-15],[0-9a-fA-F]{1,2}$";
            case 3:
                return "^r[0-15],[0-9a-fA-F]{1,2}$";
            case 4:
                return "^r([0-9]|1[0-5]),r([0-9]|1[0-5])$";
            case 5:
                return "^r([0-9]|1[0-5]),r([0-9]|1[0-5]),r([0-9]|1[0-5])$";
            case 6:
                return "^r([0-9]|1[0-5]),r([0-9]|1[0-5]),r([0-9]|1[0-5])$";
            case 7:
                return "^r([0-9]|1[0-5]),r([0-9]|1[0-5]),r([0-9]|1[0-5])$";
            case 8:
                return "^r([0-9]|1[0-5]),r([0-9]|1[0-5]),r([0-9]|1[0-5])$";
            case 9:
                return "^r([0-9]|1[0-5]),r([0-9]|1[0-5]),r([0-9]|1[0-5])$";
            case 10:
                return "^r[0-15],[0-9a-fA-F]$";
            case 11:
                return "^r[0-15],[0-9a-fA-F]{1,2}$";
            case 12:
                return "^$";
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
            default:
                return null;
        }
    }
}
