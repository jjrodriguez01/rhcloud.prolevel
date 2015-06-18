/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facade;


import java.sql.Connection;
import java.util.List;
import modelo.CampeonesDTO;
import modelo.CanchaDTO;
import modelo.EquipoDTO;
import modelo.EquiposdeltorneoDTO;
import modelo.GoleadoresDTO;
import modelo.JugadoresporequipoDTO;
import modelo.PartidoDTO;
import modelo.TablaPosicionesDTO;
import modelo.TarjetasDTO;
import modelo.TercerosDTO;
import modelo.TorneoDTO;
import persistencia.CampeonesDAO;
import persistencia.CanchaDAO;
import persistencia.EquipoDAO;
import persistencia.EquiposDelTorneoDAO;
import persistencia.GoleadoresDAO;
import persistencia.JugadoresporequipoDAO;
import persistencia.PartidoDAO;
import persistencia.TablaPosicionesDAO;
import persistencia.TarjetasDAO;
import persistencia.TercerosDAO;
import persistencia.TorneoDAO;
import utilidades.Conexion;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class FachadaTorneos {

    TorneoDAO tdao;
    CanchaDAO cdao;
    TarjetasDAO tardao;
    JugadoresporequipoDAO jequipodao;
    EquiposDelTorneoDAO edtdao;
    EquipoDAO equipodao;
    GoleadoresDAO goleadoresdao;
    PartidoDAO partidodao;
    TablaPosicionesDAO tpdao;
    TercerosDAO tercer;
    CampeonesDAO win;
    Connection conexion;
    public FachadaTorneos() throws MiExcepcion {
        tdao = new TorneoDAO();
        cdao = new CanchaDAO();
        tardao = new TarjetasDAO();
        jequipodao = new JugadoresporequipoDAO();
        equipodao = new EquipoDAO();
        edtdao = new EquiposDelTorneoDAO();
        goleadoresdao = new GoleadoresDAO();
        partidodao = new PartidoDAO();
        tpdao = new TablaPosicionesDAO();
        tercer = new TercerosDAO();
        win = new CampeonesDAO();
        conexion = Conexion.getInstance();
    }
    
//    empiezo con torneoDAO
    
    public String actualizarTorneo(TorneoDTO tdto){
        return tdao.actualizar(tdto, conexion);
    }
    
    public String eliminarTorneo(int id){
        return tdao.eliminar(id, conexion);
    }
    
    public TorneoDTO listarTorneo(int id) throws MiExcepcion{
        return tdao.listarUno(id, conexion);
    }
    
    public List<TorneoDTO> listarTorneos() throws MiExcepcion{
        return tdao.ListarTodo(conexion);
    }
    
    public List<TorneoDTO> buscar(String palabra) throws MiExcepcion{
        return tdao.buscar(palabra, conexion);
    }
    
    public boolean HayTercerPuestoEli(int idTorneo) throws MiExcepcion{
        return tdao.hayTercerPuestoEli(idTorneo, conexion);
    }
    
    public StringBuilder nombresTorneos() throws MiExcepcion{
        return tdao.nombresTorneos(conexion);
    }
    
    public StringBuilder goleTorneos() throws MiExcepcion{
        return tdao.golesTorneos(conexion);
    }
    
//    SIGO CON CANCHASDAO
    
    public String insertarCancha(CanchaDTO cancha){
        return cdao.insertar(cancha, conexion);
    }
    
    public String actualizarCancha(CanchaDTO cancha){
        return cdao.actualizar(cancha, conexion);
    }
    
    public String eliminarCancha(int numero){
        return cdao.eliminar(numero, conexion);
    }
 
    public List<CanchaDTO> listarCanchas () throws MiExcepcion{
        return cdao.listarTodo(conexion);
    }
    
    public CanchaDTO listarCancha (int numero) throws MiExcepcion{
        return cdao.listarUno(numero,conexion);
    }
    /**
     * Valida si el numero de cancha ya esta registrado
     * @param numeroCancha numero de la cancha a buscar en bd
     * @return falso si la cahcha no existe, true si ya esta en bd
     * @throws MiExcepcion 
     */
    public boolean existeCancha(int numeroCancha) throws MiExcepcion{
        return cdao.existecancha(numeroCancha, conexion);
    }
    
//    SIGO CON EQUIPODAO
    
    public String insertarEquipo(String nombre){
        return equipodao.insertar(nombre, conexion);
    }
    
    public String actualizarEquipo(EquipoDTO eq){
        return equipodao.actualizar(eq, conexion);
    }
    
    public String eliminarEquipo(int codigo){
        return equipodao.eliminar(codigo, conexion);
    }
    
    public List<EquipoDTO> listarEquiposEnTorneo(int idTorneo) throws MiExcepcion{
        return equipodao.listarTodoTorneo(idTorneo, conexion);
    }
    /**
     * busca el codigo de un equipo de acuerdo al nombre
     * @param nombre nombre del equipo a buscar en bd
     * @return 0 si no hay ningun equipo con dicho nombre o el numero del equipo
     * @throws MiExcepcion 
     */
    public int existeEquipo(String nombre) throws MiExcepcion{
        return equipodao.existeEquipo(nombre, conexion);
    }
    public EquipoDTO getEquipo(int codigo) throws MiExcepcion{
        return equipodao.listarUno(codigo, conexion);
    }
//    SIGO CON EQUIPOSDELTORNEODAO
            
    public String inscribirEquipos(int codigoequipo, int idTorneo){
        return edtdao.insertar(codigoequipo, idTorneo, conexion);
    }
    
    public List<EquiposdeltorneoDTO> listarEquiposInscritos(int idTorneo) throws MiExcepcion{
        return edtdao.listarTodo(idTorneo, conexion);
    }
    /**
     * Devuelve un List con los equipos en cuartos
     * @param idTorneo
     * @return
     * @throws MiExcepcion 
     */
    public List<EquiposdeltorneoDTO> listarEquiposEnCuartos(int idTorneo) throws MiExcepcion{
        return edtdao.listarTodoCuartos(idTorneo, conexion);
    }
     /**
     * Devuelve un List con los equipos en semis
     * @param idTorneo
     * @return
     * @throws MiExcepcion 
     */
    public List<EquiposdeltorneoDTO> listarEquiposEnSemi(int idTorneo) throws MiExcepcion{
        return edtdao.listarTodoSemi(idTorneo, conexion);
    }
    /**
     * Devuelve un List con los equipos en la final
     * @param idTorneo
     * @return
     * @throws MiExcepcion 
     */
    public List<EquiposdeltorneoDTO> listarEquiposEnFinal(int idTorneo) throws MiExcepcion{
        return edtdao.listarTodoFin(idTorneo, conexion);
    }
    
    public List<String> correosJugadoresEquipo(int idTorneo, int codigoEquipo) throws MiExcepcion{
        return edtdao.correosJugadoresEquipo(idTorneo, codigoEquipo, conexion);
    }
    
//    SIGO CON JUGADORESPOREQUIPODAO
            
    public String inscribirJugadorAEquipo(int equipo, long jugador) throws MiExcepcion{
        return jequipodao.insertar(equipo, jugador, conexion);
    }
    
    public String actualizarJugadorInscrito(int equipo, long jugador) throws MiExcepcion{
        return jequipodao.actualizar(equipo, jugador, conexion);
    }
    
    public String eliminarJugadoresEquipo(int equipo) throws MiExcepcion{
        return jequipodao.eliminar(equipo, conexion);
    }
    public String eliminarJugador(long idUsuario,int codigoEquipo) throws MiExcepcion{
        return jequipodao.eliminarJugador(idUsuario,codigoEquipo, conexion);
    }
    public List<JugadoresporequipoDTO> listarInscritos() throws MiExcepcion{
        return jequipodao.listarTodo(conexion);
    }
    
    public List<JugadoresporequipoDTO> jugadorDeEquipo(int equipo, long jugador) throws MiExcepcion{
        return jequipodao.listarUno(equipo, equipo, conexion);
    }
    
    public List<JugadoresporequipoDTO> jugadoresDeEquipo(int equipo) throws MiExcepcion{
        return jequipodao.listarJugadoresEq(equipo, conexion);
    }
    /**
    *Verifica que un usuario este en la tabla jugadoresporequipo
    *@return 0 si no encuentra jugador con el id especificado, o el numero del id si se encuentra
    */
    public long existeJugador(long idUsuario) throws MiExcepcion{
        return jequipodao.existeJugador(idUsuario, conexion);
    }
//    AHORA PARTIDODAO
    
    public String insertarPartido(PartidoDTO partido) throws MiExcepcion{
        return partidodao.insertar(partido, conexion);
    }
    /**
     * Inserta un equipo a cuertos en un torneo
     * @param idTorneo
     * @param codigoEquipo
     * @return String confirmando accion
     * @throws MiExcepcion 
     */
    public String insertarACuartos(int idTorneo, int codigoEquipo) throws MiExcepcion{
        return partidodao.insertarCuartos(idTorneo, codigoEquipo, conexion);
    }
    
    /**
     * Inserta un equipo a semifinales en un torneo
     * @param idTorneo
     * @param codigoEquipo
     * @return
     * @throws MiExcepcion 
     */
    public String insertarASemi(int idTorneo, int codigoEquipo) throws MiExcepcion{
        return partidodao.insertarSemi(idTorneo, codigoEquipo, conexion);
    }
    
    /**
     * Inserta un equipo a finales en un torneo
     * @param idTorneo
     * @param codigoEquipo
     * @return
     * @throws MiExcepcion 
     */
    public String insertarAFinal(int idTorneo, int codigoEquipo) throws MiExcepcion{
        return partidodao.insertarFinal(idTorneo, codigoEquipo, conexion);
    }
    /** 
     * 
     * Inserta los goles en la tabla partidos pasandole un  PartidoDTO con equipo1, equipo2, marcador1, marcador2, ronda, numeropartido y idTorneo
     * @param cal PartidoDTO con equipo1, equipo2, marcador1, marcador2, ronda, numeropartido y idTorneo
     * @param conexion
     * @return msj de confirmacion
     * @throws MiExcepcion 
     */
    public String insertarMarcador(PartidoDTO partido) throws MiExcepcion{
        return partidodao.insertarMarcador(partido, conexion);
    }
    
    /** 
     * 
     * valida si hay fecha u hora establecidos para un partido
     * @param cal PartidoDTO con equipo1, equipo2, y ronda
     * @param conexion
     * @return false si ya hay fechas para el partido, true caso contrario
     * @throws MiExcepcion 
     */
    public boolean validarFechas(PartidoDTO partido) throws MiExcepcion{
        return partidodao.validarFechas(partido, conexion);
    }
    
    public String actualizarPartido(PartidoDTO partido) throws MiExcepcion{
        return partidodao.actualizar(partido, conexion);
    }
    
    public String eliminarPartido(PartidoDTO partido) throws MiExcepcion{
        return partidodao.eliminar(partido, conexion);
    }
    
    public List<PartidoDTO> listarPartidos() throws MiExcepcion{
        return partidodao.listarTodo(conexion);
    }
    
    public List<PartidoDTO> listarPrimeraRonda(int idTorneo) throws MiExcepcion{
        return partidodao.listarTodoPronda(idTorneo, conexion);
    }
    
    public List<PartidoDTO> listarUnPartido(int ronda,int idTorneo) throws MiExcepcion{
        return partidodao.listarUno(ronda, idTorneo, conexion);
    }
    
    
    public int getNumeroPartido(PartidoDTO p) throws MiExcepcion{
        return partidodao.getNumeroPartido(p, conexion);
    }
//    AHORA SIGO CON TARJETAS
    
    public String insertarTarjetas(TarjetasDTO tarjeta) throws MiExcepcion{
        return tardao.insertar(tarjeta, conexion);
    }
    
    public String insertarPrimera(TarjetasDTO tarjeta) throws MiExcepcion{
        return tardao.insertarPrimer(tarjeta, conexion);
    }
    
    public String actualizarTarjetas(TarjetasDTO tarjeta) throws MiExcepcion{
        return tardao.actualizar(tarjeta, conexion);
    }
    
    public String eliminarTarjetas(long jugador, int idTorneo) throws MiExcepcion{
        return tardao.eliminar(jugador, idTorneo, conexion);
    }
    
    public List<TarjetasDTO> listarTarjetasJugador(int torneo, int jugador) throws MiExcepcion{
        return tardao.listarUno(torneo, jugador, conexion);
    }
    
    public int disminuir(int numero, int idJugador, int idTorneo) throws MiExcepcion{
        return tardao.disminuir(numero, idJugador, idTorneo, conexion);
    }
    
    public List<TarjetasDTO> listarTodoTarjetas(int idTorneo) throws MiExcepcion{
        return tardao.listarTodo(idTorneo, conexion);
    }
//    AHORA GOLEADORES DAO
    
    public String insertarGoles(GoleadoresDTO gol) throws MiExcepcion{
        return goleadoresdao.insertar(gol, conexion);
    }
    
    public List<GoleadoresDTO> existeGoleador(int idTorneo, long idJugador, int idEquipo) throws MiExcepcion{
        return goleadoresdao.listarUno(idTorneo, idJugador, idEquipo, conexion);
    }
    
    public String insertarPrimerGol(GoleadoresDTO gol) throws MiExcepcion{
        return goleadoresdao.insertarPrimer(gol, conexion);
    }
    
    public List<GoleadoresDTO> listarTodosGoleadores(int idTorneo) throws MiExcepcion{
        return goleadoresdao.listarTodo(idTorneo, conexion);
    }
//    AHORA POSICIONES
    /**
     * Actualiza la tabla de posiciones inserta los puntos y demas
     * @param p PartidoDTO con los equipos y marcadores
     * @return String de confirmacion o error
     * @throws MiExcepcion 
     */
    public String insertarPosicion(PartidoDTO p) throws MiExcepcion{
        return tpdao.insertar(p, conexion);
    }    
    /**
    * Inserta al equipo 1 en la tabla de posiciones y los numeros pertinentes para la  pos, pj,pp etc
    *@param p PartidoDTO con el id del equipo y torneo
    * @return msj de confirmacion
    * @throws MiExcepcion 
    */
    public String posEquipo1(PartidoDTO p) throws MiExcepcion{
        return tpdao.posEquipo1(p, conexion);
    } 
    
    /**
    * Inserta al equipo 2 en la tabla de posiciones y los numeros pertinentes para la  pos, pj,pp etc
    *@param p PartidoDTO con el id del equipo y torneo
    * @return msj de confirmacion
    * @throws MiExcepcion 
    */
    public String posEquipo2(PartidoDTO p) throws MiExcepcion{
        return tpdao.posEquipo2(p, conexion);
    } 
    public TablaPosicionesDTO listarUno(int idTorneo, int codigoEquipo) throws MiExcepcion{
        return tpdao.listarUno(idTorneo, codigoEquipo, conexion);
    }
    
    public List<PartidoDTO> partidosUnEquipo(int idTorneo, int codigoEquipo) throws MiExcepcion{
        return tpdao.partidosUnEquipo(idTorneo, codigoEquipo, conexion);
    }
    
    public List<TablaPosicionesDTO> listarPosiciones(int idTorneo) throws MiExcepcion{
        return tpdao.listarPosiciones(idTorneo, conexion);
    }
    
    //AHORA TERCEROSDAO
    
    public String insertarATerceros(int idTorneo, int codigoEquipo) throws MiExcepcion{
        return tercer.insertarTercero(idTorneo, codigoEquipo, conexion);
    }
    
    public List<TercerosDTO> listarPorTercerPuesto(int idTorneo) throws MiExcepcion{
        return tercer.listarTodoTerceros(idTorneo, conexion);
    }
    
    //CAMPEONES DAO
    
    public String insertarCampeon(CampeonesDTO winner) throws MiExcepcion{
        return win.insertar(winner, conexion);
    }
    
    public String eliminatHistorialCampeones() throws MiExcepcion{
        return win.eliminar(conexion);
    }
    
}
