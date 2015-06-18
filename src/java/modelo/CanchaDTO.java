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
public class CanchaDTO {
    private int numeroCancha;
    private String descripcion;

    /**
     * @return the numeroCancha
     */
    public int getNumeroCancha() {
        return numeroCancha;
    }

    /**
     * @param numeroCancha the numeroCancha to set
     */
    public void setNumeroCancha(int numeroCancha) {
        this.numeroCancha = numeroCancha;
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
        return "CanchaDTO \n" 
     + "\n numeroCancha=" + numeroCancha
     + "\n descripcion=" + descripcion ;
    }
    
}
   
