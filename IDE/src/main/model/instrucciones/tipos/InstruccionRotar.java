package main.model.instrucciones.tipos;

import main.apis.HEXAConversionAPI;
import main.apis.bitvector.BitVector;
import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class InstruccionRotar extends Instruccion {

    private int cantidadDeRotaciones;
    private ComplexNumber numeroDeRegistro;

    public InstruccionRotar(int cantidadDeRotaciones, ComplexNumber numeroDeRegistro) {
        this.cantidadDeRotaciones = cantidadDeRotaciones;
        this.numeroDeRegistro = numeroDeRegistro;
    }

    @Override
    public void operacion(Simulador simulador, MaquinaGenerica maquina) {
        String hexa = maquina.leerRegistro(numeroDeRegistro);
        BitVector bv = HEXAConversionAPI.hex_to_bitvector(hexa);
        bv.rotateRight(cantidadDeRotaciones);
        String hexNew = HEXAConversionAPI.bitvector_to_hex(bv);
        maquina.escribirEnRegistro(numeroDeRegistro,hexNew);
    }
}
