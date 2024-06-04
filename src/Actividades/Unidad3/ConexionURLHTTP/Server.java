package Actividades.Unidad3.ConexionURLHTTP;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket;

        try {
            serverSocket = new ServerSocket(500);
            System.out.println("SERVIDOR INICIADO");

            while (true) {
                Socket socket = serverSocket.accept();
                Runnable tarea = new TareaDecargarImg(socket);
                executorService.execute(tarea);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
