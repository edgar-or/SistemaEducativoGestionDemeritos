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
        String sql = "INSERT INTO personal_docente (dui_personal, primer_nombre, primer_apellido, departamento, municipio, caserio, calle, distrito, id_cargo_docente, id_usuario) "
                   + "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "121212");
            ps.setString(2, d.getNombre());
            ps.setString(3, d.getApellido());

            ps.setString(4, d.getDepartamento());
            ps.setString(5, d.getMunicipio());
            ps.setString(6, d.getCaserio());
            ps.setString(7, d.getCalle());
            ps.setString(8, d.getDistrito());
            ps.setString(9, null);
                        ps.setString(10, null);

            ps.executeUpdate();
        }
    }

    public List<ModeloDocente> listarDocentes() throws SQLException {

    List<ModeloDocente> lista = new ArrayList<>();

    String sql = "SELECT dui_personal, primer_nombre, primer_apellido, departamento, municipio, caserio, calle, distrito FROM personal_docente";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            ModeloDocente docente = new ModeloDocente();

            docente.setIdDocente(rs.getString("dui_personal"));
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

    public List<ModeloDocente> buscarPorId(int idDocente) throws SQLException {
        List<ModeloDocente> lista = new ArrayList<>();
        String sql = "SELECT dui_personal, nombre, apellido, telefono_docente, correo, departamento, municipio, caserio, calle, distrito FROM personal_docente WHERE id_docente = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idDocente);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    lista.add(new ModeloDocente(rs.getString("nombre"), rs.getString("apellido"),
                        rs.getString("id_docente"), rs.getInt("telefono_docente"), rs.getString("correo"),
                        rs.getString("departamento"), rs.getString("municipio"),
                        rs.getString("caserio"), rs.getString("calle"), rs.getString("distrito")));
            }
        }
        return lista;
    }

    public List<ModeloDocente> buscarPorNombre(String nombre) throws SQLException {
        List<ModeloDocente> lista = new ArrayList<>();
        String sql = "SELECT id_docente, nombre, apellido, departamento, municipio, caserio, calle, distrito FROM personal_docente WHERE nombre LIKE ? OR apellido LIKE ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ps.setString(2, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next())
                    lista.add(new ModeloDocente(rs.getString("nombre"), rs.getString("apellido"),
                        rs.getString("id_docente"), rs.getInt("telefono_docente"), rs.getString("correo"),
                        rs.getString("departamento"), rs.getString("municipio"),
                        rs.getString("caserio"), rs.getString("calle"), rs.getString("distrito")));
            }
        }
        return lista;
    }

    public void modificarDocente(ModeloDocente d) throws SQLException {
        String sql = "UPDATE personal_docente SET nombre=?, apellido=?, telefono_docente=?, correo=?, departamento=?, municipio=?, caserio=?, calle=?, distrito=? WHERE id_docente=?";
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
            ps.setString(10, d.getIdDocente());
            ps.executeUpdate();
        }
    }

    public void eliminarDocente(int idDocente) throws SQLException {
        String sql = "DELETE FROM personal_docente WHERE id_docente = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idDocente);
            ps.executeUpdate();
        }
    }
}