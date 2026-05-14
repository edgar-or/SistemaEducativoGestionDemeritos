/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author renec
 */
public class ModeloSeccion {

    private int idSeccion;
    private String grado;

    public ModeloSeccion(int idSeccion, String grado) {
        this.idSeccion = idSeccion;
        this.grado = grado;
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public String getGrado() {
        return grado;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

}
