/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;
import java.sql.CallableStatement;
 import modelo.TarjetasDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import modelo.EquipoDTO;
import modelo.UsuariosDTO;
import utilidades.MiExcepcion;
public class TarjetasDAO {

    //instanciamos preparestatment
    PreparedStatement statement=null;
    //variable que devuelve el metodo con el mensaje
    String mensaje = "";
    //variable que cuenta las filas afectadas
    int rtdo = 0;

    ResultSet rs=null;
    CallableStatement call = null;


    public String insertar(TarjetasDTO tar, Connection conexion) throws MiExcepcion {

        try {
            call=conexion.prepareCall("{call sp_aumentartarjetas(?,?,?,?,?)};");
           
            call.setInt(1, tar.getIdJugador());
            call.setInt(2, tar.getIdtorneo());
            call.setInt(3, tar.getTarjetaRoja());
            call.setInt(4, tar.getTarjetaAzul());
            
            call.registerOutParameter(5, Types.INTEGER);
            rtdo = call.executeUpdate();

            int salida = call.getInt(5);
            if ( rtdo > 0) {
                mensaje = "Se insertaron las tarjetas";
            }else {
                mensaje = "No se pudo insertar tarjetas";
        }
        } 
        catch (SQLException sqlexception) {
            throw new MiExcepcion("Error ", sqlexception);
        }
        //devolvemos el mensaje al usuario
        return mensaje;
    }
    
    public synchronized int disminuir(int numero,int idJugador, int idTorneo, Connection conexion) throws MiExcepcion{
        int salida = 3;//tres es porq el procedimiento devuelve 0 si falla y 1 si es exitoso 3 entonces si falla el codigo java
        try {
        call = conexion.prepareCall("{call sp_disminuirtarjeta(?,?,?,?) }");
        call.setInt(1, numero);
        call.setInt(2, idJugador);
        call.setInt(3, idTorneo);

        call.registerOutParameter(4, Types.INTEGER);
        call.execute();
        salida = call.getInt(4);
        
        }catch(SQLException sqle){
            throw new MiExcepcion("Error "+sqle.getMessage(),sqle);
        }
//        finally{
//                try {
//                    if (call != null) {
//                    call.close();    
//                    }   
//                }catch (SQLException sqlexception) {
//                    mensaje = "No se pudo crear la eliminatoria.";
//                }
//        }
        return salida;//devuelvo la salida del procedimiento
    } 
    public String insertarPrimer (TarjetasDTO tar, Connection conexion) throws MiExcepcion{
        try {
            statement = conexion.prepareStatement("INSERT INTO tarjetas "
                    + " values(?,?,?,?);");
           
            statement.setInt(1, tar.getTarjetaAzul());
            statement.setInt(2, tar.getTarjetaRoja());
            statement.setInt(3, tar.getIdJugador());
            statement.setInt(4, tar.getIdtorneo());
            
            rtdo = statement.executeUpdate();
            
            if (rtdo > 0) {
                mensaje = "se insertaron las tarjetas";
            }else {
                mensaje = "No se pudo insertar tarjetas";
            
        }
        } 
        catch (SQLException sqlexception) {
            throw new MiExcepcion("Error al insertar tarjetas", sqlexception);
        }
        return mensaje;
    }

    public String actualizar(TarjetasDTO tar, Connection conexion) throws MiExcepcion {
        try {
            //preparamos la sentencia sql
            String sql = "UPDATE Tarjetas SET "
                    + " TarjetaAzul=?,TarjetaRoja=? "
                    + " WHERE idJugador=? and idTorneo=?;";
            //pasamos el query a la conexion
           //sacamos los datos del dto de la tabla
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, tar.getTarjetaAzul());
            statement.setInt(2, tar.getTarjetaRoja());
            statement.setInt(3, tar.getIdJugador());
            statement.setInt(4, tar.getIdtorneo());
           
            //el resulset trae el numero de rows afectadas
            rtdo = statement.executeUpdate();
            if (rtdo != 0) {

               mensaje="El Campo Se a modificado: " + rtdo + " campos";

            } else {
                mensaje = "Ocurrió Un Error";
            }
        } catch (SQLException sqlexception) {
             throw new MiExcepcion("Error ", sqlexception);

        }

