package controlador;

import DAO.DocenteDAO;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.ArbolBinarioBusqueda;
import modelo.ModeloDocente;
import modelo.Nodo;
import vista.VerDocente;
import vista.VisDocente;
import vista.VistaAgregarMaestros;
import vista.VistaDocente;
import vista.VistaPrincipalDirector;

public class ControladorDocente {

    private VisDocente vistaDocente;
    private VistaPrincipalDirector vistaPrincipal;
    private DocenteDAO dao = new DocenteDAO();
    private String idDocenteSeleccionado = null;
    private ArbolBinarioBusqueda<ModeloDocente> arbolDocentes;

    public ControladorDocente(VistaPrincipalDirector vistaPrincipal) {
        this.vistaDocente = new VisDocente();
        this.vistaPrincipal = vistaPrincipal;
        eventos();
    }

    private void eventos() {
        vistaPrincipal.menuDocente.addActionListener(e -> mostrarVista());
        vistaDocente.btnSalir.addActionListener(e -> vistaDocente.dispose());
        vistaDocente.btnEliminar.addActionListener(e -> eliminarDocente());
        vistaDocente.btnBuscar.addActionListener(e -> buscar());

        vistaDocente.btnRegistro.addActionListener(e -> {
            VistaAgregarMaestros vista = new VistaAgregarMaestros();
            ControladorRegistrarDocentes ctr = new ControladorRegistrarDocentes(vista);
        });

        vistaDocente.tablaDocente.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = vistaDocente.tablaDocente.getSelectedRow();
                if (fila >= 0) {
                    idDocenteSeleccionado = vistaDocente.tablaDocente.getValueAt(fila, 0).toString();
                }
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
                new String[]{"DUI", "Nombre", "Apellido", "Teléfono", "Correo", "Departamento"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        try {
            if (lista == null) {
                lista = dao.listarDocentes();
            }

            arbolDocentes = new ArbolBinarioBusqueda<>();

            for (ModeloDocente d : lista) {
                arbolDocentes.insertar(d);
                modelo.addRow(new Object[]{
                    d.getDuiDocente(),
                    d.getNombre(),
                    d.getApellido(),
                    d.getTelefonoDocente(),
                    d.getCorreo(),
                    d.getDepartamento()
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaDocente, "Error al cargar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        vistaDocente.tablaDocente.setModel(modelo);
    }

    private void buscar() {
        String idStr = vistaDocente.txtBuscarDUI.getText().trim();
        String nombre = vistaDocente.txtNombreCompleto.getText().trim();

        try {
            if (!idStr.isEmpty()) {
                ModeloDocente buscar = new ModeloDocente();
                buscar.setDuiDocente(idStr);

                Nodo nodo = arbolDocentes.buscar(buscar);

                DefaultTableModel modelo = new DefaultTableModel(
                        new String[]{"DUI", "Nombre", "Apellido", "Teléfono", "Correo", "Departamento"}, 0
                );

                if (nodo != null) {
                    ModeloDocente d = (ModeloDocente) nodo.getDato();
                    modelo.addRow(new Object[]{
                        d.getDuiDocente(),
                        d.getNombre(),
                        d.getApellido(),
                        d.getTelefonoDocente(),
                        d.getCorreo(),
                        d.getDepartamento()
                    });
                }
                vistaDocente.tablaDocente.setModel(modelo);

            } else if (!nombre.isEmpty()) {
                cargarTabla(dao.buscarPorNombre(nombre));
            } else {
                cargarTabla(null);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaDocente, "Error en búsqueda: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarDocente() {
        JTextField fDui = new JTextField(), fNombre = new JTextField(), fApellido = new JTextField(),
                fTelefono = new JTextField(), fCorreo = new JTextField(),
                fDepartamento = new JTextField(), fMunicipio = new JTextField(),
                fCaserio = new JTextField(), fCalle = new JTextField(), fDistrito = new JTextField();

        Object[] campos = {
            "DUI (Con guiones):", fDui,
            "Nombre:", fNombre,
            "Apellido:", fApellido,
            "Teléfono:", fTelefono,
            "Correo:", fCorreo,
            "Departamento:", fDepartamento,
            "Municipio:", fMunicipio,
            "Caserío:", fCaserio,
            "Calle:", fCalle,
            "Distrito:", fDistrito
        };

        if (JOptionPane.showConfirmDialog(vistaDocente, campos, "Agregar Nuevo Docente", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                if (fDui.getText().trim().isEmpty() || fTelefono.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(vistaDocente, "El DUI y el Teléfono son campos obligatorios.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                ModeloDocente d = new ModeloDocente();
                d.setDuiDocente(fDui.getText().trim());
                d.setNombre(fNombre.getText().trim());
                d.setApellido(fApellido.getText().trim());
                d.setTelefonoDocente(Integer.parseInt(fTelefono.getText().trim()));
                d.setCorreo(fCorreo.getText().trim());
                d.setDepartamento(fDepartamento.getText().trim());
                d.setMunicipio(fMunicipio.getText().trim());
                d.setCaserio(fCaserio.getText().trim());
                d.setCalle(fCalle.getText().trim());
                d.setDistrito(fDistrito.getText().trim());

                dao.insertarDocente(d);
                JOptionPane.showMessageDialog(vistaDocente, "Docente agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarTabla(null);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaDocente, "El teléfono debe contener únicamente números.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vistaDocente, "Error al guardar el docente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void modificarDocente() {
        if (idDocenteSeleccionado == null) {
            JOptionPane.showMessageDialog(vistaDocente, "Por favor, seleccione un docente de la tabla para modificar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int fila = vistaDocente.tablaDocente.getSelectedRow();

        JTextField fNombre = new JTextField((String) vistaDocente.tablaDocente.getValueAt(fila, 1));
        JTextField fApellido = new JTextField((String) vistaDocente.tablaDocente.getValueAt(fila, 2));
        JTextField fTelefono = new JTextField(String.valueOf(vistaDocente.tablaDocente.getValueAt(fila, 3)));
        JTextField fCorreo = new JTextField((String) vistaDocente.tablaDocente.getValueAt(fila, 4));
        JTextField fDepartamento = new JTextField((String) vistaDocente.tablaDocente.getValueAt(fila, 5));

        JTextField fMunicipio = new JTextField(), fCaserio = new JTextField(),
                fCalle = new JTextField(), fDistrito = new JTextField();

        Object[] campos = {
            "DUI (No editable):", new JTextField(idDocenteSeleccionado) {
                {
                    setEditable(false);
                }
            },
            "Nombre:", fNombre,
            "Apellido:", fApellido,
            "Teléfono:", fTelefono,
            "Correo:", fCorreo,
            "Departamento:", fDepartamento,
            "Municipio:", fMunicipio,
            "Caserío:", fCaserio,
            "Calle:", fCalle,
            "Distrito:", fDistrito
        };

        if (JOptionPane.showConfirmDialog(vistaDocente, campos, "Modificar Datos del Docente", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
            try {
                ModeloDocente d = new ModeloDocente();
                d.setDuiDocente(idDocenteSeleccionado);
                d.setNombre(fNombre.getText().trim());
                d.setApellido(fApellido.getText().trim());
                d.setTelefonoDocente(Integer.parseInt(fTelefono.getText().trim()));
                d.setCorreo(fCorreo.getText().trim());
                d.setDepartamento(fDepartamento.getText().trim());
                d.setMunicipio(fMunicipio.getText().trim());
                d.setCaserio(fCaserio.getText().trim());
                d.setCalle(fCalle.getText().trim());
                d.setDistrito(fDistrito.getText().trim());

                dao.modificarDocente(d);
                JOptionPane.showMessageDialog(vistaDocente, "Datos del docente actualizados con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                idDocenteSeleccionado = null;
                cargarTabla(null);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vistaDocente, "El teléfono debe contener únicamente dígitos numéricos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vistaDocente, "Error al modificar el registro: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarDocente() {
        if (idDocenteSeleccionado == null) {
            JOptionPane.showMessageDialog(vistaDocente, "Seleccione un docente de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (JOptionPane.showConfirmDialog(vistaDocente, "¿Eliminar el docente seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            try {
                dao.eliminarDocente(idDocenteSeleccionado);
                JOptionPane.showMessageDialog(vistaDocente, "Docente eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                idDocenteSeleccionado = null;
                cargarTabla(null);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vistaDocente, "Error al eliminar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
