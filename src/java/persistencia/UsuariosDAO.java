/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;
import modelo.UsuariosDTO;
import static controlador.seguridad.Encriptacion.encriptar;
import static controlador.seguridad.Encriptacion.desencriptar;
import facade.FachadaUsuarios;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import modelo.RolUsuarioDTO;
import utilidades.MiExcepcion;
public class UsuariosDAO {

    //instanciamos preparestatment
    private PreparedStatement statement;
    //variable que devuelve el metodo con el mensaje
    private String mensaje = "";
    //variable que cuenta las filas afectadas
    private int rtdo = 0;
    //result set 
    private ResultSet rs;
    //llave
    /**
     * Inserta un usuario en la bd
     *
     * @param  usu
     *         un objeto usuariosDTO con los datos a insetat
     * @throws  MiException
     *          Excepcion peersonalizada
     */
    public synchronized String insertar(UsuariosDTO usu, Connection conexion) throws MiExcepcion{
        try {
            //sentencia sql
            String sql = "INSERT INTO usuarios(idUsuario,primerNombre, "
                    + "segundoNombre,primerApellido,segundoApellido,fechaNac,telefono,email,contrasena) "
                    + "VALUES(?,?,?,?,?,?,?,?,?);";
            //pasamos la sentencia la conexion mediante el prepare staement
            statement = conexion.prepareStatement(sql);
            //obtenemos los datos del dto de la tabla
            statement.setLong(1,usu.getIdUsuario());
            statement.setString(2, usu.getPrimerNombre());
            statement.setString(3, usu.getSegundoNombre());
            statement.setString(4, usu.getPrimerApellido());
            statement.setString(5, usu.getSegundoApellido());
            statement.setString(6, usu.getFecha());
            statement.setString(7, usu.getTelefono());
            statement.setString(8, usu.getEmail());
            byte[] passcript = encriptar(usu.getContraseña());
            statement.setBytes(9, passcript);

            //ejecuta el insert
            rtdo = statement.executeUpdate();
            //si se afectaron campos 
            if (rtdo != 0) {
                mensaje = "se inserto el usaurio correctamente";
                //si no se afecto la tabla
            } 
        } 
        catch (Exception ex) {
            throw new MiExcepcion("Error insertando usuario"+ex.getMessage(), ex);
        }
        
//        finally{
//            try {
//                statement.close();
//            } catch (SQLException ex) {
//                throw new MiExcepcion("Ha ocorrido in error cerrando el statement", ex);
//            }
//        }
        //devolvemos el mensaje al usuario
        return mensaje;
    }
    
    public String actualizar(UsuariosDTO usu, Connection conexion) throws MiExcepcion {
        try {
            //preparamos la sentencia sql
            String sql = "UPDATE usuarios SET primerNombre=?, "
                    + "segundoNombre=?,primerApellido=?, "
                    + "segundoApellido=?,telefono=?,email=?,contrasena=? WHERE idUsuario=?;";
            //pasamos el query a la conexion
           //sacamos los datos del dto de la tabla
            statement = conexion.prepareStatement(sql);
            statement.setString(1, usu.getPrimerNombre());
            statement.setString(2, usu.getSegundoNombre());
            statement.setString(3, usu.getPrimerApellido());
            statement.setString(4, usu.getSegundoApellido());
            statement.setString(5, usu.getTelefono());
            statement.setString(6, usu.getEmail());
            byte[] pass = encriptar(usu.getContraseña());
            statement.setBytes(7, pass);
            statement.setLong(8, usu.getIdUsuario());
            //el resulset trae el numero de rows afectadas
            rtdo = statement.executeUpdate();
            if (rtdo != 0) {
                mensaje = "Se han modificado los datos";
            } else {
                mensaje = "Error";
            }
        } catch (SQLException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException ex) {
            throw new MiExcepcion("Error actualizando usuario "+ex.getMessage(), ex);
        }
        return mensaje;
    }

