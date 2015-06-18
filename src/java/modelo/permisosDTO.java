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
public class permisosDTO {
   private int idpermiso;
    private String descripcion;

    /**
     * @return the idpermiso
     */
    public int getIdpermiso() {
        return idpermiso;
    }

    /**
     * @param idpermiso the idpermiso to set
     */
    public void setIdpermiso(int idpermiso) {
        this.idpermiso = idpermiso;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "permisosDTO \n" 
     + "\n idpermiso=" + idpermiso
     + "\n descripcion=" + descripcion; 
    
}}
