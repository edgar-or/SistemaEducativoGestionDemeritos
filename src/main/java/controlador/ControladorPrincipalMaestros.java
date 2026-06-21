package controlador;

import DAO.AlumnoDAO;
import DAO.GradoDAO;
import DAO.seccionProfesorDAO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ArbolBinarioBusqueda;
import modelo.Login;
import modelo.ModeloAlumno;
import modelo.ModeloGrado;
import modelo.ModeloSeccion;
import modelo.Nodo;
import vista.VistaAgregarDemerito;
import vista.VistaAgregarMerito;
import vista.VistaLogin;
import vista.VistaPrincipalMaestros;
import vista.VistaVerEstado;

public class ControladorPrincipalMaestros {

    private VistaPrincipalMaestros vista;
    private GradoDAO gradoDAO = new GradoDAO();
    private seccionProfesorDAO seccionProfesorDAO = new seccionProfesorDAO();
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    private ArbolBinarioBusqueda<ModeloAlumno> arbolAlumnos;

    public ControladorPrincipalMaestros() {
    }

    public ControladorPrincipalMaestros(VistaPrincipalMaestros vista) {
        this.vista = vista;
    }

    public void iniciar() {
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vista.comboSeccion.setEnabled(false);
        cargarGrados();
        configurarEventos();
        vista.setVisible(true);
        limpiarBusqueda();
    }

    public void cargarGrados() {
        vista.comboGrado.removeAllItems();
        vista.comboGrado.addItem("Seleccione Grado");
        try {
            List<ModeloGrado> grados = gradoDAO.listarGrados();
            for (ModeloGrado g : grados) {
                vista.comboGrado.addItem(g.getGrado());
            }
            vista.comboGrado.putClientProperty("listaGrados", grados);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar grados: " + e.getMessage());
        }
    }

    private void limpiarBusqueda() {
        vista.txtBuscarNie.setText("");
        vista.txtBuscarNombres.setText("");
        vista.txtBuscarApellido.setText("");

        int idSeccion = getIdSeccionSeleccionada();

        if (idSeccion != -1) {
            llenarTabla(alumnoDAO.obtenerAlumnosPorSeccion(idSeccion));
        }
    }

    public void cargarSecciones(int idGrado) {
        vista.comboSeccion.removeAllItems();
        vista.comboSeccion.addItem("Seleccione sección");
        List<ModeloSeccion> secciones = seccionProfesorDAO.obtenerSeccionesPorGrado(idGrado);
        for (ModeloSeccion s : secciones) {
            vista.comboSeccion.addItem(s.getSeccion());
        }
        vista.comboSeccion.putClientProperty("listaSecciones", secciones);
        vista.comboSeccion.setEnabled(!secciones.isEmpty());
    }

    private void cargarAlumnos(int idSeccion) {
        List<ModeloAlumno> lista = alumnoDAO.obtenerAlumnosPorSeccion(idSeccion);
        arbolAlumnos = new ArbolBinarioBusqueda<>();
        for (ModeloAlumno a : lista) {
            arbolAlumnos.insertar(a);
        }
        llenarTabla(lista);
    }

