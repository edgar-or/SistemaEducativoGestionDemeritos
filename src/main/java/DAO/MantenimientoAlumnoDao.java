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
import modelo.ModeloAlumno;

/**
 *
 * @author ayala
 */
public class MantenimientoAlumnoDao {

    // insertar 
    public boolean insertarAlumno(ModeloAlumno alumno) {

        String sql = "INSERT INTO estudiante( nie, primer_nombre, segundo_nombre, primer_apellido, segundo_apellido, total_puntos, id_seccion, dui_encargado ) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
                Connection con = Conexion.getConexion(); 
                PreparedStatement ps = con.prepareStatement(sql)) {

            // separa nombre
            String[] nombres
                    = alumno.getNombre().trim().split("\\s+", 2);

            String primerNombre = nombres[0];

            String segundoNombre
                    = nombres.length > 1 ? nombres[1] : "";

            // separa apellido
            String[] apellidos
                    = alumno.getApelliddos().trim().split("\\s+", 2);

            String primerApellido = apellidos[0];

            String segundoApellido
                    = apellidos.length > 1 ? apellidos[1] : "";

            ps.setString(1, String.valueOf(alumno.getNie()));
            ps.setString(2, primerNombre);
            ps.setString(3, segundoNombre);
            ps.setString(4, primerApellido);
            ps.setString(5, segundoApellido);
            ps.setInt(6, alumno.getTotalPuntos());
            ps.setInt(7, alumno.getIdSeccion());
            ps.setString(8, alumno.getDuiEncargado());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println("Error insertarAlumno: " + e.getMessage());
            return false;
        }
    }

    // modificar 
    public boolean actualizarAlumno(ModeloAlumno alumno) {

        String sql = "UPDATE estudiante SET primer_nombre = ?, segundo_nombre = ?, primer_apellido = ?, segundo_apellido = ?, total_puntos = ?, id_seccion = ?, dui_encargado = ? WHERE nie = ?";

        try (
                Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            
        // separa
        String[] nombres =
                alumno.getNombre().trim().split("\\s+", 2);

        String primerNombre = nombres[0];

        String segundoNombre =
                nombres.length > 1 ? nombres[1] : "";

        // separa
        String[] apellidos =
                alumno.getApelliddos().trim().split("\\s+", 2);

        String primerApellido = apellidos[0];

        String segundoApellido =
                apellidos.length > 1 ? apellidos[1] : "";

            ps.setString(1, primerNombre);
            ps.setString(2, segundoNombre);
            ps.setString(3, primerApellido);
            ps.setString(4, segundoApellido);
            ps.setInt(5, alumno.getTotalPuntos());
            ps.setInt(6, alumno.getIdSeccion());
            ps.setString(7, alumno.getDuiEncargado());
            ps.setString(8, String.valueOf(alumno.getNie()));

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println("Error actualizarAlumno: " + e.getMessage());
            return false;
        }
    }

    // eliinar
    public boolean eliminarAlumno(String nie) {

        String sql = "DELETE FROM estudiante WHERE nie = ?";

        try (
                Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nie);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println("Error eliminarAlumno: " + e.getMessage());
            return false;
        }
    }

    // listar
    public List<ModeloAlumno> listarAlumnos() {

    List<ModeloAlumno> lista = new ArrayList<>();

    String sql = "SELECT * FROM estudiante";

    try (
            Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
    ) {

        while (rs.next()) {

            ModeloAlumno a = new ModeloAlumno();

            // UNE NOMBRE
            String nombreCompleto =
                    rs.getString("primer_nombre");

            if (rs.getString("segundo_nombre") != null
                    && !rs.getString("segundo_nombre").isEmpty()) {

                nombreCompleto += " "
                        + rs.getString("segundo_nombre");
            }

            // UNE APELLIDOS
            String apellidoCompleto =
                    rs.getString("primer_apellido");

            if (rs.getString("segundo_apellido") != null
                    && !rs.getString("segundo_apellido").isEmpty()) {

                apellidoCompleto += " "
                        + rs.getString("segundo_apellido");
            }

            a.setNie(Integer.parseInt(rs.getString("nie")));
            a.setNombre(nombreCompleto);
            a.setApelliddos(apellidoCompleto);
            a.setTotalPuntos(rs.getInt("total_puntos"));
            a.setIdSeccion(rs.getInt("id_seccion"));
            a.setDuiEncargado(rs.getString("dui_encargado"));

            lista.add(a);
        }

    } catch (Exception e) {

        System.out.println(
                "Error listarAlumnos: "
                + e.getMessage()
        );
    }

    return lista;
}

    // BUSCA POR NIE
    public ModeloAlumno buscarPorNie(String nie) {

        String sql = "SELECT * FROM estudiante WHERE nie = ?";

        try (
                Connection con = Conexion.getConexion(); 
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nie);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    ModeloAlumno a = new ModeloAlumno();


            // UNIR NOMBRES
            String nombreCompleto =
                    rs.getString("primer_nombre");

            if (rs.getString("segundo_nombre") != null
                    && !rs.getString("segundo_nombre").isEmpty()) {

                nombreCompleto += " "
                        + rs.getString("segundo_nombre");
            }

            // UNIR APELLIDOS
            String apellidoCompleto =
                    rs.getString("primer_apellido");

            if (rs.getString("segundo_apellido") != null && !rs.getString("segundo_apellido").isEmpty()) {

                apellidoCompleto += " " + rs.getString("segundo_apellido");
            }

            a.setNie(Integer.parseInt(rs.getString("nie")));

            a.setNombre(nombreCompleto);

            a.setApelliddos(apellidoCompleto);

            a.setTotalPuntos(rs.getInt("total_puntos"));

            a.setIdSeccion(rs.getInt("id_seccion"));

            a.setDuiEncargado(rs.getString("dui_encargado"));

                    return a;
                }
            }

        } catch (Exception e) {

            System.out.println("Error buscarPorNie: " + e.getMessage());
        }

        return null;
    }

    // BUSCA POR NOMBRE
    public List<ModeloAlumno> buscarPorNombre(String nombre) {

        List<ModeloAlumno> lista = new ArrayList<>();

        String sql = "SELECT * FROM estudiante WHERE primer_nombre LIKE ?";

        try (
                Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                     ModeloAlumno a = new ModeloAlumno();

            // UNIR NOMBRES
            String nombreCompleto =
                    rs.getString("primer_nombre");

            if (rs.getString("segundo_nombre") != null
                    && !rs.getString("segundo_nombre").isEmpty()) {

                nombreCompleto += " "
                        + rs.getString("segundo_nombre");
            }

            // UNI APELLIDOS
            String apellidoCompleto =
                    rs.getString("primer_apellido");

            if (rs.getString("segundo_apellido") != null
                    && !rs.getString("segundo_apellido").isEmpty()) {

                apellidoCompleto += " "
                        + rs.getString("segundo_apellido");
            }

            // SETTERS
            a.setNie(
                    Integer.parseInt(rs.getString("nie"))
            );

            a.setNombre(nombreCompleto);

            a.setApelliddos(apellidoCompleto);

            a.setTotalPuntos(
                    rs.getInt("total_puntos")
            );

            a.setIdSeccion(
                    rs.getInt("id_seccion")
            );

            a.setDuiEncargado(
                    rs.getString("dui_encargado")
            );

                    lista.add(a);
                }
            }

        } catch (Exception e) {

            System.out.println("Error buscarPorNombre: " + e.getMessage());
        }

        return lista;
    }

   

}
