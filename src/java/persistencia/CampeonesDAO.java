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
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.CampeonesDTO;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class CampeonesDAO {
    Connection conexion;
    //instanciamos preparestatment
    private PreparedStatement statement;
    //variable que devuelve el metodo con el mensaje
    private String mensaje = "";
    //variable que cuenta las filas afectadas
    private int rtdo = 0;

    ResultSet rs;
    
    public String insertar(CampeonesDTO win, Connection conexion) throws MiExcepcion{
        try {
            statement = conexion.prepareStatement("INSERT INTO campeones  "
                    + "VALUES (null,?,?);");
            statement.setString(1, win.getNombreTorneo());
            statement.setString(2, win.getNombreEquipo());
            rtdo = statement.executeUpdate();
            if (rtdo > 0) {
                mensaje = "Se insertó el campeon";
            }else{
                mensaje = "No se insertó el campeon";
            }
        } catch (SQLException ex) {
            throw new MiExcepcion("Error insertando campeones "+ex.getMessage(),ex);
        }
        return mensaje;
    }
    
    public String eliminar(Connection conexion) throws MiExcepcion{
        try {
            statement = conexion.prepareStatement("DELETE FROM campeones;");
            rtdo = statement.executeUpdate();
            if (rtdo > 0) {
                mensaje = "Se eliminó el historial de campeones";
            }else{
                mensaje = "No se pudo eliminar";
            }
        } catch (SQLException ex) {
            throw new MiExcepcion("Error insertando campeones "+ex.getMessage(),ex);
        }
        return mensaje;
    }
}
