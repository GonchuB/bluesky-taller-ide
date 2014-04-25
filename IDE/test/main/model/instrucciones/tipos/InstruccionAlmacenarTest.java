package main.model.instrucciones.tipos;

import main.model.BancoRegistros;
import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InstruccionAlmacenarTest {

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
        Instruccion instruccionValida = new InstruccionAlmacenar(new ComplexNumber(0), new ComplexNumber(200));
        Instruccion instruccionZonaInvalida = new InstruccionAlmacenar(new ComplexNumber(0), new ComplexNumber(100));

        Assert.assertNull(instruccionValida.operacion(simulador, maquinaGenerica));
        Assert.assertNotNull(instruccionZonaInvalida.operacion(simulador, maquinaGenerica));
    }
}