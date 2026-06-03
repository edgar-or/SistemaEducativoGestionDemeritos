package DAO;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import DAO.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.ModeloConducta;

/**
 *
 * @author estud
 */
public class DemeritoDAO {

    public static List<ModeloConducta> obtenerTiposConducta() {
        List<ModeloConducta> lista = new ArrayList<>();
        String sql = "SELECT id_tipo_conducta, tipo, descripcion, puntos FROM tipo_conducta WHERE tipo = 'demerito'";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ModeloConducta conducta = new ModeloConducta();
                conducta.setIdTipo(rs.getInt("id_tipo_conducta"));
                conducta.setTipo(rs.getString("tipo"));
                conducta.setDescripcion(rs.getString("descripcion"));
                conducta.setPuntos(rs.getInt("puntos"));
                conducta.setMovimientoConducta(new ArrayList<>());

                lista.add(conducta);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener tipos de conducta: " + e.getMessage());
        }
        return lista;
    }

    public static void insertarDemerito(String nie, String observacion, String dui, int idTipo) {
        String sql = "INSERT INTO movimiento_conducta (observacion, fecha, dui_personal, nie, id_tipo_conducta) VALUES (?, NOW(), ?, ?, ?)";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, observacion);
            ps.setString(2, dui);
            ps.setString(3, nie);
            ps.setInt(4, idTipo);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actualizarPuntos(String nie) {
        String sql = "UPDATE estudiante e SET total_puntos = ( SELECT COALESCE(SUM(tc.puntos),0) FROM movimiento_conducta mc INNER JOIN tipo_conducta tc ON mc.id_tipo_conducta = tc.id_tipo_conducta WHERE mc.nie = e.nie ) WHERE e.nie = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nie);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
