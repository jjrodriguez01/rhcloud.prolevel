/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;


import java.sql.Connection;
import java.util.List;
import modelo.UsuariosDTO;
import persistencia.RolUsuarioDAO;
import persistencia.RolpermisoDAO;
import persistencia.UsuariosDAO;
import utilidades.Conexion;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class FachadaUsuarios {
    
    UsuariosDAO udao;
    RolUsuarioDAO roldao;
    RolpermisoDAO permisodao;
    Connection conexion;
    public FachadaUsuarios() throws MiExcepcion {
        udao = new UsuariosDAO();
        roldao = new RolUsuarioDAO();
        permisodao = new RolpermisoDAO();
        conexion = Conexion.getInstance();
    }
    
    public synchronized String insertarUsuario(UsuariosDTO udto) throws MiExcepcion{
        return udao.insertar(udto, conexion);
    }
    
    public String actualizarUsuario(UsuariosDTO udto) throws MiExcepcion{
        return udao.actualizar(udto, conexion);
    }
    
    public String eliminarUsuario(long id) throws MiExcepcion{
        return udao.eliminar(id, conexion);
    }
    
    public List<UsuariosDTO> listarUsuarios() throws MiExcepcion{
        return udao.listarTodo(conexion);
    }
    public List<UsuariosDTO> listarUsuariosConRoles() throws MiExcepcion{
        return udao.listarUsuariosRol(conexion);
    }
    public UsuariosDTO getUsuario(long id) throws MiExcepcion{
        return udao.listarUno(id, conexion);
    }
    
    public long validarUsuario(String email, String password) throws MiExcepcion{
        return udao.validarUsuario(email, password, conexion);
    }
    
    public boolean validarRol(UsuariosDTO udto) throws MiExcepcion{
        return udao.ValidarRol(udto, conexion);
    }
    
    public String recuperarPass(String email) throws MiExcepcion{
        return udao.recuperar(email, conexion);
    }
    
    public String cambiarPass(long id, String newpass) throws MiExcepcion{
        return udao.cambiarPass(id, newpass, conexion);
    }
    
    public StringBuilder validarDocumento(long cc, int idTorneo) throws MiExcepcion{
        return udao.validarDocumento(cc, idTorneo,conexion);
    }
    
//    AHORA LO Q ESTA EN ROLUSUARIOSDAO
    
    
    /**
     * Retorna el numero de rol del objeto UsuariosDTO
     * @param UsuariosDTO usuario a obtener umero de rol
     * @return int con el numero de rol del objeto UsuariosDTO especificado
     */
    public int getRol(UsuariosDTO udto) throws MiExcepcion{
        return roldao.getRol(udto, conexion);
    } 
    
    public String cambiarRol(int nuevoRol, long idusuario) throws MiExcepcion{
        return roldao.actualizar(nuevoRol, idusuario, conexion);
    }
    
    /**
     * Retorna el numero de rol del objeto UsuariosDTO
     * @param UsuariosDTO usuario a obtener umero de rol
     * @return int con el numero de rol del objeto UsuariosDTO especificado
     */
    public boolean existeUsuario(long idUsuario) throws MiExcepcion{
        return udao.siEstaRegistrado(idUsuario, conexion);
    } 
}
