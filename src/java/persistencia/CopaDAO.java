package persistencia;

import modelo.TorneoDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import utilidades.MiExcepcion;
/**
 *
 * @author jeisson
 */
public class CopaDAO {
  
    //instanciamos preparestatment
    private PreparedStatement statement;
    //variable que devuelve el metodo con el mensaje
    private String mensaje = "";
    //variable que cuenta las filas afectadas
    private int rtdo = 0;
    //calleableStatement
    CallableStatement call;

    ResultSet rs;
    
    public synchronized String insertar(TorneoDTO copa, Connection conexion){
        try {
        call = conexion.prepareCall("{call sp_torneocopa(?,?,?,?,?,?,?,?,?,?,?) }");
        call.setString(1, copa.getNombre());
        call.setString(2, copa.getFechaInicio());
        call.setString(3, copa.getFechaFin());
        call.setString(4, copa.getGenero());
        call.setInt(5, copa.getTipo());
        call.setInt(6, copa.getCapacidadEquipos());
        call.setBoolean(7, copa.isTercerPuesto());
        call.setInt(8, copa.getEquiposGrupos());
        call.setBoolean(9, copa.isOctavosCuartosSemifinalFinalIdaVuelta());
        call.setBoolean(10, copa.isFinalidavuelta());
        call.registerOutParameter(11, Types.INTEGER);
        call.execute();
        int salida = call.getInt(11);
            if (salida == 1) {
                mensaje = "Nueva copa creada.";
            }else {
                mensaje = "No se pudo crear la copa.";
            }
        }
        catch(SQLException sqle){
            mensaje = "Error :" + sqle.getMessage();
        }
        return mensaje;
    }
    
    public String eliminar (int id, Connection conexion){
        TorneoDTO copa = new TorneoDTO();
        try {
            statement = conexion.prepareStatement("Delete from torneo where idTorneo =?;");
            //obtenemos el id del item a eliminar del dto
            statement.setInt(1, id);
            rtdo = statement.executeUpdate();

            if (rtdo != 0) {
                System.out.println("Se elimino " + rtdo + " registro corretamente");
            } else {
                mensaje = "Ocurrió Un Error";
            }
        } catch (SQLException sqlexception) {
            System.out.println("Ocurrió un error" + sqlexception.getMessage());

        }
//        finally{
//                try {
//                    if (statement != null) {
//                        statement.close();    
//                    }
//                } catch (SQLException sqlexception) {
//                    mensaje = "Error :" + sqlexception.getMessage();
//                }
//        }
        return mensaje;
    }
    
    public String actualizar (TorneoDTO copa, Connection conexion){
        try {
            statement = conexion.prepareStatement("UPDATE torneo set nombre = ?, "
                    + "fechaInicio= ?,fechaFin= ?, genero=?, "
                    + "capacidadEquipos = ? WHERE idTorneo =?");
            statement.setString(1, copa.getNombre());
            statement.setString(2, copa.getFechaInicio());
            statement.setString(3, copa.getFechaFin());
            statement.setString(4, copa.getGenero());
            statement.setInt(5, copa.getCapacidadEquipos());
            statement.setInt(6, copa.getIdTorneo());
            
            rtdo = statement.executeUpdate();
            
            if (rtdo != 0) {

                System.out.println("Se han modificado :" + rtdo + " registro");

            } else {
                mensaje = "Error";
            }
        }catch(SQLException sqle){
            mensaje = "Error: "+ sqle.getMessage();
        }
//        finally{
//                try {
//                    if (statement != null) {
//                        statement.close();    
//                    }
//                } catch (SQLException sqlexception) {
//                    mensaje = "Error :" + sqlexception.getMessage();
//                }
//        }
        
        return mensaje;
    }
     public TorneoDTO listarUno(int id, Connection conexion) throws MiExcepcion {
        TorneoDTO copa = new TorneoDTO();
        try {
            //preparamos la consulta
            statement = conexion.prepareStatement("SELECT * from torneo inner join copa on"
                    + "torneo.idTorneo=copa.idcopa where idCopa=? and idTorneo=?;");
            statement.setInt(1, id);
            statement.setInt(2, id);
            rs = statement.executeQuery();

            //mientras halla registros
            if (rs != null) {
            while (rs.next()) {
                copa.setIdTorneo(rs.getInt(1));
                copa.setNombre(rs.getString(2));
                copa.setFechaInicio(rs.getString(3));
                copa.setFechaFin(rs.getString(4));
                copa.setGenero(rs.getString(5));
                copa.setCapacidadEquipos(rs.getInt(6));
                copa.setTipo(rs.getInt("tipo"));
                copa.setTercerPuesto(rs.getBoolean(8));
                copa.setEquiposGrupos(rs.getInt(9));
                copa.setOctavosCuartosSemifinalFinalIdaVuelta(rs.getBoolean(10));
                copa.setFinalidavuelta(rs.getBoolean(11));
            }
            }
        } catch (SQLException ex) {
            throw new MiExcepcion("Error al listar las copas", ex);
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
        //devolvemos el usuario que se encontro
        return copa;
    }
    
    public ArrayList<TorneoDTO> ListarTodo(Connection conexion) throws MiExcepcion{
        
        ArrayList<TorneoDTO> listarCopas = new ArrayList();
        try{                     
            statement = conexion.prepareStatement("SELECT idTorneo, nombre,"
                    + "fechaInicio, fechaFin, genero, capacidadEquipos"
                    + "idCopa, tercerPuesto, equiposGrupos"
                    + "octavosCuartosSemifinalIdaVuelta, finalIdaVuelta"
                    + " FROM torneo inner join copa on torneo.idtorneo = copa.idcopa");
            rs=statement.executeQuery();
            if (rs != null) {
            while(rs.next()){
                TorneoDTO cup = new TorneoDTO();
                cup.setIdTorneo(rs.getInt("idCopa"));
                cup.setNombre(rs.getString("nombre"));
                cup.setFechaInicio(rs.getString("fechaInicio"));
                cup.setFechaFin(rs.getString("fechaFin"));
                cup.setGenero(rs.getString("genero"));
                cup.setCapacidadEquipos(rs.getInt("capacidadEquipos"));
                cup.setTipo(rs.getInt("tipo"));
                cup.setTercerPuesto(rs.getBoolean("tercerPuesto"));
                cup.setEquiposGrupos(rs.getInt("equiposGrupos"));
                cup.setOctavosCuartosSemifinalFinalIdaVuelta(rs.getBoolean("octavosCuartosSemifinalFinalIdaVuelta"));
                cup.setFinalidavuelta(rs.getBoolean("finalIdaVuelta"));
                listarCopas.add(cup);   
            }
            }
        }catch(SQLException sqle){
            throw new MiExcepcion("Error al listar las copas", sqle);
        }
//        finally{
//            try {
//                 if (statement != null) {
//                        statement.close();    
//                    }
//            }catch (SQLException sqlexception) {
//                    throw new MiExcepcion("Error al listar las copas", sqlexception);
//                }
//        }
        return listarCopas;
    }
}
