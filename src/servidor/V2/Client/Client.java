package servidor.V2.Client;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Base64;

public class Client {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public Client(String address, int port) throws IOException {
        socket = new Socket(address, port);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
    }

    public void enviarComando(String comando) throws IOException {
        out.writeUTF(comando);
        System.out.println("Comando enviado: " + comando);
    }

    public void enviarDatos(byte[] datos) throws IOException {
        out.write(datos);
    }

    public void enviarTexto(String texto) throws IOException {
        out.writeUTF(texto);
    }

    public String recibirRespuesta() throws IOException {
        return in.readUTF();
    }

    public void cerrarConexion() throws IOException {
        in.close();
        out.close();
        socket.close();
    }

    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 5050);

            // Prueba del comando Hash
            client.enviarComando("Hash");
            client.enviarTexto("SHA-256");
            client.enviarDatos("Texto a hash".getBytes());
            System.out.println("Respuesta del servidor: " + client.recibirRespuesta());

            // Prueba del comando Cert
            client.enviarComando("Cert");
            client.enviarTexto("miAlias");

            // Cargar certificado desde archivo
            FileInputStream fis = new FileInputStream("certificado.cer");
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate cert = cf.generateCertificate(fis);
            String certB64 = Base64.getEncoder().encodeToString(cert.getEncoded());
            client.enviarTexto(certB64);
            System.out.println("Respuesta del servidor: " + client.recibirRespuesta());

            // Prueba del comando Cifrar
            client.enviarComando("Cifrar");
            client.enviarTexto("miAlias");
            client.enviarDatos("Texto a cifrar".getBytes());
            System.out.println("Respuesta del servidor: " + client.recibirRespuesta());

            client.cerrarConexion();

        } catch (IOException | java.security.cert.CertificateException e) {
            e.printStackTrace();
        }
    }
}

