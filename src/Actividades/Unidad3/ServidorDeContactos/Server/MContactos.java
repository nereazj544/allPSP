package Actividades.Unidad3.ServidorDeContactos.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MContactos implements Runnable{
    private Socket socket;
    private Gestion g;

    public MContactos(Socket socket, Gestion g) {
        this.socket = socket;
        this.g = g;
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
            while (true) {
                String linea = in.readUTF();
                String respuesta = Peticion(linea);
                out.writeUTF(respuesta);
            }
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private String Peticion(String linea) {
        String [] contact = linea.split(":", 2);
        if (contact.length < 2) {
            return "ERROR3\n" + linea + "\n" +"^";
        }
        
        String cmd  = contact[0].trim();
        String arg  = contact[1].trim();
        Gestion g = new Gestion();
        
        switch (cmd) {
            case "nombre":
                return procesar(arg);
            case "buscar":
                return g.buscar(arg);
            case "eliminar":
                return g.borrar(arg);
            case "contactos":
                return g.lista();
        
            default:
            return "ERROR3\n" + linea + "\n" +"^";
        }
        
    }

    private String procesar(String arg) {
        if (arg == null || !arg.contains(":")) {
            return "ERROR3\n" + arg + "\n" +"^";
            
        }
        String [] contact = arg.split(":", 2);
        if (contact.length < 2) {
            return "ERROR3\n" + arg + "\n" +"^";
        }

        String nombre = contact[0].trim();
        String telefono = contact[1].trim();
        if (!Numero(telefono)) {
            return "ERROR3\n" + arg + "\n" +"^";
        }

        return g.agregar(nombre, telefono);

    }

    private boolean Numero(String telefono) {
       return telefono.chars().allMatch(Character::isDigit);
    }

    

}
