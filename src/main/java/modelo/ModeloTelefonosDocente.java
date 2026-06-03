package modelo;


import modelo.ModeloDocente;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author estud
 */
public class ModeloTelefonosDocente {
    private int idTelefono;
    private String telefono;
    private ModeloDocente modeloDocente;

    public ModeloTelefonosDocente(int idTelefono, String telefono, ModeloDocente modeloDocente) {
        this.idTelefono = idTelefono;
        this.telefono = telefono;
        this.modeloDocente = modeloDocente;
    }

    public int getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(int idTelefono) {
        this.idTelefono = idTelefono;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public ModeloDocente getModeloDocente() {
        return modeloDocente;
    }

    public void setModeloDocente(ModeloDocente modeloDocente) {
        this.modeloDocente = modeloDocente;
    }
    
    
    
    
}
