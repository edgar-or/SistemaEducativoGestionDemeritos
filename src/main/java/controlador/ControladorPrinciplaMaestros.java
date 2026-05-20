/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.AñoSeccionDao;
import dto.SeccionGradoDto;
import java.util.List;
import javax.swing.JFrame;
import modelo.Login;
import vista.VistaAgregarDemerito;
import vista.VistaAgregarMerito;
import vista.VistaLogin;
import vista.VistaPrincipalMaestros;
import vista.VistaPrincipalMaestros;
import vista.VistaVerEstado;

/**
 *
 * @author renec
 */
public class ControladorPrinciplaMaestros {

    private VistaPrincipalMaestros visPrincipalMaaestros;
    private ControladorMerito controladorMerito;

    public ControladorPrinciplaMaestros(VistaPrincipalMaestros visPrincipalMaaestros) {

        this.visPrincipalMaaestros = visPrincipalMaaestros;
<<<<<<< HEAD
        
        
=======


        eventos();
        this.controladorMerito = new ControladorMerito(visPrincipalMaaestros);
>>>>>>> 57594b350bf720ac7a17dbe289a949182d0110cb
    }

    public void iniciar() {

        visPrincipalMaaestros.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        visPrincipalMaaestros.setExtendedState(JFrame.MAXIMIZED_BOTH);
        visPrincipalMaaestros.setVisible(true);
<<<<<<< HEAD
        
        llenarComboSeccion(); 
=======

>>>>>>> 57594b350bf720ac7a17dbe289a949182d0110cb
    }
    
    public void llenarComboSeccion(){
        AñoSeccionDao dao = new AñoSeccionDao();
List<SeccionGradoDto> lista = dao.listarSeccionGrado();

<<<<<<< HEAD
for (SeccionGradoDto obj : lista) {
    visPrincipalMaaestros.comboSeccion.addItem(obj.getSeccion().getSeccion());
    visPrincipalMaaestros.comboGrado.addItem(obj.getGrado().getGrado());
}
    }
    
    
    
    
=======
    private void eventos() {

        visPrincipalMaaestros.btnAgrgarDemerito.addActionListener(e -> {

            VistaAgregarDemerito vista = new VistaAgregarDemerito();
            new ControladorAgregarDemerito(vista);
        });
        visPrincipalMaaestros.btnAgregarMerito.addActionListener(e -> {

            VistaAgregarMerito vista = new VistaAgregarMerito();
            new ControladorAgregarMerito(vista);
        });

        visPrincipalMaaestros.btnVerEstados.addActionListener(e -> {

            VistaVerEstado vista = new VistaVerEstado();
            new ControladorVerEstado(vista);

        });
        visPrincipalMaaestros.btnCerrarsesion.addActionListener(e -> {
            // crear vista y modelo
            VistaLogin login = new VistaLogin();
            Login modelo = new Login();

            // crear controlador correctamente
            new ControladorLogin(login, modelo);

            login.setLocationRelativeTo(null);
            login.setVisible(true);

            // cerrar la ventana actual
            visPrincipalMaaestros.dispose();
        });

    }


>>>>>>> 57594b350bf720ac7a17dbe289a949182d0110cb
}
