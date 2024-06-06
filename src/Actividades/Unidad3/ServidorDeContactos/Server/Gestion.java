package Actividades.Unidad3.ServidorDeContactos.Server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Gestion
 */
public class Gestion {
    private Map<String, Contactos> conta;

    public Gestion() {
        this.conta = Collections.synchronizedMap(new HashMap<>());
    }

    public synchronized String agregar(String nombre, String telefono) {
        Contactos contacto = conta.get(nombre);
        if (conta == null) {
            contacto = new Contactos(nombre);
            conta.put(nombre, contacto);
        }
        if (contacto.agregar(telefono)) {
            return "ok";
        } else {
            return "ERROR1";
        }

    }

    public synchronized String buscar(String nombre) {
        Contactos contactos = conta.get(nombre);
        if (conta == null) {
            return "Error2";
        }
        StringBuilder sb = new StringBuilder("ok");
        for (String telefono : contactos.getTelefonos()) {
            sb.append("\n").append(telefono);
        }

        return sb.toString();
    }

    public synchronized String borrar(String nombre) {
        if (conta.remove(nombre) != null) {
            return "Ok";
        } else {
            return "ERROR2";
        }
    }

    public synchronized String lista() {
        if (conta.isEmpty()) {
            return "OK";
        }

        StringBuilder sb = new StringBuilder("OK");
        conta.keySet().stream().sorted()
                .forEach(nombre -> sb.append("\n").append(nombre));
        return sb.toString();

    }
}