/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Dimension;
import vista.VerEncargado;
import vista.VistaAgregarDemerito;
import vista.VistaPrincipalDirector;

/**
 *
 * @author estud
 */
public class ControladorDemerito {
     private VistaPrincipalDirector vistaPrincipal;
    private VistaAgregarDemerito vistaDemerito;

    public ControladorDemerito(VistaPrincipalDirector vistaPrincipal) {
        this.vistaDemerito = new VistaAgregarDemerito();
        this.vistaPrincipal = vistaPrincipal;

        onEventos();
    }

    private void onEventos() {
        vistaPrincipal.menuAgregarDemerito.addActionListener(e -> mostrarVista());
        vistaDemerito.btnCerrar.addActionListener(e -> vistaDemerito.dispose());
    }

    public void mostrarVista() {

        vistaDemerito.setVisible(true);

        // 2️⃣ Centrar la vista
        Dimension desktopSize = vistaPrincipal.escritorio.getSize();
        Dimension internal = vistaDemerito.getSize();
        int x = (desktopSize.width - internal.width) / 2;
        int y = (desktopSize.height - internal.height) / 2;
        vistaDemerito.setLocation(x, y);
        vistaPrincipal.escritorio.remove(vistaDemerito);
        vistaPrincipal.escritorio.add(vistaDemerito);

        // 3️⃣ Mostrar y traer al frente
        vistaDemerito.toFront();
        //mostrarRutasTabla(base.getRutas());
        //formaAgregarDepartamentos();
        //activarEventoDepartamento();

    }
}
