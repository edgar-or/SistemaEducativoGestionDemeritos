/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.ModeloAlumno;
import services.AlumnoService;

/**
 *
 * @author ayala
 */
public class ControladorAlumno {

    AlumnoService service = new AlumnoService();

    public void insertarAlumno() {
        //int nie, String nombre, String apelliddos, int idGrado, int duiEncargado, int totalPuntos

        ModeloAlumno alumno = new ModeloAlumno(1, "Edgar", "Ayala", 1, "06908504-1", 0);
        try {
            service.insertarAlumno(alumno);
            System.out.println("Guardado correctamente");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
