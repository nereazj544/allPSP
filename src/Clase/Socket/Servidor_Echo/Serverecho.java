package Clase.Socket.Servidor_Echo;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;



public class Serverecho {

    /**
     * * El puerte es el que se va a comunicar con el cliente
     * 
     */

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("Eco Servidor escuchando en el puerto 9999");

        while (true) {
            Socket socket = serverSocket.accept(); 
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))){
                String linea;
                while ((linea = in.readLine()) != null) {
                    System.out.println(linea);
                    out.println(linea);
                    out.flush();
                }
            } 
        }

    }
}
