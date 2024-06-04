package Clase.Socket.Servidor_Echo;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Clienteeco {

    /**
     * 
     * * Socket (host:"" , port:)
     * * el host se puede poner con una direccion ip o si es al propio (ordenador) se pone "localhost" 
     * 
     */

    public static void main(String[] args) throws IOException {
        BufferedReader keyboartdInt = new BufferedReader(new InputStreamReader(System.in));
        String liena;
        System.out.println("> ");
        while ((liena = keyboartdInt.readLine()) != null) {
            Socket socket = new Socket("127.0.0.1", 9999);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                out.println(liena);
                out.flush();
                System.out.println(in.readLine());
                System.out.println("> ");

            }
        }
    }
}
