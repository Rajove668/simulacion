package simulacion.segunda;

import java.util.ArrayList;
import static simulacion.Main.un_numero_aleatorio;

public class Sistema {

    public Cola fila1;
    public Cola fila2;
    public ArrayList<Cliente> atendidos;
    public ArrayList<Cliente> aux = new ArrayList<>();
    private Double[] fPoisson = null;
    private String mensaje = "";

    public Sistema() {
        fila1 = new Cola();
        fila2 = new Cola();
        atendidos = new ArrayList<>();
    }

    public void simular() {
        /*
            1. Se sacan cuantos clientes nuevos llegan y se crean los objetos
            2. Se atienden hasta consumir la hora y se van añadiendo a la segunda fila
            3. Se atienden hasta consumir la hora
         */

        for (int i = 0; i < 5; i++) {
            fila1.insertar(new Cliente(14.0, 16.0));
        }
        System.out.println("ENTRAN " + fila1);
        // FILA 1
        Double tiempo_restante = 60.0;
        do {
            // Por si esta vacio
            if (!fila1.vacio()) {
                if (fila1.cliente_atendiendo().tiempoAtencion1 <= tiempo_restante) {
                    // Se atiende y ese tiempo incrementa a los que estan detras de el
                    for (int j = 1; j < fila1.size(); j++) {
                        fila1.lista.get(j).tiempoTotal += fila1.cliente_atendiendo().tiempoAtencion1;
                    }
                    // Se atiende
                    tiempo_restante -= fila1.cliente_atendiendo().tiempoAtencion1;
                    fila1.cliente_atendiendo().tiempoTotal += fila1.cliente_atendiendo().tiempoAtencion1;
                    fila1.cliente_atendiendo().tiempoAtencion1 = 0.0;
                    aux.add(fila1.lista.get(0));
                    fila2.insertar(fila1.remover_cliente_atendido());
                } else {
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
        System.out.println("ENTRAN A FILA 2 " + aux);
        // Fila 2
        // seguir pensando
        if (!fila2.vacio()) {
            tiempo_restante = 60.0 - fila2.cliente_atendiendo().tiempoTotal % 60;
            System.out.println("T restante fila 2 " + tiempo_restante);
            // Solo si se puede atender a alguien mas (tiempo_restante_fila2.py)
            if (!(fila2.cliente_atendiendo().tiempoTotal % 60 == 0)){
                // Se puede atender al primero
            }else{
                // Todos esperan pero solo al primero lo atienden
            }
            for(Cliente cliente : fila2.lista){
                if ((60.0 - cliente.tiempoTotal % 60) == tiempo_restante){
                    
                }
            }
        }
    }

    public Integer clientes_nuevos() {
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
//        System.out.println("R " + R);
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
        double MEDIA = 2.0;
        Double R = un_numero_aleatorio();
        if (R.equals(0.0)) {
            return 0.0;
        }
        // Redondea arriba
        return Math.ceil(-MEDIA * Math.log(R));
    }

    public Double tiempo_atencion2() {
        int A = 1;
        int B = 2;
        return Math.ceil(A + (B - A) * un_numero_aleatorio());
    }

    @Override
    public String toString() {
        return "Fila1 " + fila1 + "\nFila2 " + fila2;
    }

    public String toString2() {
        return "Fila1 " + fila1 + "\nFila2 " + fila2;
    }

}
