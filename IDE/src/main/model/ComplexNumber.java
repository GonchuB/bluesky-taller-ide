package main.model;

import main.apis.HEXAConversionAPI;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class ComplexNumber implements Comparable{
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


    @Override
    public boolean equals(Object obj) {
        return numero == ((ComplexNumber)obj).numero;
    }

    @Override
    public int hashCode() {
        return numero;
    }

    @Override
    public int compareTo(Object o) {
        if (numero == ((ComplexNumber)o).numero) return 0;
        if (numero > ((ComplexNumber)o).numero) return 1;
        return -1;

    }
}
