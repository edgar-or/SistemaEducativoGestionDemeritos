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
import modelo.ArbolBinarioBusqueda;
import modelo.ModeloAlumno;
import modelo.ModeloEncardoAlumno;
import modelo.ModeloSeccion;

/**
 *
 * @author ayala
 */
public class MantenimientoAlumnoDao {

    private ArbolBinarioBusqueda arbol;

    public boolean insertarAlumno(ModeloAlumno alumno) {
        String sql = "INSERT INTO estudiante(nie, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, total_puntos, id_seccion, id_encargado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            String[] nombres = alumno.getNombre().trim().split("\\s+", 2);
            String primerNombre = nombres[0];
            String segundoNombre = nombres.length > 1 ? nombres[1] : "";

            String[] apellidos = alumno.getApelliddos().trim().split("\\s+", 2);
            String primerApellido = apellidos[0];
            String segundoApellido = apellidos.length > 1 ? apellidos[1] : "";

            int idSeccion = (alumno.getModeloSeccion() != null) ? alumno.getModeloSeccion().getIdSeccion() : 0;

            String duiEncargado = (alumno.getModeloEncargadoAlumno() != null) ? alumno.getModeloEncargadoAlumno().getDui() : null;

            ps.setInt(1, alumno.getNie());
            ps.setString(2, primerNombre);
            ps.setString(3, segundoNombre);
            ps.setString(4, primerApellido);
            ps.setString(5, segundoApellido);
            ps.setInt(6, alumno.getTotalPuntos());
            ps.setInt(7, idSeccion);

            if (duiEncargado != null) {
                ps.setString(8, duiEncargado);
            } else {
                ps.setNull(8, java.sql.Types.INTEGER);
            }

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error insertarAlumno: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarAlumno(ModeloAlumno alumno) {
        String sql = "UPDATE estudiante SET nie = ?, primer_nombre = ?, segundo_nombre = ?, "
                + "primer_apellido = ?, segundo_apellido = ?, total_puntos = ?, "
                + "id_seccion = ?, id_encargado = ? WHERE id_estudiante = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            String[] nombres = alumno.getNombre().trim().split("\\s+", 2);
            String primerNombre = nombres[0];
            String segundoNombre = nombres.length > 1 ? nombres[1] : "";

            String[] apellidos = alumno.getApelliddos().trim().split("\\s+", 2);
            String primerApellido = apellidos[0];
            String segundoApellido = apellidos.length > 1 ? apellidos[1] : "";

            int idSeccion = (alumno.getModeloSeccion() != null)
                    ? alumno.getModeloSeccion().getIdSeccion() : 0;

            Integer idEncargado = (alumno.getModeloEncargadoAlumno() != null)
                    ? alumno.getModeloEncargadoAlumno().getIdEncargado() : null;

            ps.setInt(1, alumno.getNie());        // nuevo NIE (editable)
            ps.setString(2, primerNombre);
            ps.setString(3, segundoNombre);
            ps.setString(4, primerApellido);
            ps.setString(5, segundoApellido);
            ps.setInt(6, alumno.getTotalPuntos());
            ps.setInt(7, idSeccion);

            if (idEncargado != null) {
                ps.setInt(8, idEncargado);
            } else {
                ps.setNull(8, java.sql.Types.INTEGER);
            }

            ps.setInt(9, alumno.getId_alumno());  // PK serial en el WHERE

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error actualizarAlumno: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminarAlumno(int id) {
        String sql = "DELETE FROM estudiante WHERE id_estudiante = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error eliminarAlumno: " + e.getMessage());
            return false;
        }
    }

