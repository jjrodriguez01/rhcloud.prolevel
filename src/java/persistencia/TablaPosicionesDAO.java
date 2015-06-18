/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import modelo.TablaPosicionesDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import modelo.EquipoDTO;
import modelo.PartidoDTO;
import utilidades.MiExcepcion;

public class TablaPosicionesDAO {

    //instanciamos preparestatment
    private PreparedStatement statement;
    //variable que devuelve el metodo con el mensaje
    private String mensaje = "";
    //variable que cuenta las filas afectadas
    private int rtdo = 0;
    //calleableStatement
    CallableStatement call;

    ResultSet rs;


    public synchronized String insertar(PartidoDTO p,Connection conexion) throws MiExcepcion {
        try {
            call = conexion.prepareCall("{call sp_actalizarpos(?,?,?,?,?,?) }");
            call.setInt(1, p.getMarcador1());
            call.setInt(2, p.getMarcador2());
            call.setInt(3, p.getEquipo1());
            call.setInt(4, p.getEquipo2());
            call.setInt(5, p.getIdTorneo());

            call.registerOutParameter(6, Types.INTEGER);
            call.execute();
            
            int salida = call.getInt(6);
            if (salida == 1) {
                mensaje = "Se han actualizado las posiciones.";
            } else {
                mensaje = "No se pudo actualizar las posiciones.";
            }
        } catch (SQLException sqle) {
           throw new MiExcepcion("Error al insertar posiciones ", sqle);
        }
        return mensaje;
    }
    
    public synchronized String posEquipo1(PartidoDTO p,Connection conexion) throws MiExcepcion {

        try {

            statement = conexion.prepareStatement("INSERT INTO tablaposiciones "
                    + " (idTorneo, idEquipo, posicion, puntos, partidosJugados, partidosGanados, partidosEmpatados, partidosPerdidos, golesAnotados, golesRecibidos) "
                    + " VALUES(?,?,0,0,0,0,0,0,0,0);");
            //obtenemos los datos del dto de la tabla
            statement.setInt(1, p.getIdTorneo());
            statement.setInt(2, p.getEquipo1());
            //ejecuta el insert
            rtdo = statement.executeUpdate();
            //si se afectaron campos 
            if (rtdo != 0) {
                mensaje = "Se inserto correctamente";
                //si no se afecto la tabla
            } else {
                mensaje = "Error";
            }
        } 
        catch (SQLException sqlexception) {
            throw new MiExcepcion("Error insertando partidos", sqlexception);
        }
//        finally{
//            try{
//                statement.close();
//            }catch(SQLException sqlexception){
//                throw new MiExcepcion("Error insertando partidos", sqlexception);
//            }
//        }
        //devolvemos el mensaje al usuario
        return mensaje;
    }
    
    public synchronized String posEquipo2(PartidoDTO p,Connection conexion) throws MiExcepcion {

        try {

            statement = conexion.prepareStatement("INSERT INTO tablaposiciones "
                    + " (idTorneo, idEquipo, posicion, puntos, partidosJugados, partidosGanados, partidosEmpatados, partidosPerdidos, golesAnotados, golesRecibidos) "
                    + " VALUES(?,?,0,0,0,0,0,0,0,0);");
            //obtenemos los datos del dto de la tabla
            statement.setInt(1, p.getIdTorneo());
            statement.setInt(2, p.getEquipo2());
            //ejecuta el insert
            rtdo = statement.executeUpdate();
            //si se afectaron campos 
            if (rtdo != 0) {
                mensaje = "Se inserto correctamente";
                //si no se afecto la tabla
            } else {
                mensaje = "Error";
            }
        } 
        catch (SQLException sqlexception) {
            throw new MiExcepcion("Error insertando partidos", sqlexception);
        }
//        finally{
//            try{
//                statement.close();
//            }catch(SQLException sqlexception){
//                throw new MiExcepcion("Error insertando partidos", sqlexception);
//            }
//        }
        //devolvemos el mensaje al usuario
        return mensaje;
    }

