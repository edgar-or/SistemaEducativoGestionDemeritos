package DAO;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import DAO.conexion.Conexion;
import java.sql.Statement;


 
import modelo.ModeloDocente;
public class DocenteDAO {
    
 
    // NUEVO MÉTODO: Para llenar dinámicamente el JComboBox de cargos desde la BD
    public List<Object[]> listarCargos() throws SQLException {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id_cargo_personal, cargo_personal FROM cargo_personal";
        
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new Object[]{rs.getInt("id_cargo_personal"), rs.getString("cargo_personal")});
            }
        }
        return lista;
    }


public int insertarDocente(ModeloDocente d) throws SQLException {
    String sql = "INSERT INTO personal_docente "
            + "(dui_personal, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, "
            + "departamento, municipio, distrito, caserio, calle, id_cargo_personal, id_usuario) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NULL)";

    int idGenerado;

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        ps.setString(1, d.getDuiDocente());
        ps.setString(2, d.getNombre());
        ps.setString(3, d.getSegundoNombre());
        ps.setString(4, d.getApellido());
        ps.setString(5, d.getSegundoApellido());
        ps.setString(6, d.getDepartamento());
        ps.setString(7, d.getMunicipio());
        ps.setString(8, d.getDistrito());
        ps.setString(9, d.getCaserio());
        ps.setString(10, d.getCalle());
        ps.setInt(11, d.getIdCargo());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            idGenerado = rs.getInt(1);
        } else {
            throw new SQLException("No se generó ID para el docente.");
        }
    }

    if (d.getTelefonoDocente() != null && !d.getTelefonoDocente().isEmpty()) {
        insertarTelefono(d.getDuiDocente(), d.getTelefonoDocente());
    }

    if (d.getCorreo() != null && !d.getCorreo().isEmpty()) {
        insertarCorreo(d.getDuiDocente(), d.getCorreo());
    }

    return idGenerado;
}
 
    public void insertarTelefono(String dui, String telefono) throws SQLException {
        String sql = "INSERT INTO tel_personal_docente (telefono, id_personal) VALUES (?, (SELECT id_personal FROM personal_docente WHERE dui_personal = ?))";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, telefono);
            ps.setString(2, dui);
            ps.executeUpdate();
        }
    }
 
    public void insertarCorreo(String dui, String correo) throws SQLException {
        String sql = "INSERT INTO correo_personal_docente (correo_electronico, id_personal) VALUES (?, (SELECT id_personal FROM personal_docente WHERE dui_personal = ?))";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setString(2, dui);
            ps.executeUpdate();
        }
    }
 
    public List<ModeloDocente> listarDocentes() throws SQLException {
        List<ModeloDocente> lista = new ArrayList<>();
        // ARREGLADO: Agregada la columna pd.id_cargo_personal
        String sql = "SELECT pd.id_personal, pd.dui_personal, pd.primer_nombre, pd.segundo_nombre, "
                + "pd.primer_apellido, pd.segundo_apellido, pd.departamento, pd.municipio, "
                + "pd.distrito, pd.caserio, pd.calle, pd.id_cargo_personal, "
                + "(SELECT t.telefono FROM tel_personal_docente t "
                + " WHERE t.id_personal = pd.id_personal LIMIT 1) AS telefono, "
                + "(SELECT c.correo_electronico FROM correo_personal_docente c "
                + " WHERE c.id_personal = pd.id_personal LIMIT 1) AS correo "
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
        // ARREGLADO: Agregada la columna pd.id_cargo_personal
        String sql = "SELECT pd.id_personal, pd.dui_personal, pd.primer_nombre, pd.segundo_nombre, "
                + "pd.primer_apellido, pd.segundo_apellido, pd.departamento, pd.municipio, "
                + "pd.distrito, pd.caserio, pd.calle, pd.id_cargo_personal, "
                + "(SELECT t.telefono FROM tel_personal_docente t "
                + " WHERE t.id_personal = pd.id_personal LIMIT 1) AS telefono, "
                + "(SELECT c.correo_electronico FROM correo_personal_docente c "
                + " WHERE c.id_personal = pd.id_personal LIMIT 1) AS correo "
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
        // ARREGLADO: Agregada la columna pd.id_cargo_personal
        String sql = "SELECT pd.id_personal, pd.dui_personal, pd.primer_nombre, pd.segundo_nombre, "
                + "pd.primer_apellido, pd.segundo_apellido, pd.departamento, pd.municipio, "
                + "pd.distrito, pd.caserio, pd.calle, pd.id_cargo_personal, "
                + "(SELECT t.telefono FROM tel_personal_docente t "
                + " WHERE t.id_personal = pd.id_personal LIMIT 1) AS telefono, "
                + "(SELECT c.correo_electronico FROM correo_personal_docente c "
                + " WHERE c.id_personal = pd.id_personal LIMIT 1) AS correo "
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
                + "distrito=?, caserio=?, calle=?, id_cargo_personal=? WHERE dui_personal=?";
 
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
            ps.setInt(10, d.getIdCargo()); 
            ps.setString(11, d.getDuiDocente());
            ps.executeUpdate();
        }
 
        if (d.getTelefonoDocente() != null && !d.getTelefonoDocente().isEmpty()) {
            eliminarTelefonos(d.getDuiDocente());
            insertarTelefono(d.getDuiDocente(), d.getTelefonoDocente());
        }
 
        if (d.getCorreo() != null && !d.getCorreo().isEmpty()) {
            eliminarCorreos(d.getDuiDocente());
            insertarCorreo(d.getDuiDocente(), d.getCorreo());
        }
    }
 
    public void eliminarDocente(String dui) throws SQLException {
        eliminarTelefonos(dui);
        eliminarCorreos(dui);
        
        String sql = "DELETE FROM personal_docente WHERE dui_personal = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dui);
            ps.executeUpdate();
        }
    }
 
    private void eliminarTelefonos(String dui) throws SQLException {
        String sql = "DELETE FROM tel_personal_docente WHERE id_personal = (SELECT id_personal FROM personal_docente WHERE dui_personal = ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dui);
            ps.executeUpdate();
        }
    }
 
    private void eliminarCorreos(String dui) throws SQLException {
        String sql = "DELETE FROM correo_personal_docente WHERE id_personal = (SELECT id_personal FROM personal_docente WHERE dui_personal = ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dui);
            ps.executeUpdate();
        }
    }
 
    public List<Object[]> listarTelefonosPorDui(String dui) throws SQLException {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id_telefono, telefono FROM tel_personal_docente WHERE id_personal = (SELECT id_personal FROM personal_docente WHERE dui_personal = ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dui);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Object[]{rs.getInt(1), rs.getString(2)});
                }
            }
        }
        return lista;
    }
 
    public void eliminarTelefonoPorId(int idTelefono) throws SQLException {
        String sql = "DELETE FROM tel_personal_docente WHERE id_telefono = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idTelefono);
            ps.executeUpdate();
        }
    }
 
    public List<Object[]> listarCorreosPorDui(String dui) throws SQLException {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT id_correo, correo_electronico FROM correo_personal_docente WHERE id_personal = (SELECT id_personal FROM personal_docente WHERE dui_personal = ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dui);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Object[]{rs.getInt(1), rs.getString(2)});
                }
            }
        }
        return lista;
    }
 
    public void eliminarCorreoPorId(int idCorreo) throws SQLException {
        String sql = "DELETE FROM correo_personal_docente WHERE id_correo = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCorreo);
            ps.executeUpdate();
        }
    }
 
    private ModeloDocente mapear(ResultSet rs) throws SQLException {
        ModeloDocente d = new ModeloDocente();
        try { d.setIdPersonal(rs.getInt("id_personal")); } catch (SQLException ignored) {}
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
        d.setTelefonoDocente(rs.getString("telefono"));
        d.setCorreo(rs.getString("correo"));
        
        // ARREGLADO: Mapeamos el ID del cargo recuperado de la base de datos
        try { d.setIdCargo(rs.getInt("id_cargo_personal")); } catch (SQLException ignored) {}
        
        return d;
    }
}