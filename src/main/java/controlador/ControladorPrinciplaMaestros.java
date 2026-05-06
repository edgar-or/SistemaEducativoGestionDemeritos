/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.JFrame;
import vista.VistaPrincipalMaestros;
import vista.vistaAgregarMerito;

/**
 *
 * @author renec
 */
public class ControladorPrinciplaMaestros {
    private VistaPrincipalMaestros visPrincipalMaaestros;
    private ControladorAgragarDemerito controladoragrgarDemerito;

    public ControladorPrinciplaMaestros(VistaPrincipalMaestros visPrincipalMaaestros, ControladorAgragarDemerito controladoragrgarDemerito) {
        this.visPrincipalMaaestros = visPrincipalMaaestros;
        this.controladoragrgarDemerito = new ControladorAgragarDemerito();
        controladoragrgarDemerito.iniciarVistaDemerito();
        
        
        
        
        
    }
    
    public void iniciar() {
        visPrincipalMaaestros.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        visPrincipalMaaestros.setExtendedState(JFrame.MAXIMIZED_BOTH);
        visPrincipalMaaestros.setVisible(true);
        
    }

    private void iniciarVistaDemerito() {
        
        visPrincipalMaaestros.btnAgregarDemerito.addActionListener(e->{
                       
            ControladorAgragarDemerito ctr = new ControladorAgragarDemerito();

        
  
            
        });
    }
    
}
