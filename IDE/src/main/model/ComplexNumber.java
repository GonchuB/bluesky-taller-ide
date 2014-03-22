package main.model;

import main.apis.HEXAConversionAPI;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class ComplexNumber {
    private int numero;

    public ComplexNumber(int numero) {
        this.numero = numero;
    }

    public ComplexNumber(String hexa) {
        String entero = HEXAConversionAPI.hex_to_decimal(hexa);
        this.numero = new Integer(entero).intValue();
    }

    public String getHexaNumber(){
        return HEXAConversionAPI.decimal_to_hex(numero);
    }

    public int getDecimalNumber(){
        return numero;
    }



    public boolean equals(ComplexNumber obj) {
        return numero == obj.numero;
    }
}
