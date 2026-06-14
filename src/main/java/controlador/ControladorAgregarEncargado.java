/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.EncargadoDAO;
import javax.swing.JOptionPane;
import modelo.ModeloEncardoAlumno;
import vista.VerEncargado;
import vista.VistaAgregarEncargado;

/**
 *
 * @author estud
 */
public class ControladorAgregarEncargado {

    private VistaAgregarEncargado vistaAgregarEncargado;
    private VerEncargado verEncargado;
    private ControladorEncargado controladorEncargado;
    private boolean esEdicion = false;
    private int idEncargadoEditar = -1;

    public ControladorAgregarEncargado(VerEncargado verEncargado, ControladorEncargado controladorEncargado) {
        this.vistaAgregarEncargado = new VistaAgregarEncargado();
        this.verEncargado = verEncargado;
        this.controladorEncargado = controladorEncargado;
        onEvento();
    }

    public void iniciarVista() {
        this.esEdicion = false;
        this.idEncargadoEditar = -1;
        vistaAgregarEncargado.setLocationRelativeTo(null);
        vistaAgregarEncargado.setVisible(true);
    }

    public void iniciarVistaEdicion(ModeloEncardoAlumno modelo) {
        this.esEdicion = true;
        this.idEncargadoEditar = modelo.getIdEncargado();
        cargarDatosFormulario(modelo);
        vistaAgregarEncargado.txtDui.setEditable(true);
        vistaAgregarEncargado.setLocationRelativeTo(null);
        vistaAgregarEncargado.setVisible(true);
    }

    private void onEvento() {
        vistaAgregarEncargado.btnSalir.addActionListener(e -> {
            limpiarCampos();
            this.esEdicion = false;
            this.idEncargadoEditar = -1;
            vistaAgregarEncargado.txtDui.setEditable(true);
            vistaAgregarEncargado.dispose();
        });

        vistaAgregarEncargado.btnGuardar.addActionListener(e -> {
            ejecutarRegistro();
        });
    }


    private void ejecutarRegistro() {
        // 1. VALIDACIÓN GENERAL: Limpieza de espacios y campos obligatorios mínimos
        String dui = vistaAgregarEncargado.txtDui.getText().trim();
        String primerNombre = vistaAgregarEncargado.txtPrimerNombre.getText().trim();
        String primerApellido = vistaAgregarEncargado.txtPrimerApellido.getText().trim();

        if (dui.isEmpty()) {
            JOptionPane.showMessageDialog(vistaAgregarEncargado, "El campo DUI es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!dui.matches("^\\d{8}-\\d$")) {
            JOptionPane.showMessageDialog(vistaAgregarEncargado, "El DUI no tiene un formato válido (ejemplo: 12345678-9).", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (primerNombre.isEmpty()) {
            JOptionPane.showMessageDialog(vistaAgregarEncargado, "El Primer Nombre es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (primerApellido.isEmpty()) {
            JOptionPane.showMessageDialog(vistaAgregarEncargado, "El Primer Apellido es obligatorio.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ModeloEncardoAlumno modelo = new ModeloEncardoAlumno();

        if (esEdicion) {
            modelo.setIdEncargado(this.idEncargadoEditar);
        }

        modelo.setDui(dui);
        modelo.setPrimerNombre(primerNombre);
        modelo.setSegundoNombre(vistaAgregarEncargado.txtSeundoNombre.getText().trim());
        modelo.setPrimerApellido(primerApellido);
        modelo.setSegundoApellido(vistaAgregarEncargado.txtSegundoApellido.getText().trim());
        modelo.setDepartamento(vistaAgregarEncargado.txtDepartamentos.getText().trim());
        modelo.setMunicipio(vistaAgregarEncargado.txtMunicipio.getText().trim());
        modelo.setDistrito(vistaAgregarEncargado.txtDistrito.getText().trim());
        modelo.setCanton(vistaAgregarEncargado.txtcanton.getText().trim());
        modelo.setCaserio(vistaAgregarEncargado.txtCaserio.getText().trim());
        modelo.setCalle(vistaAgregarEncargado.txtCalle.getText().trim());
        modelo.setNumCasa(vistaAgregarEncargado.txtNumeroCasa.getText().trim());

        EncargadoDAO dao = new EncargadoDAO();

        try {
            if (esEdicion) {
                dao.modificarEncargado(modelo);
                JOptionPane.showMessageDialog(vistaAgregarEncargado, "¡Encargado modificado con éxito!");
            } else {
                if (dao.buscarPorDui(dui) != null) {
                    JOptionPane.showMessageDialog(vistaAgregarEncargado, "El DUI ingresado ya se encuentra registrado en el sistema.", "DUI Duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                dao.insertarEncargado(modelo);
                JOptionPane.showMessageDialog(vistaAgregarEncargado, "¡Encargado registrado con éxito!");
            }

            if (controladorEncargado != null) {
                controladorEncargado.llenarTabla();
            }

            limpiarCampos();
            this.idEncargadoEditar = -1;
            vistaAgregarEncargado.dispose();

        } catch (java.sql.SQLException e) {
            JOptionPane.showMessageDialog(vistaAgregarEncargado,
                    "Error al procesar el registro en la base de datos: " + e.getMessage(),
                    "Error SQL",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            if (verEncargado != null && verEncargado.tablaEncargados != null) {
                verEncargado.tablaEncargados.clearSelection();
            }
        }
    }

    private void cargarDatosFormulario(ModeloEncardoAlumno modelo) {
        vistaAgregarEncargado.txtDui.setEditable(true);
        vistaAgregarEncargado.txtDui.setText(modelo.getDui());
        vistaAgregarEncargado.txtPrimerNombre.setText(modelo.getPrimerNombre());
        vistaAgregarEncargado.txtSeundoNombre.setText(modelo.getSegundoNombre());
        vistaAgregarEncargado.txtPrimerApellido.setText(modelo.getPrimerApellido());
        vistaAgregarEncargado.txtSegundoApellido.setText(modelo.getSegundoApellido());
        vistaAgregarEncargado.txtDepartamentos.setText(modelo.getDepartamento());
        vistaAgregarEncargado.txtMunicipio.setText(modelo.getMunicipio());
        vistaAgregarEncargado.txtDistrito.setText(modelo.getDistrito());
        vistaAgregarEncargado.txtcanton.setText(modelo.getCanton());
        vistaAgregarEncargado.txtCaserio.setText(modelo.getCaserio());
        vistaAgregarEncargado.txtCalle.setText(modelo.getCalle());
        vistaAgregarEncargado.txtNumeroCasa.setText(modelo.getNumCasa());
    }

    public void limpiarCampos() {
        vistaAgregarEncargado.txtDui.setText("");
        vistaAgregarEncargado.txtPrimerNombre.setText("");
        vistaAgregarEncargado.txtSeundoNombre.setText("");
        vistaAgregarEncargado.txtPrimerApellido.setText("");
        vistaAgregarEncargado.txtSegundoApellido.setText("");
        vistaAgregarEncargado.txtDepartamentos.setText("");
        vistaAgregarEncargado.txtMunicipio.setText("");
        vistaAgregarEncargado.txtDistrito.setText("");
        vistaAgregarEncargado.txtcanton.setText("");
        vistaAgregarEncargado.txtCaserio.setText("");
        vistaAgregarEncargado.txtCalle.setText("");
        vistaAgregarEncargado.txtNumeroCasa.setText("");
        vistaAgregarEncargado.txtDui.requestFocus();
    }

    public void habilitarDui() {
        vistaAgregarEncargado.txtDui.setEditable(true);
    }
}
