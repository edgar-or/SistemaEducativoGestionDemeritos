/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.DemeritoDAO;
import java.util.List;
import modelo.ModeloConducta;
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
        llenarCombo();
    }

    private void iniciarVista() {
        visAgregarMerito.setLocationRelativeTo(null);
        visAgregarMerito.setVisible(true);
    }

    private void onEvento() {
        visAgregarMerito.btnCerrar.addActionListener(e -> {
            visAgregarMerito.dispose();

        });
        visAgregarMerito.ComboDescripcion.addActionListener(e -> {
            ModeloConducta seleccionado = (ModeloConducta) visAgregarMerito.ComboDescripcion.getSelectedItem();
            if (seleccionado != null) {
                int id = seleccionado.getIdTipoConducta();
                System.out.println("ID seleccionado: " + id);
            }
        });
         
    }
    


    private void llenarCombo() {

        List<ModeloConducta> listaConductas = DemeritoDAO.obtenerTiposConducta();

        for (ModeloConducta con : listaConductas) {
            visAgregarMerito.ComboDescripcion.addItem(con);
        }
    }
}
