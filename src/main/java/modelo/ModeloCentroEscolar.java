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
public class ModeloCentroEscolar {

    private String codigoCE;
    private String nombreCentroEscolar;
    private String numeroTel;
    private String departamento;
    private String municipio;
    private String caserio;
    private String calle;
    private String distrito;
    private String correoElectronico;
    private ArrayList<ModeloGrado> modeloGrado;
    

    public ModeloCentroEscolar() {
    }

    public ModeloCentroEscolar(String codigoCE, String nombreCentroEscolar, String numeroTel, String departamento, String municipio, String caserio, String calle, String distrito, String correoElectronico, ArrayList<ModeloGrado> modeloGrado) {
        this.codigoCE = codigoCE;
        this.nombreCentroEscolar = nombreCentroEscolar;
        this.numeroTel = numeroTel;
        this.departamento = departamento;
        this.municipio = municipio;
        this.caserio = caserio;
        this.calle = calle;
        this.distrito = distrito;
        this.correoElectronico = correoElectronico;
        this.modeloGrado = modeloGrado;
    }
    
    
   
    public String getCodigoCE() {
        return codigoCE;
    }

    public void setCodigoCE(String codigoCE) {
        this.codigoCE = codigoCE;
    }

    public String getNombreCentroEscolar() {
        return nombreCentroEscolar;
    }

    public void setNombreCentroEscolar(String nombreCentroEscolar) {
        this.nombreCentroEscolar = nombreCentroEscolar;
    }

    public String getNumeroTel() {
        return numeroTel;
    }

    public void setNumeroTel(String numeroTel) {
        this.numeroTel = numeroTel;
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

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public ArrayList<ModeloGrado> getModeloGrado() {
        return modeloGrado;
    }

    public void setModeloGrado(ArrayList<ModeloGrado> modeloGrado) {
        this.modeloGrado = modeloGrado;
    }

    
}
