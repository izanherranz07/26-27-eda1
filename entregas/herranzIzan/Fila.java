import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fila {

    private final List<Persona> personas = new ArrayList<>();
 
    public int tamano() {
        return personas.size();
    }
 
    public boolean estaVacia() {
        return personas.isEmpty();
    }
 
    public int longitudMetros() {
        return personas.size();
    }
 
    public Persona get(int posicion) {
        return personas.get(posicion);
    }
 
    public void agregarAlFinal(Persona p) {
        personas.add(p);
    }
 
    public Persona atenderFrente() {
        if (personas.isEmpty()) {
            return null;
        }
        return personas.remove(0);
    }

    public void agregarPreferente(Persona p) {
        int ultimoPreferente = -1;
        for (int i = 0; i < personas.size(); i++) {
            if (personas.get(i).isPreferente()) {
                ultimoPreferente = i;
            }
        }
        personas.add(ultimoPreferente + 1, p);
    }
 
    public void colarDetras(Persona p, int posicionConocido) {
        personas.add(posicionConocido + 1, p);
    }

    public Persona entregarCompras(int posicionDador, int posicionReceptor) {
        Persona dador = personas.get(posicionDador);
        Persona receptor = personas.get(posicionReceptor);
        receptor.recibirCompras(dador.getCompras());
        personas.remove(posicionDador);
        return dador;
    }

    public int retirarAburridos(int minutoActual, int umbralMinutos, double prob, Random rnd) {
        int fuera = 0;
        for (int i = personas.size() - 1; i >= 0; i--) {
            Persona p = personas.get(i);
            if (p.minutosEnFila(minutoActual) > umbralMinutos && rnd.nextDouble() < prob) {
                personas.remove(i);
                fuera++;
            }
        }
        return fuera;
    }
 
    @Override
    public String toString() {
        return personas.toString();
    }

}