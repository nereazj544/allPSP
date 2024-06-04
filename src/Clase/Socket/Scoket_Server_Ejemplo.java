package Clase.Socket;

// import java.io.BufferedReader;
import java.io.IOException;
// import java.io.InputStreamReader;
// import java.io.OutputStreamWriter;
// import java.io.PrintWriter;
import java.net.ServerSocket;
// import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.net.*;

public class Scoket_Server_Ejemplo {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("COMUNICANDOSE POR EL PUERTO: 5000");
            
            ExecutorService e = Executors.newFixedThreadPool(100);
            
            while (true) {
                // ! Creamos el hilo en el servidor ⬇️
                Socket socket = serverSocket.accept();
                e.execute(new TareaRespuesta(socket));
            }
        }

    }
}