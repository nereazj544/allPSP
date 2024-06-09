package servidor.V2.Server;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UTFDataFormatException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Base64;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.sql.rowset.BaseRowSet;

/**
 * Server
 */
public class Server extends Thread {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    // Constructor
    public Server(Socket socket) throws IOException {
        this.socket = socket;
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        socket.setSoTimeout(5000); // 5s
    }

    // Respuesta que recivira el cliente.
    private void RespuestaAlCliente(String respuesta) {
        System.out.println(socket.getInetAddress() + "-> " + respuesta);
        try {
            out.writeUTF(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Conenctado con <<" + socket.getInetAddress() + ">>");
        try {
            String peticionCliente = in.readUTF();
            switch (peticionCliente) {
                case "Hash":
                    Hash();
                    break;
                case "Cert":
                    Cert();
                    break;
                case "Cifrar":
                    Cifrar();
                    break;

                default:
                    RespuestaAlCliente(String.format("Error: '%s' no se ha reconocido la orden", peticionCliente));
            }
        } catch (SocketTimeoutException e) {
            RespuestaAlCliente("Error: Tiempo de espera agotado.");
        } catch (EOFException e) {
            RespuestaAlCliente("Error: Se esperaba una peticion");
        } catch (IOException e) {
            RespuestaAlCliente("Error: " + e.getLocalizedMessage());
        }
    }

    private void Hash() {
        String algoritmo = null;
        try {
            algoritmo = in.readUTF();
            byte[] texto = in.readAllBytes();

            MessageDigest md = MessageDigest.getInstance(algoritmo);
            if (texto.length == 0) {
                RespuestaAlCliente("Error: se esperaban datos");
            } else {
                byte[] mensajeHash = md.digest(texto);
                String HashB64 = Base64.getEncoder().encodeToString(mensajeHash);
                RespuestaAlCliente("Ok: " + HashB64);
            }

        } catch (SocketTimeoutException e) {
            RespuestaAlCliente("Error: tiempo de espera agotado");
        } catch (NoSuchAlgorithmException e) {
            RespuestaAlCliente("Error: Algoritmo no soportado");
        } catch (IOException e) {
            RespuestaAlCliente("Error: " + e.getLocalizedMessage());
            try {
                socket.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    private void Cert() {
        String alias = "";
        try {
            alias = in.readUTF();
            String B64 = in.readUTF();
            byte[] byteB64 = Base64.getDecoder().decode(B64);

            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);

            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.504");
            Certificate cert = certificateFactory.generateCertificate(new ByteArrayInputStream(byteB64));
            keyStore.setCertificateEntry(alias, cert);

            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(B64.getBytes());
            String hashB64 = Base64.getEncoder().encodeToString(md.digest(byteB64));
            RespuestaAlCliente("OK: " + hashB64);

        } catch (SocketTimeoutException e) {
            RespuestaAlCliente("Error: tiempo de espera agotado");
        } catch (EOFException | UTFDataFormatException e) {
            RespuestaAlCliente("Error: Se esperaba un certicicado o alias valido");

        } catch (IllegalArgumentException e) {
            RespuestaAlCliente("Error: Formato BASE64 invalidp");
        } catch (CertificateException | KeyStoreException | NoSuchAlgorithmException | IOException e) {
            RespuestaAlCliente("Error: " + e.getLocalizedMessage());
            try {
                socket.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    private void Cifrar() {
        try {
            KeyStore keyStore = KeyStore.getInstance("pksc12");
            // keyStore.load(new FileInputStream(System.getProperty("user.dir") +
            // /"res/keysotore.p12"), "pass".toCharArray());

            String alias = in.readUTF();
            byte[] linea = in.readAllBytes();

            Certificate certificate = keyStore.getCertificate(alias);
            if (certificate != null) {
                PublicKey pkKey = certificate.getPublicKey();
                if (pkKey.getAlgorithm().equals("RSA")) {
                    String hash64;
                    try {
                        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                        cipher.init(Cipher.ENCRYPT_MODE, pkKey);

                        byte[] encryptedData = cipher.doFinal(linea);
                        hash64 = Base64.getEncoder().encodeToString(encryptedData);
                        RespuestaAlCliente("OK: " + hash64);
                    } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException
                            | IllegalBlockSizeException | BadPaddingException e) {
                        RespuestaAlCliente("Error: " + e.getLocalizedMessage());
                    }
                } else {
                    RespuestaAlCliente("Error: algoritmo de clave no soportado");
                }
            } else {
                RespuestaAlCliente("Error: Certificado no encontrado");
            }
            /* 
             * 
             String alias = in.readUTF();
             byte[] linea = in.readAllBytes();
             
             Certificate certificate = keyStore.getCertificate(alias);
             if (certificate != null) {
                PublicKey pkKey = certificate.getPublicKey();
                if (pkKey.getAlgorithm().equals("RSA")) {
                    Cipher cipher;
                    try {
                        cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
                        cipher.init(Cipher.ENCRYPT_MODE, keyStore.getCertificate(alias).getPublicKey());

                    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
                        RespuestaAlCliente("Error: en el algoritmo");
                        } catch (InvalidKeyException | KeyStoreException e) {
                        RespuestaAlCliente("Error: " + e.getLocalizedMessage());
                    }
                    
                    // int n = 0;
                    // byte[] b = new byte[256];
                    // boolean aux = false;
                    
                    // try (ByteArrayInputStream ArrayBy = new ByteArrayInputStream(linea)) {
                        //     while ((n = ArrayBy.read(b)) != -1) {
                            //         aux = true;
                            //         byte [] byCipher = cipher.doFinal(linea);
                            //         String hash64 = Base64.getEncoder().encodeToString(byCipher);
                            //         RespuestaAlCliente("OK: " + hash64);
                            
                            //         if (aux == true) {
                                //             RespuestaAlCliente("FIN");
                                //         }
                    //     }
                    // } catch (IllegalBlockSizeException | BadPaddingException e) {
                        //     RespuestaAlCliente("Error: se esperaban datos");
                        // }
                        
                        }
                        }
                        */
                        
        } catch (SocketTimeoutException e) {
            RespuestaAlCliente("Error: tiempo de espera agotado");
        } catch (KeyStoreException | IOException e) {
            RespuestaAlCliente("Error: " + e.getLocalizedMessage());
            try {
                socket.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
    }

    // ** ============================ ** ============================ **
    // !Main
    public static void main(String[] args) {
        int port = 5050;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(60000);
            ExecutorService executorService = Executors.newFixedThreadPool(1000);
            System.out.println("Servidor escuchado en el puerto: " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                new Server(socket).start();
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getLocalizedMessage());
        }
    }
}