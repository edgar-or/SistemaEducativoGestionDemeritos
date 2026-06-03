package controlador;

import DAO.UsuarioDao;
import controlador.ControladorPrincipal;
import dto.LoginResultadoDto;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import modelo.Login;
import modelo.ModeloCargoDocente;
import modelo.ModeloDocente;
import sesion.Sesion;
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
    private UsuarioDao usuarioDao;
    private LoginResultadoDto loginResultDto;

    public ControladorLogin(VistaLogin vistaLogin, Login Login) {
        this.loginVista = vistaLogin;
        this.loginModelo = Login;
        this.vista = null;
        this.vistaPrincipalMaestros = new VistaPrincipalMaestros();

        //Para boton Enter
        this.loginVista.getRootPane().setDefaultButton(this.loginVista.btnLogin);

        this.loginVista.btnLogin.addActionListener(e -> {
            try {
                login();
            } catch (SQLException ex) {
                System.getLogger(ControladorLogin.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });

    }

    private void login() throws SQLException {

        String usuario = loginVista.txtUsuario.getText().trim();

        String password
                = new String(loginVista.txtContrasenia.getPassword());

        // VALIDAR CAMPOS VACIOS
        if (usuario.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    loginVista,
                    "El usuario y la contraseña no pueden estar vacíos.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        UsuarioDao dao = new UsuarioDao();

        LoginResultadoDto res = dao.validar(usuario, password);

        // VALIDAR LOGIN
        if (res == null) {

            JOptionPane.showMessageDialog(
                    loginVista,
                    "Usuario o contraseña incorrectos.",
                    "Inicio de sesión",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        ModeloCargoDocente cargo = res.getCargoDocente();

        ModeloDocente docente = res.getModeloDocente();

        // VALIDAR DOCENTE
        if (docente == null || cargo == null) {

            JOptionPane.showMessageDialog(
                    loginVista,
                    "Error al obtener datos del usuario.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // GUARDAR SESION
//    Sesion.setDuiPersonal(docente.getIdDocente());
        Sesion.setDuiPersonal(docente.getDuiDocente());

        // ABRIR VENTANA SEGUN CARGO
        if (cargo.getCargo().equalsIgnoreCase("Director")) {

            loginVista.dispose();

            VistaPrincipalDirector vistaDirector
                    = new VistaPrincipalDirector();

            ControladorPrincipal ctrlDirector
                    = new ControladorPrincipal(vistaDirector);

            ctrlDirector.iniciar();

        } else if (cargo.getCargo().equalsIgnoreCase("Docente")) {

            loginVista.dispose();

            VistaPrincipalMaestros vistaMaestros
                    = new VistaPrincipalMaestros();

            ControladorPrincipalMaestros ctrlMaestros
                    = new ControladorPrincipalMaestros(vistaMaestros);

            ctrlMaestros.iniciar();
        }
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(loginVista, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void cerrar() {
        loginVista.dispose();
    }

    public void iniciar() {

        loginVista.setLocationRelativeTo(null);
        loginVista.getRootPane().setDefaultButton(loginVista.btnLogin);
        loginVista.setVisible(true);
    }

    private void cerrarSesion() {
        vista.dispose();
        vista = null;
        iniciar();

        loginVista.txtUsuario.setText("");
        loginVista.txtContrasenia.setText("");
        loginVista.txtUsuario.requestFocus();
    }

}
