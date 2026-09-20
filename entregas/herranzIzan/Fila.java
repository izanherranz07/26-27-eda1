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

}