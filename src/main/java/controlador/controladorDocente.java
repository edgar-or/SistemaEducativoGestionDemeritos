package controlador;

import DAO.DocenteDAO;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
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
            String dui    = vistaDocente.tablaDocente.getValueAt(fila, 1).toString();
            String nombre = vistaDocente.tablaDocente.getValueAt(fila, 2).toString()
                          + " " + vistaDocente.tablaDocente.getValueAt(fila, 3).toString();
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
            String dui    = vistaDocente.tablaDocente.getValueAt(fila, 1).toString();
            String nombre = vistaDocente.tablaDocente.getValueAt(fila, 2).toString()
                          + " " + vistaDocente.tablaDocente.getValueAt(fila, 3).toString();
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
                new String[]{"ID", "DUI", "Nombre", "Apellido", "Departamento", "Municipio", "Distrito", "Correo", "Teléfono"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        List<ModeloDocente> lista = arbolDocentes.IND();
        for (ModeloDocente d : lista) {
            modelo.addRow(new Object[]{
                d.getIdPersonal(),
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

    // Método auxiliar para llenar el ComboBox desde el Controlador
    private void llenarComboCargos(VistaAgregarMaestros form) {
        try {
            DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel();
            List<Object[]> listaCargos = dao.listarCargos();
            for (Object[] cargo : listaCargos) {
                modeloCombo.addElement((String) cargo[1]);
            }
            form.cmbCargo.setModel(modeloCombo);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(form, "Error al cargar los cargos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirFormularioRegistrar() {
        VistaAgregarMaestros form = new VistaAgregarMaestros();
        form.setTitle("Registrar Docente");

        // Llenamos el ComboBox de cargos inmediatamente al abrir la ventana
        llenarComboCargos(form);

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

            // Recuperamos el ID del cargo seleccionado de manera secuencial
            String cargoSeleccionado = (String) form.cmbCargo.getSelectedItem();
            int idCargo = 0;
            try {
                List<Object[]> listaCargos = dao.listarCargos();
                for (Object[] cargo : listaCargos) {
                    if (cargo[1].equals(cargoSeleccionado)) {
                        idCargo = (int) cargo[0];
                        break;
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error al asociar cargo: " + ex.getMessage());
            }

            try {
                ModeloDocente d = new ModeloDocente();
                d.setDuiDocente(dui);
                d.setNombre(nombre);
                d.setApellido(apellido);
                d.setDepartamento(form.txtmunicipio.getText().trim());
                d.setMunicipio(form.txtDistrito1.getText().trim());
                d.setDistrito(form.txtDistrito.getText().trim());
                d.setCaserio(form.txtDistr1.getText().trim());
                d.setCalle(form.txtDistr.getText().trim());
                d.setNumeroCasa(parsearCasa(form.txtDistr2.getText().trim()));
                d.setTelefonoDocente("");
                d.setCorreo("");
                
                // Asignamos el ID numérico capturado del combo
                d.setIdCargo(idCargo); 

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

        String dui = vistaDocente.tablaDocente.getValueAt(fila, 1).toString();

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

        // Cargamos los cargos en la ventana de modificación
        llenarComboCargos(form);

        form.txtDui.setText(docente.getDuiDocente());
        form.txtDui.setEnabled(false);
        form.txtNombres1.setText(nv(docente.getNombre()));
        form.txtNombres.setText(nv(docente.getApellido()));
        form.txtmunicipio.setText(nv(docente.getDepartamento()));
        form.txtDistrito1.setText(nv(docente.getMunicipio()));
        form.txtDistrito.setText(nv(docente.getDistrito()));
        form.txtDistr1.setText(nv(docente.getCaserio()));
        form.txtDistr.setText(nv(docente.getCalle()));
        form.txtDistr2.setText(String.valueOf(docente.getNumeroCasa() > 0 ? docente.getNumeroCasa() : ""));

        form.btnGuardar.addActionListener(e -> {
            String nombre   = form.txtNombres1.getText().trim();
            String apellido = form.txtNombres.getText().trim();

            if (nombre.isEmpty() || apellido.isEmpty()) {
                JOptionPane.showMessageDialog(form,
                        "Nombre y Apellido son obligatorios.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // También capturamos el cargo en caso de que se haya modificado
            String cargoSeleccionado = (String) form.cmbCargo.getSelectedItem();
            int idCargo = 0;
            try {
                List<Object[]> listaCargos = dao.listarCargos();
                for (Object[] cargo : listaCargos) {
                    if (cargo[1].equals(cargoSeleccionado)) {
                        idCargo = (int) cargo[0];
                        break;
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error al asociar cargo: " + ex.getMessage());
            }

            try {
                ModeloDocente d = new ModeloDocente();
                d.setDuiDocente(docente.getDuiDocente());
                d.setNombre(nombre);
                d.setApellido(apellido);
                d.setDepartamento(form.txtmunicipio.getText().trim());
                d.setMunicipio(form.txtDistrito1.getText().trim());
                d.setDistrito(form.txtDistrito.getText().trim());
                d.setCaserio(form.txtDistr1.getText().trim());
                d.setCalle(form.txtDistr.getText().trim());
                d.setNumeroCasa(parsearCasa(form.txtDistr2.getText().trim()));
                d.setTelefonoDocente("");
                d.setCorreo("");
                
                d.setIdCargo(idCargo); // Guardamos la actualización del cargo

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

        String dui = vistaDocente.tablaDocente.getValueAt(fila, 1).toString();

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
                        new String[]{"ID", "DUI", "Nombre", "Apellido", "Departamento", "Municipio", "Distrito", "Correo", "Teléfono"}, 0) {
                    @Override
                    public boolean isCellEditable(int r, int c) { return false; }
                };

                if (nodo != null) {
                    ModeloDocente d = (ModeloDocente) nodo.getDato();
                    modelo.addRow(new Object[]{
                        d.getIdPersonal(),
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

    private int parsearCasa(String texto) {
        try { return Integer.parseInt(texto); } catch (NumberFormatException e) { return 0; }
    }
}