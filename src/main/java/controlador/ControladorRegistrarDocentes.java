package controlador;

import DAO.DocenteDAO;
import modelo.ModeloDocente;
import javax.swing.JOptionPane;
import vista.VistaAgregarMaestros;

public class ControladorRegistrarDocentes {

    private VistaAgregarMaestros visAgregarProfes;

    public ControladorRegistrarDocentes(VistaAgregarMaestros visAgregarProfes) {
        this.visAgregarProfes = visAgregarProfes;
        iniciarVista();
        onEvento();
    }

    public void iniciarVista() {
        visAgregarProfes.setLocationRelativeTo(null);
        visAgregarProfes.setVisible(true);
    }

    private void onEvento() {

        visAgregarProfes.btnSalir.addActionListener(e -> {
            visAgregarProfes.dispose();
        });

        visAgregarProfes.btnGuardar.addActionListener(e -> guardarDocente());
    }

    private void guardarDocente() {
        String dui       = visAgregarProfes.txtDistr.getText().trim();
        String nombre    = visAgregarProfes.txtNombres.getText().trim();
        String apellido  = visAgregarProfes.txtNombres1.getText().trim();
        String depto     = visAgregarProfes.txtDepartamentos.getText().trim();
        String municipio = visAgregarProfes.txtmunicipio.getText().trim();
        String distrito  = visAgregarProfes.txtDistrito.getText().trim();
        String caserio   = visAgregarProfes.txtDistr1.getText().trim();
        String calle     = visAgregarProfes.txtDistr2.getText().trim();

        if (dui.isEmpty() || nombre.isEmpty() || apellido.isEmpty()) {
            JOptionPane.showMessageDialog(visAgregarProfes,
                "DUI, Nombre y Apellido son obligatorios.",
                "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ModeloDocente d = new ModeloDocente();
            d.setDuiDocente(dui);
            d.setNombre(nombre);
            d.setApellido(apellido);
            d.setDepartamento(depto);
            d.setMunicipio(municipio);
            d.setDistrito(distrito);
            d.setCaserio(caserio);
            d.setCalle(calle);

            new DocenteDAO().insertarDocente(d);

            JOptionPane.showMessageDialog(visAgregarProfes,
                "Docente guardado correctamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            visAgregarProfes.dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(visAgregarProfes,
                "Error al guardar: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}