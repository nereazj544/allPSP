package Clase.Scoket.Modificacion_serv_echo.V2.Server;

import java.net.Socket;


public class Recpetor implements Runnable {
    private Socket socket;
    private Conexion conexion;

    public Recpetor(Socket socket, Conexion conexion) {
        this.socket = socket;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'run'");
    }

}
