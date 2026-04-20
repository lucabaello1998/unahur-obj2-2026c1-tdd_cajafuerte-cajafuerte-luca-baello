package ar.edu.unahur.obj2.tdd;

public class CajaFuerte {
    private String codigoSeguridad;
    private boolean abierta;

    public CajaFuerte(String codigoInicial) {
        this.codigoSeguridad = codigoInicial;
        this.abierta = false;
    }

    public void abrir(String codigo) {
        if (!codigo.equals(this.codigoSeguridad)) {
            throw new RuntimeException("Código incorrecto");
        }
        this.abierta = true;
    }

    public void cerrar(String codigoNuevo) {
        if (!this.abierta) {
            throw new RuntimeException("La caja fuerte no está abierta");
        }
        this.codigoSeguridad = codigoNuevo;
        this.abierta = false;
    }

    public boolean estaAbierta() {
        return this.abierta;
    }
}
