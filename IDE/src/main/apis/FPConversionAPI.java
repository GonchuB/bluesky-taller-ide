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

    public String roundBinaryMantissa(String binary) {
        String rounded = "";
        for (int i = 0; i < MANTISSA_LENGTH; i++) {
            if (binary.length() > i) {
                rounded += binary.charAt(i);
            } else {
                rounded += "0";
            }
        }
        return rounded;
    }

    public String floatToBinary(Float floatNum) {

        // Sign calculation.
        String binarySign;
        if (floatNum < 0) {
            binarySign = "1";
            floatNum = -floatNum;
        } else {
            binarySign = "0";
        }

        // Mantissa calculation.
        Integer integerPart = floatNum.intValue();
        Float floatPart = floatNum - (float) integerPart;

        String integerPartBinary = floatToBinaryIntegerPart(integerPart);
        String floatPartBinary = floatToBinaryFloatPart(floatPart);
        String mantissa = roundBinaryMantissa(integerPartBinary + floatPartBinary);

        // Exponent calculation.
        Integer exponentCandidate = 0;
        if (integerPartBinary.length() > 0) {
            exponentCandidate = integerPartBinary.length();
        } else if (floatPartBinary.length() > 0) {
            for (int i = 0; i < floatPartBinary.length(); i++) {
                if (floatPartBinary.charAt(i) != '0') {
                    exponentCandidate = -i;
                    break;
                }
            }
        } else {
            exponentCandidate = 0;
        }
        String exponentBinary = HEXAConversionAPI.hex_to_binary(HEXAConversionAPI.a2_decimal_to_hex(exponentCandidate));

        // Concatenate sign + mantissa + exponent.
        return binarySign + mantissa + exponentBinary;
    }

    public Float binaryToFloat(String binaryNum) {
        return 0.0f;
    }

}
