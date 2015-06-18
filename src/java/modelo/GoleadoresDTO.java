/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;


public class GoleadoresDTO {
private int numeroGoles;
private long  idJugador;
private int idTorneo;
private int idEquipo;
private UsuariosDTO usu;
private TorneoDTO torneo;
private EquipoDTO equipo;

    public int getNumeroGoles() {
        return numeroGoles;
    }

    public void setNumeroGoles(int numeroGoles) {
        this.numeroGoles = numeroGoles;
    }

    public long getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(long idJugador) {
        this.idJugador = idJugador;
    }

    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    @Override
    public String toString() {
        return "GoleadoresDTO{" + "numeroGoles=" + numeroGoles + ", idJugador=" + idJugador + ", idTorneo=" + idTorneo + ", idEquipo=" + idEquipo + '}';
    }

    public UsuariosDTO getUsu() {
        return usu;
    }

    public void setUsu(UsuariosDTO usu) {
        this.usu = usu;
    }

    public TorneoDTO getTorneo() {
        return torneo;
    }

    public void setTorneo(TorneoDTO torneo) {
        this.torneo = torneo;
    }

    public EquipoDTO getEquipo() {
        return equipo;
    }

    public void setEquipo(EquipoDTO equipo) {
        this.equipo = equipo;
    }

}
