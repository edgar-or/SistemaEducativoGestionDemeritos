/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author renec
 */
public class ModeloDocente {

    private String nombre;
    private String apellido;
    private String idDocente;
    private int telefonoDocente;
    private String correo;
    private String departamento;
    private String municipio;
    private String caserio;
    private String calle;
    private String distrito;

    public ModeloDocente(String nombre, String apellido, String idDocente,
            int telefonoDocente, String correo, String departamento,
            String municipio, String caserio, String calle, String distrito) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.idDocente = idDocente;
        this.telefonoDocente = telefonoDocente;
        this.correo = correo;
        this.departamento = departamento;
        this.municipio = municipio;
        this.caserio = caserio;
        this.calle = calle;
        this.distrito = distrito;
    }

    public ModeloDocente() {
    }
    
    

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getIdDocente() {
        return idDocente;
    }

    public int getTelefonoDocente() {
        return telefonoDocente;
    }

    public String getCorreo() {
        return correo;
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

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setIdDocente(String idDocente) {
        this.idDocente = idDocente;
    }

    public void setTelefonoDocente(int telefonoDocente) {
        this.telefonoDocente = telefonoDocente;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
