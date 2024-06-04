package Actividades.Unidad2.Banco_Del_Parque;

import static Actividades.Unidad2.Banco_Del_Parque.Main.actualizar;

import java.util.concurrent.Semaphore;

public class PersonasTarea extends Thread {
    private Banco banco;
    private Semaphore semaphore;
    private boolean pausado;

    public PersonasTarea(String name, Banco banco, Semaphore semaphore) {
        super(name);
        this.banco = banco;
        this.semaphore = semaphore;
        this.pausado = false;
    }

    public synchronized void pausar() {
        this.pausado = true;
    }

    public synchronized void reanudar() {
        this.pausado = false;
        synchronized(this){

            notifyAll();
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            synchronized (this) {
                while (pausado) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }

            try {
                semaphore.acquire();

                if (banco.ocupar()) {
                    actualizar(getName() + " ocupó una plaza\n");
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        return;
                    }
                    banco.liberar();
                    actualizar(getName() + " liberó la plaza\n");
                } else {
                    actualizar(getName() + " no pudo ocupar una plaza\n");
                }
            } catch (InterruptedException e) {
                return;
            } finally {
                semaphore.release();
            }
        }
    }
}