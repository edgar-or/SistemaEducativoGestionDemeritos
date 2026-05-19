/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vista.VistaVerEstado;

/**
 *
 * @author renec
 */
public class ControladorVerEstado {
    private VistaVerEstado visVerEstado;

    public ControladorVerEstado(VistaVerEstado visVerEstado) {
        this.visVerEstado = visVerEstado;
        iniciarVista();
        onEvento();
    }

    private void iniciarVista() {
        visVerEstado.setLocationRelativeTo(null);
        visVerEstado.setVisible(true);
    }

    private void onEvento() {
        visVerEstado.btnCerrar.addActionListener(e->{
        visVerEstado.dispose();
        });
    }

    
    
    
}
