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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import static simulacion.Generar.duplicados;
import simulacion.primera.Empresa;
import simulacion.segunda.Sistema;
import simulacion.ventanas.Ventana5_12;
import simulacion.ventanas.Ventana5_6;

public class Main {

    public static long tiempoInicio;
    public static long tiempoEstimado;
    public static ArrayList<Double> numeros_aleatorios;
    public static int N_ALEATORIOS = 100000;

    public static void main(String[] args) {
        // Pedir al usuario que tanto debug quiere
        Util.DEBUG = true;
        Util.DEBUG2 = true;
        numeros_aleatorios = Generar.numeros(N_ALEATORIOS);
//        readFile1("../Numeros-texto-plano/archivo.txt");
        writeFile(N_ALEATORIOS);
        System.gc();
        int N_CORRIDAS_5_6 = 10;
        int N_CORRIDAS_5_12 = 10;
        int N_HORAS_5_12 = 24;
        // Entrada 1
        if (JOptionPane.showConfirmDialog(null, "Valores predeterminados\nSIM 5.6 -> "
                + N_CORRIDAS_5_6 + " Corridas\nSIM 5.12 -> " + N_CORRIDAS_5_12
                + " con " + N_HORAS_5_12 + " horas por corrida\nCambiar numero de corridas?", "Mensaje", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            // Pedir al usuario
            N_CORRIDAS_5_6 = Integer.parseInt(JOptionPane.showInputDialog("Cuantas corridas para SIM 5.6?"));
            N_CORRIDAS_5_12 = Integer.parseInt(JOptionPane.showInputDialog("Cuantas corridas para SIM 5.12?"));
            N_HORAS_5_12 = Integer.parseInt(JOptionPane.showInputDialog("Cuantas horas para cada corrida de SIM 5.12?"));
        }
        // Entrada 2
        Util.DEBUG2 = false;
        if (JOptionPane.showConfirmDialog(null, "Valores predeterminados\nResumen de cada corrida: "
                + Util.DEBUG + "\nPaso a paso de cada corrida: " + Util.DEBUG2
                + "\nDesea cambiar que tan descriptiva es la salida?", "Mensaje", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            Util.DEBUG = (JOptionPane.showConfirmDialog(null, "Resumen de cada corrida", "Mensaje", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
            Util.DEBUG2 = (JOptionPane.showConfirmDialog(null, "Paso a paso de cada corrida", "Mensaje", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION);
        }
        //Simulacion 5.8
        // Llevar registro de lo que sucede
        Map<Float, Float> costos_P1 = new HashMap<>();
        Map<Float, Float> costos_P2 = new HashMap<>();
        long nuevoTiempo = System.nanoTime();
        for (int j = 0; j < N_CORRIDAS_5_6; j++) {
            Empresa empresa = new Empresa(1);
            empresa.simular();
            // Conteo del costo P1
            if (!costos_P1.containsKey(empresa.costo_total)) {
                costos_P1.put(empresa.costo_total, Float.parseFloat("1"));
            } else {
                costos_P1.put(empresa.costo_total, costos_P1.get(empresa.costo_total) + 1);
            }
            empresa = new Empresa(2);
            empresa.simular();
            // Conteo del costo P2
            if (!costos_P2.containsKey(empresa.costo_total)) {
                costos_P2.put(empresa.costo_total, Float.parseFloat("1"));
            } else {
                costos_P2.put(empresa.costo_total, costos_P2.get(empresa.costo_total) + 1);
            }
            System.gc();
        }
        long nuevoTiempo2 = System.nanoTime() - nuevoTiempo;
        System.out.println("[SIM 5.6 FIN] Tiempo: " + TimeUnit.NANOSECONDS.toMillis(nuevoTiempo2) + "ms");
        System.out.println(costos_P1);
        System.out.println(costos_P2);

        //Simulacion 5.12
        // Llevar registro de lo que sucede
        Map<Integer, Float> fila1_tamaños = new HashMap<>();
        Map<Integer, Float> fila2_tamaños = new HashMap<>();
        Double promedio = 0.0;
        nuevoTiempo = System.nanoTime();
        for (int j = 0; j < N_CORRIDAS_5_12; j++) {
            Sistema sistema = new Sistema();
            sistema.simular(N_HORAS_5_12);
            // Saca resultados F1
            if (!fila1_tamaños.containsKey(sistema.fila1.size())) {
                fila1_tamaños.put(sistema.fila1.size(), Float.parseFloat("1.0"));
            } else {
                fila1_tamaños.put(sistema.fila1.size(), fila1_tamaños.get(sistema.fila1.size()) + 1);
            }
            // Saca resultados F2
            if (!fila2_tamaños.containsKey(sistema.fila2.size())) {
                fila2_tamaños.put(sistema.fila2.size(), Float.parseFloat("1.0"));
            } else {
                fila2_tamaños.put(sistema.fila2.size(), fila2_tamaños.get(sistema.fila2.size()) + 1);
            }
            // Saca resultados tiempo promedio
            promedio += sistema.tiempoPromedio();
            System.gc();
        }
        promedio /= N_CORRIDAS_5_12;
        nuevoTiempo2 = System.nanoTime() - nuevoTiempo;
        System.out.println("[SIM 5.12 FIN] Tiempo: " + TimeUnit.NANOSECONDS.toMillis(nuevoTiempo2) + "ms");
        System.out.println("F1 " + fila1_tamaños);
        System.out.println("F2 " + fila2_tamaños);
        System.out.println("T promedio en sistema " + promedio + " min");
        resultados(costos_P1, costos_P2, N_CORRIDAS_5_6, fila1_tamaños, fila2_tamaños, promedio, N_CORRIDAS_5_12, N_HORAS_5_12);
    }

    public static Double un_numero_aleatorio() {
        if (numeros_aleatorios.isEmpty()) {
            System.out.println("[ALERTA] Generando mas numeros");
            boolean respaldo1 = Util.DEBUG;
            boolean respaldo2 = Util.DEBUG2;
            Util.DEBUG = true;
            Util.DEBUG2 = true;
            N_ALEATORIOS += 100000;
            numeros_aleatorios = Generar.numeros(100000);
            writeFile(100000);
            System.gc();
            Util.DEBUG = respaldo1;
            Util.DEBUG2 = respaldo2;
            return numeros_aleatorios.remove(0);
        } else {
            return numeros_aleatorios.remove(0);
        }
    }

    public static void writeFile(int n) {
        String archivo = "../Numeros-texto-plano/" + n + "_" + new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss").format(new Date()) + ".txt";
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(archivo))) {
            for (Double numero : numeros_aleatorios) {
                pw.println(numero);
            }
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void resultados(Map<Float, Float> costos_P1, Map<Float, Float> costos_P2, int N_CORRIDAS_5_6, Map<Integer, Float> fila1_tamaños, Map<Integer, Float> fila2_tamaños, Double t_promedio, int N_CORRIDAS_5_12, int N_HORAS_5_12) {
        if (!costos_P1.isEmpty() && !costos_P2.isEmpty()) {
//            Por si se quiere ordenado en String
//            System.out.println("POLITICA 1");
//            ArrayList<Float> copia_costos = new ArrayList<>(costos_P1.keySet());
//            Collections.sort(copia_costos);
//            for (Float costo : copia_costos) {
//                System.out.println("$" + costo + ": " + costos_P1.get(costo) + " veces");
//            }
//            System.out.println("POLITICA 2");
//            copia_costos = new ArrayList<>(costos_P2.keySet());
//            Collections.sort(copia_costos);
//            for (Float costo : copia_costos) {
//                System.out.println("$" + costo + ": " + costos_P2.get(costo) + " veces");
//            }
            SwingUtilities.invokeLater(() -> {
                Ventana5_6 ventana = new Ventana5_6(costos_P1, costos_P2, N_CORRIDAS_5_6);
                ventana.setSize(800, 400);
                ventana.setLocationRelativeTo(null);
                ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                ventana.setVisible(true);
            });
        }
        if (!fila1_tamaños.isEmpty() && !fila2_tamaños.isEmpty()) {
            SwingUtilities.invokeLater(() -> {
                Ventana5_12 ventana2 = new Ventana5_12(fila1_tamaños, fila2_tamaños, t_promedio, N_HORAS_5_12, N_CORRIDAS_5_12);
                ventana2.setSize(800, 400);
                ventana2.setLocationRelativeTo(null);
                ventana2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                ventana2.setVisible(true);
            });
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
        int N_ALEATORIOS_2 = 10000000;
        ArrayList<Double> numeros_aleatorios2;
        tiempoInicio = System.nanoTime();
        System.out.println("OTRA LIBRERIA");
        numeros_aleatorios2 = otra_libreria(N_ALEATORIOS_2);
        System.out.println("JAVA");
        numeros_aleatorios2 = java(N_ALEATORIOS_2);
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
        numeros_aleatorios = lectura;
    }
}
