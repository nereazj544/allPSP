package Actividades.Unidad2.Banco_Del_Parque;

public class Banco {

    private int Disponible;
    private boolean p;
    

    public Banco(int disponible) {
        Disponible = disponible;
        p = false;
    }



    public synchronized boolean ocupar() {
        if (!p && Disponible > 0) {
            Disponible--;
            return true;
        }
        return false;
    }

    public void liberar() {
        Disponible++;
    }

    public synchronized void pausar(){
        p = true;
    }
    public synchronized void reanudar(){
        p = false;
        notifyAll();
    }


}
