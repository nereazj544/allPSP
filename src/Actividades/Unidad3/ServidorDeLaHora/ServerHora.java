package Actividades.Unidad3.ServidorDeLaHora;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ServerHora {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(500);

        while (true) {
            Socket socket = serverSocket.accept();
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String tiempo = new SimpleDateFormat("HH:mm:ss").format(new Date());
            out.writeUTF(tiempo);
            socket.close();
        }
    }
}
