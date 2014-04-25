package main.apis;

import main.apis.bitvector.BitVector;

/**
 * Created by Juan-Asus on 20/03/2014.
 */
public class HEXAConversionAPI {


    public enum ConversionType {
        A2COMPLEMENT, FLOATINGPOINT
    }

    public static String zero_pad_bin_char(String bin_char) {
        int len = bin_char.length();
        if (len == 8) return bin_char;
        String zero_pad = "0";
        for (int i = 1; i < 8 - len; i++) zero_pad = zero_pad + "0";
        return zero_pad + bin_char;
    }

    public static String binary_to_hex(String binary) {
        String hex = "";
        String hex_char;
        int len = binary.length() / 8;
        for (int i = 0; i < len; i++) {
            String bin_char = binary.substring(8 * i, 8 * i + 8);
            int conv_int = Integer.parseInt(bin_char, 2);
            hex_char = Integer.toHexString(conv_int);
            if (i == 0) hex = hex_char;
            else hex = hex + hex_char;
        }
        if (hex.length() == 1) hex = "0" + hex;
        return hex.toUpperCase();
    }

    public static BitVector hex_to_bitvector(String hex) {
        String ff = hex_to_binary(hex);
        BitVector bv = new BitVector(ff.length());
        for (int i = 0; i < ff.length(); i++) {
            Boolean val = false;
            if (ff.charAt(i) == '1') val = true;
            bv.setBit(i, val);
        }
        return bv;
    }

    public static String bitvector_to_hex(BitVector bv) {
        String binary = "";
        for (int i = 0; i < bv.size(); i++) {
            if (bv.getBit(i)) binary += "1";
            else binary += "0";
        }
        return binary_to_hex(binary);
    }

    public static String hex_to_binary(String hex) {
        String hex_char, bin_char, binary;
        binary = "";
        if (hex.length() % 2 == 1) hex = "0" + hex;
        int len = hex.length() / 2;
        for (int i = 0; i < len; i++) {
            hex_char = hex.substring(2 * i, 2 * i + 2);
            int conv_int = Integer.parseInt(hex_char, 16);
            bin_char = Integer.toBinaryString(conv_int);
            bin_char = zero_pad_bin_char(bin_char);
            if (i == 0) binary = bin_char;
            else binary = binary + bin_char;
            //out.printf("%s %s\n", hex_char,bin_char);
        }
        return binary;
    }

    public static String hex_to_decimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16 * val + d;
        }
        return Integer.toString(val);
    }


    public static String decimal_to_hex(String d) {
        return decimal_to_hex(new Integer(d).intValue());
    }

    // precondition:  d is a nonnegative integer
    public static String decimal_to_hex(int d) {
        String digits = "0123456789ABCDEF";
        if (d == 0) return "0";
        String hex = "";
        while (d > 0) {
            int digit = d % 16;                // rightmost digit
            hex = digits.charAt(digit) + hex;  // string concatenation
            d = d / 16;
        }
        return hex;
    }

    public static String hex_to_decimal(String hex, ConversionType type) {
        String auxHex = hex.replaceFirst("0x", "");
        if (ConversionType.A2COMPLEMENT.equals(type)) {
            return hex_to_a2_decimal(auxHex).toString();
        } else if (ConversionType.FLOATINGPOINT.equals(type)) {
            return FPConversionAPI.hexaToFloat(auxHex).toString();
        }

        return "";
    }


    public static Integer hex_to_a2_decimal(String str) {
        Integer num = Integer.valueOf(str, 16);
        double pow = Math.pow(2, 8);
        int i = new Double(pow).intValue();
        return (num >= (i / 2)) ? (num - i) : num;
    }



    public static String a2_decimal_to_binary(Integer i, Integer numberOfBits) {
        Integer newN = i;
        if (i < 0) newN = (int) Math.pow(2, numberOfBits) + i;
        String fullBinary = Integer.toBinaryString(newN);
        if (fullBinary.length() > numberOfBits) {
            return fullBinary.substring(fullBinary.length() - numberOfBits - 1);
        } else {
            String prepend = "";
            for (int j = 0; j < numberOfBits - fullBinary.length(); j++) {
                prepend = prepend + 0;
            }
            return prepend + fullBinary;
        }

    }

    public static String a2_decimal_to_hex(Integer i) {
        Integer newN = i;
        if (i < 0) newN = 256 + i;
        return decimal_to_hex(newN.intValue());
    }



}
