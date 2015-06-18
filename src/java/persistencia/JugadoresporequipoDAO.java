/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import modelo.JugadoresporequipoDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import utilidades.MiExcepcion;

public class JugadoresporequipoDAO {

    //instanciamos preparestatment
    PreparedStatement statement;
    //variable que devuelve el metodo con el mensaje
    String mensaje = "";
    //variable que cuenta las filas afectadas
    int rtdo = 0;

    ResultSet rs;

    public synchronized String insertar(int equipo, long jugador, Connection conexion) throws MiExcepcion {

        try {
            //sentencia sql
            String sql = "INSERT INTO jugadoresporequipo(codigoEquipo, codigoJugador) VALUES(?,?);";
            //pasamos la sentencia la conexion mediante el prepare staement
            statement = conexion.prepareStatement(sql);
            //obtenemos los datos del dto de la tabla
            statement.setInt(1, equipo);
            statement.setLong(2, jugador);
            
            //ejecuta el insert
            rtdo = statement.executeUpdate();
            //si se afectaron campos 
            if (rtdo != 0) {
                mensaje = "Se inserto el jugador";
                //si no se afecto la tabla
            } else {
                mensaje = "Error no se insertó el jugador";
            }
        } 
        catch (SQLException sqlexception) {
            throw new MiExcepcion("Error insertando jugadores "+sqlexception.getMessage(), sqlexception);
        }
//        finally{
//            try {
//                statement.close();
//            } catch (SQLException sqlexception) {
//                throw new MiExcepcion("Error insertando jugadores", sqlexception);
//            }
//        }
        //devolvemos el mensaje al usuario
        return mensaje;
    }

    public String actualizar(int equipo, long jugador, Connection conexion) throws MiExcepcion {
        try {
            //preparamos la sentencia sql
            String sql = "UPDATE Jugadoresporequipo SET "
                    + " codigoEquipo=?,codigoJugador=? "
                    + "WHERE codigoEquipo=? and codigoJugador = ?;";
            //pasamos el query a la conexion
           //sacamos los datos del dto de la tabla
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, equipo);
            statement.setLong(2, jugador);
            statement.setInt(3, equipo);
            statement.setLong(4, jugador);
            //el resulset trae el numero de rows afectadas
            rtdo = statement.executeUpdate();
            if (rtdo != 0) {

                mensaje="El Campo Se a modificado:" + rtdo + "saludes";

            } else {
                mensaje = "Error";
            }
        } catch (SQLException sqlexception) {
            throw new MiExcepcion("Error", sqlexception);

        }
//        finally{
//            try {
//                statement.close();
//            } catch (SQLException sqlexception) {
//                throw new MiExcepcion("Error actualizando jugadores", sqlexception);
//            }
//        }

