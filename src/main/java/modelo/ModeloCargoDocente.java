/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author renec
 */
public class ModeloCargoDocente {

    private int idCargo;
    private String cargo;

    public ModeloCargoDocente(int idCargo, String cargo) {
        this.idCargo = idCargo;
        this.cargo = cargo;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

}
