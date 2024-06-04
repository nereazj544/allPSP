package Actividades.Unidad2.CadenaDeMontaje;

public class Colocador extends Thread{
    private final Cadena cadena;
    private boolean pausado = false;
    public Colocador(Cadena cadena) {
        this.cadena = cadena;
    }

    @Override
    public void run() {
        int t = 1;
        while (true) {
            try{
                if (cadena.colProducto(t)) {
                    Main.actualizar("Robot colocador colaca producto tip " + t + "\n");
                }
                t = (t % 3) + 1;
                sleep(1000);
            }catch(InterruptedException e){
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
