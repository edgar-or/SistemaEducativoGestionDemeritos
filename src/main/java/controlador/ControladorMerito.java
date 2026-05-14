/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Dimension;
import vista.VerEncargado;
import vista.VistaAgregarMerito;
import vista.VistaPrincipalDirector;

/**
 *
 * @author estud
 */
public class ControladorMerito {

    private VistaPrincipalDirector vistaPrincipal;
    private VistaAgregarMerito vistaMerito;

    public ControladorMerito(VistaPrincipalDirector vistaPrincipal) {
        this.vistaMerito = new VistaAgregarMerito();
        this.vistaPrincipal = vistaPrincipal;

        onEventos();
    }

    private void onEventos() {
        vistaPrincipal.menuAgregarMerito.addActionListener(e -> mostrarVista());
        vistaMerito.btnCerrar.addActionListener(e -> vistaMerito.dispose());
    }

    public void mostrarVista() {

        vistaMerito.setVisible(true);

        // 2️⃣ Centrar la vista
        Dimension desktopSize = vistaPrincipal.escritorio.getSize();
        Dimension internal = vistaMerito.getSize();
        int x = (desktopSize.width - internal.width) / 2;
        int y = (desktopSize.height - internal.height) / 2;
        vistaMerito.setLocation(x, y);
        vistaPrincipal.escritorio.remove(vistaMerito);
        vistaPrincipal.escritorio.add(vistaMerito);

        // 3️⃣ Mostrar y traer al frente
        vistaMerito.toFront();
        //mostrarRutasTabla(base.getRutas());
        //formaAgregarDepartamentos();
        //activarEventoDepartamento();

    }
}
