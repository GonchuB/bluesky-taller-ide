package main.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by gonchub on 4/20/14.
 */
public class EstadoMaquinaTest {

    private EstadoMaquina estadoMaquina;
    private BancoRegistros bancoRegs;
    private MemoriaPrincipal memoria;
    private ALUControl alu;

    @Before
    public void setUp() throws Exception {
        bancoRegs = new BancoRegistros();
        memoria = new MemoriaPrincipal();
        alu = new ALUControl();
        estadoMaquina = new EstadoMaquina(bancoRegs, memoria, alu);
    }

    @Test
    public void testCalcularEstadoMaquinaSinModificar() throws Exception {
        estadoMaquina.calcularEstadoMaquina();
        Assert.assertEquals(new Float(0), estadoMaquina.getPorcentajeMemoriaUtilizada());
        Assert.assertEquals(new Float(0), estadoMaquina.getPorcentajeRegistrosUtilizados());
    }

    @Test
    public void testCalcularEstadoMaquinaRegistrosModificados() throws Exception {
        for(int i=0; i<8; i++) {
            bancoRegs.escribirEnRegistro(new ComplexNumber(i), "0A");
        }
        estadoMaquina.calcularEstadoMaquina();
        Assert.assertEquals(new Float(50), estadoMaquina.getPorcentajeRegistrosUtilizados());
        Assert.assertEquals(new Float(0), estadoMaquina.getPorcentajeMemoriaUtilizada());
    }

    @Test
    public void testCalcularEstadoMaquinaMemoriaModificada() throws Exception {
        for(int i=0; i<32; i++) {
            memoria.setValor(new ComplexNumber(255 - i), "AA", false);
        }
        estadoMaquina.calcularEstadoMaquina();
        Assert.assertEquals(new Float(12.5f), estadoMaquina.getPorcentajeMemoriaUtilizada());
        Assert.assertEquals(new Float(0), estadoMaquina.getPorcentajeRegistrosUtilizados());
    }

    @Test
    public void testObtenerValorSeteadoRegistro() throws Exception {
        String hexaValue = "0A";
        ComplexNumber regNumber = new ComplexNumber(4);
        bancoRegs.escribirEnRegistro(regNumber, hexaValue);
        Assert.assertEquals(hexaValue, estadoMaquina.getBancoRegistros().leerRegistro(regNumber));
    }

    @Test
    public void testObtenerValorSeteadoMemoria() throws Exception {
        String hexaValue = "0A";
        ComplexNumber memPos = new ComplexNumber(200);
        memoria.setValor(memPos, hexaValue, false);
        Assert.assertEquals(hexaValue, estadoMaquina.getMemoriaPrincipal().leerCelda(memPos));
    }
}
