package Actividades.Unidad3.ConexionURLHTTP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("INTRODUCE LA URL DE LA IMAGEN A DESCARGAR: ");

        String link = sc.nextLine();

        try (Socket socket = new Socket("localhost", 500)) {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(link);
            System.out.println(in.readUTF());
            
        } catch (Exception e) {
            // TODO: handle exception
        }

    }
}
