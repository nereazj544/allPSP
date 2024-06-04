package Actividades.Unidad2.Globos;

import static Actividades.Unidad2.Globos.Main.actualizar;

import java.util.concurrent.Semaphore;

public class PichaGlobos extends Thread{
    
    private String nombre;
    private Semaphore semaphore;
    public PichaGlobos(String nombre, Semaphore semaphore) {
        this.nombre = nombre;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
    try {
        
        while (true) {
            Thread.sleep(100);

            if (semaphore.availablePermits() < 3) {
                actualizar("Globo " + (int)(Math.random() * 3 +1) + " PINCHADO POR " + nombre);
                semaphore.release();
            }else{
                break;
            }
        }




    } catch (Exception e) {
    }
    }
}
