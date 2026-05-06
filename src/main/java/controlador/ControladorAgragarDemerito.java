/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vista.VistaPrincipalMaestros;
import vista.vistaAgregarDemerito;

/**
 *
 * @author renec
 */
public class ControladorAgragarDemerito {
    
    vistaAgregarDemerito visAgrgarDemeritos;
    VistaPrincipalMaestros visPrincipalMaestros;

    public ControladorAgragarDemerito(vistaAgregarDemerito visAgrgarDemeritos, VistaPrincipalMaestros visPPrincipalMaestros) {
        this.visAgrgarDemeritos = visAgrgarDemeritos;
        this.visPrincipalMaestros = visPrincipalMaestros;
        
    }

    public ControladorAgragarDemerito() {
    }
    
    

   

    public void iniciarVistaDemerito() {
        visPrincipalMaestros.btnAgregarDemerito.addActionListener(e->{
            visAgrgarDemeritos.setVisible(true);
            
        
        });
        
        
    }
    
    
    
    
}
