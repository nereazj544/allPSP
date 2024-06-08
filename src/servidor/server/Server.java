package servidor.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Server
 */
public class Server {
	static KeyStore kStore;
	public static void main(String[] args) throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
		
		//KEYSTORE
		kStore = KeyStore.getInstance(KeyStore.getDefaultType());
		kStore.load(null, null);
		
		try (ServerSocket serverSocket = new ServerSocket(5000)) {
			serverSocket.setSoTimeout(60000);

			ExecutorService executor = Executors.newFixedThreadPool(1000);
			System.out.println("Servidor ECHO escuchando en puerto 5000");
			while (true) {
				Socket socket = serverSocket.accept();
				executor.submit(new N(socket));
			}
		}
	}

}