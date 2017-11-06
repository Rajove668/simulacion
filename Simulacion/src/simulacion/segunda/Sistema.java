package simulacion.segunda;

import static simulacion.Main.un_numero_aleatorio;

public class Sistema {

    public Cola fila1;
    public Cola fila2;
    private Double[] fPoisson = null;
    private String mensaje = "";

    public Sistema() {
        fila1 = new Cola();
        fila2 = new Cola();
    }

    public void simular() {
        /*
            1. Se sacan cuantos clientes nuevos llegan y se crean los objetos
            2. Se atienden hasta consumir la hora y se van añadiendo a la segunda fila
            3. Se atienden hasta consumir la hora
         */

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
}
