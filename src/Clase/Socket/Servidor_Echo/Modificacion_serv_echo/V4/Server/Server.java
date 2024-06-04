package Clase.Scoket.Modificacion_serv_echo.V4.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server
 */
public class Server {
	public static void main(String[] args) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(5000)) {
			ExecutorService executor = Executors.newFixedThreadPool(100);
			Almacen a = new Almacen();
			new Emisor(a).start();
			System.out.println("SERCIDOR PUERTO 5000");
			while (true) {
				Socket socket = serverSocket.accept();
				
			}
		}
	}

}