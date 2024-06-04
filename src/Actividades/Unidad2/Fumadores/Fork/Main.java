package Actividades.Unidad2.Fumadores.Fork;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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

	private Mesa mesa = new Mesa();
	private Agente agente = new Agente(mesa);
	private Fumador f1 = new Fumador("Fernándo", Ingrediente.TABACO, mesa);
	private Fumador f2 = new Fumador("Manuela", Ingrediente.CERILLAS, mesa);
	private Fumador f3 = new Fumador("Carmen", Ingrediente.PAPEL, mesa);

	public Main() {
		super("Fumadores");
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

	private synchronized void pausa(ActionEvent e) {
		pausa.setEnabled(false);
		reanudar.setEnabled(true);
		textArea.append("PAUSADO\n");
		// TODO pausar fumadores y agente
		f1.pausar();
		f2.pausar();
		f3.pausar();
		agente.pausar();
		// ! CODIGO AÑADIDO.


		/*
		 * ? Con el metodo en desuso de "suspend" si que funciona.
		 * f1.suspend();
		 * f2.suspend();
		 * f3.suspend();
		 * agente.suspend();
		 */
		// Interrumpimos a los hilos
		// f1.interrupt();
		// f2.interrupt();
		// f3.interrupt();
		// agente.interrupt();

	
	}

	private synchronized void reanudar(ActionEvent e) {
		pausa.setEnabled(true);
		reanudar.setEnabled(false);
		textArea.append("REANUDADO\n");
		// TODO reanudar fumadores y agente
		// ! CODIGO AÑADIDO.

		/*
		 * ? Con estos metodos si que funciona, pero estan en desuso.
		 * agente.resume();
		 * f1.resume();
		 * f2.resume()
		 * f3.resume();
		 */
		// notifyAll(); // Notificamos a todos los hilos
		f1.reanudar();
		f2.reanudar();
		f3.reanudar();
		agente.reanudar();
	}

	private void iniciar() {
		setVisible(true);
		f1.start();
		f2.start();
		f3.start();
		agente.start();
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
		// TODO finalizar hilos de forma ordenada antes de salir
		// ! CODIGO AÑADIDO.

		// f1.stop();
		// f2.stop();
		// f3.stop();
		// agente.stop();
		
		// Se detiene
		f1.interrupt();
		f2.interrupt();
		f3.interrupt();
		agente.interrupt();

		// Se espera a que termine cada hilo
		try {
			f1.join();
			f2.join();
			f3.join();
			agente.join();
		} catch (Exception ek) {
			// TODO: handle exception
		}
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
