package controlador;

import DAO.DocenteDAO;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ArbolBinarioBusqueda;
import modelo.ModeloDocente;
import modelo.Nodo;
import vista.VisDocente;
import vista.VistaAgregarMaestros;
import vista.VistaPrincipalDirector;

public class ControladorDocente {

    private VisDocente vistaDocente;
    private VistaPrincipalDirector vistaPrincipal;
    private DocenteDAO dao = new DocenteDAO();
    private ArbolBinarioBusqueda<ModeloDocente> arbolDocentes;

    public ControladorDocente(VistaPrincipalDirector vistaPrincipal) {
        this.vistaDocente = new VisDocente();
        this.vistaPrincipal = vistaPrincipal;
        onEventos();
        listarDocentes();
    }

    private void onEventos() {
        vistaPrincipal.menuDocente.addActionListener(e -> mostrarVista());

        vistaDocente.btnSalir.addActionListener(e -> vistaDocente.dispose());

        vistaDocente.btnRegistro.addActionListener(e -> abrirFormularioRegistrar());

        vistaDocente.btnModificar.addActionListener(e -> abrirFormularioModificar());

        vistaDocente.btnEliminar.addActionListener(e -> eliminarDocente());

        vistaDocente.btnLimpiar.addActionListener(e -> {
            vistaDocente.txtBuscarDUI.setText("");
            vistaDocente.txtNombreCompleto.setText("");
            listarDocentes();
        });

        vistaDocente.btnBuscar.addActionListener(e -> buscar());

        vistaDocente.btnTelefono.addActionListener(e -> {
            int fila = vistaDocente.tablaDocente.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vistaDocente,
                        "Seleccione un docente de la tabla primero.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String dui    = vistaDocente.tablaDocente.getValueAt(fila, 0).toString();
            String nombre = vistaDocente.tablaDocente.getValueAt(fila, 1).toString()
                          + " " + vistaDocente.tablaDocente.getValueAt(fila, 2).toString();
            new ControladorAgregarTelefono(dui, nombre);
        });

        vistaDocente.btnCorreo.addActionListener(e -> {
            int fila = vistaDocente.tablaDocente.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vistaDocente,
                        "Seleccione un docente de la tabla primero.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String dui    = vistaDocente.tablaDocente.getValueAt(fila, 0).toString();
            String nombre = vistaDocente.tablaDocente.getValueAt(fila, 1).toString()
                          + " " + vistaDocente.tablaDocente.getValueAt(fila, 2).toString();
            new ControladorAgregarCorreo(dui, nombre);
        });
    }

    public void mostrarVista() {
        vistaDocente.setVisible(true);
        Dimension desk = vistaPrincipal.escritorio.getSize();
        Dimension win  = vistaDocente.getSize();
        vistaDocente.setLocation(
                (desk.width  - win.width)  / 2,
                (desk.height - win.height) / 2);
        vistaPrincipal.escritorio.remove(vistaDocente);
        vistaPrincipal.escritorio.add(vistaDocente);
        vistaDocente.toFront();
    }

