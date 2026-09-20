import java.util.Random;

public class Simulacion {

    static final double P_LLEGADA = 0.6;
    static final int UMBRAL_ABURRIMIENTO = 8;   
    static final double P_ABURRIRSE = 0.3;
 
    static final double P_PREFERENTE = 0.10;
    static final double P_COLADO = 0.05;
    static final double P_ENTREGA = 0.05;
 
    private static final int LLEGADA = 0;
    private static final int CAJA = 1;
    private static final int PREFERENTE = 2;
    private static final int COLADO = 3;
    private static final int ENTREGA = 4;
    private static final int ABURRIMIENTO = 5;
 
    private final Random rnd;
    private final Tiempo tiempo;
    private final CentroComercial cccf;
    private int siguienteId = 1;

    private int aburridos, desistieron, preferentes, colados, entregas, avisos;
 
    public Simulacion(int duracionMinutos, boolean extendido, long semilla) {
        this.rnd = new Random(semilla);
        this.tiempo = new Tiempo(duracionMinutos, extendido);
        this.cccf = new CentroComercial("CCCF");
    }

    public int[] ejecutar() {
        int[] longitudes = new int[tiempo.getDuracion()];
 
        while (!tiempo.haTerminado()) {
            tiempo.avanzar();
            int minuto = tiempo.getMinuto();
            boolean reglasNuevas = tiempo.reglasNuevasActivas();
 
            for (int accion : accionesDelMinuto(reglasNuevas)) {
                ejecutarAccion(accion, minuto, reglasNuevas);
            }
 
            if (reglasNuevas && tiempo.tocaAviso() && cccf.debeAvisar()) {
                avisos++;
                System.out.println(cccf.anuncio(minuto));
            }
 
            longitudes[minuto - 1] = cccf.getFila().longitudMetros();
        }
        return longitudes;
    }
}