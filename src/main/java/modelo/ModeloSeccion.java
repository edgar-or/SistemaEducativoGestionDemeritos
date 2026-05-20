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
    private String seccion;

    public ModeloSeccion(int idSeccion, String seccion) {
        this.idSeccion = idSeccion;
        this.seccion = seccion;
    }

    public ModeloSeccion() {
    }

    public int getIdSeccion() {
        return idSeccion;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setIdSeccion(int idSeccion) {
        this.idSeccion = idSeccion;
    }

    public void setSeccion(String grado) {
        this.seccion = seccion;
    }

}
