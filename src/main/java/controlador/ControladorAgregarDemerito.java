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

    public ControladorAgregarDemerito(VistaAgregarDemerito visAgregarDemeritos, String nie, String nombreCompleto) {
        this.visAgregarDemerito = visAgregarDemeritos;
        iniciarVista();
        onEvento();
        llenarCombo();
        cargarDatosEstudiante(nie, nombreCompleto);
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
                System.out.println("ID seleccionado: " + id);
            }
        });

        visAgregarDemerito.btnAgregar.addActionListener(e -> {
            String duiSesion = Sesion.getDuiPersonal();
            String nie = visAgregarDemerito.txtNie.getText();
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

            if (duiSesion == null || duiSesion.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Error: sesión no encontrada");
                return;
            }

            DemeritoDAO.insertarDemerito(nie, observacion, duiSesion, tipo.getIdTipo());
            DemeritoDAO.actualizarPuntos(nie);

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

    private void cargarDatosEstudiante(String nie, String nombreCompleto) {
        this.visAgregarDemerito.txtNie.setText(nie);
        this.visAgregarDemerito.txtNombreCompleto.setText(nombreCompleto);
        this.visAgregarDemerito.txtNie.setEditable(false);
        this.visAgregarDemerito.txtNombreCompleto.setEditable(false);
    }
}