    public String eliminar(Long id, Connection conexion) throws MiExcepcion {
        try {
            statement = conexion.prepareStatement("Delete from usuarios where idUsuario=?;");
            //obtenemos el id del item a eliminar del dto
            statement.setLong(1, id);
            rtdo = statement.executeUpdate();

            if (rtdo != 0) {
                mensaje = ("Usuario eliminado satisfactoriamente");
            } else {
                mensaje = "Ocurrio Un Error";
            }
        } catch (SQLException sqlexception) {
            throw new MiExcepcion("Error eliminando usuario", sqlexception);
        }
        return mensaje;
    }
    
    public List<UsuariosDTO> listarTodo(Connection conexion) throws MiExcepcion {
        //creamos el array que va a contener los datos de la consulta    
        ArrayList<UsuariosDTO> listarUsuarios = new ArrayList();

        try {
            statement = conexion.prepareStatement("SELECT * FROM usuarios;");
            rs = statement.executeQuery();
            if(rs != null){            //mientras que halla registros cree un nuevo dto y pasele la info
            while (rs.next()) {
                //crea un nuevo dto
                UsuariosDTO usu = new UsuariosDTO();
                //le pasamos los datos que se encuentren
                usu.setIdUsuario(rs.getLong("idUsuario"));
                usu.setPrimerNombre(rs.getString("primerNombre"));
                usu.setSegundoNombre(rs.getString("segundoNombre"));
                usu.setPrimerApellido(rs.getString("primerApellido"));
                usu.setSegundoApellido(rs.getString("segundoApellido"));
                usu.setFecha(rs.getString("fechaNac"));
                usu.setEmail(rs.getString("email"));
                usu.setTelefono(rs.getString("telefono"));
                usu.setContraseña(rs.getString("contrasena"));
                //agregamos el objeto dto al arreglo
                listarUsuarios.add(usu);
            }
        }
        } catch (SQLException sqlexception) {
            throw new MiExcepcion("Error sql", sqlexception);

        } 
//        finally {
//            try {
//                statement.close();
//            } catch (SQLException ex) {
//                throw new MiExcepcion("Error cerrando prepare",ex);
//            }
//        }
        //devolvemos el arreglo
        return listarUsuarios;
    }
    
