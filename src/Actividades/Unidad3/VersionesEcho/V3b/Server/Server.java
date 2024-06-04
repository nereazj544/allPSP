package Actividades.Unidad3.VersionesEcho.V3b.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(5000)) {
			serverSocket.setSoTimeout(60000);
			Almacen almacen = new Almacen();
			ExecutorService executor = Executors.newFixedThreadPool(1000);
			System.out.println("Servidor ECHO escuchando en puerto 5000");
			while (true) {
				try {
					Socket socket = serverSocket.accept();
					executor.submit(new Receptor(socket, almacen, executor));
				} catch (SocketTimeoutException e) {
					System.out.println("Error: tiempo de espera agotado");
				}
			}
		}
	}
}
