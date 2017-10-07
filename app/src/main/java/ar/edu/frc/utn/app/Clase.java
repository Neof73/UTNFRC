package ar.edu.frc.utn.app;

public class Clase {
    String id = null;
    String fecha = null;
    String tipo = null;
    String clases = null;
    String presentacion = null;
    public Clase(String id, String fecha, String tipo, String clases, String presentacion) {
        this.id = id;
        this.fecha = fecha;
        this.tipo = tipo;
        this.clases = clases;
        this.presentacion = presentacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getClases() {
        return clases;
    }

    public void setClases(String clases) {
        this.clases = clases;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

}
