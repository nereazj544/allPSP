package Actividades.Unidad3.ConexionURLHTTP;

import java.io.*;
import java.net.*;

public class TareaDecargarImg implements Runnable{
    private Socket socket;



    public TareaDecargarImg(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String url = in.readUTF();
            URL img = new URL(url);
            String link = url.substring(url.lastIndexOf("/") + 1);

            InputStream i = img.openStream();
            OutputStream o = new FileOutputStream(link);

            byte [] b = new byte[2048];
            int l;

            while ((l = i.read(b)) != -1) {
                o.write(b, 0, l);
            }

            i.close();
            o.close();
            out.writeUTF("IMAGEN DESCARGADA: " + link);
            

        }catch(IOException e){}
    }
    
}