    public String eliminar(TablaPosicionesDTO tab,Connection conexion) {

        try {
            statement = conexion.prepareStatement("Delete from tablaposiciones where idTorneo = ? and idEquipo=?;");
            //obtenemos el id del item a eliminar del dto
            statement.setInt(2, tab.getIdequipo());
            statement.setInt(1,tab.getIdtorneo());
            rtdo = statement.executeUpdate();

            if (rtdo != 0) {
                mensaje = "Se elimino corretamente";
            } else {
                mensaje = "Ocurrió Un Error";
            }
        } catch (SQLException sqlexception) {
            mensaje = "Ocurrió un error" + sqlexception.getMessage();

        }
        return mensaje;
    }
  public String actualizar(TablaPosicionesDTO tab,Connection conexion){
        
        try {
            statement = conexion.prepareStatement("UPDATE tablaPosiciones set golesAnotados= ?, "
                    + "golesRecibidos= ?,= ?, partidosEmpatados= ?, "
                    + " partidosGanados = ? ,partidosJugados = ?, partidosPerdidos = ?, posicion=?, puntos = ? "
                    + "WHERE idTorneo = ? and idEquipo=?");
            statement.setInt(1, tab.getGolesAnotados());
            statement.setInt(2, tab.getGolesRecibidos());
            statement.setInt(3, tab.getPartidosEmpatados()); 
            statement.setInt(4, tab.getPartidosGanados());
            statement.setInt(5, tab.getPartidosJugados());
            statement.setInt(6, tab.getPartidosPerdidos());
            statement.setInt(7, tab.getPosicion());
            statement.setInt(8, tab.getPuntos());
            statement.setInt(9, tab.getIdtorneo());
            statement.setInt(10, tab.getIdequipo());
            
            rtdo = statement.executeUpdate();
            
            if (rtdo != 0) {

                mensaje = "Se han modificado :" + rtdo + " registro";

            } else {
                mensaje = "Error";
            }
        }catch(SQLException sqle){
            mensaje = "Error: "+ sqle.getMessage();
        }
        
        return mensaje;
    }
     public TablaPosicionesDTO listarUno(int idtorneo,int codigoequipo,Connection conexion) throws MiExcepcion {
         TablaPosicionesDTO tab = new TablaPosicionesDTO();

        try {
            //preparamos la consulta       
            statement = conexion.prepareStatement("SELECT tablaposiciones.idTorneo, "
                    + "tablaposiciones.idEquipo, "
                    + "tablaposiciones.partidosJugados, " +
"tablaposiciones.posicion, " +
"tablaposiciones.partidosGanados, " +
"tablaposiciones.partidosEmpatados," +
"tablaposiciones.partidosPerdidos, " +
"tablaposiciones.golesAnotados, " +
"tablaposiciones.golesRecibidos, " +
"tablaposiciones.golesAnotados-tablaposiciones.golesRecibidos, " +
"tablaposiciones.puntos " +
"FROM tablaposiciones "
+ "WHERE tablaposiciones.idTorneo = ? " +
"AND " +
"tablaposiciones.idEquipo = ?;");
            statement.setInt(1, idtorneo);
            statement.setInt(2, codigoequipo);
            rs = statement.executeQuery();
            //mientras halla registros
            while(rs.next()){
                tab.setIdtorneo(rs.getInt("idTorneo"));
                tab.setIdequipo(rs.getInt("idEquipo"));
                tab.setPosicion(rs.getInt("posicion"));
                tab.setPuntos(rs.getInt("puntos"));
                tab.setPartidosJugados(rs.getInt("partidosJugados"));
                tab.setPartidosGanados(rs.getInt("partidosGanados"));
                tab.setPartidosEmpatados(rs.getInt("partidosEmpatados"));
                tab.setPartidosPerdidos(rs.getInt("partidosPerdidos"));
                tab.setGolesAnotados(rs.getInt("golesAnotados"));
                tab.setGolesRecibidos(rs.getInt("GolesRecibidos"));             
            }

        } catch (SQLException ex) {
            throw new MiExcepcion("Error "+ex.getMessage(),ex);
        }
        return tab;
    }
     
