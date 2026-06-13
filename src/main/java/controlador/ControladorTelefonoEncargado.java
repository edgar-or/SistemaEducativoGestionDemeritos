/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.TelEncargadoDAO;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloEncardoAlumno;
import modelo.ModeloTelefonosEncargadosAlumno;
import vista.VerEncargado;
import vista.VistaTelefonoEncargado;

public class ControladorTelefonoEncargado {

    private VistaTelefonoEncargado vista;
    private TelEncargadoDAO dao = new TelEncargadoDAO();
    private int idEncargadoSeleccionado = -1;
    private String nombreEncargadoSeleccionado = "";
    private VerEncargado verEncargado;
    
    public ControladorTelefonoEncargado(VerEncargado verEncargado, ControladorEncargado controladorEncargado) {
        this.vista = new VistaTelefonoEncargado();
        onEvento();
    }

    public void encargadoSeleccionado(int idEncargado, String nombre) {
        this.idEncargadoSeleccionado = idEncargado;
        this.nombreEncargadoSeleccionado = nombre;
    }

    public void iniciarVista() {
        vista.setLocationRelativeTo(null);
        vista.setTitle("Teléfonos de: " + nombreEncargadoSeleccionado);
        vista.txtTel.setText("");
        llenarTablaTelefonos();
        vista.setVisible(true);
    }

    private void onEvento() {
        vista.btnSalir.addActionListener(e -> vista.dispose());
        vista.btnAgregar.addActionListener(e -> agregarTelefono());
        vista.btnEliminar.addActionListener(e -> eliminarTelefono());
    }

    public void llenarTablaTelefonos() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tablaTelefonos.getModel();
        modeloTabla.setColumnIdentifiers(new String[]{"ID Registro", "Teléfono"});
        modeloTabla.setRowCount(0);

        if (idEncargadoSeleccionado == -1) {
            return;
        }

        try {
            List<ModeloTelefonosEncargadosAlumno> lista = dao.listarTelefonosPorEncargado(idEncargadoSeleccionado);
            for (ModeloTelefonosEncargadosAlumno listado : lista) {
                modeloTabla.addRow(new Object[]{
                    listado.getId(),
                    listado.getTelefono()
                });
            }
        } catch (SQLException e) {
            mostrarMensaje("Error al cargar los teléfonos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarTelefono() {
        String telefonoStr = vista.txtTel.getText().trim();

        if (telefonoStr.isEmpty()) {
            mostrarMensaje("Por favor, ingrese un número de teléfono.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //validacion
        int telefonoInt;
        try {
            telefonoStr = telefonoStr.replace("-", "").replace(" ", "");
            telefonoInt = Integer.parseInt(telefonoStr);
        } catch (NumberFormatException e) {
            mostrarMensaje("El teléfono debe contener solo números válidos (sin letras).", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ModeloEncardoAlumno encargadoDummy = new ModeloEncardoAlumno();
            encargadoDummy.setIdEncargado(idEncargadoSeleccionado);

            dao.guardarTelefonoEncargado(new ModeloTelefonosEncargadosAlumno(0, telefonoInt, encargadoDummy));

            mostrarMensaje("Teléfono agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.txtTel.setText("");
            llenarTablaTelefonos();
        } catch (SQLException e) {
            mostrarMensaje("Error al guardar el teléfono: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(vista, mensaje, titulo, tipo);
    }

    private void eliminarTelefono() {
        int fila = vista.tablaTelefonos.getSelectedRow();

        if (fila == -1) {
            mostrarMensaje("Seleccione un teléfono de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idTel = (int) vista.tablaTelefonos.getValueAt(fila, 0);
            dao.eliminarTelefonoEncargado(idTel);

            mostrarMensaje("Teléfono eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            llenarTablaTelefonos();
        } catch (SQLException e) {
            mostrarMensaje("Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
