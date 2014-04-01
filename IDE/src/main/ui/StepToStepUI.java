package main.ui;
import java.awt.*;

import main.model.*;
import main.ui.Editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;
import java.util.Vector;


import main.ui.Editor.EventHandler;
public class StepToStepUI extends Editor {
    Vector<String> nRegistros;
    Vector<String> registros;
    private JTable  tablaRegistros;
    private JTable tablaRam;
    private JTable tablaFlags;
    private JScrollPane regArea;
    private JScrollPane flagArea;
    private JScrollPane ramArea;
    private String valoresRegistros[];
    private boolean C,Z,O,F;

    private JFrame jFrame;            //instancia de JFrame (ventana principal)
   // private JMenuBar jMenuBar;        //instancia de JMenuBar (barra de menú)
    private JToolBar jToolBar;        //instancia de JToolBar (barra de herramientas)
    private JTextArea jTextArea;      //instancia de JTextArea (área de edición)
    public void setjTextArea(JTextArea jTextArea) {
	//	this.jTextArea.setText(jTextArea.getText());
	}

	//private JPopupMenu jPopupMenu;    //instancia de JPopupMenu (menú emergente)
    private JPanel statusBar;         //instancia de JPanel (barra de estado)
    private JPanel compilationResultsBar; //instancia de JPanel (resultado de compilacion)

 

