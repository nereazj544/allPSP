package Clase.Banco_del_parque;

public class Banco {

    // !RECURSO COMPARTIDO ⬇️
    private int plazaslibres;

    // private int ocupado;
    // ! MONITOS ⬇️
    public Banco(int plazas) {
        plazaslibres = plazas;
    }

    // Estos metodos lo van a invocar los hilos
    public synchronized void sentarse() {

        // ! se necesita un while no un if
        while (plazaslibres == 0) {
            try {
                System.out.println(Thread.currentThread().getName() + " espera");
                wait();

                // ? Cuando termine compite por adquirir el bloqueo de la seccion critica, el
                // hilo que entre aqui adquiere el cerrojo, cuando adquiere no quiere decir que
                // lo tenga, cuando sale del metodo no va a tener el cerrojo y volvera a
                // competir a por el.
                // ? El estado wait no compite por el cerrojo, tienes que esperar a que alguien
                // te meta una ostia y te despierte o que el despertador te despierte.
                // ? El estado bloqueo
                // ! Si se le va el wait si no hay plazas, en el momento que salga puede que
                // haya plazas (o no). Si sale del wait se quedaria bloqueado (al no conseguir
                // el cerrojo y no hay forma de recuperaro).

            } catch (InterruptedException e) {
            }

            plazaslibres--; // ! simulacion de ocupacion de plaza

        }
    }

    public synchronized void levantarse() {
        plazaslibres++;
        notify(); // notifica a uno solo, notificara al que el planificador quiera
        // notifyAll(); //no
    }

}
