package main;


import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {
	public final static Semaphore SEMAFORO = new Semaphore(1);

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
//		System.out.println("Introduce patron");
		String patron = "t2v3h3x5o8";

		// Saco los patrones individualmente
		String[] secuencias = new String[patron.length() / 2];
		for (int i = 0; i < patron.length() / 2; i++) {
			secuencias[i] = patron.substring(i * 2, (i * 2) + 2);
		}

		// Ahora creo un hilo EscribeLetras para cada uno.
		Thread[] hilos = new Thread[secuencias.length];
		for (int j = 0; j < secuencias.length; j++) {
			hilos[j] = new EscribeLetras(secuencias[j]);
			hilos[j].start();
		}
		for(int i = 0; i<secuencias.length; i++)
			try {
				hilos[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		

		sc.close();
		// t6v5a1b7
	}

}
