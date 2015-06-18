/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import modelo.RolpermisoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;
public class RolpermisoDAO {

    //instanciamos preparestatment
    PreparedStatement statement;
    //variable que devuelve el metodo con el mensaje
    String mensaje = "";
    //variable que cuenta las filas afectadas
    int rtdo = 0;

    ResultSet rs;


    public String insertar (RolpermisoDTO ins, Connection conexion) {

        try {
            //sentencia sql
            String sql = "INSERT INTO Rolpermiso(idRol,idPermiso,nombre)VALUES(?,?,?)";
            //pasamos la sentencia la conexion mediante el prepare staement
            statement = conexion.prepareStatement(sql);
            //obtenemos los datos del dto de la tabla
            statement.setInt(1, ins.getIdRol());
            statement.setInt(2, ins.getIdPermiso());
            statement.setString(3, ins.getNombre());
            

            //ejecuta el insert
            rtdo = statement.executeUpdate();
            //si se afectaron campos 
            if (rtdo != 0) {
                System.out.println("se insertaron:" + rtdo + "Datos");
                //si no se afecto la tabla
            } else {
                mensaje = "Error";
            }
        } 
        catch (SQLException sqlexception) {
            mensaje = "Ha ocurrido un error "+ sqlexception.getMessage();
        }
        //devolvemos el mensaje al usuario
        return mensaje;
    }

    public String modificar(RolpermisoDTO ins, Connection conexion) {
        try {
            //preparamos la sentencia sql
            String sql = "UPDATE Rolpermiso SET idRol=?,IdPermiso=?,Nombre=?";
            //pasamos el query a la conexion
           //sacamos los datos del dto de la tabla
            statement = conexion.prepareStatement(sql);
            statement.setInt(2, ins.getIdRol());
            statement.setInt(3, ins.getIdPermiso());
            statement.setString(4, ins.getNombre());
            
            //el resulset trae el numero de rows afectadas
            rtdo = statement.executeUpdate();
            if (rtdo != 0) {

              mensaje="El Campo Se a modificado:" + rtdo + "saludes";

            } else {
                mensaje = "Error";
            }
        } catch (SQLException sqlexception) {
            mensaje = "Ha ocurrido un error "+ sqlexception.getMessage();

        }

        return mensaje;

    }

    public String eliminar(RolpermisoDTO usu, Connection conexion) {
        try {
            statement = conexion.prepareStatement("Delete from Rolpermiso where idRol=?;");
            //obtenemos el id del item a eliminar del dto
            statement.setInt(1, usu.getIdRol());
            rtdo = statement.executeUpdate();

            if (rtdo != 0) {
                mensaje="El siguiente campo" + rtdo + "se elimino Corretamente";
            } else {
                mensaje = "Ocurrio Un Error";
            }
        } catch (SQLException sqlexception) {
            mensaje = "Ha ocurrido un error "+ sqlexception.getMessage();

        }

        return mensaje;
    }

    public List<RolpermisoDTO> listarTodo(Connection conexion) {
        //creamos el array que va a contener los datos de la consulta    
        ArrayList<RolpermisoDTO> listarUsuarios = new ArrayList();

        try {
            String query = "SELECT Rolpermiso as idRol, idPermiso, nombre "
                    + " FROM Rolpermiso ";
            statement = conexion.prepareStatement(query);
            rs = statement.executeQuery();
            //mientras que halla registros cree un nuevo dto y pasele la info
            while (rs.next()) {
                //crea un nuevo dto
                RolpermisoDTO usu = new RolpermisoDTO();
                //le pasamos los datos que se encuentren
                usu.setIdRol(rs.getInt("Rol"));
                usu.setIdPermiso(rs.getInt("Permiso"));
                usu.setNombre(rs.getString("nombre"));
               
                //agregamos el objeto dto al arreglo
                listarUsuarios.add(usu);

            }
        } catch (SQLException sqlexception) {
           mensaje = "Ha ocurrido un error "+ sqlexception.getMessage();

        } finally {

        }
        //devolvemos el arreglo
        return listarUsuarios;

    }

    public String listarUno(RolpermisoDTO usuario, Connection conexion) {
        try {
            //preparamos la consulta 
            statement = conexion.prepareStatement("SELECT idRol,"
                    + "idPermiso,Nombre FROM Rolpermiso "
                    + "WHERE idRol = ? ;");
            statement.setInt(1, usuario.getIdRol());
            rs = statement.executeQuery();
            //mientras halla registros
            while (rs.next()) {
                usuario.setIdRol(rs.getInt("Rol"));
                usuario.setIdPermiso(rs.getInt("idPermiso"));
                usuario.setNombre(rs.getString("Nombre"));
                
            }

        } catch (SQLException ex) {
            mensaje = "Error inesperado: " + ex.getMessage() + " codigo de error " + ex.getErrorCode();
        }
        //devolvemos el usuario que se encontro
        return "" + usuario;
    }

} 
