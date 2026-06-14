/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.AlumnoDAO;
import DAO.VerEstadoDAO;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloAlumno;
import modelo.ModeloMovimientoConducta;
import vista.VistaVerEstado;

/**
 *
 * @author renec
 */
public class ControladorVerEstado {

    private VistaVerEstado visVerEstado;
    private ModeloAlumno alumno;

    public ControladorVerEstado(VistaVerEstado visVerEstado, ModeloAlumno alumno) {
        this.visVerEstado = visVerEstado;
        this.alumno = alumno;


        iniciarVista();
        onEvento();
        cargarTablaEstadoAlumno(alumno.getId_alumno());
        cargarDatosAlumnos();

    }

    private void iniciarVista() {
        visVerEstado.setLocationRelativeTo(null);
        visVerEstado.setVisible(true);
        visVerEstado.txtNombre.setEditable(false);
        visVerEstado.txtNombre.setFocusable(false);
    }

    private void onEvento() {
        visVerEstado.btnCerrar.addActionListener(e -> {
            visVerEstado.dispose();
        });
    }

    //R
    private void cargarTablaEstadoAlumno(int nie) {
        try {
            VerEstadoDAO dao = new VerEstadoDAO();
            List<ModeloMovimientoConducta> lista = dao.listarConductasAlumnos(nie);
            DefaultTableModel modelo = (DefaultTableModel) visVerEstado.tablaVerEstados.getModel();
            modelo.setRowCount(0);
            int totalPuntos = 0;

            for (ModeloMovimientoConducta movimiento : lista) {
                String docente = movimiento.getModeloDocente().getNombre() + " " + movimiento.getModeloDocente().getApellido();
                modelo.addRow(new Object[]{
                    movimiento.getModeloConducta().getDescripcion(),
                    docente,
                    movimiento.getFecha(),
                    movimiento.getObservacion(),
                    movimiento.getModeloConducta().getPuntos()
                });
                totalPuntos += movimiento.getModeloConducta().getPuntos();
            }
            visVerEstado.totalPuntos.setText("TOTAL PUNTOS: " + totalPuntos);

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(visVerEstado, "Error al cargar datos: " + ex.getMessage());
        }
    }

    private void cargarDatosAlumnos() {
        visVerEstado.txtNombre.setText(alumno.getNombre() + " " + alumno.getApelliddos());
    }

}
