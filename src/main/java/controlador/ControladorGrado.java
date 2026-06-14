package controlador;

import DAO.GradoDAO;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloGrado;
import vista.VerGrado;
import vista.VistaPrincipalDirector;

public class ControladorGrado {

    private VistaPrincipalDirector vistaPrincipal;
    private VerGrado verGrado;
    private GradoDAO dao = new GradoDAO();
    private int idGradoSeleccionado = -1;

    private ControladorAgregarSeccion controlAgregarSeccion;

    public ControladorGrado(VistaPrincipalDirector vistaPrincipal) {
        this.verGrado = new VerGrado();
        this.vistaPrincipal = vistaPrincipal;
        onEventos();
    }

    private void onEventos() {
        vistaPrincipal.menuGrado.addActionListener(e -> mostrarVista());
        verGrado.btnCerrar.addActionListener(e -> verGrado.dispose());
        verGrado.btnGuardarGrado.addActionListener(e -> guardarGrado());
        verGrado.btnModificar.addActionListener(e -> modificarGrado());
        verGrado.btnEliminar.addActionListener(e -> eliminarGrado());

        verGrado.btnAgregarSeccion.addActionListener(e -> mostrarVistaAgregarSeccion());

        verGrado.tablaGrados.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = verGrado.tablaGrados.getSelectedRow();
                if (fila >= 0) {
                    idGradoSeleccionado = (int) verGrado.tablaGrados.getValueAt(fila, 0);
                    verGrado.comboGrados.setSelectedItem(verGrado.tablaGrados.getValueAt(fila, 1));
                }
            }
        });
    }

    public int obtenerIdGradoDeTabla() {

        int fila = verGrado.tablaGrados.getSelectedRow();

        if (fila >= 0) {
            return (int) verGrado.tablaGrados.getValueAt(fila, 0);
        }

        return -1;
    }

    public void mostrarVistaAgregarSeccion() {

        if (obtenerIdGradoDeTabla() != -1) {
            controlAgregarSeccion = new ControladorAgregarSeccion(this);
            controlAgregarSeccion.iniciar();
        } else {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un grado de la tabla");
        }

    }

    public void mostrarVista() {
        verGrado.setVisible(true);
        verGrado.setSize(1000, 400); 

        Dimension desktopSize = vistaPrincipal.escritorio.getSize();
        Dimension internal = verGrado.getSize();
        verGrado.setLocation(
                (desktopSize.width - internal.width) / 2,
                (desktopSize.height - internal.height) / 2
        );
        vistaPrincipal.escritorio.remove(verGrado);
        vistaPrincipal.escritorio.add(verGrado);
        verGrado.toFront();
        cargarComboGrados();
        cargarTabla();
    }

    private void cargarComboGrados() {
        verGrado.comboGrados.removeAllItems();
        for (String g : new String[]{"Primero", "Segundo", "Tercero", "Cuarto", "Quinto", "Sexto", "Septimo", "Octavo", "Noveno", "1° Bachillerato", "2° Bachillerato"}) {
            verGrado.comboGrados.addItem(g);
        }
    }

    private void cargarTabla() {
        DefaultTableModel modelo = new DefaultTableModel(new String[]{"ID", "Grado"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        try {
            List<ModeloGrado> lista = dao.listarGrados();
            for (ModeloGrado g : lista) {
                modelo.addRow(new Object[]{g.getIdGrado(), g.getGrado()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(verGrado, "Error al cargar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        verGrado.tablaGrados.setModel(modelo);
    }

    private void guardarGrado() {
        String sel = (String) verGrado.comboGrados.getSelectedItem();
        if (sel == null || sel.isEmpty()) {
            JOptionPane.showMessageDialog(verGrado, "Seleccione un grado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            dao.insertarGrado(sel);
            JOptionPane.showMessageDialog(verGrado, "Grado guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(verGrado, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarGrado() {
        if (idGradoSeleccionado < 0) {
            JOptionPane.showMessageDialog(verGrado, "Seleccione un grado de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            dao.modificarGrado(idGradoSeleccionado, (String) verGrado.comboGrados.getSelectedItem());
            JOptionPane.showMessageDialog(verGrado, "Grado modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            idGradoSeleccionado = -1;
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(verGrado, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarGrado() {
        if (idGradoSeleccionado < 0) {
            JOptionPane.showMessageDialog(verGrado, "Seleccione un grado de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(verGrado, "¿Eliminar el grado seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                dao.eliminarGrado(idGradoSeleccionado);
                JOptionPane.showMessageDialog(verGrado, "Grado eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                idGradoSeleccionado = -1;
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(verGrado, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
