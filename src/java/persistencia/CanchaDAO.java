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
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.CanchaDTO;
import utilidades.MiExcepcion;

public class CanchaDAO {
 
    //instanciamos preparestatment
    PreparedStatement statement;
    //variable que devuelve el metodo con el mensaje
    String mensaje = "";
    //variable que cuenta las filas afectadas
    int rtdo = 0;

    ResultSet rs;

    public String insertar(CanchaDTO can, Connection conexion) {

        try {
            statement = conexion.prepareStatement("INSERT INTO Cancha(numeroCancha,descripcion)VALUES(?,?);");
            //obtenemos los datos del dto de la tabla
            statement.setInt(1, can.getNumeroCancha());
            statement.setString(2, can.getDescripcion());
            
            //ejecuta el insert
            rtdo = statement.executeUpdate();
            //si se afectaron campos 
            if (rtdo != 0) {
                mensaje = "Se inserto la cancha";
                //si no se afecto la tabla
            } else {
                mensaje = "Error";
            }
        } 
        catch (SQLException sqlexception) {
         mensaje = "Ha ocurrido un error "+ sqlexception.getMessage();
        }
        return mensaje;
    }

    public String actualizar(CanchaDTO can, Connection conexion) {
        try {
            statement = conexion.prepareStatement("UPDATE Cancha SET numeroCancha=?, descripcion=? "
                    + "WHERE numeroCancha=?;");
            statement.setInt(1, can.getNumeroCancha());
            statement.setString(2, can.getDescripcion());
            statement.setInt(3, can.getNumeroCancha());
            //el resulset trae el numero de rows afectadas
            rtdo = statement.executeUpdate();
            if (rtdo != 0) {

               mensaje="Se actualizo la cancha";

            } else {
                mensaje = "Error";
            }
        } catch (SQLException sqlexception) {
         mensaje = "Ha ocurrido un error "+ sqlexception.getMessage();
        }
        
        return mensaje;

    }

    public String eliminar(int numero, Connection conexion) {
        try {
            statement = conexion.prepareStatement("Delete from cancha where numeroCancha=?;");
            //obtenemos el id del item a eliminar del dto
            statement.setInt(1, numero);
            rtdo = statement.executeUpdate();

            if (rtdo != 0) {
               mensaje="Se elimino la cancha";
            } else {
                mensaje = "Ha ocurrido un error";
            }
        } catch (SQLException sqlexception) {
            mensaje = "Ha ocurrido un error";

        }

        return mensaje;
    }

    public List<CanchaDTO> listarTodo(Connection conexion) throws MiExcepcion {
        //creamos el array que va a contener los datos de la consulta    
        ArrayList<CanchaDTO> listarCancha = new ArrayList();

        try {
            String query = "SELECT  cancha as numeroCancha, descripcion "
                    + " FROM cancha; ";
            statement = conexion.prepareStatement(query);
            rs = statement.executeQuery();
            //mientras que halla registros cree un nuevo dto y pasele la info
            while (rs.next()) {
                //crea un nuevo dto
                CanchaDTO can = new CanchaDTO();
                //le pasamos los datos que se encuentren
                can.setNumeroCancha(rs.getInt("numeroCancha"));
                can.setDescripcion(rs.getString("descripcion"));
               
                //agregamos el objeto dto al arreglo
                listarCancha.add(can);

            }
        } catch (SQLException sqlexception) {
            throw new MiExcepcion("Error al listar las canchas", sqlexception);

        }
        //devolvemos el arreglo
        return listarCancha;

    }

    public CanchaDTO listarUno(int numeroCancha, Connection conexion) throws MiExcepcion {
        CanchaDTO can = new CanchaDTO();
        try {
            //preparamos la consulta 
            statement = conexion.prepareStatement("SELECT numeroCancha,descripcion FROM cancha"
                    + "WHERE numeroCancha =? ;");
            statement.setInt(1, numeroCancha);
            rs = statement.executeQuery();
            //mientras halla registros
            while (rs.next()) {
                can.setNumeroCancha(rs.getInt("numeroCancha"));
                can.setDescripcion(rs.getString("descripcion"));
            }

        } catch (SQLException ex) {
            throw new MiExcepcion("Error al listar la cancha", ex);
        }
        //devolvemos el usuario que se encontro
        return can;
    }
    
    public boolean existecancha(int numeroCancha, Connection conexion) throws MiExcepcion {
        boolean existe = false;
        try {
            //preparamos la consulta 
            statement = conexion.prepareStatement("SELECT numeroCancha,descripcion FROM cancha "
                    + "WHERE numeroCancha=?;");
            statement.setInt(1, numeroCancha);
            rs = statement.executeQuery();
            //mientras halla registros
            while (rs.next()) {
                existe = true;
            }

        } catch (SQLException ex) {
            throw new MiExcepcion("Error al listar la cancha"+ex.getMessage(), ex);
        }
        //devolvemos el usuario que se encontro
        return existe;
    }

}   

