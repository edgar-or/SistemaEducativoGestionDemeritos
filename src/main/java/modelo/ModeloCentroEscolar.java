/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author renec
 */
public class ModeloCentroEscolar {

    private String nombreCentroEscolar;
    private int numeroTel;
    private int codigoCentroEsc;
    private String departamento;
    private String municipio;
    private String caserio;
    private String calle;
    private String distrito;
    private String correoElectronico;

    public ModeloCentroEscolar(String nombreCentroEscolar, int numeroTel,
            int codigoCentroEsc, String departamento,
            String municipio, String caserio, String calle, String distrito,
            String correoElectronico) {
        this.nombreCentroEscolar = nombreCentroEscolar;
        this.numeroTel = numeroTel;
        this.codigoCentroEsc = codigoCentroEsc;
        this.departamento = departamento;
        this.municipio = municipio;
        this.caserio = caserio;
        this.calle = calle;
        this.distrito = distrito;
        this.correoElectronico = correoElectronico;
    }

    public String getNombreCentroEscolar() {
        return nombreCentroEscolar;
    }

    public int getNumeroTel() {
        return numeroTel;
    }

    public int getCodigoCentroEsc() {
        return codigoCentroEsc;
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

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setNombreCentroEscolar(String nombreCentroEscolar) {
        this.nombreCentroEscolar = nombreCentroEscolar;
    }

    public void setNumeroTel(int numeroTel) {
        this.numeroTel = numeroTel;
    }

    public void setCodigoCentroEsc(int codigoCentroEsc) {
        this.codigoCentroEsc = codigoCentroEsc;
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

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

}
