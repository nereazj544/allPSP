package Actividades.Unidad2.BarberoDormilon;

public class Cliente implements Runnable{
    private final int id;
    private final TiendaBarberia tiendaBarberia;

    public Cliente(int id, TiendaBarberia tiendaBarberia) {
        this.id = id;
        this.tiendaBarberia = tiendaBarberia;
    }

    
    @Override
    public void run() {
        tiendaBarberia.entrar(this);
        
    }


    public int getId() {
        return id;
    }

}
