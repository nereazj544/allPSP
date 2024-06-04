package Clase.Scoket.Modificacion_serv_echo.V4.Server;

import java.io.DataOutputStream;
import java.util.LinkedList;


public class Almacen {
    private LinkedList<Items> almacen = new LinkedList<>();
    private static final int MAX = 1000;

    public synchronized void almacen (String s, DataOutputStream d){
        while (almacen.size() == MAX) {
            try {
                wait();
            } catch (InterruptedException e) {}
        }
        almacen.offer(new Items(s, d));
        notify();
    }

    public synchronized Items retirar(){
        try {
            while (almacen.isEmpty()) {
                wait();
                Items i = almacen.poll();
                notify();
                return i;
            }
        } catch (Exception e) {}
        return null;
    }

    // ! END CLASS
}