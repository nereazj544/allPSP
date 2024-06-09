package servidor.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class Cliente {
    private final String host;
    private final int port;
    private DataOutputStream out;
    private DataInputStream in;
    private Socket socket;

    public Cliente(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() throws IOException {
        socket = new Socket(host, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
    }

    public void disconnect() throws IOException {
        socket.close();
    }

    public String sendHashRequest(String algorithm, byte[] data) throws IOException {
        out.writeUTF("Hash");
        out.writeUTF(algorithm);
        out.write(data);
        return in.readUTF();
    }

    public String sendCertRequest(String alias, X509Certificate certificate) throws IOException, CertificateEncodingException {
        out.writeUTF("Certificado");
        out.writeUTF(alias);
        String certBase64 = Base64.getEncoder().encodeToString(certificate.getEncoded());
        out.writeUTF(certBase64);
        return in.readUTF();
    }

    public String sendEncryptRequest(String alias, byte[] data) throws IOException {
        out.writeUTF("Cifrar");
        out.writeUTF(alias);
        out.write(data);
        return in.readUTF();
    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente("localhost", 5050);

        try {
            cliente.connect();
            
            // Ejemplo de solicitud de hash
            String hashResponse = cliente.sendHashRequest("SHA-256", "datos para hash".getBytes());
            System.out.println("Respuesta Hash: " + hashResponse);

            // Ejemplo de solicitud de certificado
            KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(null, null);
            X509Certificate cert = (X509Certificate) ks.getCertificate("miAlias"); // Asegúrate de tener el certificado cargado
            if (cert != null) {
                String certResponse = cliente.sendCertRequest("miAlias", cert);
                System.out.println("Respuesta Cert: " + certResponse);
            } else {
                System.out.println("Certificado no encontrado en el KeyStore.");
            }

            // Ejemplo de solicitud de cifrado
            String encryptResponse = cliente.sendEncryptRequest("miAlias", "datos para cifrar".getBytes());
            System.out.println("Respuesta Cifrado: " + encryptResponse);

            cliente.disconnect();
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }
    }
}
