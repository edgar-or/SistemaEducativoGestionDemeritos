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
        String sql = "INSERT INTO personal_docente "
                + "(dui_personal, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, "
                + "departamento, municipio, distrito, caserio, calle, id_cargo_docente, id_usuario) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, NULL)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, d.getDuiDocente());
            ps.setString(2, d.getNombre());
            ps.setString(3, d.getSegundoNombre());   // puede ser null
            ps.setString(4, d.getApellido());
            ps.setString(5, d.getSegundoApellido()); // puede ser null
            ps.setString(6, d.getDepartamento());
            ps.setString(7, d.getMunicipio());
            ps.setString(8, d.getDistrito());
            ps.setString(9, d.getCaserio());
            ps.setString(10, d.getCalle());
            ps.executeUpdate();
        }

        // Si viene teléfono, lo insertamos en la tabla separada
        if (d.getTelefonoDocente() != null && !d.getTelefonoDocente().isEmpty()) {
            insertarTelefono(d.getDuiDocente(), d.getTelefonoDocente());
        }
    }

 
    public void insertarTelefono(String dui, String telefono) throws SQLException {
        String sql = "INSERT INTO tel_personal_docente (telefono, dui_personal_docente) VALUES (?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, telefono);
            ps.setString(2, dui);
            ps.executeUpdate();
        }
    }

    public List<ModeloDocente> listarDocentes() throws SQLException {
        List<ModeloDocente> lista = new ArrayList<>();
        String sql = "SELECT pd.dui_personal, pd.primer_nombre, pd.segundo_nombre, "
                + "pd.primer_apellido, pd.segundo_apellido, pd.departamento, pd.municipio, "
                + "pd.distrito, pd.caserio, pd.calle, "
                + "(SELECT t.telefono FROM tel_personal_docente t "
                + " WHERE t.dui_personal_docente = pd.dui_personal LIMIT 1) AS telefono "
                + "FROM personal_docente pd";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

 
    public ModeloDocente buscarPorDui(String dui) throws SQLException {
        String sql = "SELECT pd.dui_personal, pd.primer_nombre, pd.segundo_nombre, "
                + "pd.primer_apellido, pd.segundo_apellido, pd.departamento, pd.municipio, "
                + "pd.distrito, pd.caserio, pd.calle, "
                + "(SELECT t.telefono FROM tel_personal_docente t "
                + " WHERE t.dui_personal_docente = pd.dui_personal LIMIT 1) AS telefono "
                + "FROM personal_docente pd WHERE pd.dui_personal = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dui);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapear(rs);
                }
            }
        }
        return null;
    }


    public List<ModeloDocente> buscarPorNombre(String nombre) throws SQLException {
        List<ModeloDocente> lista = new ArrayList<>();
        String sql = "SELECT pd.dui_personal, pd.primer_nombre, pd.segundo_nombre, "
                + "pd.primer_apellido, pd.segundo_apellido, pd.departamento, pd.municipio, "
                + "pd.distrito, pd.caserio, pd.calle, "
                + "(SELECT t.telefono FROM tel_personal_docente t "
                + " WHERE t.dui_personal_docente = pd.dui_personal LIMIT 1) AS telefono "
                + "FROM personal_docente pd "
                + "WHERE pd.primer_nombre LIKE ? OR pd.primer_apellido LIKE ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            ps.setString(2, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }


    public void modificarDocente(ModeloDocente d) throws SQLException {
        String sql = "UPDATE personal_docente SET primer_nombre=?, segundo_nombre=?, "
                + "primer_apellido=?, segundo_apellido=?, departamento=?, municipio=?, "
                + "distrito=?, caserio=?, calle=? WHERE dui_personal=?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, d.getNombre());
            ps.setString(2, d.getSegundoNombre());
            ps.setString(3, d.getApellido());
            ps.setString(4, d.getSegundoApellido());
            ps.setString(5, d.getDepartamento());
            ps.setString(6, d.getMunicipio());
            ps.setString(7, d.getDistrito());
            ps.setString(8, d.getCaserio());
            ps.setString(9, d.getCalle());
            ps.setString(10, d.getDuiDocente());
            ps.executeUpdate();
        }

        // Actualizar teléfono: borrar el anterior e insertar el nuevo
        if (d.getTelefonoDocente() != null && !d.getTelefonoDocente().isEmpty()) {
            eliminarTelefonos(d.getDuiDocente());
            insertarTelefono(d.getDuiDocente(), d.getTelefonoDocente());
        }
    }


    public void eliminarDocente(String dui) throws SQLException {
        // Los teléfonos se eliminan solos por el ON DELETE CASCADE de la FK
        String sql = "DELETE FROM personal_docente WHERE dui_personal = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dui);
            ps.executeUpdate();
        }
    }

 
    private void eliminarTelefonos(String dui) throws SQLException {
        String sql = "DELETE FROM tel_personal_docente WHERE dui_personal_docente = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dui);
            ps.executeUpdate();
        }
    }


    private ModeloDocente mapear(ResultSet rs) throws SQLException {
        ModeloDocente d = new ModeloDocente();
        d.setDuiDocente(rs.getString("dui_personal"));
        d.setNombre(rs.getString("primer_nombre"));
        d.setSegundoNombre(rs.getString("segundo_nombre"));
        d.setApellido(rs.getString("primer_apellido"));
        d.setSegundoApellido(rs.getString("segundo_apellido"));
        d.setDepartamento(rs.getString("departamento"));
        d.setMunicipio(rs.getString("municipio"));
        d.setDistrito(rs.getString("distrito"));
        d.setCaserio(rs.getString("caserio"));
        d.setCalle(rs.getString("calle"));
        d.setTelefonoDocente(rs.getString("telefono")); // puede ser null
        return d;
    }
}