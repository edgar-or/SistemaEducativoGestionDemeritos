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
    private ModeloGrado grado;

    // Constructor básico (id + nombre de sección)
    public ModeloSeccion(int idSeccion, String seccion) {
        this.idSeccion = idSeccion;
        this.seccion = seccion;
    }

    // Constructor completo (id + sección + grado)
    public ModeloSeccion(int idSeccion, String seccion, ModeloGrado grado) {
        this.idSeccion = idSeccion;
        this.seccion = seccion;
        this.grado = grado;
    }

    public int getIdSeccion() { return idSeccion; }
    public void setIdSeccion(int idSeccion) { this.idSeccion = idSeccion; }

    public String getSeccion() { return seccion; }
    public void setSeccion(String seccion) { this.seccion = seccion; }

    public ModeloGrado getGrado() { return grado; }
    public void setGrado(ModeloGrado grado) { this.grado = grado; }

    @Override
    public String toString() {
        return  this.seccion;
    }
    
    
    
}
