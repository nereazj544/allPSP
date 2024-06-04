package Actividades.Unidad3.VersionesEcho.V4b.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class Receptor implements Runnable {

	private Socket socket;
	private Almacen almacen;

	public Receptor(Socket socket, Almacen almacen) {
		this.socket = socket;
		this.almacen = almacen;
	}

	@Override
	public void run() {
		boolean fin = false;
		try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				DataInputStream in = new DataInputStream(socket.getInputStream())) {
				socket.setSoTimeout(10000);
			while (!fin) {
				String s = in.readUTF();
				System.out.println(socket.getInetAddress() + "\n" + s);
				almacen.almacenar(s, out);
			}
		} catch (EOFException e) {
			fin = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
