package controlador;

import DAO.DocenteDAO;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloDocente;
import vista.VistaAgregarTelefono;

public class ControladorAgregarTelefono {

    private VistaAgregarTelefono vista;
    private DocenteDAO dao = new DocenteDAO();
    private String duiDocente;

    public ControladorAgregarTelefono(String duiDocente, String nombreDocente) {
        this.duiDocente = duiDocente;
        this.vista = new VistaAgregarTelefono();

        // Mostrar el nombre del docente en el label
        vista.jLabel3.setText(nombreDocente);

        onEventos();
        cargarTelefonos();

        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }

    private void onEventos() {
        vista.btnAgregar.addActionListener(e -> agregarTelefono());
        vista.btnEliminar.addActionListener(e -> eliminarTelefono());
        vista.btnSalir.addActionListener(e -> vista.dispose());
    }

    private void agregarTelefono() {
        String telefono = vista.txtAgregar.getText().trim();

        
        if (telefono.isEmpty()) {
            JOptionPane.showMessageDialog(vista,
                    "Ingrese un número de teléfono.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }


        if (telefono.length() != 9 || telefono.charAt(4) != '-') {
            JOptionPane.showMessageDialog(vista,
                    "El teléfono no es válido.\nEjemplo: 7894-5612",
                    "Formato inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            dao.insertarTelefono(duiDocente, telefono);
            vista.txtAgregar.setText("");
            cargarTelefonos();
            JOptionPane.showMessageDialog(vista,
                    "Teléfono agregado correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error al agregar teléfono: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarTelefono() {
        int fila = vista.tablaTelefonos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista,
                    "Seleccione un teléfono de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idTelefono = (int) vista.tablaTelefonos.getValueAt(fila, 0);

        int confirm = JOptionPane.showConfirmDialog(vista,
                "¿Eliminar este teléfono?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            dao.eliminarTelefonoPorId(idTelefono);
            cargarTelefonos();
            JOptionPane.showMessageDialog(vista,
                    "Teléfono eliminado correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error al eliminar teléfono: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarTelefonos() {
        try {
            List<Object[]> lista = dao.listarTelefonosPorDui(duiDocente);
            DefaultTableModel modelo = new DefaultTableModel(
                    new String[]{"ID", "Teléfono"}, 0) {
                @Override
                public boolean isCellEditable(int r, int c) { return false; }
            };
            for (Object[] fila : lista) {
                modelo.addRow(fila);
            }
            vista.tablaTelefonos.setModel(modelo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(vista,
                    "Error al cargar teléfonos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}