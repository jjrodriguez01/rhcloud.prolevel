/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author Rambo
 */
public class RolpermisoDTO {
   private int idRol;
   private int idPermiso;
   private String nombre;

    /**
     * @return the idRol
     */
    public int getIdRol() {
        return idRol;
    }

    /**
     * @param idRol the idRol to set
     */
    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    /**
     * @return the idPermiso
     */
    public int getIdPermiso() {
        return idPermiso;
    }

    /**
     * @param idPermiso the idPermiso to set
     */
    public void setIdPermiso(int idPermiso) {
        this.idPermiso = idPermiso;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
       return "RolpermisoDTO \n" 
     + "\n idPermiso=" + idPermiso
     + "\n nombre=" + nombre; 
    }
  
}
