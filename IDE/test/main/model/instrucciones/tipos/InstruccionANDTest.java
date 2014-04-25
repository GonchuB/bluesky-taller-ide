package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class InstruccionANDTest {

    private Simulador simulador;
    private MaquinaGenerica maquinaGenerica;

    @Before
    public void setUp() throws Exception {
        simulador = new Simulador();
        maquinaGenerica = new MaquinaGenerica();
    }

    @Test
    public void testOperacion() throws Exception {
        maquinaGenerica.escribirEnRegistro(new ComplexNumber(0), "0F");
        maquinaGenerica.escribirEnRegistro(new ComplexNumber(1), "F0");
        Instruccion isFalse = new InstruccionAND(new ComplexNumber(0), new ComplexNumber(1), new ComplexNumber(2));
        isFalse.operacion(simulador, maquinaGenerica);
        Assert.assertEquals(maquinaGenerica.leerRegistro(new ComplexNumber(2)), "00");

        maquinaGenerica.escribirEnRegistro(new ComplexNumber(0), "F5");
        maquinaGenerica.escribirEnRegistro(new ComplexNumber(1), "05");
        Instruccion isMixed = new InstruccionAND(new ComplexNumber(0), new ComplexNumber(1), new ComplexNumber(2));
        isMixed.operacion(simulador, maquinaGenerica);
        Assert.assertEquals(maquinaGenerica.leerRegistro(new ComplexNumber(2)), "05");

        maquinaGenerica.escribirEnRegistro(new ComplexNumber(0), "FF");
        maquinaGenerica.escribirEnRegistro(new ComplexNumber(1), "FF");
        Instruccion isTrue = new InstruccionAND(new ComplexNumber(0), new ComplexNumber(1), new ComplexNumber(2));
        isTrue.operacion(simulador, maquinaGenerica);
        Assert.assertEquals(maquinaGenerica.leerRegistro(new ComplexNumber(2)), "FF");
    }
}