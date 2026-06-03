package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ModeloDocente;
import DAO.conexion.Conexion;
import java.util.HashSet;

public class DocenteDAO {

    public void insertarDocente(ModeloDocente d) throws SQLException {
        String sql = "INSERT INTO personal_docente (dui_personal, primer_nombre, primer_apellido, departamento, municipio, caserio, calle, distrito, id_cargo_docente, id_usuario) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, d.getDuiDocente());
            ps.setString(2, d.getNombre());
            ps.setString(3, d.getApellido());
            ps.setString(4, d.getDepartamento());
            ps.setString(5, d.getMunicipio());
            ps.setString(6, d.getCaserio());
            ps.setString(7, d.getCalle());
            ps.setString(8, d.getDistrito());
            ps.setNull(9, java.sql.Types.INTEGER);
            ps.setNull(10, java.sql.Types.INTEGER);
            ps.executeUpdate();
        }
    }

    public List<ModeloDocente> listarDocentes() throws SQLException {
        List<ModeloDocente> lista = new ArrayList<>();
        String sql = "SELECT dui_personal, primer_nombre, primer_apellido, departamento, municipio, caserio, calle, distrito FROM personal_docente";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ModeloDocente docente = new ModeloDocente();

                docente.setDuiDocente(rs.getString("dui_personal"));
                docente.setNombre(rs.getString("primer_nombre"));
                docente.setApellido(rs.getString("primer_apellido"));
                docente.setDepartamento(rs.getString("departamento"));
                docente.setTelefonoDocente(23);
                docente.setCorreo("asdsa@gmail.com");
                docente.setMunicipio(rs.getString("municipio"));
                docente.setCaserio(rs.getString("caserio"));
                docente.setCalle(rs.getString("calle"));
                docente.setDistrito(rs.getString("distrito"));

                lista.add(docente);
            }
        }
        return lista;
    }

    public ModeloDocente buscarPorDui(String duiDocente) throws SQLException {
        String sql = "SELECT dui_personal, primer_nombre, primer_apellido, departamento, municipio, caserio, calle, distrito FROM personal_docente WHERE dui_personal = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, duiDocente);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ModeloDocente docente = new ModeloDocente();

                    docente.setDuiDocente(rs.getString("dui_personal"));
                    docente.setNombre(rs.getString("primer_nombre"));
                    docente.setApellido(rs.getString("primer_apellido"));
                    docente.setDepartamento(rs.getString("departamento"));
                    docente.setTelefonoDocente(23);
                    docente.setCorreo("asdsa@gmail.com");
                    docente.setMunicipio(rs.getString("municipio"));
                    docente.setCaserio(rs.getString("caserio"));
                    docente.setCalle(rs.getString("calle"));
                    docente.setDistrito(rs.getString("distrito"));

                    return docente;
                }
            }
        }
        return null;
    }

    public List<ModeloDocente> buscarPorNombre(String nombre) throws SQLException {
        List<ModeloDocente> lista = new ArrayList<>();
        String sql = "SELECT dui_personal, primer_nombre, primer_apellido, departamento, municipio, caserio, calle, distrito FROM personal_docente WHERE primer_nombre LIKE ? OR primer_apellido LIKE ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");
            ps.setString(2, "%" + nombre + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ModeloDocente docente = new ModeloDocente();

                    docente.setDuiDocente(rs.getString("dui_personal"));
                    docente.setNombre(rs.getString("primer_nombre"));
                    docente.setApellido(rs.getString("primer_apellido"));
                    docente.setDepartamento(rs.getString("departamento"));
                    docente.setTelefonoDocente(23);
                    docente.setCorreo("asdsa@gmail.com");
                    docente.setMunicipio(rs.getString("municipio"));
                    docente.setCaserio(rs.getString("caserio"));
                    docente.setCalle(rs.getString("calle"));
                    docente.setDistrito(rs.getString("distrito"));

                    lista.add(docente);
                }
            }
        }
        return lista;
    }

    public void modificarDocente(ModeloDocente d) throws SQLException {
        String sql = "UPDATE personal_docente SET primer_nombre=?, primer_apellido=?, departamento=?, municipio=?, caserio=?, calle=?, distrito=? WHERE dui_personal=?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, d.getNombre());
            ps.setString(2, d.getApellido());
            ps.setString(3, d.getDepartamento());
            ps.setString(4, d.getMunicipio());
            ps.setString(5, d.getCaserio());
            ps.setString(6, d.getCalle());
            ps.setString(7, d.getDistrito());
            ps.setString(8, d.getDuiDocente());

            ps.executeUpdate();
        }
    }

    public void eliminarDocente(String duiDocente) throws SQLException {
        String sql = "DELETE FROM personal_docente WHERE dui_personal = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, duiDocente);
            ps.executeUpdate();
        }
    }
}
