package Actividades.Unidad3.ServidorDeContactos.Server;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class Server {
    public static void main(String[] args) {
        Gestion g = new Gestion();
        
        try (ServerSocket serverSocket = new ServerSocket(500)) {
            System.out.println("> Servidor telefono escuchando en puerto <<5000>>");
            serverSocket.setSoTimeout(50000);
            
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    MContactos contactos = new MContactos(socket, g);
                    Thread t = new Thread(contactos);
                    t.start();
                
            } catch (SocketTimeoutException e) {
                System.out.println("> Servidor: tiempo de espera agotado");
            }
        }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
