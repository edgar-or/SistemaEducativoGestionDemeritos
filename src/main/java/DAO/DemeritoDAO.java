/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

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
        //String sql = "SELECT id_tipo_conducta, tipo, descripcion, puntos FROM tipo_conducta where puntos < 0";
        String sql = "SELECT id_tipo_conducta, tipo, descripcion, puntos FROM tipo_conducta WHERE tipo = 'demerito'";
        
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

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
}
