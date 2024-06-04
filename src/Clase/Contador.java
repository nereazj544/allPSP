package Clase;

public class Contador {
	
	//Lo que hace ChatGPT
//	private static Object m = new Object(); 
	
//	long v;
	
	//Ahora a pasado a ser un objeto primitivo
	private Long v = 0l;
	//Al usar esto se va a la puta el programa
	
	
	// Crear contadores por defecto
	public Contador() {

	}

	// Crear contadores con cantidad expecifica
	public Contador(long v) {
		this.v = v;

	
		/*MONITORES
		 * El monito referenciado por: THIS es el monitor Si el metodo es una clase el
		 * monitor seria el nombre de la clase: Contador.class (sirve para muchas cosas
		 * pero ahora no nos interesa de mucho)
		 * 
		 */
	}

	// Con solo poner "synchronized" ya se arregla, y los hilos empiezan a competir

	// hilo secion critica: como sabe si entrar? hay un monitor que gestiona esos
	// bloques sincronizados, tiene un cerrejo (bajo nivel).
	// El hilo adquiere el cerrojo

	// Aqui tenemos que controlar la concurriencia, aqui no importa el monitor

//	public synchronized void in() {
//		v++;
//		// No es una operacion atomica
//	}
//	
	
	/*
	 * otra manera
	 */
	
	public  void in() {
		synchronized (v) {
			//Aqui se escoje el monitor.
			//No se esta obligado a usar el this
			v++;
		}
		// No es una operacion atomica
	}
	

	public long getValor() {
		return v;
	}
}
