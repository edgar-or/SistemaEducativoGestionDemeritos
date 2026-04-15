/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javax.swing.JOptionPane;
import modelo.Login;
import vista.vistaLogin;
import vista.vistaPrincipalDirector;

/**
 *
 * @author estud
 */
public class controladorLogin {
   
    
     private final vistaLogin loginVista;
    private final Login loginModelo;
    vistaPrincipalDirector vista; 
    controladorPrincipal controladorPrincipal; 
    
    

    public controladorLogin(vistaLogin vistaLogin, Login Login) {
        this.loginVista = vistaLogin;
        this.loginModelo = Login;
        this.vista  = null; 
        
        //Para boton Enter
        this.loginVista.getRootPane().setDefaultButton(this.loginVista.btnLogin);

        this.loginVista.btnLogin.addActionListener(e -> login() );
 
    }
    
     private void login() {

        String usuario = loginVista.txtUsuario.getText();
        String password = new String(loginVista.txtContraseña.getText());

        if (usuario.isEmpty() || password.isEmpty()) {
            mostrarError("El usuario y la contraseña no pueden estar vacíos.");
            return; 
        }

        loginModelo.setUsuario(usuario);
        loginModelo.setPassword(password);

        boolean esValido = loginModelo.validarCredenciales();

        if (esValido) {

            vista = new vistaPrincipalDirector(); 

           

            controladorPrincipal = new controladorPrincipal(vista);
            controladorPrincipal.iniciar();

            // Registrar listener DESPUÉS de crear la vista
            vista.btnCerrarSesion.addActionListener(e -> cerrarSesion());

            cerrar(); 
        } else {
            mostrarError("Usuario o contraseña incorrectos.");
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(loginVista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void cerrar() {
        loginVista.dispose();
    }
    
    public void iniciar(){
        
        loginVista.setLocationRelativeTo(null);
        loginVista.getRootPane().setDefaultButton(loginVista.btnLogin);
        loginVista.setVisible(true);
    }
    
     private void cerrarSesion(){
        vista.dispose();
        vista = null; 
        iniciar();
        
        loginVista.txtUsuario.setText("");
       loginVista.txtContraseña.setText("");
       loginVista.txtUsuario.requestFocus();
    }
    
}
