package simulacion.segunda;

public class Cliente {
    public Integer horaEntrada;
    public Integer id;
    public static int ID = 1;
    public Double tiempoTotal;
    public Double tiempoAtencion1;
    public Double tiempoAtencion2;
    public Double tiempoAtencion1_bak;
    public Double tiempoAtencion2_bak;

    public Cliente(Double tiempoAtencion1, Double tiempoAtencion2, Integer hora) {
        this.tiempoAtencion1 = tiempoAtencion1;
        this.tiempoAtencion1_bak = tiempoAtencion1;
        this.tiempoAtencion2 = tiempoAtencion2;
        this.tiempoAtencion2_bak = tiempoAtencion2;
        this.tiempoTotal = 0.0;
        this.id = Cliente.ID;
        Cliente.ID++;
        this.horaEntrada = hora;
    }

    @Override
    public String toString() {
        return "#" + id + "(" + tiempoTotal + ", " + tiempoAtencion1 + ", " + tiempoAtencion2 + ')';
    }

}
