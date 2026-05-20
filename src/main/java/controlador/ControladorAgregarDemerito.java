/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.JFrame;
import vista.VistaAgregarDemerito;
import vista.VistaPrincipalMaestros;

/**
 *
 * @author renec
 */
public class ControladorAgregarDemerito {
    
    private VistaAgregarDemerito visAgregarDemerito;

    public ControladorAgregarDemerito(VistaAgregarDemerito visAgregarDemeritos) {
        this.visAgregarDemerito = visAgregarDemeritos;
        iniciarVista();
        onEvento();

    }

    private void iniciarVista() {
      visAgregarDemerito.setLocationRelativeTo(null);
      visAgregarDemerito.setVisible(true);
    }

    private void onEvento() {
        visAgregarDemerito.btnCerrar.addActionListener(e->{
        visAgregarDemerito.dispose();
        });
    }

}
    

