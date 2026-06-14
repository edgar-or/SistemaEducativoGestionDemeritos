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
import modelo.ModeloConducta;
import modelo.ModeloDocente;
import modelo.ModeloMovimientoConducta;

/**
 *
 * @author renec
 */
public class VerEstadoDAO {

    public List<ModeloMovimientoConducta> listarConductasAlumnos(int nie) throws SQLException {
        List<ModeloMovimientoConducta> lista = new ArrayList<>();
        String sql = """
        SELECT
            mc.observacion,
            mc.fecha,
            tc.id_tipo_conducta,
            tc.descripcion,
            tc.puntos,
            pd.dui_personal,
            pd.primer_nombre,
            pd.primer_apellido
        FROM movimiento_conducta mc
        INNER JOIN tipo_conducta tc
            ON mc.id_tipo_conducta = tc.id_tipo_conducta
        INNER JOIN personal_docente pd
            ON mc.dui_personal = pd.dui_personal
        WHERE mc.nie = ?
        ORDER BY mc.fecha DESC
    """;
       try (Connection con = Conexion.getConexion();
     PreparedStatement ps = con.prepareStatement(sql)) {

    ps.setInt(1, nie);

    try (ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            ModeloMovimientoConducta movimiento = new ModeloMovimientoConducta();
            movimiento.setObservacion(rs.getString("observacion"));
            movimiento.setFecha(rs.getDate("fecha").toLocalDate());

            ModeloConducta conducta = new ModeloConducta();
            conducta.setIdTipo(rs.getInt("id_tipo_conducta"));
            conducta.setDescripcion(rs.getString("descripcion"));
            conducta.setPuntos(rs.getInt("puntos"));

            ModeloDocente docente = new ModeloDocente();
            docente.setDuiDocente(rs.getString("dui_personal"));
            docente.setNombre(rs.getString("primer_nombre"));
            docente.setApellido(rs.getString("primer_apellido"));

            movimiento.setModeloConducta(conducta);
            movimiento.setModeloDocente(docente);

            lista.add(movimiento);
            }
        }
        return lista;
    }

    }
}