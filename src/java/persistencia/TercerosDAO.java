/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.TercerosDTO;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class TercerosDAO {
    
    //instanciamos preparestatment
    PreparedStatement statement;
    //variable que devuelve el metodo con el mensaje
    String mensaje = "";
    //variable que cuenta las filas afectadas
    int rtdo = 0;

    ResultSet rs;
    
     public synchronized String insertarTercero(int idTorneo, int codigoequipo, Connection conexion) throws MiExcepcion{
        try {
            statement = conexion.prepareStatement("INSERT INTO terceros (idTorneo, codigoEquipo)"
                    + " VALUES (?,?);");
            statement.setInt(1, idTorneo);
            statement.setInt(2, codigoequipo);
            rtdo = statement.executeUpdate();
            if (rtdo > 0) {
                mensaje ="Se inserto el equipo a tercer puestos";
            }else{
                mensaje = "No se inserto el equipo";
            }
            
        } catch (SQLException ex) {
            throw new MiExcepcion("No se inserto el equipo a terceros error: "+ex.getMessage(), ex);
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
    
     public List<TercerosDTO> listarTodoTerceros(int idTorneo, Connection conexion) throws MiExcepcion{
        ArrayList<TercerosDTO> equipos = new ArrayList();
        try {
            statement = conexion.prepareStatement("SELECT idTorneo, codigoEquipo "
                    + "FROM terceros WHERE idTorneo =?;");
            statement.setInt(1, idTorneo);
            rs = statement.executeQuery();
            while(rs.next()){
                TercerosDTO eq = new TercerosDTO();
                eq.setIdTorneo(rs.getInt("idTorneo"));
                eq.setCodigoEquipo(rs.getInt("codigoEquipo"));
                equipos.add(eq);
            }
        } catch (SQLException ex) {
            throw new MiExcepcion("Error", ex);
        }
//        finally{
//            try {
//                if (statement != null) {
//                        statement.close();    
//                    }
//            } catch (SQLException ex) {
//                throw new MiExcepcion("Error cerrando prepared ",ex);
//            }
//        }
        return equipos;
    }
}
