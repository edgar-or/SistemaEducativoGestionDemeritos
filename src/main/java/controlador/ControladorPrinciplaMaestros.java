/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

<<<<<<< HEAD
import DAO.AlumnoDAO;
import DAO.gradoProfesorDAO;
import DAO.seccionProfesorDAO;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloAlumno;
import modelo.ModeloGrado;
import modelo.ModeloSeccion;
=======
import DAO.AñoSeccionDao;
import dto.SeccionGradoDto;
import java.util.List;
import javax.swing.JFrame;
import modelo.Login;
import vista.VistaAgregarDemerito;
import vista.VistaAgregarMerito;
import vista.VistaLogin;
>>>>>>> 0d18ed142cb5075dba1ccf9fd7fe192f8993b176
import vista.VistaPrincipalMaestros;
import vista.VistaPrincipalMaestros;
import vista.VistaVerEstado;

/**
 *
 * @author renec
 */
/*public class ControladorPrinciplaMaestros {

    private VistaPrincipalMaestros visPrincipalMaaestros;
    private ControladorMerito controladorMerito;

    public ControladorPrinciplaMaestros(VistaPrincipalMaestros visPrincipalMaaestros) {

        this.visPrincipalMaaestros = visPrincipalMaaestros;
        


        eventos();
        this.controladorMerito = new ControladorMerito(visPrincipalMaaestros);
    }

    public void iniciar() {

        visPrincipalMaaestros.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        visPrincipalMaaestros.setExtendedState(JFrame.MAXIMIZED_BOTH);
        visPrincipalMaaestros.setVisible(true);
        
        llenarComboSeccion(); 

    }
    
    public void llenarComboSeccion(){
        AñoSeccionDao dao = new AñoSeccionDao();
List<SeccionGradoDto> lista = dao.listarSeccionGrado();

for (SeccionGradoDto obj : lista) {
    visPrincipalMaaestros.comboSeccion.addItem(obj.getSeccion().getSeccion());
    visPrincipalMaaestros.comboGrado.addItem(obj.getGrado().getGrado());
}
    }
    
    
    
    
    private void eventos() {

        visPrincipalMaaestros.btnAgregarDemerito.addActionListener(e -> {

            VistaAgregarDemerito vista = new VistaAgregarDemerito();
            new ControladorAgregarDemerito(vista);
        });
        visPrincipalMaaestros.btnAgregarMerito.addActionListener(e -> {

            VistaAgregarMerito vista = new VistaAgregarMerito();
            new ControladorAgregarMerito(vista);
        });

        visPrincipalMaaestros.btnVerEstado.addActionListener(e -> {

            VistaVerEstado vista = new VistaVerEstado();
            new ControladorVerEstado(vista);

        });
        visPrincipalMaaestros.btnCerrarsesion.addActionListener(e -> {
            // crear vista y modelo
            VistaLogin login = new VistaLogin();
            Login modelo = new Login();

            // crear controlador correctamente
            new ControladorLogin(login, modelo);

            login.setLocationRelativeTo(null);
            login.setVisible(true);

            // cerrar la ventana actual
            visPrincipalMaaestros.dispose();
        });

    }

}*/
public class ControladorPrinciplaMaestros {
 
    private VistaPrincipalMaestros vista;
    private gradoProfesorDAO   gradoDAO   = new gradoProfesorDAO();
    private seccionProfesorDAO seccionDAO = new seccionProfesorDAO();
    private AlumnoDAO  alumnoDAO  = new AlumnoDAO();
 
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
 
        List<ModeloGrado> grados = gradoDAO.obtenerGrados();
        for (ModeloGrado g : grados) {
            vista.comboGrado.addItem(g.getGrado());
        }
        vista.comboGrado.putClientProperty("listaGrados", grados);
    }
 
    private void cargarSecciones(int idGrado) {
        vista.comboSeccion.removeAllItems();
        vista.comboSeccion.addItem("-- Seleccione sección --");
 
        List<ModeloSeccion> secciones = seccionDAO.obtenerSeccionesPorGrado(idGrado);
        for (ModeloSeccion s : secciones) {
            vista.comboSeccion.addItem(s.getGrado()); // retorna "A", "B", etc.
        }
        vista.comboSeccion.putClientProperty("listaSecciones", secciones);
        vista.comboSeccion.setEnabled(!secciones.isEmpty());
    }
 
    private void cargarAlumnos(int idSeccion) {
        llenarTabla(alumnoDAO.obtenerAlumnosPorSeccion(idSeccion));
    }
 
    private void buscarAlumnos() {
        int idSeccion = getIdSeccionSeleccionada();
        if (idSeccion == -1) return;
 
        String nie      = vista.txtBuscarNie.getText().trim();
        String nombre   = vista.txtBuscarNombres.getText().trim();
        String apellido = vista.txtBuscarApellido.getText().trim();
 
        llenarTabla(alumnoDAO.buscarAlumnos(idSeccion, nie, nombre, apellido));
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
        vista.jTable1.setModel(modelo);
    }
  
    private void configurarEventos() {
 
        vista.comboGrado.addActionListener(e -> {
            int idGrado = getIdGradoSeleccionado();
            if (idGrado != -1) {
                cargarSecciones(idGrado);
            } else {
                vista.comboSeccion.removeAllItems();
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