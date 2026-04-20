
package com.mycompany.sistemaeducativogestiondemeritos;

import controlador.ControladorLogin;

import controlador.ControladorCentroEscolar;
import controlador.ControladorLogin;
import modelo.Login;
import vista.VistaLogin;


public class SistemaEducativoGestionDemeritos {

    public static void main(String[] args) {
        
        ControladorCentroEscolar ce = new ControladorCentroEscolar(); 
        ce.insertar();
        
        
        try {
            Login loginModelo = new Login(); 
               VistaLogin loginVista = new VistaLogin();
               
               ControladorLogin controladorLogin = new ControladorLogin(loginVista, loginModelo); 
               controladorLogin.iniciar();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
