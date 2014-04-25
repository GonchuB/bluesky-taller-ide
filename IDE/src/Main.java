import main.model.Compilador;
import main.model.Simulador;
import main.ui.Editor;

import javax.swing.*;

/**
 * Created by Juan-Asus on 20/03/2014.
 */
public class Main {
    public static void main(String[] args) {

        //runWhitoutUI();

        runUI();
    }

    public static void runUI() {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Editor editor = new Editor();
                JFrame jFrame = editor.getJFrame();
                jFrame.setExtendedState(jFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
                jFrame.setVisible(true);    //hace visible la GUI creada por la clase TPEditor
            }
        });
    }

    public static void runWhitoutUI() {

        /**
         * CAMBIAR ARCHIVO ESPECIFICO DE CADA MAQUINA.
         */
        String rutaArchivoASM = "/home/gonchub/Documents/FIUBA/bluesky-taller-ide/IDE/src/resources/programas/test.asm";
        rutaArchivoASM = "D:\\Workspace\\bluesky-taller-ide\\IDE\\src\\resources\\programas\\ejemplo.asm";


        Compilador compilador = new Compilador();
        String rutaArchivoMAQ = rutaArchivoASM.replace(".asm", ".maq");
        String status = compilador.compilar(rutaArchivoASM);

        if (status != null) {
            System.out.println(status);
        } else {
            System.out.println("Compilación exitosa - Se creó el archivo " + rutaArchivoMAQ);
        }

        Simulador simulador = new Simulador();
        simulador.init(rutaArchivoMAQ);
        simulador.iniciarSimulacionCompleta();
        simulador.mostrarEstadoSimulacion();
    }

}
