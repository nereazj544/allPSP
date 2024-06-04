package Actividades.Unidad2.GeneradorDeSecuencias.String;


public class Letras extends Thread{
    private String letra;
    private int r;

    public Letras(String letra, int r) {
        this.letra = letra;
        this.r = r;
    }

    @Override
    public void run() {
        for (int i = 0; i < r; i++) {
            System.out.println(letra);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    
    
}
