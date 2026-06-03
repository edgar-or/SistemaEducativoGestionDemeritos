package modelo;


import java.time.LocalDate;
import modelo.ModeloAlumno;
import modelo.ModeloDocente;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author estud
 */
public class ModeloMovimientoConducta {

    private String observacion;
    private LocalDate fecha;
    private ModeloDocente modeloDocente;
    private ModeloAlumno modeloAlumno;
    private ModeloConducta modeloConducta;

    public ModeloMovimientoConducta() {
    }

    public ModeloMovimientoConducta(String observacion, LocalDate fecha, ModeloDocente modeloDocente, ModeloAlumno modeloAlumno, ModeloConducta modeloConducta) {
        this.observacion = observacion;
        this.fecha = fecha;
        this.modeloDocente = modeloDocente;
        this.modeloAlumno = modeloAlumno;
        this.modeloConducta = modeloConducta;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ModeloDocente getModeloDocente() {
        return modeloDocente;
    }

    public void setModeloDocente(ModeloDocente modeloDocente) {
        this.modeloDocente = modeloDocente;
    }

    public ModeloAlumno getModeloAlumno() {
        return modeloAlumno;
    }

    public void setModeloAlumno(ModeloAlumno modeloAlumno) {
        this.modeloAlumno = modeloAlumno;
    }

    public ModeloConducta getModeloConducta() {
        return modeloConducta;
    }

    public void setModeloConducta(ModeloConducta modeloConducta) {
        this.modeloConducta = modeloConducta;
    }

    

}
