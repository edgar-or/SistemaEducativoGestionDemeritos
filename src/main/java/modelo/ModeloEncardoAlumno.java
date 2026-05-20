/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author renec
 */
public class ModeloEncardoAlumno {

    private String nombre;
    private String apelllido;
    private int dui;
    private int telefono;
    private String departamento;
    private String municipio;
    private String caserio;
    private String calle;
    private String distrito;

    public ModeloEncardoAlumno(String nombre, String apelllido,
            int dui, int telefono, String departamento, String municipio,
            String caserio, String calle, String distrito) {
        this.nombre = nombre;
        this.apelllido = apelllido;
        this.dui = dui;
        this.telefono = telefono;
        this.departamento = departamento;
        this.municipio = municipio;
        this.caserio = caserio;
        this.calle = calle;
        this.distrito = distrito;

    }

    public String getNombre() {
        return nombre;
    }

    public String getApelllido() {
        return apelllido;
    }

    public int getDui() {
        return dui;
    }

    public int getTelefono() {
        return telefono;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public String getCaserio() {
        return caserio;
    }

    public String getCalle() {
        return calle;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApelllido(String apelllido) {
        this.apelllido = apelllido;
    }

    public void setDui(int dui) {
        this.dui = dui;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setCaserio(String caserio) {
        this.caserio = caserio;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

}
