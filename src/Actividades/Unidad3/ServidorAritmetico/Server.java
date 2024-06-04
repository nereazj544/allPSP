package Actividades.Unidad3.ServidorAritmetico;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(500);

        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            String o = in.readUTF();
            double op = in.readDouble();
            double op2 = in.readDouble();
            double r = 0;

            switch (o) {
                case "+":
                    r = op + op2;
                    break;
                case "-":
                    r = op - op2;
                    break;
                case "*":
                    r = op * op2;
                    break;
                case "/":
                    if (op2 != 0) {
                        r = op / op2;

                    } else {
                        out.writeUTF("ERROR: DIVISION ES CERO");
                        socket.close();
                        continue;
                    }
                    break;
                case "√":
                    if (op >= 0) {
                        r = Math.sqrt(op);
                    } else {
                        out.writeUTF("ERROR: RAIZ CUADARA");
                        socket.close();
                        continue;
                    }
                    break;
            }

            out.writeUTF(String.valueOf(r));
            socket.close();

        }
    }
}
