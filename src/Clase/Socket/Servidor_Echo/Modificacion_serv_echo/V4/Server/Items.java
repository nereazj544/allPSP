package Clase.Scoket.Modificacion_serv_echo.V4.Server;


import java.io.DataOutputStream;

public class Items {
    private String s;
    private DataOutputStream d;

    public Items(String s, DataOutputStream d) {
        this.s = s;
        this.d = d;
    }

    public String getS() {
        return s;
    }
    public DataOutputStream getD() {
        return d;
    }    
}
