package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InstruccionCargarInmediatoTest {

    private Simulador simulador;
    private MaquinaGenerica maquinaGenerica;

    @Before
    public void setUp() throws Exception {
        simulador = new Simulador();
        maquinaGenerica = new MaquinaGenerica();
    }

    @Test
    public void testOperacion() throws Exception {
        String aCargar = "AB";
        Instruccion instruccionValida = new InstruccionCargarInmediato(new ComplexNumber(0), aCargar);
        instruccionValida.operacion(simulador, maquinaGenerica);

        Assert.assertEquals(aCargar, maquinaGenerica.leerRegistro(new ComplexNumber(0)));
    }
}