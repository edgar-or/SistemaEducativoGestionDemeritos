package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ModeloEncardoAlumno;
import DAO.conexion.Conexion;

public class EncargadoDAO {

    public void insertarEncargado(ModeloEncardoAlumno e) throws SQLException {
        String sql = "INSERT INTO encargado_estudiante (nombre, apellido, dui, telefono, departamento, municipio, caserio, calle, distrito) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApelllido());
            ps.setInt(3, e.getDui());
            ps.setInt(4, e.getTelefono());
            ps.setString(5, e.getDepartamento());
            ps.setString(6, e.getMunicipio());
            ps.setString(7, e.getCaserio());
            ps.setString(8, e.getCalle());
            ps.setString(9, e.getDistrito());
            ps.executeUpdate();
        }
    }

    public List<ModeloEncardoAlumno> listarEncargados() throws SQLException {
        List<ModeloEncardoAlumno> lista = new ArrayList<>();
        String sql = "SELECT nombre, apellido, dui, telefono, departamento, municipio, caserio, calle, distrito FROM encargado_estudiante";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next())
                lista.add(new ModeloEncardoAlumno(rs.getString("nombre"), rs.getString("apellido"),
                    rs.getInt("dui"), rs.getInt("telefono"), rs.getString("departamento"),
                    rs.getString("municipio"), rs.getString("caserio"),
                    rs.getString("calle"), rs.getString("distrito")));
        }
        return lista;
    }

    public List<ModeloEncardoAlumno> buscarPorDui(int dui) throws SQLException {
        List<ModeloEncardoAlumno> lista = new ArrayList<>();
        String sql = "SELECT nombre, apellido, dui, telefono, departamento, municipio, caserio, calle, distrito FROM encargado_estudiante WHERE dui = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, dui);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    lista.add(new ModeloEncardoAlumno(rs.getString("nombre"), rs.getString("apellido"),
                        rs.getInt("dui"), rs.getInt("telefono"), rs.getString("departamento"),
                        rs.getString("municipio"), rs.getString("caserio"),
                        rs.getString("calle"), rs.getString("distrito")));
            }
        }
        return lista;
    }

    public List<ModeloEncardoAlumno> buscarPorNombre(String nombre) throws SQLException {
        List<ModeloEncardoAlumno> lista = new ArrayList<>();
        String sql = "SELECT nombre, apellido, dui, telefono, departamento, municipio, caserio, calle, distrito FROM encargado_estudiante WHERE nombre LIKE ? OR apellido LIKE ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ps.setString(2, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    lista.add(new ModeloEncardoAlumno(rs.getString("nombre"), rs.getString("apellido"),
                        rs.getInt("dui"), rs.getInt("telefono"), rs.getString("departamento"),
                        rs.getString("municipio"), rs.getString("caserio"),
                        rs.getString("calle"), rs.getString("distrito")));
            }
        }
        return lista;
    }

    public void modificarEncargado(ModeloEncardoAlumno e, int duiOriginal) throws SQLException {
        String sql = "UPDATE encargado_estudiante SET nombre=?, apellido=?, telefono=?, departamento=?, municipio=?, caserio=?, calle=?, distrito=? WHERE dui=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApelllido());
            ps.setInt(3, e.getTelefono());
            ps.setString(4, e.getDepartamento());
            ps.setString(5, e.getMunicipio());
            ps.setString(6, e.getCaserio());
            ps.setString(7, e.getCalle());
            ps.setString(8, e.getDistrito());
            ps.setInt(9, duiOriginal);
            ps.executeUpdate();
        }
    }

    public void eliminarEncargado(int dui) throws SQLException {
        String sql = "DELETE FROM encargado_alumno WHERE dui = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, dui);
            ps.executeUpdate();
        }
    }
}