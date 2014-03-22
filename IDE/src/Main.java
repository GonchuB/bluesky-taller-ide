import main.apis.HEXAConversionAPI;
import main.apis.bitvector.BitVector;

/**
 * Created by Juan-Asus on 20/03/2014.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(HEXAConversionAPI.hex_to_decimal("3E8", HEXAConversionAPI.ConversionType.A2COMPLEMENT));
        System.out.println(HEXAConversionAPI.hex_to_decimal("3E8", HEXAConversionAPI.ConversionType.FLOATINGPOINT));// Display the string.

        BitVector bv = new BitVector(8);

        BitVector ff = HEXAConversionAPI.hex_to_bitvector("FF");

        System.out.println(ff.toString());


    }
}
