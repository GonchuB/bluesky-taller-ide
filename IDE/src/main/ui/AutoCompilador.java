package main.ui;

import main.model.Compilador;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import javax.swing.text.BadLocationException;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class AutoCompilador {
	
	private String lineaEditada;
	private JTextArea areaTexto;
	private Compilador compilador;
	private String nombreArchivoAEditar;
	private int numeroLineaEditada;
	private VistaAutoCompilador vistaAutoCompilador;
	
	public AutoCompilador(JTextArea areaText, Compilador elCompilador){
		areaTexto = areaText;
		compilador = elCompilador;
		lineaEditada = "";
		nombreArchivoAEditar = "";
		vistaAutoCompilador = null;
		areaTexto.addKeyListener(new KeyAdapter(){
			public void keyTyped(KeyEvent e){
			
				int numeroLineaDespuesDelEnter;
				numeroLineaEditada = -1;
				int startOffset;
				int endOffset;
				char caracter = e.getKeyChar();
				if(caracter == KeyEvent.VK_ENTER){
					try{
						numeroLineaDespuesDelEnter = areaTexto.getLineOfOffset(areaTexto.getCaretPosition());
						numeroLineaEditada = numeroLineaDespuesDelEnter - 1;
						//System.out.print(numeroLineaEditada);
						startOffset = areaTexto.getLineStartOffset(numeroLineaEditada);
						//System.out.print(startOffset);
						endOffset = areaTexto.getLineEndOffset(numeroLineaEditada);
						//System.out.print(endOffset);
						//lineaEditadaPrincipio = areaTexto.getText(startOffset,endOffset);
						lineaEditada = areaTexto.getText(startOffset,endOffset - startOffset);
					} catch (BadLocationException ex) {
						ex.printStackTrace();
					}
					PointerInfo a = MouseInfo.getPointerInfo();
					Point b = a.getLocation();
					int posXCursor = (int) b.getX();
					int posYCursor = (int) b.getY();
					if(numeroLineaEditada >= 0){
                        String error = compilarLinea();
                        if (error != null){
                        	if(vistaAutoCompilador == null)
                        		vistaAutoCompilador = new VistaAutoCompilador(posYCursor,posYCursor);
                        	vistaAutoCompilador.mostrarResultadoCompilacionLinea(lineaEditada,error);
                            //System.out.println(error);
                        }

                    }
				}
			}
			});
	}
	
	private String compilarLinea() {
		if (nombreArchivoAEditar.length() > 0){
			boolean isAsmFile = nombreArchivoAEditar.contains(".asm") || nombreArchivoAEditar.contains(".ASM");
        	String error = null;
        	try{
        		if (isAsmFile)
        			error = compilador.chequearSyntaxisDeLineaASM(lineaEditada,numeroLineaEditada);
        		else
        			error = compilador.chequearSyntaxisDeLineaMAQ(lineaEditada,numeroLineaEditada);
             

        		return error;
       
        	} catch (Exception e) {
        		return "ERROR - error en la compilación del archivo  " + nombreArchivoAEditar;
        	}
		}

        return "ERROR - El auto compilador no tiene un archivo asociado";
    }
	
	public void setNombreArchivo (String nombre){
		nombreArchivoAEditar = nombre;
	}
}
