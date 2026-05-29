/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.ModeloConducta;

/**
 *
 * @author renec
 */
public class MeritoDAO {
      public static List<ModeloConducta> obtenerTiposConducta() {
        List<ModeloConducta> lista = new ArrayList<>();
        String sql = "SELECT id_tipo_conducta, tipo, descripcion, puntos FROM tipo_conducta WHERE tipo= 'merito'";

        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ModeloConducta conducta = new ModeloConducta(
                        rs.getString("tipo"),
                        rs.getString("descripcion"),
                        rs.getInt("id_tipo_conducta"),
                        rs.getInt("puntos")
                );
                lista.add(conducta);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener tipos de conducta: " + e.getMessage());
        }
        return lista;
    }

    // 🔥 ESTE ES EL QUE TE FALTABA
    public static void insertarMerito(String nie, int idTipo, String observacion) {

        String sql = "INSERT INTO movimiento_conducta (nie, id_tipo_conducta, observacion, fecha) VALUES (?, ?, ?, NOW())";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nie);
            ps.setInt(2, idTipo);
            ps.setString(3, observacion);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🔥 Y ESTE PARA ACTUALIZAR PUNTOS
    public static void actualizarPuntos(String nie) {

        String sql = """
            UPDATE estudiante e
            SET total_puntos = (
                SELECT COALESCE(SUM(tc.puntos),0)
                FROM movimiento_conducta mc
                INNER JOIN tipo_conducta tc 
                ON mc.id_tipo_conducta = tc.id_tipo_conducta
                WHERE mc.nie = e.nie
            )
            WHERE e.nie = ?
        """;

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nie);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
