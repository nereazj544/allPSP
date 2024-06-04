package Actividades.Unidad2.CadenaDeMontaje;

import java.util.concurrent.Semaphore;

public class Cadena {
    private final Semaphore semaphore = new Semaphore(0);
    private final int n;
    private final int[] montaje;
    private  int toatal = 0;
    private boolean pausado = false;

    public Cadena(int n) {
        this.n = n;
        this.montaje = new int[n];
    }


    public boolean colProducto(int t) {
        try {
            semaphore.acquire();
            for (int i = 0; i < n; i++) {
                if (montaje[i] == 0) {
                    montaje[i] = t;
                    semaphore.release();
                    return true;
                }
            }
            
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        semaphore.release();
        return false;
    }


    public int empaProducto(int t) throws InterruptedException{
            semaphore.acquire();
            int em = 0;
            for (int i = 0; i < n; i++) {
                if (montaje[i] == t) {
                    montaje[i] = 0;
                    em++;
                }
            }
            toatal += em;
            semaphore.release();
            return em;
    }

    public int gTotalEmpaquetados(){
        return toatal;
    }
    
public synchronized void pausar(){
    pausado = true;
}
public synchronized void reanudar(){
    pausado = false;
    notifyAll();
}

}
