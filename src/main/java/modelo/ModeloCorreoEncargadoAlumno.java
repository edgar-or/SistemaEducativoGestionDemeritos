package modelo;


import modelo.ModeloEncardoAlumno;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author estud
 */
public class ModeloCorreoEncargadoAlumno {
  private int id;
  private String correoEncargado;
  private ModeloEncardoAlumno modeloEncardoAlumno;

    public ModeloCorreoEncargadoAlumno() {
    }

  
    public ModeloCorreoEncargadoAlumno(int id, String correoEncargado, ModeloEncardoAlumno modeloEncardoAlumno) {
        this.id = id;
        this.correoEncargado = correoEncargado;
        this.modeloEncardoAlumno = modeloEncardoAlumno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreoEncargado() {
        return correoEncargado;
    }

    public void setCorreoEncargado(String correoEncargado) {
        this.correoEncargado = correoEncargado;
    }

    public ModeloEncardoAlumno getModeloEncardoAlumno() {
        return modeloEncardoAlumno;
    }

    public void setModeloEncardoAlumno(ModeloEncardoAlumno modeloEncardoAlumno) {
        this.modeloEncardoAlumno = modeloEncardoAlumno;
    }
  
    
  
  
}
