package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.ModeloEncardoAlumno;
import DAO.conexion.Conexion;
import modelo.ArbolBinarioBusqueda;
import modelo.ModeloCorreoEncargadoAlumno;
import modelo.ModeloTelefonosEncargadosAlumno;
import utileria.ArbolB;

public class EncargadoDAO {

    private ArbolB arbolB;

    public void insertarEncargado(ModeloEncardoAlumno encargado) throws SQLException {

        String sql = "INSERT INTO encargado_estudiante (dui_encargado, primer_nombre, segundo_nombre, "
                + "primer_apellido, segundo_apellido, departamento, municipio, distrito, "
                + "canton, caserio, calle, num_casa) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, encargado.getDui());
            ps.setString(2, encargado.getPrimerNombre());
            ps.setString(3, encargado.getSegundoNombre());
            ps.setString(4, encargado.getPrimerApellido());
            ps.setString(5, encargado.getSegundoApellido());
            ps.setString(6, encargado.getDepartamento());
            ps.setString(7, encargado.getMunicipio());
            ps.setString(8, encargado.getDistrito());
            ps.setString(9, encargado.getCanton());
            ps.setString(10, encargado.getCaserio());
            ps.setString(11, encargado.getCalle());
            ps.setString(12, encargado.getNumCasa());

            ps.executeUpdate();
        }
    }

    public List<ModeloEncardoAlumno> listarEncargados() throws SQLException {
        ArbolBinarioBusqueda<ModeloEncardoAlumno> arbol = new ArbolBinarioBusqueda<>();

        String sql = "SELECT id_encargado, dui_encargado, primer_nombre, segundo_nombre, primer_apellido, "
                + "segundo_apellido, departamento, municipio, distrito, canton, caserio, calle, num_casa "
                + "FROM encargado_estudiante";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ModeloEncardoAlumno encargado = new ModeloEncardoAlumno();

                encargado.setIdEncargado(rs.getInt("id_encargado"));
                encargado.setDui(rs.getString("dui_encargado"));
                encargado.setPrimerNombre(rs.getString("primer_nombre"));
                encargado.setSegundoNombre(rs.getString("segundo_nombre"));
                encargado.setPrimerApellido(rs.getString("primer_apellido"));
                encargado.setSegundoApellido(rs.getString("segundo_apellido"));
                encargado.setDepartamento(rs.getString("departamento"));
                encargado.setMunicipio(rs.getString("municipio"));
                encargado.setDistrito(rs.getString("distrito"));
                encargado.setCanton(rs.getString("canton"));
                encargado.setCaserio(rs.getString("caserio"));
                encargado.setCalle(rs.getString("calle"));
                encargado.setNumCasa(rs.getString("num_casa"));

                arbol.insertar(encargado);
            }
        }

        return arbol.NID();
    }

    public ModeloEncardoAlumno buscarPorDui(String dui) throws SQLException {
        String sql = "SELECT * FROM encargado_estudiante WHERE dui_encargado = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dui);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ModeloEncardoAlumno encargado = new ModeloEncardoAlumno();
                    encargado.setDui(rs.getString("dui_encargado"));
                    encargado.setPrimerNombre(rs.getString("primer_nombre"));
                    encargado.setSegundoNombre(rs.getString("segundo_nombre"));
                    encargado.setPrimerApellido(rs.getString("primer_apellido"));
                    encargado.setSegundoApellido(rs.getString("segundo_apellido"));
                    encargado.setDepartamento(rs.getString("departamento"));
                    encargado.setMunicipio(rs.getString("municipio"));
                    encargado.setDistrito(rs.getString("distrito"));
                    encargado.setCanton(rs.getString("canton"));
                    encargado.setCaserio(rs.getString("caserio"));
                    encargado.setCalle(rs.getString("calle"));
                    encargado.setNumCasa(rs.getString("num_casa"));
                    return encargado;
                }
            }
        }
        return null;
    }

    public void modificarEncargado(ModeloEncardoAlumno encargado) throws SQLException {
        String sql = "UPDATE encargado_estudiante SET dui_encargado=?, primer_nombre=?, segundo_nombre=?, primer_apellido=?, "
                + "segundo_apellido=?, departamento=?, municipio=?, distrito=?, canton=?, caserio=?, "
                + "calle=?, num_casa=? WHERE id_encargado=?"; // <--- Filtro por ID

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, encargado.getDui()); // Ahora el DUI se puede actualizar
            ps.setString(2, encargado.getPrimerNombre());
            ps.setString(3, encargado.getSegundoNombre());
            ps.setString(4, encargado.getPrimerApellido());
            ps.setString(5, encargado.getSegundoApellido());
            ps.setString(6, encargado.getDepartamento());
            ps.setString(7, encargado.getMunicipio());
            ps.setString(8, encargado.getDistrito());
            ps.setString(9, encargado.getCanton());
            ps.setString(10, encargado.getCaserio());
            ps.setString(11, encargado.getCalle());
            ps.setString(12, encargado.getNumCasa());

            // El ID va al final porque es el parámetro del WHERE
            ps.setInt(13, encargado.getIdEncargado());

            ps.executeUpdate();
        }
    }

    public void eliminarEncargado(String dui) throws SQLException {
        String sql = "DELETE FROM encargado_estudiante WHERE dui_encargado = ?";

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, dui);
            ps.executeUpdate();
        }
    }
    

    // correos
//   public List<ModeloTelefonosEncargadosAlumno> listarTelefonosPorEncargado(int idEncargado) throws SQLException {
//        List<ModeloTelefonosEncargadosAlumno> lista = new ArrayList<>();
//
//        // CORREGIDO: Nombre de tabla y columnas según tu MySQL
//        String sql = "SELECT id_tel, telefono FROM tel_encargado_estudiante WHERE id_encargado = ?";
//
//        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setInt(1, idEncargado);
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    // CORREGIDO: Mapeo con los nombres reales de las columnas 'id_tel' y 'telefono'
//                    ModeloTelefonosEncargadosAlumno tel = new ModeloTelefonosEncargadosAlumno(
//                            rs.getInt("id_tel"),
//                            rs.getInt("telefono"),
//                            null
//                    );
//                    lista.add(tel);
//                }
//            }
//        }
//        return lista;
//    }
//
//    public void guardarTelefonoEncargado(ModeloTelefonosEncargadosAlumno telefono) throws SQLException {
//        String sql = "INSERT INTO tel_encargado_estudiante (telefono, id_encargado) VALUES (?, ?)";
//
//        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setInt(1, telefono.getTelefono());
//            ps.setInt(2, telefono.getEncargadoAlumno().getIdEncargado());
//            ps.executeUpdate();
//        }
//    }
//
//    public void eliminarTelefonoEncargado(int idTel) throws SQLException {
//        String sql = "DELETE FROM tel_encargado_estudiante WHERE id_tel = ?";
//
//        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
//
//            ps.setInt(1, idTel);
//            ps.executeUpdate();
//        }
//    }
}
