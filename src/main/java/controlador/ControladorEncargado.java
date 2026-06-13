package controlador;

import DAO.EncargadoDAO;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.ModeloEncardoAlumno;
import vista.VerEncargado;
import vista.VistaPrincipalDirector;


public class ControladorEncargado {

    private VistaPrincipalDirector vistaPrincipal;
    private VerEncargado verEncargado;
    private EncargadoDAO dao = new EncargadoDAO();
    private int duiSeleccionado = -1;
    private ControladorAgregarEncargado controladorAgregarEncargado;

    public ControladorEncargado(VistaPrincipalDirector vistaPrincipal) {
        this.verEncargado = new VerEncargado();
        this.vistaPrincipal = vistaPrincipal;
        this.controladorAgregarEncargado = new ControladorAgregarEncargado(verEncargado, this);
        onEventos();
        llenarTabla();
    }

    private void onEventos() {
        vistaPrincipal.menuEncargado.addActionListener(e -> mostrarVista());
        verEncargado.btnCerrar.addActionListener(e -> verEncargado.dispose());

        this.verEncargado.btnAgregar.addActionListener(e -> {
            controladorAgregarEncargado.limpiarCampos();
            controladorAgregarEncargado.habilitarDui();
            controladorAgregarEncargado.iniciarVista();
        });

        this.verEncargado.btnModificar.addActionListener(e -> modificarEncargado());
        this.verEncargado.btnEliminar.addActionListener(e -> eliminarEncargado());
        this.verEncargado.btnBuscar.addActionListener(e -> buscarEncargado());

        this.verEncargado.txtDui.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                verEncargado.txtNombre.setText("");
                verEncargado.txtNombre.setEnabled(false);
                verEncargado.txtDui.setEnabled(true);
            }
        });

        this.verEncargado.txtNombre.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                verEncargado.txtDui.setText("");
                verEncargado.txtDui.setEnabled(false);
                verEncargado.txtNombre.setEnabled(true);
            }
        });
    }

    public void mostrarVista() {
        verEncargado.setVisible(true);
        verEncargado.setSize(1000, 500);

        Dimension desk = vistaPrincipal.escritorio.getSize();
        int x = (desk.width - 1000) / 2;
        int y = (desk.height - 500) / 2;
        verEncargado.setLocation(x, y);

        vistaPrincipal.escritorio.remove(verEncargado);
        vistaPrincipal.escritorio.add(verEncargado);

        verEncargado.revalidate();
        verEncargado.repaint();
        verEncargado.toFront();
    }

    public void llenarTabla() {
        DefaultTableModel modeloTabla = (DefaultTableModel) verEncargado.tablaEncargados.getModel();
        String[] titulos = {"ID", "DUI", "Primer Nombre", "Segundo Nombre", "Primer Apellido", "Segundo Apellido"};
        modeloTabla.setColumnIdentifiers(titulos);
        modeloTabla.setRowCount(0);

        try {
            List<ModeloEncardoAlumno> lista = dao.listarEncargados();
            for (ModeloEncardoAlumno e : lista) {
                Object[] fila = new Object[]{
                    e.getIdEncargado(),
                    e.getDui(),
                    e.getPrimerNombre(),
                    e.getSegundoNombre(),
                    e.getPrimerApellido(),
                    e.getSegundoApellido()
                };
                modeloTabla.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(verEncargado,
                    "Error al cargar los datos en la tabla: " + e.getMessage(),
                    "Error de Carga",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarEncargado() {
        int filaSeleccionada = verEncargado.tablaEncargados.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(verEncargado,
                    "Por favor, seleccione un encargado de la tabla para modificar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int filaReal = verEncargado.tablaEncargados.convertRowIndexToModel(filaSeleccionada);
            String dui = verEncargado.tablaEncargados.getModel().getValueAt(filaReal, 1).toString();
            ModeloEncardoAlumno encargado = dao.buscarPorDui(dui);

            if (encargado != null) {
                controladorAgregarEncargado.iniciarVistaEdicion(encargado);
            } else {
                JOptionPane.showMessageDialog(verEncargado, "No se encontraron los datos de ese encargado en el sistema.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(verEncargado,
                    "Error al recuperar datos para modificar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            verEncargado.tablaEncargados.clearSelection();
        }
    }

    private void buscarEncargado() {
        DefaultTableModel modelo = (DefaultTableModel) verEncargado.tablaEncargados.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        verEncargado.tablaEncargados.setRowSorter(sorter);

        String dui = verEncargado.txtDui.getText().trim();
        String nombre = verEncargado.txtNombre.getText().trim();

        if (!dui.isEmpty()) {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + dui, 1));
        } else if (!nombre.isEmpty()) {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + nombre, 2, 3, 4, 5));
        } else {
            sorter.setRowFilter(null);
        }
    }

    private void eliminarEncargado() {
        int filaSeleccionada = verEncargado.tablaEncargados.getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(verEncargado,
                    "Por favor, seleccione un encargado de la tabla para eliminar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int filaReal = verEncargado.tablaEncargados.convertRowIndexToModel(filaSeleccionada);
        String dui = verEncargado.tablaEncargados.getModel().getValueAt(filaReal, 1).toString();
        String nombre = verEncargado.tablaEncargados.getModel().getValueAt(filaReal, 2).toString() + " "
                + verEncargado.tablaEncargados.getModel().getValueAt(filaReal, 4).toString();

        int respuesta = JOptionPane.showConfirmDialog(verEncargado,
                "¿Está seguro de que desea eliminar al encargado: " + nombre + " (DUI: " + dui + ")?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (respuesta == JOptionPane.YES_OPTION) {
            try {
                dao.eliminarEncargado(dui);
                JOptionPane.showMessageDialog(verEncargado, "Encargado eliminado correctamente.");
                llenarTabla();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(verEncargado,
                        "Error al intentar eliminar el encargado: " + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                verEncargado.tablaEncargados.clearSelection();
            }
        }
    }
}