    public ArbolBinarioBusqueda<ModeloAlumno> listarAlumnos() {

        ArbolBinarioBusqueda<ModeloAlumno> arbol = new ArbolBinarioBusqueda<>();

        String sql = "SELECT * FROM estudiante";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ModeloAlumno a = new ModeloAlumno();

                String nombreCompleto = rs.getString("primer_nombre");

                if (rs.getString("segundo_nombre") != null
                        && !rs.getString("segundo_nombre").isEmpty()) {
                    nombreCompleto += " " + rs.getString("segundo_nombre");
                }

                String apellidoCompleto = rs.getString("primer_apellido");

                if (rs.getString("segundo_apellido") != null
                        && !rs.getString("segundo_apellido").isEmpty()) {
                    apellidoCompleto += " " + rs.getString("segundo_apellido");
                }

                a.setId_alumno(rs.getInt("id_estudiante"));
                a.setNie(rs.getInt("nie"));
                a.setNombre(nombreCompleto);
                a.setApelliddos(apellidoCompleto);
                a.setTotalPuntos(rs.getInt("total_puntos"));

                arbol.insertar(a);
            }

        } catch (Exception e) {
            System.out.println("Error listarAlumnos: " + e.getMessage());
        }

        return arbol;
    }

    public ModeloAlumno buscarPorNie(int nie) {
        String sql = "SELECT * FROM estudiante WHERE nie = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, nie);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ModeloAlumno a = new ModeloAlumno();
                    String nombreCompleto = rs.getString("primer_nombre");
                    if (rs.getString("segundo_nombre") != null && !rs.getString("segundo_nombre").isEmpty()) {
                        nombreCompleto += " " + rs.getString("segundo_nombre");
                    }

                    String apellidoCompleto = rs.getString("primer_apellido");
                    if (rs.getString("segundo_apellido") != null && !rs.getString("segundo_apellido").isEmpty()) {
                        apellidoCompleto += " " + rs.getString("segundo_apellido");
                    }

                    a.setId_alumno(rs.getInt("id_estudiante"));

                    a.setNie(rs.getInt("nie"));
                    a.setNombre(nombreCompleto);
                    a.setApelliddos(apellidoCompleto);
                    a.setTotalPuntos(rs.getInt("total_puntos"));

                    ModeloSeccion seccion = new ModeloSeccion();
                    seccion.setIdSeccion(rs.getInt("id_seccion"));
                    a.setModeloSeccion(seccion);

                    int duiVal =  rs.getInt("id_encargado");
                    if (!rs.wasNull()) {
                        ModeloEncardoAlumno encargado = new ModeloEncardoAlumno();
                        encargado.setIdEncargado(duiVal);
                        a.setModeloEncargadoAlumno(encargado);
                    }

                    return a;
                }
            }
        } catch (Exception e) {
            System.out.println("Error buscarPorNie: " + e.getMessage());
        }
        return null;
    }

    public List<ModeloAlumno> buscarPorNombre(String nombre) {
        List<ModeloAlumno> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiante WHERE primer_nombre LIKE ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nombre + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ModeloAlumno a = new ModeloAlumno();
                    String nombreCompleto = rs.getString("primer_nombre");
                    if (rs.getString("segundo_nombre") != null && !rs.getString("segundo_nombre").isEmpty()) {
                        nombreCompleto += " " + rs.getString("segundo_nombre");
                    }

                    String apellidoCompleto = rs.getString("primer_apellido");
                    if (rs.getString("segundo_apellido") != null && !rs.getString("segundo_apellido").isEmpty()) {
                        apellidoCompleto += " " + rs.getString("segundo_apellido");
                    }
                    a.setId_alumno(rs.getInt("id_estudiante"));

                    a.setNie(rs.getInt("nie"));
                    a.setNombre(nombreCompleto);
                    a.setApelliddos(apellidoCompleto);
                    a.setTotalPuntos(rs.getInt("total_puntos"));

                    ModeloSeccion seccion = new ModeloSeccion();
                    seccion.setIdSeccion(rs.getInt("id_seccion"));
                    a.setModeloSeccion(seccion);

                    int duiVal = rs.getInt("id_encargado");
                    if (!rs.wasNull()) {
                        ModeloEncardoAlumno encargado = new ModeloEncardoAlumno();
                        encargado.setIdEncargado(duiVal);
                        a.setModeloEncargadoAlumno(encargado);
                    }

                    lista.add(a);
                }
            }
        } catch (Exception e) {
            System.out.println("Error buscarPorNombre: " + e.getMessage());
        }
        return lista;
    }
}
