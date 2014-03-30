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
        Float float1 = 0.390625f;
        Float float2 = 0.5f;
        Float float3 = 0.25f;
        Float float4 = 0.125f;

        Assert.assertEquals("011001", converter.floatToBinaryFloatPart(float1));
        Assert.assertEquals("1", converter.floatToBinaryFloatPart(float2));
        Assert.assertEquals("01", converter.floatToBinaryFloatPart(float3));
        Assert.assertEquals("001", converter.floatToBinaryFloatPart(float4));
    }

    public void testRoundBinaryMantissa() throws Exception {
        String binary1 = "1111";
        String binary2 = "11111";
        String binary3 = "11110";
        String binary4 = "1010";
        String binary5 = "1101";
        String binary6 = "111";
        String binary7 = "11";
        String binary8 = "1";

        Assert.assertEquals("1111", converter.roundBinaryMantissa(binary1));
        Assert.assertEquals("1111", converter.roundBinaryMantissa(binary2));
        Assert.assertEquals("1111", converter.roundBinaryMantissa(binary3));
        Assert.assertEquals("1010", converter.roundBinaryMantissa(binary4));
        Assert.assertEquals("1101", converter.roundBinaryMantissa(binary5));
        Assert.assertEquals("1110", converter.roundBinaryMantissa(binary6));
        Assert.assertEquals("1100", converter.roundBinaryMantissa(binary7));
        Assert.assertEquals("1000", converter.roundBinaryMantissa(binary8));
    }

    public void testFloatToBinary() throws Exception {
        Float float1 = 0.5f;
        Float float2 = 2.5f;
        Float float3 = 0.25f;
        Float float4 = 0.125f;

        Assert.assertEquals("01000000", converter.floatToBinary(float1));
        Assert.assertEquals("01010010", converter.floatToBinary(float2));
        Assert.assertEquals("01000111", converter.floatToBinary(float3));
        Assert.assertEquals("01000110", converter.floatToBinary(float4));
    }

    public void testBinaryToFloat() throws Exception {

    }
}
