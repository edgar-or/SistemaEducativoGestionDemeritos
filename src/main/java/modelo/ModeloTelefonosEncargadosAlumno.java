package modelo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author estud
 */
public class ModeloTelefonosEncargadosAlumno {
    private int id;
    private int telefono;
    private ModeloEncardoAlumno encargadoAlumno;

    public ModeloTelefonosEncargadosAlumno(int id, int telefono, ModeloEncardoAlumno encargadoAlumno) {
        this.id = id;
        this.telefono = telefono;
        this.encargadoAlumno = encargadoAlumno;
    }

   
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    
    
    public ModeloEncardoAlumno getEncargadoAlumno() {
        return encargadoAlumno;
    }

    public void setEncargadoAlumno(ModeloEncardoAlumno encargadoAlumno) {
        this.encargadoAlumno = encargadoAlumno;
    }
    
    
}
