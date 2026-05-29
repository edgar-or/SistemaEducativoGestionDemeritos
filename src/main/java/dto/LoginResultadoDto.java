/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import modelo.Login;
import modelo.ModeloCargoDocente;
import modelo.ModeloDocente;

/**
 *
 * @author ayala
 */
public class LoginResultadoDto {
    
    private Login usuario; 
    private ModeloCargoDocente cargoDocente; 
    private ModeloDocente modeloDocente; 

    public LoginResultadoDto() {
    }

    public Login getUsuario() {
        return usuario;
    }

    public void setUsuario(Login usuario) {
        this.usuario = usuario;
    }

    public ModeloCargoDocente getCargoDocente() {
        return cargoDocente;
    }

    public void setCargoDocente(ModeloCargoDocente cargoDocente) {
        this.cargoDocente = cargoDocente;
    }

    public ModeloDocente getModeloDocente() {
        return modeloDocente;
    }

    public void setModeloDocente(ModeloDocente modeloDocente) {
        this.modeloDocente = modeloDocente;
    }

    
    
}
