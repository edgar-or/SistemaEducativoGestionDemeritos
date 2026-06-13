/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ayala
 */
public class RegistrarUsuarioContraseñaDao {

    // UsuarioDAO.java
   public void insertarUsuario(int idDocente, String usuario, String contrasena) throws SQLException {
    String sqlVerificar = "SELECT COUNT(*) FROM usuario WHERE usuario = ?";
    String sqlInsertar  = "INSERT INTO usuario(usuario, contrasena) VALUES (?,?)";
    String sqlUpdate    = "UPDATE personal_docente SET id_usuario = ? WHERE id_personal = ?";

    try (Connection con = Conexion.getConexion()) {
        con.setAutoCommit(false);
        try {
            // 1. Verificar que el usuario no exista
            try (PreparedStatement psVerificar = con.prepareStatement(sqlVerificar)) {
                psVerificar.setString(1, usuario);
                ResultSet rs = psVerificar.executeQuery();
                rs.next();
                if (rs.getInt(1) > 0) {
                    throw new SQLException("El usuario '" + usuario + "' ya existe.");
                }
            }

            // 2. Insertar usuario
            int idUsuario;
            try (PreparedStatement ps = con.prepareStatement(sqlInsertar, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, usuario);
                ps.setString(2, contrasena);
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                rs.next();
                idUsuario = rs.getInt(1);
            }

            // 3. Actualizar foránea en personal_docente
            try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
                psUpdate.setInt(1, idUsuario);
                psUpdate.setInt(2, idDocente);
                psUpdate.executeUpdate();
            }

            con.commit();
        } catch (SQLException e) {
            con.rollback();
            throw e;
        }
    }
}

   
}