     public StepToStepUI(ActionPerformer actionPerformerInstance) {
         super("Sol temporal");//TODO resolver esto de una forma mejor
        try {    //LookAndFeel nativo
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println(ex);
        }
 
        //construye un JFrame con título
        jFrame = new JFrame("Ejecución Paso a Paso");
        jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
         this.jTextArea = new JTextArea();
        //asigna un manejador de eventos para el cierre del JFrame
        jFrame.addWindowListener(new WindowAdapter() {
 
            @Override
            public void windowClosing(WindowEvent we) {
            	goClose();  //invoca el método actionExit()
            }
        });
 
        eventHandler = new EventHandler();              //construye una instancia de EventHandler
        actionPerformer = actionPerformerInstance;    //construye una instancia de ActionPerformer
        undoManager = new UndoManager();                //construye una instancia de UndoManager
        undoManager.setLimit(50);                       //le asigna un límite al buffer de ediciones
        inicializarValoresStep();
        buildTextArea();     //construye el área de edición, es importante que esta sea la primera parte en construirse
       // buildMenuBar();      //construye la barra de menú
        buildToolBar();      //construye la barra de herramientas
        buildCompilationBar();
        buildStatusBar();	//construye la barra de estado
        buildRegArea();
         buildRamArea();
        //buildPopupMenu();    //construye el menú emergente
       // jFrame.setJMenuBar(jMenuBar);                              //designa la barra de menú del JFrame
        Container c = jFrame.getContentPane();                     //obtiene el contendor principal
        BorderLayout experimentLayout = new BorderLayout();
        jFrame.setLayout(experimentLayout);

        jFrame.add(jToolBar, BorderLayout.PAGE_START);                       //añade la barra de herramientas, orientación NORTE del contendor
        JScrollPane tSP = new JScrollPane(jTextArea);
        tSP.setSize(200,50);
        jFrame.add(tSP, BorderLayout.CENTER);    //añade el area de edición en el CENTRO
        jFrame.add(statusBar, BorderLayout.PAGE_END);                      //añade la barra de estado, orientación SUR
        jFrame.add(regArea, BorderLayout.LINE_START);
        jFrame.add(flagArea, BorderLayout.LINE_END);

        jFrame.pack();
//        c.add(compilationResultsBar, BorderLayout.PAGE_START);
//        c.add(compilationResultsBar);
        //configura el JFrame con un tamaño inicial proporcionado con respecto a la pantalla
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setSize(pantalla.width / 2, pantalla.height / 2);
 
        //centra el JFrame en pantalla
        jFrame.setLocationRelativeTo(null);
    }
 
    
    private void goClose()
    {
    	actionPerformer.actionExitToPrincipal();
    }
    /**
     * Construye el área de edición.
     */
    private void buildTextArea() {
        jTextArea = new JTextArea();    //construye un JTextArea
        jTextArea.setEditable(false);
        //se configura por defecto para que se ajusten las líneas al tamaño del área de texto ...
        jTextArea.setLineWrap(true);
        //... y que se respete la integridad de las palaras en el ajuste
        jTextArea.setWrapStyleWord(true);
 
        //asigna el manejador de eventos para el cursor
        jTextArea.addCaretListener(eventHandler);
        //asigna el manejador de eventos para el ratón
        jTextArea.addMouseListener(eventHandler);
        //asigna el manejador de eventos para registrar los cambios sobre el documento
        jTextArea.getDocument().addUndoableEditListener(eventHandler);
 
        //remueve las posibles combinaciones de teclas asociadas por defecto con el JTextArea
        jTextArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK), "none");    //remueve CTRL + X ("Cortar")
        jTextArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK), "none");    //remueve CTRL + C ("Copiar")
        jTextArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK), "none");    //remueve CTRL + V ("Pegar")
    }
 
    
 
    /**
     * Construye la barra de herramientas.
     */
    private void buildToolBar() {
        jToolBar = new JToolBar();       //construye un JToolBar
        jToolBar.setFloatable(false);    //se configura por defecto como barra fija

        JButton buttonExecuteStep = new JButton();
        buttonExecuteStep.setToolTipText("Ejecutar siguiente instrucción");
        buttonExecuteStep.setIcon(new ImageIcon(getClass().getClassLoader().getResource("play_pause.png").getPath()));
        buttonExecuteStep.setActionCommand("cmd_nextStep");

        JButton buttonGetMemory = new JButton();
        buttonGetMemory.setToolTipText("Ver estado de memoria");
        buttonGetMemory.setIcon(new ImageIcon(getClass().getClassLoader().getResource("tp_ram.png").getPath()));
        buttonGetMemory.setActionCommand("cmd_show_ram");


        jToolBar.addSeparator();    //añade separadores entre algunos botones
        jToolBar.add(buttonExecuteStep);
        jToolBar.add(buttonGetMemory);
        /** itera sobre todos los componentes de la barra de herramientas, se les asigna el
        mismo margen y el mismo manejador de eventos unicamente a los botones */
        for (Component c : jToolBar.getComponents()) {
            //si el componente es un botón
            if (c.getClass().equals(javax.swing.JButton.class)) {
                JButton jb = (JButton) c;
                jb.setMargin(new Insets(0, 0, 0, 0));
                jb.addActionListener(eventHandler);
            }
        }
    }
 
    
    public void buildCompilationBar()
    {
    	compilationResultsBar = new JPanel();    //construye un JPanel
         //se configura con un BoxLayout
         compilationResultsBar.setLayout(new BoxLayout(compilationResultsBar, BoxLayout.LINE_AXIS));
         //le añade un borde compuesto
         compilationResultsBar.setBorder(BorderFactory.createCompoundBorder(
                 BorderFactory.createLoweredBevelBorder(),
                 BorderFactory.createEmptyBorder(5, 5, 5, 5)));
         compilationResultsBar.setBounds(15, 15, 3, 3);
//         compilationResultsBar.setLocation(20, 20);
         //construye la etiqueta para mostrar la ubicación del archivo actual
         sbFilePath = new JLabel("path");
         //construye la etiqueta para mostrar el tamaño del archivo actual
         sbFileSize = new JLabel("Resultado compilacion");
         //construye la etiqueta para mostrar la posición del cursor en el documento actual
         sbCaretPos = new JLabel("posicion");
  
         /** se añaden las etiquetas construidas al JPanel, el resultado es un panel
         similar a una barra de estado */
         compilationResultsBar.add(sbFilePath);
         compilationResultsBar.add(Box.createRigidArea(new Dimension(10, 0)));
         compilationResultsBar.add(sbFileSize);
         compilationResultsBar.add(Box.createRigidArea(new Dimension(10, 0)));
         compilationResultsBar.add(Box.createHorizontalGlue());
         compilationResultsBar.add(sbCaretPos);
    }
    /**
     * Construye la barra de estado.
     */
    private void buildStatusBar() {
        statusBar = new JPanel();    //construye un JPanel
        //se configura con un BoxLayout
        statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.LINE_AXIS));
        //le añade un borde compuesto
        statusBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
 
        //construye la etiqueta para mostrar la ubicación del archivo actual
        sbFilePath = new JLabel("...");
        //construye la etiqueta para mostrar el tamaño del archivo actual
        sbFileSize = new JLabel("PASO A PASO");
        //construye la etiqueta para mostrar la posición del cursor en el documento actual
        sbCaretPos = new JLabel("...");
 
        /** se añaden las etiquetas construidas al JPanel, el resultado es un panel
        similar a una barra de estado */
        statusBar.add(sbFilePath);
        statusBar.add(Box.createRigidArea(new Dimension(10, 0)));
        statusBar.add(sbFileSize);
        statusBar.add(Box.createRigidArea(new Dimension(10, 0)));
        statusBar.add(Box.createHorizontalGlue());
        statusBar.add(sbCaretPos);
    }
 
    
    /**
     * Actualiza el estado de las opciones "Deshacer" y "Rehacer".
     */
    void updateControls() {
        //averigua si se pueden deshacer los cambios en el documento actual
        boolean canUndo = undoManager.canUndo();
        //averigua si se pueden rehacer los cambios en el documento actual
        boolean canRedo = undoManager.canRedo();
 
        //activa o desactiva las opciones en la barra de menú
        //mbItemUndo.setEnabled(canUndo);
        //mbItemRedo.setEnabled(canRedo);
 
        //activa o desactiva las opciones en la barra de herramientas
//        buttonUndo.setEnabled(canUndo);
//        buttonRedo.setEnabled(canRedo);
 
        //activa o desactiva las opciones en el menú emergente
       // mpItemUndo.setEnabled(canUndo);
        //mpItemRedo.setEnabled(canRedo);
    }
 

    UndoManager getUndoManager() {
        return undoManager;
    }
 
    /**
     * Retorna el estado del documento actual.
     *
     * @return true si ah sido modificado, false en caso contrario
     */
    boolean documentHasChanged() {
        return hasChanged;
    }
 
    /**
     * Establece el estado del documento actual.
     *
     * @param hasChanged true si ah sido modificado, false en caso contrario
     */
    void setDocumentChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }
 
    /**
     * Retorna la instancia de JTextArea, el área de edición.
     *
     * @return retorna el área de edición.
     */
    JTextArea getJTextArea() {
        return jTextArea;
    }
 
    /**
     * Retorna la instancia de JFrame, la ventana principal del editor.
     *
     * @return la ventana principal del editor.
     */
   public JFrame getJFrame() {
        return jFrame;
    }
 
    /**
     * Retorna la instancia de File, el archivo actual.
     *
     * @return el archivo actual
     */
    File getCurrentFile() {
        return currentFile;
    }
 
    /**
     * Establece el archivo actual.
     *
     * @param currentFile el archivo actual
     */
    void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }
 
    /**
     * Retorna la instancia de la etiqueta sbFilePath, donde se muestra la ubicación
     * del archivo actual.
     *
     * @return la instancia de la etiqueta sbFilePath
     */
    JLabel getJLabelFilePath() {
        return sbFilePath;
    }
 
    /**
     * Retorna la instancia de la etiqueta sbFileSize, donde se muestra el tamaño
     * del archivo actual
     *
     * @return la instancia de la etiqueta sbFileSize
     */
    JLabel getJLabelFileSize() {
        return sbFileSize;
    }


    private void buildRegArea()
    {

        Vector<Vector> data = new Vector<Vector>();
        Vector<Vector> dataFlags  = new Vector<Vector>();
//    	registros.clear();

        Vector<String> columnNames = new Vector<String>();
        columnNames.add("NumRegs");
        columnNames.add("Regs");
        for (int i = 0 ; i < 16 ; i++)
        {
            registros = new Vector<String>();

            registros.add("R"+String.valueOf(i));
            registros.add(this.valoresRegistros[i]);	//Es el controlador quien afecta la vista, no al reves. Ver metodo public void setEstadoActual(EstadoMaquina estadoMaquina) de StepToStepUI
            data.add(registros);

        }
        tablaRegistros = new JTable(data,columnNames);
        tablaRegistros.setAutoscrolls(true);

        Vector<String> columnFlags = new Vector();
        columnFlags.add("N");
        columnFlags.add("Z");
        columnFlags.add("O");
        columnFlags.add("F");
        Vector<String> flags = new Vector<String>();
        //TODO NOTA: Es el controlador quien afecta la vista, no al reves. Ver metodo public void setEstadoActual(EstadoMaquina estadoMaquina) de StepTOStep
        flags.add(String.valueOf(this.C));
        flags.add(String.valueOf(this.Z));
        flags.add(String.valueOf(this.O));
        flags.add(String.valueOf(this.F));
        dataFlags.add(flags);
        tablaFlags = new JTable(dataFlags, columnFlags);
        tablaFlags.setVisible(true);
        tablaFlags.setAutoscrolls(true);
        tablaFlags.setLocation(15, 15);
        regArea = new JScrollPane(tablaRegistros);

        flagArea = new JScrollPane(tablaFlags);

        flagArea.setPreferredSize(new Dimension(100,100));
        regArea.setPreferredSize(new Dimension(100,100));
    }

    public void setEstadoActual(EstadoMaquina estadoMaquina) {
        //TODO - Setear a partir de estado maquina l oque se quiere mostrar
       BancoRegistros b =  estadoMaquina.getBancoRegistros();
        MemoriaPrincipal m = estadoMaquina.getMemoriaPrincipal();
        ALUControl a = estadoMaquina.getAluControlBits();
        for (int i = 0 ; i < 16 ; i++)
        {

          //  this.valoresRegistros[i] = b.leerRegistro(j);
            tablaRegistros.setValueAt(b.leerRegistro(new ComplexNumber(i)),i,1);

        }

        tablaFlags.setValueAt(a.isNegative(),0,1);
            tablaFlags.setValueAt(a.isZero(),0,1);
        tablaFlags.setValueAt(a.isOverflow(),0,2);
        tablaFlags.setValueAt(a.isPrecisionLost(),0,3);

        for (int i = 0 ; i < 256 ; i++)
        {
         tablaRam.setValueAt(m.leerCelda(new ComplexNumber(i)),i,1);
        }

    }

    private void inicializarValoresStep()
    {
        this.valoresRegistros = new String[16];
        for (int i = 0 ; i < 16 ; i++) this.valoresRegistros[i] = String.valueOf("0");
        this.C = this.F = this.Z = this.O = false;
    }

    private void buildRamArea()
    {
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
        ramArea = new JScrollPane(tablaRam);
    }

    public void showMemoryRam()
    {
        final JComponent[] inputs = new JComponent[]{
            ramArea
        };
        JOptionPane.showMessageDialog(null, inputs, "Memoria Ram", JOptionPane.PLAIN_MESSAGE);
    }
}
