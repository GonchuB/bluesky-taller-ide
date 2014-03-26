package main.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by JuanchoM on 25/03/2014.
 */
public class Traductor {
    Map<String,String> mapa;
    Map<String,String> mapaInvertido;

    public Traductor() {
        this.mapa = new HashMap<String, String>();
        this.mapaInvertido = new HashMap<String, String>();
    }

    public void agregarValor(String key,String value){
        mapa.put(key,value);
        mapaInvertido.put(value,key);
    }

    public String obtenerTraduccion(String valor){
        String s = mapa.get(valor);
        if (s == null) s = mapaInvertido.get(valor);
        return s;
    }

    public boolean existeValorKey(String valor){
        String s = mapa.get(valor);
        if (s == null) return false;
        return true;
    }

    public boolean existeValorValue(String valor){
        String s = mapaInvertido.get(valor);
        if (s == null) return false;
        return true;
    }
}
