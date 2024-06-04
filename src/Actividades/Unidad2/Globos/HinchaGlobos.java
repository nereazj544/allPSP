package Actividades.Unidad2.Globos;

import java.util.concurrent.Semaphore;
import static Actividades.Unidad2.Globos.Main.actualizar;

public class HinchaGlobos extends Thread {
    private AlmacenGlobos almacenGlobos;
    private Semaphore semaphore;
    private String nombre;

    public HinchaGlobos(AlmacenGlobos almacenGlobos, Semaphore semaphore, String nombre) {
        this.almacenGlobos = almacenGlobos;
        this.semaphore = semaphore;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int globo = almacenGlobos.enG();
                if (globo == -1)
                    ;
                semaphore.acquire();
                for (int i = 0; i < 5; i++) {
                    actualizar("GOBLO " + globo + " Volumen " + (i + 1) + "\n");
                    Thread.sleep(100);
                }

                actualizar("Globo " + globo + " ESTALLA\n");
                semaphore.release();
            }
        } catch (Exception e) {
        }
    }

}
