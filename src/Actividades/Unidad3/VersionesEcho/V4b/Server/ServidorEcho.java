package Actividades.Unidad3.VersionesEcho.V4b.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorEcho {

	public static void main(String[] args) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(9999)) {
			serverSocket.setSoTimeout(10000);
			Almacen almacen = new Almacen();
			new Emisor(almacen).start();
			ExecutorService executor = Executors.newFixedThreadPool(1000);
			System.out.println("Servidor ECHO escuchando en puerto 9999");
			while (true) {
				try {
					Socket socket = serverSocket.accept();
					executor.submit(new Receptor(socket, almacen));
				} catch (SocketTimeoutException e) {
					System.out.println("Error: tiempo de espera agotado");
				}
			}
		}
	}
}
