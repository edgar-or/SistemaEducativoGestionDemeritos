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
import modelo.ModeloCorreoEncargadoAlumno;
import modelo.ModeloEncardoAlumno;

/**
 *
 * @author estud
 */
public class CorreoEncargadoDAO {

    public List<ModeloCorreoEncargadoAlumno> listarCorreosPorEncargado(int idEncargado) throws SQLException {
        List<ModeloCorreoEncargadoAlumno> lista = new ArrayList<>();
        String sql = "SELECT id_correo, correo, id_encargado FROM correo_encargado_estudiante WHERE id_encargado = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEncargado);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ModeloEncardoAlumno encargado = new ModeloEncardoAlumno();
                    encargado.setIdEncargado(rs.getInt("id_encargado"));

                    ModeloCorreoEncargadoAlumno correo = new ModeloCorreoEncargadoAlumno(
                            rs.getInt("id_correo"),
                            rs.getString("correo"),
                            encargado
                    );

                    lista.add(correo);
                }
            }
        }
        return lista;
    }

    
    public void guardarCorreoEncargado(ModeloCorreoEncargadoAlumno correo) throws SQLException {
        String sql = "INSERT INTO correo_encargado_estudiante (correo, id_encargado) VALUES (?, ?)";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, correo.getCorreoEncargado());
            ps.setInt(2, correo.getModeloEncardoAlumno().getIdEncargado());

            ps.executeUpdate();
        }
    }

    
    public void eliminarCorreoEncargado(int idCorreo) throws SQLException {
        String sql = "DELETE FROM correo_encargado_estudiante WHERE id_correo = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCorreo);
            ps.executeUpdate();
        }
    }
}
