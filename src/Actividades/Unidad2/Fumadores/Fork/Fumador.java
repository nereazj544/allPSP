package Actividades.Unidad2.Fumadores.Fork;

import static Actividades.Unidad2.Fumadores.Fork.Main.actualizar;

public class Fumador extends Thread {
	Ingrediente ingrediente;
	Mesa mesa;
	// Boolean pausado=true;
	boolean pausado;

	public Fumador(String nombre, Ingrediente ingrediente, Mesa mesa) {
		super(nombre);
		this.ingrediente = ingrediente;
		this.mesa = mesa;
	}

	public synchronized void pausar() {
		pausado = true;
	}

	public synchronized void reanudar() {
		pausado = false;
		notify();
	}

	@Override
	public void run() {
		while (true) {
			// ! CODIGO AÑADIDO.

			synchronized (this) {
				if (pausado) {
					try {
						wait(); // Esperamos a que sea notificado por el Mian
					} catch (InterruptedException e) {
						interrupt();
						return;
					}

				}
			}
			// ! CODIGO INICAL
			mesa.retirar(ingrediente);
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				interrupt();
				return;
			}
			actualizar(getName() + " terminó de fumar\n");
			// actualizar(getName() + " finaliza su tarea\n");
		}
		// System.out.println("finaliza su tarea\n");
		// TODO quitar el comentario de la línea siguiente cuando se pueda finalizar el
		// hilo (es decir, cuando el bucle ya no sea infinito)
	}
}
