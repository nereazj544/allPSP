package Clase.Scoket.Modificacion_serv_echo.V4.Server;

import java.io.IOException;

public class Emisor extends Thread {
    private Almacen almacen;

    public Emisor( Almacen almacen) {
        this.almacen = almacen;
    }

    @Override
    public void run() {
        while (true) {
            Items i = almacen.retirar();
            try {
                i.getD().writeUTF(i.getS());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
