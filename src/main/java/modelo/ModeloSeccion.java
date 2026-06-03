/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author renec
 */
public class ModeloSeccion {

    private int idSeccion;
    private String seccion;
    private ModeloGrado modeloGrado;
    private ModeloDocente modeloDocente;
    private ArrayList<ModeloAlumno> modeloAlumno;

    public ModeloSeccion() {
    }

    public ModeloSeccion(int idSeccion, String seccion, ModeloGrado modeloGrado, ModeloDocente modeloDocente, ArrayList<ModeloAlumno> modeloAlumno) {
        this.idSeccion = idSeccion;
        this.seccion = seccion;
        this.modeloGrado = modeloGrado;
        this.modeloDocente = modeloDocente;
        this.modeloAlumno = modeloAlumno;
    }

    

    public int getIdSeccion() {
        return idSeccion;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public ModeloGrado getModeloGrado() {
        return modeloGrado;
    }

    public void setModeloGrado(ModeloGrado modeloGrado) {
        this.modeloGrado = modeloGrado;
    }

    public ModeloDocente getModeloDocente() {
        return modeloDocente;
    }

    public void setModeloDocente(ModeloDocente modeloDocente) {
        this.modeloDocente = modeloDocente;
    }

    public ArrayList<ModeloAlumno> getModeloAlumno() {
        return modeloAlumno;
    }

    public void setModeloAlumno(ArrayList<ModeloAlumno> modeloAlumno) {
        this.modeloAlumno = modeloAlumno;
    }

    

    @Override
    public String toString() {
        return  this.seccion;
    }
    
    
    
}
