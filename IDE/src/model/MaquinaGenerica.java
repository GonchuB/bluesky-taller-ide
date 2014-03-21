package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class MaquinaGenerica {
    private static final int TAM_REG = 16;
    Map<ComplexNumber,Registro> registros;
    MemoriaPrincipal memoriaPrincipal;

    public MaquinaGenerica() {
        memoriaPrincipal = new MemoriaPrincipal();
        registros = new HashMap<ComplexNumber, Registro>();
        for (int i=0; i< TAM_REG; i++){
            ComplexNumber key = new ComplexNumber(i);
            registros.put(key,new Registro(key,"00"));
        }
    }
}
