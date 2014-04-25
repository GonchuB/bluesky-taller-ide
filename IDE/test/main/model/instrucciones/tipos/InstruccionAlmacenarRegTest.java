package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InstruccionAlmacenarRegTest {

    private Simulador simulador;
    private MaquinaGenerica maquinaGenerica;

    @Before
    public void setUp() throws Exception {
        simulador = new Simulador();
        maquinaGenerica = new MaquinaGenerica();
        for (Integer i = 0; i < 16; i++) {
            maquinaGenerica.escribirEnRegistro(new ComplexNumber(i), "" + i);
        }
    }

    @Test
    public void testOperacion() throws Exception {
        maquinaGenerica.escribirEnRegistro(new ComplexNumber(6), "F0");
        Instruccion instruccionValida = new InstruccionAlmacenarReg(new ComplexNumber(5), new ComplexNumber(6));
        Instruccion instruccionZonaInvalida = new InstruccionAlmacenarReg(new ComplexNumber(5), new ComplexNumber(5));

        Assert.assertNull(instruccionValida.operacion(simulador, maquinaGenerica));
        Assert.assertNotNull(instruccionZonaInvalida.operacion(simulador, maquinaGenerica));
    }
}