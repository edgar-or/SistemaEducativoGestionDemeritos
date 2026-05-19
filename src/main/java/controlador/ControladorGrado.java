/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Dimension;
import vista.VerGrado;
import vista.VistaAgregarAlumno;
import vista.VistaPrincipalDirector;

/**
 *
 * @author estud
 */
public class ControladorGrado {
    private VistaPrincipalDirector vistaPrincipal;
    private VerGrado verGrado;

    public ControladorGrado(VistaPrincipalDirector vistaPrincipal) {
        this.verGrado = new VerGrado();
        this.vistaPrincipal = vistaPrincipal;
        
        onEventos();
    }

   
    
    
    
    private void onEventos() {
        vistaPrincipal.menuGrado.addActionListener(e -> mostrarVista());
        verGrado.btnCerrar.addActionListener(e -> verGrado.dispose());
    }
    public void mostrarVista() {

        verGrado.setVisible(true);

        // 2️⃣ Centrar la vista
        Dimension desktopSize = vistaPrincipal.escritorio.getSize();
        Dimension internal = verGrado.getSize();
        int x = (desktopSize.width - internal.width) / 2;
        int y = (desktopSize.height - internal.height) / 2;
        verGrado.setLocation(x, y);
        vistaPrincipal.escritorio.remove(verGrado);
        vistaPrincipal.escritorio.add(verGrado);

        // 3️⃣ Mostrar y traer al frente
        verGrado.toFront();
        //mostrarRutasTabla(base.getRutas());
        //formaAgregarDepartamentos();
        //activarEventoDepartamento();

    } 
}
