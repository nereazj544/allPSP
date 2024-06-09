package servidor.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Base64;

public class ClientePruebas {

    static KeyStore ks;

    public static void main(String[] args) {
        try {
            ks = KeyStore.getInstance(KeyStore.getDefaultType());
            ks.load(ClientePruebas.class.getResourceAsStream("/keystore.p12"), "practicas".toCharArray());

            System.out.println("Realizando pruebas...");
            realizarPruebas();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void realizarPruebas() throws IOException, KeyStoreException {
        System.out.println("Prueba 01...");
        testHash();
        System.out.println("Prueba 02...");
        testCert();
        System.out.println("Prueba 03...");
        testCifrar();
    }

    static void testHash() throws IOException {
        try (Socket socket = new Socket("localhost", 5050)) {
            socket.setSoTimeout(10000);

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("hash");
            out.writeUTF("SHA-256");
            out.write("Mensaje de prueba".getBytes());

            String respuesta = new DataInputStream(socket.getInputStream()).readUTF();
            System.out.println(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void testCert() throws IOException, KeyStoreException {
        try (Socket socket = new Socket("localhost", 5050)) {
            socket.setSoTimeout(10000);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("cert");
            
            String alias = "psp";
            int pas = 123;
            Certificate cert = ks.getCertificate(alias);
            if (cert != null) {
                String certificadoBase64 = Base64.getEncoder().encodeToString(cert.getEncoded());
    
                out.writeUTF(alias);
                out.writeUTF(Integer.toString(pas));
                out.writeUTF(certificadoBase64);
    
                String respuesta = new DataInputStream(socket.getInputStream()).readUTF();
                System.out.println(respuesta);
            } else {
                System.out.println("Certificate with alias '" + alias + "' not found in the keystore.");
            }
        } catch (IOException | CertificateEncodingException e) {
            e.printStackTrace();
        }
    }
    static void testCifrar() throws IOException {
        try (Socket socket = new Socket("localhost", 5050)) {
            socket.setSoTimeout(10000);

            String alias = "psp";
            String texto = "Mensaje secreto";
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF("cifrar");
            out.writeUTF(alias);
            out.write(texto.getBytes());
            socket.shutdownOutput();

            DataInputStream in = new DataInputStream(socket.getInputStream());
            String respuesta = in.readUTF();
            System.out.println(respuesta);
            if(respuesta.startsWith("Ok:")) {
                String mensajeCifrado = respuesta.substring(4);
                System.out.println("Mensaje cifrado: " + mensajeCifrado);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
