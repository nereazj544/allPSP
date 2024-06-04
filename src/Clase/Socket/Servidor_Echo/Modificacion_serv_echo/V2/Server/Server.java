package Clase.Scoket.Modificacion_serv_echo.V2.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server
 */
public class Server {
    public static LinkedList<Conexion> c = new LinkedList<>(); 
public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(5000);
		ExecutorService executor = Executors.newFixedThreadPool(100);
		while (true) {
			Socket socket = serverSocket.accept();
			c.add(new Conexion(socket, executor));
		}
	}

    
}