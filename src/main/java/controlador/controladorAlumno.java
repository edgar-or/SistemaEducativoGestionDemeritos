package controlador;

import DAO.GradoDAO;
import DAO.MantenimientoAlumnoDao;
import DAO.seccionProfesorDAO;
import java.awt.Dimension;
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
    private MantenimientoAlumnoDao alumnoDAO
            = new MantenimientoAlumnoDao();

    private GradoDAO gradoDAO
            = new GradoDAO();

    private seccionProfesorDAO seccionDAO
            = new seccionProfesorDAO();

    public ControladorAlumno(
            VistaPrincipalDirector vistaPrincipal
    ) {

        this.vistaAlumno = new VistaAlumno();
        this.vistaPrincipal = vistaPrincipal;

        onEventos();

        listarAlumnos();

        cargarGrados();
    }

    // eventos
    private void onEventos() {

        // abre vista
        vistaPrincipal.menuAlumno.addActionListener(e -> mostrarVista());

        // cierra
        vistaAlumno.btnSalir.addActionListener(e -> vistaAlumno.dispose());

        vistaAlumno.btnGuar.addActionListener(e -> {

            if (modoEdicion) {

                actualizarAlumno();

            } else {

                insertarAlumno();
            }
        });

        vistaAlumno.btnModi.addActionListener(e -> {
            cargarAlumnoFormulario();
        });

        vistaAlumno.btnElimi.addActionListener(e -> {
            eliminarAlumno();
        });

        vistaAlumno.btnBuscar.addActionListener(e -> {
            buscarAlumnoPorNie();
        });

        //actualiza grados
        vistaAlumno.Grados.addActionListener(e -> {

            int idGrado
                    = getIdGradoSeleccionado();

            if (idGrado != -1) {

                cargarSecciones(idGrado);

            } else {

                vistaAlumno.Secciones.removeAllItems();

                vistaAlumno.Secciones.addItem("-- Seleccione sección --");
            }
        });
    }

    //mostrar vista
    public void mostrarVista() {

        vistaAlumno.setVisible(true);

        Dimension desktopSize = vistaPrincipal.escritorio.getSize();
        Dimension internal = vistaAlumno.getSize();

        int x = (desktopSize.width - internal.width) / 2;

        int y = (desktopSize.height - internal.height) / 2;

        vistaAlumno.setLocation(x, y);
               vistaAlumno.setSize(900, 500);


        vistaPrincipal.escritorio.remove(vistaAlumno);

        vistaPrincipal.escritorio.add(vistaAlumno);

        vistaAlumno.toFront();
    }

    //lista alumnos en tabla
    public void listarAlumnos() {

        try {

            List<ModeloAlumno> lista = alumnoDAO.listarAlumnos();

            //crea arbol
            arbolAlumnos = new ArbolBinarioBusqueda<>();

            //incerta arbol
            for (ModeloAlumno a : lista) {
                arbolAlumnos.insertar(a);
            }

            // llena la tabla
            llenarTablaDesdeArbol();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(vistaAlumno, "Error al listar alumnos: " + e.getMessage());
        }
    }

    public void llenarTablaDesdeArbol() {

        DefaultTableModel modelo = new DefaultTableModel(new String[]{
            "NIE",
            "Nombre",
            "Apellido",
            "Puntos"
        }, 0
        );

        //arma lista in orden
        List<ModeloAlumno> lista = arbolAlumnos.IND();

        for (ModeloAlumno a : lista) {

            modelo.addRow(new Object[]{
                a.getNie(),
                a.getNombre(),
                a.getApelliddos(),
                a.getTotalPuntos()
            });
        }

        vistaAlumno.tablaAlumnos.setModel(modelo);
    }

    // carga grados
    public void cargarGrados() {

        vistaAlumno.Grados.removeAllItems();

        vistaAlumno.Grados.addItem("-- Seleccione grado --");

        try {

            List<ModeloGrado> grados = gradoDAO.listarGrados();

            for (ModeloGrado g : grados) {
                vistaAlumno.Grados.addItem(g.getGrado());
            }

            vistaAlumno.Grados.putClientProperty(
                    "listaGrados",
                    grados
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    vistaAlumno,
                    "Error al cargar grados: "
                    + e.getMessage()
            );
        }
    }

    // carga Secciones
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

    // obtener idGrado
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

    // obtener idSeccion
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
            // calida camps
            if (vistaAlumno.txtNIE.getText().trim().isEmpty() || vistaAlumno.txtNombre.getText().trim().isEmpty() || vistaAlumno.txtApellido.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(vistaAlumno, "Complete todos los campos.");

                return;
            }

            int idSeccion = getIdSeccionSeleccionada();

            if (idSeccion == -1) {

                JOptionPane.showMessageDialog(vistaAlumno, "Seleccione una sección.");

                return;
            }

            // crea objetos
            ModeloAlumno alumno = new ModeloAlumno();

            alumno.setNie(Integer.parseInt(vistaAlumno.txtNIE.getText().trim())
            );

            alumno.setNombre(vistaAlumno.txtNombre.getText().trim()
            );

            alumno.setApelliddos(vistaAlumno.txtApellido.getText().trim());

            alumno.setTotalPuntos(0);

            alumno.setIdSeccion(idSeccion);
            //inserta en bd
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

            Nodo nodo = arbolAlumnos.buscar(alumnoBuscar);

            DefaultTableModel modelo = new DefaultTableModel(
                    new String[]{
                        "NIE",
                        "Nombre",
                        "Apellido",
                        "Puntos"
                    }, 0
            );

            if (nodo != null) {

                ModeloAlumno a = (ModeloAlumno) nodo.getDato();

                modelo.addRow(new Object[]{
                    a.getNie(),
                    a.getNombre(),
                    a.getApelliddos(),
                    a.getTotalPuntos()
                });
            }

            vistaAlumno.tablaAlumnos.setModel(modelo);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(vistaAlumno, "Error: " + e.getMessage());
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

            JOptionPane.showMessageDialog(vistaAlumno,"Seleccione un alumno de la tabla." );

            return;
        }

        // carga datos
       
        vistaAlumno.txtNIE.setText(vistaAlumno.tablaAlumnos.getValueAt(fila, 0).toString());

        vistaAlumno.txtNombre.setText(vistaAlumno.tablaAlumnos.getValueAt(fila, 1).toString());

        vistaAlumno.txtApellido.setText(vistaAlumno.tablaAlumnos.getValueAt(fila, 2).toString());

        // bloquea el campo nie
        vistaAlumno.txtNIE.setEnabled(false);

       
        vistaAlumno.btnGuar.setText("Actualizar");

        modoEdicion = true;
    }

    public void actualizarAlumno() {

        try {

            if (vistaAlumno.txtNombre.getText().trim().isEmpty() || vistaAlumno.txtApellido.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(vistaAlumno,"Complete todos los campos.");

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

            alumno.setIdSeccion(idSeccion);

            boolean actualizado = alumnoDAO.actualizarAlumno(alumno);

            if (actualizado) {

                JOptionPane.showMessageDialog(vistaAlumno, "Alumno actualizado correctamente.");

                listarAlumnos();

                limpiarCampos();

              
                vistaAlumno.btnGuar.setText("Guardar");

                vistaAlumno.txtNIE.setEnabled(true);

                modoEdicion = false;

            } else {

                JOptionPane.showMessageDialog(vistaAlumno,"No se pudo actualizar.");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog( vistaAlumno, "Error: " + e.getMessage());
        }
    }

    public void eliminarAlumno() {

        try {

            int fila
                    = vistaAlumno.tablaAlumnos.getSelectedRow();

            if (fila == -1) {

                JOptionPane.showMessageDialog(vistaAlumno,"Seleccione un alumno de la tabla.");

                return;
            }

            // captura nie
            String nie = vistaAlumno.tablaAlumnos.getValueAt(fila, 0).toString();

            // Ccnfirma
            int confirmacion = JOptionPane.showConfirmDialog(vistaAlumno,"¿Desea eliminar este alumno?","Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION
                    );

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            // elimina en bd
            boolean eliminado = alumnoDAO.eliminarAlumno(nie);

            if (eliminado) {

                JOptionPane.showMessageDialog(vistaAlumno,"Alumno eliminado correctamente.");

                listarAlumnos();

                limpiarCampos();

            } else {

                JOptionPane.showMessageDialog(vistaAlumno,"No se pudo eliminar el alumno.");
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(vistaAlumno,"Error: " + e.getMessage());
        }
    }

}
