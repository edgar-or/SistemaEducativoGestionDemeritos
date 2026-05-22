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
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
    public List<ModeloAlumno> listarEstudiantes() {
        List<ModeloAlumno> listar = new ArrayList<>();
        String sql = "SELECT nie, "
                + "TRIM(CONCAT(primer_nombre, ' ', COALESCE(segundo_nombre, ''))) AS nombre_completo, "
                + "TRIM(CONCAT(primer_apellido, ' ', COALESCE(segundo_apellido, ''))) AS apellidos_completos, "
                + "total_puntos, id_seccion, dui_encargado "
                + "FROM estudiante";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int nie = rs.getInt("nie");
                String nombre = rs.getString("nombre_completo");
                String apellidos = rs.getString("apellidos_completos");
                int totalPuntos = rs.getInt("total_puntos");
                int idSeccion = rs.getInt("id_seccion"); // Mapeado a idGrado en tu modelo
                String duiEncargado = rs.getString("dui_encargado");

                ModeloAlumno alumno = new ModeloAlumno(nie, nombre, apellidos, idSeccion, duiEncargado, totalPuntos);
                listar.add(alumno);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar estudiantes en AlumnoDAO: " + e.getMessage());
        }

        return listar;
    }
}
