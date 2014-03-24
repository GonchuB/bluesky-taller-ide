package main.model;

import java.util.Map;

/**
 * Created by gonchub on 24/03/14.
 */
public class EstadoMaquina {

    private Map<ComplexNumber, Registro> registros;
    private MemoriaPrincipal memoriaPrincipal;
    private String aluControlBits;
    private Float porcentajeMemoriaUtilizada;
    private Float porcentajeRegistrosUtilizados;

    public EstadoMaquina(Map<ComplexNumber, Registro> regs, MemoriaPrincipal mem, String aluBits) {
        registros = regs;
        memoriaPrincipal = mem;
        aluControlBits = aluBits;
    }

    public void calcularEstadoMaquina() {
        this.setPorcentajeMemoriaUtilizada(memoriaPrincipal.obtenerPorcentajeUtilizado());
    }

    public Float getPorcentajeMemoriaUtilizada() {
        return porcentajeMemoriaUtilizada;
    }

    public void setPorcentajeMemoriaUtilizada(Float porcentajeMemoriaUtilizada) {
        this.porcentajeMemoriaUtilizada = porcentajeMemoriaUtilizada;
    }

    public Float getPorcentajeRegistrosUtilizados() {
        return porcentajeRegistrosUtilizados;
    }

    public String getAluControlBits() {
        return aluControlBits;
    }

    public void setAluControlBits(String aluControlBits) {
        this.aluControlBits = aluControlBits;
    }

    public void setPorcentajeRegistrosUtilizados(Float porcentajeRegistrosUtilizados) {
        this.porcentajeRegistrosUtilizados = porcentajeRegistrosUtilizados;
    }
}