        return mensaje;

    }

    public String eliminar(long Jugador, int Torneo, Connection conexion) throws MiExcepcion {
        try {
            statement = conexion.prepareStatement("Delete from tarjetas "
                    + "where idJugador=? and idTorneo=?;");
            
            statement.setLong(1,Jugador);
            statement.setInt(2, Torneo);
            rtdo = statement.executeUpdate();

            if (rtdo != 0) {
               mensaje="El siguiente campo" + rtdo + "se elimino Corretamente";
            } else {
                mensaje = "Ocurrio Un Error";
            }
        } catch (SQLException sqlexception) {
            throw new MiExcepcion("Error ", sqlexception);

        }

        return mensaje;
    }

    public List<TarjetasDTO> listarTodo(int idTorneo,Connection conexion) throws MiExcepcion {
        //creamos el array que va a contener los datos de la consulta    
        ArrayList<TarjetasDTO> listar = new ArrayList();

        try {
            String query = "SELECT DISTINCT concat(usuarios.primerNombre,' ', usuarios.primerApellido) nombres, " +
"    tarjetas.tarjetaAzul, tarjetas.tarjetaRoja, equipo.nombre " +
"    FROM tarjetas " +
"    INNER JOIN  jugadoresporequipo " +
"    ON tarjetas.idJugador = jugadoresporequipo.codigoJugador " +
"    INNER JOIN  usuarios " +
"    ON jugadoresporequipo.codigoJugador = usuarios.idUsuario " +
"    INNER JOIN equipo " +
"    ON jugadoresporequipo.codigoEquipo = equipo.codigo " +
"	INNER JOIN equiposdeltorneo " +
"	On jugadoresporequipo.codigoEquipo = equiposdeltorneo.equipoCodigo " +
"    WHERE tarjetas.idTorneo =? " +
"	and equiposdeltorneo.torneoIdTorneo =?;";
            statement = conexion.prepareStatement(query);
            statement.setInt(1, idTorneo);
            statement.setInt(2, idTorneo);
            rs = statement.executeQuery();
            //mientras que halla registros cree un nuevo dto y pasele la info
            while (rs.next()) {
                //crea un nuevo dto
                TarjetasDTO tarjetas = new TarjetasDTO();
                UsuariosDTO usu = new UsuariosDTO();
                //lleno los nombres de usuario
                usu.setPrimerNombre(rs.getString("nombres"));
                tarjetas.setUsu(usu);//paso al usuario
                //datos de equipo
                EquipoDTO equipo = new EquipoDTO();
                equipo.setNombre(rs.getString("nombre"));
                tarjetas.setEquipo(equipo);//paso al equipo
                //le pasamos los datos que se encuentren
                tarjetas.setTarjetaAzul(rs.getInt("tarjetaAzul"));
                tarjetas.setTarjetaRoja(rs.getInt("tarjetaRoja"));
                //agregamos el objeto dto al arreglo
                listar.add(tarjetas);

            }
        } catch (SQLException sqlexception) {
             throw new MiExcepcion("Error ", sqlexception);

        } finally {

        }
        //devolvemos el arreglo
        return listar;
    }

    public List<TarjetasDTO> listarUno(int torneo, int jugador, Connection conexion) throws MiExcepcion {
        LinkedList<TarjetasDTO> listar = new LinkedList<TarjetasDTO>();
        try {
            //preparamos la consulta 
            statement = conexion.prepareStatement("SELECT "
                    + "tarjetaAzul, tarjetaRoja, idJugador, idTorneo"
                    + " FROM tarjetas "
                    + "WHERE idtorneo = ? and idJugador = ? ;");
            statement.setInt(1, torneo);
            statement.setInt(2, jugador);
            rs = statement.executeQuery();
            //mientras halla registros
            while (rs.next()) {
                TarjetasDTO tarjetas = new TarjetasDTO();
               tarjetas.setIdtorneo(rs.getInt("idTorneo"));
               tarjetas.setTarjetaAzul(rs.getInt("tarjetaAzul"));
                tarjetas.setTarjetaRoja(rs.getInt("tarjetaRoja"));
                tarjetas.setIdJugador(rs.getInt("idJugador"));
                listar.add(tarjetas);
            }

        } catch (SQLException ex) {
            throw new MiExcepcion("Error ", ex);
        }
        //devolvemos el usuario que se encontro
        return listar;
    }


}
