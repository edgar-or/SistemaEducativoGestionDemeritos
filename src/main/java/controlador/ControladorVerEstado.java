/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.AlumnoDAO;
import DAO.VerEstadoDAO;
import DAO.conexion.Conexion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.ModeloAlumno;
import modelo.ModeloMovimientoConducta;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import utileria.ArbolB;
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
        //abrirReporte("repDemerito.jasper");
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

        visVerEstado.btnReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirReporte("repDemerito.jasper");
            }
        });
    }

    private void cargarTablaEstadoAlumno(int nie) {
        try {
            VerEstadoDAO dao = new VerEstadoDAO();

            ArbolB<ModeloMovimientoConducta> arbol = dao.listarConductasAlumnosEnArbol(nie);

            DefaultTableModel modelo = (DefaultTableModel) visVerEstado.tablaVerEstados.getModel();
            modelo.setRowCount(0);

            List<ModeloMovimientoConducta> listaOrdenada = arbol.obtenerListaOrdenada();
            int totalPuntos = 0;

            for (ModeloMovimientoConducta movimiento : listaOrdenada) {

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

    private void abrirReporte(String nombreReporte) {
        try {
            Connection cn = Conexion.getConexion();

            // Parámetros para el reporte
            Map<String, Object> params = new HashMap<>();
            params.put("estudiante", alumno.getId_alumno());
            InputStream archivo = getClass().getResourceAsStream(
                    "/reportes/" + nombreReporte
            );

            JasperPrint jp = JasperFillManager.fillReport(
                    archivo,
                    params, // ahora sí se pasan los parámetros
                    cn
            );

            JasperViewer viewer = new JasperViewer(jp, false);
            viewer.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error al abrir reporte\n" + e
            );
        }
    }
}
