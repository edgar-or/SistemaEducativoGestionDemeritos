/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.Dimension;
import vista.VerEncargado;
import vista.VerGrado;
import vista.VistaPrincipalDirector;

/**
 *
 * @author estud
 */
public class ControladorEncargado {

    private VistaPrincipalDirector vistaPrincipal;
    private VerEncargado verEncargado;

    public ControladorEncargado(VistaPrincipalDirector vistaPrincipal) {
        this.verEncargado = new VerEncargado();
        this.vistaPrincipal = vistaPrincipal;

        onEventos();
    }

    private void onEventos() {
        vistaPrincipal.menuEncargado.addActionListener(e -> mostrarVista());
        verEncargado.btnCerrar.addActionListener(e -> verEncargado.dispose());
    }

    public void mostrarVista() {

        verEncargado.setVisible(true);

        // 2️⃣ Centrar la vista
        Dimension desktopSize = vistaPrincipal.escritorio.getSize();
        Dimension internal = verEncargado.getSize();
        int x = (desktopSize.width - internal.width) / 2;
        int y = (desktopSize.height - internal.height) / 2;
        verEncargado.setLocation(x, y);
        vistaPrincipal.escritorio.remove(verEncargado);
        vistaPrincipal.escritorio.add(verEncargado);

        // 3️⃣ Mostrar y traer al frente
        verEncargado.toFront();
        //mostrarRutasTabla(base.getRutas());
        //formaAgregarDepartamentos();
        //activarEventoDepartamento();

    }
}
