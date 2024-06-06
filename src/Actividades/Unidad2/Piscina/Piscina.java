package Actividades.Unidad2.Piscina;

import static Actividades.Unidad2.Piscina.Main.actualizar;

import java.util.concurrent.Semaphore;

public class Piscina {
    private boolean p;

    private Semaphore calles = new Semaphore(8);
    private Semaphore semaphore = new Semaphore(1);

    private int h = 0, m = 0, n = 0, s = 0; // Hombres, mujeres, niñx y submarinistas

    public void nadar(String nombre) {
        try {
            calles.acquire();
            if (p) {
                semaphore.release();
            }
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Estadisticas(nombre);
        calles.release();
    }

    public void bucear(String nombre) {
        try {
            calles.acquire(2);
            if (p) {
                semaphore.release();
            }
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Estadisticas(nombre);
        calles.release(2);
    }

    private void Estadisticas(String nombre) {
        actualizar(nombre + " ha entrado a la piscina.\n");
        if (nombre.equals("h")) {
            h++;
        } else if (nombre.equals("m")) {
            m++;
        } else if (nombre.equals("n")) {
            n++;
        } else if (nombre.equals("s")) {
            s++;
        }

        actualizar("Estadisticas acutales\n");
        actualizar("Hombres: " + h + "\n");
        actualizar("Mujeres: " + m + "\n");
        actualizar("Niñxs: " + n + "\n");
        actualizar("Submarinistas: " + s + "\n");

    }

    public void pausar() {
        p = true;
    }

    public void reanudar() {
        p = false;
        semaphore.release();
    }

}
