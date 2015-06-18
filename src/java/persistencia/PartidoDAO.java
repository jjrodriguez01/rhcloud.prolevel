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
import modelo.CanchaDTO;
import modelo.EquipoDTO;
import modelo.PartidoDTO;
import modelo.TorneoDTO;
import utilidades.MiExcepcion;

public class PartidoDAO {
 
    //instanciamos preparestatment
    PreparedStatement statement;
    //variable que devuelve el metodo con el mensaje
    String mensaje = "";
    //variable que cuenta las filas afectadas
    int rtdo = 0;

    ResultSet rs;


    public synchronized String insertar(PartidoDTO cal,Connection conexion) throws MiExcepcion {

        try {
            //sentencia sql
            String sql = "INSERT INTO partidos(ronda,equipo1,equipo2,idTorneo,numero,estado) "
                    + "VALUES(?,?,?,?,?,?);";
            //pasamos la sentencia la conexion mediante el prepare staement
            statement = conexion.prepareStatement(sql);
            //obtenemos los datos del dto de la tabla
            statement.setInt(1, cal.getRonda());
            statement.setInt(2, cal.getEquipo1());
            statement.setInt(3, cal.getEquipo2());
            statement.setInt(4, cal.getIdTorneo());
            statement.setInt(5, cal.getNumero());
            statement.setInt(6, cal.getEstado());
            //ejecuta el insert
            rtdo = statement.executeUpdate();
            //si se afectaron campos 
            if (rtdo != 0) {
                mensaje = "Se insertaron los partidos";
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
    
    /** 
     * 
     * Inserta los goles en la tabla partidos
     * @param cal PartidoDTO con equipo1, equipo2, marcador1, marcador2, ronda, numeropartido y idTorneo
     * @param conexion
     * @return msj de confirmacion
     * @throws MiExcepcion 
     */
    
    public synchronized String insertarMarcador(PartidoDTO p, Connection conexion) throws MiExcepcion {

        try {
        statement = conexion.prepareStatement("update partidos SET marcador1 = ?, marcador2= ?, estado = ?"
        + " WHERE ronda = ? and equipo1= ? and equipo2= ? and numero = ? and idTorneo= ?");
            //obtenemos los datos del dto de la tabla
            statement.setInt(1, p.getMarcador1());
            statement.setInt(2, p.getMarcador2());
            statement.setInt(3, p.getEstado());
            statement.setInt(4, p.getRonda());
            statement.setInt(5, p.getEquipo1());
            statement.setInt(6, p.getEquipo2());
            statement.setInt(7, p.getNumero());
            statement.setInt(8, p.getIdTorneo());
            //ejecuta el update
            rtdo = statement.executeUpdate();
            //si se afectaron campos 
            if (rtdo > 0) {
                mensaje = "Se insertaron los marcadores";
                //si no se afecto la tabla
            } else {
                mensaje = "Error no se insertaron los marcadores";
            }
        } 
        catch (SQLException sqlexception) {
            throw new MiExcepcion("Error insertando marcadores : "+sqlexception.getMessage(), sqlexception);
        }

        return mensaje;
    }

    public String actualizar(PartidoDTO cal,Connection conexion) throws MiExcepcion {
        try {
            //preparamos la sentencia sql
            String sql = "UPDATE partidos SET fecha = ?, hora = ?, cancha = ? "
                    + "WHERE ronda=? and equipo1=? and equipo2=? and idTorneo=?;";
            //pasamos el query a la conexion
           //sacamos los datos del dto de la tabla
            statement = conexion.prepareStatement(sql);
            statement.setString(1, cal.getFecha());
            statement.setString(2,cal.getHora());
            statement.setInt(3, cal.getCancha());
            statement.setInt(4, cal.getRonda());
            statement.setInt(5, cal.getEquipo1());
            statement.setInt(6, cal.getEquipo2());
            statement.setInt(7, cal.getIdTorneo());
            
            //el resulset trae el numero de rows afectadas
            rtdo = statement.executeUpdate();
            if (rtdo != 0) {
                mensaje = "Se modificaron las fechas";

            } else {
                mensaje = "Error";
            }
        } catch (SQLException sqlexception) {
         throw new MiExcepcion("Error modificando partidos "+sqlexception.getMessage(), sqlexception);
        }
//        finally{
//            try{
//                statement.close();
//            }catch(SQLException sqlexception){
//                throw new MiExcepcion("Error insertando partidos", sqlexception);
//            }
//        }
        return mensaje;
    }

    public String eliminar(PartidoDTO cal,Connection conexion) throws MiExcepcion {
        try {
            statement = conexion.prepareStatement("Delete from partidos where idTorneo = ?;");
            //obtenemos el id del item a eliminar del dto
            statement.setInt(1, cal.getIdTorneo());
            rtdo = statement.executeUpdate();

            if (rtdo != 0) {
                System.out.println("El siguiente campo" + rtdo + "se elimino Corretamente");
            } else {
                mensaje = "Ocurrio Un Error";
            }
        } catch (SQLException sqlexception) {
             throw new MiExcepcion("Error eliminando partidos", sqlexception);
        }
//        finally{
//            try{
//                statement.close();
//            }catch(SQLException sqlexception){
//                throw new MiExcepcion("Error insertando partidos", sqlexception);
//            }
//        }

        return mensaje;
    }

    public List<PartidoDTO> listarTodo(Connection conexion) throws MiExcepcion {
        //creamos el array que va a contener los datos de la consulta    
        ArrayList<PartidoDTO> listar = new ArrayList();
        try {
            String query = "SELECT DISTINCT " +
"    ronda, " +
"    marcador1, " +
"    (select equipo.nombre from equipo where codigo=partidos.equipo1)as equipo1, " +
"    (select equipo.nombre from equipo where codigo=partidos.equipo2)as equipo2, " +
"    marcador2, " +
"    fecha, " +
"    hora, " +
"    torneo.nombre as Torneo, " +
"    cancha.descripcion as descripcion " +
"FROM " +
"    partidos " +
"INNER JOIN equiposdeltorneo " +
"ON partidos.equipo1 = equiposdeltorneo.equipoCodigo " +
"INNER JOIN equipo " +
"ON equiposdeltorneo.equipoCodigo = equipo.codigo " +
"INNER JOIN torneo " +
"ON partidos.idTorneo = torneo.idTorneo " +
"INNER JOIN cancha " +
"ON partidos.cancha = cancha.numeroCancha;";
            statement = conexion.prepareStatement(query);
            rs = statement.executeQuery();
            //mientras que halla registros cree un nuevo dto y pasele la info
            while (rs.next()) {
                //crea un nuevo dto
                PartidoDTO cal = new PartidoDTO();
                EquipoDTO equipouno = new EquipoDTO();
                EquipoDTO equipodos = new EquipoDTO();
                TorneoDTO torneo = new TorneoDTO();
                CanchaDTO cancha = new CanchaDTO();
                
                //le pasamos los datos que se encuentren
                cal.setRonda(rs.getInt("ronda"));
                cal.setMarcador1(rs.getInt("marcador1"));
                equipouno.setNombre(rs.getString("equipo1"));
                cal.setEquipouno(equipouno);
                equipodos.setNombre(rs.getString("equipo2"));
                cal.setMarcador2(rs.getInt("marcador2"));
                cal.setFecha(rs.getString("fecha"));
                cal.setHora(rs.getString("hora"));
                torneo.setNombre(rs.getString("torneo"));
                cal.setTorneo(torneo);
                cancha.setDescripcion(rs.getString("descripcion"));
                cal.setCanchas(cancha);
                //agregamos el objeto dto al arreglo
                listar.add(cal);
            }
        } catch (SQLException sqlexception) {
            throw new MiExcepcion("Error ", sqlexception);
        }
//        finally{
//            try{
//                statement.close();
//            }catch(SQLException sqlexception){
//                throw new MiExcepcion("Error ", sqlexception);
//            }
//        }
        //devolvemos el arreglo
        return listar;
    }
    public List<PartidoDTO> listarTodoPronda(int idtorneo,Connection conexion) throws MiExcepcion {
        //creamos el array que va a contener los datos de la consulta    
        ArrayList<PartidoDTO> listar = new ArrayList();
        try {

            statement = conexion.prepareStatement("SELECT DISTINCT "
                +"ronda, "
                +"partidos.equipo1 as ceq1, "
                +"partidos.equipo2 as ceq2, "
                +"(select equipo.nombre from equipo where codigo=partidos.equipo1)as equipo1, "
                +"(select equipo.nombre from equipo where codigo=partidos.equipo2)as equipo2, " 
                +"torneo.nombre as Torneo, "
                +"cancha.descripcion as descripcion "
                +"FROM " 
                +"partidos " 
                +"INNER JOIN equiposdeltorneo " 
                +"ON partidos.equipo1 = equiposdeltorneo.equipoCodigo " 
                +"INNER JOIN equipo " 
                +"ON equiposdeltorneo.equipoCodigo = equipo.codigo " 
                +"INNER JOIN torneo " 
                +"ON partidos.idTorneo = torneo.idTorneo " 
                +"INNER JOIN cancha " 
                +"ON partidos.cancha = cancha.numeroCancha " 
                +"WHERE torneo.idtorneo = ? AND partidos.ronda = 1;");
            statement.setInt(1, idtorneo);
            rs = statement.executeQuery();
            //mientras que halla registros cree un nuevo dto y pasele la info
            while (rs.next()) {
                //crea un nuevo dto
                PartidoDTO cal = new PartidoDTO();
                EquipoDTO equipouno = new EquipoDTO();
                EquipoDTO equipodos = new EquipoDTO();
                TorneoDTO torneo = new TorneoDTO();
                CanchaDTO cancha = new CanchaDTO();
                
                //le pasamos los datos que se encuentren
                cal.setRonda(rs.getInt("ronda"));
                equipouno.setNombre(rs.getString("equipo1"));
                equipouno.setCodigo(rs.getInt("ceq1"));
                cal.setEquipouno(equipouno);
                equipodos.setNombre(rs.getString("equipo2"));
                equipodos.setCodigo(rs.getInt("ceq2"));
                cal.setEquipodos(equipodos);
                torneo.setNombre(rs.getString("Torneo"));
                cal.setTorneo(torneo);
                cancha.setDescripcion(rs.getString("descripcion"));
                cal.setCanchas(cancha);
                //agregamos el objeto dto al arreglo
                listar.add(cal);
            }
        } catch (SQLException sqlexception) {
            throw new MiExcepcion("Error ", sqlexception);

        } 
//        finally {
//            try {
//                statement.close();
//            } catch (SQLException ex) {
//                throw new MiExcepcion("Error sql ", ex);
//            }
//        }
        //devolvemos el arreglo
        return listar;
    }

    public List<PartidoDTO> listarUno(int ronda, int idTorneo,Connection conexion) throws MiExcepcion {
        //creamos el array que va a contener los datos de la consulta    
        ArrayList<PartidoDTO> listar = new ArrayList();
        try {
            String query = "SELECT DISTINCT\n" +
"    ronda,\n" +
"    marcador1,\n" +
"    (select equipo.nombre from equipo where codigo=partidos.equipo1)as equipo1,\n" +
"    (select equipo.nombre from equipo where codigo=partidos.equipo2)as equipo2,\n" +
"    marcador2,\n" +
"    fecha,\n" +
"    hora,\n" +
"    torneo.nombre as Torneo,\n" +
"    cancha.descripcion as descripcion\n" +
"FROM\n" +
"    partidos\n" +
"INNER JOIN equiposdeltorneo\n" +
"ON partidos.equipo1 = equiposdeltorneo.equipoCodigo\n" +
"INNER JOIN equipo\n" +
"ON equiposdeltorneo.equipoCodigo = equipo.codigo\n" +
"INNER JOIN torneo\n" +
"ON partidos.idTorneo = torneo.idTorneo\n" +
"INNER JOIN cancha\n" +
"ON partidos.cancha = cancha.numeroCancha\n" +
"WHERE ronda = ? AND partidos.idTorneo = ?;";
            statement = conexion.prepareStatement(query);
            statement.setInt(1, ronda);
            statement.setInt(2, idTorneo);
            rs = statement.executeQuery();
            //mientras que halla registros cree un nuevo dto y pasele la info
            while (rs.next()) {
                //crea un nuevo dto
                PartidoDTO cal = new PartidoDTO();
                EquipoDTO equipouno = new EquipoDTO();
                EquipoDTO equipodos = new EquipoDTO();
                TorneoDTO torneo = new TorneoDTO();
                CanchaDTO cancha = new CanchaDTO();
                
                //le pasamos los datos que se encuentren
                cal.setRonda(rs.getInt("ronda"));
                cal.setMarcador1(rs.getInt("marcador1"));
                equipouno.setNombre(rs.getString("equipo1"));
                cal.setEquipouno(equipouno);
                equipodos.setNombre(rs.getString("equipo2"));
                cal.setMarcador2(rs.getInt("marcador2"));
                cal.setFecha(rs.getString("fecha"));
                cal.setHora(rs.getString("hora"));
                torneo.setNombre(rs.getString("torneo"));
                cal.setTorneo(torneo);
                cancha.setDescripcion(rs.getString("descripcion"));
                cal.setCanchas(cancha);
                //agregamos el objeto dto al arreglo
                listar.add(cal);
            }
        } catch (SQLException sqlexception) {
            throw new MiExcepcion("Error ", sqlexception);

        } 
//        finally {
//            try {
//                statement.close();
//            } catch (SQLException ex) {
//                throw new MiExcepcion("Error ", ex);
//            }
//        }
        //devolvemos el arreglo
        return listar;

    }
    
    public synchronized String insertarCuartos(int idTorneo, int codigoequipo, Connection conexion) throws MiExcepcion{
        try {
            statement = conexion.prepareStatement("INSERT INTO cuartos (idTorneo, codigoEquipo)"
                    + " VALUES (?,?);");
            statement.setInt(1, idTorneo);
            statement.setInt(2, codigoequipo);
            rtdo = statement.executeUpdate();
            if (rtdo>0) {
                mensaje ="Se inserto el equipo a cuartos";
            }else{
                mensaje = "No se inserto el equipo";
            }
            
        } catch (SQLException ex) {
            throw new MiExcepcion("No se inserto el equipo a cuartos error: "+ex.getMessage(), ex);
        }
//        finally{
//            try {
//                if (statement != null) {
//                        statement.close();    
//                    }
//            } catch (SQLException ex) {
//                mensaje = "Ha ocurrido un error "+ex.getMessage();
//            }
//        }
        return mensaje;
    }
    
    public synchronized String insertarSemi(int idTorneo, int codigoequipo, Connection conexion) throws MiExcepcion{
        try {
            statement = conexion.prepareStatement("INSERT INTO semifinales (idTorneo, codigoEquipo)"
                    + " VALUES (?,?);");
            statement.setInt(1, idTorneo);
            statement.setInt(2, codigoequipo);
            rtdo = statement.executeUpdate();
            if (rtdo > 0) {
                mensaje ="Se inserto el equipo a cuartos";
            }else{
                mensaje = "No se inserto el equipo";
            }
            
        } catch (SQLException ex) {
            throw new MiExcepcion("No se inserto el equipo a cuartos error: "+ex.getMessage(), ex);
        }
//        finally{
//            try {
//                if (statement != null) {
//                        statement.close();    
//                    }
//            } catch (SQLException ex) {
//                mensaje = "Ha ocurrido un error "+ex.getMessage();
//            }
//        }
        return mensaje;
    }
    
    public synchronized String insertarFinal(int idTorneo, int codigoequipo, Connection conexion) throws MiExcepcion{
        try {
            statement = conexion.prepareStatement("INSERT INTO finales (idTorneo, codigoEquipo)"
                    + " VALUES (?,?);");
            statement.setInt(1, idTorneo);
            statement.setInt(2, codigoequipo);
            rtdo = statement.executeUpdate();
            if (rtdo > 0) {
                mensaje ="Se inserto el equipo a finales";
            }else{
                mensaje = "No se inserto el equipo";
            }
            
        } catch (SQLException ex) {
            throw new MiExcepcion("No se inserto el equipo a cuartos error: "+ex.getMessage(), ex);
        }
//        finally{
//            try {
//                if (statement != null) {
//                        statement.close();    
//                    }
//            } catch (SQLException ex) {
//                mensaje = "Ha ocurrido un error "+ex.getMessage();
//            }
//        }
        return mensaje;
    }
    
    public int getNumeroPartido(PartidoDTO p, Connection conexion) throws MiExcepcion{
        int numero = 0;
        try {
            statement = conexion.prepareStatement("SELECT numero FROM partidos WHERE equipo1=? "
                    + "AND equipo2 =? AND idTorneo=? AND ronda=?;");
            statement.setInt(1, p.getEquipo1());
            statement.setInt(2, p.getEquipo2());
            statement.setInt(3, p.getIdTorneo());
            statement.setInt(4, p.getRonda());
            rs = statement.executeQuery();
            while(rs.next()){
                numero = rs.getInt("numero");
            }
            
        } catch (SQLException ex) {
            throw new MiExcepcion("No se inserto el equipo a cuartos error: "+ex.getMessage(), ex);
        }
//        finally{
//            try {
//                if (statement != null) {
//                        statement.close();    
//                    }
//            } catch (SQLException ex) {
//                mensaje = "Ha ocurrido un error "+ex.getMessage();
//            }
//        }
        return numero;
    }

    
    public synchronized boolean validarFechas(PartidoDTO p, Connection conexion) throws MiExcepcion {
        boolean fechasVacias = true;
        try {
        statement = conexion.prepareStatement("SELECT fecha, hora FROM partidos "
        + " WHERE equipo1 = ? and equipo2 = ? and ronda = ? ;");
            //obtenemos los datos del dto de la tabla
            statement.setInt(1, p.getEquipo1());
            statement.setInt(2, p.getEquipo2());
            statement.setInt(3, p.getRonda());
            //ejecuta la consulta
            rs = statement.executeQuery();
            //mientras haya alguna fecha 
            while (rs.next()){
                fechasVacias = false;// ya hay fech u hora establecida
            }
        } 
        catch (SQLException sqlexception) {
            throw new MiExcepcion("Error validando fechas : "+sqlexception.getMessage(), sqlexception);
        }

        return fechasVacias;
    }
}
  

