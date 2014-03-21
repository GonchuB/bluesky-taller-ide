package model;

import apis.HEXAConversionAPI;
import apis.bitvector.BitVector;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class Registro {
    ComplexNumber numero;
    BitVector bitVector;

    //Precondicion, Hexa de tamaño 2, o BitVector de 8 bits
    public Registro(ComplexNumber numero, String hexa) {
        this.numero = numero;
        bitVector = HEXAConversionAPI.hex_to_bitvector(hexa);

    }

    public Registro(ComplexNumber numero, BitVector b) {
        this.numero = numero;
        bitVector = b;
    }
}
