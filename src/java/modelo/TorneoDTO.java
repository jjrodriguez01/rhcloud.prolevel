package modelo;

/**
 *
 * @author jeisson
 */
public class TorneoDTO {
    
    private int idTorneo;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private String genero;
    private int capacidadEquipos;
    private int tipo;
    private boolean tercerPuesto;
    private int equiposGrupos;
    private boolean octavosCuartosSemifinalFinalIdaVuelta;
    private boolean finalidavuelta; 
    private boolean idaVuelta;

    public int getIdTorneo() {
        return idTorneo;
    }

    public void setIdTorneo(int idTorneo) {
        this.idTorneo = idTorneo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getCapacidadEquipos() {
        return capacidadEquipos;
    }

    public void setCapacidadEquipos(int capacidadEquipos) {
        this.capacidadEquipos = capacidadEquipos;
    }

    public boolean isTercerPuesto() {
        return tercerPuesto;
    }

    public void setTercerPuesto(boolean tercerPuesto) {
        this.tercerPuesto = tercerPuesto;
    }

    public int getEquiposGrupos() {
        return equiposGrupos;
    }

    public void setEquiposGrupos(int equiposGrupos) {
        this.equiposGrupos = equiposGrupos;
    }

    public boolean isOctavosCuartosSemifinalFinalIdaVuelta() {
        return octavosCuartosSemifinalFinalIdaVuelta;
    }

    public void setOctavosCuartosSemifinalFinalIdaVuelta(boolean octavosCuartosSemifinalFinalIdaVuelta) {
        this.octavosCuartosSemifinalFinalIdaVuelta = octavosCuartosSemifinalFinalIdaVuelta;
    }

    public boolean isFinalidavuelta() {
        return finalidavuelta;
    }

    public void setFinalidavuelta(boolean finalidavuelta) {
        this.finalidavuelta = finalidavuelta;
    }

    public boolean isIdaVuelta() {
        return idaVuelta;
    }

    public void setIdaVuelta(boolean idVuelta) {
        this.idaVuelta = idaVuelta;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "TorneoDTO{" + "idTorneo=" + idTorneo + ", nombre=" + nombre + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", genero=" + genero + ", capacidadEquipos=" + capacidadEquipos + ", tipo=" + tipo + ", tercerPuesto=" + tercerPuesto + ", equiposGrupos=" + equiposGrupos + ", octavosCuartosSemifinalFinalIdaVuelta=" + octavosCuartosSemifinalFinalIdaVuelta + ", finalidavuelta=" + finalidavuelta + ", idaVuelta=" + idaVuelta + '}';
    }
    
    
    
}
