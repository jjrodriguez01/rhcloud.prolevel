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
public class JugadoresporequipoDTO {
    private String nombreEquipo;
    private String nombreJugador;
    private int codigoEquipo;
    private int codigoJugador;

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public int getCodigoEquipo() {
        return codigoEquipo;
    }

    public void setCodigoEquipo(int codigoEquipo) {
        this.codigoEquipo = codigoEquipo;
    }

    public int getCodigoJugador() {
        return codigoJugador;
    }

    public void setCodigoJugador(int codigoJugador) {
        this.codigoJugador = codigoJugador;
    }

    @Override
    public String toString() {
        return "JugadoresporequipoDTO{" 
                + "\nnombreEquipo=" + nombreEquipo 
                + "\nnombreJugador=" + nombreJugador 
                + "\ncodigoEquipo=" + codigoEquipo 
                + "\ncodigoJugador=" + codigoJugador + '}';
    }
    
}