     public List<PartidoDTO> partidosUnEquipo(int idtorneo,int codigoequipo,Connection conexion) throws MiExcepcion {
         ArrayList<PartidoDTO> listapartidos = new ArrayList();
        try {
            //preparamos la consulta       
            statement = conexion.prepareStatement("SELECT  " +
                        "(select equipo.nombre from equipo where codigo=partidos.equipo1)as eq1, " +
                        "partidos.marcador1, " +
                        "(select equipo.nombre from equipo where codigo=partidos.equipo2)as eq2, " +
                        "partidos.marcador2 " +
                        "FROM " +
                        "partidos " +
                        "INNER JOIN equiposdeltorneo " +
                        "ON partidos.equipo1 = equiposdeltorneo.equipoCodigo " +
                        "INNER JOIN equipo " +
                        "ON equiposdeltorneo.equipoCodigo = equipo.codigo " +
                        "WHERE partidos.equipo1 = ? or partidos.equipo2 =? and partidos.idTorneo = ?");
            statement.setInt(1, codigoequipo);
            statement.setInt(2, codigoequipo);
            statement.setInt(3, idtorneo);
            rs = statement.executeQuery();
            //mientras halla registros
            while(rs.next()){
                PartidoDTO p = new PartidoDTO();
                EquipoDTO e1 = new EquipoDTO(); 
                e1.setNombre(rs.getString("eq1"));
                p.setMarcador1(rs.getInt("marcador1"));
                EquipoDTO e2 = new EquipoDTO();
                e2.setNombre(rs.getString("eq2"));
                p.setMarcador2(rs.getInt("marcador2"));
                p.setEquipouno(e1);
                p.setEquipodos(e2);
                listapartidos.add(p);
            }

        } catch (SQLException ex) {
            throw new MiExcepcion("Error "+ex.getMessage(),ex);
        }
        return listapartidos;
    }
    
//    public List ListarTodo(Connection conexion){
//        
//        ArrayList<TablaPosicionesDTO> listarTablaPosiciones = new ArrayList();
//        try{
//            String sql= "SELECT * FROM tablaPosiciones where idTorneo=? and idEquipo=?";
//                                         
//            statement = conexion.prepareStatement(sql);
//            
//            rs = statement.executeQuery();
//            
//            while(rs.next()){
//                TablaPosicionesDTO cup = new TablaPosicionesDTO();
//                
//                cup.setIdtorneo(rs.getInt("idTorneo"));
//                cup.setIdequipo(rs.getInt("idEquipo"));
//                cup.setPosicion(rs.getInt("Posicion"));
//                cup.setPuntos(rs.getInt("Puntos"));
//                cup.setPartidosJugados(rs.getInt("PartidosJugados"));
//                cup.setPartidosGanados(rs.getInt("PartidosGanados"));
//                cup.setPartidosEmpatados(rs.getInt("PartidosEmpatados"));
//                cup.setGolesAnotados(rs.getInt("GolesAnotados"));
//                cup.setGolesRecibidos(rs.getInt("GolesResividos"));
//                cup.setPartidosPerdidos(rs.getInt("PartiosPerdidos"));
//                
//                listarTablaPosiciones.add(cup);
//            }
//            
//        }catch(SQLException sqle){
//            mensaje = "Error: "+ sqle.getMessage();
//        }
//        return listarTablaPosiciones;
//    }
    
     
     public List<TablaPosicionesDTO> listarPosiciones(int idTorneo,Connection conexion) throws MiExcepcion {
         ArrayList<TablaPosicionesDTO> posiciones = new ArrayList();
        try {
            //preparamos la consulta       
            statement = conexion.prepareStatement("SELECT equipo.nombre, " +
"    tablaposiciones.partidosJugados as PJ," +
"    tablaposiciones.partidosGanados as PG, " +
"    tablaposiciones.partidosEmpatados as PE," +
"    tablaposiciones.partidosPerdidos as PP, " +
"    tablaposiciones.golesAnotados as GA, " +
"    tablaposiciones.golesRecibidos as GR, " +
"    tablaposiciones.golesAnotados-tablaposiciones.golesRecibidos as GD, " +
"    tablaposiciones.puntos as pts " +
"    FROM equipo " +
"    inner join equiposdeltorneo " +
"    on equipo.codigo = equiposdeltorneo.equipoCodigo " +
"    inner join tablaPosiciones " +
"    on equiposdeltorneo.equipoCodigo = tablaposiciones.idEquipo " +
"    WHERE equiposdeltorneo.torneoidtorneo=? " +
"    and " +
"    tablaposiciones.idTorneo = ? " +
"    ORDER BY puntos DESC");
            statement.setInt(1, idTorneo);
            statement.setInt(2, idTorneo);
            rs = statement.executeQuery();
            //mientras halla registros
            while(rs.next()){
                TablaPosicionesDTO tab = new TablaPosicionesDTO();
                EquipoDTO eq = new EquipoDTO();
                eq.setNombre(rs.getString("nombre"));
                tab.setEquipo(eq);
                tab.setPuntos(rs.getInt("pts"));
                tab.setPartidosJugados(rs.getInt("PJ"));
                tab.setPartidosGanados(rs.getInt("PG"));
                tab.setPartidosEmpatados(rs.getInt("PE"));
                tab.setPartidosPerdidos(rs.getInt("PP"));
                tab.setGolesAnotados(rs.getInt("GA"));
                tab.setGolesRecibidos(rs.getInt("GR")); 
                tab.setDiferencia(rs.getInt("GD"));
            }

        } catch (SQLException ex) {
            throw new MiExcepcion("Error "+ex.getMessage(),ex);
        }
        return posiciones;
    }
}
