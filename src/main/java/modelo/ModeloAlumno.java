/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import DAO.AlumnoDAO;
import java.util.ArrayList;

public class ModeloAlumno implements Comparable<ModeloAlumno> {

    private int nie;
    private String nombre;
    private String apelliddos;
    private int totalPuntos;
    private ModeloEncardoAlumno modeloEncargadoAlumno;
    private ModeloSeccion modeloSeccion;
    private ArrayList<ModeloConducta> modeloConducta;

    public ModeloAlumno() {
    }

    public ModeloAlumno(int nie, String nombre, String apelliddos, int totalPuntos,
            ModeloEncardoAlumno modeloEncargadoAlumno, ModeloSeccion modeloSeccion,
            ArrayList<ModeloConducta> modeloConducta) {
        this.nie = nie;
        this.nombre = nombre;
        this.apelliddos = apelliddos;
        this.totalPuntos = totalPuntos;
        this.modeloEncargadoAlumno = modeloEncargadoAlumno;
        this.modeloSeccion = modeloSeccion;
        this.modeloConducta = modeloConducta;
    }

    @Override
    public int compareTo(ModeloAlumno otroAlumno) {
        return Integer.compare(this.nie, otroAlumno.getNie());
    }

    public int getNie() {
        return nie;
    }

    public void setNie(int nie) {
        this.nie = nie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApelliddos() {
        return apelliddos;
    }

    public void setApelliddos(String apelliddos) {
        this.apelliddos = apelliddos;
    }

    public int getTotalPuntos() {
        return totalPuntos;
    }

    public void setTotalPuntos(int totalPuntos) {
        this.totalPuntos = totalPuntos;
    }

    public ModeloEncardoAlumno getModeloEncargadoAlumno() {
        return modeloEncargadoAlumno;
    }

    public void setModeloEncargadoAlumno(ModeloEncardoAlumno modeloEncargadoAlumno) {
        this.modeloEncargadoAlumno = modeloEncargadoAlumno;
    }

    public ModeloSeccion getModeloSeccion() {
        return modeloSeccion;
    }

    public void setModeloSeccion(ModeloSeccion modeloSeccion) {
        this.modeloSeccion = modeloSeccion;
    }

    public ArrayList<ModeloConducta> getModeloConducta() {
        return modeloConducta;
    }

    public void setModeloConducta(ArrayList<ModeloConducta> modeloConducta) {
        this.modeloConducta = modeloConducta;
    }
}
