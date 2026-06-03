/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.SeccionDAO;
import java.util.HashSet;
import javax.swing.JOptionPane;
import modelo.ModeloGrado;
import modelo.ModeloSeccion;
import vista.VistaAgregarSeccion;

/**
 *
 * @author ayala
 */
public class ControladorAgregarSeccion {

    VistaAgregarSeccion visAgregarSeccion;
    ControladorGrado controladorGrado;
    private SeccionDAO secDao = new SeccionDAO();

    public ControladorAgregarSeccion(ControladorGrado controlGrado) {
        visAgregarSeccion = new VistaAgregarSeccion();
        this.controladorGrado = controlGrado;
        eventos();
    }

    private void eventos() {
        visAgregarSeccion.btnCerrar.addActionListener(e -> cerrarVista());
        visAgregarSeccion.btnAgregarSeccion.addActionListener(e -> registrarSeccion());
    }

    public void iniciar() {
        visAgregarSeccion.setVisible(true);
        visAgregarSeccion.setLocationRelativeTo(null);
    }

    private void cerrarVista() {
        visAgregarSeccion.dispose();
    }

    public void registrarSeccion() {
        try {
            String seccionSelect = (String) visAgregarSeccion.comboSeccion.getSelectedItem();
            int idGrado = controladorGrado.obtenerIdGradoDeTabla();

            ModeloSeccion seccion = new ModeloSeccion();
            ModeloGrado grado = new ModeloGrado();

            grado.setIdGrado(idGrado);
            seccion.setSeccion(seccionSelect);
            seccion.setModeloGrado(grado);

            secDao.insertarSeccion(seccion);

            JOptionPane.showMessageDialog(null, "Sección asignada correctamente");
            cerrarVista();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "No se pudo asignar sección al grado seleccionado\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
