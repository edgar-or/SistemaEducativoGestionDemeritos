/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.util.ArrayList;

/**
 *
 * @author estud
 */
public class Usuario {

    private int idUsuario;
    private String usuario;
    private String contrasenia;
    private ArrayList<ModeloDocente> docente;

    public Usuario(int idUsuario, String usuario, String contrasenia, ArrayList<ModeloDocente> docente) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.docente = docente;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public ArrayList<ModeloDocente> getDocente() {
        return docente;
    }

    public void setDocente(ArrayList<ModeloDocente> docente) {
        this.docente = docente;
    }
    
    
    
    
}
