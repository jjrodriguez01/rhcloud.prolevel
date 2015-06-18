package persistencia;

import modelo.TorneoDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class EliminatoriaDAO {
    
    //instanciamos preparestatment
    private PreparedStatement statement;
    //variable que devuelve el metodo con el mensaje
    private String mensaje = "";
    //variable que cuenta las filas afectadas
    private int rtdo = 0;
    //calleableStatement
    CallableStatement call;

    ResultSet rs;
    
    public synchronized String insertar(TorneoDTO eliminatoria, Connection conexion){
        try {
        call = conexion.prepareCall("{call sp_torneoeliminatoria (?,?,?,?,?,?,?,?) }");
        call.setString(1, eliminatoria.getNombre());
        call.setString(2, eliminatoria.getFechaInicio());
        call.setString(3, eliminatoria.getFechaFin());
        call.setString(4, eliminatoria.getGenero());
        call.setInt(5, eliminatoria.getCapacidadEquipos());
        call.setInt(6, eliminatoria.getTipo());
        call.setBoolean(7, eliminatoria.isIdaVuelta());

        call.registerOutParameter(8, Types.INTEGER);
        call.execute();
        int salida = call.getInt(8);
            if (salida == 1) {
                mensaje = "Nueva eliminatoria creada.";
            }else {
                mensaje = "No se pudo crear la eliminatoria.";
            }
        }
        catch(SQLException sqle){
            mensaje = "Error :" + sqle.getMessage();
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
        return mensaje;
    } 
    
    public String eliminar(int id, Connection conexion){
        
        try {
            statement = conexion.prepareStatement("Delete from torneo where idTorneo = ?;");
            //obtenemos el id del item a eliminar del dto
            statement.setInt(1, id);
            rtdo = statement.executeUpdate();

            if (rtdo != 0) {
                System.out.println("Se elimino " + rtdo + " registro corretamente");
            } else {
                mensaje = "Ocurrió Un Error";
            }
        } catch (SQLException sqlexception) {
            mensaje = "No se pudo borrar la eliminatoria.";

        }
//        finally{
//                try {
//                    if (statement != null) {
//                        statement.close();    
//                    }
//                }catch (SQLException sqlexception) {
//                    mensaje = "No se pudo crear la eliminatoria.";
//                }
//        } 
        return mensaje;
    }
    
    public String actualizar(TorneoDTO eliminatoria, Connection conexion){
        
        try {
            statement = conexion.prepareStatement("UPDATE torneo set nombre = ?, "
                    + "fechaInicio= ?,fechaFin= ?, genero= ?, capacidadEquipos = ? WHERE idTorneo = ?");
            statement.setString(1, eliminatoria.getNombre());
            statement.setString(2, eliminatoria.getFechaInicio());
            statement.setString(3, eliminatoria.getFechaFin());
            statement.setString(4, eliminatoria.getGenero());
            statement.setInt(5, eliminatoria.getCapacidadEquipos());
            statement.setInt(6, eliminatoria.getIdTorneo());            
            rtdo = statement.executeUpdate();            
            if (rtdo != 0) {
                System.out.println("Se ha modificado la eliminatoria");
            } else {
                mensaje = "Error, no se pudo modificar";
            }
        }catch(SQLException sqle){
            mensaje = "Error: "+ sqle.getMessage();
        }
//        finally{
//                try {
//                    if (statement != null) {
//                        statement.close();    
//                    }
//                }catch (SQLException sqlexception) {
//                    mensaje = "No se pudo modificar la eliminatoria.";
//                }
//        }       
        return mensaje;
    }
     public TorneoDTO listarUno(int idTorneo, Connection conexion) throws MiExcepcion {
         TorneoDTO eliminatoria = new TorneoDTO();
        try {
            //preparamos la consulta       
            statement = conexion.prepareStatement("SELECT * from torneo where idTorneo = ?;");
            statement.setInt(1, idTorneo);
            rs = statement.executeQuery();
            //mientras halla registros
            while (rs.next()) {
                eliminatoria.setIdTorneo(rs.getInt("idTorneo"));
                eliminatoria.setNombre(rs.getString("nombre"));
                eliminatoria.setFechaInicio(rs.getString("fechaInicio"));
                eliminatoria.setFechaFin(rs.getString("fechaFin"));
                eliminatoria.setGenero(rs.getString("genero"));
                eliminatoria.setCapacidadEquipos(rs.getInt("capacidadEquipos"));
                eliminatoria.setTipo(rs.getInt("tipo"));
                eliminatoria.setIdaVuelta(rs.getBoolean("idaVuelta"));             
            }

        } catch (SQLException ex) {
            throw new MiExcepcion("Error al listar la eliminatoria",ex);
        }
//        finally{
//                try {
//                    if (statement != null) {
//                        statement.close();    
//                    }
//                }catch (SQLException sqlexception) {
//                    throw new MiExcepcion("Error al listar las copas", sqlexception);
//                }
//        }
        //devolvemos el usuario que se encontro
        return eliminatoria;
    }
    
    public List ListarTodo(Connection conexion) throws MiExcepcion{
        
        ArrayList<TorneoDTO> listarEliminatorias = new ArrayList();
        try{
            String sql= "SELECT * FROM torneo inner join eliminatoria on" +
"torneo.idTorneo = eliminatoria.idEliminatoria;";                                         
            statement = conexion.prepareStatement(sql);           
            rs = statement.executeQuery();           
            while(rs.next()){
                TorneoDTO cup = new TorneoDTO();
                
                cup.setIdTorneo(rs.getInt("idEliminatoria"));
                cup.setNombre(rs.getString("nombre"));
                cup.setFechaInicio(rs.getString("fechaInicio"));
                cup.setFechaFin(rs.getString("fechaFin"));
                cup.setGenero(rs.getString("genero"));
                cup.setCapacidadEquipos(rs.getInt("capacidadEquipos"));
                cup.setTipo(rs.getInt("tipo"));
                cup.setIdaVuelta(rs.getBoolean("idaVuelta"));
                
                listarEliminatorias.add(cup);
            }
            
        }catch(SQLException sqle){
            throw new MiExcepcion("Error al listar las eliminatorias",sqle);
        }
//        finally{
//                try {
//                    if (statement != null) {
//                        statement.close();    
//                    }
//                }catch (SQLException sqlexception) {
//                    throw new MiExcepcion("Error al listar las eliminatorias", sqlexception);
//                }
//        }
        return listarEliminatorias;
    }

}
