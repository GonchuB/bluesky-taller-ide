package main.model;

/**
 * Created by gonchub on 24/03/14.
 */
public class EstadoMaquina {

    private BancoRegistros bancoRegistros;
    private MemoriaPrincipal memoriaPrincipal;
    private ALUControl aluControlBits;
    private Float porcentajeMemoriaUtilizada;
    private Float porcentajeRegistrosUtilizados;

    public EstadoMaquina(BancoRegistros regs, MemoriaPrincipal mem, ALUControl aluBits) {
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

    public void setPorcentajeRegistrosUtilizados(Float porcentajeRegistrosUtilizados) {
        this.porcentajeRegistrosUtilizados = porcentajeRegistrosUtilizados;
    }

    public ALUControl getAluControlBits() {
        return aluControlBits;
    }

    public void setAluControlBits(ALUControl aluControlBits) {
        this.aluControlBits = aluControlBits;
    }

    public BancoRegistros getBancoRegistros() {
        return bancoRegistros;
    }

    public void setBancoRegistros(BancoRegistros bancoRegistros) {
        this.bancoRegistros = bancoRegistros;
    }

    public MemoriaPrincipal getMemoriaPrincipal() {
        return memoriaPrincipal;
    }

    public void setMemoriaPrincipal(MemoriaPrincipal memoriaPrincipal) {
        this.memoriaPrincipal = memoriaPrincipal;
    }
}