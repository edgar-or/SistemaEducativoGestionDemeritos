package modelo;

import java.util.ArrayList;

public class ModeloDocente implements Comparable<ModeloDocente> {
    
    private int idPersonal; 
    private String duiDocente;
    private String nombre;
    private String segundoNombre;
    private String apellido;
    private String segundoApellido;
    private String telefonoDocente;
    private String correo;
    private String departamento;
    private String municipio;
    private String caserio;
    private String calle;
    private String distrito;
    private int numeroCasa;
    private ArrayList<ModeloSeccion> seccion;
    private ArrayList<ModeloTelefonosDocente> telefonoDocentes;
    private ArrayList<ModeloCorreoDocente> correoDocentes;
    private ArrayList<ModeloMovimientoConducta> movimientoConducta;
    private ModeloCargoDocente cargoDocente;
    private Usuario usuario;

    public ModeloDocente() {
    }

    public ModeloDocente(String duiDocente, String nombre, String segundoNombre,
            String apellido, String segundoApellido, String telefonoDocente,
            String correo, String departamento, String municipio, String caserio,
            String calle, String distrito, int numeroCasa,
            ArrayList<ModeloSeccion> seccion,
            ArrayList<ModeloTelefonosDocente> telefonoDocentes,
            ArrayList<ModeloCorreoDocente> correoDocentes,
            ArrayList<ModeloMovimientoConducta> movimientoConducta,
            ModeloCargoDocente cargoDocente, Usuario usuario) {
        this.duiDocente = duiDocente;
        this.nombre = nombre;
        this.segundoNombre = segundoNombre;
        this.apellido = apellido;
        this.segundoApellido = segundoApellido;
        this.telefonoDocente = telefonoDocente;
        this.correo = correo;
        this.departamento = departamento;
        this.municipio = municipio;
        this.caserio = caserio;
        this.calle = calle;
        this.distrito = distrito;
        this.numeroCasa = numeroCasa;
        this.seccion = seccion;
        this.telefonoDocentes = telefonoDocentes;
        this.correoDocentes = correoDocentes;
        this.movimientoConducta = movimientoConducta;
        this.cargoDocente = cargoDocente;
        this.usuario = usuario;
    }

    @Override
    public int compareTo(ModeloDocente otroDocente) {
        if (this.duiDocente == null || otroDocente.getDuiDocente() == null) {
            return 0;
        }
        return this.duiDocente.compareTo(otroDocente.getDuiDocente());
    }


    public String getDuiDocente() { return duiDocente; }
    public void setDuiDocente(String duiDocente) { this.duiDocente = duiDocente; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getSegundoNombre() { return segundoNombre; }
    public void setSegundoNombre(String segundoNombre) { this.segundoNombre = segundoNombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }

    public String getTelefonoDocente() { return telefonoDocente; }
    public void setTelefonoDocente(String telefonoDocente) { this.telefonoDocente = telefonoDocente; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public String getCaserio() { return caserio; }
    public void setCaserio(String caserio) { this.caserio = caserio; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }

    public int getNumeroCasa() { return numeroCasa; }
    public void setNumeroCasa(int numeroCasa) { this.numeroCasa = numeroCasa; }

    public ArrayList<ModeloSeccion> getSeccion() { return seccion; }
    public void setSeccion(ArrayList<ModeloSeccion> seccion) { this.seccion = seccion; }

    public ArrayList<ModeloTelefonosDocente> getTelefonoDocentes() { return telefonoDocentes; }
    public void setTelefonoDocentes(ArrayList<ModeloTelefonosDocente> telefonoDocentes) { this.telefonoDocentes = telefonoDocentes; }

    public ArrayList<ModeloCorreoDocente> getCorreoDocentes() { return correoDocentes; }
    public void setCorreoDocentes(ArrayList<ModeloCorreoDocente> correoDocentes) { this.correoDocentes = correoDocentes; }

    public ArrayList<ModeloMovimientoConducta> getMovementoConducta() { return movimientoConducta; }
    public void setMovimientoConducta(ArrayList<ModeloMovimientoConducta> movimientoConducta) { this.movimientoConducta = movimientoConducta; }

    public ModeloCargoDocente getCargoDocente() { return cargoDocente; }
    public void setCargoDocente(ModeloCargoDocente cargoDocente) { this.cargoDocente = cargoDocente; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario;  }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

 
}