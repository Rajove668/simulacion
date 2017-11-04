package simulacion.primera;

import java.util.ArrayList;
import java.util.Collections;
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
//        int horas_penalizacion = 0;1
//        posible solucion
        /*
            La mejor forma de solucionarlo es
            sacar el numero de veces que se reemplaza 1, 2, 3 o 4
            cuando se reemplaza, solo avanzar las cosas de horas, no costos
            al final calcular el costo total CT = #_1*(100 + 1*200) + #_2*(100 + 2*200) + #_3*(100 + 3*200) + #_4*(100*2 + 4*200)
        */
        for (int hora = 1; hora <= 20000; hora++) {
            mensaje += "\n[SIM 5.8] Hora(" + hora + ") ";
            if (politica == 1) {
                // POLITICA 1 Al dañarse, solo se cambia el dañado
                if (hora == 1 && Collections.frequency(componentes, 0) != 0) {
                    // Si viene al menos uno dañado
                    // Detecta si se dañó uno
                    if (Collections.frequency(componentes, 0) == 4) {
                        mensaje += "Cambiando 4 Componentes";
                        hora += 2;
                        costo_total += 100 * 2 + 200 * 4;
                        componentes.clear();
                        componentes.add(nueva_hora());
                        componentes.add(nueva_hora());
                        componentes.add(nueva_hora());
                        componentes.add(nueva_hora());
                    } else if (Collections.frequency(componentes, 0) > 0) {
                        mensaje += "Cambiando " + Collections.frequency(componentes, 0) + " Componentes";
                        hora++;
                        costo_total += 100 + Collections.frequency(componentes, 0) * 200;
                        //Suponiendo que desconectados, el tiempo de vida de los componentes no baja
                        for (int k = 0; k < Collections.frequency(componentes, 0); k++) {
                            componentes.add(nueva_hora());
                        }
                        componentes.removeIf(numero -> {
                            return numero.equals(0);
                        });
                    } else {
                        mensaje += this;
                    }
                }
                // Consume 1 hora a todos
                for (int j = 0; j < 4; j++) {
                    this.componentes.set(j, this.componentes.get(j) - 1);
                }
                // Detecta si se dañó uno
                if (Collections.frequency(componentes, 0) == 4) {
                    mensaje += "Cambiando 4 Componentes";
                    hora += 2;
                    costo_total += 100 * 2 + 200 * 4;
                    componentes.clear();
                    componentes.add(nueva_hora());
                    componentes.add(nueva_hora());
                    componentes.add(nueva_hora());
                    componentes.add(nueva_hora());
                } else if (Collections.frequency(componentes, 0) > 0) {
                    mensaje += "Cambiando " + Collections.frequency(componentes, 0) + " Componentes";
                    hora++;
                    costo_total += 100 + Collections.frequency(componentes, 0) * 200;
                    //Suponiendo que desconectados, el tiempo de vida de los componentes no baja
                    for (int k = 0; k < Collections.frequency(componentes, 0); k++) {
                        componentes.add(nueva_hora());
                    }
                    componentes.removeIf(numero -> {
                        return numero.equals(0);
                    });
                } else {
                    mensaje += this;
                }
            } else {
                // POLITICA 2 Al dañarse, se cambian todos
            }
        }

        int hora = 0;

        if (Util.DEBUG) {
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
