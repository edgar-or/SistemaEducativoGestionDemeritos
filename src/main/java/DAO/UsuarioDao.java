/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.conexion.Conexion;
import dto.LoginResultadoDto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Login;
import modelo.ModeloCargoDocente;
import modelo.ModeloDocente;

/**
 *
 * @author ayala
 *
 */
public class UsuarioDao {

    public LoginResultadoDto validar(String usuario, String password) {
        Login u = null;
        LoginResultadoDto resultado = null;

        String consulta = "SELECT pd.id_personal, pd.dui_personal, pd.primer_nombre, pd.segundo_nombre, pd.primer_apellido, pd.segundo_apellido, pd.departamento, pd.municipio, pd.distrito, pd.caserio, pd.calle, pd.num_casa, cp.id_cargo_personal, cp.cargo_personal, u.id_usuario, u.usuario, u.contrasena FROM personal_docente pd INNER JOIN cargo_personal cp ON pd.id_cargo_personal = cp.id_cargo_personal INNER JOIN usuario u ON pd.id_usuario = u.id_usuario WHERE u.usuario = ? AND u.contrasena = ?;";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(consulta)) {

            ps.setString(1, usuario);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    resultado = new LoginResultadoDto();

                    ModeloDocente docente = new ModeloDocente();

                    docente.setIdPersonal(rs.getInt("id_personal")); 
                    docente.setDuiDocente(rs.getString("dui_personal"));

                    docente.setNombre(rs.getString("primer_nombre"));
                    docente.setApellido(rs.getString("primer_apellido"));
                    docente.setDepartamento(rs.getString("departamento"));
                    docente.setMunicipio(rs.getString("municipio"));
                    docente.setDistrito(rs.getString("distrito"));
                    docente.setCaserio(rs.getString("caserio"));
                    docente.setCalle(rs.getString("calle"));
                    docente.setNumeroCasa(rs.getInt("num_casa"));

                    u = new Login();
                    u.setUsuario(rs.getString("usuario"));
                    u.setPassword(rs.getString("contrasena"));

                    ModeloCargoDocente cargo = new ModeloCargoDocente();
                    cargo.setIdCargo(rs.getInt("id_cargo_personal"));
                    cargo.setCargo(rs.getString("cargo_personal"));

                    resultado.setUsuario(u);
                    resultado.setCargoDocente(cargo);
                    resultado.setModeloDocente(docente);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error en la autenticación: " + e.getMessage());
            e.printStackTrace();
        }

        return resultado;
    }
}
//public class UsuarioDao {
//
//    public LoginResultadoDto validar(String usuario, String password) {
//        Login u = null;
//        LoginResultadoDto resultado = null;
//
//        String consulta = "SELECT pd.dui_personal, pd.primer_nombre, pd.segundo_nombre, "
//                + "pd.primer_apellido, pd.segundo_apellido, pd.departamento, pd.municipio, "
//                + "pd.distrito, pd.caserio, pd.calle, pd.num_casa, pd.id_cargo_docente, pd.id_usuario, "
//                + "cp.id_cargo_personal, cp.cargo_personal, u.id_usuaio AS usuario_id, u.usuario, u.contrasena "
//                + "FROM personal_docente pd "
//                + "INNER JOIN cargo_personal cp ON pd.id_cargo_docente = cp.id_cargo_personal "
//                + "INNER JOIN usuario u ON pd.id_usuario = u.id_usuaio "
//                + "WHERE u.usuario = ? AND u.contrasena = ?;";
//
//        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(consulta)) {
//
//            ps.setString(1, usuario);
//            ps.setString(2, password);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    resultado = new LoginResultadoDto();
//
//                    ModeloDocente docente = new ModeloDocente();
//                    docente.setDuiDocente(rs.getInt("dui_personal"));
//                    docente.setNombre(rs.getString("primer_nombre"));
//                    docente.setApellido(rs.getString("primer_apellido"));
//                    docente.setDepartamento(rs.getString("departamento"));
//                    docente.setMunicipio(rs.getString("municipio"));
//                    docente.setDistrito(rs.getString("distrito"));
//                    docente.setCaserio(rs.getString("caserio"));
//                    docente.setCalle(rs.getString("calle"));
//                    docente.setNumeroCasa(rs.getInt("num_casa"));
//
//                    u = new Login();
//                    u.setUsuario(rs.getString("usuario"));
//                    u.setPassword(rs.getString("contrasena"));
//
//                    ModeloCargoDocente cargo = new ModeloCargoDocente();
//                    cargo.setIdCargo(rs.getInt("id_cargo_personal"));
//                    cargo.setCargo(rs.getString("cargo_personal"));
//
//                    resultado.setUsuario(u);
//                    resultado.setCargoDocente(cargo);
//                    resultado.setModeloDocente(docente);
//                }
//            }
//
//        } catch (SQLException e) {
//            System.err.println("Error en la autenticación: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return resultado;
//    }
//}
