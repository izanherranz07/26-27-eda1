public class Fila {

    private static final int CAPACIDAD_INICIAL = 16;

    private Persona[] personas = new Persona[CAPACIDAD_INICIAL];
    private int cantidad = 0;

    public int tamano() {
        return cantidad;
    }

    public boolean estaVacia() {
        return cantidad == 0;
    }

    public int longitudMetros() {
        return cantidad;
    }

    public Persona get(int posicion) {
        if (posicion < 0 || posicion >= cantidad) {
            throw new IndexOutOfBoundsException("Posición inválida: " + posicion);
        }
        return personas[posicion];
    }

    public void agregarAlFinal(Persona p) {
        insertar(cantidad, p);
    }

    public Persona atenderFrente() {
        if (estaVacia()) {
            return null;
        }
        return eliminar(0);
    }

    private void asegurarCapacidad() {
        if (cantidad == personas.length) {
            Persona[] nuevo = new Persona[personas.length * 2];
            for (int i = 0; i < cantidad; i++) {
                nuevo[i] = personas[i];
            }
            personas = nuevo;
        }
    }

    private void insertar(int posicion, Persona p) {
        asegurarCapacidad();
        for (int i = cantidad; i > posicion; i--) {
            personas[i] = personas[i - 1];
        }
        personas[posicion] = p;
        cantidad++;
    }

    private Persona eliminar(int posicion) {
        Persona eliminada = personas[posicion];
        for (int i = posicion; i < cantidad - 1; i++) {
            personas[i] = personas[i + 1];
        }
        personas[cantidad - 1] = null;
        cantidad--;
        return eliminada;
    }
}