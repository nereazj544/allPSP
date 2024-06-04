package Clase;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
 * Escribir un programa concurrente que ejecute N hilos. Todos los hilos realizan el mismo trabajo: 
• Imprimir una línea identificándose, anunciando el inicio de su ejecución y mostrando el 
tiempo que permanecerán dormidos. 
• Dormir durante el tiempo especificado para cada uno de ellos. 
• Imprimir una línea donde se identifiquen de nuevo para anunciar su finalización. 
Cuando todos los hilos hayan finalizado su tarea, el hilo principal imprimirá un mensaje informando 
de ello.
 */


public class Ejercicio1 {
	
	//TODO: Lo encontre por el repositorio que tenia en github xd
	private static final int N = 5;
	
	public static void main(String[] args) throws InterruptedException{
		Thread [] t = new Thread[N];
		
		//Otra manera de hacerlo:
		Thread threads = new Thread();
		
		for (int i = 0; i < N; i++) {
			int timeSleep = i + 1;
			
			//
			System.out.println("Hilo ID: " + threads.getName() + "\n"
					+ "Tiempo en ejecucion: "
					+ "Tiempo de mimir: " + timeSleep + "s.");
			
			//Con retorno del hilo:
			System.out.println("Hilo " + Thread.currentThread().getName() + "; \n"
										//Con un metodo de instacia recibiran un primer parametro de instacia "this"	
					+ "Tiempo en ejecucion: "
					+ "Tiempo de mimir: " + timeSleep + "s.");
			
			t[i] = new Thread(() -> {
				try {
					TimeUnit.SECONDS.sleep(timeSleep);
					
				} catch (Exception e) {
					System.err.println("Error");
					e.printStackTrace();
				}
				
			});
			t[i].start();
			
			
			/*
			 * Thread 
			 * - currentThread  (Clase)
			 * - getName (Instacia) 
			 * 
			 * Para invocar a los metodos de la clase se puede invocar sin extanciarlo, para los metodos de instacia, se necesita crearlo.
			 */
			
			
			
			
			
			
		}
		
		for (Thread thread : t) {
			thread.join();
			System.out.println("HAN FINALIZADO");
		}
	}

	
	//TODO: Realizado en Clase
	public static class Hilo extends Thread{
		private long time;
		
		public Hilo(long t) {
			time = t;
		}
		
		@Override
		public void run() {
			System.out.println(getName() + "  va a dormir: " + time );
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {} //nunca va a saltar la exception
			
			System.out.println(getName() + " ha finalizado ");
		}
		
		public static void main(String[] args) throws InterruptedException {
			int n = 5;
			
			long time;
			Thread [] hilos = new Thread[n];
			for (int i = 0; i <n; i++) {
				time = (long) (Math.random() * 2000+100);
				Thread h = new Hilo(time);
				hilos[i] = new Hilo(time);
				hilos[i].start();
				
			}
			for (int j = 0; j < n; j++) {
				hilos[j].join();
			}
			System.out.println("TODOS LOS HILOS HAN FINALIZADO.");
		}
	}
	
	
}
