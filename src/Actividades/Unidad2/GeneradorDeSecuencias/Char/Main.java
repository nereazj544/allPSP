package Actividades.Unidad2.GeneradorDeSecuencias.Char;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduce la cadena de texto: ");
        String pt = sc.nextLine();

        if (pt == null || pt.isEmpty()) {
            System.out.println("Vacio");
        }

        for (char letra : pt.toCharArray()) {
            LetrasChar h = new LetrasChar(letra, 1);
            h.start();
            h.join();
        }

        sc.close();
    }

    
}
