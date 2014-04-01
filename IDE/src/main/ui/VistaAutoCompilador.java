package main.ui;
import javax.swing.*;

import java.awt.*;

public class VistaAutoCompilador {
	
	private JEditorPane panel;
	private JFrame frame;
	
	public VistaAutoCompilador(int posXVista, int posYVista){
		panel = new JEditorPane();
		frame = new JFrame();
		frame.setBounds(posXVista, posYVista, 600, 100);
		panel.setContentType("text/html");
		panel.setEditable(false);
		frame.add(panel);
		frame.setVisible(false);
		
	}
	
	public void mostrarResultadoCompilacionLinea(String lineaEditada, String error){
		String texto="<font color=red>Compilador en linea: </font><br>" + lineaEditada + " : " + error;
		panel.setText(texto);
		frame.setVisible(true);
	}
	
	

}
