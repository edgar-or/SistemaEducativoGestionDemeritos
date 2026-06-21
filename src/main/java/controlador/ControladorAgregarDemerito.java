/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.DemeritoDAO;
import dto.LoginResultadoDto;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.ModeloConducta;
import modelo.ModeloDocente;
import sesion.Sesion;
import vista.VistaAgregarDemerito;
import vista.VistaPrincipalMaestros;

/**
 *
 * @author renec
 */
public class ControladorAgregarDemerito {

    private VistaAgregarDemerito visAgregarDemerito;

    public ControladorAgregarDemerito(VistaAgregarDemerito visAgregarDemeritos, String nie, String id, String nombreCompleto) {
        this.visAgregarDemerito = visAgregarDemeritos;
        iniciarVista();
        onEvento();
        llenarCombo();
        cargarDatosEstudiante(nie, id, nombreCompleto);
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
                int id = seleccionado.getIdTipo();
                // System.out.println("ID seleccionado: " + id);
            }
        });

        visAgregarDemerito.btnAgregar.addActionListener(e -> {
            int idSesion = Sesion.getIdPersonal();
            String nie = visAgregarDemerito.txtNie.getText();
            int id = Integer.parseInt(visAgregarDemerito.txtidM.getText());
            String observacion = visAgregarDemerito.txtObservaciones.getText();
            ModeloConducta tipo = (ModeloConducta) visAgregarDemerito.ComboDescripcion.getSelectedItem();

            if (tipo == null) {
                JOptionPane.showMessageDialog(null, "Seleccione un tipo de demérito");
                return;
            }

            if (observacion.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese una observación");
                return;
            }

            if (idSesion == -1) {
                JOptionPane.showMessageDialog(null, "Error: sesión no encontrada");
                return;
            }

            DemeritoDAO.insertarDemerito(id, observacion, idSesion, tipo.getIdTipo());
            DemeritoDAO.actualizarPuntos(id);
            //System.out.println("id estudiante"+ id+ "id sesion" + idSesion+ "tipo"+tipo.getIdTipo());
            JOptionPane.showMessageDialog(null, "Demérito guardado correctamente");
            visAgregarDemerito.dispose();
        });
    }

    private void llenarCombo() {
        List<ModeloConducta> listaConductas = DemeritoDAO.obtenerTiposConducta();
        visAgregarDemerito.ComboDescripcion.removeAllItems();
        for (ModeloConducta con : listaConductas) {
            visAgregarDemerito.ComboDescripcion.addItem(con);
        }
    }

    private void cargarDatosEstudiante(String nie, String id, String nombreCompleto) {
        this.visAgregarDemerito.txtidM.setText(id);
        this.visAgregarDemerito.txtNie.setText(nie);
        this.visAgregarDemerito.txtNombreCompleto.setText(nombreCompleto);
        this.visAgregarDemerito.txtidM.setEditable(false);
        this.visAgregarDemerito.txtNie.setEditable(false);
        this.visAgregarDemerito.txtNombreCompleto.setEditable(false);
    }
}
