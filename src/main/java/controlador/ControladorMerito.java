package controlador;

import java.awt.Dimension;
import javax.swing.JOptionPane;
import vista.vistaAgregarMerito;
import vista.VistaPrincipalDirector;

public class ControladorMerito {

    private VistaPrincipalDirector vistaPrincipal;
    private vistaAgregarMerito vistaMerito;

    public ControladorMerito(VistaPrincipalDirector vistaPrincipal) {
        this.vistaMerito = new vistaAgregarMerito();
        this.vistaPrincipal = vistaPrincipal;

        onEventos();
    }

    private void onEventos() {
        vistaPrincipal.menuAgregarMerito.addActionListener(e -> mostrarVista());
        vistaMerito.btnCerrar.addActionListener(e -> vistaMerito.dispose());
        vistaMerito.btnAgregar.addActionListener(e -> agregarMerito());
    }

    private void agregarMerito() {

        String nie = vistaMerito.txtNie.getText();
        String nombre = vistaMerito.txtNombreCompleto.getText();
        String descripcion = vistaMerito.ComboDescripcion.getSelectedItem().toString();
        String observacion = vistaMerito.txtObservaciones.getText();

        if (nie.isEmpty() || nombre.isEmpty() || observacion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos");
            return;
        }

        JOptionPane.showMessageDialog(
                null,
                "Mérito agregado correctamente"+ "NIE: " + nie + "\n"+ "Nombre: " + nombre + "\n"+ "Descripción: " + descripcion + "\n"          + "Observación: " + observacion
        ); 
        vistaMerito.txtNie.setText("");
        vistaMerito.txtNombreCompleto.setText("");
        vistaMerito.txtObservaciones.setText("");
        vistaMerito.ComboDescripcion.setSelectedIndex(0);
    }

    public void mostrarVista() {

        vistaMerito.setVisible(true);
        Dimension desktopSize = vistaPrincipal.escritorio.getSize();
        Dimension internal = vistaMerito.getSize();
        int x = (desktopSize.width - internal.width) / 2;
        int y = (desktopSize.height - internal.height) / 2;
        vistaMerito.setLocation(x, y);
        vistaPrincipal.escritorio.remove(vistaMerito);
        vistaPrincipal.escritorio.add(vistaMerito);

        vistaMerito.toFront();
    }
}