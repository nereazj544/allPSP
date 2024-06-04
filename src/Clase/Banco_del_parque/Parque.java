package Clase.Banco_del_parque;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Parque {

    public static void main(String[] args) throws Exception {
        // Banco banco;
        // Personas_Tarea[] personas;

        // Scanner sc = new Scanner(System.in);
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Numero de plazas: ");
        int numPlazas = Integer.parseInt(r.readLine());
        System.out.println("Numero de personas: ");
        int numPerso = Integer.parseInt(r.readLine());

        Banco b = new Banco(numPlazas);
        Personas_Tarea[] p = new Personas_Tarea[numPerso];

        for (int i = 0; i < p.length; i++) {
            p[i] = new Personas_Tarea(b, " PERSONA " + i);
        }
        for (int i = 0; i < p.length; i++) {
            p[i].start();
        }
        for (int i = 0; i < p.length; i++) {
            p[i].join();
        }

        System.out.println("FIN");

    }

}

/*
 * Los metodos "wait", "sleep" los deja en modo de espera
 * Hay dos:
 * wait()
 * wait(ms) (despues de el tiempo de espera, no hace falta usar notify)
 * relacionados con estos dos metodos: notify (notifica a otro) y notifyAll
 * (notifica a todos) (comunicacion entre hilos).
 * Notifican el estado en el que tienen que estar. Esta es la unica comunicacion
 * que exite entre hilos.
 * ! Solo se pueden ejecutar el monitor.
 * Pueden invocarlo cual quierer objeto, solo se pueden invocar en una sececion
 * critica y con el monitor.
 */