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
        String sql = "SELECT id_tipo_conducta, tipo, descripcion, puntos " +
                 "FROM tipo_conducta WHERE tipo = 'merito'";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            ModeloConducta conducta = new ModeloConducta();
            conducta.setIdTipo(rs.getInt("id_tipo_conducta"));
            conducta.setTipo(rs.getString("tipo"));
            conducta.setDescripcion(rs.getString("descripcion"));
            conducta.setPuntos(rs.getInt("puntos"));

            // misma lógica que demérito
            conducta.setMovimientoConducta(new ArrayList<>());
                lista.add(conducta);
            }
        } catch (Exception e) {
            System.err.println("Error al obtener tipos de conducta: " + e.getMessage());
        }
    return lista;
    }

    public static void insertarMerito(int id_estudiante, String observacion, int id_personal, int idTipo) {
        String sql = "INSERT INTO movimiento_conducta (observacion, fecha, id_personal, id_estudiante, id_tipo_conducta) VALUES (?, NOW(), ?,?,? )";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, observacion);
            ps.setInt(2, id_personal);
            ps.setInt(3, id_estudiante);
            ps.setInt(4, idTipo);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void actualizarPuntos(int id) {
        String sql = """
            UPDATE estudiante e
            SET total_puntos = (
                SELECT COALESCE(SUM(tc.puntos),0)
                FROM movimiento_conducta mc
                INNER JOIN tipo_conducta tc 
                ON mc.id_tipo_conducta = tc.id_tipo_conducta
                WHERE mc.id_estudiante = e.id_estudiante
            )
            WHERE e.id_estudiante = ?
        """;

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