     public List<UsuariosDTO> listarUsuariosRol(Connection conexion) throws MiExcepcion {
        //creamos el array que va a contener los datos de la consulta    
        ArrayList<UsuariosDTO> listarUsuarios = new ArrayList();

        try {
            statement = conexion.prepareStatement("SELECT * FROM usuarios " +
                        "inner join rol_usuario on " +
                        "usuarios.idusuario = rol_usuario.usuarioidusuario;");
            rs = statement.executeQuery();
            if(rs != null){            //mientras que halla registros cree un nuevo dto y pasele la info
            while (rs.next()) {
                //crea un nuevo dto
                UsuariosDTO usu = new UsuariosDTO();
                //le pasamos los datos que se encuentren
                usu.setIdUsuario(rs.getLong("idUsuario"));
                usu.setPrimerNombre(rs.getString("primerNombre"));
                usu.setSegundoNombre(rs.getString("segundoNombre"));
                usu.setPrimerApellido(rs.getString("primerApellido"));
                usu.setSegundoApellido(rs.getString("segundoApellido"));
                usu.setFecha(rs.getString("fechaNac"));
                usu.setEmail(rs.getString("email"));
                usu.setTelefono(rs.getString("telefono"));
                usu.setContraseña(rs.getString("contrasena"));
                RolUsuarioDTO rol = new RolUsuarioDTO();
                rol.setRolesidRol(rs.getInt("rolesIdRol"));
                rol.setUsuarioIdUsuario(rs.getLong("usuarioIdUsuario"));
                usu.setRol(rol);
                //agregamos el objeto dto al arreglo
                listarUsuarios.add(usu);
            }
        }
        } catch (SQLException sqlexception) {
            throw new MiExcepcion("Error sql"+sqlexception.getMessage(), sqlexception);

        } 
//        finally {
//            try {
//                statement.close();
//            } catch (SQLException ex) {
//                throw new MiExcepcion("Error cerrando prepare",ex);
//            }
//        }
        //devolvemos el arreglo
        return listarUsuarios;
    }
    /**
     * Lista un usuario en la bd
     *
     * @param  id
     *         documento del usaurio
     * @throws  MiExcepcion
     *          Excepcion personalizada
     * @return objeto UsuarioDTO con los datos existentes o null si no se encontro con ese id
     */
    public synchronized UsuariosDTO listarUno(Long id, Connection conexion)throws MiExcepcion {
        UsuariosDTO usuario = new UsuariosDTO();
        try {
            //preparamos la consulta 
            statement = conexion.prepareStatement("SELECT idUsuario,"
                    + "primerNombre, segundoNombre,primerApellido,segundoApellido,"
                    + "fechaNac,telefono,email,contrasena FROM usuarios "
                    + "Where idUsuario = ?;");
            statement.setLong(1, id);
            rs = statement.executeQuery();
            //mientras halla registros
            if (rs!=null) {
            while (rs.next()) {
                usuario.setIdUsuario(rs.getLong("idUsuario"));
                usuario.setPrimerNombre(rs.getString("primerNombre"));
                usuario.setSegundoNombre(rs.getString("segundoNombre"));
                usuario.setPrimerApellido(rs.getString("primerApellido"));
                usuario.setSegundoApellido(rs.getString("segundoApellido"));
                usuario.setFecha(rs.getString("fechaNac"));
                usuario.setEmail(rs.getString("email"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setContraseña(rs.getString("contrasena")); 
            }
            }
            //devolvemos el usuario que se encontro
        return usuario;
        } catch (SQLException ex) {
            throw new MiExcepcion ("Error inesperado al obtener usuario", ex);
        }
        finally{
            try{
                statement.close();
                rs.close();
//                if (conexion != null) {
//                    conexion.close();
//                }
            }catch(SQLException sqlexception){
                throw new MiExcepcion("Error sql", sqlexception);
            }
        }
        
    }
    /**
     * Valida si el usuario existe en la base de datos
     * @param  email
     *         String con el correo
     *
     * @param  password
     *         contraseña del usuario
     */
        public synchronized long validarUsuario(String email, String password, Connection conexion) throws MiExcepcion {
        long cc = 0;
        try {
            statement = conexion.prepareStatement("SELECT idUsuario "
                    + "from usuarios where email=? and contrasena=?;");
            byte[] passwordbd = encriptar(password);
            statement.setString(1, email);
            statement.setBytes(2, passwordbd);
            rs = statement.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                cc = rs.getLong("idUsuario");
                }     
            }
        return cc;
        } catch (SQLException sqle) {
           throw new MiExcepcion("Ha ocurrido un error", sqle);
        }catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e){
           throw new MiExcepcion("Ha ocorrido in error encriptando su contraseña. Por favor intentelo de nuevo.",e);
        }
        finally {
            try {
                statement.close();
                rs.close();
//                if (conexion != null) {
//                    conexion.close();
//                }
            } catch (SQLException ex) {
                throw new MiExcepcion("Ha ocurrido un error", ex);
            }
        }
        
    }
    /**
     * Valida que el rol sea de administrador.
     *
     * @param  usu
     *         un objeto UsuariosDTO
     *
     * @throws MiExcepcion
     *          excepcion personalizada
     */
    public synchronized boolean ValidarRol(UsuariosDTO usu, Connection conexion)throws MiExcepcion{
    boolean logeado=false;
    try{
      statement = conexion.prepareStatement("select * from usuarios as u  inner join rol_usuario as r"
        +" on u.idUsuario=r.usuarioIdUsuario where u.idusuario = ? and r.rolesIdRol=1;");
      statement.setLong(1, usu.getIdUsuario());
            rs = statement.executeQuery();
            usu.setIdUsuario(rs.getLong("idUsuario"));
            usu.setPrimerNombre(rs.getString("primerNombre"));
            usu.setSegundoNombre(rs.getString("segundoNombre"));
            usu.setPrimerApellido(rs.getString("primerApellido"));
            usu.setSegundoApellido(rs.getString("segundoApellido"));
            usu.setFecha(rs.getString("fechaNac"));
            usu.setEmail(rs.getString("email"));
            usu.setTelefono(rs.getString("telefono"));
            usu.setContraseña(rs.getString("contrasena"));
    if(usu!=null){
    return logeado=true;
    }
    }catch(SQLException sqle){
        throw new MiExcepcion("Ha ocurrido un error ", sqle);
    }
//    finally{
//            try{
//                statement.close();
//            }catch(SQLException sqlexception){
//                throw new MiExcepcion("Error sql", sqlexception);
//            }
//        }
    return logeado; 
    }
    
    public String recuperar (String email, Connection conexion) throws MiExcepcion{
        byte[] password; 
        String reestablecida = "";
        try{
            statement = conexion.prepareStatement("SELECT contrasena FROM usuarios "
                    + "WHERE email =?;");
        statement.setString(1,email);
        rs = statement.executeQuery();
            while (rs.next()) {
                password = rs.getBytes("contrasena");
                reestablecida = desencriptar(password);
            }
           
    }catch(SQLException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException  ex){
        throw new MiExcepcion("Error recuperando contraseña", ex);
    }
//        finally{
//            try{
//                statement.close();
//            }catch(SQLException sqlexception){
//                throw new MiExcepcion("Error sql", sqlexception);
//            }
//        }
        
        return reestablecida;
    }
    
    public synchronized String cambiarPass(long id, String newpass, Connection conexion) throws MiExcepcion{
        try {
            statement =conexion.prepareStatement("UPDATE usuarios SET contrasena = ? "
                    + "WHERE idUsuario = ?;");
            byte[] pass = encriptar(newpass);
            statement.setBytes(1, pass);
            statement.setLong(2, id);
            rtdo = statement.executeUpdate();
            if (rtdo > 0) {
                mensaje = "Se cambió la contraseña";
            }
        } catch (SQLException ex) {
            throw new MiExcepcion("Error cambiando contraseña", ex);
        }catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e){
            throw new MiExcepcion("Error cambiando contraseña", e);
        }
