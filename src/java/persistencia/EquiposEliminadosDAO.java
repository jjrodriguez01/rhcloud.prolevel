/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import modelo.Equipos_eliminadosDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.util.List;

public class EquiposEliminadosDAO {

    //instanciamos preparestatment
    PreparedStatement statement;
    //variable que devuelve el metodo con el mensaje
    String mensaje = "";
    //variable que cuenta las filas afectadas
    int rtdo = 0;

    ResultSet rs;


    public synchronized String insertar(Equipos_eliminadosDTO ee, Connection conexion) {

        try {
            //sentencia sql
            String sql = "INSERT INTO Equipos_eliminados(CodigoEquipo,IdTorneo)VALUES(?,?)";
            //pasamos la sentencia la conexion mediante el prepare staement
            statement = conexion.prepareStatement(sql);
            //obtenemos los datos del dto de la tabla
            statement.setInt(1, ee.getCodigoEquipo());
            statement.setInt(2, ee.getIdTorneo());
           
            //ejecuta el insert
            rtdo = statement.executeUpdate();
            //si se afectaron campos 
            if (rtdo != 0) {
                mensaje = "Se elimino el equipo";
                //si no se afecto la tabla
            } else {
                mensaje = "Error, no se pudo eliminar";
            }
        } 
        catch (SQLException sqlexception) {
            mensaje = "Ha ocurrido un error "+ sqlexception.getMessage();
        }
        //devolvemos el mensaje al usuario
        return mensaje;
    }

    public String actulizar(Equipos_eliminadosDTO ee, Connection conexion) {
        try {
            //preparamos la sentencia sql
            String sql = "UPDATE Equipos_eliminados SET CodigoEquipo=?,IdTorneo=? "
                    + "WHERE CodigoEquipo=? and idTorneo = ?;";
            //pasamos el query a la conexion
           //sacamos los datos del dto de la tabla
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, ee.getCodigoEquipo());
            statement.setInt(2, ee.getIdTorneo());
            statement.setInt(3, ee.getCodigoEquipo());
            statement.setInt(4, ee.getIdTorneo());
           
            //el resulset trae el numero de rows afectadas
            rtdo = statement.executeUpdate();
            if (rtdo != 0) {

              mensaje="Se actualizo correctamente";

            } else {
                mensaje = "Error, no se pudo actualizar";
            }
        } catch (SQLException sqlexception) {
            mensaje = "Ha ocurrido un error "+ sqlexception.getMessage();

        }

        return mensaje;

    }

    public String eliminar(int codigoEquipo, int idTorneo, Connection conexion) {
        try {
            statement = conexion.prepareStatement("Delete from Equipos_eliminados"
                    + " where CodigoEquipo=? and idTorneo=?;");
            //obtenemos el id del item a eliminar del dto
            statement.setInt(1, codigoEquipo);
            statement.setInt(1, idTorneo);
            rtdo = statement.executeUpdate();

            if (rtdo != 0) {
                mensaje="Se elimino correctamente";
            } else {
                mensaje = "Ocurrio Un Error, No Se Pudo Eliminar";
            }
        } catch (SQLException sqlexception) {
            mensaje = "Ha ocurrido un error "+ sqlexception.getMessage();

        }

        return mensaje;
    }

    public List<Equipos_eliminadosDTO> listarTodo(Connection conexion) {
        //creamos el array que va a contener los datos de la consulta    
        ArrayList<Equipos_eliminadosDTO> listar = new ArrayList();

        try {
            String query = "SELECT   CodigoEquipo, idTorneo"
                    + " FROM Equipos_eliminados;";
            statement = conexion.prepareStatement(query);
            rs = statement.executeQuery();
            //mientras que halla registros cree un nuevo dto y pasele la info
            while (rs.next()) {
                //crea un nuevo dto
               Equipos_eliminadosDTO ee = new Equipos_eliminadosDTO();
                //le pasamos los datos que se encuentren
                ee.setCodigoEquipo(rs.getInt("CodigoEquipo"));
                ee.setIdTorneo(rs.getInt("idTorneo"));
                //agregamos el objeto dto al arreglo
                listar.add(ee);

            }
        } catch (SQLException sqlexception) {
           mensaje = "Ha ocurrido un error "+ sqlexception.getMessage();

        } finally {

        }
        //devolvemos el arreglo
        return listar;

    }

    public List<Equipos_eliminadosDTO> listarUno(int codigoEquipo, int idTorneo, Connection conexion) {
        ArrayList<Equipos_eliminadosDTO> listar = new ArrayList();
        try {
            //preparamos la consulta 
            statement = conexion.prepareStatement("SELECT CodigoEquipo,idTorneo FROM Equipos_eliminados "
                    + "WHERE codigoEquipo = ? and idTorneo = ? ;");
            statement.setInt(1, codigoEquipo);
            statement.setInt(1, idTorneo);
            rs = statement.executeQuery();
            //mientras halla registros
            while (rs.next()) {
                Equipos_eliminadosDTO ee = new Equipos_eliminadosDTO();
                //le pasamos los datos que se encuentren
                ee.setCodigoEquipo(rs.getInt("CodigoEquipo"));
                ee.setIdTorneo(rs.getInt("idTorneo"));
                //agregamos el objeto dto al arreglo
                listar.add(ee);
            }

        } catch (SQLException ex) {
            mensaje = "Error inesperado: " + ex.getMessage() + " codigo de error " + ex.getErrorCode();
        }
        //devolvemos el usuario que se encontro
        return listar;
    }

}