    public void listarDocentes() {
        try {
            List<ModeloDocente> lista = dao.listarDocentes();
            arbolDocentes = new ArbolBinarioBusqueda<>();
            for (ModeloDocente d : lista) {
                arbolDocentes.insertar(d);
            }
            llenarTablaDesdeArbol();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaDocente,
                    "Error al listar docentes: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void llenarTablaDesdeArbol() {
        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"DUI", "Nombre", "Apellido", "Departamento", "Municipio", "Distrito", "Correo", "Teléfono"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        List<ModeloDocente> lista = arbolDocentes.IND();
        for (ModeloDocente d : lista) {
            modelo.addRow(new Object[]{
                d.getDuiDocente(),
                d.getNombre(),
                d.getApellido(),
                nv(d.getDepartamento()),
                nv(d.getMunicipio()),
                nv(d.getDistrito()),
                nv(d.getCorreo()),
                nv(d.getTelefonoDocente())
            });
        }
        vistaDocente.tablaDocente.setModel(modelo);
    }

    private void abrirFormularioRegistrar() {
        VistaAgregarMaestros form = new VistaAgregarMaestros();
        form.setTitle("Registrar Docente");

        form.txtDui.setText("");
        form.txtDui.setEnabled(true);
        form.txtNombres1.setText("");
        form.txtNombres.setText("");
        form.txtDepartamentos.setText("");
        form.txtmunicipio.setText("");
        form.txtDistrito1.setText("");
        form.txtDistrito.setText("");
        form.txtDistr1.setText("");
        form.txtDistr.setText("");
        form.txtDistr2.setText("");

        form.btnGuardar.addActionListener(e -> {
            String dui      = form.txtDui.getText().trim();
            String nombre   = form.txtNombres1.getText().trim();
            String apellido = form.txtNombres.getText().trim();

            if (dui.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
                JOptionPane.showMessageDialog(form,
                        "DUI, Nombre y Apellido son obligatorios.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                ModeloDocente d = new ModeloDocente();
                d.setDuiDocente(dui);
                d.setNombre(nombre);
                d.setApellido(apellido);
                d.setDepartamento(form.txtDepartamentos.getText().trim());
                d.setMunicipio(form.txtmunicipio.getText().trim());
                d.setDistrito(form.txtDistrito1.getText().trim());
                d.setCaserio(form.txtDistr1.getText().trim());
                d.setCalle(form.txtDistr.getText().trim());
                d.setTelefonoDocente(form.txtDistrito.getText().trim());
                d.setCorreo(form.txtDistr2.getText().trim());

                dao.insertarDocente(d);

                JOptionPane.showMessageDialog(form,
                        "Docente registrado correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                form.dispose();
                listarDocentes();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(form,
                        "Error al guardar: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        form.btnSalir.addActionListener(e -> form.dispose());
        form.setLocationRelativeTo(null);
        form.setVisible(true);
    }

    private void abrirFormularioModificar() {
        int fila = vistaDocente.tablaDocente.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vistaDocente,
                    "Seleccione un docente de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String dui = vistaDocente.tablaDocente.getValueAt(fila, 0).toString();

        ModeloDocente actual = null;
        try {
            actual = dao.buscarPorDui(dui);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaDocente,
                    "Error al obtener datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (actual == null) {
            JOptionPane.showMessageDialog(vistaDocente,
                    "No se encontraron datos del docente seleccionado.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        final ModeloDocente docente = actual;

        VistaAgregarMaestros form = new VistaAgregarMaestros();
        form.setTitle("Modificar Docente");

        form.txtDui.setText(docente.getDuiDocente());
        form.txtDui.setEnabled(false);
        form.txtNombres1.setText(nv(docente.getNombre()));
        form.txtNombres.setText(nv(docente.getApellido()));
        form.txtDepartamentos.setText(nv(docente.getDepartamento()));
        form.txtmunicipio.setText(nv(docente.getMunicipio()));
        form.txtDistrito1.setText(nv(docente.getDistrito()));
        form.txtDistr1.setText(nv(docente.getCaserio()));
        form.txtDistr.setText(nv(docente.getCalle()));
        form.txtDistrito.setText(nv(docente.getTelefonoDocente()));
        form.txtDistr2.setText(nv(docente.getCorreo()));

        form.btnGuardar.addActionListener(e -> {
            String nombre   = form.txtNombres1.getText().trim();
            String apellido = form.txtNombres.getText().trim();

            if (nombre.isEmpty() || apellido.isEmpty()) {
                JOptionPane.showMessageDialog(form,
                        "Nombre y Apellido son obligatorios.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                ModeloDocente d = new ModeloDocente();
                d.setDuiDocente(docente.getDuiDocente());
                d.setNombre(nombre);
                d.setApellido(apellido);
                d.setDepartamento(form.txtDepartamentos.getText().trim());
                d.setMunicipio(form.txtmunicipio.getText().trim());
                d.setDistrito(form.txtDistrito1.getText().trim());
                d.setCaserio(form.txtDistr1.getText().trim());
                d.setCalle(form.txtDistr.getText().trim());
                d.setTelefonoDocente(form.txtDistrito.getText().trim());
                d.setCorreo(form.txtDistr2.getText().trim());

                dao.modificarDocente(d);

                JOptionPane.showMessageDialog(form,
                        "Docente actualizado correctamente.",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                form.dispose();
                listarDocentes();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(form,
                        "Error al actualizar: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        form.btnSalir.addActionListener(e -> form.dispose());
        form.setLocationRelativeTo(null);
        form.setVisible(true);
    }

    private void eliminarDocente() {
        int fila = vistaDocente.tablaDocente.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vistaDocente,
                    "Seleccione un docente de la tabla.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String dui = vistaDocente.tablaDocente.getValueAt(fila, 0).toString();

        int confirm = JOptionPane.showConfirmDialog(vistaDocente,
                "¿Eliminar al docente con DUI: " + dui + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            dao.eliminarDocente(dui);
            JOptionPane.showMessageDialog(vistaDocente,
                    "Docente eliminado correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            listarDocentes();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaDocente,
                    "Error al eliminar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscar() {
        String dui    = vistaDocente.txtBuscarDUI.getText().trim();
        String nombre = vistaDocente.txtNombreCompleto.getText().trim();

        try {
            if (!dui.isEmpty()) {
                ModeloDocente clave = new ModeloDocente();
                clave.setDuiDocente(dui);
                Nodo nodo = arbolDocentes.buscar(clave);

                DefaultTableModel modelo = new DefaultTableModel(
                        new String[]{"DUI", "Nombre", "Apellido", "Departamento", "Municipio", "Distrito", "Correo", "Teléfono"}, 0) {
                    @Override
                    public boolean isCellEditable(int r, int c) { return false; }
                };

                if (nodo != null) {
                    ModeloDocente d = (ModeloDocente) nodo.getDato();
                    modelo.addRow(new Object[]{
                        d.getDuiDocente(),
                        d.getNombre(),
                        d.getApellido(),
                        nv(d.getDepartamento()),
                        nv(d.getMunicipio()),
                        nv(d.getDistrito()),
                        nv(d.getCorreo()),
                        nv(d.getTelefonoDocente())
                    });
                } else {
                    JOptionPane.showMessageDialog(vistaDocente,
                            "No se encontró ningún docente con ese DUI.",
                            "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                }
                vistaDocente.tablaDocente.setModel(modelo);

            } else if (!nombre.isEmpty()) {
                List<ModeloDocente> lista = dao.buscarPorNombre(nombre);
                arbolDocentes = new ArbolBinarioBusqueda<>();
                for (ModeloDocente d : lista) arbolDocentes.insertar(d);
                llenarTablaDesdeArbol();

                if (lista.isEmpty()) {
                    JOptionPane.showMessageDialog(vistaDocente,
                            "No se encontró ningún docente con ese nombre.",
                            "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                listarDocentes();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vistaDocente,
                    "Error en la búsqueda: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String nv(String valor) {
        return valor != null ? valor : "";
    }
}