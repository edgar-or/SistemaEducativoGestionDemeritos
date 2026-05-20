/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vista.VistaAgregarSeccion;

/**
 *
 * @author ayala
 */
public class ControladorAgregarSeccion {

    VistaAgregarSeccion visAgregarSeccion;
    ControladorGrado controladorGrado; 

    public ControladorAgregarSeccion(ControladorGrado controlGrado) {
        visAgregarSeccion = new VistaAgregarSeccion();
        this.controladorGrado =  controlGrado; 

        eventos();
    }

    private void eventos() {
        visAgregarSeccion.btnCerrar.addActionListener(e -> cerrarVista());

    }

    public void iniciar() {

        visAgregarSeccion.setVisible(true);
        visAgregarSeccion.setLocationRelativeTo(null);
    }

    private void cerrarVista() {
        visAgregarSeccion.dispose();
    }

    public void registrarSeccion(){
        
    }
    
}
