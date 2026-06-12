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

    private int idEncargado;
    private String dui;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String departamento;
    private String municipio;
    private String distrito;
    private String canton;
    private String caserio;
    private String calle;
    private String numCasa;
    private ArrayList<ModeloTelefonosEncargadosAlumno> telefonos;
    private ArrayList<ModeloCorreoEncargadoAlumno> correo;
    private ArrayList<ModeloAlumno> Alumno;

    public ModeloEncardoAlumno() {
    }

    public ModeloEncardoAlumno(int idEncargado, String dui, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String departamento, String municipio, String distrito, String canton, String caserio, String calle, String numCasa, ArrayList<ModeloTelefonosEncargadosAlumno> telefonos, ArrayList<ModeloCorreoEncargadoAlumno> correo, ArrayList<ModeloAlumno> Alumno) {
        this.idEncargado = idEncargado;
        this.dui = dui;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.departamento = departamento;
        this.municipio = municipio;
        this.distrito = distrito;
        this.canton = canton;
        this.caserio = caserio;
        this.calle = calle;
        this.numCasa = numCasa;
        this.telefonos = telefonos;
        this.correo = correo;
        this.Alumno = Alumno;
    }

    
    public int getIdEncargado() {
        return idEncargado;
    }

    public void setIdEncargado(int idEncargado) {
        this.idEncargado = idEncargado;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
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

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getCanton() {
        return canton;
    }

    public void setCanton(String canton) {
        this.canton = canton;
    }

    public String getCaserio() {
        return caserio;
    }

    public void setCaserio(String caserio) {
        this.caserio = caserio;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumCasa() {
        return numCasa;
    }

    public void setNumCasa(String numCasa) {
        this.numCasa = numCasa;
    }

    public ArrayList<ModeloTelefonosEncargadosAlumno> getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(ArrayList<ModeloTelefonosEncargadosAlumno> telefonos) {
        this.telefonos = telefonos;
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

}
