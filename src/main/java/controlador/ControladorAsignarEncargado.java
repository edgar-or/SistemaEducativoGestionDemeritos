/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Dimension;
import vista.VistaAsignarEncargado;
import vista.VistaPrincipalDirector;

/**
 *
 * @author ayala
 */
public class ControladorAsignarEncargado {

    private VistaAsignarEncargado visAsignarEncargado;
    private VistaPrincipalDirector vistaPrincipal;

    public ControladorAsignarEncargado(VistaPrincipalDirector vistaPrincipal) {
        
        visAsignarEncargado = new VistaAsignarEncargado(); 

    this.vistaPrincipal = vistaPrincipal;

        cargarTabla();

    }

    public void iniciarVista() {

        visAsignarEncargado.setVisible(true);
        Dimension desk = vistaPrincipal.escritorio.getSize();
        Dimension win = visAsignarEncargado.getSize();
        visAsignarEncargado.setLocation((desk.width - win.width) / 2, (desk.height - win.height) / 2);
        vistaPrincipal.escritorio.remove(visAsignarEncargado);
        vistaPrincipal.escritorio.add(visAsignarEncargado);
        visAsignarEncargado.toFront();

    }

    private void cargarTabla() {

    }

}
