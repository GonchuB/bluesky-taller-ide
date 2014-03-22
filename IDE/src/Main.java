import main.apis.HEXAConversionAPI;
import main.apis.bitvector.BitVector;
import main.model.Simulador;

/**
 * Created by Juan-Asus on 20/03/2014.
 */
public class Main {
    public static void main(String[] args) {
        Simulador simulador = new Simulador();
        simulador.init("D:\\Workspace\\bluesky-taller-ide\\IDE\\src\\resources\\programas\\ejemplo.maq");
        simulador.iniciarSimulacionCompleta();
        simulador.mostrarEstadoSimulacion();

        System.exit(0);
    }
}
