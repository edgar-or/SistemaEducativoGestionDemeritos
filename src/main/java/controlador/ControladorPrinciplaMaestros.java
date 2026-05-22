/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.AlumnoDAO;
import DAO.AñoSeccionDao;
import dto.SeccionGradoDto;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import modelo.Login;
import modelo.ModeloAlumno;
import vista.VistaAgregarDemerito;
import vista.VistaAgregarMerito;
import vista.VistaLogin;
import vista.VistaPrincipalMaestros;
import vista.VistaPrincipalMaestros;
import vista.VistaVerEstado;

/**
 *
 * @author renec
 */
public class ControladorPrinciplaMaestros {

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
        cargarTabla();

    }

    public void llenarComboSeccion() {
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

    private void cargarTabla() {

        try {
            AlumnoDAO dao = new AlumnoDAO();
            List<ModeloAlumno> lista = dao.listarEstudiantes();

            DefaultTableModel modelo = new DefaultTableModel();

            // columnas
            modelo.addColumn("NIE");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido");
            modelo.addColumn("Puntos Totales");

            // filas
            for (ModeloAlumno e : lista) {
                modelo.addRow(new Object[]{
                    e.getNie(),
                    e.getNombre(),
                    e.getApelliddos(),
                    e.getTotalPuntos()
                });
            }

            visPrincipalMaaestros.tablaEstudiantes.setModel(modelo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
