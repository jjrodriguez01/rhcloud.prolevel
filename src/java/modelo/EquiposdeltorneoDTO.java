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
public class EquiposdeltorneoDTO {
private int equipoCodigo;
private int torneoIdTorneo;

    /**
     * @return the equipoCodigo
     */
    public int getEquipoCodigo() {
        return equipoCodigo;
    }

    /**
     * @param equipoCodigo the equipoCodigo to set
     */
    public void setEquipoCodigo(int equipoCodigo) {
        this.equipoCodigo = equipoCodigo;
    }

    /**
     * @return the torneoIdTorneo
     */
    public int getTorneoIdTorneo() {
        return torneoIdTorneo;
    }

    /**
     * @param torneoIdTorneo the torneoIdTorneo to set
     */
    public void setTorneoIdTorneo(int torneoIdTorneo) {
        this.torneoIdTorneo = torneoIdTorneo;
    }

    @Override
    public String toString() {
        return "EquiposdeltorneoDTO" 
                + "\nequipoCodigo=" + equipoCodigo 
                + "\ntorneoIdTorneo=" + torneoIdTorneo;
    }

    

}
