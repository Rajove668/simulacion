package simulacion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
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
        mensaje += "\n-------------------------------------------------------\n"
                + "Luego de trabajar con " + contador_sets + " sets\n"
                + "Tengo " + numeros.size() + " Numeros pseudo-aleatorios con"
                + " distrubicion uniforme generados\n";
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
        mensaje += "[GENERANDO] Luego de " + contador + " intentos se tienen numeros"
                + " generados por random sin duplicados";
        return nuevo_set;
    }

    public static boolean todas_pruebas(ArrayList<Double> numeros) {
        int aciertos = 0;
        mensaje += "\n[PRUEBAS] Comenzando pruebas";
        if (Pruebas.poker(numeros)) {
            mensaje += "\n[PRUEBAS] Poker OK";
            aciertos++;
        } else {
            mensaje += "\n[PRUEBAS] Poker FAIL";
        }
        if (Pruebas.kolmogorov_smirnov(numeros)) {
            mensaje += "\n[PRUEBAS] Kolmogorov Smirnov OK";
            aciertos++;
        } else {
            mensaje += "\n[PRUEBAS] Kolmogorov Smirnov FAIL";
        }
        if (Pruebas.frecuencias_chi_cuadrado(numeros)) {
            mensaje += "\n[PRUEBAS] Frecuencias X^2 OK";
            aciertos++;
        } else {
            mensaje += "\n[PRUEBAS] Frecuencias X^2 FAIL";
        }
        if (Pruebas.promedios(numeros)) {
            mensaje += "\n[PRUEBAS] Promedios OK";
            aciertos++;
        } else {
            mensaje += "\n[PRUEBAS] Promedios FAIL";
        }
        mensaje += "\n[PRUEBAS] Total " + aciertos + " de 4";
        return aciertos >= 3;
    }

    public static boolean duplicados(ArrayList<Double> numeros_aleatorios) {
        Set<Double> dupes = new HashSet<>(numeros_aleatorios);
        return dupes.size() != numeros_aleatorios.size();
//        for (Double numero : dupes) {
//            if (Collections.frequency(numeros_aleatorios, numero) > 1) {
//                System.out.println(numero + " - " + Collections.frequency(numeros_aleatorios, numero));
//                //return true;
//            }
//        }
//        return false;
    }

}
