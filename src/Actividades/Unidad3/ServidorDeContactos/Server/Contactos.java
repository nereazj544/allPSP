package Actividades.Unidad3.ServidorDeContactos.Server;

import java.util.HashSet;
import java.util.Set;

public class Contactos {
    private String nombre;
    private Set <String> telefonos;
    
    public Contactos(String nombre) {
        this.nombre = nombre;
        this.telefonos = new HashSet<>();
    }

    public String getNombre() {
        return nombre;
    }

    public Set<String> getTelefonos() {
        return telefonos;
    }
    
    public boolean agregar(String tfl){
        return telefonos.add(tfl);
    }
}
