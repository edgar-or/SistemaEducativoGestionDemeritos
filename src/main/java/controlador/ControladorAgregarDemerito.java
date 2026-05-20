/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.DemeritoDAO;
import java.util.List;
import javax.swing.JFrame;
import modelo.ModeloConducta;
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
        llenarCombo();

    }

    private void iniciarVista() {
        visAgregarDemerito.setLocationRelativeTo(null);
        visAgregarDemerito.setVisible(true);
    }

    private void onEvento() {
        
        visAgregarDemerito.btnCerrar.addActionListener(e -> {
            visAgregarDemerito.dispose();
        });
        
        visAgregarDemerito.ComboDescripcion.addActionListener(e -> {
            ModeloConducta seleccionado = (ModeloConducta) visAgregarDemerito.ComboDescripcion.getSelectedItem();
            if (seleccionado != null) {
                int id = seleccionado.getIdTipoConducta();
                System.out.println("ID seleccionado: " + id);
            }
        });
         
        
    }

    private void llenarCombo() {

        List<ModeloConducta> listaConductas = DemeritoDAO.obtenerTiposConducta();

        for (ModeloConducta con : listaConductas) {
            visAgregarDemerito.ComboDescripcion.addItem(con);
        }
    }

   
}
