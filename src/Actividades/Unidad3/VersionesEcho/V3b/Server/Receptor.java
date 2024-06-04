package Actividades.Unidad3.VersionesEcho.V3b.Server;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Receptor implements Runnable {

	private Socket socket;
	private Almacen almacen;
	private Future<?> emisor;

	public Receptor(Socket socket, Almacen almacen, ExecutorService executor) {
		this.socket = socket;
		this.almacen = almacen;
		emisor = executor.submit(new Emisor(socket, almacen));
	}

	@Override
	public void run() {
		try {
			socket.setSoTimeout(30000);
			DataInputStream in = new DataInputStream(socket.getInputStream());
			String s;
			while (true) {
				s = in.readUTF();
				System.out.println(socket.getInetAddress() + " -> " + s);
				almacen.almacenar(s);
			}
		} catch (SocketTimeoutException e){
			System.out.println("Error: tiempo de espera agotado");
		} catch (EOFException e) {
			System.out.println(socket.getInetAddress() + ": EOF");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			emisor.cancel(true);
			if (!emisor.isDone())
				try {
					System.out.println(socket.getInetAddress() + ": CONEXIÓN FINALIZADA");
					emisor.get();
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println(socket.getInetAddress() + ": CONEXIÓN FINALIZADA");
		}
	}

}