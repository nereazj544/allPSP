package Actividades.Unidad3.ServidorEco.data.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
		BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("> ");
		String linea;
		while ((linea = teclado.readLine()) != null) {
			try (Socket socket = new Socket("localhost", 5000)) {
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				out.writeUTF(linea);
				System.out.println(in.readUTF());
			}
		}
    }
}
