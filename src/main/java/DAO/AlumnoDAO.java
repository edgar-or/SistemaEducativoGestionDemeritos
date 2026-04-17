/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.ModeloAlumno;
import DAO.conexion.Conexion;

/**
 *
 * @author ayala
 */
public class AlumnoDAO {
    
     // ✅ INSERTAR
//    public void insertar(ModeloAlumno alumno) {
//        String sql = "INSERT INTO alumno (nie, nombre, apellidos, id_grado, dui_encargado, total_puntos) VALUES (?, ?, ?, ?, ?, ?)";
//
//        try (Connection con = Conexion.getConexion();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setInt(1, alumno.getNie());
//            ps.setString(2, alumno.getNombre());
//            ps.setString(3, alumno.getApelliddos());
//            ps.setInt(4, alumno.getIdGrado());
//            ps.setInt(5, alumno.getDuiEncargado());
//            ps.setInt(6, alumno.getTotalPuntos());
//
//            ps.executeUpdate();
//
//        } catch (SQLException e) {
//            System.out.println("Error insertar: " + e.getMessage());
//        }
//    }
//    
}
