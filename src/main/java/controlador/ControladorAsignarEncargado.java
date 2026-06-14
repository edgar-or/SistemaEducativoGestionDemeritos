/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.AsignarEncargadoAlumnoDao;
import DAO.EncargadoDAO;
import java.awt.Dimension;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ArbolBinarioBusqueda;
import modelo.ModeloEncardoAlumno;
import vista.VistaAsignarEncargado;
import vista.VistaPrincipalDirector;

/**
 *
 * @author ayala
 */
public class ControladorAsignarEncargado {

    private VistaAsignarEncargado visAsignarEncargado;
    private VistaPrincipalDirector vistaPrincipal;
    private ControladorAlumno controlAlumno;
    private EncargadoDAO encargadoDao;

    public ControladorAsignarEncargado(VistaPrincipalDirector vistaPrincipal, ControladorAlumno controlAlumno) {

        visAsignarEncargado = new VistaAsignarEncargado();

        this.controlAlumno = controlAlumno;

        this.vistaPrincipal = vistaPrincipal;

        llenarTabla();

        eventos();

    }

    private void eventos() {
        visAsignarEncargado.btnCerrar.addActionListener(e -> visAsignarEncargado.dispose());

        visAsignarEncargado.btnBucar.addActionListener(e -> {
            try {
                filtro();
            } catch (SQLException ex) {
                System.getLogger(ControladorAsignarEncargado.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });

        visAsignarEncargado.txtBuscarNombre.addActionListener(e -> {
            asignarEncargado();
        });

    }

    public void iniciarVista() {
        visAsignarEncargado.setSize(800, 500); // agregás esto primero
        visAsignarEncargado.setVisible(true);
        Dimension desk = vistaPrincipal.escritorio.getSize();
        Dimension win = visAsignarEncargado.getSize();
        visAsignarEncargado.setLocation((desk.width - win.width) / 2, (desk.height - win.height) / 2);
        vistaPrincipal.escritorio.remove(visAsignarEncargado);
        vistaPrincipal.escritorio.add(visAsignarEncargado);
        visAsignarEncargado.toFront();
    }


    public void llenarTabla() {

        encargadoDao = new EncargadoDAO();

        DefaultTableModel modeloTabla = (DefaultTableModel) visAsignarEncargado.tablaEncargados.getModel();
        String[] titulos = {"ID", "DUI", "Nombre Completo"};
        modeloTabla.setColumnIdentifiers(titulos);
        modeloTabla.setRowCount(0);

        try {
            ArbolBinarioBusqueda<ModeloEncardoAlumno> arbol = encargadoDao.listarEncargados();

            List<ModeloEncardoAlumno> lista = arbol.IND();

            for (ModeloEncardoAlumno e : lista) {
                Object[] fila = new Object[]{
                    e.getIdEncargado(),
                    e.getDui(),
                    e.getPrimerNombre() + " " + e.getSegundoNombre() + " " + e.getPrimerApellido() + " " + e.getSegundoApellido(),};
                modeloTabla.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar los datos en la tabla: " + e.getMessage(),
                    "Error de Carga",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filtro() throws SQLException {

        if (!visAsignarEncargado.txtPorDui.getText().isEmpty() && visAsignarEncargado.txtBuscarPorNombre.getText().isEmpty()) {
            buscarPorDui();
        } else if (visAsignarEncargado.txtPorDui.getText().isEmpty() && !visAsignarEncargado.txtBuscarPorNombre.getText().isEmpty()) {
            buscarPorNombre();

        } else if (visAsignarEncargado.txtPorDui.getText().isEmpty() && visAsignarEncargado.txtBuscarPorNombre.getText().isEmpty()) {

            llenarTabla();

        } else {
            JOptionPane.showMessageDialog(null,
                    "Solo debe buscar por un filtro ",
                    "Error de Carga",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void buscarPorDui() {
        encargadoDao = new EncargadoDAO();

        String dui = visAsignarEncargado.txtPorDui.getText();

        DefaultTableModel modeloTabla = (DefaultTableModel) visAsignarEncargado.tablaEncargados.getModel();
        String[] titulos = {"ID", "DUI", "Nombre Completo"};
        modeloTabla.setColumnIdentifiers(titulos);
        modeloTabla.setRowCount(0);

        try {
            ModeloEncardoAlumno e = encargadoDao.buscarPorDui(dui);

            Object[] fila = new Object[]{
                e.getIdEncargado(),
                e.getDui(),
                e.getPrimerNombre() + " " + e.getSegundoNombre() + " " + e.getPrimerApellido() + " " + e.getSegundoApellido(),};
            modeloTabla.addRow(fila);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar los datos en la tabla: " + e.getMessage(),
                    "Error de Carga",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    private void buscarPorNombre() throws SQLException {
        encargadoDao = new EncargadoDAO();

        String nombre = visAsignarEncargado.txtBuscarPorNombre.getText().trim();

        DefaultTableModel modeloTabla = (DefaultTableModel) visAsignarEncargado.tablaEncargados.getModel();
        String[] titulos = {"ID", "DUI", "Nombre Completo"};
        modeloTabla.setColumnIdentifiers(titulos);
        modeloTabla.setRowCount(0);

        try {
            List<ModeloEncardoAlumno> listaBusqueda = encargadoDao.buscarPorNombre(nombre);

            for (ModeloEncardoAlumno e : listaBusqueda) {
                Object[] fila = new Object[]{
                    e.getIdEncargado(),
                    e.getDui(),
                    e.getPrimerNombre() + " " + e.getSegundoNombre() + " " + e.getPrimerApellido() + " " + e.getSegundoApellido(),};
                modeloTabla.addRow(fila);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar los datos en la tabla: " + e.getMessage(),
                    "Error de Carga",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public int obtenerIdEncargadoSeleccionado() {
        int fila = visAsignarEncargado.tablaEncargados.getSelectedRow();
        if (fila == -1) {
            return -1;
        }
        return (int) visAsignarEncargado.tablaEncargados.getValueAt(fila, 0);
    }

    private void asignarEncargado() {

        AsignarEncargadoAlumnoDao asignarEncargadoDao = new AsignarEncargadoAlumnoDao();

        int idAlumno = controlAlumno.obtenerIdAlumnoSeleccionado();

        int idEncargado = obtenerIdEncargadoSeleccionado();

        System.out.println("id encargado: " + idEncargado);

        System.out.println("id alumno: " + idAlumno);

        if (idEncargado != -1) {

            if (asignarEncargadoDao.asignarEncargadoAAlumno(idAlumno, idEncargado)) {
                JOptionPane.showMessageDialog(null, "Encargado asignado correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Error al asignar el encargado");

            }

        } else {
            JOptionPane.showMessageDialog(null, "seleccione un encargado de la tabla");

        }

    }

}
