/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


public class Equipos_eliminadosDTO {
   private int CodigoEquipo;
   private int idTorneo;

    /**
     * @return the CodigoEquipo
     */
    public int getCodigoEquipo() {
        return CodigoEquipo;
    }

    /**
     * @param CodigoEquipo the CodigoEquipo to set
     */
    public void setCodigoEquipo(int CodigoEquipo) {
        this.CodigoEquipo = CodigoEquipo;
    }

    /**
     * @return the idTorneo
     */
    public int getIdTorneo() {
        return idTorneo;
    }

    /**
     * @param idTorneo the idTorneo to set
     */
    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    @Override
    public String toString() {
       return "Equipos_eliminadosDTO \n" 
     + "\n CodigoEquipo=" + CodigoEquipo
     + "\n Idtorneo=" + idTorneo;
    }
    
}
