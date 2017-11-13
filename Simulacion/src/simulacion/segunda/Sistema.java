package simulacion.segunda;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import simulacion.Main;
import static simulacion.Main.numeros_aleatorios;
import static simulacion.Main.tiempoEstimado;
import static simulacion.Main.tiempoInicio;
import static simulacion.Main.un_numero_aleatorio;
import simulacion.Util;

public class Sistema {

    public Cola fila1;
    public Cola fila2;
    public ArrayList<Cliente> atendidos;
    public ArrayList<Cliente> aux;
    private Double[] fPoisson = null;
    private String mensaje = "";

    public Sistema() {
        fila1 = new Cola();
        fila2 = new Cola();
        atendidos = new ArrayList<>();
        aux = new ArrayList<>();
    }

    public void simular(int horas) {
        /*
            1. Se sacan cuantos clientes nuevos llegan y se crean los objetos
            2. Se atienden hasta consumir la hora y se van añadiendo a la segunda fila
            3. Se atienden hasta consumir la hora la segunda fila
         */
        tiempoInicio = System.nanoTime();
        for (int hora = 0; hora < horas; hora++) {
            mensaje += "[SIM 5.12] Hora(" + (hora + 1) + ") INICIO\n";
            // Valores de prueba
            for (int i = 0; i < clientes_nuevos(); i++) {
                fila1.insertar(new Cliente(tiempo_atencion(), tiempo_atencion2(), hora));
                aux.add(fila1.lista.get(fila1.lista.size() - 1));
            }
            mensaje += "[SIM 5.12] Hora(" + (hora + 1) + ") NUEVOS (" + aux.size() + ") " + aux + "\n";
            aux.clear();
            mensaje += "[SIM 5.12] Hora(" + (hora + 1) + ") F1 antes atender " + fila1 + "\n";
            // FILA 1
            // Se atienden hasta consumir la hora y se van añadiendo a la segunda fila
            Double tiempo_restante = 60.0;
            do {
                // Por si esta vacio
                if (!fila1.vacio()) {
                    if (fila1.cliente_atendiendo().tiempoAtencion1 <= tiempo_restante) {
                        mensaje += "[SIM 5.12] Hora(" + (hora + 1) + ") F1 Atiende COMPLETO " + fila1.cliente_atendiendo() + "\n";
                        // Se atiende y ese tiempo incrementa a los que estan detras de el
                        for (int j = 1; j < fila1.size(); j++) {
                            fila1.lista.get(j).tiempoTotal += fila1.cliente_atendiendo().tiempoAtencion1;
                        }
                        // Se atiende
                        tiempo_restante -= fila1.cliente_atendiendo().tiempoAtencion1;
                        fila1.cliente_atendiendo().tiempoTotal += fila1.cliente_atendiendo().tiempoAtencion1;
                        fila1.cliente_atendiendo().tiempoAtencion1 = 0.0;
                        fila2.insertar(fila1.remover_cliente_atendido());
                    } else {
                        mensaje += "[SIM 5.12] Hora(" + (hora + 1) + ") F1 Atiende PARCIAL " + fila1.cliente_atendiendo() + "\n";
                        // Se medio-atiende y ese tiempo incrementa a los que estan detras de el
                        for (int j = 1; j < fila1.size(); j++) {
                            fila1.lista.get(j).tiempoTotal += tiempo_restante;
                        }
                        // Medio atiende
                        fila1.cliente_atendiendo().tiempoTotal += tiempo_restante;
                        fila1.cliente_atendiendo().tiempoAtencion1 -= tiempo_restante;
                        tiempo_restante = 0.0;
                    }
                } else {
                    tiempo_restante = 0.0;
                }
            } while (tiempo_restante > 0.0);
            // FIN FILA 1
            mensaje += "[SIM 5.12] Hora(" + (hora + 1) + ") F1 despues atender " + fila1 + "\n";
            mensaje += "[SIM 5.12] Hora(" + (hora + 1) + ") F2 antes atender " + fila2 + "\n";
            // FILA 2
            while (!fila2.vacio()) {
                if ((fila2.cliente_atendiendo().tiempoTotal + fila2.cliente_atendiendo().tiempoAtencion2) <= (1 + hora - fila2.cliente_atendiendo().horaEntrada) * 60.0) {
                    // Atiende completamente
                    mensaje += "[SIM 5.12] Hora(" + (hora + 1) + ") F2 atiende COMPLETO " + fila2.cliente_atendiendo() + " POR " + (fila2.cliente_atendiendo().tiempoTotal + fila2.cliente_atendiendo().tiempoAtencion2) + " <= " + (1 + hora - fila2.cliente_atendiendo().horaEntrada) * 60.0 + "\n";
                    for (int i = 1; i < fila2.size(); i++) {
                        if (fila2.lista.get(i).tiempoTotal + fila2.cliente_atendiendo().tiempoAtencion2 <= (1 + hora - fila2.lista.get(i).horaEntrada) * 60.0) {
                            fila2.lista.get(i).tiempoTotal += fila2.cliente_atendiendo().tiempoAtencion2;
                        } else {
                            fila2.lista.get(i).tiempoTotal = (1 + hora - fila2.lista.get(i).horaEntrada) * 60.0;
                        }
                    }
                    fila2.cliente_atendiendo().tiempoTotal += fila2.cliente_atendiendo().tiempoAtencion2;
                    fila2.cliente_atendiendo().tiempoAtencion2 = 0.0;
                    atendidos.add(fila2.remover_cliente_atendido());
                } else if (fila2.cliente_atendiendo().tiempoTotal != (1 + hora - fila2.cliente_atendiendo().horaEntrada) * 60.0) {
                    // Atiende parcialmente
                    mensaje += "[SIM 5.12] Hora(" + (hora + 1) + ") F2 atiende PARCIAL " + fila2.cliente_atendiendo() + " POR " + fila2.cliente_atendiendo().tiempoTotal + " != " + (1 + hora - fila2.cliente_atendiendo().horaEntrada) * 60.0 + "\n";
                    fila2.cliente_atendiendo().tiempoAtencion2 -= (1 + hora - fila2.cliente_atendiendo().horaEntrada) * 60.0 - fila2.cliente_atendiendo().tiempoTotal;
                    fila2.cliente_atendiendo().tiempoTotal = (1 + hora - fila2.cliente_atendiendo().horaEntrada) * 60.0;
                    for (int i = 1; i < fila2.size(); i++) {
                        fila2.lista.get(i).tiempoTotal = (1 + hora - fila2.lista.get(i).horaEntrada) * 60.0;
                    }
                    break;
                } else {
                    break;
                }
            }
            mensaje += "[SIM 5.12] Hora(" + (hora + 1) + ") atendidos (" + atendidos.size() + ") " + atendidos + "\n";
            mensaje += "[SIM 5.12] Hora(" + (hora + 1) + ") F2 despues atender " + fila2 + "\n";
            mensaje += "[SIM 5.12] Hora(" + (hora + 1) + ") FIN F1" + fila1 + " F2" + fila2 + "\n";
        }
        tiempoEstimado = System.nanoTime() - tiempoInicio;
        if (Util.DEBUG2) {
            System.out.print(mensaje);
        }
        if (Util.DEBUG) {
            System.out.println("[SIM 5.12] Resumen: En " + horas
                    + " horas, F1(" + fila1.size() + ") y F2(" + fila2.size()
                    + ") con " + atendidos.size() + " clientes atendidos y tiempo promedio de "
                    + tiempoPromedio() + " min. Tiempo de simulacion "
                    + TimeUnit.NANOSECONDS.toMillis(tiempoEstimado) + "ms y se han usado "
                    + (Main.N_ALEATORIOS - numeros_aleatorios.size()) + " numeros pseudo-aleatorios");
        }
    }

