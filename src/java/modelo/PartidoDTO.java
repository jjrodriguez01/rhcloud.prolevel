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
public class PartidoDTO {
    private int ronda;
    private int marcador1;
    private int equipo1;
    private int equipo2;
    private int marcador2;
    private String fecha;
    private String hora;
    private int idTorneo;
    private int cancha;
    private EquipoDTO equipouno;
    private EquipoDTO equipodos;
    private TorneoDTO torneo;
    private CanchaDTO canchas;
    private int numero;
    private int estado;

    public int getRonda() {
        return ronda;
    }

    public void setRonda(int ronda) {
        this.ronda = ronda;
    }

    public int getMarcador1() {
        return marcador1;
    }

    public void setMarcador1(int marcador1) {
        this.marcador1 = marcador1;
    }

    public int getEquipo1() {
        return equipo1;
    }

    public void setEquipo1(int equipo1) {
        this.equipo1 = equipo1;
    }

    public int getEquipo2() {
        return equipo2;
    }

    public void setEquipo2(int equipo2) {
        this.equipo2 = equipo2;
    }

    public int getMarcador2() {
        return marcador2;
    }

    public void setMarcador2(int marcador2) {
        this.marcador2 = marcador2;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    public int getCancha() {
        return cancha;
    }

    public void setCancha(int cancha) {
        this.cancha = cancha;
    }

    public EquipoDTO getEquipouno() {
        return equipouno;
    }

    public void setEquipouno(EquipoDTO equipouno) {
        this.equipouno = equipouno;
    }

    public EquipoDTO getEquipodos() {
        return equipodos;
    }

    public void setEquipodos(EquipoDTO equipodos) {
        this.equipodos = equipodos;
    }

    public TorneoDTO getTorneo() {
        return torneo;
    }

    public void setTorneo(TorneoDTO torneo) {
        this.torneo = torneo;
    }

    public CanchaDTO getCanchas() {
        return canchas;
    }

    public void setCanchas(CanchaDTO canchas) {
        this.canchas = canchas;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    
   
}
    

