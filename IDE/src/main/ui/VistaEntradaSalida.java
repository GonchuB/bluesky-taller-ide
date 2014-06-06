package main.ui;

import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class VistaEntradaSalida extends Editor {

    private JButton botonConversionHexa;


    public VistaEntradaSalida() {
        this.actionPerformer = new ActionPerformer(this);
        this.eventHandler = new EventHandler();
        this.botonConversionHexa = new JButton();
        botonConversionHexa.setToolTipText("Conversion Hexadecimal");
        botonConversionHexa.setIcon(new ImageIcon(getClass().getClassLoader().getResource("bit_refresh.png").getPath()));
        botonConversionHexa.setActionCommand("cmd_hexa");
        botonConversionHexa.addActionListener(eventHandler);
    }

    public void mostrarSalidaHexa(String hexa) {

        JOptionPane.showOptionDialog(null, "Sale: " + hexa, "Instruccion Almacenar", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"OK", botonConversionHexa}, "OK");
    }

    public String mostrarEntradaHexa() {
        String hexa = "";
        final String posibleVals = "0123456789ABCDEF";
        while (hexa.isEmpty() || hexa.length() != 2 || !posibleVals.contains("" + hexa.charAt(0)) || !posibleVals.contains("" + hexa.charAt(1))) {
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
                    hexaField, validationLabel, botonConversionHexa
            };
            JOptionPane.showMessageDialog(null, inputs, "Ingresar valor hexadecimal de 2 digitos", JOptionPane.PLAIN_MESSAGE);
            hexa = hexaField.getText();
        }
        return hexa;
    }

}