    public double tiempoPromedio() {
        double sum = 0.0;
        for (Cliente cliente : atendidos) {
            sum += cliente.tiempoTotal;
        }
        return sum / atendidos.size();
    }

    public Integer clientes_nuevos() {
        // TI Dist poisson
        int MEDIA = 20;
        if (this.fPoisson == null) {
            Double[] f = new Double[3 * MEDIA + 1];
//            Double sum = 0.0;
            for (int i = 0; i < (3 * MEDIA + 1); i++) {
                f[i] = (Math.pow(Math.E, -MEDIA) * Math.pow(MEDIA, i)) / (factorial(i));
//                System.out.println("f[" + i + "] " + f[i]);
//                sum += f[i];
            }
//            System.out.println(sum);
            Double[] F = new Double[3 * MEDIA + 2];
            F[0] = 0.0;
            for (int i = 1; i < 3 * MEDIA + 2; i++) {
                F[i] = F[i - 1] + f[i - 1];
            }
//            for (int i = 0; i < 3 * MEDIA + 2; i++) {
//                System.out.println("F[" + i + "] " + F[i]);
//            }
            this.fPoisson = F;
        }

        Double R = un_numero_aleatorio();
        for (int i = 1; i < 3 * MEDIA + 2; i++) {
            if (R >= fPoisson[i - 1] && R < fPoisson[i]) {
                return i - 1;
            }
        }
        return null;
    }

    public double factorial(double numero) {
        if (numero == 0) {
            return 1;
        } else {
            return numero * factorial(numero - 1);
        }
    }

    public Double tiempo_atencion() {
        // TI Dist exponencial
        double MEDIA = 2.0;
        Double R = un_numero_aleatorio();
        if (R.equals(0.0)) {
            return 0.0;
        }
        // Redondea arriba
        return Math.ceil(-MEDIA * Math.log(R));
    }

    public Double tiempo_atencion2() {
        // TI Dist uniforme
        int A = 1;
        int B = 2;
        return Math.ceil(A + (B - A) * un_numero_aleatorio());
    }

    @Override
    public String toString() {
        return "Fila1 " + fila1 + "\nFila2 " + fila2;
    }

}
