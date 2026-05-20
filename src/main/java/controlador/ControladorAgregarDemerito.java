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
    
    private VistaAgregarDemerito visAgrgarDemerito;

    public ControladorAgregarDemerito(VistaAgregarDemerito visAgrgarDemeritos) {
        this.visAgrgarDemerito = visAgrgarDemeritos;
        iniciarVista();
        onEvento();

    }

    private void iniciarVista() {
      visAgrgarDemerito.setLocationRelativeTo(null);
      visAgrgarDemerito.setVisible(true);
    }

    private void onEvento() {
        visAgrgarDemerito.btnCerrar.addActionListener(e->{
        visAgrgarDemerito.dispose();
        });
    }

}
    

