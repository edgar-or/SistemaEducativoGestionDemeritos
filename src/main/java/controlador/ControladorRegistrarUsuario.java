/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import DAO.RegistrarUsuarioContraseñaDao;
import java.awt.Dimension;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import vista.VistaAgregarUsuarioDocente;
import vista.VistaPrincipalDirector;

/**
 *
 * @author ayala
 */
public class ControladorRegistrarUsuario {

    VistaAgregarUsuarioDocente vista;

    VistaPrincipalDirector vistaPrincipal;

    RegistrarUsuarioContraseñaDao dao;
    int idDocente;

    public ControladorRegistrarUsuario(VistaPrincipalDirector vistaPrincipal, int idDocente) {
        this.vistaPrincipal = vistaPrincipal;
        this.dao = new RegistrarUsuarioContraseñaDao();
        this.idDocente = idDocente;
        this.vista = new VistaAgregarUsuarioDocente();

        vistaPrincipal.escritorio.add(vista);
        vista.setVisible(true);

        // Centrar dentro del escritorio
        Dimension desktopSize = vistaPrincipal.escritorio.getSize();
        Dimension vistaSize = vista.getSize();
        int x = Math.max(0, (desktopSize.width - vistaSize.width) / 2);
        int y = Math.max(0, (desktopSize.height - vistaSize.height) / 2);
        vista.setLocation(x, y);

        try {
            vista.setSelected(true); // lo ponés en primer plano
        } catch (java.beans.PropertyVetoException e) {
            e.printStackTrace();
        }

        eventos();

    }

    private void eventos() {

        vista.btnCerrar.addActionListener(e -> vista.dispose());

        vista.btnRegistrar.addActionListener(e -> {
            try {
                registrarUsuario();
            } catch (SQLException ex) {
                System.getLogger(ControladorRegistrarUsuario.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });

    }

    private void registrarUsuario() throws SQLException {
        String usu = vista.txtUsuario.getText().trim();
        String contra1 = vista.txtContraUno.getText();
        String contra2 = vista.txtContraDos.getText();

        if (!usu.isEmpty() && !contra1.isEmpty() && !contra2.isEmpty()) {
            if (contra1.equals(contra2)) {
                dao.insertarUsuario(idDocente, usu, contra2); // usas el id
                JOptionPane.showMessageDialog(null, "Usuario registrado correctamente.");
                vista.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Complete todos los campos.");
        }
    }
}
