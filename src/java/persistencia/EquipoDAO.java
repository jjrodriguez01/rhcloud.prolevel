/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import modelo.EquipoDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilidades.MiExcepcion;

public class EquipoDAO {
    //instanciamos preparestatment
    PreparedStatement statement;
    //variable que devuelve el metodo con el mensaje
    String mensaje = "";
    //variable que cuenta las filas afectadas
    int rtdo = 0;

    ResultSet rs;

    public synchronized String insertar(String nombre, Connection conexion) {
        try {
            //sentencia sql
            String sql = "INSERT INTO equipo(codigo,nombre) VALUES(null,?);";
            //pasamos la sentencia la conexion mediante el prepare staement
            statement = conexion.prepareStatement(sql);
            //obtenemos los datos del dto de la tabla
            statement.setString(1, nombre);            

            //ejecuta el insert
            rtdo = statement.executeUpdate();
            //si se afectaron campos 
            if (rtdo != 0) {
                mensaje = "Se inserto el equipo";
                //si no se afecto la tabla
            } else {
                mensaje = "Error";
            }
        } 
        catch (SQLException sqlexception) {
            mensaje = "Ha ocurrido un error "+ sqlexception.getMessage();
        }
//        finally{
//            if (null != conexion) {
//                try {
//                    if (statement != null) {
//                        statement.close();    
//                    }
//                } catch (SQLException sqlexception) {
//                    mensaje = "Error :" + sqlexception.getMessage();
//                }
//        }
//        }
        //devolvemos el mensaje al usuario
        return mensaje;
    }

    public String actualizar(EquipoDTO eq, Connection conexion) {
        try {
            //preparamos la sentencia sql
            String sql = "UPDATE equipo SET nombre=? WHERE codigo=?;";
            //pasamos el query a la conexion
           //sacamos los datos del dto de la tabla
            statement = conexion.prepareStatement(sql);
            statement.setString(1, eq.getNombre());
            statement.setInt(2, eq.getCodigo());
            //el resulset trae el numero de rows afectadas
            rtdo = statement.executeUpdate();
            if (rtdo != 0) {

              mensaje="Se ha modificado el equipo";

            } else {
                mensaje = "Error";
            }
        } catch (SQLException sqlexception) {
            mensaje = "Ha ocurrido un error "+ sqlexception.getMessage();
        }
//        finally{
//            if (null != conexion) {
//                try {
//                    if (statement != null) {
//                        statement.close();    
//                    }
//                } catch (SQLException sqlexception) {
//                    mensaje = "Error :" + sqlexception.getMessage();
//                }
//        }
//        }

        return mensaje;

    }

    public String eliminar(int equipo, Connection conexion) {
        try {
            statement = conexion.prepareStatement("Delete from equipo where codigo=?;");
            //obtenemos el id del item a eliminar del dto
            statement.setInt(1, equipo);
            rtdo = statement.executeUpdate();

            if (rtdo != 0) {
                mensaje="el equipo fue eliminado satisfactoriamente";
            } else {
                mensaje = "Ocurrio Un Error";
            }
        } catch (SQLException sqlexception) {
            mensaje = "Ha ocurrido un error "+ sqlexception.getMessage();

        }
//        finally{
//            if (null != conexion) {
//                try {
//                    if (statement != null) {
//                        statement.close();    
//                    }
//                } catch (SQLException sqlexception) {
//                    mensaje = "Error :" + sqlexception.getMessage();
//                }
//        }
//        }

        return mensaje;
    }

    public List<EquipoDTO> listarTodo(Connection conexion) throws MiExcepcion {
        //creamos el array que va a contener los datos de la consulta    
        LinkedList<EquipoDTO> equipos = new LinkedList();

        try {
            String query = "SELECT  codigo, nombre as Equipo"
                    + " FROM equipo;";
            statement = conexion.prepareStatement(query);
            rs = statement.executeQuery();
            //mientras que halla registros cree un nuevo dto y pasele la info
            while (rs.next()) {
                //crea un nuevo dto
                EquipoDTO eq = new EquipoDTO();
                //le pasamos los datos que se encuentren
                eq.setCodigo(rs.getInt("codigo"));
                eq.setNombre(rs.getString("Equipo"));
               
                //agregamos el objeto dto al arreglo
                equipos.add(eq);

            }
        } catch (SQLException sqlexception) {
           throw new MiExcepcion("Error ", sqlexception);

        } 
//        finally {
//            if (statement != null) {
//                        statement.close();    
//                    }
//        }
        //devolvemos el arreglo
        return equipos;
    }
        public List<EquipoDTO> listarTodoTorneo(int torneo, Connection conexion) throws MiExcepcion {
        //creamos el array que va a contener los datos de la consulta    
        LinkedList<EquipoDTO> equipos = new LinkedList();

        try {
            statement = conexion.prepareStatement("SELECT  e.codigo, e.nombre as Equipo"
                    + " FROM equipo as e"
                    + " inner join equiposdeltorneo as et "
                    + " on e.codigo = et.equipoCodigo"
                    + " where et.torneoIdTorneo=?");
            statement.setInt(1, torneo);
            rs = statement.executeQuery();
            //mientras que halla registros cree un nuevo dto y pasele la info
            while (rs.next()) {
                //crea un nuevo dto
                EquipoDTO eq = new EquipoDTO();
                //le pasamos los datos que se encuentren
                eq.setCodigo(rs.getInt("codigo"));
                eq.setNombre(rs.getString("Equipo"));
               
                //agregamos el objeto dto al arreglo
                equipos.add(eq);

            }
        } catch (SQLException sqlexception) {
           throw new MiExcepcion("Error ", sqlexception);

        }
//        finally{
//            if (null != conexion) {
//                try {
//                    if (statement != null) {
//                        statement.close();    
//                    }
//                } catch (SQLException sqlexception) {
//                    throw new MiExcepcion("Error al listar las copas", sqlexception);
//                }
//        }
//        }
        //devolvemos el arreglo
        return equipos;
        }

    public EquipoDTO listarUno(int codigo, Connection conexion) throws MiExcepcion {
        EquipoDTO equ = new EquipoDTO();
        try {
            //preparamos la consulta 
            statement = conexion.prepareStatement("SELECT codigo, "
                    + "nombre FROM equipo "
                    + "WHERE codigo = ? ;");
            statement.setInt(1, codigo);
            rs = statement.executeQuery();
            //mientras halla registros
            while (rs.next()) {
                equ.setCodigo(rs.getInt("codigo"));
                equ.setNombre(rs.getString("nombre"));
            }

        } catch (SQLException ex) {
            throw new MiExcepcion("Error ", ex);
        }
//        finally{
//                try {
//                    if (statement != null) {
//                        statement.close();    
//                    }
//                } catch (SQLException sqlexception) {
//                    throw new MiExcepcion("Error al listar las copas", sqlexception);
//                }
//        }
        return equ;
    }
    
    public int existeEquipo(String nombre, Connection conexion) throws MiExcepcion{
        int codigo = 0;
         try {
             statement = conexion.prepareStatement("SELECT codigo FROM equipo Where nombre= ?;");
             statement.setString(1, nombre);
             rs = statement.executeQuery();
             while (rs.next()) {
                 codigo = rs.getInt("codigo");
             }
         } catch (SQLException sqle) {
             throw new MiExcepcion("Error ", sqle);
         }
//         finally{
//                try {
//                    if (statement != null) {
//                        statement.close();    
//                    }
//                } catch (SQLException sqlexception) {
//                    throw new MiExcepcion("Error al listar las copas", sqlexception);
//                }
//        }
        return codigo;
    }
    
    

}


