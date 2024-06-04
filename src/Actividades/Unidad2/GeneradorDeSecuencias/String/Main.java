package Actividades.Unidad2.GeneradorDeSecuencias.String;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("INTRODUCE LETRAS: ");
        
        String pt = sc.nextLine();

        if (pt == null || pt.isEmpty()) {
            System.out.println("No puede ser vacío");
        }
        for (String letra : pt.split("")) {
            Letras h = new Letras(letra, 1);
            h.start();

            try {
                h.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }
}
