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
<<<<<<< HEAD
=======
import javax.swing.table.DefaultTableModel;
>>>>>>> 449e241f3923a750b837c0e2d070ac9f78a4071b

/**
 *
 * @author ayala
 */
<<<<<<< HEAD
public class AlumnoDAO {

    // ✅ INSERTAR
=======
/*public class AlumnoDAO {
    
     // ✅ INSERTAR
>>>>>>> 449e241f3923a750b837c0e2d070ac9f78a4071b
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
<<<<<<< HEAD
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
=======
}*/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.ModeloAlumno;

public class AlumnoDAO {
 
    public List<ModeloAlumno> obtenerAlumnosPorSeccion(int idSeccion) {
        List<ModeloAlumno> lista = new ArrayList<>();
 
        String sql = "SELECT a.nie, " +
                     "CONCAT(a.primer_nombre, ' ', IFNULL(a.segundo_nombre,'')) AS nombre, " +
                     "CONCAT(a.primer_apellido, ' ', IFNULL(a.segundo_apellido,'')) AS apellidos, " +
                     "s.id_grado, " +
                     "a.dui_encargado, " +
                     "a.total_puntos " +
                     "FROM alumnos a " +
                     "INNER JOIN secciones s ON a.id_seccion = s.id_seccion " +
                     "WHERE a.id_seccion = ?";
 
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, idSeccion);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int    nie          = rs.getInt("nie");
                    String nombre       = rs.getString("nombre").trim();
                    String apellidos    = rs.getString("apellidos").trim();
                    int    idGrado      = rs.getInt("id_grado");
                    String duiEncargado = rs.getString("dui_encargado");
                    int    totalPuntos  = rs.getInt("total_puntos");
 
                    lista.add(new ModeloAlumno(nie, nombre, apellidos,
                                               idGrado, duiEncargado, totalPuntos));
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Error al obtener alumnos: " + e.getMessage());
        }
        return lista;
    }
 
    public List<ModeloAlumno> buscarAlumnos(int idSeccion, String nie,
                                             String nombre, String apellido) {
        List<ModeloAlumno> lista = new ArrayList<>();
        String sql = "SELECT a.nie, " +
                     "CONCAT(a.primer_nombre, ' ', IFNULL(a.segundo_nombre,'')) AS nombre, " +
                     "CONCAT(a.primer_apellido, ' ', IFNULL(a.segundo_apellido,'')) AS apellidos, " +
                     "s.id_grado, " +
                     "a.dui_encargado, " +
                     "a.total_puntos " +
                     "FROM alumnos a " +
                     "INNER JOIN secciones s ON a.id_seccion = s.id_seccion " +
                     "WHERE a.id_seccion = ? " +
                     "AND CAST(a.nie AS CHAR)    LIKE ? " +
                     "AND a.primer_nombre        LIKE ? " +
                     "AND a.primer_apellido      LIKE ?";
 
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, idSeccion);
            ps.setString(2, "%" + nie      + "%");
            ps.setString(3, "%" + nombre   + "%");
            ps.setString(4, "%" + apellido + "%");
 
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int    rNie         = rs.getInt("nie");
                    String rNombre      = rs.getString("nombre").trim();
                    String rApellidos   = rs.getString("apellidos").trim();
                    int    idGrado      = rs.getInt("id_grado");
                    String duiEncargado = rs.getString("dui_encargado");
                    int    totalPuntos  = rs.getInt("total_puntos");
 
                    lista.add(new ModeloAlumno(rNie, rNombre, rApellidos,
                                               idGrado, duiEncargado, totalPuntos));
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Error al buscar alumnos: " + e.getMessage());
        }
        return lista;
>>>>>>> 449e241f3923a750b837c0e2d070ac9f78a4071b
    }
}
 