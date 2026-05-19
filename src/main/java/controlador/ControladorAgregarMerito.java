/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vista.VistaAgregarMerito;

/**
 *
 * @author renec
 */
public class ControladorAgregarMerito {
    private VistaAgregarMerito visAgregarMerito;

    public ControladorAgregarMerito(VistaAgregarMerito visAgregarMerito) {
        this.visAgregarMerito = visAgregarMerito;
        iniciarVista();
        onEvento();
       
    }

    private void iniciarVista() {
        visAgregarMerito.setLocationRelativeTo(null);
        visAgregarMerito.setVisible(true);
    }

    private void onEvento() {
        visAgregarMerito.btnCerrar.addActionListener(e->{
        visAgregarMerito.dispose();
        
        });
    }

    
    

    
    
    
}
