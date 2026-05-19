package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ModeloDocente;
import DAO.conexion.Conexion;

public class DocenteDAO {

    public void insertarDocente(ModeloDocente d) throws SQLException {
        String sql = "INSERT INTO docente (nombre, apellido, telefono_docente, correo, departamento, municipio, caserio, calle, distrito) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getApellido());
            ps.setInt(3, d.getTelefonoDocente());
            ps.setString(4, d.getCorreo());
            ps.setString(5, d.getDepartamento());
            ps.setString(6, d.getMunicipio());
            ps.setString(7, d.getCaserio());
            ps.setString(8, d.getCalle());
            ps.setString(9, d.getDistrito());
            ps.executeUpdate();
        }
    }

    public List<ModeloDocente> listarDocentes() throws SQLException {
        List<ModeloDocente> lista = new ArrayList<>();
        String sql = "SELECT id_docente, nombre, apellido, telefono_docente, correo, departamento, municipio, caserio, calle, distrito FROM docente";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next())
                lista.add(new ModeloDocente(rs.getString("nombre"), rs.getString("apellido"),
                    rs.getInt("id_docente"), rs.getInt("telefono_docente"), rs.getString("correo"),
                    rs.getString("departamento"), rs.getString("municipio"),
                    rs.getString("caserio"), rs.getString("calle"), rs.getString("distrito")));
        }
        return lista;
    }

    public List<ModeloDocente> buscarPorId(int idDocente) throws SQLException {
        List<ModeloDocente> lista = new ArrayList<>();
        String sql = "SELECT id_docente, nombre, apellido, telefono_docente, correo, departamento, municipio, caserio, calle, distrito FROM docente WHERE id_docente = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idDocente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    lista.add(new ModeloDocente(rs.getString("nombre"), rs.getString("apellido"),
                        rs.getInt("id_docente"), rs.getInt("telefono_docente"), rs.getString("correo"),
                        rs.getString("departamento"), rs.getString("municipio"),
                        rs.getString("caserio"), rs.getString("calle"), rs.getString("distrito")));
            }
        }
        return lista;
    }

    public List<ModeloDocente> buscarPorNombre(String nombre) throws SQLException {
        List<ModeloDocente> lista = new ArrayList<>();
        String sql = "SELECT id_docente, nombre, apellido, telefono_docente, correo, departamento, municipio, caserio, calle, distrito FROM docente WHERE nombre LIKE ? OR apellido LIKE ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ps.setString(2, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    lista.add(new ModeloDocente(rs.getString("nombre"), rs.getString("apellido"),
                        rs.getInt("id_docente"), rs.getInt("telefono_docente"), rs.getString("correo"),
                        rs.getString("departamento"), rs.getString("municipio"),
                        rs.getString("caserio"), rs.getString("calle"), rs.getString("distrito")));
            }
        }
        return lista;
    }

    public void modificarDocente(ModeloDocente d) throws SQLException {
        String sql = "UPDATE docente SET nombre=?, apellido=?, telefono_docente=?, correo=?, departamento=?, municipio=?, caserio=?, calle=?, distrito=? WHERE id_docente=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getApellido());
            ps.setInt(3, d.getTelefonoDocente());
            ps.setString(4, d.getCorreo());
            ps.setString(5, d.getDepartamento());
            ps.setString(6, d.getMunicipio());
            ps.setString(7, d.getCaserio());
            ps.setString(8, d.getCalle());
            ps.setString(9, d.getDistrito());
            ps.setInt(10, d.getIdDocente());
            ps.executeUpdate();
        }
    }

    public void eliminarDocente(int idDocente) throws SQLException {
        String sql = "DELETE FROM docente WHERE id_docente = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idDocente);
            ps.executeUpdate();
        }
    }
}