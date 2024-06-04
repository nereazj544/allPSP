package Actividades.Unidad2.GeneradorDeSecuencias.Char;

public class LetrasChar extends Thread{
    
    private char letra;
    private int r;

public LetrasChar(char letra, int r) {
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
