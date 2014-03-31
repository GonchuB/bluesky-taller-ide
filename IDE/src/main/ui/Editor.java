
package main.ui;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager;
import main.ui.StepToStepUI;
/**
 * Clase principal donde se construye la GUI del editor.
 *
 * @author Dark[byte]
 */
public class Editor {
	private StepToStepUI sts;
    private JFrame jFrame;            //instancia de JFrame (ventana principal)
   // private JMenuBar jMenuBar;        //instancia de JMenuBar (barra de menú)
    private JToolBar jToolBar;        //instancia de JToolBar (barra de herramientas)
    private JTextArea jTextArea;      //instancia de JTextArea (área de edición)
    //private JPopupMenu jPopupMenu;    //instancia de JPopupMenu (menú emergente)
    private JPanel statusBar;         //instancia de JPanel (barra de estado)
    private JPanel compilationResultsBar; //instancia de JPanel (resultado de compilacion)
   /* private JCheckBoxMenuItem itemLineWrap;         //instancias de algunos items de menú que necesitan ser accesibles
    private JCheckBoxMenuItem itemShowToolBar;
    private JCheckBoxMenuItem itemFixedToolBar;
    private JCheckBoxMenuItem itemShowStatusBar;
    private JMenuItem mbItemUndo;
    private JMenuItem mbItemRedo;
    private JMenuItem mpItemUndo;
    private JMenuItem mpItemRedo;*/
 
    private JButton buttonUndo;    //instancias de algunos botones que necesitan ser accesibles
    private JButton buttonRedo;
 
    protected JLabel sbFilePath;    //etiqueta que muestra la ubicación del archivo actual
    protected JLabel sbFileSize;    //etiqueta que muestra el tamaño del archivo actual
    protected JLabel sbCaretPos;    //etiqueta que muestra la posición del cursor en el área de edición
 
    protected boolean hasChanged = false;    //el estado del documento actual, no modificado por defecto
    protected File currentFile = null;       //el archivo actual, ninguno por defecto
 
    protected EventHandler eventHandler;          //instancia de EventHandler (la clase que maneja eventos)
    protected ActionPerformer actionPerformer;    //instancia de ActionPerformer (la clase que ejecuta acciones)
    protected UndoManager undoManager;            //instancia de UndoManager (administrador de edición)

    protected boolean textAreaEnable;
    /**
     * Punto de entrada del programa.
     *
     * Instanciamos esta clase para construir la GUI y hacerla visible.
     *
     * @param args argumentos de la línea de comandos.
     */
    /*public static void main(String[] args) {
        //construye la GUI en el EDT (Event Dispatch Thread)
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
 
            @Override
            public void run() {
                new Editor().jFrame.setVisible(true);    //hace visible la GUI creada por la clase TPEditor
            }
        });
    }*/
 
    /**
     * Constructor de la clase.
     *
     * Se construye la GUI del editor, y se instancian clases importantes.
     */
    public Editor() {
        try {    //LookAndFeel nativo
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println(ex);
        }
 
        //construye un JFrame con título
        jFrame = new JFrame("Simulador de Máquina Genérica");
        jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
 
        //asigna un manejador de eventos para el cierre del JFrame
        jFrame.addWindowListener(new WindowAdapter() {
 
            @Override
            public void windowClosing(WindowEvent we) {
                actionPerformer.actionExit();    //invoca el método actionExit()
            }
        });
 
        eventHandler = new EventHandler();              //construye una instancia de EventHandler
        
        undoManager = new UndoManager();                //construye una instancia de UndoManager
        undoManager.setLimit(50);                       //le asigna un límite al buffer de ediciones
 
        buildTextArea();     //construye el área de edición, es importante que esta sea la primera parte en construirse
       // buildMenuBar();      //construye la barra de menú
        buildToolBar();      //construye la barra de herramientas
        buildCompilationBar();
        buildStatusBar();	//construye la barra de estado
        actionPerformer = new ActionPerformer(this);    //construye una instancia de ActionPerformer
        //buildPopupMenu();    //construye el menú emergente
       // jFrame.setJMenuBar(jMenuBar);                              //designa la barra de menú del JFrame
        Container c = jFrame.getContentPane();                     //obtiene el contendor principal
        c.add(jToolBar, BorderLayout.NORTH);                       //añade la barra de herramientas, orientación NORTE del contendor
        c.add(new JScrollPane(jTextArea), BorderLayout.CENTER);    //añade el area de edición en el CENTRO
        c.add(statusBar, BorderLayout.SOUTH);                      //añade la barra de estado, orientación SUR
//        c.add(compilationResultsBar, BorderLayout.PAGE_START);
//        c.add(compilationResultsBar);
        //configura el JFrame con un tamaño inicial proporcionado con respecto a la pantalla
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setSize(pantalla.width / 2, pantalla.height / 2);
 
        //centra el JFrame en pantalla
        jFrame.setLocationRelativeTo(null);
        disableTextArea();
    }

