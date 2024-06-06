package Actividades.Unidad2.Piscina;

public class Nadador extends Thread{
    private final Piscina p;
    private final String nombre;

    public Nadador(Piscina p, String nombre) {
        this.p = p;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        p.nadar(nombre);
    }

}
