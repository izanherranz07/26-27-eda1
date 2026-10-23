
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
}
