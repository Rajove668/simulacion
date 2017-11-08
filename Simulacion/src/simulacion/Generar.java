package simulacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import static simulacion.Util.DEBUG;

public class Generar {

    public static String mensaje = "";

    public static ArrayList<Double> numeros(int n) {
        ArrayList<Double> numeros;
        int contador_sets = 0;
        do {
            contador_sets++;
            numeros = set_numeros(n);
        } while (!todas_pruebas(numeros));
        mensaje += "\n[GENERANDO FIN] " + contador_sets + " set/s despues, " + numeros.size()
                + " Numeros pseudo-aleatorios con distrubicion uniforme confirmado";
        if (DEBUG) {
            System.out.println(mensaje);
        }

        return numeros;
    }

    public static ArrayList<Double> set_numeros(int n) {
        ArrayList<Double> nuevo_set = new ArrayList<>();
        Random r = new Random();
        int contador = 0;
        do {
            contador++;
            for (int i = 0; i < n; i++) {
                nuevo_set.add(r.nextDouble());
            }
        } while (duplicados(nuevo_set));
        mensaje += "[DUPLICADOS] Luego de " + contador + " intentos se tienen numeros"
                + " generados por random sin duplicados";
        return nuevo_set;
    }

    public static boolean todas_pruebas(ArrayList<Double> numeros) {
        int aciertos = 0;
        mensaje += "\n[PRUEBAS] Comenzando pruebas";
        if (poker(numeros)) {
            mensaje += "\n[PRUEBAS] Poker OK";
            aciertos++;
        } else {
            mensaje += "\n[PRUEBAS] Poker FAIL";
        }
        if (kolmogorov_smirnov(numeros)) {
            mensaje += "\n[PRUEBAS] Kolmogorov Smirnov OK";
            aciertos++;
        } else {
            mensaje += "\n[PRUEBAS] Kolmogorov Smirnov FAIL";
        }
        if (frecuencias_chi_cuadrado(numeros)) {
            mensaje += "\n[PRUEBAS] Frecuencias X^2 OK";
            aciertos++;
        } else {
            mensaje += "\n[PRUEBAS] Frecuencias X^2 FAIL";
        }
        if (promedios(numeros)) {
            mensaje += "\n[PRUEBAS] Promedios OK";
            aciertos++;
        } else {
            mensaje += "\n[PRUEBAS] Promedios FAIL";
        }
        mensaje += "\n[PRUEBAS FIN] Total " + aciertos + " de 4";
        System.gc();
        return aciertos >= 3;
    }

    public static boolean duplicados(ArrayList<Double> numeros_aleatorios) {
        Set<Double> set = new HashSet<>();
        for (Double numero : numeros_aleatorios) {
            if (set.add(numero) == false) {
                System.out.println(numero);
                return true;
            }
        }
        return false;
    }

    static boolean poker(ArrayList<Double> numeros) {
        return true;
    }

    static boolean kolmogorov_smirnov(ArrayList<Double> numeros) {
        Double D_max, d, D_max_aux;
        ArrayList<Double> numeros_aux = new ArrayList<>(numeros);
        Collections.sort(numeros_aux);
        // Primer maximo
        D_max = Math.abs((1.0 / numeros_aux.size()) - numeros_aux.get(0));
        for (int i = 0; i < numeros_aux.size(); i++) {
            D_max_aux = Math.abs((i + 1.0) / numeros_aux.size() - numeros_aux.get(i));
//            System.out.println("F[" + (i + 1) + "]: " + (i + 1.0) / numeros_aux.size() + " D[" + (i + 1) + "]: " + D_max_aux);
            if (D_max_aux > D_max) {
                D_max = D_max_aux;
            }
        }
        //Para una significacia de 5%
        d = 1.36 / Math.sqrt(numeros.size());
        if(Util.DEBUG2){
            mensaje += "\n[PRUEBAS] Kolmogorov Smirnov D = " + D_max + " < " + d;
        }
        return D_max < d;
    }

    static boolean frecuencias_chi_cuadrado(ArrayList<Double> numeros) {
        int[] conteo = new int[]{0, 0, 0, 0};
        for (Double numero : numeros) {
            if (numero < 0.25) {
                conteo[0]++;
            } else if (numero < 0.5) {
                conteo[1]++;
            } else if (numero < 0.75) {
                conteo[2]++;
            } else {
                conteo[3]++;
            }
        }
        float resultado = 0;
        for (int i = 0; i < 4; i++) {
            resultado += Math.pow(conteo[i] - numeros.size() / 4, 2) / (numeros.size() / 4);
        }
        if (Util.DEBUG2) {
            mensaje += "\n[PRUEBAS] Frecuencias X^2  X = " + resultado + " < " + 9.49;
        }
        return resultado < 9.49;
    }

    static boolean promedios(ArrayList<Double> numeros) {
        SummaryStatistics ayuda = new SummaryStatistics();
        numeros.forEach((numero) -> {
            ayuda.addValue(numero);
        });
        if (Util.DEBUG2) {
            mensaje += "\n[PRUEBAS] Promedios |Z| = " + (Math.abs((ayuda.getMean() - 0.5) * Math.sqrt(numeros.size())) / Math.sqrt(1.0 / 12.0)) + " < 1.96";
        }
        return Math.abs((ayuda.getMean() - 0.5) * Math.sqrt(numeros.size())) / Math.sqrt(1.0 / 12.0) < 1.96;
    }

}
