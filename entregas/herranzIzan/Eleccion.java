
public class Eleccion {

    private ListaEnlazada candidaturas;
    private String[] boleta;
    private ListaEnlazada censo;
    private ListaEnlazada presentes;
    private ListaEnlazada votantesRegistrados;
    private Cola colaEntrega;
    private Pila urna;
    private int[] marcador;

    private boolean boletaCerrada;
    private boolean urnaCerrada;
    private boolean escrutinioIniciado;
    private int votosAceptados;
    private int votosContados;

    private String errorAnotado;
    private String errorReal;

    public Eleccion() {
        candidaturas = new ListaEnlazada();
        censo = new ListaEnlazada();
        presentes = new ListaEnlazada();
        votantesRegistrados = new ListaEnlazada();
        colaEntrega = new Cola();
        urna = new Pila();

        boletaCerrada = false;
        urnaCerrada = false;
        escrutinioIniciado = false;
        votosAceptados = 0;
        votosContados = 0;
    }

    public void proponerCandidatura(String nombre) {
        if (boletaCerrada || nombre == null ||
            nombre.isEmpty() || candidaturas.contiene(nombre)) {
            System.out.println("Candidatura no admitida: " + nombre);
            return;
        }

        candidaturas.agregarFinal(nombre);
    }

    public void retirarCandidatura(String nombre) {
        if (!boletaCerrada && candidaturas.eliminar(nombre)) {
            System.out.println("Candidatura retirada: " + nombre);
        } else {
            System.out.println("No se puede retirar: " + nombre);
        }
    }

    public void rechazarCandidatura(String nombre) {
        if (!boletaCerrada && candidaturas.eliminar(nombre)) {
            System.out.println("Candidatura rechazada: " + nombre);
        } else {
            System.out.println("No se puede rechazar: " + nombre);
        }
    }

    public void cerrarBoleta() {
        if (boletaCerrada || candidaturas.estaVacia()) {
            throw new IllegalStateException(
                "No se puede cerrar la boleta."
            );
        }

        boleta = new String[candidaturas.tamano()];

        for (int i = 0; i < boleta.length; i++) {
            boleta[i] = candidaturas.obtener(i);
        }

        marcador = new int[boleta.length + 2];
        boletaCerrada = true;
        System.out.println("Boleta definitiva cerrada.");
    }

    public void inscribirAlumno(Alumno alumno) {
        if (boletaCerrada) {
            System.out.println("El censo ya esta cerrado.");
            return;
        }

        String dni = alumno.getDni();

        if (!censo.contiene(dni)) {
            censo.agregarFinal(dni);
        }
    }

    public void registrarPresencia(Alumno alumno) {
        String dni = alumno.getDni();

        if (!urnaCerrada && censo.contiene(dni)
                && !presentes.contiene(dni)) {
            presentes.agregarFinal(dni);
            System.out.println(alumno.getNombre() + " esta presente.");
        } else {
            System.out.println("No se puede registrar a "
                    + alumno.getNombre());
        }
    }

    public void alumnoLlegaTarde(Alumno alumno) {
        System.out.println(alumno.getNombre() + " llega tarde.");
        registrarPresencia(alumno);
    }

    public void alumnoSeMarcha(Alumno alumno) {
        if (!urnaCerrada && presentes.eliminar(alumno.getDni())) {
            System.out.println(alumno.getNombre() + " se marcha.");
        } else {
            System.out.println("No se puede registrar la salida.");
        }
    }

    public void ponerseEnCola(Alumno alumno) {
        if (!boletaCerrada || urnaCerrada) {
            System.out.println("La votacion no esta abierta.");
            return;
        }

        colaEntrega.encolar(alumno.getDni());
        System.out.println(alumno.getNombre() + " entra en la cola.");
    }

    public void atenderSiguiente(String opcion) {
        if (!boletaCerrada || urnaCerrada || colaEntrega.estaVacia()) {
            System.out.println("No se puede atender al votante.");
            return;
        }

        String dni = colaEntrega.desencolar();

        if (!censo.contiene(dni)) {
            System.out.println("RECHAZADO: no pertenece al censo.");
            return;
        }

        if (!presentes.contiene(dni)) {
            System.out.println("RECHAZADO: no esta presente.");
            return;
        }

        if (votantesRegistrados.contiene(dni)) {
            System.out.println("RECHAZADO: intento de doble voto.");
            return;
        }

        if (votosAceptados >= censo.tamano()) {
            System.out.println("RECHAZADO: limite de votos alcanzado.");
            return;
        }

        if (opcion == null) {
            System.out.println("RECHAZADO: papeleta inexistente.");
            return;
        }

        Papeleta papeleta = new Papeleta(opcion);

        urna.apilar(papeleta.getOpcion());
        votantesRegistrados.agregarFinal(dni);
        votosAceptados++;

        System.out.println("Papeleta depositada correctamente.");
    }

    public void cerrarUrna() {
        if (!boletaCerrada || urnaCerrada || !colaEntrega.estaVacia()) {
            System.out.println("No se puede cerrar la urna.");
            return;
        }

        urnaCerrada = true;

        if (urna.tamano() != votosAceptados ||
            votosAceptados != votantesRegistrados.tamano() ||
            votosAceptados > censo.tamano()) {
            throw new IllegalStateException(
                "IRREGULARIDAD: numero de papeletas incorrecto."
            );
        }

        System.out.println("Urna cerrada y verificada.");
    }

}
