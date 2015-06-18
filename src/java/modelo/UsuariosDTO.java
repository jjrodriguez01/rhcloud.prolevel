
package modelo;


/**
 *
 * @author jeisson
 */
public class UsuariosDTO {
    
    private Long idUsuario;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String fecha;
    private String telefono;
    private String email;
    private String contraseña;
    private RolUsuarioDTO rol;
    /**
     * @return the idJugador
     */
    public Long getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idJugador the idJugador to set
     */
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the primerNombre
     */
    public String getPrimerNombre() {
        return primerNombre;
    }

    /**
     * @param primerNombre the primerNombre to set
     */
    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    /**
     * @return the segundoNombre
     */
    public String getSegundoNombre() {
        return segundoNombre;
    }

    /**
     * @param segundoNombre the segundoNombre to set
     */
    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    /**
     * @return the primerApellido
     */
    public String getPrimerApellido() {
        return primerApellido;
    }

    /**
     * @param primerApellido the primerApellido to set
     */
    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    /**
     * @return the segundoApellido
     */
    public String getSegundoApellido() {
        return segundoApellido;
    }

    /**
     * @param segundoApellido the segundoApellido to set
     */
    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    /**
     * @return the fecha
     */
    public String getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the contraseña
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * @param contraseña the contraseña to set
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * @return the numeroJugador
     */


    
    
    
     @Override
    public String toString() {
        return "UsuariosDTO \n" 
     + "\n id Jugador=" + idUsuario
     + "\n PrimerNombre=" + primerNombre 
     + "\n segundonombre=" + segundoNombre
     + "\n primerApellido=" + primerApellido
     + "\n segundoApellido=" + segundoApellido
     + "\n fechaNac="+ fecha
     +"\n telefono"+ telefono 
     +"\n email="+ email
     +"\n contraseña ="+contraseña;
    }

    public RolUsuarioDTO getRol() {
        return rol;
    }

    public void setRol(RolUsuarioDTO rol) {
        this.rol = rol;
    }
}

