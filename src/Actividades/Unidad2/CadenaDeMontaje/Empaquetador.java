package Actividades.Unidad2.CadenaDeMontaje;

public class Empaquetador extends Thread {
    private final Cadena cadena;
    private final int t;
    private boolean pausado = false;
    
    public Empaquetador(Cadena cadena, int t) {
        this.cadena = cadena;
        this.t = t;
    }

    @Override
    public void run() {
        while (true) {
            try {
                int em = cadena.empaProducto(t);
                if (em > 0) {
                    Main.actualizar("Robot empaquetador tipo " + t + " empaqueta " + em + " productos\n");
                }
                sleep(4000);
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized void pausar(){
        pausado = true;
    }
    public synchronized void reanudar(){
        pausado = false;
    }
}
