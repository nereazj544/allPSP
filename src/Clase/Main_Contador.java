package Clase;

public class Main_Contador implements Runnable {
	// Creacion del hilo

	Contador contador;

	public Main_Contador(Contador contador) {
		this.contador = contador;
	}

	
		
	public static void main(String[] args) throws InterruptedException {
		Contador c = new Contador(150);

		Main_Contador t = new Main_Contador(c);
		Thread h1 = new Thread(t);
		Thread h2 = new Thread(t);
		
		h1.start();
		h2.start();
		
		h1.join();
		h2.join();
		
		
		
		System.out.println(c.getValor());

		/*
		 * Ejecucion de dos tareas que realizan dos cosas dirferentes Thread h1 = new
		 * Thread(new Main_Contador(c)); Thread h2 = new Thread(new Main_Contador(c));
		 */
	}
	
	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			contador.in();

		}
	

	}
}
