package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ModeloGrado;
import DAO.conexion.Conexion;

public class GradoDAO {
    
   public String obtenerCodigoCE() throws SQLException {

    String sql = "SELECT codigo_CE FROM centro_escolar";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            return rs.getString("codigo_CE");
        }
    }

    return null;
}
   public void insertarGrado(String nombreGrado) throws SQLException {

    String sql = "INSERT INTO grado(grado, cod_CE) VALUES (?, ?)";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, nombreGrado);
        ps.setString(2, obtenerCodigoCE());

        ps.executeUpdate();
    }
}

    public List<ModeloGrado> listarGrados() throws SQLException {
        List<ModeloGrado> lista = new ArrayList<>();
        String sql = "SELECT id_grado, grado FROM grado";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next())
                lista.add(new ModeloGrado(rs.getInt("id_grado"), rs.getString("grado")));
        }
        return lista;
    }

    public void modificarGrado(int idGrado, String nuevoNombre) throws SQLException {
        String sql = "UPDATE grado SET grado = ? WHERE id_grado = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoNombre);
            ps.setInt(2, idGrado);
            ps.executeUpdate();
        }
    }

    public void eliminarGrado(int idGrado) throws SQLException {
        String sql = "DELETE FROM grado WHERE id_grado = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idGrado);
            ps.executeUpdate();
        }
    }
}