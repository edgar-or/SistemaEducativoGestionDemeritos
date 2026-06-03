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
public class ModeloCargoDocente {

    private int idCargo;
    private String cargo;
    private ArrayList<ModeloDocente> modeloDocente;

    public ModeloCargoDocente() {
    }

    public ModeloCargoDocente(int idCargo, String cargo, ArrayList<ModeloDocente> modeloDocente) {
        this.idCargo = idCargo;
        this.cargo = cargo;
        this.modeloDocente = modeloDocente;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public ArrayList<ModeloDocente> getModeloDocente() {
        return modeloDocente;
    }

    public void setModeloDocente(ArrayList<ModeloDocente> modeloDocente) {
        this.modeloDocente = modeloDocente;
    }
    
    

}
