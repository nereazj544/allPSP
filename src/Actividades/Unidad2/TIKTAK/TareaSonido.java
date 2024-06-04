package Actividades.Unidad2.TIKTAK;

import java.util.concurrent.Semaphore;

import static Actividades.Unidad2.TIKTAK.Main.actualizar;

public class TareaSonido implements Runnable {
    private String sonido;
    private Semaphore semaphore;
    private Semaphore semaphore2;
    private boolean p;

    public TareaSonido(String sonido, Semaphore semaphore, Semaphore semaphore2) {
        this.sonido = sonido;
        this.semaphore = semaphore;
        this.semaphore2 = semaphore2;
    }

    @Override
    public void run() {
        try {
            
            while (true) {
                semaphore.acquire();
                if (Thread.interrupted()) return;
                if (p) {
                    semaphore.release();
                    semaphore2.acquire();
                }
                actualizar(sonido + " ");
                Thread.sleep(1000);
                semaphore2.release();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    public void pausar() {
        p = true;
    }

    public void reanudar() {
        p = false;
        semaphore.release();
        // notify();
    }

}
