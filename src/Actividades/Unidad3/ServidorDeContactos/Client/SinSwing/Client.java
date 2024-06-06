package Actividades.Unidad3.ServidorDeContactos.Client.SinSwing;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;


public class Client {
    public static void main(String[] args) {
        try (BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))){
            System.out.println("> Nota del servidor: Para añadir contactos estrucutra: nombre:x:nº");
            String l;
            System.out.print("> ");
            String linea;
		while ((linea = teclado.readLine()) != null) {
			try (Socket socket = new Socket("localhost", 500)) {
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				out.writeUTF(linea);
				String r = in.readUTF();
                System.out.println(r);
            }
        }
        System.out.println("> ");
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}
