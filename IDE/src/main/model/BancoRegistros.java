package main.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gonchub on 24/03/14.
 */
public class BancoRegistros {
    private static final int TAM_REG = 16;
    private Map<ComplexNumber, Registro> registros;

    public BancoRegistros() {
        registros = new HashMap<ComplexNumber, Registro>();
        for (int i = 0; i < TAM_REG; i++) {
            ComplexNumber key = new ComplexNumber(i);
            registros.put(key, new Registro(key));
        }
    }

    public void escribirEnRegistro(ComplexNumber numeroRegistro, String hexa) {
        registros.get(numeroRegistro).setValor(hexa);
    }

    public Float obtenerPorcentajeRegistrosUtilizados() {
        Float regsUtilizados = 0f;
        for (Map.Entry<ComplexNumber, Registro> entry : registros.entrySet()) {
            if (entry.getValue().getModificado()) {
                regsUtilizados += 1f;
            }
        }
        regsUtilizados = regsUtilizados / TAM_REG;
        return 100 * regsUtilizados;
    }

    public String leerRegistro(ComplexNumber numeroRegistro) {
        return registros.get(numeroRegistro).getValorHexa();
    }
}
