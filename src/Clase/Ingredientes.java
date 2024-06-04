package Clase;

import java.util.Random;

//! Valores random
public enum Ingredientes {
    CERILLAS, PAPEL;

    Random r = new Random();
    public Ingredientes get(){
        return Ingredientes.values()[r.nextInt(Ingredientes.values().length)]    ;
    }
}
