/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author ayala
 */
public class AsignarEncargadoAlumnoDao {
    
    public boolean asignarEncargadoAAlumno(int idEstudiante, int idEncargado) {
    String sql = "UPDATE estudiante SET id_encargado = ? WHERE id_estudiante = ?";
    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idEncargado);
        ps.setInt(2, idEstudiante);

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        System.err.println("Error al asignar encargado: " + e.getMessage());
        return false;
    }
}
    
}
