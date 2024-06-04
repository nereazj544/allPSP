package Clase.Scoket.Modificacion_serv_echo.V3.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Recpetor implements Runnable {
    private Socket socket;
    private Almacen almacen;

    public Recpetor(Socket socket, Almacen almacen) {
        this.socket = socket;
        this.almacen = almacen;
    }

    @Override
    public void run() {
        try {
            BufferedReader b = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String mensaje;

            while ((mensaje = b.readLine()) != null) {
                almacen.aMensaje(mensaje);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
