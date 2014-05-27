package main.ui;

import main.model.ComplexNumber;
import main.model.EstadoMaquina;
import main.model.MemoriaPrincipal;

import javax.swing.*;
import java.util.Vector;

/**
 * Created by ezequiel on 27/05/14.
 */
public class VistaMemoria {


    JTable tablaRam;
    JScrollPane ramArea ;
    Vector<String> registros;
    public VistaMemoria(EstadoMaquina estadoMaquina) {
        tablaRam = new JTable();
        ramArea = new JScrollPane(tablaRam);
        Vector<Vector> data = new Vector<Vector>();
        Vector<Vector> dataFlags  = new Vector<Vector>();
//    	registros.clear();

        Vector<String> columnNames = new Vector<String>();
        columnNames.add("Celda");
        columnNames.add("Valor");

        for (int i = 0 ; i < 256 ; i++)
        {
            registros = new Vector<String>();

            registros.add(String.valueOf(i));
            registros.add("0");
            data.add(registros);

        }
        tablaRam = new JTable(data,columnNames);
        tablaRam.setAutoscrolls(true);
        tablaRam.setEnabled(false);
        ramArea = new JScrollPane(tablaRam);

        buildMemoryRam(estadoMaquina);
    }

    public void buildMemoryRam(EstadoMaquina estadoMaquina)
    {
        MemoriaPrincipal m = estadoMaquina.getMemoriaPrincipal();




        for (int i = 0 ; i < 256 ; i++)
        {
            tablaRam.setValueAt(m.leerCelda(new ComplexNumber(i)),i,1);
        }



    }

    public void showMemoryRam(String mje)
    {
        final JComponent[] inputs = new JComponent[]{

                ramArea,
                new JSeparator(),
                new JLabel(mje)
        };
        JOptionPane.showMessageDialog(null, inputs, "Bit de control !", JOptionPane.PLAIN_MESSAGE);
    }

}
