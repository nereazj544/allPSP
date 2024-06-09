package servidor.V1.client;



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
    private KeyStore kStore;

    public Client(String address, int port, String keystorePath, String keystorePassword) {
        try {
            // Conectar al servidor
            socket = new Socket(address, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Conectado al servidor");

            // Inicializar el KeyStore
            kStore = KeyStore.getInstance("PKCS12");
            kStore.load(new FileInputStream(keystorePath), keystorePassword.toCharArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Enviar un comando al servidor
    private void sendCommand(String command) throws IOException {
        out.writeUTF(command);
        System.out.println("Comando enviado: " + command);
    }

    // Leer la respuesta del servidor
    private void readResponse() throws IOException {
        String response = in.readUTF();
        System.out.println("Respuesta del servidor: " + response);
    }

    // Enviar datos para cifrar
    public void sendEncryptRequest(String alias, byte[] data) throws Exception {
        sendCommand("Cifrar");
        out.writeUTF(alias);
        out.write(data);
        readResponse();
    }

    // Enviar un certificado al servidor
    public void sendCertificate(String alias, String certPath) throws Exception {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        FileInputStream fis = new FileInputStream(certPath);
        Certificate cert = cf.generateCertificate(fis);
        fis.close();
        
        String certBS64 = Base64.getEncoder().encodeToString(cert.getEncoded());
        
        sendCommand("Certificado");
        out.writeUTF(alias);
        out.writeUTF(certBS64);
        readResponse();
    }

    // Enviar datos para generar un hash
    public void sendHashRequest(String algorithm, byte[] data) throws Exception {
        sendCommand("Hash");
        out.writeUTF(algorithm);
        out.write(data);
        readResponse();
    }

    public void close() throws IOException {
        socket.close();
    }

    public static void main(String[] args) {
        // Especificar la ruta al KeyStore y la contraseña
        String keystorePath = System.getProperty("user.dir") + "/res/keystore.p12";
        String keystorePassword = ""; // Especifica la contraseña del KeyStore si la hay
        
        Client client = new Client("localhost", 5050, keystorePath, keystorePassword);
        try {
            // Ejemplo de uso: Enviar una petición de hash
            String algorithm = "SHA-256";
            byte[] data = "Datos para hash".getBytes();
            client.sendHashRequest(algorithm, data);

            // Ejemplo de uso: Enviar una petición para cifrar
            String alias = "myalias";
            byte[] encryptData = "Datos para cifrar".getBytes();
            client.sendEncryptRequest(alias, encryptData);

            // Ejemplo de uso: Enviar un certificado
            String certPath = System.getProperty("user.dir") + "/res/certificado.crt";
            client.sendCertificate(alias, certPath);

            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
