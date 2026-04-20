
package controlador;

import javax.swing.JOptionPane;
import modelo.Login;
import vista.VistaLogin;
import vista.VistaPrincipalDirector;
import vista.VistaPrincipalMaestros;


/**
 *
 * @author estud
 */
public class ControladorLogin {
   
    
     private final VistaLogin loginVista;
    private final Login loginModelo;
    private VistaPrincipalDirector vista; 
    private ControladorPrincipal controladorPrincipal; 
    private VistaPrincipalMaestros vistaPrincipalMaestros;

    public ControladorLogin(VistaLogin vistaLogin, Login Login ) {
        this.loginVista = vistaLogin;
        this.loginModelo = Login;
        this.vista  = null; 
        this.vistaPrincipalMaestros= new VistaPrincipalMaestros();
        
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

        String tipo= loginModelo.validarCredenciales();
        if (tipo.equals("ADMIN")) {

            vista = new VistaPrincipalDirector(); 

           

            controladorPrincipal = new ControladorPrincipal(vista);
            controladorPrincipal.iniciar();

            // Registrar listener DESPUÉS de crear la vista
            vista.btnCerrarSesion.addActionListener(e -> cerrarSesion());

            cerrar(); 
        } else if (tipo.equals("USER")){
            VistaPrincipalMaestros visMaestros= new VistaPrincipalMaestros();
            ControladorPrinciplaMaestros controladorMaestros = new ControladorPrinciplaMaestros(visMaestros);
            controladorMaestros.iniciar();
            
            visMaestros.btnCerrarsesion.addActionListener(e->{
            visMaestros.dispose();
            iniciar();
            
            });
            cerrar();
           
        }else{
            mostrarError("Usuario o contraseña incorrectos");
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
