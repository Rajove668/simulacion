package simulacion.primera;

import java.util.ArrayList;
import static simulacion.Main.un_numero_aleatorio;
import simulacion.Util;

public class Empresa {

    public float costo_total = (float) 0.0;
    private final int politica;
    private ArrayList<Integer> componentes = new ArrayList<>();
    private String mensaje = "";

    public Empresa(int politica) {
        this.politica = politica;
        componentes.add(nueva_hora());
        componentes.add(nueva_hora());
        componentes.add(nueva_hora());
        componentes.add(nueva_hora());
        mensaje += "[SIM 5.8] " + this;
    }

    public void simular() {
        int hora = 1;
        while(hora<=20000){
            // Si no hay que cambiar, hora+=(min de horas entre los componentes)
            // y restar esa cantidad a cada componente para llevar a 0
            // Si hay que cambiar, elegir entra las dos politicas
                // P1  
        }
        if(Util.DEBUG){
            System.out.println(mensaje);
        }
    }

    private Integer nueva_hora() {
        Integer MEDIA = 600;
        Integer DESVIACION_ESTANDAR = 100;
        Double sum = 0.0;
        for (int i = 0; i < 12; i++) {
            sum += un_numero_aleatorio();
        }
        return Math.round((float) (MEDIA + DESVIACION_ESTANDAR * (sum - 6)));
    }

    @Override
    public String toString() {
        return "Empresa[P" + politica + "] = " + componentes + " C.T " + costo_total;
    }

}
