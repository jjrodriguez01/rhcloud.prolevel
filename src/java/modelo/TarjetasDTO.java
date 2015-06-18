package modelo;


public class TarjetasDTO {
 private int idtorneo;
 private int tarjetaAzul;
 private int tarjetaRoja;
 private int idJugador;
private UsuariosDTO usu;
private EquipoDTO equipo;

    @Override
    public String toString() {
         return "tarjetasDTO \n" 
     + "\n idtarjeta=" + idtorneo
     + "\n tarjetaAzul=" + getTarjetaAzul()
     + "\n tarjetaRoja=" + getTarjetaRoja()
     + "\n idJugador=" + getIdJugador();
    }

    public int getIdtorneo() {
        return idtorneo;
    }

    public void setIdtorneo(int idtorneo) {
        this.idtorneo = idtorneo;
    }

    public int getTarjetaAzul() {
        return tarjetaAzul;
    }

    public void setTarjetaAzul(int tarjetaAzul) {
        this.tarjetaAzul = tarjetaAzul;
    }

    public int getTarjetaRoja() {
        return tarjetaRoja;
    }

    public void setTarjetaRoja(int tarjetaRoja) {
        this.tarjetaRoja = tarjetaRoja;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public UsuariosDTO getUsu() {
        return usu;
    }

    public void setUsu(UsuariosDTO usu) {
        this.usu = usu;
    }

    public EquipoDTO getEquipo() {
        return equipo;
    }

    public void setEquipo(EquipoDTO equipo) {
        this.equipo = equipo;
    }
 
}
