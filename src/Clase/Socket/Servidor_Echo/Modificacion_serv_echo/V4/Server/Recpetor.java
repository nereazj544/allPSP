package Clase.Scoket.Modificacion_serv_echo.V4.Server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.xml.crypto.Data;

public class Recpetor implements Runnable {
    private Socket socket;
    private Almacen almacen;
    

    public Recpetor(Socket socket, Almacen almacen) {
        this.socket = socket;
        this.almacen = almacen;
    }

    @Override
    public void run() {
        try (DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream()) ) {
            Boolean fin = false;

            while (!fin) {
                
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
