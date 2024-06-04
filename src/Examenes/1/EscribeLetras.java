package main;

public class EscribeLetras extends Thread {

	String comando;

	public EscribeLetras(String comando) {
		this.comando = comando;
	}

	@Override
	public void run() {

		String letra = Character.toString(comando.charAt(0)).toUpperCase();
		int repeticiones = Integer.valueOf(Character.toString(comando.charAt(1)));

		try {
			Main.SEMAFORO.acquire();
			for (int i = 0; i < repeticiones; i++) {
				System.out.print(letra);
				try {
					sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Main.SEMAFORO.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
