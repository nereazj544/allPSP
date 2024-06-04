package Actividades.Unidad2.BarberoDormilon;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class TiendaBarberia {
    private final int sillas;
    private final List<Cliente> esperar;
    private final Semaphore barberoS;
    private final Semaphore clienteS;
    private boolean pausa=false;
    private final Semaphore pS = new Semaphore(0);

    public TiendaBarberia(int sillas) {
        this.sillas = sillas;
        this.esperar= Collections.synchronizedList(new LinkedList<>());
        this.barberoS = new Semaphore(1);
        this.clienteS = new Semaphore(0);
    }

    public void entrar(Cliente cliente){
        synchronized(esperar){
            if (esperar.size() < sillas) {
                esperar.add(cliente);
                Main.actualizar("Cliente " + cliente.getId() + " entra en la sala de espera"+ "\n");
                clienteS.release();
            }else{
                Main.actualizar("Cliente " + cliente.getId() + " se piro no hay sitio"+ "\n");
            }
        }
    }

    //Espera del siguiente cliente
    public Cliente Siguiente() throws InterruptedException{
        clienteS.acquire();
        synchronized(esperar){
            return esperar.remove(0);
        }
    }

    //Simulacion del corte
    public void servir (Cliente cliente) throws InterruptedException{
        barberoS.acquire();
        Main.actualizar("Barbero atendio al cliente " + cliente.getId() + "\n");
        Thread.sleep(1000);
        Main.actualizar("Barbero termino con el cliente " + cliente.getId()+ "\n");
        barberoS.release(); //? Liberacion del barbero
    }

    
    public void pausa() {
        pausa = true;
    }
    public void reanudar() {
        pausa = false;
        pS.release();
    }

    public boolean isPausa(){
        return pausa;
    }
    

}
