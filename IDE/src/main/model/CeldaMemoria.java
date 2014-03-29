package main.model;

import main.apis.HEXAConversionAPI;
import main.apis.bitvector.BitVector;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class CeldaMemoria implements Comparable{

    private Boolean modificada;
    private ComplexNumber numero;
    private BitVector bitVector;

    //Precondicion, Hexa de tamaño 2, o BitVector de 8 bits
    public CeldaMemoria(ComplexNumber numero) {
        this.numero = numero;
        this.modificada = false;
        bitVector = new BitVector(8);
        for (int i = 0; i < 8; i++) {
            bitVector.setBit(i, false);
        }
    }

    public void setValor(int pos, boolean valor) {
        bitVector.setBit(pos, valor);
        this.setModificada(true);
    }

    public boolean getBit(int pos) {
        return bitVector.getBit(pos);
    }

    public String getValorHexa() {
        return HEXAConversionAPI.bitvector_to_hex(bitVector);
    }

    public BitVector getValorBV() {
        return bitVector;
    }

    public Boolean getModificada() {
        return modificada;
    }

    public void setModificada(Boolean modificada) {
        this.modificada = modificada;
    }

    @Override
    public boolean equals(Object obj) {
        return numero == ((CeldaMemoria)obj).numero;
    }

    @Override
    public int hashCode() {
        return numero.hashCode();
    }

    @Override
    public int compareTo(Object o) {
        CeldaMemoria c = (CeldaMemoria) o;
        return numero.compareTo(c.numero);

    }
}
