package Actividades.Unidad2.Piscina;

public class Submarinistas extends Thread {
    private final Piscina p;
    private final String nombre;

    public Submarinistas(Piscina p, String nombre) {
        this.p = p;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        p.bucear(nombre);
    }

}