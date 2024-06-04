package Clase;

//Movida con los ::

// Usando referencias a metodos usando meros lo del "tic::Tarea" es una referencia a estancia.

	
public class Ejercico2 {
private String sonido;


public Ejercico2(String sonido) {
	this.sonido = sonido;
	
}

void Tarea() {
	while (true) {
		System.out.println(sonido + " ");
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}


public static void main(String[] args) {
	Ejercico2 tic = new Ejercico2("TIC");
	Ejercico2 tac = new Ejercico2("TAC");
	new Thread(tic::Tarea).start();
	new Thread(tac::Tarea).start();
	
	//Modia con "::"
	
}

//END PROGRAM
}
