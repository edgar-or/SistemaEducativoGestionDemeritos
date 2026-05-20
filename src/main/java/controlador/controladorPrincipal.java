/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.JFrame;
import vista.VistaPrincipalDirector;

/**
 *
 * @author estud
 */
public class ControladorPrincipal {

    private VistaPrincipalDirector vista;
    private ControladorDocente controlDocente;
    private ControladorAlumno controlAlumno;
    private ControladorEncargado controlEncargado;
    private ControladorGrado controlGrado;
    private ControladorDemerito controlDemerito;
    private ControladorMerito conrolMerito;

    public ControladorPrincipal(VistaPrincipalDirector vista) {
        this.vista = vista;
        this.controlDocente = new ControladorDocente(vista);
        this.controlAlumno = new ControladorAlumno(vista);
        this.controlEncargado = new ControladorEncargado(vista);
        this.controlGrado = new ControladorGrado(vista);
        this.controlDemerito = new ControladorDemerito(vista);
        this.conrolMerito = new ControladorMerito(vista);
    }

    public void iniciar() {
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vista.setVisible(true);
    }
}
