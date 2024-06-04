package Clase.Scoket.Modificacion_serv_echo.V2.Server;

import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class Conexion {
    private Socket socket;
    private Almacen Almacen;
    private Emisor emisor;
    private Recpetor recpetor;

    public Conexion(Socket socket, ExecutorService service) {
        this.socket = socket;
        emisor = new Emisor(socket);
        recpetor = new Recpetor(socket, this);
    }

    public void Finalizar() {
        //! El repcetor es el que finalizara la conexion, se elimara a si mismo. Se ha de finalizar de manera ordenada

        //TODO: EL RECPTROR TIENE QUE INVOCAR ESTE METODO

        //!LO ULTIMO
        Server.c.remove(this);
    }

}