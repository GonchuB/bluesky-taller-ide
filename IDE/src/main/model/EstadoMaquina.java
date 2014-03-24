package main.model;

import java.util.Map;

/**
 * Created by gonchub on 24/03/14.
 */
public class EstadoMaquina {

    private BancoRegistros bancoRegistros;
    private MemoriaPrincipal memoriaPrincipal;
    private String aluControlBits;
    private Float porcentajeMemoriaUtilizada;
    private Float porcentajeRegistrosUtilizados;

    public EstadoMaquina(BancoRegistros regs, MemoriaPrincipal mem, String aluBits) {
        bancoRegistros = regs;
        memoriaPrincipal = mem;
        aluControlBits = aluBits;
    }

    public void calcularEstadoMaquina() {
        this.setPorcentajeMemoriaUtilizada(memoriaPrincipal.obtenerPorcentajeUtilizado());
        this.setPorcentajeRegistrosUtilizados(bancoRegistros.obtenerPorcentajeRegistrosUtilizados());
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