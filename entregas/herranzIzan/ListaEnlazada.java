public class ListaEnlazada {

    private Nodo cabeza;
    private Nodo ultimo;
    private int cantidad;

    public ListaEnlazada() {
        cabeza = null;
        ultimo = null;
        cantidad = 0;
    }

    public void agregarFinal(String dato) {
        Nodo nuevo = new Nodo(dato);

        if (cabeza == null) {
            cabeza = nuevo;
        } else {
            ultimo.siguiente = nuevo;
        }

        ultimo = nuevo;
        cantidad++;
    }

    public boolean contiene(String dato) {
    Nodo actual = cabeza;

    while (actual != null) {
        if (actual.dato.equals(dato)) {
            return true;
        }
        actual = actual.siguiente;
    }

    return false;
}

    public boolean eliminar(String dato) {
    if (cabeza == null) {
        return false;
    }

    if (cabeza.dato.equals(dato)) {
        cabeza = cabeza.siguiente;

        if (cabeza == null) {
            ultimo = null;
        }

        cantidad--;
        return true;
    }

    Nodo anterior = cabeza;
    Nodo actual = cabeza.siguiente;

    while (actual != null) {
        if (actual.dato.equals(dato)) {
            anterior.siguiente = actual.siguiente;

            if (actual == ultimo) {
                ultimo = anterior;
            }

            cantidad--;
            return true;
        }

        anterior = actual;
        actual = actual.siguiente;
    }

        return false;
    }
}
