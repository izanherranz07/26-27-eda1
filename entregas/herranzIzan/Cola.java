public class Cola {

    private Nodo frente;
    private Nodo ultimo;
    private int cantidad;

    public Cola() {
        frente = null;
        ultimo = null;
        cantidad = 0;
    }

    public void encolar(String dato) {
        Nodo nuevo = new Nodo(dato);

        if (frente == null) {
            frente = nuevo;
        } else {
            ultimo.siguiente = nuevo;
        }

        ultimo = nuevo;
        cantidad++;
    }
}
