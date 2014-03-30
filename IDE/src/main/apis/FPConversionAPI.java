package main.apis;

/**
 * Created by GonchuB on 29/03/2014.
 */
public class FPConversionAPI {

    public static Integer MANTISSA_LENGTH = 4;
    public static Integer EXP_LENGTH = 3;
    public static Integer BINARY_LENGTH = 8;

    public static String PRECISION_ERROR = "PRECISION_ERROR";
    public static String OVERFLOW_ERROR = "OVERFLOW_ERROR";

    public String floatToBinaryIntegerPart(Integer intNum) {
        String result = "";
        String binary = HEXAConversionAPI.hex_to_binary(HEXAConversionAPI.decimal_to_hex(intNum));
        boolean mostSignificative = false;
        for (int i = 0; i < binary.length(); i++) {
            char charAt = binary.charAt(i);
            if (charAt != '0' || mostSignificative) {
                mostSignificative = true;
                result += charAt;
            }
        }
        return result;
    }

    public String floatToBinaryFloatPart(Float floatNum) {
        String parsedBinary = "";
        while (floatNum != 0.0f) {
            floatNum = floatNum * 2;
            if (floatNum >= 1.0f) {
                parsedBinary += "1";
                floatNum = floatNum - 1.0f;
            } else {
                parsedBinary += "0";
            }
        }
        return parsedBinary;
    }

    public String floatToBinary(Float floatNum) {
        String binaryNum = null;

        if (floatNum < 0) {
            binaryNum = "1";
            floatNum = -floatNum;
        } else {
            binaryNum = "0";
        }

        Integer integerPart = floatNum.intValue();
        Float floatPart = floatNum - (float) integerPart;

        binaryNum += floatToBinaryIntegerPart(integerPart);
        binaryNum += floatToBinaryFloatPart(floatPart);

        return binaryNum;
    }

    public Float binaryToFloat(String binaryNum) {
        return 0.0f;
    }

}
