package main.apis;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by GonchuB on 29/03/2014.
 */
public class FPConversionAPITest extends TestCase {

    private FPConversionAPI converter;

    public void setUp() throws Exception {
        super.setUp();

        converter = new FPConversionAPI();
    }

    public void testFloatToBinaryIntegerPart() throws Exception {
        Integer int1 = 123;
        Integer int2 = 256;
        Integer int3 = 0;
        Integer int4 = 1025;

        Assert.assertEquals("1111011", converter.floatToBinaryIntegerPart(int1));
        Assert.assertEquals("100000000", converter.floatToBinaryIntegerPart(int2));
        Assert.assertEquals("", converter.floatToBinaryIntegerPart(int3));
        Assert.assertEquals("10000000001", converter.floatToBinaryIntegerPart(int4));
    }

    public void testFloatToBinaryFloatPart() throws Exception {

    }

    public void testFloatToBinary() throws Exception {

    }

    public void testBinaryToFloat() throws Exception {

    }
}
