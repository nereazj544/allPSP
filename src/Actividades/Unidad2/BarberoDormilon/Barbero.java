package Actividades.Unidad2.BarberoDormilon;

public class Barbero implements Runnable {
    private final TiendaBarberia tiendaBarberia;

    public Barbero(TiendaBarberia tiendaBarberia) {
        this.tiendaBarberia = tiendaBarberia;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Cliente cliente = tiendaBarberia.Siguiente();
                tiendaBarberia.servir(cliente);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
}
