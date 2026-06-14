package controlador;

import DAO.GradoDAO;
import DAO.MantenimientoAlumnoDao;
import DAO.seccionProfesorDAO;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloAlumno;
import modelo.ModeloGrado;
import modelo.ModeloSeccion;
import vista.VistaAlumno;
import vista.VistaPrincipalDirector;
import modelo.ArbolBinarioBusqueda;
import modelo.Nodo;

public class ControladorAlumno {

    private VistaAlumno vistaAlumno;
    private VistaPrincipalDirector vistaPrincipal;
    private boolean modoEdicion = false;
    private ArbolBinarioBusqueda<ModeloAlumno> arbolAlumnos;
    private MantenimientoAlumnoDao alumnoDAO = new MantenimientoAlumnoDao();
    private GradoDAO gradoDAO = new GradoDAO();
    private seccionProfesorDAO seccionDAO = new seccionProfesorDAO();

    private ControladorAsignarEncargado controlAsignarEncargado;

    public ControladorAlumno(VistaPrincipalDirector vistaPrincipal) {
        this.vistaAlumno = new VistaAlumno();
        this.vistaPrincipal = vistaPrincipal;

        this.controlAsignarEncargado = new ControladorAsignarEncargado(vistaPrincipal, this);

        onEventos();
        listarAlumnos();
        cargarGrados();
    }

    private void onEventos() {
        vistaPrincipal.menuAlumno.addActionListener(e -> mostrarVista());
        vistaAlumno.btnSalir.addActionListener(e -> vistaAlumno.dispose());
        vistaAlumno.btnGuar.addActionListener(e -> {
            if (modoEdicion) {
                actualizarAlumno();
            } else {
                insertarAlumno();
            }
        });
        vistaAlumno.btnModi.addActionListener(e -> cargarAlumnoFormulario());
        vistaAlumno.btnElimi.addActionListener(e -> eliminarAlumno());
        vistaAlumno.btnBuscar.addActionListener(e -> {
            filtro();
        });
        vistaAlumno.Grados.addActionListener(e -> {
            int idGrado = getIdGradoSeleccionado();
            if (idGrado != -1) {
                cargarSecciones(idGrado);
            } else {
                vistaAlumno.Secciones.removeAllItems();
                vistaAlumno.Secciones.addItem("-- Seleccione sección --");
            }
        });

        vistaAlumno.btnAsignarResponsable.addActionListener(e -> {

            if (obtenerIdAlumnoSeleccionado() == -1) {
                JOptionPane.showMessageDialog(null, "Debe seleccionar un alumno");
            } else {
                controlAsignarEncargado.iniciarVista();

            }

        });

        vistaAlumno.btnLimp.addActionListener(e -> {
            limpiarCampos();
        });

    }

    public void mostrarVista() {
        vistaAlumno.setVisible(true);

        // Tamaño fijo razonable basado en tu diseño
        vistaAlumno.setSize(1200, 700);

        // Centrar dentro del escritorio
        Dimension desktopSize = vistaPrincipal.escritorio.getSize();
        int x = Math.max(0, (desktopSize.width - 900) / 2);
        int y = Math.max(0, (desktopSize.height - 560) / 2);
        vistaAlumno.setLocation(x, y);

        vistaPrincipal.escritorio.remove(vistaAlumno);
        vistaPrincipal.escritorio.add(vistaAlumno);
        vistaAlumno.toFront();
    }

