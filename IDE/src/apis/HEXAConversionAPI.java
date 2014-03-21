package apis;

import apis.bitvector.BitVector;

/**
 * Created by Juan-Asus on 20/03/2014.
 */
public class HEXAConversionAPI {

    public enum ConversionType {
        A2COMPLEMENT, FLOATINGPOINT
    }

    public static String zero_pad_bin_char(String bin_char){
        int len = bin_char.length();
        if(len == 8) return bin_char;
        String zero_pad = "0";
        for(int i=1;i<8-len;i++) zero_pad = zero_pad + "0";
        return zero_pad + bin_char;
    }
    public static String plaintext_to_binary(String pt){
        return hex_to_binary(plaintext_to_hex(pt));
    }
    public static String binary_to_plaintext(String bin){
        return hex_to_plaintext(binary_to_hex(bin));
    }
    public static String plaintext_to_hex(String pt) {
        String hex = "";
        for(int i=0;i<pt.length();i++){
            String hex_char = Integer.toHexString(pt.charAt(i));
            if(i==0) hex = hex_char;
            else hex = hex + hex_char;
        }
        return hex;
    }
    public static String binary_to_hex(String binary) {
        String hex = "";
        String hex_char;
        int len = binary.length()/8;
        for(int i=0;i<len;i++){
            String bin_char = binary.substring(8*i,8*i+8);
            int conv_int = Integer.parseInt(bin_char,2);
            hex_char = Integer.toHexString(conv_int);
            if(i==0) hex = hex_char;
            else hex = hex+hex_char;
        }
        return hex;
    }

    public static BitVector hex_to_bitvector(String hex){
        String ff = hex_to_binary(hex);
        BitVector bv = new BitVector(ff.length());
        for(int i=0;i<ff.length();i++){
            Boolean val = false;
            if (ff.charAt(0) == '1') val = true;
            bv.setBit(i,val);
        }
        return bv;
    }

    public static String hex_to_binary(String hex) {
        String hex_char,bin_char,binary;
        binary = "";
        int len = hex.length()/2;
        for(int i=0;i<len;i++){
            hex_char = hex.substring(2*i,2*i+2);
            int conv_int = Integer.parseInt(hex_char,16);
            bin_char = Integer.toBinaryString(conv_int);
            bin_char = zero_pad_bin_char(bin_char);
            if(i==0) binary = bin_char;
            else binary = binary+bin_char;
            //out.printf("%s %s\n", hex_char,bin_char);
        }
        return binary;
    }
    public static String hex_to_plaintext(String hex) {
        String hex_char;
        StringBuilder plaintext = new StringBuilder();
        char pt_char;
        int len = hex.length()/2;
        for(int i=0;i<len;i++){
            hex_char = hex.substring(2*i,2*i+2);
            pt_char = (char)Integer.parseInt(hex_char,16);
            plaintext.append(pt_char);
            //out.printf("%s %s\n", hex_char,bin_char);
        }
        return plaintext.toString();
    }

    public static String hex_to_decimal(String s) {
        String digits = "0123456789ABCDEF";
        s = s.toUpperCase();
        int val = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int d = digits.indexOf(c);
            val = 16*val + d;
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

    public static String hex_to_decimal(String hex,ConversionType type){
        String auxHex = hex.replaceFirst("0x","");
        if (ConversionType.A2COMPLEMENT.equals(type)){
            return twosComp(auxHex).toString();
        } else if (ConversionType.FLOATINGPOINT.equals(type)){
            return floatingPoint(auxHex).toString();
        }

        return "";
    }

    public static Integer twosComp(String str)  {
        Integer num = Integer.valueOf(str, 16);
        return (num > 32767) ? num - 65536 : num;
    }

    public static Float floatingPoint(String str){
        float f = Float.intBitsToFloat(new Integer(hex_to_decimal(str)).intValue());
        return new Float(f);
    }

}
