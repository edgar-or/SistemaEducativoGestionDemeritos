package com.mycompany.sistemaeducativogestiondemeritos;

import controlador.ControladorLogin;

import controlador.ControladorLogin;
import modelo.Login;
import vista.VistaLogin;

public class SistemaEducativoGestionDemeritos {

    public static void main(String[] args) {

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
