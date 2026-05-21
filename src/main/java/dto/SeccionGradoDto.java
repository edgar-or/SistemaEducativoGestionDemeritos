/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import modelo.ModeloGrado;
import modelo.ModeloSeccion;

/**
 *
 * @author ayala
 */
public class SeccionGradoDto {
    
   private ModeloGrado grado; 
   private ModeloSeccion seccion; 

    public SeccionGradoDto() {
    }

    public ModeloGrado getGrado() {
        return grado;
    }

    public void setGrado(ModeloGrado grado) {
        this.grado = grado;
    }

    public ModeloSeccion getSeccion() {
        return seccion;
    }

    public void setSeccion(ModeloSeccion seccion) {
        this.seccion = seccion;
    }
   
   


    
}
