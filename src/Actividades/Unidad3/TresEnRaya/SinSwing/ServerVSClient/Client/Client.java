package Actividades.Unidad3.TresEnRaya.SinSwing.ServerVSClient.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private DataInputStream in;
    private DataOutputStream out;
    private char Marca;
    private Scanner scanner = new Scanner(System.in);

    public Client(String direccionServidor) throws Exception {
        Socket socket = new Socket(direccionServidor, 5000);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        new Thread(() -> {
            try {
                String respuesta;
                while ((respuesta = in.readUTF()) != null) {
                    if (respuesta.startsWith("BIENVENIDO")) {
                        Marca = respuesta.charAt(10);
                        System.out.println("Bienvenido Jugador " + Marca);
                    } else if (respuesta.startsWith("MOVIMIENTO_VALIDO")) {
                        System.out.println("Movimiento válido, espera a tu oponente.");
                    } else if (respuesta.startsWith("MOVIMIENTO_OPONENTE")) {
                        int x = Integer.parseInt(respuesta.substring(18, 19));
                        int y = Integer.parseInt(respuesta.substring(20, 21));
                        System.out.println("El oponente se movió a (" + x + ", " + y + ")");
                        System.out.println("Tu turno.");
                    } else if (respuesta.startsWith("VICTORIA")) {
                        System.out.println("¡Has ganado!");
                        break;
                    } else if (respuesta.startsWith("DERROTA")) {
                        System.out.println("Has perdido.");
                        break;
                    } else if (respuesta.startsWith("EMPATE")) {
                        System.out.println("Empate.");
                        break;
                    } else if (respuesta.startsWith("MENSAJE")) {
                        System.out.println(respuesta.substring(8));
                    }
                }
                out.writeUTF("SALIR");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void jugar() {
        while (true) {
            System.out.println("Introduce tu movimiento (fila[0-2] y columna[0-2]): ");
            int fila = scanner.nextInt();
            int columna = scanner.nextInt();
            try {
                out.writeUTF("MOVER " + fila + " " + columna);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Client cliente = new Client("localhost");
        cliente.jugar();
    }
}

