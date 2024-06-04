package Clase.Scoket.Modificacion_serv_echo.V3.Server;

import java.util.LinkedList;
import java.util.Queue;

public class Almacen {
    private Queue<String> mensajes = new LinkedList<>();
    
    public synchronized void aMensaje (String mensaje){
        mensajes.add(mensaje);
    }

    public synchronized String oMensjae(){
        return mensajes.poll();
    }
    // ! END CLASS
}