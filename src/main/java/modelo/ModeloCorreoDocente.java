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
public class ModeloCorreoDocente {
    private int idCorreo;
    private String correo;
    private ModeloDocente modeloDodente;

    public ModeloCorreoDocente() {
    }

    
    public ModeloCorreoDocente(int idCorreo, String correo, ModeloDocente modeloDodente) {
        this.idCorreo = idCorreo;
        this.correo = correo;
        this.modeloDodente = modeloDodente;
    }

    public int getIdCorreo() {
        return idCorreo;
    }

    public void setIdCorreo(int idCorreo) {
        this.idCorreo = idCorreo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public ModeloDocente getModeloDodente() {
        return modeloDodente;
    }

    public void setModeloDodente(ModeloDocente modeloDodente) {
        this.modeloDodente = modeloDodente;
    }
    
    
    
    
}
