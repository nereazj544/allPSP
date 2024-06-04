package Actividades.Unidad2.CreadcionDeHilos;

import static Actividades.Unidad2.CreadcionDeHilos.Main.actualizar;

public class CreacionHilos implements Runnable{
    private int id;
    private int tiempoMimir;
    private boolean p;

    public CreacionHilos(int id, int tiempoMimir) {
        this.id = id;
        this.tiempoMimir = tiempoMimir;
    }

    @Override
    public void run() {
        actualizar("Hilo " + id + " inciando. \n Dormira por " + tiempoMimir);

        try {
            Thread.sleep(tiempoMimir);
        } catch (InterruptedException e) {
            actualizar("Hilo " + id + " mimiendo");
            return;
        }
    }

    public synchronized void pausar(){
        p = true;
    }
    public synchronized void reanudar(){
        p = false;
        notify();
    }

}
