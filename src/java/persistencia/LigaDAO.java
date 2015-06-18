
package persistencia;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import modelo.CampeonesDTO;
import modelo.TorneoDTO;
import utilidades.MiExcepcion;

/**
 *
 * @author Sena
 */
public class LigaDAO {
    
    //instanciamos preparestatment
    PreparedStatement statement;
    //variable que devuelve el metodo con el mensaje
    String mensaje = "";
    //variable que cuenta las filas afectadas
    int rtdo = 0;
    CallableStatement call=null;
    ResultSet rs=null;


    public synchronized String insertar (TorneoDTO liga, Connection conexion) {

        try {
            //sentencia sql
            call = conexion.prepareCall("call sp_torneoliga (?,?,?,?,?,?,?,?);");
            //pasamos la sentencia la conexion mediante el prepare staement
            call.setString(1, liga.getNombre());
            call.setString(2, liga.getFechaInicio());
            call.setString(3, liga.getFechaFin());
            call.setString(4, liga.getGenero());
            call.setInt(5, liga.getCapacidadEquipos());
            call.setInt(6, liga.getTipo());
            call.setBoolean(7, liga.isIdaVuelta());
            call.registerOutParameter(8, Types.INTEGER);
            call.execute();
            int salida = call.getInt(8);
            
            if (salida == 1) {
                mensaje = "Nueva liga creada.";
            }else {
                mensaje = "No se pudo crear la liga.";
            }
        } 
        catch (SQLException sqlexception) {
            mensaje = "Ha ocurrido un error "+ sqlexception.getMessage();
        }
        //devolvemos el mensaje al usuario
        return mensaje;
    }

    public String actualizar(TorneoDTO liga, Connection conexion) {
        try {
            statement = conexion.prepareStatement("UPDATE torneo SET nombre=?, "
                    + "fechaInicio =?, fechaFin = ?, genero = ?, "
                    + " WHERE idTorneo=?;");
            statement.setString(1, liga.getNombre());
            statement.setString(2, liga.getFechaInicio());
            statement.setString(3, liga.getFechaFin());
            statement.setString(4, liga.getGenero());
            statement.setInt(5, liga.getIdTorneo());
            rtdo = statement.executeUpdate();
            if (rtdo != 0) {
              mensaje="El Campo Se a modificado la liga";

            } else {
                mensaje = "Ocurrio un error";
            }
        } catch (SQLException sqlexception) {
            mensaje = "Ha ocurrido un error "+ sqlexception.getMessage();

        }

        return mensaje;

    }

    public String eliminar(int id, Connection conexion) {
        try {
            statement = conexion.prepareStatement("Delete from torneo where idTorneo=?;");
            //obtenemos el id del item a eliminar del dto
            statement.setInt(1, id);
            rtdo = statement.executeUpdate();

            if (rtdo != 0) {
                mensaje="Se eliminaron " + rtdo + " Corretamente";
            } else {
                mensaje = "Ocurrio Un Error";
            }
        } catch (SQLException sqlexception) {
            mensaje = "Ha ocurrido un error "+ sqlexception.getMessage();

        }

        return mensaje;
    }

    public List<TorneoDTO> listarTodo(Connection conexion) throws MiExcepcion {
        //creamos el array que va a contener los datos de la consulta    
        ArrayList<TorneoDTO> listarLiga = new ArrayList();

        try {
            statement = conexion.prepareStatement("SELECT * FROM torneo"
                    + "inner join liga"
                    + "on torneo.idTorneo = liga.idLiga");
            rs = statement.executeQuery();
            while (rs.next()) {
               TorneoDTO liga = new TorneoDTO();
                liga.setIdTorneo(rs.getInt("idLiga"));
                liga.setNombre(rs.getString("nombre"));
                liga.setFechaInicio(rs.getString("fechaInicio"));
                liga.setFechaFin(rs.getString("fechaFin"));
                liga.setGenero(rs.getString("genero"));
                liga.setTipo(rs.getInt("tipo"));
                liga.setIdaVuelta(rs.getBoolean("idaVuelta"));
                
                listarLiga.add(liga);
            }
        } catch (SQLException sqlexception) {
           throw new MiExcepcion("Error al obtener las ligas",sqlexception );
        } finally {

        }
        //devolvemos el arreglo
        return listarLiga;

    }

    public TorneoDTO listarUno(int id, Connection conexion) throws MiExcepcion {
        TorneoDTO liga = new TorneoDTO();
        try {
            //preparamos la consulta 
            statement = conexion.prepareStatement("SELECT * from torneo"
                    + " inner join liga"
                    + "on torneo.idTorneo = liga.idLiga"
                    + "WHERE idTorneo = ? and idLiga = ?");
            statement.setInt(1,id);
            statement.setInt(2,id);
            rs = statement.executeQuery();
            //mientras halla registros
            while (rs.next()) {
                
                liga.setIdTorneo(rs.getInt("idLiga"));
                liga.setNombre(rs.getString("nombre"));
                liga.setFechaInicio(rs.getString("fechaInicio"));
                liga.setFechaFin(rs.getString("fechaFin"));
                liga.setGenero(rs.getString("genero"));
                liga.setTipo(rs.getInt("tipo"));
                liga.setIdaVuelta(rs.getBoolean("idaVuelta"));
            }

        } catch (SQLException ex) {
            throw new MiExcepcion("Error al obtener la liga",ex);
        }
        return liga;
    }
    
    public CampeonesDTO declararCampeon(int idTorneo, Connection conexion) throws MiExcepcion {
        CampeonesDTO campeon = new CampeonesDTO();
        try {
            //preparamos la consulta 
            statement = conexion.prepareStatement("SELECT equipo.nombre as equipo,"
                    + " torneo.nombre as torneo, "
                    + "tablaposiciones.partidosJugados as PJ, "
                    + "tablaposiciones.partidosGanados as PG, " +
"tablaposiciones.partidosEmpatados as PE, " +
"tablaposiciones.partidosPerdidos as PP, " +
"tablaposiciones.golesAnotados as Goles, " +
"tablaposiciones.golesRecibidos as GC, " +
"tablaposiciones.golesAnotados-tablaposiciones.golesRecibidos as GD, " +
"tablaposiciones.puntos as pts " +
"FROM equipo " +
"inner join equiposdeltorneo " +
"on equipo.codigo = equiposdeltorneo.equipoCodigo " +
"inner join tablaPosiciones " +
"on equiposdeltorneo.equipoCodigo = tablaposiciones.idEquipo " +
"inner join torneo on " +
"torneo.idTorneo = tablaposiciones.idTorneo " +
"WHERE equiposdeltorneo.torneoIdTorneo=? " +
"and " +
"tablaposiciones.idTorneo = ? " +
"ORDER BY pts DESC, Goles DESC, GC ASC limit 1");
            statement.setInt(1,idTorneo);
            statement.setInt(2,idTorneo);
            rs = statement.executeQuery();
            //mientras halla registros
            while (rs.next()) {
                
                campeon.setNombreEquipo(rs.getString("equipo"));
                campeon.setNombreTorneo(rs.getString("torneo"));
            }

        } catch (SQLException ex) {
            throw new MiExcepcion("Error al declarar campeon "+ex.getMessage(),ex);
        }
        return campeon;
    }
}
