package controlador;

import java.awt.Dimension;
import javax.swing.JOptionPane;
import vista.VistaAgregarMerito;
import vista.VistaPrincipalDirector;

public class ControladorMerito {

    private VistaPrincipalDirector vistaPrincipal;
    private VistaAgregarMerito vistaMerito;

    public ControladorMerito(VistaPrincipalDirector vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        this.vistaMerito = new VistaAgregarMerito();

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
                "Mérito agregado correctamente\n\n"
                + "NIE: " + nie + "\n"
                + "Nombre: " + nombre + "\n"
                + "Descripción: " + descripcion + "\n"
                + "Observación: " + observacion
        );

        vistaMerito.txtNie.setText("");
        vistaMerito.txtNombreCompleto.setText("");
        vistaMerito.txtObservaciones.setText("");
        vistaMerito.ComboDescripcion.setSelectedIndex(0);
    }

    public void mostrarVista() {

        if (vistaMerito.getParent() == null) {
            vistaPrincipal.escritorio.add(vistaMerito);
        }

        Dimension desktopSize = vistaPrincipal.escritorio.getSize();
        Dimension internalSize = vistaMerito.getSize();

        int x = (desktopSize.width - internalSize.width) / 2;
        int y = (desktopSize.height - internalSize.height) / 2;

        vistaMerito.setLocation(x, y);
        vistaMerito.setVisible(true);
        vistaMerito.toFront();
    }
}
