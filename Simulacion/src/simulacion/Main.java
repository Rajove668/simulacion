package simulacion;

import edu.rit.util.Random;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import static simulacion.Generar.duplicados;
import simulacion.primera.Empresa;
import simulacion.segunda.Sistema;

public class Main {

    public static long tiempoInicio;
    public static long tiempoEstimado;
    public static ArrayList<Double> numeros_aleatorios;
    public static int N_ALEATORIOS = 100000;

    public static void main(String[] args) {
        numeros_aleatorios = Generar.numeros(N_ALEATORIOS);
        System.gc();
        //Simulacion 5.8
//        int N_CORRIDAS_5_6 = 1;
//        // Llevar registro de lo que sucede
//        Map<Float, Float> costos_P1 = new HashMap<>();
//        Map<Float, Float> costos_P2 = new HashMap<>();
//        long nuevoTiempo = System.nanoTime();
//        for (int j = 0; j < N_CORRIDAS_5_6; j++) {
//            Empresa empresa = new Empresa(1);
//            empresa.simular();
//            // Conteo del costo P1
//            if (!costos_P1.containsKey(empresa.costo_total)) {
//                costos_P1.put(empresa.costo_total, Float.parseFloat("1"));
//            } else {
//                costos_P1.put(empresa.costo_total, costos_P1.get(empresa.costo_total) + 1);
//            }
//            empresa = new Empresa(2);
//            empresa.simular();
//            // Conteo del costo P2
//            if (!costos_P2.containsKey(empresa.costo_total)) {
//                costos_P2.put(empresa.costo_total, Float.parseFloat("1"));
//            } else {
//                costos_P2.put(empresa.costo_total, costos_P2.get(empresa.costo_total) + 1);
//            }
//            System.gc();
//        }
//        long nuevoTiempo2 = System.nanoTime() - nuevoTiempo;
//        System.out.println("Tiempo: " + TimeUnit.NANOSECONDS.toMillis(nuevoTiempo2) + "ms");
//        System.out.println(costos_P1);
//        System.out.println(costos_P2);

        //Simulacion 5.12
        Sistema sistema = new Sistema();
        sistema.simular(5);
        System.out.println(sistema);
        System.out.println(sistema.atendidos);
     }

    public static Double un_numero_aleatorio() {
        if (numeros_aleatorios.isEmpty()) {
            numeros_aleatorios = Generar.numeros(1000000);
            System.gc();
            return numeros_aleatorios.remove(0);
        } else {
            return numeros_aleatorios.remove(0);
        }
    }

    public static ArrayList<Double> otra_libreria(int cantidad) {
        tiempoInicio = System.nanoTime();

        ArrayList<Double> nuevo_set;
        Random r = Random.getInstance(1234L);
        nuevo_set = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            nuevo_set.add(r.nextDouble());
        }

        tiempoEstimado = System.nanoTime() - tiempoInicio;
        System.out.println("Tiempo SET: " + TimeUnit.NANOSECONDS.toMillis(tiempoEstimado) + "ms");

        tiempoInicio = System.nanoTime();

        System.out.println(duplicados(nuevo_set));

        tiempoEstimado = System.nanoTime() - tiempoInicio;
        System.out.println("Tiempo DUPLICADOS: " + TimeUnit.NANOSECONDS.toMillis(tiempoEstimado) + "ms");

        return nuevo_set;
    }

    public static ArrayList<Double> java(int cantidad) {
        tiempoInicio = System.nanoTime();

        ArrayList<Double> nuevo_set = new ArrayList<>();
        java.util.Random r = new java.util.Random();
        for (int i = 0; i < cantidad; i++) {
            nuevo_set.add(r.nextDouble());
        }

        tiempoEstimado = System.nanoTime() - tiempoInicio;
        System.out.println("Tiempo SET: " + TimeUnit.NANOSECONDS.toMillis(tiempoEstimado) + "ms");

        tiempoInicio = System.nanoTime();

        System.out.println(duplicados(nuevo_set));

        tiempoEstimado = System.nanoTime() - tiempoInicio;
        System.out.println("Tiempo DUPLICADOS: " + TimeUnit.NANOSECONDS.toMillis(tiempoEstimado) + "ms");

        return nuevo_set;
    }

    public static void pruebas() {
        int N_ALEATORIOS = 10000000;
        ArrayList<Double> numeros_aleatorios;
        tiempoInicio = System.nanoTime();
        System.out.println("OTRA LIBRERIA");
        numeros_aleatorios = otra_libreria(N_ALEATORIOS);
        System.out.println("JAVA");
        numeros_aleatorios = java(N_ALEATORIOS);
        System.gc();
        System.out.println("Tiempo total: " + TimeUnit.NANOSECONDS.toMillis(tiempoEstimado) + "ms");
    }

    private static void readFile1(String archivo) {
        ArrayList<Double> lectura = new ArrayList<>();
        File fin = new File(archivo);
        FileInputStream fis;
        try {
            fis = new FileInputStream(fin);
            //Construct BufferedReader from InputStreamReader
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String line = null;
            while ((line = br.readLine()) != null) {
                lectura.add(Double.parseDouble(line));
            }

            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void writeFile(String archivo) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(archivo))) {
            for (Double numero : numeros_aleatorios) {
                pw.println(numero);
            }
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
