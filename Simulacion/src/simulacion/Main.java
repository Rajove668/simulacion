package simulacion;

import edu.rit.util.Random;
import java.util.ArrayList;
import static simulacion.Generar.duplicados;

public class Main {

    public static void main(String[] args) {
        int N_ALEATORIOS = 50000000;
        long tiempoInicio = System.nanoTime();
        
        ArrayList<Double> numeros_aleatorios = Generar.numeros(N_ALEATORIOS);
        
        long tiempoEstimado = System.nanoTime() - tiempoInicio;
        System.out.println(tiempoEstimado/1000000000.0 + "s");
//        numeros_aleatorios = otra_libreria(N_ALEATORIOS);
//        numeros_aleatorios = java(N_ALEATORIOS);
    }

    public static ArrayList<Double> otra_libreria(int cantidad) {
        long startTime = System.nanoTime();
        ArrayList<Double> nuevo_set;
        Random r = Random.getInstance(1234L);
        nuevo_set = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            nuevo_set.add(r.nextDouble());
        }
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("SET HECHO en " + estimatedTime / 1000000000 + "s");
        startTime = System.nanoTime();
        System.out.println(duplicados(nuevo_set));
        estimatedTime = System.nanoTime() - startTime;
        System.out.println("dubplicados en " + estimatedTime / 1000000000 + "s");
        return nuevo_set;
    }

    public static ArrayList<Double> java(int cantidad) {
        long startTime = System.nanoTime();
        ArrayList<Double> nuevo_set = new ArrayList<>();
        java.util.Random r = new java.util.Random();
        for (int i = 0; i < cantidad; i++) {
            nuevo_set.add(r.nextDouble());
        }
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("");
        System.out.println("SET HECHO en " + estimatedTime / 1000000000 + "s");
        startTime = System.nanoTime();
        System.out.println(duplicados(nuevo_set));
        estimatedTime = System.nanoTime() - startTime;
        System.out.println("dubplicados en " + estimatedTime / 1000000000 + "s");
        return nuevo_set;
    }

}
