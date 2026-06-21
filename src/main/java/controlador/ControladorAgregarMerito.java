/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.DemeritoDAO;
import DAO.MeritoDAO;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.ModeloConducta;
import sesion.Sesion;
import vista.VistaAgregarMerito;

/**
 *
 * @author renec
 */
public class ControladorAgregarMerito {

    private VistaAgregarMerito visAgregarMerito;

    public ControladorAgregarMerito(VistaAgregarMerito visAgregarMerito, String nie, String id, String nombreCompleto) {
        this.visAgregarMerito = visAgregarMerito;
        iniciarVista();
        onEvento();
        llenarCombo();
        cargarDatosEstudiante(nie, id, nombreCompleto);
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
                int id = seleccionado.getIdTipo();
              //  System.out.println("ID seleccionado: " + id);
            }
        });

        visAgregarMerito.btnAgregar.addActionListener(e -> {
            int idSesion = Sesion.getIdPersonal();
            int id = Integer.parseInt(visAgregarMerito.txtidd.getText());
            String nie = visAgregarMerito.txtniee.getText();
            String observacion = visAgregarMerito.txtObservaciones.getText();
            ModeloConducta tipo = (ModeloConducta) visAgregarMerito.ComboDescripcion.getSelectedItem();

            if (tipo == null) {
                JOptionPane.showMessageDialog(null, "Seleccione un tipo de mérito");
                return;
            }

            if (observacion.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese una observación");
                return;
            }

            try {
                MeritoDAO.insertarMerito(id, observacion,idSesion,tipo.getIdTipo());
                MeritoDAO.actualizarPuntos(id);
                JOptionPane.showMessageDialog(null, "Mérito guardado correctamente");
                visAgregarMerito.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });
    }

    private void llenarCombo() {
        List<ModeloConducta> listaConductas = MeritoDAO.obtenerTiposConducta();
        visAgregarMerito.ComboDescripcion.removeAllItems();
        for (ModeloConducta con : listaConductas) {
            visAgregarMerito.ComboDescripcion.addItem(con);
        }
    }

    private void cargarDatosEstudiante(String nie, String id, String nombreCompleto) {
        visAgregarMerito.txtniee.setText(nie);
        visAgregarMerito.txtidd.setText(id);

        visAgregarMerito.txtNombreCompleto.setText(nombreCompleto);
        visAgregarMerito.txtidd.setEditable(false);
        visAgregarMerito.txtNombreCompleto.setEditable(false);
    }
}
