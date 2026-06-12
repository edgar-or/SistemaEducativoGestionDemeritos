package controlador;

import DAO.DocenteDAO;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.VistaAgregarCorreo;

public class ControladorAgregarCorreo {

    private VistaAgregarCorreo vista;
    private DocenteDAO dao = new DocenteDAO();
    private String duiDocente;

    public ControladorAgregarCorreo(String duiDocente, String nombreDocente) {
        this.duiDocente = duiDocente;
        this.vista = new VistaAgregarCorreo();

        // Mostrar el nombre del docente en el label
        vista.lblNombreDocente.setText(nombreDocente);

        onEventos();
        cargarCorreos();

        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    private void onEventos() {
        vista.btnAgregar.addActionListener(e -> agregarCorreo());
        vista.btnEliminar.addActionListener(e -> eliminarCorreo());
        vista.btnSalir.addActionListener(e -> vista.dispose());
    }

    private void agregarCorreo() {
        String correo = vista.txtCorreo.getText().trim();

        if (correo.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                    "Ingrese un correo electrónico.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            dao.insertarCorreo(duiDocente, correo);
            vista.txtCorreo.setText("");
            cargarCorreos();
            JOptionPane.showMessageDialog(vista,
                    "Correo agregado correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error al agregar correo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCorreo() {
        int fila = vista.tablaCorreos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista,
                    "Seleccione un correo de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCorreo = (int) vista.tablaCorreos.getValueAt(fila, 0);

        int confirm = JOptionPane.showConfirmDialog(vista,
                "¿Eliminar este correo?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            dao.eliminarCorreoPorId(idCorreo);
            cargarCorreos();
            JOptionPane.showMessageDialog(vista,
                    "Correo eliminado correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error al eliminar correo: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarCorreos() {
        try {
            List<Object[]> lista = dao.listarCorreosPorDui(duiDocente);
            DefaultTableModel modelo = new DefaultTableModel(
                    new String[]{"ID", "Correo"}, 0) {
                @Override
                public boolean isCellEditable(int r, int c) { return false; }
            };
            for (Object[] fila : lista) {
                modelo.addRow(fila);
            }
            vista.tablaCorreos.setModel(modelo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error al cargar correos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}