    private void buscarAlumnos() {

        int idSeccion = getIdSeccionSeleccionada();

        if (idSeccion == -1) {
            return;
        }

        String textoNie = vista.txtBuscarNie.getText().trim();
        String nombre = vista.txtBuscarNombres.getText().trim();
        String apellido = vista.txtBuscarApellido.getText().trim();

        //Validacion para que solo busqye por un filtro
        int filtrosActivos = 0;

        if (!textoNie.isEmpty()) {
            filtrosActivos++;
        }

        if (!nombre.isEmpty()) {
            filtrosActivos++;
        }

        if (!apellido.isEmpty()) {
            filtrosActivos++;
        }

        if (filtrosActivos > 1) {
            JOptionPane.showMessageDialog(
                    vista,
                    "Utilice únicamente un filtro a la vez.\n"
                    + "Puede buscar por NIE, Nombre o Apellido.",
                    "Búsqueda inválida",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        // validacion de nie 
        if (!textoNie.isEmpty()) {

            if (!textoNie.matches("\\d+")) {
                JOptionPane.showMessageDialog(
                        vista,
                        "El NIE solo puede contener números.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                vista.txtBuscarNie.requestFocus();
                return;
            }

            int nie = Integer.parseInt(textoNie);

            ModeloAlumno alumnoBuscar = new ModeloAlumno();
            alumnoBuscar.setNie(nie);

            Nodo nodo = arbolAlumnos.buscar(alumnoBuscar);

            DefaultTableModel modelo = new DefaultTableModel(
                    new String[]{"ID", "NIE", "Nombre", "Apellidos", "Puntos"}, 0
            );

            if (nodo != null) {

                ModeloAlumno encontrado = (ModeloAlumno) nodo.getDato();

                modelo.addRow(new Object[]{
                    encontrado.getId_alumno(),
                    encontrado.getNie(),
                    encontrado.getNombre(),
                    encontrado.getApelliddos(),
                    encontrado.getTotalPuntos()
                });

            } else {

                JOptionPane.showMessageDialog(
                        vista,
                        "No se encontró ningún estudiante con ese NIE.",
                        "Sin resultados",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            vista.tablaEstidiantes.setModel(modelo);
            return;
        }

        // validacion del nombre
        if (!nombre.isEmpty()) {

            if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {

                JOptionPane.showMessageDialog(
                        vista,
                        "El nombre solo puede contener letras.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                vista.txtBuscarNombres.requestFocus();
                return;
            }
        }

        // validacion del apeillido      
        if (!apellido.isEmpty()) {

            if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {

                JOptionPane.showMessageDialog(
                        vista,
                        "El apellido solo puede contener letras.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                vista.txtBuscarApellido.requestFocus();
                return;
            }
        }

        // busqueda ya sea por el nombre o por el apellido
        List<ModeloAlumno> resultados = alumnoDAO.buscarAlumnos(
                idSeccion,
                "",
                nombre,
                apellido
        );

        if (resultados.isEmpty()) {

            if (!nombre.isEmpty()) {
                JOptionPane.showMessageDialog(
                        vista,
                        "Nombre no encontrado.",
                        "Sin resultados",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else if (!apellido.isEmpty()) {
                JOptionPane.showMessageDialog(
                        vista,
                        "Apellido no encontrado.",
                        "Sin resultados",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

            return;
        }

        llenarTabla(resultados);
    }

    @SuppressWarnings("unchecked")
    public int getIdGradoSeleccionado() {
        int idx = vista.comboGrado.getSelectedIndex();
        if (idx <= 0) {
            return -1;
        }
        List<ModeloGrado> lista = (List<ModeloGrado>) vista.comboGrado.getClientProperty("listaGrados");
        if (lista == null || idx - 1 >= lista.size()) {
            return -1;
        }
        return lista.get(idx - 1).getIdGrado();
    }

    @SuppressWarnings("unchecked")
    private int getIdSeccionSeleccionada() {
        int idx = vista.comboSeccion.getSelectedIndex();
        if (idx <= 0) {
            return -1;
        }
        List<ModeloSeccion> lista = (List<ModeloSeccion>) vista.comboSeccion.getClientProperty("listaSecciones");
        if (lista == null || idx - 1 >= lista.size()) {
            return -1;
        }
        return lista.get(idx - 1).getIdSeccion();
    }

    private void llenarTabla(List<ModeloAlumno> alumnos) {
        DefaultTableModel modelo = new DefaultTableModel(
                new String[]{"ID", "NIE", "Nombre", "Apellidos", "Puntos"}, 0
        );
        for (ModeloAlumno a : alumnos) {
            modelo.addRow(new Object[]{
                a.getId_alumno(),
                a.getNie(),
                a.getNombre(),
                a.getApelliddos(),
                a.getTotalPuntos()
            });
        }
        vista.tablaEstidiantes.setModel(modelo);
    }

    private void configurarEventos() {
        vista.comboGrado.addActionListener(e -> {
            int idGrado = getIdGradoSeleccionado();
            if (idGrado != -1) {
                cargarSecciones(idGrado);
            } else {
                vista.comboSeccion.removeAllItems();
                vista.comboSeccion.addItem("-- Seleccione sección --");
                vista.comboSeccion.setEnabled(false);
                llenarTabla(new ArrayList<>());
            }
        });

        vista.comboSeccion.addActionListener(e -> {
            int idSeccion = getIdSeccionSeleccionada();
            if (idSeccion != -1) {
                cargarAlumnos(idSeccion);
            }
        });

        vista.btnBuscar.addActionListener(e -> buscarAlumnos());

        vista.btnAgregarDemerito.addActionListener(e -> {
            int filaSeleccionada = vista.tablaEstidiantes.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(vista, "Por favor, seleccione un estudiante.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nie = vista.tablaEstidiantes.getValueAt(filaSeleccionada, 1).toString();
            String id = vista.tablaEstidiantes.getValueAt(filaSeleccionada, 0).toString();
            String nombreCompleto = vista.tablaEstidiantes.getValueAt(filaSeleccionada, 2).toString() + " " + vista.tablaEstidiantes.getValueAt(filaSeleccionada, 2).toString();
            VistaAgregarDemerito vistaDemerito = new VistaAgregarDemerito();
            new ControladorAgregarDemerito(vistaDemerito, nie, id, nombreCompleto);
        });

        vista.btnAgregarMerito.addActionListener(e -> {
            int filaSeleccionada = vista.tablaEstidiantes.getSelectedRow();
            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(vista, "Por favor, seleccione un estudiante.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nie = vista.tablaEstidiantes.getValueAt(filaSeleccionada, 1).toString();
            String id = vista.tablaEstidiantes.getValueAt(filaSeleccionada, 0).toString();
            String nombreCompleto = vista.tablaEstidiantes.getValueAt(filaSeleccionada, 2).toString() + " " + vista.tablaEstidiantes.getValueAt(filaSeleccionada, 2).toString();
            VistaAgregarMerito vistaMerito = new VistaAgregarMerito();
            new ControladorAgregarMerito(vistaMerito, nie, id, nombreCompleto);
        });

        vista.btnVerEstado.addActionListener(e -> {

            int filaSeleccionada = vista.tablaEstidiantes.getSelectedRow();

            if (filaSeleccionada == -1) {
                JOptionPane.showMessageDialog(
                        vista, "Por favor, seleccione un estudiante.", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ModeloAlumno alumnoActual = new ModeloAlumno();
            alumnoActual.setId_alumno(Integer.parseInt(vista.tablaEstidiantes.getValueAt(filaSeleccionada, 0).toString()));
            alumnoActual.setNie(Integer.parseInt(vista.tablaEstidiantes.getValueAt(filaSeleccionada, 1).toString()));
            alumnoActual.setNombre(vista.tablaEstidiantes.getValueAt(filaSeleccionada, 2).toString());
            alumnoActual.setApelliddos(vista.tablaEstidiantes.getValueAt(filaSeleccionada, 3).toString());
            VistaVerEstado v = new VistaVerEstado();
            new ControladorVerEstado(v, alumnoActual);

        });

        vista.btnCerrarsesion.addActionListener(e -> {
            VistaLogin login = new VistaLogin();
            Login modelo = new Login();
            new ControladorLogin(login, modelo);
            login.setLocationRelativeTo(null);
            login.setVisible(true);
            vista.dispose();
        });
        vista.btnLimpiar.addActionListener(e -> {
            limpiarBusqueda();

        });
    }
}
