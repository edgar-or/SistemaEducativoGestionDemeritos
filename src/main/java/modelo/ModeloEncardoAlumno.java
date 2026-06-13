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
public class ModeloEncardoAlumno {
    
    private int id_encargado; 
    private int dui;
    private String nombre;
    private String apelllido;
    private ArrayList<ModeloTelefonosEncargadosAlumno>  telefonos;
    private ArrayList<ModeloCorreoEncargadoAlumno>  correo;
    private ArrayList<ModeloAlumno>  Alumno;
    private String departamento;
    private String municipio;
    private String caserio;
    private String canto;
    private String calle;
    private String distrito;

    public ModeloEncardoAlumno() {
    }

    public ModeloEncardoAlumno(int dui, String nombre, String apelllido, ArrayList<ModeloTelefonosEncargadosAlumno> telefonos, ArrayList<ModeloCorreoEncargadoAlumno> correo, ArrayList<ModeloAlumno> Alumno, String departamento, String municipio, String caserio, String canto, String calle, String distrito) {
        this.dui = dui;
        this.nombre = nombre;
        this.apelllido = apelllido;
        this.telefonos = telefonos;
        this.correo = correo;
        this.Alumno = Alumno;
        this.departamento = departamento;
        this.municipio = municipio;
        this.caserio = caserio;
        this.canto = canto;
        this.calle = calle;
        this.distrito = distrito;
    }

    

    
    public int getDui() {
        return dui;
    }

    public void setDui(int dui) {
        this.dui = dui;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApelllido() {
        return apelllido;
    }

    public void setApelllido(String apelllido) {
        this.apelllido = apelllido;
    }

    public ArrayList<ModeloTelefonosEncargadosAlumno> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(ArrayList<ModeloTelefonosEncargadosAlumno> telefonos) {
        this.telefonos = telefonos;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getCaserio() {
        return caserio;
    }

    public void setCaserio(String caserio) {
        this.caserio = caserio;
    }

    public String getCanto() {
        return canto;
    }

    public void setCanto(String canto) {
        this.canto = canto;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public ArrayList<ModeloCorreoEncargadoAlumno> getCorreo() {
        return correo;
    }

    public void setCorreo(ArrayList<ModeloCorreoEncargadoAlumno> correo) {
        this.correo = correo;
    }

    public ArrayList<ModeloAlumno> getAlumno() {
        return Alumno;
    }

    public void setAlumno(ArrayList<ModeloAlumno> Alumno) {
        this.Alumno = Alumno;
    }

    public int getId_encargado() {
        return id_encargado;
    }

    public void setId_encargado(int id_encargado) {
        this.id_encargado = id_encargado;
    }

    
    
    
}
