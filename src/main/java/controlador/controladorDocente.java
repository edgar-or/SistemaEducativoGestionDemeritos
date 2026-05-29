package controlador;

import DAO.DocenteDAO;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloDocente;
import vista.VerDocente;
import vista.VistaDocente;
import vista.VistaPrincipalDirector;

public class ControladorDocente {

    private VistaDocente vistaDocente;
    private VistaPrincipalDirector vistaPrincipal;
    private DocenteDAO dao = new DocenteDAO();
    private int idDocenteSeleccionado = -1;

    public ControladorDocente(VistaPrincipalDirector vistaPrincipal) {
        this.vistaDocente = new VistaDocente();
        this.vistaPrincipal = vistaPrincipal;
        eventos();
    }

    private void eventos() {
        vistaPrincipal.menuDocente.addActionListener(e -> mostrarVista());
        vistaDocente.btnSalir.addActionListener(e -> vistaDocente.dispose());
        vistaDocente.btnGuardar.addActionListener(e -> agregarDocente());
//        vistaDocente.btnModificar.addActionListener(e -> modificarDocente());
        vistaDocente.btnEliminar.addActionListener(e -> eliminarDocente());
     //   vistaDocente.btnBuscar.addActionListener(e -> buscar());

        vistaDocente.tablaDocente.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = vistaDocente.tablaDocente.getSelectedRow();
                if (fila >= 0)
                    idDocenteSeleccionado = (int) vistaDocente.tablaDocente.getValueAt(fila, 0);
            }
        });
    }

    public void mostrarVista() {
        vistaDocente.setVisible(true);
        Dimension desk = vistaPrincipal.escritorio.getSize();
        Dimension win = vistaDocente.getSize();
        vistaDocente.setLocation((desk.width - win.width) / 2, (desk.height - win.height) / 2);
        vistaPrincipal.escritorio.remove(vistaDocente);
        vistaPrincipal.escritorio.add(vistaDocente);
        vistaDocente.toFront();
        cargarTabla(null);
    }

    private void cargarTabla(List<ModeloDocente> lista) {
        DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"ID", "Nombre", "Apellido", "Teléfono", "Correo", "Departamento"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        try {
            if (lista == null) lista = dao.listarDocentes();
            for (ModeloDocente d : lista)
                modelo.addRow(new Object[]{d.getIdDocente(), d.getNombre(), d.getApellido(),
                    d.getTelefonoDocente(), d.getCorreo(), d.getDepartamento()});
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaDocente, "Error al cargar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        vistaDocente.tablaDocente.setModel(modelo);
    }

//    private void buscar() {
//        String dui = vistaDocente.txtDUI.getText().trim();
//        String nombre = vistaDocente.txtNombre.getText().trim();
//        try {
//            if (!dui.isEmpty())
//                cargarTabla(dao.buscarPorId(Integer.parseInt(dui)));
//            else if (!nombre.isEmpty())
//                cargarTabla(dao.buscarPorNombre(nombre));
//            else
//                cargarTabla(null);
//        } catch (NumberFormatException ex) {
//            JOptionPane.showMessageDialog(vistaDocente, "El ID debe ser numérico.", "Aviso", JOptionPane.WARNING_MESSAGE);
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(vistaDocente, "Error en búsqueda: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }

    private void agregarDocente() {
        JTextField fNombre = new JTextField(), fApellido = new JTextField(),
            fTelefono = new JTextField(), fCorreo = new JTextField(),
            fDepartamento = new JTextField(), fMunicipio = new JTextField(),
            fCaserio = new JTextField(), fCalle = new JTextField(), fDistrito = new JTextField();

        Object[] campos = {"Nombre:", fNombre, "Apellido:", fApellido,
            "Teléfono:", fTelefono, "Correo:", fCorreo, "Departamento:", fDepartamento,
            "Municipio:", fMunicipio, "Caserío:", fCaserio, "Calle:", fCalle, "Distrito:", fDistrito};

        if (JOptionPane.showConfirmDialog(vistaDocente, campos, "Agregar Docente", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                ModeloDocente d = new ModeloDocente(fNombre.getText().trim(), fApellido.getText().trim(),
                    "", Integer.parseInt(fTelefono.getText().trim()), fCorreo.getText().trim(),
                    fDepartamento.getText().trim(), fMunicipio.getText().trim(),
                    fCaserio.getText().trim(), fCalle.getText().trim(), fDistrito.getText().trim());
                dao.insertarDocente(d);
                JOptionPane.showMessageDialog(vistaDocente, "Docente agregado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarTabla(null);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaDocente, "El teléfono debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vistaDocente, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

//    private void modificarDocente() {
//        if (idDocenteSeleccionado < 0) {
//            JOptionPane.showMessageDialog(vistaDocente, "Seleccione un docente de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
//            return;
//        }
//        int fila = vistaDocente.tablaDocentes.getSelectedRow();
//        JTextField fNombre = new JTextField((String) vistaDocente.tablaDocentes.getValueAt(fila, 1));
//        JTextField fApellido = new JTextField((String) vistaDocente.tablaDocentes.getValueAt(fila, 2));
//        JTextField fTelefono = new JTextField(String.valueOf(vistaDocente.tablaDocentes.getValueAt(fila, 3)));
//        JTextField fCorreo = new JTextField((String) vistaDocente.tablaDocentes.getValueAt(fila, 4));
//        JTextField fDepartamento = new JTextField((String) vistaDocente.tablaDocentes.getValueAt(fila, 5));
//        JTextField fMunicipio = new JTextField(), fCaserio = new JTextField(),
//            fCalle = new JTextField(), fDistrito = new JTextField();
//
//        Object[] campos = {"Nombre:", fNombre, "Apellido:", fApellido,
//            "Teléfono:", fTelefono, "Correo:", fCorreo, "Departamento:", fDepartamento,
//            "Municipio:", fMunicipio, "Caserío:", fCaserio, "Calle:", fCalle, "Distrito:", fDistrito};
//
//        if (JOptionPane.showConfirmDialog(vistaDocente, campos, "Modificar Docente", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
//            try {
//                ModeloDocente d = new ModeloDocente(fNombre.getText().trim(), fApellido.getText().trim(),
//                    idDocenteSeleccionado, Integer.parseInt(fTelefono.getText().trim()),
//                    fCorreo.getText().trim(), fDepartamento.getText().trim(),
//                    fMunicipio.getText().trim(), fCaserio.getText().trim(),
//                    fCalle.getText().trim(), fDistrito.getText().trim());
//                dao.modificarDocente(d);
//                JOptionPane.showMessageDialog(vistaDocente, "Docente modificado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
//                idDocenteSeleccionado = -1;
//                cargarTabla(null);
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(vistaDocente, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        }
//    }

    private void eliminarDocente() {
        if (idDocenteSeleccionado < 0) {
            JOptionPane.showMessageDialog(vistaDocente, "Seleccione un docente de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(vistaDocente, "¿Eliminar el docente seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                dao.eliminarDocente(idDocenteSeleccionado);
                JOptionPane.showMessageDialog(vistaDocente, "Docente eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                idDocenteSeleccionado = -1;
                cargarTabla(null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vistaDocente, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}