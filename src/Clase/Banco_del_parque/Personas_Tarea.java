package Clase.Banco_del_parque;

public class Personas_Tarea extends Thread {
    private Banco banco;
    // Estos valores tienen que ser aleatorios
    private long tpaseo;
    private long tsentado;

    public Personas_Tarea(Banco banco, String nombre) {
        super(nombre);
        this.banco = banco;
        // Hay más probabilidad de que salgan los numeros (Segun Julio).
        this.tpaseo = (long) ((Math.random() * 1000000) % 2001 + 100);
        this.tsentado = (long) ((Math.random() * 1000000) % 6001 + 100);

        // this.tpaseo = (int) (Math.random() * 2000 + 100);
        // Hay un metodo más facil que es con la clase random
    }

    @Override
    public void run() {
        try {
            System.out.println(getName() + " llega al banco");
            Thread.sleep(tpaseo);
            banco.sentarse();
            System.out.println(getName() + " se ha setado");
            Thread.sleep(tsentado);
            banco.levantarse();
            System.out.println(getName() + " se ha levantado");
        } catch (InterruptedException e) {

        }
    }
}
