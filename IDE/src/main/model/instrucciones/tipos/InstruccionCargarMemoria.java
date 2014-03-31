package main.model.instrucciones.tipos;

import main.model.ComplexNumber;
import main.model.MaquinaGenerica;
import main.model.Simulador;

import javax.swing.*;

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

        if (decimalNumber == 253) {
            String error = maquina.escribirEnMemoria(new ComplexNumber(decimalNumber - 1), "01");
            if (error != null) return error;
            final String posibleVals = "0123456789ABCDEF";
            while (hexa.isEmpty() || hexa.length() != 2 || !posibleVals.contains("" + hexa.charAt(0)) || !posibleVals.contains("" + hexa.charAt(1))){
            JTextField hexaField = new JTextField();
            final JLabel validationLabel = new JLabel();
                hexaField.setInputVerifier(new InputVerifier() {
                    @Override
                    public boolean verify(JComponent input) {
                        String text = ((JTextField) input).getText();
                        if (text.isEmpty()) {
                            validationLabel.setText("No ingreso ningun valor");
                            return false;
                        }

                        if (text.length() != 2) {
                            validationLabel.setText("El valor debe tener 2 digitos");
                            return false;
                        }
                        if (!posibleVals.contains("" + text.charAt(0)) || !posibleVals.contains("" + text.charAt(1))) {
                            validationLabel.setText("Valores posibles: " + posibleVals);
                            return false;
                        }

                        validationLabel.setText("");
                        return true;
                    }
                });
            final JComponent[] inputs = new JComponent[]{
                    new JLabel("Entra: "),
                    hexaField, validationLabel
            };
            JOptionPane.showMessageDialog(null, inputs, "Ingresar valor hexadecimal de 2 digitos", JOptionPane.PLAIN_MESSAGE);
            hexa = hexaField.getText();
            }

            error = maquina.escribirEnMemoria(new ComplexNumber(decimalNumber - 1), "00");
            if (error != null) return error;
        } else {
            hexa = maquina.leerMemoria(numeroCeldaMemoria);
        }


        maquina.escribirEnRegistro(numeroRegistro, hexa);
        return null;
    }
}
