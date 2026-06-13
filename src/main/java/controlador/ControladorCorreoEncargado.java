/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.CorreoEncargadoDAO;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloCorreoEncargadoAlumno;
import modelo.ModeloEncardoAlumno;
import vista.VerEncargado;
import vista.VistaCorreoEncargado;

/**
 *
 * @author estud
 */
public class ControladorCorreoEncargado {

    private VistaCorreoEncargado vista;
    private CorreoEncargadoDAO dao = new CorreoEncargadoDAO();
    private int idEncargadoSeleccionado = -1;
    private String nombreEncargadoSeleccionado = "";

    public ControladorCorreoEncargado(VerEncargado verEncargado, ControladorEncargado controladorEncargado) {
        this.vista = new VistaCorreoEncargado();
        onEvento();
    }

    public void encargadoSeleccionado(int idEncargado, String nombre) {
        this.idEncargadoSeleccionado = idEncargado;
        this.nombreEncargadoSeleccionado = nombre;
    }

    public void iniciarVista() {
        vista.setLocationRelativeTo(null);
        vista.setTitle("Correos de: " + nombreEncargadoSeleccionado); 
        vista.txtCorreo.setText("");

        llenarTablaCorreos();

        vista.setVisible(true);
    }

    private void onEvento() {
        vista.btnSalir.addActionListener(e -> vista.dispose());
        vista.btnAgregar.addActionListener(e -> agregarCorreo());
        vista.btnEliminar.addActionListener(e -> eliminarCorreo());
    }

    public void llenarTablaCorreos() {
        DefaultTableModel modeloTabla = (DefaultTableModel) vista.tablaCorreo.getModel();
        modeloTabla.setColumnIdentifiers(new String[]{"ID Registro", "Correo Electrónico"});
        modeloTabla.setRowCount(0);

        if (idEncargadoSeleccionado == -1) {
            return;
        }

        try {
            List<ModeloCorreoEncargadoAlumno> lista = dao.listarCorreosPorEncargado(idEncargadoSeleccionado);
            for (ModeloCorreoEncargadoAlumno listado : lista) {
                modeloTabla.addRow(new Object[]{
                    listado.getId(), // El ID del registro de correo
                    listado.getCorreoEncargado() // La dirección de correo (String)
                });
            }
        } catch (SQLException e) {
            mostrarMensaje("Error al cargar los correos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarCorreo() {
        String correoStr = vista.txtCorreo.getText().trim();

        if (correoStr.isEmpty()) {
            mostrarMensaje("Por favor, ingrese un correo electrónico.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validación de formato de correo electrónico
        if (!correoStr.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")) {
            mostrarMensaje("Por favor, ingrese un correo electrónico válido (ejemplo@dominio.com).", "Error de formato", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ModeloEncardoAlumno encargadoDummy = new ModeloEncardoAlumno();
            encargadoDummy.setIdEncargado(idEncargadoSeleccionado);

            dao.guardarCorreoEncargado(new ModeloCorreoEncargadoAlumno(0, correoStr, encargadoDummy));

            mostrarMensaje("Correo agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            vista.txtCorreo.setText("");
            llenarTablaCorreos();
        } catch (SQLException e) {
            mostrarMensaje("Error al guardar el correo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCorreo() {
        int fila = vista.tablaCorreo.getSelectedRow();

        if (fila == -1) {
            mostrarMensaje("Seleccione un correo de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idCorreo = (int) vista.tablaCorreo.getValueAt(fila, 0);
            dao.eliminarCorreoEncargado(idCorreo);

            mostrarMensaje("Correo eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            llenarTablaCorreos(); // Refresca la tabla
        } catch (SQLException e) {
            mostrarMensaje("Error al eliminar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarMensaje(String mensaje, String titulo, int tipo) {
        JOptionPane.showMessageDialog(vista, mensaje, titulo, tipo);
    }
}
