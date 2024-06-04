package Actividades.Unidad3.ServidorDeLaHora;

import java.io.DataInputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cliente {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost", 500);
        DataInputStream in = new DataInputStream(socket.getInputStream());

        String sTiempo = in.readUTF();
        String cTiempo = new SimpleDateFormat("HH:mm:ss").format(new Date());

        System.out.println("HORA DEL SERVIDOR: " +  sTiempo);
        System.out.println("HORA DEL CLIENTE: " +  cTiempo);


    }
}
