/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import vista.VistaAgregarMaestros;

/**
 *
 * @author renec
 */
public class ControladorRegistrarDocentes {

    private VistaAgregarMaestros visAgregarProfes;

    public ControladorRegistrarDocentes(VistaAgregarMaestros visAgregarProfes) {
        this.visAgregarProfes = visAgregarProfes;
        iniciarVista();
        onEvento();
    }

    public void iniciarVista() {
        visAgregarProfes.setLocationRelativeTo(null);
        visAgregarProfes.setVisible(true);
    }

    private void onEvento() {

        visAgregarProfes.btnSalir.addActionListener(e -> {
            visAgregarProfes.dispose();
        });
    }
}
