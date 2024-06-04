package Clase.Scoket.Modificacion_serv_echo.V3.Server;

import java.io.PrintWriter;
import java.net.Socket;

public class Emisor implements Runnable {
    private Socket socket;
    private Almacen almacen;
   
    public Emisor(Socket socket, Almacen almacen) {
        this.socket = socket;
        this.almacen = almacen;
    }

    @Override
    public void run() {
        try {
            PrintWriter p = new PrintWriter(socket.getOutputStream(), true);
            String mensaje;
            while ((mensaje = almacen.oMensjae()) != null) {
                p.println(mensaje);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
