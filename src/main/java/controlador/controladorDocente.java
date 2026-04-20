/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Dimension;
import vista.VerDocente;
import vista.VistaPrincipalDirector;

/**
 *
 * @author estud
 */
public class ControladorDocente {
    private VerDocente vistaDocente;
    private VistaPrincipalDirector vistaPrincipal;

    public ControladorDocente(VistaPrincipalDirector vistaPrincipal) {
        this.vistaDocente = new VerDocente();
        this.vistaPrincipal = vistaPrincipal;
        eventos();
    }
    private void eventos() {
        vistaPrincipal.menuDocente.addActionListener(e -> mostrarVista());
        vistaDocente.btnCerrar.addActionListener(e -> vistaDocente.dispose());
    }

    
    
    public void mostrarVista() {

        vistaDocente.setVisible(true);

        // 2️⃣ Centrar la vista
        Dimension desktopSize = vistaPrincipal.escritorio.getSize();
        Dimension internal = vistaDocente.getSize();
        int x = (desktopSize.width - internal.width) / 2;
        int y = (desktopSize.height - internal.height) / 2;
        vistaDocente.setLocation(x, y);
        vistaPrincipal.escritorio.remove(vistaDocente);
        vistaPrincipal.escritorio.add(vistaDocente);

        // 3️⃣ Mostrar y traer al frente
        vistaDocente.toFront();
        //mostrarRutasTabla(base.getRutas());
        //formaAgregarDepartamentos();
        //activarEventoDepartamento();

    }

}
