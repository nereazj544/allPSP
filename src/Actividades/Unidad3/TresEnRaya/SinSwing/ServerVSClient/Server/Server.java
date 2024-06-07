package Actividades.Unidad3.TresEnRaya.SinSwing.ServerVSClient.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(5000);
		ExecutorService executor = Executors.newFixedThreadPool(100);
		System.out.println("Servidor escuchando en el puerto 5000");
		serverSocket.setSoTimeout(50000);
		
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				executor.execute(new Tablero(socket));
			} catch (SocketTimeoutException e) {
                System.out.println("> Servidor: tiempo de espera agotado");
            }
		}
    }
}
