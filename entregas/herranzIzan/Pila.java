public class Pila {

    private Nodo cima;
    private int cantidad;

    public Pila() {
        cima = null;
        cantidad = 0;
    }

    public void apilar(String dato) {
        Nodo nuevo = new Nodo(dato);
        nuevo.siguiente = cima;
        cima = nuevo;
        cantidad++;
    }
}
