package Actividades.Unidad3.TresEnRaya.SinSwing.Other.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.file.WatchEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server extends Thread {
    private Socket socket1;
    private Socket socket2;
    private DataInputStream in1;
    private DataOutputStream out1;
    private DataInputStream in2;
    private DataOutputStream out2;

    public Server(Socket socket1, Socket socket2) throws IOException {
        this.socket1 = socket1;
        this.socket2 = socket2;

        in1 = new DataInputStream(socket1.getInputStream());
        out1 = new DataOutputStream(socket1.getOutputStream());

        in2 = new DataInputStream(socket2.getInputStream());
        out2 = new DataOutputStream(socket2.getOutputStream());
    }

    private void RespuestaAlCliente1(String respuesta) {
        System.out.println(socket1.getInetAddress() + "-> " + respuesta);
        try {
            out1.writeUTF(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void RespuestaAlCliente2(String respuesta) {
        System.out.println(socket2.getInetAddress() + "-> " + respuesta);
        try {
            out2.writeUTF(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String[] tablero = { " ", " ", " ", " ", " ", " ", " ", " ", " " };
            String jugadorActual = "O"; // !Comiencia el jugador O

            while (true) {
                out1.writeUTF(String.join("|", tablero));
                out2.writeUTF(String.join("|", tablero));

                String movimiento;
                if (jugadorActual.equals("O")) {
                    out1.writeUTF("Mueve");
                    movimiento = in1.readUTF();
                } else {
                    out2.writeUTF("Mueve");
                    movimiento = in2.readUTF();
                }

                int p = Integer.parseInt(movimiento);
                tablero[p] = jugadorActual;

                boolean Win = false;

                for (int i = 0; i < 9; i += 3) {
                    if (tablero[i].equals(jugadorActual) && tablero[i + 1].equals(jugadorActual)
                            && tablero[i + 2].equals(jugadorActual)) {
                        Win = true;
                        break;
                    }
                }
                // Verticales
                if (!Win) {
                    for (int i = 0; i < 3; ++i) {
                        if (tablero[i].equals(jugadorActual) && tablero[i + 3].equals(jugadorActual)
                                && tablero[i + 6].equals(jugadorActual)) {
                            Win = true;
                            break;
                        }
                    }
                }
                // Diagonales
                if (!Win) {
                    if (tablero[0].equals(jugadorActual) && tablero[4].equals(jugadorActual)
                            && tablero[8].equals(jugadorActual)) {
                        Win = true;
                    } else if (tablero[2].equals(jugadorActual) && tablero[4].equals(jugadorActual)
                            && tablero[6].equals(jugadorActual)) {
                        Win = true;
                    }
                }
                if (Win) {
                    out1.writeUTF("Player " + jugadorActual + " has won");
                    out2.writeUTF("Player " + jugadorActual + " has won");
                    break; // Terminar el juego al tener un ganador
                }

                jugadorActual = jugadorActual.equals("O") ? "O" : "X";

            }

        } catch (IOException e) {
            RespuestaAlCliente1("Error: " + e.getLocalizedMessage());
            RespuestaAlCliente2("Error: " + e.getLocalizedMessage());
        } finally {
            try {
                if (socket1 != null) {
                    socket1.close();
                }
                if (socket2 != null) {
                    socket2.close();
                }
            } catch (SocketTimeoutException e) {
                RespuestaAlCliente1("Error: Tiempo de espera agotado");
                RespuestaAlCliente2("Error: Tiempo de espera agotado");
            } catch (IOException e) {
                RespuestaAlCliente1("Error: " + e.getLocalizedMessage());
                RespuestaAlCliente2("Error: " + e.getLocalizedMessage());
            }
        }
    }

    public static void main(String[] args) {
        int port = 5050;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(60000);
            ExecutorService executorService = Executors.newFixedThreadPool(1000);
            System.out.println("Servidor escuchado en el puerto: " + port);

            while (true) {
                Socket socket1 = serverSocket.accept();
                Socket socket2 = serverSocket.accept();
                new Server(socket1, socket2).start();
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
    }
}
