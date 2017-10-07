package ar.edu.frc.utn.app;

import java.util.ArrayList;

/**
 * Created by Mario Di Giorgio on 16/06/2017.
 */

public class Course {
    String anio = null;
    String nombre = null;
    String docente = null;
    String email = null;
    ArrayList<Clase> clase = null;

    public Course(String anio, String nombre, String docente, String email) {
        this.anio = anio;
        this.nombre = nombre;
        this.docente = docente;
        this.email = email;
        this.clase = new ArrayList<Clase>();
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
