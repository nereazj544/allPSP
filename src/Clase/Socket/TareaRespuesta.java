package Clase.Socket;

import java.io.BufferedReader;
import java.io.EOFException;
// import java.io.DataInput;
// import java.io.DataInputStream;
// import java.io.DataOutput;
// import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
// import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


//! https://github.com/pspFleming/clienteservidorecho
public class TareaRespuesta implements Runnable{

    private Socket socket;

    public TareaRespuesta(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        //! La parte respuesta lo que tiene que hacer es recibir una cadena (String), la envia y cierra la conexion.
        System.out.println("Perdicion de " + socket.getInetAddress()); //Pticion del cliente
        try (BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))
            //DataInputStream in = new DataInputStream(socket.getInputStream());
            //DataOutputStream out = new DataOutputStream(socket.getOutputStream())
        ) {
            String s; 
            while ((s = bf.readLine()) != null) {
                //DETECTA EL FIN DE FICHERO ⤴️
                System.out.println(s);
                pw.println(s);
                pw.flush();
            }
            
            //! UTF = Metodo que sirve para ver y el otro para enviar
            //! Con Data se lanza una excepcion, donde haces un while
            // while (true) {
            //  try {
            //         s = in.readUTF();
            //         out.writeUTF(s);
            //         System.out.println(s);
            //  }catch (EOFException e) {
            //     break;
            //  } 
            //  }
            

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
