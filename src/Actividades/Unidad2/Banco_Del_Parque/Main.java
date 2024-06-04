package Actividades.Unidad2.Banco_Del_Parque;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.concurrent.Semaphore;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Main extends JFrame implements WindowListener {

    private static final long serialVersionUID = 1L;
    private static final JTextArea textArea = new JTextArea();
    private JButton pausa = new JButton("PAUSA");
    private JButton reanudar = new JButton("REANUDAR");

    private Banco banco = new Banco(14);
    private PersonasTarea[] personas = new PersonasTarea[15];
    private Semaphore semaphore = new Semaphore(14, true);

    public Main() {
        super("Banco del Parque");
        this.addWindowListener(this);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Container contentPane = getContentPane();
        contentPane.setPreferredSize(new Dimension(900, 700));
        JPanel panel = new JPanel();
        pausa.addActionListener(this::pausa);
        panel.add(pausa, BorderLayout.WEST);
        reanudar.setEnabled(false);
        reanudar.addActionListener(this::reanudar);
        panel.add(reanudar, BorderLayout.EAST);
        contentPane.add(panel, BorderLayout.NORTH);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        contentPane.add(scrollPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }

    public static void actualizar(String msg) {
        SwingUtilities.invokeLater(() -> textArea.append(msg));
    }

    private void pausa(ActionEvent e) {
        reanudar.setEnabled(true);
        textArea.append("PAUSADO\n");
        banco.pausar();
        for (PersonasTarea persona : personas) {
            persona.pausar();
        }
        
    }

    private void reanudar(ActionEvent e) {
        pausa.setEnabled(true);
        reanudar.setEnabled(false);
        textArea.append("REANUDADO\n");
        for (PersonasTarea persona : personas) {
            persona.reanudar();
        }
        banco.reanudar();
        
    }

    private void iniciar() {
        setVisible(true);

        for (int i = 0; i < personas.length; i++) {
            personas[i] = new PersonasTarea("Persona " + (i + 1), banco, semaphore);
            personas[i].start();
        }

    }

    private static void crear() {
        new Main().iniciar();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::crear);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

}
