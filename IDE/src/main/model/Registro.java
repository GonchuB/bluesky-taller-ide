package main.model;

import main.apis.HEXAConversionAPI;
import main.apis.bitvector.BitVector;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class Registro {
    private ComplexNumber numero;
    private BitVector bitVector;

    public Registro(ComplexNumber numero) {
        this.numero = numero;
        bitVector = new BitVector(8);

    }

    public void setValor(int pos,boolean valor){
        bitVector.setBit(pos,valor);
    }

    public void setValor(String hexa){
        setValor(HEXAConversionAPI.hex_to_bitvector(hexa));
    }

    public void setValor(BitVector bits){
        for (int i= 0; i< bits.size(); i++) {
            bitVector.setBit(i,bits.getBit(i));
        }


    }


    public boolean getBit(int pos){
        return bitVector.getBit(pos);
    }

    public String getValorHexa(){
        return HEXAConversionAPI.bitvector_to_hex(bitVector);
    }

    public BitVector getValorBV(){
        return bitVector;
    }


}
