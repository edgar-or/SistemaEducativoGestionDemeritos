/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.conexion.Conexion;
import dto.SeccionGradoDto;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import modelo.ModeloGrado;
import modelo.ModeloSeccion;

/**
 *
 * @author ayala
 */
public class AñoSeccionDao {
    
    public List<SeccionGradoDto> listarSeccionGrado() {
        List<SeccionGradoDto> lista = new ArrayList<>();

        String sql = "SELECT s.id_seccion, s.seccion, g.id_grado, g.grado " +
                     "FROM seccion s " +
                     "INNER JOIN grado g ON s.id_grado = g.id_grado";

        try {
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
                        ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SeccionGradoDto dto = new SeccionGradoDto();
                
                ModeloGrado grado = new ModeloGrado(); 

                ModeloSeccion seccion = new ModeloSeccion();
                seccion.setIdSeccion(rs.getInt("id_seccion"));
                seccion.setSeccion(rs.getString("seccion"));
                grado.setIdGrado(rs.getInt("id_grado"));
                grado.setGrado(rs.getString("grado"));
                
                dto.setGrado(grado);
                dto.setSeccion(seccion);

                lista.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
    
    
}
