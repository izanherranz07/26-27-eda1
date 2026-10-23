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
}
