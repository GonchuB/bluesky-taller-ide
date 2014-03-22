package main.model;

import main.apis.HEXAConversionAPI;
import main.apis.bitvector.BitVector;

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