    public boolean isTextAreaEnable(){
        return textAreaEnable;
    }

    public void enableTextArea() {
        jTextArea.setText("");
        jTextArea.setEditable(true);
        jTextArea.setBackground(Color.WHITE);
        textAreaEnable = true;
    }

    public void disableTextArea() {
        jTextArea.setText("Bienvenido al Simulador de Maquina Generica! \n\t Utilice los botones Nuevo o Abrir para comenzar");
        jTextArea.setEditable(false);
        jTextArea.setBackground(Color.LIGHT_GRAY);
        textAreaEnable = false;
    }

    /**
     * Construye el área de edición.
     */
    private void buildTextArea() {
        jTextArea = new JTextArea();    //construye un JTextArea
 
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
 
        //construye el botón "Nuevo"
        JButton buttonNew = new JButton();
        //le asigna una etiqueta flotante
        buttonNew.setToolTipText("Nuevo");
        //le asigna una imagen ubicada en los recursos del proyecto
        buttonNew.setIcon(new ImageIcon(getClass().getClassLoader().getResource("papel.png").getPath()));
        //le asigna un nombre de comando
        buttonNew.setActionCommand("cmd_new");
 
        JButton buttonOpen = new JButton();
        buttonOpen.setToolTipText("Abrir");
        buttonOpen.setIcon(new ImageIcon(getClass().getClassLoader().getResource("carpeta.png").getPath()));
        buttonOpen.setActionCommand("cmd_open");
 
        JButton buttonSave = new JButton();
        buttonSave.setToolTipText("Guardar");
        buttonSave.setIcon(new ImageIcon(getClass().getClassLoader().getResource("tp_save.png").getPath()));
        buttonSave.setActionCommand("cmd_save");
 
        JButton buttonSaveAs = new JButton();
        buttonSaveAs.setToolTipText("Guardar como...");
        buttonSaveAs.setIcon(new ImageIcon(getClass().getClassLoader().getResource("tp_saveas.png").getPath()));
        buttonSaveAs.setActionCommand("cmd_saveas");
 
       JButton buttonExecute = new JButton();
       buttonExecute.setToolTipText("Ejecutar");
       buttonExecute.setIcon(new ImageIcon(getClass().getClassLoader().getResource("play.png").getPath()));
       buttonExecute.setActionCommand("cmd_execute");
        
        JButton buttonCompile = new JButton();
        buttonCompile.setToolTipText("Compilar");
        buttonCompile.setIcon(new ImageIcon(getClass().getClassLoader().getResource("compilar.png").getPath()));
        buttonCompile.setActionCommand("cmd_compile");
        
        JButton buttonExecuteStep = new JButton();
        buttonExecuteStep.setToolTipText("Ejecutar paso a paso");
        buttonExecuteStep.setIcon(new ImageIcon(getClass().getClassLoader().getResource("play_pause.png").getPath()));
        buttonExecuteStep.setActionCommand("cmd_executeStep");
        
        JButton buttonTranslate = new JButton();
        buttonTranslate.setToolTipText("Traducir");
        buttonTranslate.setIcon(new ImageIcon(getClass().getClassLoader().getResource("traducor.png").getPath()));
        buttonTranslate.setActionCommand("cmd_translate");

        JButton buttonHexaCoonversion = new JButton();
        buttonTranslate.setToolTipText("Conversion Hexadecimal");
        buttonTranslate.setIcon(new ImageIcon(getClass().getClassLoader().getResource("bit_refresh.png").getPath()));
        buttonTranslate.setActionCommand("cmd_hexa");
 
        buttonUndo = new JButton();
        buttonUndo.setEnabled(false);
        buttonUndo.setToolTipText("Deshacer");
        buttonUndo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("tp_undo.png").getPath()));
        buttonUndo.setActionCommand("cmd_undo");
 
        buttonRedo = new JButton();
        buttonRedo.setEnabled(false);
        buttonRedo.setToolTipText("Rehacer");
        buttonRedo.setIcon(new ImageIcon(getClass().getClassLoader().getResource("tp_redo.png").getPath()));
        buttonRedo.setActionCommand("cmd_redo");
 
        JButton buttonCut = new JButton();
        buttonCut.setToolTipText("Cortar");
        buttonCut.setIcon(new ImageIcon(getClass().getClassLoader().getResource("tp_cut.png").getPath()));
        buttonCut.setActionCommand("cmd_cut");
 
        JButton buttonCopy = new JButton();
        buttonCopy.setToolTipText("Copiar");
        buttonCopy.setIcon(new ImageIcon(getClass().getClassLoader().getResource("tp_copy.png").getPath()));
        buttonCopy.setActionCommand("cmd_copy");
 
        JButton buttonPaste = new JButton();
        buttonPaste.setToolTipText("Pegar");
        buttonPaste.setIcon(new ImageIcon(getClass().getClassLoader().getResource("tp_paste.png").getPath()));
        buttonPaste.setActionCommand("cmd_paste");
 
        jToolBar.add(buttonNew);    //se añaden los botones construidos a la barra de herramientas
        jToolBar.add(buttonOpen);
        jToolBar.add(buttonSave);
        jToolBar.add(buttonSaveAs);
        jToolBar.addSeparator();    //añade separadores entre algunos botones
        jToolBar.add(buttonExecute);
        jToolBar.add(buttonExecuteStep);
        jToolBar.add(buttonCompile);
        jToolBar.addSeparator();
        jToolBar.add(buttonTranslate);
        jToolBar.add(buttonHexaCoonversion);
        jToolBar.addSeparator();
        jToolBar.add(buttonUndo);
        jToolBar.add(buttonRedo);
        jToolBar.addSeparator();
        jToolBar.add(buttonCut);
        jToolBar.add(buttonCopy);
        jToolBar.add(buttonPaste);
 
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
        sbFileSize = new JLabel("");
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
        buttonUndo.setEnabled(canUndo);
        buttonRedo.setEnabled(canRedo);
 
        //activa o desactiva las opciones en el menú emergente
       // mpItemUndo.setEnabled(canUndo);
        //mpItemRedo.setEnabled(canRedo);
    }
 
    /**
     * Retorna la instancia de EventHandler, la clase interna que maneja eventos.
     *
     * @return el manejador de eventos.
     */
    protected EventHandler getEventHandler() {
        return eventHandler;
    }
 
    /**
     * Retorna la instancia de UndoManager, la cual administra las ediciones sobre
     * el documento en el área de texto.
     *
     * @return el administrador de edición.
     */
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


