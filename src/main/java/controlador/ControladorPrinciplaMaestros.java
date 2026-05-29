/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;


import DAO.AlumnoDAO;
import DAO.AnioSeccionDao;
import DAO.GradoDAO;
import DAO.SeccionDAO;
import DAO.gradoProfesorDAO;
import DAO.seccionProfesorDAO;
import dto.SeccionGradoDto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
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




public class ControladorPrinciplaMaestros {
 
    private VistaPrincipalMaestros vista;
    private GradoDAO gradoDAO = new GradoDAO();
    private seccionProfesorDAO seccionProfesorDAO = new seccionProfesorDAO();
    private AlumnoDAO alumnoDAO = new AlumnoDAO();
    
    private ArbolBinarioBusqueda<ModeloAlumno> arbolAlumnos;
 
    public ControladorPrinciplaMaestros(VistaPrincipalMaestros vista) {
        this.vista = vista;
    }
 
    public void iniciar() {
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vista.comboSeccion.setEnabled(false);
        cargarGrados();
        configurarEventos();
        vista.setVisible(true);
    }
 
    private void cargarGrados() {
        vista.comboGrado.removeAllItems();
        vista.comboGrado.addItem("-- Seleccione grado --");
 
        try {
            List<ModeloGrado> grados = gradoDAO.listarGrados();
            for (ModeloGrado g : grados) {
                vista.comboGrado.addItem(g.getGrado());
            }
            vista.comboGrado.putClientProperty("listaGrados", grados);
        } catch (Exception e) {
            System.out.println("Error al cargar grados: " + e.getMessage());
        }
    }
 
    private void cargarSecciones(int idGrado) {
        vista.comboSeccion.removeAllItems();
        vista.comboSeccion.addItem("-- Seleccione sección --");
 
        List<ModeloSeccion> secciones = seccionProfesorDAO.obtenerSeccionesPorGrado(idGrado);
        for (ModeloSeccion s : secciones) {
            vista.comboSeccion.addItem(s.getSeccion());
        }
        vista.comboSeccion.putClientProperty("listaSecciones", secciones);
        vista.comboSeccion.setEnabled(!secciones.isEmpty());
    }
 
  private void cargarAlumnos(int idSeccion) {

    List<ModeloAlumno> lista =
            alumnoDAO.obtenerAlumnosPorSeccion(idSeccion);

    // crea arbol
    arbolAlumnos = new ArbolBinarioBusqueda<>();

    // insertar alumno
    for (ModeloAlumno a : lista) {
        arbolAlumnos.insertar(a);
    }

    llenarTabla(lista);
}
 
    private void buscarAlumnos() {

    String textoNie = vista.txtBuscarNie.getText().trim();

    // BUSQUEDA CON ÁRBOL
    if (!textoNie.isEmpty()) {

        try {

            int nie = Integer.parseInt(textoNie);

            ModeloAlumno alumnoBuscar =
                    new ModeloAlumno(nie, "", "", 0, "", 0);

            Nodo nodo = arbolAlumnos.buscar(alumnoBuscar);

            DefaultTableModel modelo = new DefaultTableModel(
                    new String[]{"NIE", "Nombre", "Apellidos", "Puntos"}, 0
            );

            if (nodo != null) {

                ModeloAlumno encontrado =
                        (ModeloAlumno) nodo.getDato();

                modelo.addRow(new Object[]{
                    encontrado.getNie(),
                    encontrado.getNombre(),
                    encontrado.getApelliddos(),
                    encontrado.getTotalPuntos()
                });

            }

            vista.tablaEstudiantes.setModel(modelo);

        } catch (NumberFormatException e) {

            System.out.println("NIE inválido");
        }

    } else {

        // SI NO BUSCA POR NIE USA SQL NORMAL

        int idSeccion = getIdSeccionSeleccionada();

        if (idSeccion == -1) return;

        String nombre = vista.txtBuscarNombres.getText().trim();
        String apellido = vista.txtBuscarApellido.getText().trim();

        llenarTabla(
                alumnoDAO.buscarAlumnos(
                        idSeccion,
                        "",
                        nombre,
                        apellido
                )
        );
    }
}
 
    @SuppressWarnings("unchecked")
    private int getIdGradoSeleccionado() {
        int idx = vista.comboGrado.getSelectedIndex();
        if (idx <= 0) return -1;
        List<ModeloGrado> lista = (List<ModeloGrado>)
                vista.comboGrado.getClientProperty("listaGrados");
        if (lista == null || idx - 1 >= lista.size()) return -1;
        return lista.get(idx - 1).getIdGrado();
    }
 
    @SuppressWarnings("unchecked")
    private int getIdSeccionSeleccionada() {
        int idx = vista.comboSeccion.getSelectedIndex();
        if (idx <= 0) return -1;
        List<ModeloSeccion> lista = (List<ModeloSeccion>)
                vista.comboSeccion.getClientProperty("listaSecciones");
        if (lista == null || idx - 1 >= lista.size()) return -1;
        return lista.get(idx - 1).getIdSeccion();
    }
 
    private void llenarTabla(List<ModeloAlumno> alumnos) {
        DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"NIE", "Nombre", "Apellidos", "Puntos"}, 0
        );
        for (ModeloAlumno a : alumnos) {
            modelo.addRow(new Object[]{
                a.getNie(),
                a.getNombre(),
                a.getApelliddos(),
                a.getTotalPuntos()
            });
        }
        vista.tablaEstudiantes.setModel(modelo);
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
 
        vista.brnBuscar.addActionListener(e -> buscarAlumnos());
    }
}
 