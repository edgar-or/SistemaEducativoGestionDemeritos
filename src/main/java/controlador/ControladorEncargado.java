package controlador;

import DAO.EncargadoDAO;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloEncardoAlumno;
import vista.VerEncargado;
import vista.VistaPrincipalDirector;

public class ControladorEncargado {

    private VistaPrincipalDirector vistaPrincipal;
    private VerEncargado verEncargado;
    private EncargadoDAO dao = new EncargadoDAO();
    private int duiSeleccionado = -1;

    public ControladorEncargado(VistaPrincipalDirector vistaPrincipal) {
        this.verEncargado = new VerEncargado();
        this.vistaPrincipal = vistaPrincipal;
        onEventos();
    }

    private void onEventos() {
        vistaPrincipal.menuEncargado.addActionListener(e -> mostrarVista());
        verEncargado.btnCerrar.addActionListener(e -> verEncargado.dispose());
        verEncargado.btnagregarEncargado.addActionListener(e -> agregarEncargado());
        verEncargado.btnModificar.addActionListener(e -> modificarEncargado());
        verEncargado.btnEliminar.addActionListener(e -> eliminarEncargado());
        verEncargado.btnBuscar.addActionListener(e -> buscar());

        verEncargado.tablaEncargados.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = verEncargado.tablaEncargados.getSelectedRow();
                if (fila >= 0) {
                    try {
                        duiSeleccionado = Integer.parseInt(String.valueOf(verEncargado.tablaEncargados.getValueAt(fila, 2)));
                    } catch (Exception ex) {
                        duiSeleccionado = -1;
                    }
                }
            }
        });
    }

    public void mostrarVista() {
        verEncargado.setVisible(true);
        Dimension desk = vistaPrincipal.escritorio.getSize();
        Dimension win = verEncargado.getSize();
        verEncargado.setLocation((desk.width - win.width) / 2, (desk.height - win.height) / 2);
        vistaPrincipal.escritorio.remove(verEncargado);
        vistaPrincipal.escritorio.add(verEncargado);
        verEncargado.toFront();
        cargarTabla(null);
    }

    private void cargarTabla(List<ModeloEncardoAlumno> lista) {
        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"Nombre", "Apellido", "DUI", "Departamento", "Municipio"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        try {
            if (lista == null) {
                lista = dao.listarEncargados();
            }
            for (ModeloEncardoAlumno e : lista) {
                modelo.addRow(new Object[]{
                    e.getNombre(),
                    e.getApelllido(),
                    e.getDui(),
                    e.getDepartamento(),
                    e.getMunicipio()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(verEncargado, "Error al cargar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        verEncargado.tablaEncargados.setModel(modelo);
    }

    private void buscar() {
        String dui = verEncargado.txtDUI.getText().trim();
        String nombre = verEncargado.txtNombre.getText().trim();
        try {
            if (!dui.isEmpty()) {
                cargarTabla(dao.buscarPorDui(Integer.parseInt(dui)));
            } else if (!nombre.isEmpty()) {
                cargarTabla(dao.buscarPorNombre(nombre));
            } else {
                cargarTabla(null);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(verEncargado, "El DUI debe ser numérico.", "Aviso", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(verEncargado, "Error en búsqueda: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarEncargado() {
        JTextField fNombre = new JTextField(), fApellido = new JTextField(),
                fDUI = new JTextField(), fDepartamento = new JTextField(),
                fMunicipio = new JTextField(), fCaserio = new JTextField(),
                fCalle = new JTextField(), fDistrito = new JTextField(), fCanto = new JTextField();

        Object[] campos = {
            "Nombre:", fNombre, "Apellido:", fApellido, "DUI:", fDUI,
            "Departamento:", fDepartamento, "Municipio:", fMunicipio,
            "Distrito:", fDistrito, "Caserío:", fCaserio, "Cantón:", fCanto, "Calle:", fCalle
        };

        if (JOptionPane.showConfirmDialog(verEncargado, campos, "Agregar Encargado", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                ModeloEncardoAlumno enc = new ModeloEncardoAlumno();
                enc.setDui(Integer.parseInt(fDUI.getText().trim()));
                enc.setNombre(fNombre.getText().trim());
                enc.setApelllido(fApellido.getText().trim());
                enc.setDepartamento(fDepartamento.getText().trim());
                enc.setMunicipio(fMunicipio.getText().trim());
                enc.setDistrito(fDistrito.getText().trim());
                enc.setCaserio(fCaserio.getText().trim());
                enc.setCanto(fCanto.getText().trim());
                enc.setCalle(fCalle.getText().trim());

                dao.insertarEncargado(enc);
                JOptionPane.showMessageDialog(verEncargado, "Encargado agregado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarTabla(null);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(verEncargado, "El DUI debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(verEncargado, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modificarEncargado() {
        if (duiSeleccionado < 0) {
            JOptionPane.showMessageDialog(verEncargado, "Seleccione un encargado de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int fila = verEncargado.tablaEncargados.getSelectedRow();
        JTextField fNombre = new JTextField((String) verEncargado.tablaEncargados.getValueAt(fila, 0));
        JTextField fApellido = new JTextField((String) verEncargado.tablaEncargados.getValueAt(fila, 1));
        JTextField fDepartamento = new JTextField((String) verEncargado.tablaEncargados.getValueAt(fila, 3));
        JTextField fMunicipio = new JTextField((String) verEncargado.tablaEncargados.getValueAt(fila, 4));
        JTextField fCaserio = new JTextField(), fCalle = new JTextField(), fDistrito = new JTextField(), fCanto = new JTextField();

        Object[] campos = {
            "Nombre:", fNombre, "Apellido:", fApellido,
            "Departamento:", fDepartamento, "Municipio:", fMunicipio,
            "Distrito:", fDistrito, "Caserío:", fCaserio, "Cantón:", fCanto, "Calle:", fCalle
        };

        if (JOptionPane.showConfirmDialog(verEncargado, campos, "Modificar Encargado", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                ModeloEncardoAlumno enc = new ModeloEncardoAlumno();
                enc.setDui(duiSeleccionado);
                enc.setNombre(fNombre.getText().trim());
                enc.setApelllido(fApellido.getText().trim());
                enc.setDepartamento(fDepartamento.getText().trim());
                enc.setMunicipio(fMunicipio.getText().trim());
                enc.setDistrito(fDistrito.getText().trim());
                enc.setCaserio(fCaserio.getText().trim());
                enc.setCanto(fCanto.getText().trim());
                enc.setCalle(fCalle.getText().trim());

                dao.modificarEncargado(enc, duiSeleccionado);
                JOptionPane.showMessageDialog(verEncargado, "Encargado modificado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                duiSeleccionado = -1;
                cargarTabla(null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(verEncargado, "Error al modificar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarEncargado() {
        if (duiSeleccionado < 0) {
            JOptionPane.showMessageDialog(verEncargado, "Seleccione un encargado de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(verEncargado, "¿Eliminar el encargado seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                dao.eliminarEncargado(duiSeleccionado);
                JOptionPane.showMessageDialog(verEncargado, "Encargado eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                duiSeleccionado = -1;
                cargarTabla(null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(verEncargado, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
