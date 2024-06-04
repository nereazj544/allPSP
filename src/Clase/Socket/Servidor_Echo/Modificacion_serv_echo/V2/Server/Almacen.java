package Clase.Scoket.Modificacion_serv_echo.V2.Server;

import java.util.LinkedList;

public class Almacen {
    private LinkedList<String> almacen = new LinkedList<>();

    private static final int MAX = 100;

    public synchronized void almacenar(String s) throws InterruptedException {
        while (almacen.size() == MAX) {
            wait();
        }
        almacen.offer(s);
        notify(); // Es suficinete un notify porque solo hay dos hilos compitiendo
    }

    public synchronized String retirar() throws InterruptedException {
        while (almacen.isEmpty()) {
            wait();
        }
        String s = almacen.poll();
        notify();
        return s;
    }

    // ! END CLASS
}