/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.ModeloCentroEscolar;
import DAO.conexion.Conexion;

/**
 *
 * @author ayala
 */
public class CentroEscolarDAO {
    
    
     // INSERTAR
    public void insertarCentroEscolar(ModeloCentroEscolar c) throws SQLException {

        String sql = "INSERT INTO centro_escolar " +
                "(codigo_CE, nombre_CE, num_tel, departamento, municipio, distrito, caserio, calle, correo_electronico) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, c.getCodigoCE());
            ps.setString(2, c.getNombreCentroEscolar());
            ps.setString(3, c.getNumeroTel());
            ps.setString(4, c.getDepartamento());
            ps.setString(5, c.getMunicipio());
            ps.setString(6, c.getDistrito());
            ps.setString(7, c.getCaserio());
            ps.setString(8, c.getCalle());
            ps.setString(9, c.getCorreoElectronico());

            ps.executeUpdate();
        }
    }
    
}
