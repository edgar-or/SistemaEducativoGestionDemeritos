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
public class ModeloGrado {

    private int idGrado;
    private String grado;
    private ModeloCentroEscolar centroEscolar;    
    private ArrayList<ModeloSeccion> modeloSecion;

    public ModeloGrado() {
    }

    
    public ModeloGrado(int idGrado, String grado, ModeloCentroEscolar centroEscolar, ArrayList<ModeloSeccion> modeloSecion) {
        this.idGrado = idGrado;
        this.grado = grado;
        this.centroEscolar = centroEscolar;
        this.modeloSecion = modeloSecion;
    }

    public int getIdGrado() {
        return idGrado;
    }

    public void setIdGrado(int idGrado) {
        this.idGrado = idGrado;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public ModeloCentroEscolar getCentroEscolar() {
        return centroEscolar;
    }

    public void setCentroEscolar(ModeloCentroEscolar centroEscolar) {
        this.centroEscolar = centroEscolar;
    }

    public ArrayList<ModeloSeccion> getModeloSecion() {
        return modeloSecion;
    }

    public void setModeloSecion(ArrayList<ModeloSeccion> modeloSecion) {
        this.modeloSecion = modeloSecion;
    }
    
    
    
}
