package main.apis;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Created by GonchuB on 29/03/2014.
 */
public class FPConversionAPITest extends TestCase {

    public void testFloatToBinaryIntegerPart() throws Exception {
        Integer int1 = 123;
        Integer int2 = 256;
        Integer int3 = 0;
        Integer int4 = 1025;

        Assert.assertEquals("1111011", FPConversionAPI.floatToBinaryIntegerPart(int1));
        Assert.assertEquals("100000000", FPConversionAPI.floatToBinaryIntegerPart(int2));
        Assert.assertEquals("", FPConversionAPI.floatToBinaryIntegerPart(int3));
        Assert.assertEquals("10000000001", FPConversionAPI.floatToBinaryIntegerPart(int4));
    }

    public void testFloatToBinaryFloatPart() throws Exception {
        Float float1 = 0.390625f;
        Float float2 = 0.5f;
        Float float3 = 0.25f;
        Float float4 = 0.125f;

        Assert.assertEquals("011001", FPConversionAPI.floatToBinaryFloatPart(float1));
        Assert.assertEquals("1", FPConversionAPI.floatToBinaryFloatPart(float2));
        Assert.assertEquals("01", FPConversionAPI.floatToBinaryFloatPart(float3));
        Assert.assertEquals("001", FPConversionAPI.floatToBinaryFloatPart(float4));
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

        Assert.assertEquals("1111", FPConversionAPI.roundBinaryMantissa(binary1));
        Assert.assertEquals("1111", FPConversionAPI.roundBinaryMantissa(binary2));
        Assert.assertEquals("1111", FPConversionAPI.roundBinaryMantissa(binary3));
        Assert.assertEquals("1010", FPConversionAPI.roundBinaryMantissa(binary4));
        Assert.assertEquals("1101", FPConversionAPI.roundBinaryMantissa(binary5));
        Assert.assertEquals("1110", FPConversionAPI.roundBinaryMantissa(binary6));
        Assert.assertEquals("1100", FPConversionAPI.roundBinaryMantissa(binary7));
        Assert.assertEquals("1000", FPConversionAPI.roundBinaryMantissa(binary8));
    }

    public void testFloatToBinary() throws Exception {
        Float float1 = 0.5f;
        Float float2 = 2.5f;
        Float float3 = 0.25f;
        Float float4 = 0.125f;

        Assert.assertEquals("01000000", FPConversionAPI.floatToBinary(float1));
        Assert.assertEquals("01010010", FPConversionAPI.floatToBinary(float2));
        Assert.assertEquals("01000111", FPConversionAPI.floatToBinary(float3));
        Assert.assertEquals("01000110", FPConversionAPI.floatToBinary(float4));
    }

    public void testIsPrecisionLostInConversion() throws Exception {
        Float float1 = 0.5f;
        Float float2 = 0.390625f;
        Float float3 = 1.125f;
        Float float4 = 2.125f;

        Assert.assertFalse(FPConversionAPI.isPrecisionLostInConversion(float1));
        Assert.assertTrue(FPConversionAPI.isPrecisionLostInConversion(float2));
        Assert.assertFalse(FPConversionAPI.isPrecisionLostInConversion(float3));
        Assert.assertTrue(FPConversionAPI.isPrecisionLostInConversion(float4));
    }

    public void testBinaryToFloat() throws Exception {

    }
}
