package Actividades.Unidad3.ServidorEco.data.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import Actividades.Unidad3.ServidorEco.data.*;

public class Server {
    public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(5000);
		ExecutorService executor = Executors.newFixedThreadPool(100);
		while (true) {
			Socket socket = serverSocket.accept();
			executor.execute(new TareaRespuesta(socket));
		}
        
    }
}