class EventHandler extends MouseAdapter implements ActionListener,CaretListener,UndoableEditListener {
	
	public void actionPerformed(ActionEvent ae) {
        String ac = ae.getActionCommand();    //se obtiene el nombre del comando ejecutado
        actionPerformer.DoAction(ac);
       
    }

    /**
     * Atiende y maneja los eventos del cursor.
     *
     */
    @Override
    public void caretUpdate(CaretEvent e) {
        final int caretPos;  //valor de la posición del cursor sin inicializar
        int y = 1;           //valor de la línea inicialmente en 1
        int x = 1;           //valor de la columna inicialmente en 1

        try {
            //obtiene la posición del cursor con respecto al inicio del JTextArea (área de edición)
            caretPos = jTextArea.getCaretPosition();
            //sabiendo lo anterior se obtiene el valor de la línea actual (se cuenta desde 0)
            y = jTextArea.getLineOfOffset(caretPos);

            /** a la posición del cursor se le resta la posición del inicio de la línea para
            determinar el valor de la columna actual */
            x = caretPos - jTextArea.getLineStartOffset(y);

            //al valor de la línea actual se le suma 1 porque estas comienzan contándose desde 0
            y += 1;
        } catch (BadLocationException ex) {    //en caso de que ocurra una excepción
            System.err.println(ex);
        }

        /** muestra la información recolectada en la etiqueta sbCaretPos de la
        barra de estado, también se incluye el número total de lineas */
        sbCaretPos.setText("Líneas: " + jTextArea.getLineCount() + " - Y: " + y + " - X: " + x);
    }

    /**
     * Atiende y maneja los eventos sobre el documento en el área de edición.
     *
     * @param uee evento de edición
     */
    @Override
    public void undoableEditHappened(UndoableEditEvent uee) {
        /** el cambio realizado en el área de edición se guarda en el buffer
        del administrador de edición */
        undoManager.addEdit(uee.getEdit());
        updateControls();    //actualiza el estado de las opciones "Deshacer" y "Rehacer"

        hasChanged = true;
    }

}
public void setjTextArea(JTextArea jTextArea) {
	this.jTextArea.setText(jTextArea.getText());
}

}