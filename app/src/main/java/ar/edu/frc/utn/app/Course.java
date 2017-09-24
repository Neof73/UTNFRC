package ar.edu.frc.utn.app;

import android.app.ListActivity;

import java.util.List;

/**
 * Created by Mario Di Giorgio on 16/06/2017.
 */

public class Course {

    String id = null;
    String fecha = null;
    String anio = null;
    String tipo = null;
    String nombre = null;
    String clases = null;
    String presentacion = null;
    String docente = null;
    String email = null;

    public Course(String id, String fecha, String anio, String tipo, String nombre, String clases, String presentacion, String docente, String email) {
        this.id = id;
        this.fecha = fecha;
        this.anio = anio;
        this.tipo = tipo;
        this.nombre = nombre;
        this.clases = clases;
        this.presentacion = presentacion;
        this.docente = docente;
        this.email = email;
    }

    public String getId() { return id; }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
    public String getDocente() {
        return docente;
    }

    public void setDocente(String docente) {
        this.docente = docente;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}

class Anio {
    public String anio;
    public List<Curso> cursos;
}

class Curso {
    public String curso;
    public List<Unidad> unidades;
}

class Unidad {
    public String unidad;
    public List<Clase> clases;
}
class Clase {
    public String clase;
    public List<String> fechas;
}