    public void listarAlumnos() {
        try {
            arbolAlumnos = alumnoDAO.listarAlumnos();
            llenarTablaDesdeArbol();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaAlumno,
                    "Error al listar alumnos: " + e.getMessage());
        }
    }

    public void llenarTablaDesdeArbol() {
        DefaultTableModel modelo = new DefaultTableModel(new String[]{
            "ID", "NIE", "Nombre", "Apellido"
        }, 0);
        List<ModeloAlumno> lista = arbolAlumnos.IND();
        for (ModeloAlumno a : lista) {
            modelo.addRow(new Object[]{
                a.getId_alumno(),
                a.getNie(),
                a.getNombre(),
                a.getApelliddos()
        });
        }
        vistaAlumno.tablaAlumnos.setModel(modelo);
    }

    public void cargarGrados() {
        vistaAlumno.Grados.removeAllItems();
        vistaAlumno.Grados.addItem("-- Seleccione grado --");
        try {
            List<ModeloGrado> grados = gradoDAO.listarGrados();
            for (ModeloGrado g : grados) {
                vistaAlumno.Grados.addItem(g.getGrado());
            }
            vistaAlumno.Grados.putClientProperty("listaGrados", grados);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaAlumno, "Error al cargar grados: " + e.getMessage());
        }
    }

    public void cargarSecciones(int idGrado) {
        vistaAlumno.Secciones.removeAllItems();
        vistaAlumno.Secciones.addItem("-- Seleccione sección --");
        try {
            List<ModeloSeccion> secciones = seccionDAO.obtenerSeccionesPorGrado(idGrado);
            for (ModeloSeccion s : secciones) {
                vistaAlumno.Secciones.addItem(s.getSeccion());
            }
            vistaAlumno.Secciones.putClientProperty("listaSecciones", secciones);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaAlumno, "Error al cargar secciones: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private int getIdGradoSeleccionado() {
        int idx = vistaAlumno.Grados.getSelectedIndex();
        if (idx <= 0) {
            return -1;
        }
        List<ModeloGrado> lista = (List<ModeloGrado>) vistaAlumno.Grados.getClientProperty("listaGrados");
        if (lista == null || idx - 1 >= lista.size()) {
            return -1;
        }
        return lista.get(idx - 1).getIdGrado();
    }

    @SuppressWarnings("unchecked")
    private int getIdSeccionSeleccionada() {
        int idx = vistaAlumno.Secciones.getSelectedIndex();
        if (idx <= 0) {
            return -1;
        }
        List<ModeloSeccion> lista = (List<ModeloSeccion>) vistaAlumno.Secciones.getClientProperty("listaSecciones");
        if (lista == null || idx - 1 >= lista.size()) {
            return -1;
        }
        return lista.get(idx - 1).getIdSeccion();
    }

    public void insertarAlumno() {
        try {
            if (vistaAlumno.txtNIE.getText().trim().isEmpty() || vistaAlumno.txtNombre.getText().trim().isEmpty() || vistaAlumno.txtApellido.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(vistaAlumno, "Complete todos los campos.");
                return;
            }

            if (!vistaAlumno.txtNombre.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+")) {
                JOptionPane.showMessageDialog(vistaAlumno, "El nombre solo debe contener letras.");
                return;
            }
            if (!vistaAlumno.txtApellido.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+")) {
                JOptionPane.showMessageDialog(vistaAlumno, "El apellido solo debe contener letras.");
                return;
            }

            int idSeccion = getIdSeccionSeleccionada();
            if (idSeccion == -1) {
                JOptionPane.showMessageDialog(vistaAlumno, "Seleccione una sección.");
                return;
            }
            ModeloAlumno alumno = new ModeloAlumno();
            alumno.setNie(Integer.parseInt(vistaAlumno.txtNIE.getText().trim()));
            alumno.setNombre(vistaAlumno.txtNombre.getText().trim());
            alumno.setApelliddos(vistaAlumno.txtApellido.getText().trim());
            alumno.setTotalPuntos(0);

            ModeloSeccion seccion = new ModeloSeccion();
            seccion.setIdSeccion(idSeccion);
            alumno.setModeloSeccion(seccion);

            boolean insertado = alumnoDAO.insertarAlumno(alumno);
            if (insertado) {
                JOptionPane.showMessageDialog(vistaAlumno, "Alumno agregado correctamente.");
                listarAlumnos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vistaAlumno, "No se pudo insertar el alumno.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(vistaAlumno, "El NIE debe ser numérico.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaAlumno, "Error: " + e.getMessage());
        }
    }

    public void buscarAlumnoPorNie() {
        try {

            String texto = vistaAlumno.buscarPorNie.getText().trim();
            if (texto.isEmpty()) {
                llenarTablaDesdeArbol();
                return;
            }
            int nie = Integer.parseInt(texto);
            ModeloAlumno alumnoBuscar = new ModeloAlumno();
            alumnoBuscar.setNie(nie);

            Nodo<ModeloAlumno> nodo = arbolAlumnos.buscar(alumnoBuscar);
            DefaultTableModel modelo = new DefaultTableModel(new String[]{
                "ID", "NIE", "Nombre", "Apellido"
            }, 0);

            if (nodo != null) {
                ModeloAlumno a = nodo.getDato();
                modelo.addRow(new Object[]{
                    a.getId_alumno(),
                    a.getNie(),
                    a.getNombre(),
                    a.getApelliddos()                });
            } else {
                JOptionPane.showMessageDialog(vistaAlumno, "Alumno no encontrado.");
            }
            vistaAlumno.tablaAlumnos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaAlumno, "Error: " + e.getMessage());
        }
    }

    public void buscarAlumnoPorNombre() {
        try {

            String nombre = vistaAlumno.txtBuscarPorNombre.getText().trim();
            if (nombre.isEmpty()) {
                llenarTablaDesdeArbol();
                return;
            }
            ModeloAlumno alumnoBuscar = new ModeloAlumno();

            DefaultTableModel modelo = new DefaultTableModel(new String[]{
                "ID", "NIE", "Nombre", "Apellido"
            }, 0);

            List<ModeloAlumno> lista = alumnoDAO.buscarPorNombre(nombre);

            if (!lista.isEmpty()) {

                for (ModeloAlumno al : lista) {
                    modelo.addRow(new Object[]{
                        al.getId_alumno(),
                        al.getNie(),
                        al.getNombre(),
                        al.getApelliddos()
                    });
                }

            } else {
                JOptionPane.showMessageDialog(vistaAlumno, "Alumno no encontrado.");

            }
            vistaAlumno.tablaAlumnos.setModel(modelo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaAlumno, "Error: " + e.getMessage());
        }
    }

    private void filtro() {
        if (!vistaAlumno.buscarPorNie.getText().isEmpty() && vistaAlumno.txtBuscarPorNombre.getText().isEmpty()) {

            buscarAlumnoPorNie();

        } else if (vistaAlumno.buscarPorNie.getText().isEmpty() && !vistaAlumno.txtBuscarPorNombre.getText().isEmpty()) {
            buscarAlumnoPorNombre();
        } else if (!vistaAlumno.buscarPorNie.getText().isEmpty() && !vistaAlumno.txtBuscarPorNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe llenar solo un filtro");
        } else {
            llenarTablaDesdeArbol();
        }
    }

    private void limpiarCampos() {
        vistaAlumno.txtNIE.setText("");
        vistaAlumno.txtNombre.setText("");
        vistaAlumno.txtApellido.setText("");
        vistaAlumno.txtNIE.setEnabled(true);
        vistaAlumno.Grados.setSelectedIndex(0);
        vistaAlumno.Secciones.removeAllItems();
        vistaAlumno.Secciones.addItem("Seleccione sección");
        vistaAlumno.btnGuar.setText("Guardar");
        modoEdicion = false;
    }

    public void cargarAlumnoFormulario() {
        int fila = vistaAlumno.tablaAlumnos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vistaAlumno, "Seleccione un alumno de la tabla.");
            return;
        }
        vistaAlumno.txtNIE.setText(vistaAlumno.tablaAlumnos.getValueAt(fila, 1).toString());
        vistaAlumno.txtNombre.setText(vistaAlumno.tablaAlumnos.getValueAt(fila, 2).toString());
        vistaAlumno.txtApellido.setText(vistaAlumno.tablaAlumnos.getValueAt(fila, 3).toString());
        vistaAlumno.btnGuar.setText("Actualizar");
        modoEdicion = true;
    }

    public void actualizarAlumno() {
        try {
            if (vistaAlumno.txtNombre.getText().trim().isEmpty() || vistaAlumno.txtApellido.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(vistaAlumno, "Complete todos los campos.");
                return;
            }
            int idSeccion = getIdSeccionSeleccionada();
            if (idSeccion == -1) {
                JOptionPane.showMessageDialog(vistaAlumno, "Seleccione una sección.");
                return;
            }
            ModeloAlumno alumno = new ModeloAlumno();

            int fila = vistaAlumno.tablaAlumnos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(null, "Seleccione un estudiante de la tabla");
            }

            int id_estudiante = (int) vistaAlumno.tablaAlumnos.getValueAt(fila, 0);
            alumno.setId_alumno(id_estudiante);
            alumno.setNie(Integer.parseInt(vistaAlumno.txtNIE.getText().trim()));
            alumno.setNombre(vistaAlumno.txtNombre.getText().trim());
            alumno.setApelliddos(vistaAlumno.txtApellido.getText().trim());
            alumno.setTotalPuntos(0);

            ModeloSeccion seccion = new ModeloSeccion();
            seccion.setIdSeccion(idSeccion);
            alumno.setModeloSeccion(seccion);

            boolean actualizado = alumnoDAO.actualizarAlumno(alumno);
            if (actualizado) {
                JOptionPane.showMessageDialog(vistaAlumno, "Alumno actualizado correctamente.");
                listarAlumnos();
                limpiarCampos();
                vistaAlumno.btnGuar.setText("Guardar");
                vistaAlumno.txtNIE.setEnabled(true);
                modoEdicion = false;
            } else {
                JOptionPane.showMessageDialog(vistaAlumno, "No se pudo actualizar.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaAlumno, "Error: " + e.getMessage());
        }
    }

    public void eliminarAlumno() {
        try {
            int fila = vistaAlumno.tablaAlumnos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vistaAlumno, "Seleccione un alumno de la tabla.");
                return;
            }

            String id_alumno = vistaAlumno.tablaAlumnos.getValueAt(fila, 0).toString();

            int confirmacion = JOptionPane.showConfirmDialog(vistaAlumno, "¿Desea eliminar este alumno?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            int id = Integer.parseInt(id_alumno);
            boolean eliminado = alumnoDAO.eliminarAlumno(id);

            if (eliminado) {
                JOptionPane.showMessageDialog(vistaAlumno, "Alumno eliminado correctamente.");
                listarAlumnos();
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(vistaAlumno, "No se pudo eliminar el alumno.");
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(vistaAlumno, "El NIE de la fila seleccionada no es un número válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vistaAlumno, "Error: " + e.getMessage());
        }
    }

    public int obtenerIdAlumnoSeleccionado() {
        int fila = vistaAlumno.tablaAlumnos.getSelectedRow();
        if (fila == -1) {
            return -1;
        }
        return (int) vistaAlumno.tablaAlumnos.getValueAt(fila, 0);
    }

}
