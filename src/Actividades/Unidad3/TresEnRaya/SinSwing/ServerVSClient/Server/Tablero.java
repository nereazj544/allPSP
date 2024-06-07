package Actividades.Unidad3.TresEnRaya.SinSwing.ServerVSClient.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

public class Tablero implements Runnable {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private char[][] tablero;
    private char actualJ;

    public Tablero(Socket socket) {
        this.socket = socket;
        this.tablero = new char[3][3];
        this.actualJ = 'O';
        iniciarTab();
    }

    private void iniciarTab() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = '-';
            }
        }
    }

    @Override
    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF("> Bienvenido O");
            out.writeUTF("> COMIENZA EL JUEGO");

            while (true) {
                String cmd = in.readUTF();
                if (cmd.startsWith("MOVER")) {
                    int x = Integer.parseInt(cmd.substring(6, 7));
                    int z = Integer.parseInt(cmd.substring(8, 9));

                    if (tablero[x][z] == '-' && actualJ == 'O') {
                        tablero[x][z] = 'O';
                        out.writeUTF("> VALIDO");

                        if (Win()) {
                            out.writeUTF("> GANA");
                            break;
                        } else if (tbLleno()) {
                            out.writeUTF("> EMPATE");
                            break;
                        }
                        actualJ = 'X';
                        MovimentoMaquina();
                    } else {
                        out.writeUTF("> NO VALIDO");
                    }
                } else if (cmd.startsWith("SALIR")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("> Error: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("> Error: " + e.getMessage());
            }
        }
    }

    private void MovimentoMaquina() {
        Random r = new Random();
        int x, z;
        do {
            x = r.nextInt(3);
            z = r.nextInt(3);
        } while (tablero[x][z] != '-');
        tablero[x][z] = 'X';
        try {
            out.writeUTF("> MI MOVIMIENTO: " + x + " " + z);
            if (Win()) {
                out.writeUTF("> HE GANADO");
            } else if (tbLleno()) {
                out.writeUTF("> HEMOS EMPATADO");
            } else {
                actualJ = 'O';
            }
        } catch (IOException e) {
            System.out.println("> Error: " + e.getMessage());
        }
    }

    private boolean tbLleno() {
        for (int fila = 0; fila < 3; fila++) {
            for (int col = 0; col < 3; col++) {
                if (tablero[fila][col] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean Win() {
        for (int fila = 0; fila < 3; fila++) {
            if (tablero[fila][0] != '-' && tablero[fila][0] == tablero[fila][1]
                    && tablero[fila][0] == tablero[fila][2]) {
                return true;
            }
        }
        for (int col = 0; col < 3; col++) {
            if (tablero[0][col] != '-' && tablero[0][col] == tablero[1][col] && tablero[0][col] == tablero[2][col]) {
                return true;
            }
        }
        if (tablero[0][0] != '-' && tablero[0][0] == tablero[1][1] && tablero[0][0] == tablero[2][2]) {
            return true;
        }
        if (tablero[0][2] != '-' && tablero[0][2] == tablero[1][1] && tablero[0][2] == tablero[2][0]) {
            return true;
        }
        return false;
    }

}
