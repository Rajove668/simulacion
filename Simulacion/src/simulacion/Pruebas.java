package simulacion;

import java.util.ArrayList;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class Pruebas {

    static boolean poker(ArrayList<Double> numeros) {
        return true;
    }

    static boolean kolmogorov_smirnov(ArrayList<Double> numeros) {
        return true;
    }

    static boolean frecuencias_chi_cuadrado(ArrayList<Double> numeros) {
        return true;
    }

    static boolean promedios(ArrayList<Double> numeros) {
        SummaryStatistics ayuda = new SummaryStatistics();
        numeros.forEach((numero) -> {
            ayuda.addValue(numero);
        });
        return Math.abs((ayuda.getMean() - 0.5) * Math.sqrt(numeros.size())) / Math.sqrt(1.0 / 12.0) < 1.96;
    }

}
