/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexion {
     private static Connection conexion = null;

    private static void conectar() throws MiExcepcion {
        try {
            //carga el driver para la conexion
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            //la conexion mediante el metodo getConnection toma 
            //la direccion de la bd en localhost,el usuario de bd y la contraseña 
//            conexion = DriverManager.getConnection("jdbc:mysql://127.9.104.130:3306/prolevel", "admind8617kC", "GZF2QfCShh-I");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbprolevel", "root", "j3216514086");
        } catch (SQLException sqlException) {
            throw new MiExcepcion("Error al conectar a BD", sqlException);
        } catch (Exception exception) {
            throw new MiExcepcion("Error al conectar a BD", exception);
        }
    }
    //constructor
    private Conexion() {
    }
    
    //solo permite crear un objeto y utilizarlo
    //singleton
    public static Connection getInstance() throws MiExcepcion {
        if (conexion == null) {
            conectar();
        }
        return conexion;
    }
    
}