        return mensaje;

    }

    public String eliminar(int equipo, Connection conexion) throws MiExcepcion {
        try {
            statement = conexion.prepareStatement("delete from jugadoresporequipo where codigoEquipo = ?;");
            //obtenemos el id del item a eliminar del dto
            statement.setInt(1, equipo);
            rtdo = statement.executeUpdate();

            if (rtdo != 0) {
                mensaje = "Se eliminaron los jugadores";
            } else {
                mensaje = "Ocurrio Un Error";
            }
        } catch (SQLException sqlexception) {
            throw new MiExcepcion("Error", sqlexception);

        }
//        finally{
//            try {
//                statement.close();
//            } catch (SQLException sqlexception) {
//                throw new MiExcepcion("Error eliminando jugadores", sqlexception);
//            }
//        }

        return mensaje;
    }
    
    public String eliminarJugador(long idUsuario,int codigoEquipo, Connection conexion) throws MiExcepcion {
        try {
            statement = conexion.prepareStatement("delete from jugadoresporequipo "
                    + "where codigoEquipo = ? and codigoJugador = ?;");
            //obtenemos el id del item a eliminar del dto
            statement.setInt(1, codigoEquipo);
            statement.setLong(2, idUsuario);
            rtdo = statement.executeUpdate();

            if (rtdo != 0) {
                mensaje = "Se elimino el jugador";
            } else {
                mensaje = "Ocurrio Un Error";
            }
        } catch (SQLException sqlexception) {
            throw new MiExcepcion("Error eliminando jugador "+sqlexception.getMessage(), sqlexception);

        }
//        finally{
//            try {
//                statement.close();
//            } catch (SQLException sqlexception) {
//                throw new MiExcepcion("Error eliminando jugadores", sqlexception);
//            }
//        }

        return mensaje;
    }

    public List<JugadoresporequipoDTO> listarTodo(Connection conexion) throws MiExcepcion {
        //creamos el array que va a contener los datos de la consulta    
        LinkedList<JugadoresporequipoDTO> jugadoreseq = new LinkedList();

        try {

            statement = conexion.prepareStatement("select "
                    + "concat(u.primerNombre, ' ',u.primerApellido)as jugador,e.nombre as equipo"
                    + " from usuarios as u inner join jugadoresporequipo as j  on " +
                       " u.idUsuario = j.codigoJugador inner join equipo as e "
                    + " on j.codigoEquipo=e.codigo ;");
            rs = statement.executeQuery();
            //mientras que halla registros cree un nuevo dto y pasele la info
            while (rs.next()) {
                //crea un nuevo dto
                JugadoresporequipoDTO jug = new JugadoresporequipoDTO();
                //le pasamos los datos que se encuentren
                jug.setNombreEquipo(rs.getString("equipo"));
                jug.setNombreJugador(rs.getString("jugador"));      
                //agregamos el objeto dto al arreglo
                jugadoreseq.add(jug);

            }
        } catch (SQLException sqle) {
            throw new MiExcepcion("Error", sqle);

        }
//        finally{
//            try {
//                statement.close();
//            } catch (SQLException sqlexception) {
//                throw new MiExcepcion("Error listando jugadores", sqlexception);
//            }
//        }
        //devolvemos el arreglo
        return jugadoreseq;

    }

    public List<JugadoresporequipoDTO> listarUno(int equipo, long jugador, Connection conexion) throws MiExcepcion {
         LinkedList<JugadoresporequipoDTO> jugadoreseq = new LinkedList();
        try {
            statement = conexion.prepareStatement("SELECT codigoEquipo,codigoJugador "
                    + " FROM Jugadoresporequipo "
                    + "where codigoEquipo=? and codigoJugador=?");
                 
            statement.setInt(1, equipo);
            statement.setLong(2, jugador);
            rs = statement.executeQuery();
            //mientras halla registros
            while (rs.next()) {
                JugadoresporequipoDTO jugadoreq = new JugadoresporequipoDTO();
                jugadoreq.setNombreEquipo(rs.getString("CodigoEquipo"));
                jugadoreq.setNombreJugador(rs.getString("codigoJugador"));
                jugadoreseq.add(jugadoreq);
            }

        } catch (SQLException ex) {
            throw new MiExcepcion("Error", ex);
        }
//        finally{
//            try {
//                statement.close();
//            } catch (SQLException sqlexception) {
//                throw new MiExcepcion("Error listando jugadores", sqlexception);
//            }
//        }
        //devolvemos el usuario que se encontro
        return jugadoreseq;
    }
    public List<JugadoresporequipoDTO> listarJugadoresEq(int equipo, Connection conexion) throws MiExcepcion {
        //creamos el array que va a contener los datos de la consulta    
        LinkedList<JugadoresporequipoDTO> jugadoreseq = new LinkedList();

        try {

            statement = conexion.prepareStatement("select "
                + "concat(u.primerNombre, ' ',u.primerApellido)as jugador, "
                    + "u.idUsuario, e.nombre as equipo, e.codigo "
                + " from usuarios as u inner join jugadoresporequipo as j  on " 
                + "u.idUsuario = j.codigoJugador inner join equipo as e "
                + "on j.codigoEquipo=e.codigo where e.codigo=?;");
            statement.setInt(1, equipo);
            rs = statement.executeQuery();
            //mientras que halla registros cree un nuevo dto y pasele la info
            while (rs.next()) {
                //crea un nuevo dto
                JugadoresporequipoDTO jug = new JugadoresporequipoDTO();
                //le pasamos los datos que se encuentren
                jug.setNombreJugador(rs.getString("jugador"));
                jug.setCodigoJugador(rs.getInt("idUsuario"));
                jug.setNombreEquipo(rs.getString("equipo"));
                jug.setCodigoEquipo(rs.getInt("codigo"));
                //agregamos el objeto dto al arreglo
                jugadoreseq.add(jug);

            }
        } catch (SQLException sqle) {
            throw new MiExcepcion("Error", sqle);

        }
//        finally{
//            try {
//                statement.close();
//            } catch (SQLException sqlexception) {
//                throw new MiExcepcion("Error listando jugadores", sqlexception);
//            }
//        }
        //devolvemos el arreglo
        return jugadoreseq;

    }
    
    public long existeJugador(long idUsuario, Connection conexion) throws MiExcepcion {
        //creamos el array que va a contener los datos de la consulta    
        long id = 0;

        try {

            statement = conexion.prepareStatement("select codigoJugador from jugadoresporequipo "
                    + "where codigoJugador = ?;");
            statement.setLong(1, idUsuario);
            rs = statement.executeQuery();
            //mientras que halla registros cree un nuevo dto y pasele la info
            while (rs.next()) {
                //crea un nuevo dto
                id = rs.getLong("codigoJugador");
            }
        } catch (SQLException sqle) {
            throw new MiExcepcion("Error "+sqle.getMessage(), sqle);

        }
//        finally{
//            try {
//                statement.close();
//            } catch (SQLException sqlexception) {
//                throw new MiExcepcion("Error listando jugadores", sqlexception);
//            }
//        }
        //devolvemos el arreglo
        return id;

    }
}
  

