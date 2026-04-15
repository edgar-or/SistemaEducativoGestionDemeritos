/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.JFrame;
import vista.vistaPrincipalDirector;

/**
 *
 * @author estud
 */
public class controladorPrincipal {
    private vistaPrincipalDirector vista;

    public controladorPrincipal(vistaPrincipalDirector vista) {
        this.vista = vista;
    }
    
    public void iniciar() {
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vista.setVisible(true);
    }
}