//        finally{
//            try{
//                statement.close();
//            }catch(SQLException sqlexception){
//                throw new MiExcepcion("Error sql", sqlexception);
//            }
//        }
        return mensaje;
    }
    
    public StringBuilder validarDocumento(long cc,int idTorneo, Connection conexion)throws MiExcepcion{
        StringBuilder salida = new StringBuilder("");
        FachadaUsuarios facade = new FachadaUsuarios();
        boolean existe = facade.existeUsuario(cc);
        try {
            statement = conexion.prepareStatement("SELECT usuarios.idUsuario, equiposdeltorneo.equipoCodigo "
                    + "FROM usuarios " 
                    + "inner join jugadoresporequipo on "
                    +"usuarios.idUsuario = jugadoresporequipo.codigoJugador " 
                    +"inner join equipo on " 
                    +"equipo.codigo = jugadoresporequipo.codigoEquipo "
                    +"inner join equiposdeltorneo on "
                    +"equipo.codigo = equiposdeltorneo.equipoCodigo " 
                    +"WHERE idusuario = ? and equiposdeltorneo.torneoIdTorneo = ?;");
            statement.setLong(1, cc);
            statement.setInt(2, idTorneo);
            rs = statement.executeQuery();
            if (rs.next() || existe == false) {
                salida.append("Este jugador no esta registrado o ya se encuentra inscrito a un equipo en este torneo");
            }else{
                
                salida.append("El usuario ").append(cc).append(" está habilitado para la inscripción.");
            }
        } catch (SQLException sqle) {
            throw new MiExcepcion("Error ", sqle); 
        }
//        finally{
//            try{
//                statement.close();
//                rs.close();
//                if (conexion != null) {
//                    conexion.close();
//                }
//            }catch(SQLException sqlexception){
//                throw new MiExcepcion("Error sql", sqlexception);
//            }
//        }
        return salida;
    }
    
    public synchronized boolean siEstaRegistrado(long idUsuario,Connection conexion)throws MiExcepcion{
    boolean existe=false;
    
    try{
      statement = conexion.prepareStatement("select idUsuario from usuarios "
              + "where idUsuario = ?;");
      statement.setLong(1, idUsuario);
            rs = statement.executeQuery();
            while(rs.next()){
            return existe=true;
            }
    }catch(SQLException sqle){
        throw new MiExcepcion("Ha ocurrido un error "+sqle.getMessage(), sqle);
    }
//    finally{
//            try{
//                if(conexion !=null){
//                    conexion.close();
//                }
//            }catch(SQLException sqlexception){
//                throw new MiExcepcion("Error sql", sqlexception);
//            }
//        }
    return existe; 
    }
}