package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;

import java.util.Scanner;

/**
 * Created by Juan-Asus on 21/03/2014.
 */
public class InstruccionCargarMemoria extends Instruccion {
    private ComplexNumber numeroRegistro;
    private ComplexNumber numeroCeldaMemoria;

    public InstruccionCargarMemoria(ComplexNumber numeroRegistro, ComplexNumber numeroCeldaMemoria) {
        this.numeroRegistro = numeroRegistro;
        this.numeroCeldaMemoria = numeroCeldaMemoria;
    }

    @Override
    public String operacion(Simulador simulador, MaquinaGenerica maquina) {
        int decimalNumber = numeroCeldaMemoria.getDecimalNumber();
        String hexa = "";

        if (decimalNumber == 253){
            String error = maquina.escribirEnMemoria(new ComplexNumber(decimalNumber - 1), "01");
            if(error != null) return error;
            //TODO - Cambiar esto por un popup que lea 2 digitos
            Scanner scanner = new Scanner(System.in);
            while (hexa.isEmpty() || hexa.length() != 2){
                System.out.print("Entra: ");
                hexa = scanner.next();
                if (hexa.isEmpty()) System.out.println("No ingreso ningun valor hexadecimal de 2 digitos");
                if (hexa.length() != 2) System.out.println("El valor hexadecimal ingresado debe tener 2 digitos");
            }
            error = maquina.escribirEnMemoria(new ComplexNumber(decimalNumber - 1), "00");
            if(error != null) return error;
        } else {
            hexa = maquina.leerMemoria(numeroCeldaMemoria);
        }


        maquina.escribirEnRegistro(numeroRegistro,hexa);
        return null;
    }
}
