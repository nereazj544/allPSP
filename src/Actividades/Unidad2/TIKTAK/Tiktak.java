package Actividades.Unidad2.TIKTAK;

//Movida con los ::

// Usando referencias a metodos usando meros lo del "tic::Tarea" es una referencia a estancia.

	
public class Tiktak {
private String sonido;


public Tiktak(String sonido) {
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
	Tiktak tic = new Tiktak("TIC");
	Tiktak tac = new Tiktak("TAC");
	new Thread(tic::Tarea).start();
	new Thread(tac::Tarea).start();
	
	//Modia con "::"
	
}

//END PROGRAM
}
