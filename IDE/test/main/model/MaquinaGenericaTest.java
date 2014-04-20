package main.model;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by gonchub on 4/20/14.
 */
public class MaquinaGenericaTest {

    private MaquinaGenerica maquinaGenerica;

    @Before
    public void setUp() throws Exception {
        maquinaGenerica = new MaquinaGenerica();
    }

    @Test
    public void testEscribirEnMemoria() throws Exception {
        String hexaValue = "AA";
        ComplexNumber memPos = new ComplexNumber(200);
        maquinaGenerica.escribirEnMemoria(memPos, hexaValue);

        Assert.assertEquals(hexaValue, maquinaGenerica.leerMemoria(memPos));
    }

    @Test
    public void testEscribirEnRegistro() throws Exception {
        String hexaValue = "AA";
        ComplexNumber regNum = new ComplexNumber(8);
        maquinaGenerica.escribirEnRegistro(regNum, hexaValue);

        Assert.assertEquals(hexaValue, maquinaGenerica.leerRegistro(regNum));
    }

    @Test
    public void testObtenerEstado() throws Exception {
        for (int i = 0; i < 8; i++) {
            maquinaGenerica.escribirEnRegistro(new ComplexNumber(i), "0A");
        }
        for (int i = 0; i < 32; i++) {
            maquinaGenerica.escribirEnMemoria(new ComplexNumber(255 - i), "AA");
        }
        org.junit.Assert.assertEquals(new Float(50), maquinaGenerica.obtenerEstado().getPorcentajeRegistrosUtilizados());
        org.junit.Assert.assertEquals(new Float(12.5f), maquinaGenerica.obtenerEstado().getPorcentajeMemoriaUtilizada());
    }

}
