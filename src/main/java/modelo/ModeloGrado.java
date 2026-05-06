/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author renec
 */
public class ModeloGrado {
    
    private int idGrado;
    private String grado;

    public ModeloGrado(int idGrado, String grado) {
        this.idGrado = idGrado;
        this.grado = grado;
    }

    public ModeloGrado() {
    }
    

    public int getIdGrado() {
        return idGrado;
    }

    public String getGrado() {
        return grado;
    }

    public void setIdGrado(int idGrado) {
        this.idGrado = idGrado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }
    
    
    
}
