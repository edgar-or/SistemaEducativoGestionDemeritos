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
        String sql = "INSERT INTO encargado_estudiante (nombre, apellido, dui, departamento, municipio, caserio, calle, distrito, canto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApelllido());
            ps.setInt(3, e.getDui());
            ps.setString(4, e.getDepartamento());
            ps.setString(5, e.getMunicipio());
            ps.setString(6, e.getCaserio());
            ps.setString(7, e.getCalle());
            ps.setString(8, e.getDistrito());
            ps.setString(9, e.getCanto());
            ps.executeUpdate();
        }
    }

    public List<ModeloEncardoAlumno> listarEncargados() throws SQLException {
        List<ModeloEncardoAlumno> lista = new ArrayList<>();
        String sql = "SELECT nombre, apellido, dui, departamento, municipio, caserio, calle, distrito, canto FROM encargado_estudiante";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ModeloEncardoAlumno e = new ModeloEncardoAlumno();
                e.setDui(rs.getInt("dui"));
                e.setNombre(rs.getString("nombre"));
                e.setApelllido(rs.getString("apellido"));
                e.setDepartamento(rs.getString("departamento"));
                e.setMunicipio(rs.getString("municipio"));
                e.setCaserio(rs.getString("caserio"));
                e.setCalle(rs.getString("calle"));
                e.setDistrito(rs.getString("distrito"));
                e.setCanto(rs.getString("canto"));
                lista.add(e);
            }
        }
        return lista;
    }

    public List<ModeloEncardoAlumno> buscarPorDui(int dui) throws SQLException {
        List<ModeloEncardoAlumno> lista = new ArrayList<>();
        String sql = "SELECT nombre, apellido, dui, departamento, municipio, caserio, calle, distrito, canto FROM encargado_estudiante WHERE dui = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, dui);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ModeloEncardoAlumno e = new ModeloEncardoAlumno();
                    e.setDui(rs.getInt("dui"));
                    e.setNombre(rs.getString("nombre"));
                    e.setApelllido(rs.getString("apellido"));
                    e.setDepartamento(rs.getString("departamento"));
                    e.setMunicipio(rs.getString("municipio"));
                    e.setCaserio(rs.getString("caserio"));
                    e.setCalle(rs.getString("calle"));
                    e.setDistrito(rs.getString("distrito"));
                    e.setCanto(rs.getString("canto"));
                    lista.add(e);
                }
            }
        }
        return lista;
    }

    public List<ModeloEncardoAlumno> buscarPorNombre(String nombre) throws SQLException {
        List<ModeloEncardoAlumno> lista = new ArrayList<>();
        String sql = "SELECT nombre, apellido, dui, departamento, municipio, caserio, calle, distrito, canto FROM encargado_estudiante WHERE nombre LIKE ? OR apellido LIKE ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ps.setString(2, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ModeloEncardoAlumno e = new ModeloEncardoAlumno();
                    e.setDui(rs.getInt("dui"));
                    e.setNombre(rs.getString("nombre"));
                    e.setApelllido(rs.getString("apellido"));
                    e.setDepartamento(rs.getString("departamento"));
                    e.setMunicipio(rs.getString("municipio"));
                    e.setCaserio(rs.getString("caserio"));
                    e.setCalle(rs.getString("calle"));
                    e.setDistrito(rs.getString("distrito"));
                    e.setCanto(rs.getString("canto"));
                    lista.add(e);
                }
            }
        }
        return lista;
    }

    public void modificarEncargado(ModeloEncardoAlumno e, int duiOriginal) throws SQLException {
        String sql = "UPDATE encargado_estudiante SET nombre=?, apellido=?, departamento=?, municipio=?, caserio=?, calle=?, distrito=?, canto=? WHERE dui=?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, e.getNombre());
            ps.setString(2, e.getApelllido());
            ps.setString(3, e.getDepartamento());
            ps.setString(4, e.getMunicipio());
            ps.setString(5, e.getCaserio());
            ps.setString(6, e.getCalle());
            ps.setString(7, e.getDistrito());
            ps.setString(8, e.getCanto());
            ps.setInt(9, duiOriginal);
            ps.executeUpdate();
        }
    }

    public void eliminarEncargado(int dui) throws SQLException {
        String sql = "DELETE FROM encargado_estudiante WHERE dui = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, dui);
            ps.executeUpdate();
        }
    }
}
