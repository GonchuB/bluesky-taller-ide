package apis;

import main.apis.HEXAConversionAPI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.Assert;

/**
 * Created by Juan-Asus on 24/03/2014.
 */
@RunWith(JUnit4.class)
public class HEXAConversionAPITest {

    @Test
    public void testHexa_To_DecimalConversions() {
        String fa = HEXAConversionAPI.hex_to_decimal("FA");
        Assert.assertEquals("250", fa);

        String fa1 = HEXAConversionAPI.hex_to_decimal("FA", HEXAConversionAPI.ConversionType.A2COMPLEMENT);
        Assert.assertEquals("-6",fa1);

        String fa2 = HEXAConversionAPI.hex_to_decimal("FA", HEXAConversionAPI.ConversionType.FLOATINGPOINT);
        Assert.assertEquals("3.5E-43",fa2);
    }

    @Test
    public void testDecimal_To_HEXConversions() {
        String fa = HEXAConversionAPI.decimal_to_hex("250");
        Assert.assertEquals("FA", fa);

        String fa0 = HEXAConversionAPI.decimal_to_hex(250);
        Assert.assertEquals("FA", fa0);

        String fa1 = HEXAConversionAPI.a2_decimal_to_hex(-1);
        Assert.assertEquals("FF",fa1);

        String fa2 = HEXAConversionAPI.a2_decimal_to_hex(3);
        Assert.assertEquals("3",fa2);

        String fa3 = HEXAConversionAPI.fp_decimal_to_hex(new Float("3.5E-43"));
        Assert.assertEquals("FA",fa3);

    }

    @Test
    public void testBinary_To_HEXConversions() {
        String s = HEXAConversionAPI.binary_to_hex("11111010");
        Assert.assertEquals("FA",s);
        String s1 = HEXAConversionAPI.binary_to_hex("00000011");
        Assert.assertEquals("03",s1);
    }

    @Test
    public void testHex_To_BinaryConversions() {
        String fa = HEXAConversionAPI.hex_to_binary("03");
        Assert.assertEquals("00000011",fa);

        String fa2 = HEXAConversionAPI.hex_to_binary("FA");
        Assert.assertEquals("11111010",fa2);
    }



}
