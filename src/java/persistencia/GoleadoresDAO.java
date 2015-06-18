/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelo.GoleadoresDTO;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import modelo.EquipoDTO;
import modelo.UsuariosDTO;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class GoleadoresDAO {
    
    //instanciamos preparestatment
    PreparedStatement statement;
    //variable que devuelve el metodo con el mensaje
    String mensaje = "";
    //variable que cuenta las filas afectadas
    int rtdo = 0;

    ResultSet rs =null;
    CallableStatement call= null;

    public synchronized String insertar(GoleadoresDTO gol, Connection conexion) throws MiExcepcion {
        try {
            
            call = conexion.prepareCall("{call sp_aumentarmarcador (?,?,?,?,?)};");
                
            call.setLong(1, gol.getIdJugador());
            call.setInt(2, gol.getNumeroGoles());
            call.setInt(3, gol.getIdEquipo());
            call.setInt(4, gol.getIdTorneo());
            
            call.registerOutParameter(5, Types.INTEGER);
            call.execute();
            int salida = call.getInt(5);
            if (salida == 1) {
                mensaje = "Marcador Asignado.";
            }else {
                mensaje = "No se pudo asignar marcador.";
            }
        } 
        catch (SQLException sqlexception) {
         throw new MiExcepcion("Error insertando goles", sqlexception);
        }
        //devolvemos el mensaje al usuario
        return mensaje;
    }
    
    public synchronized String insertarPrimer(GoleadoresDTO gol, Connection conexion) throws MiExcepcion {
        try {
            
           statement = conexion.prepareStatement("INSERT INTO tablagoleadores "
                    + " values(?,?,?,?);");
           
            statement.setInt(1, gol.getNumeroGoles());
            statement.setInt(2, gol.getIdEquipo());
            statement.setLong(3, gol.getIdJugador());
            statement.setInt(4, gol.getIdTorneo());
            
            rtdo = statement.executeUpdate();
            
            if (rtdo > 0) {
                mensaje = "Marcador Asignado.";
            }else {
                mensaje = "No se pudo asignar marcador.";
            
        }
        } 
        catch (SQLException sqlexception) {
         throw new MiExcepcion("Error insertando goles", sqlexception);
        }
        //devolvemos el mensaje al usuario
        return mensaje;
    }

    public String actualizar(GoleadoresDTO gol, Connection conexion) throws MiExcepcion {
        try {
            //preparamos la sentencia sql
            String sql = "UPDATE tablaGoleadores SET numeroGoles=?, idTorneo=?, idJugador=? ,idEquipo=?  WHERE idJugador=? and idTorneo=? and idEquipo=?;";
            //pasamos el query a la conexion
           //sacamos los datos del dto de la tabla
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, gol.getNumeroGoles());
            statement.setInt(2, gol.getIdTorneo());
            statement.setInt(3, gol.getIdEquipo());
            statement.setLong(4, gol.getIdJugador());
           
            
            //el resulset trae el numero de rows afectadas
            rtdo = statement.executeUpdate();
            if (rtdo != 0) {

               mensaje="El Campo Se a modificado:" + rtdo + "saludes";

            } else {
                mensaje = "Error";
            }
        } catch (SQLException sqlexception) {
         throw new MiExcepcion("Error actualizando goles", sqlexception);

        }

        return mensaje;

    }

    public String eliminar(GoleadoresDTO gol, Connection conexion) throws MiExcepcion {
        try {
            statement = conexion.prepareStatement("Delete from tablaGoleadores where idTorneo=? and idJugador and idEquipo;");
            //obtenemos el id del item a eliminar del dto
            statement.setInt(1, gol.getNumeroGoles());
            statement.setLong(2, gol.getIdJugador());
            statement.setInt(3, gol.getIdEquipo());
            statement.setInt(4, gol.getIdTorneo());
            rtdo = statement.executeUpdate();

            if (rtdo != 0) {
               mensaje="El siguiente campo" + rtdo + "se elimino Corretamente";
            } else {
                mensaje = "Ocurrio Un Error";
            }
        } catch (SQLException sqlexception) {
            throw new MiExcepcion("Error eliminando goles", sqlexception);

        }

        return mensaje;
    }

    public List<GoleadoresDTO> listarTodo(int idTorneo,Connection conexion) throws MiExcepcion {
        //creamos el array que va a contener los datos de la consulta    
        ArrayList<GoleadoresDTO> listarGoleadores = new ArrayList();

        try {
            String query = "SELECT DISTINCT usuarios.primerNombre, " +
"    usuarios.primerApellido, tablagoleadores.numeroGoles, tablagoleadores.idEquipo, "+ 
"    tablagoleadores.idTorneo,tablagoleadores.idJugador, " +
"    equipo.nombre " +
"    FROM tablagoleadores " +
"    INNER JOIN jugadoresporequipo " +
"    ON tablagoleadores.idJugador = jugadoresporequipo.codigoJugador " +
"    INNER JOIN usuarios " +
"    ON jugadoresporequipo.codigoJugador = usuarios.idUsuario " +
"    INNER JOIN equiposdeltorneo " +
"    ON tablagoleadores.idEquipo = equiposdeltorneo.equipoCodigo " +
"    INNER JOIN equipo " +
"    ON equiposdeltorneo.equipoCodigo = equipo.codigo " +
"    INNER JOIN torneo " +
"    ON tablagoleadores.idTorneo = torneo.idTorneo " +
"    WHERE tablagoleadores.idTorneo = ? " +
"    AND equiposdeltorneo.torneoIdTorneo=? " +
"    ORDER BY numeroGoles DESC";
            statement = conexion.prepareStatement(query);
            statement.setInt(1, idTorneo);
            statement.setInt(2, idTorneo);
            rs = statement.executeQuery();
            //mientras que halla registros cree un nuevo dto y pasele la info
            while (rs.next()) {
                //crea un nuevo dto
                GoleadoresDTO gol = new GoleadoresDTO();
                UsuariosDTO usu = new UsuariosDTO();
                EquipoDTO equipo = new EquipoDTO();
                //le pasamos los datos que se encuentren
                gol.setNumeroGoles(rs.getInt("numeroGoles"));
                gol.setIdEquipo(rs.getInt("idEquipo"));
                gol.setIdTorneo(rs.getInt("idTorneo"));
                gol.setIdJugador(rs.getLong("idJugador"));
                usu.setPrimerNombre(rs.getString("primerNombre"));
                usu.setPrimerApellido(rs.getString("primerApellido"));
                gol.setUsu(usu);//paso el usuario al objeto 
                equipo.setNombre(rs.getString("nombre"));
                gol.setEquipo(equipo);
                //agregamos el objeto dto al arreglo
                listarGoleadores.add(gol);

            }
        } catch (SQLException sqlexception) {
            throw new MiExcepcion("Error listando goles", sqlexception);

        } 
//        finally {
//            try{
//            if (statement != null) {
//                        statement.close();    
//                    }
//            }catch (SQLException sqlexception) {
//            throw new MiExcepcion("Error listando goles", sqlexception);
//
//        }
//        }
        //devolvemos el arreglo
        return listarGoleadores;

    }

    public List<GoleadoresDTO> listarUno(int idTorneo, long idJugador, int idEquipo, Connection conexion) throws MiExcepcion {
        ArrayList<GoleadoresDTO> goles = new ArrayList();
        try {
            //preparamos la consulta 
            statement = conexion.prepareStatement("SELECT numeroGoles,idJugador, idTorneo, idEquipo "
                    + "FROM tablagoleadores "
                    + "WHERE idTorneo = ? and idJugador=? and idEquipo=? ;");
            statement.setInt(1,idTorneo);
            statement.setLong(2,idJugador);
            statement.setInt(3,idEquipo);
            rs = statement.executeQuery();
            //mientras halla registros
            while (rs.next()) {
            GoleadoresDTO gol = new GoleadoresDTO();
               gol.setNumeroGoles(rs.getInt("numeroGoles"));
               gol.setIdEquipo(rs.getInt("idEquipo"));
               gol.setIdJugador(rs.getInt("idJugador"));
               gol.setIdTorneo(rs.getInt("idTorneo"));
            goles.add(gol);
            }

        } catch (SQLException ex) {
            throw new MiExcepcion("Error listando goles"+ex.getMessage(), ex);
        }
//        finally {
//            try{
//            if (statement != null) {
//                        statement.close();    
//                    }
//            }catch (SQLException sqlexception) {
//            throw new MiExcepcion("Error listando goles", sqlexception);
//
//        }
//        }
        //devolvemos el usuario que se encontro
        return goles;
    }
}
