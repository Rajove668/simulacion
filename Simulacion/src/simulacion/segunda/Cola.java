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
    
    public Cliente cliente_atendiendo(){
        return lista.get(0);
    }
    
    public boolean vacio() {
        return lista.isEmpty();
    }

    public Cliente remover_cliente_atendido() {
        return lista.remove(0);
    }

    @Override
    public String toString() {
        return lista.toString();
    }

    public int size() {
        return lista.size();
    }
}
