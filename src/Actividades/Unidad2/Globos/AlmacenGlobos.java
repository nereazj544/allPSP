package Actividades.Unidad2.Globos;

import static Actividades.Unidad2.Globos.Main.actualizar;

public class AlmacenGlobos {
    private int Globos = 10;
    private int GlobosEn = 0;

    public synchronized int enG() {
        if (GlobosEn < Globos) {
            GlobosEn++;
            actualizar("GLOBO " + GlobosEn + " ENTREGADO\n");
            return GlobosEn;
        }

        return -1;
    }

}
