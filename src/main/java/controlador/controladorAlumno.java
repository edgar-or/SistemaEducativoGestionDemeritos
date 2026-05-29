/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Dimension;
import modelo.ModeloAlumno;
import services.AlumnoService;
import vista.VistaAgregarAlumno;
import vista.VistaAlumno;
import vista.VistaPrincipalDirector;

/**
 *
 * @author ayala
 */
public class ControladorAlumno {

    private VistaAlumno vistaAlumno;
    private VistaPrincipalDirector vistaPrincipal;
    private AlumnoService service = new AlumnoService();

    public ControladorAlumno(VistaPrincipalDirector vistaPrincipal) {
        this.vistaAlumno = new VistaAlumno();
        this.vistaPrincipal = vistaPrincipal;
        onEventos();
    }

    private void onEventos() {
        vistaPrincipal.menuAlumno.addActionListener(e -> mostrarVista());
        vistaAlumno.Salir.addActionListener(e -> vistaAlumno.dispose());
    }

    public void mostrarVista() {

        vistaAlumno.setVisible(true);

        
        Dimension desktopSize = vistaPrincipal.escritorio.getSize();
        Dimension internal = vistaAlumno.getSize();
        int x = (desktopSize.width - internal.width) / 2;
        int y = (desktopSize.height - internal.height) / 2;
        vistaAlumno.setLocation(x, y);
        vistaPrincipal.escritorio.remove(vistaAlumno);
        vistaPrincipal.escritorio.add(vistaAlumno);

      //⃣ Mostrar y traer al frente
        vistaAlumno.toFront();
        //mostrarRutasTabla(base.getRutas());
        //formaAgregarDepartamentos();
        //activarEventoDepartamento();

    }

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
