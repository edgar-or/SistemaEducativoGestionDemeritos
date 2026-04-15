
package com.mycompany.sistemaeducativogestiondemeritos;

import controlador.controladorLogin;
import modelo.Login;
import vista.vistaLogin;


public class SistemaEducativoGestionDemeritos {

    public static void main(String[] args) {
        try {
            Login loginModelo = new Login(); 
               vistaLogin loginVista = new vistaLogin();
               
               controladorLogin controladorLogin = new controladorLogin(loginVista, loginModelo); 
               controladorLogin.iniciar();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
