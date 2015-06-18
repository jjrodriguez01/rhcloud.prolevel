/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractFactory;


import java.sql.Connection;
import java.util.List;
import modelo.TorneoDTO;
import persistencia.CopaDAO;
import utilidades.Conexion;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class Copa extends Torneo {
    CopaDAO cup;
    Connection conexion;

    public Copa() throws MiExcepcion {
        cup = new CopaDAO();
        conexion = Conexion.getInstance();
    }
    
    @Override
    public String crear(TorneoDTO copa){     
        return cup.insertar(copa,conexion);
    }
    @Override
    public String modificar(TorneoDTO copa){
        return cup.actualizar(copa, conexion);
    }
    @Override
    public String eliminar(int id){
        return cup.eliminar(id, conexion);
    }
    @Override
    public List listarTodo() throws MiExcepcion{
        return cup.ListarTodo(conexion);
    }

    @Override
    public TorneoDTO listarUno(int id) throws MiExcepcion {
        return cup.listarUno(id,conexion);
    }
}
