package controlador;

import java.awt.Dimension;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import vista.VistaAgregarMerito;
import vista.VistaPrincipalDirector;
import vista.VistaPrincipalMaestros;

public class ControladorMerito {

    private VistaPrincipalDirector vistaPrincipal;
    private VistaPrincipalMaestros vistaPrincipalMaestros;
    private VistaAgregarMerito vistaMerito;
    

    // Constructor para Director (usa JDesktopPane con menú)
    public ControladorMerito(VistaPrincipalDirector vistaPrincipal) {
        this.vistaPrincipal = vistaPrincipal;
        this.vistaMerito = new VistaAgregarMerito();
        onEventosDirector();
    }

    // Constructor para Maestros (usa JFrame con botón)
    public ControladorMerito(VistaPrincipalMaestros vistaPrincipalMaestros) {
        this.vistaPrincipalMaestros = vistaPrincipalMaestros;
        this.vistaMerito = new VistaAgregarMerito();
        onEventosMaestros();
    }

    private void onEventosDirector() {
        // Limpiar listener previo de NetBeans
        for (java.awt.event.ActionListener al : vistaPrincipal.menuAgregarMerito.getActionListeners()) {
            vistaPrincipal.menuAgregarMerito.removeActionListener(al);
        }
        vistaPrincipal.menuAgregarMerito.addActionListener(e -> mostrarVistaEnDesktop(vistaPrincipal.escritorio));
        vistaMerito.btnCerrar.addActionListener(e -> vistaMerito.dispose());
        vistaMerito.btnAgregar.addActionListener(e -> agregarMerito());
    }

    private void onEventosMaestros() {
        // Limpiar listener previo de NetBeans
        for (java.awt.event.ActionListener al : vistaPrincipalMaestros.btnAgregarMerito.getActionListeners()) {
            vistaPrincipalMaestros.btnAgregarMerito.removeActionListener(al);
        }
        // VistaPrincipalMaestros no tiene JDesktopPane, se muestra en JDialog
        vistaPrincipalMaestros.btnAgregarMerito.addActionListener(e -> mostrarVistaComoDialogo());
        vistaMerito.btnCerrar.addActionListener(e -> cerrarDialogo());
        vistaMerito.btnAgregar.addActionListener(e -> agregarMerito());
    }

    // ─── Para Director: muestra el JInternalFrame dentro del JDesktopPane ───
    private void mostrarVistaEnDesktop(JDesktopPane escritorio) {
        if (vistaMerito.getParent() == null) {
            escritorio.add(vistaMerito);
        }
        Dimension desktopSize = escritorio.getSize();
        Dimension internalSize = vistaMerito.getPreferredSize();
        int x = (desktopSize.width - internalSize.width) / 2;
        int y = (desktopSize.height - internalSize.height) / 2;
        vistaMerito.setLocation(x, y);
        vistaMerito.setVisible(true);
        vistaMerito.toFront();
    }

    // ─── Para Maestros: monta el JInternalFrame en un JDialog propio ───
    private JDialog dialogo;

    private void mostrarVistaComoDialogo() {
        if (dialogo == null) {
            dialogo = new JDialog(vistaPrincipalMaestros, "Agregar Mérito", true);

            JDesktopPane desktop = new JDesktopPane();
            desktop.setPreferredSize(new Dimension(480, 430));
            desktop.add(vistaMerito);

            vistaMerito.setSize(vistaMerito.getPreferredSize());
            vistaMerito.setLocation(10, 10);
            vistaMerito.setVisible(true);

            dialogo.setContentPane(desktop);
            dialogo.pack();
            dialogo.setLocationRelativeTo(vistaPrincipalMaestros);
            dialogo.setResizable(false);
        }
        dialogo.setVisible(true);
    }

    private void cerrarDialogo() {
        if (dialogo != null) {
            dialogo.setVisible(false);
        }
    }

    // ─── Lógica de guardar mérito ───
    private void agregarMerito() {
        String nie = vistaMerito.txtNie.getText().trim();
        String nombre = vistaMerito.txtNombreCompleto.getText().trim();
        String descripcion = vistaMerito.ComboDescripcion.getSelectedItem().toString();
        String observacion = vistaMerito.txtObservaciones.getText().trim();

        if (nie.isEmpty() || nombre.isEmpty() || observacion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Complete todos los campos");
            return;
        }

        JOptionPane.showMessageDialog(null,
                "Mérito agregado correctamente\n\n"
                + "NIE: " + nie + "\n"
                + "Nombre: " + nombre + "\n"
                + "Descripción: " + descripcion + "\n"
                + "Observación: " + observacion);

        // Limpiar campos
        vistaMerito.txtNie.setText("");
        vistaMerito.txtNombreCompleto.setText("");
        vistaMerito.txtObservaciones.setText("");
        vistaMerito.ComboDescripcion.setSelectedIndex(0);

        // Cerrar si viene de maestros
        cerrarDialogo();
    }
}