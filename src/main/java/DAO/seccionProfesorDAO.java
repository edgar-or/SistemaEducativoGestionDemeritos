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
import modelo.ModeloSeccion;

/**
 *
 * @author zair8
 */
public class seccionProfesorDAO {
 
    public List<ModeloSeccion> obtenerSeccionesPorGrado(int idGrado) {
        List<ModeloSeccion> lista = new ArrayList<>();
        String sql = "SELECT id_seccion, seccion FROM seccion WHERE id_grado = ?";
 
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
 
            ps.setInt(1, idGrado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int    idSeccion = rs.getInt("id_seccion");
                    String seccion   = rs.getString("seccion"); 
                    lista.add(new ModeloSeccion(idSeccion, seccion));
                }
            }
 
        } catch (SQLException e) {
            System.out.println("Error al obtener secciones: " + e.getMessage());
        }
        return lista;
    }
}
 