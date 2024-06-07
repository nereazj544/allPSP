package Actividades.Unidad3.ServidorEco.VersionesEcho.V3b.Client;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private Conector con = new Conector(this);
	private JTextArea texto = new JTextArea();
	private JTextArea echo = new JTextArea();
	private JButton enviar = new JButton("Enviar");
	private Border borde1 = BorderFactory.createLineBorder(Color.DARK_GRAY, 3);
	private Border borde2 = BorderFactory.createLineBorder(Color.GREEN, 3);
	private Border borde3 = BorderFactory.createLineBorder(Color.RED, 3);
	

	public Main() {
		super("Cliente ECHO");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		add(con, BorderLayout.NORTH);
		
		texto.setColumns(80);
		texto.setRows(50);
		texto.setEditable(false);
		texto.setBorder(borde1);
		JScrollPane sp = new JScrollPane(texto, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(sp, BorderLayout.WEST);
		echo.setColumns(80);
		echo.setRows(50);
		echo.setEditable(false);
		echo.setBorder(borde1);
		sp = new JScrollPane(echo, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(sp, BorderLayout.EAST);
		enviar = new JButton("enviar");
		enviar.setEnabled(false);
		enviar.addActionListener(this::enviar);
		add(enviar, BorderLayout.SOUTH);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				con.desconectar();
				System.exit(0);
			}
		});
		
		pack();
		setLocationRelativeTo(null);
	}
	
	public void conexionOn() {
		texto.setEditable(true);
		texto.setBorder(borde2);
		echo.setBorder(borde3);
		enviar.setEnabled(true);
		texto.requestFocus();
	}
	
	public void conexionOff() {
		texto.setEditable(false);
		texto.setBorder(borde1);
		echo.setBorder(borde1);
		echo.setText("");
		enviar.setEnabled(false);
	}
	
	private void enviar(ActionEvent e) {
		con.enviar(texto.getText());
		texto.setText("");
		texto.requestFocus();
	}
	
	public JTextArea getEcho() {
		return echo;
	}
	
	private void iniciar() {
		setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Main()::iniciar);
	}
	
	
}