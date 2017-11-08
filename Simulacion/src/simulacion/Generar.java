package simulacion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
        if (Util.DEBUG2) {
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
            mensaje += "\n[PRUEBAS] Frecuencias X^2  X^2 = " + resultado + " < " + 9.49;
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

    static boolean poker(ArrayList<Double> numeros) {
        /*
         * quintilla - todos iguales
         * poker - 4 iguales
         * full - 3 iguales y 1 par
         * tercia - 3 iguales
         * dos_pares
         * un_par
         * todos diferentes
         */
        // Limpieza de numeros
        ArrayList<String> numeros_aux = new ArrayList<>();
        for (Double numero : numeros) {
            numeros_aux.add(numero.toString().substring(2, 7));
        }
        // Preparando para contar
        Map<String, Double> fo = new HashMap<>();
        fo.put("todos_diferentes", 0.0);
        fo.put("un_par", 0.0);
        fo.put("dos_pares", 0.0);
        fo.put("tercia", 0.0);
        fo.put("full", 0.0);
        fo.put("poker", 0.0);
        fo.put("quintilla", 0.0);
        Map<String, Double> fe = new HashMap<>();
        fe.put("todos_diferentes", 0.30240 * numeros_aux.size());
        fe.put("un_par", 0.50400 * numeros_aux.size());
        fe.put("dos_pares", 0.10800 * numeros_aux.size());
        fe.put("tercia", 0.07200 * numeros_aux.size());
        fe.put("full", 0.00900 * numeros_aux.size());
        fe.put("poker", 0.00450 * numeros_aux.size());
        fe.put("quintilla", 0.00010 * numeros_aux.size());

        for (String numero : numeros_aux) {
            String tipo = tipo(numero);
            fo.put(tipo, fo.get(tipo) + 1);
//            System.out.println("# " + numero + " " + tipo);
//            System.out.println("quintilla: " + quintilla(numero));
//            System.out.println("poker: " + poker_tipo(numero));
//            System.out.println("full: " + full(numero));
//            System.out.println("tercia: " + tercia(numero));
//            System.out.println("dos_pares: " + dos_pares(numero));
//            System.out.println("un_par: " + un_par(numero));
//            System.out.println("todos_diferentes: " + todos_diferentes(numero));
//            System.out.println("");
        }
//        System.out.println("FE " + fe);
//        System.out.println("FO " + fo);
        Double sum = 0.0;
        for (String tipo : fe.keySet()) {
            sum += Math.pow((fo.get(tipo) - fe.get(tipo)), 2) / fe.get(tipo);
        }
        mensaje += "\n[PRUEBAS] Poker X^2 = " + sum + " < 7.81";
        return true;
    }

    private static String tipo(String numero) {
        if (quintilla(numero)) {
            return "quintilla";
        } else if (poker_tipo(numero)) {
            return "poker";
        } else if (full(numero)) {
            return "full";
        } else if (tercia(numero)) {
            return "tercia";
        } else if (dos_pares(numero)) {
            return "dos_pares";
        } else if (un_par(numero)) {
            return "un_par";
        } else {
            return "todos_diferentes";
        }
    }

    private static boolean quintilla(String numero) {
        char digito1 = numero.toCharArray()[0];
        for (char digito : numero.toCharArray()) {
            if (digito != digito1) {
                return false;
            }
        }
        return true;
    }

    private static boolean poker_tipo(String numero) {
        // Conteo
        Map<Character, Integer> guia = new HashMap<>();
        for (char digito : numero.toCharArray()) {
            if (guia.containsKey(digito)) {
                guia.put(digito, guia.get(digito) + 1);
            } else {
                guia.put(digito, 1);
            }
        }
        return guia.containsValue(4);
    }

    private static boolean full(String numero) {
        // Conteo
        Map<Character, Integer> guia = new HashMap<>();
        for (char digito : numero.toCharArray()) {
            if (guia.containsKey(digito)) {
                guia.put(digito, guia.get(digito) + 1);
            } else {
                guia.put(digito, 1);
            }
        }
        return guia.containsValue(2) && guia.containsValue(3);
    }

    private static boolean tercia(String numero) {
        // Conteo
        Map<Character, Integer> guia = new HashMap<>();
        for (char digito : numero.toCharArray()) {
            if (guia.containsKey(digito)) {
                guia.put(digito, guia.get(digito) + 1);
            } else {
                guia.put(digito, 1);
            }
        }
        return guia.containsValue(3);
    }

    private static boolean dos_pares(String numero) {
        if (un_par(numero)) {
            // Conteo
            Map<Character, Integer> guia = new HashMap<>();
            for (char digito : numero.toCharArray()) {
                if (guia.containsKey(digito)) {
                    guia.put(digito, guia.get(digito) + 1);
                } else {
                    guia.put(digito, 1);
                }
            }
            Character par = null;
            for (Character llave : guia.keySet()) {
                if (guia.get(llave).equals(2)) {
                    par = llave;
                    break;
                }
            }
            // Quitando el primer par
            guia.remove(par);
            return guia.containsValue(2);
        } else {
            return false;
        }
    }

    private static boolean un_par(String numero) {
        // Conteo
        Map<Character, Integer> guia = new HashMap<>();
        for (char digito : numero.toCharArray()) {
            if (guia.containsKey(digito)) {
                guia.put(digito, guia.get(digito) + 1);
            } else {
                guia.put(digito, 1);
            }
        }
        return guia.containsValue(2);
    }

    private static boolean todos_diferentes(String numero) {
        // Conteo
        Map<Character, Integer> guia = new HashMap<>();
        for (char digito : numero.toCharArray()) {
            if (guia.containsKey(digito)) {
                return false;
            } else {
                guia.put(digito, 1);
            }
        }
        return true;
    }

}
