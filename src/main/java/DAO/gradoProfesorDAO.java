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
import modelo.ModeloGrado;

/**
 *
 * @author zair8
 */
public class gradoProfesorDAO {
 
    public List<ModeloGrado> obtenerGrados() {
        List<ModeloGrado> lista = new ArrayList<>();
        String sql = "SELECT id_grado, grado FROM grados";
 
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
 
            while (rs.next()) {
                int idGrado = rs.getInt("id_grado");
                int grado   = rs.getInt("grado");
                lista.add(new ModeloGrado(idGrado, String.valueOf(grado)));
            }
 
        } catch (SQLException e) {
            System.out.println("Error al obtener grados: " + e.getMessage());
        }
        return lista;
    }
}