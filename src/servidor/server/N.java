package servidor.server;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UTFDataFormatException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class N implements Runnable {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private KeyStore kStore;

    // Constructor
    public N(Socket socket, KeyStore kStore) throws IOException {
        // Lo que simpre hay que poner:
        this.socket = socket;
        this.kStore = kStore;

        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        // terminamos el tiempo de espera del socket (5 s)
        socket.setSoTimeout(5000);
    }

    // Para enviar las respuestas al cliente.
    private void Respuesta(String respuesta) {
        System.out.println(socket.getInetAddress() + " -> " + respuesta);
        try {
            out.writeUTF(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Conectado con: '" + socket.getInetAddress() + "'");
        try {
            String respuestaCliente = in.readUTF(); // Leemos la respuesta del cliente.
            Respuesta(respuestaCliente);

        } catch (SocketTimeoutException e) {
            Respuesta("ERROR: Tiempo de espera agotado.");
        } catch (EOFException e) {
            Respuesta("ERROR: Se esperaba una peticion.");
        } catch (IOException e) {
            Respuesta("ERROR: " + e.getLocalizedMessage());
        // } finally {
        //     try {
        //         socket.close(); // Se cierra el socket
        //     } catch (IOException e) {
        //         // TODO Auto-generated catch block
        //         e.printStackTrace();
        //     }
        }
    }

    private void R(String r) {
        switch (r) {
            case "Hash":
                Hash();
                break;
            case "Certificado":
                Certificado();
                break;
            case "Cifrar":
                Cifrar();
                break;

            default:
                Respuesta(String.format("Error: '%s' no se ha reconocido la peticon", r));
        }
    }

    private void Cifrar() {
        try {
            kStore = KeyStore.getInstance("pksc12");
            kStore.load(new FileInputStream(System.getProperty("user.dir") + "/res/keystore.p12"), "".toCharArray());
            
            String alias = in.readUTF();
            byte[] data = in.readAllBytes();


            Certificate cert =kStore.getCertificate(alias);
            // Obtenemos el certificado

            if (cert != null) {
                PublicKey kPublicKey = cert.getPublicKey();
                if (kPublicKey.getAlgorithm().equals("RSA")) {
                    //! CIPHER
                    Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                    cipher.init(Cipher.ENCRYPT_MODE, kPublicKey);
                    
                    byte[] e = cipher.doFinal(data); //Ciframos los datos
                    String eBS64 = Base64.getEncoder().encodeToString(e);
                    Respuesta("Ok: " + eBS64);
                }else{
                    Respuesta("Error: algoritmo de clave no soportada");
                }
            }else{
                Respuesta("Error: Certificado no encontrado");
            }
        } catch (SocketTimeoutException e) {
            Respuesta("Error: Tiempo de espera agotado");
        } catch (KeyStoreException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | CertificateException  e) {
            Respuesta(e.getLocalizedMessage());
        }catch(BadPaddingException | IllegalBlockSizeException e){
            Respuesta("Error: en el cifrado");
        }catch(IOException e){
            Respuesta("Error: " + e.getLocalizedMessage());
        }
    }

    private void Certificado() {
        String alias = "";
        try {
            alias = in.readUTF();
            String certBS64 = in.readUTF(); // Con esto se lee el formato
            byte[] cByte = Base64.getDecoder().decode(certBS64);

            kStore = KeyStore.getInstance(KeyStore.getDefaultType());
            kStore.load(null);

            CertificateFactory cFactory = CertificateFactory.getInstance("X.509");
            Certificate cert = cFactory.generateCertificate(new ByteArrayInputStream(cByte));
            kStore.setCertificateEntry(alias, cert); // Almacenamos el cert

            MessageDigest md = MessageDigest.getInstance(certBS64);
            md.update(certBS64.getBytes());
            String hashBS64 = Base64.getEncoder().encodeToString(md.digest());
            Respuesta("OK: " + hashBS64);

        } catch (SocketTimeoutException e) {
            Respuesta("Error: Tiempo de espera agotado");
        } catch (EOFException | UTFDataFormatException e) {
            Respuesta("Error: Se esperaba un certificado o alias valido");
        } catch (IllegalArgumentException e) {
            Respuesta("Error: Fromato Base65 invalido");
        } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException e) {
            Respuesta("Error: " + e.getLocalizedMessage());
        } catch (IOException e) {
            Respuesta("Error: " + e.getLocalizedMessage());
            
        }
    }

    private void Hash() {
        try {
            String algoritmo = in.readUTF();
            MessageDigest md = MessageDigest.getInstance(algoritmo);
            byte[] data = in.readAllBytes();

            if (data.length == 0) {
                Respuesta("Error: Se esperaban datos");
            } else {
                byte[] hash = md.digest(data); // Generamos el hash de los datos
                String encodeHash = Base64.getEncoder().encodeToString(hash); // Se codifica el hash
                Respuesta("Ok: " + encodeHash);
            }

        } catch (SocketTimeoutException e) {
            Respuesta("ERROR: Tiempo de espera agotado.");
        } catch (NoSuchAlgorithmException e) {
            Respuesta("Error: Algoritmo no soportado");
        } catch (IOException e) {
            Respuesta("Error: " + e.getLocalizedMessage());
            
        }
    }

    // END CLASS N
}
