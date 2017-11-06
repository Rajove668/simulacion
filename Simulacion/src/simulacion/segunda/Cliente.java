package simulacion.segunda;

public class Cliente {

    public Double tiempoTotal;
    public Double tiempoAtencion;

    public Cliente(Double tiempoAtencion) {
        this.tiempoAtencion = tiempoAtencion;
        this.tiempoTotal = 0.0;
    }

    public void atender() {
        tiempoTotal += tiempoAtencion;
        tiempoAtencion = 0.0;
    }

    public void atender(Double tiempo) {
        tiempoTotal += tiempo;
        tiempoAtencion -= tiempo;
    }

}
