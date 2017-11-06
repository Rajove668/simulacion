package simulacion.segunda;

import java.util.ArrayList;

public class Cola {

    public ArrayList<Cliente> lista;

    public Cola() {
        lista = new ArrayList<>();
    }

    public void insertar(Cliente cliente) {
        lista.add(lista.size(), cliente);
    }

    public Cliente cliente_atendiendo() {
        if (lista.isEmpty()) {
            return null;
        }
        return lista.get(0);
    }

}
