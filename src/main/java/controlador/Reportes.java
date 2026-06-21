/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

import DAO.conexion.Conexion;
import java.awt.GridLayout;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author zair8
 */
public class Reportes  {


//    public Reportes() {
//        initComponents();
//    }
//
//    @SuppressWarnings("unchecked")
//    private void initComponents() {
//
//        btnReporte.setText("Ver Reporte");
//        btnReporte.addActionListener(evt -> {
//            abrirReporte("repDemerito.jasper");
//        });
//
//      
//    }
//
//    private void abrirReporte(String nombreReporte) {
//        try {
//            Connection cn = Conexion.getConexion();
//
//            // Parámetros para el reporte
//            Map<String, Object> params = new HashMap<>();
//            params.put("estudiante", 2); // aquí defines el ID del estudiante
//
//            InputStream archivo = getClass().getResourceAsStream(
//                    "/reportes/" + nombreReporte
//            );
//
//            JasperPrint jp = JasperFillManager.fillReport(
//                    archivo,
//                    params,   // ahora sí se pasan los parámetros
//                    cn
//            );
//
//            JasperViewer viewer = new JasperViewer(jp, false);
//            viewer.setVisible(true);
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(
//                    null,
//                    "Error al abrir reporte\n" + e
//            );
//        }
//    }

   
}