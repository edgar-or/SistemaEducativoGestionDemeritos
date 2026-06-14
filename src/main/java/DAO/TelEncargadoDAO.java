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
import modelo.ModeloTelefonosEncargadosAlumno;

/**
 *
 * @author estud
 */
public class TelEncargadoDAO {

    public List<ModeloTelefonosEncargadosAlumno> listarTelefonosPorEncargado(int idEncargado) throws SQLException {
        List<ModeloTelefonosEncargadosAlumno> lista = new ArrayList<>();

        // CORREGIDO: Nombre de tabla y columnas según tu MySQL
        String sql = "SELECT id_tel, telefono FROM tel_encargado_estudiante WHERE id_encargado = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEncargado);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // CORREGIDO: Mapeo con los nombres reales de las columnas 'id_tel' y 'telefono'
                    ModeloTelefonosEncargadosAlumno tel = new ModeloTelefonosEncargadosAlumno(
                            rs.getInt("id_tel"),
                            rs.getInt("telefono"),
                            null
                    );
                    lista.add(tel);
                }
            }
        }
        return lista;
    }

    public void guardarTelefonoEncargado(ModeloTelefonosEncargadosAlumno telefono) throws SQLException {
        String sql = "INSERT INTO tel_encargado_estudiante (telefono, id_encargado) VALUES (?, ?)";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, telefono.getTelefono());
            ps.setInt(2, telefono.getEncargadoAlumno().getIdEncargado());
            ps.executeUpdate();
        }
    }

    public void eliminarTelefonoEncargado(int idTel) throws SQLException {
        String sql = "DELETE FROM tel_encargado_estudiante WHERE id_tel = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTel);
            ps.executeUpdate();
        }
    }
}
