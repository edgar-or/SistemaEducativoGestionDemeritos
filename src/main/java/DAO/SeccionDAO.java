/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import DAO.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.ModeloSeccion;

/**
 *
 * @author ayala
 */
public class SeccionDAO {
   
    public void insertarSeccion(ModeloSeccion seccion) throws Exception {

    String sql = "INSERT INTO seccion(seccion, id_grado) VALUES (?, ?)";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, seccion.getSeccion());

        // obtenemos el id desde el objeto grado
        ps.setInt(2, seccion.getGrado().getIdGrado());

        ps.executeUpdate();
    }
}

    
}
