package main.apis;

/**
 * Created by GonchuB on 29/03/2014.
 */
public class FPConversionAPI {

    public static Integer MANTISSA_LENGTH = 4;
    public static Integer EXP_LENGTH = 3;

    public static String floatToBinaryIntegerPart(Integer intNum) {
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

    public static String floatToBinaryFloatPart(Float floatNum) {
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

    public static String roundBinaryMantissa(String binary) {

        // Strip 0's from msbs.
        String stripped = "";
        boolean mostSignificative = false;
        for (int i = 0; i < binary.length(); i++) {
            char charAt = binary.charAt(i);
            if (charAt != '0' || mostSignificative) {
                mostSignificative = true;
                stripped += charAt;
            }
        }

        String rounded = "";
        for (int i = 0; i < MANTISSA_LENGTH; i++) {
            if (stripped.length() > i) {
                rounded += stripped.charAt(i);
            } else {
                rounded += "0";
            }
        }
        return rounded;
    }

    public static boolean isPrecisionLostInConversion(Float floatNum) {
        Integer integerPart = floatNum.intValue();
        Float floatPart = floatNum - (float) integerPart;

        String integerPartBinary = floatToBinaryIntegerPart(integerPart);
        String floatPartBinary = floatToBinaryFloatPart(floatPart);
        String mantissa = roundBinaryMantissa(integerPartBinary + floatPartBinary);
        return mantissa.length() < integerPartBinary.length() + floatPartBinary.length();
    }

    public static String floatToBinary(Float floatNum) {

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
        String exponentBinary = HEXAConversionAPI.a2_decimal_to_binary(exponentCandidate, EXP_LENGTH);

        // Concatenate sign + mantissa + exponent.
        return binarySign + mantissa + exponentBinary;
    }

    public static Float binaryToFloat(String binaryNum) {

        // Separate binary in its 3 components (sign, mantissa, exponent).
        boolean isNegative = binaryNum.charAt(0) == '1';
        String mantissa = binaryNum.substring(1, 5);
        String exponent = binaryNum.substring(5, 8);

        // Extend exponent sign to be an 8 bit binary.
        char exponentExtender = exponent.charAt(0);
        for (int i = 0; i < 5; i++) {
            exponent = exponentExtender + exponent;
        }
        // Extend mantissa to be an 8 bit binary.
        for (int i = 0; i < 4; i++) {
            mantissa = '0' + mantissa;
        }

        // Convert exponent and mantissa.
        Integer exponentValue = HEXAConversionAPI.hex_to_a2_decimal(HEXAConversionAPI.binary_to_hex(exponent));
        Integer mantissaValueNoComma = Integer.parseInt(HEXAConversionAPI.hex_to_decimal(HEXAConversionAPI.binary_to_hex(mantissa)));
        Float mantissaValue = (float) (mantissaValueNoComma / Math.pow(2, 4));

        // float = (sign) * mantissa * 2^exponent.
        Float finalValue = (float) (mantissaValue * Math.pow(2, exponentValue));
        if (isNegative) return -finalValue;
        return finalValue;
    }

}
