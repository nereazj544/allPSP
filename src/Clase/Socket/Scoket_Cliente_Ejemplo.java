package Clase.Socket;

import java.io.BufferedReader;
// import java.io.DataInputStream;
// import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Scoket_Cliente_Ejemplo {
    public static void main(String[] args) throws IOException {
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Escribe: ");
        String linea = teclado.readLine();

        while (linea != null) {
            try (Socket socket = new Socket("localhost", 5000)){
                BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                while(linea != null && !linea.equalsIgnoreCase("fin")){

                    pw.print(linea);
                    pw.flush();
    
                    System.out.println(bf.readLine());
                    System.out.print(">");
                    linea = teclado.readLine();
                    pw.close();
                }
                socket.shutdownOutput();
                while ((linea = bf.readLine()) != null) {
                    System.out.println(linea);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

