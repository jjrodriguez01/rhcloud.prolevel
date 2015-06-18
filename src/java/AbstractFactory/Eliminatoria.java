/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractFactory;


import facade.FachadaTorneos;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import modelo.EquiposdeltorneoDTO;
import modelo.PartidoDTO;
import modelo.TercerosDTO;
import modelo.TorneoDTO;
import persistencia.EliminatoriaDAO;
import utilidades.Conexion;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class Eliminatoria extends Torneo {

    EliminatoriaDAO eli;
    Connection conexion;

    public Eliminatoria() throws MiExcepcion {
        eli = new EliminatoriaDAO();
        conexion = Conexion.getInstance();
    }
    
    
    @Override
    public String crear(TorneoDTO torneo) {
        return eli.insertar(torneo,conexion);
    }

    @Override
    public String modificar(TorneoDTO torneo) {
        return eli.actualizar(torneo,conexion);
    }

    @Override
    public String eliminar(int id) {
        return eli.eliminar(id,conexion);
    }

    @Override
    public List listarTodo() throws MiExcepcion {
        return eli.ListarTodo(conexion);
    }

    @Override
    public TorneoDTO listarUno(int id) throws MiExcepcion {
        return eli.listarUno(id,conexion);
    }
    /**
     * Crea los emparejamientos de una eliminatoria de 16 equipos
     * 
     * @param arr Equipos inscritos al torneo
     * @throws MiExcepcion 
     */
    public void primeraRondaDiesciseis(List<EquiposdeltorneoDTO> arr) throws MiExcepcion{
        ArrayList<EquiposdeltorneoDTO> arrayeq = (ArrayList)arr;
        Map<Integer,EquiposdeltorneoDTO> equipos = new TreeMap<Integer,EquiposdeltorneoDTO>();
        int clave = 0;
        for(EquiposdeltorneoDTO eq : arrayeq){
            clave++;
            equipos.put(clave, eq);
        }
        int ronda = 1;
        int idTorneo = equipos.get(1).getTorneoIdTorneo();
        int estado = 0;//estado del partido 0=por jugar
        FachadaTorneos partido = new FachadaTorneos();
        //instancio la cantidad de partidos que necesito
        //para un torneo de 16 equipos seran 8 en primera ronda
        PartidoDTO puno = new PartidoDTO();
        PartidoDTO pdos = new PartidoDTO();
        PartidoDTO ptres = new PartidoDTO();
        PartidoDTO pcuatro = new PartidoDTO();
        PartidoDTO pcinco = new PartidoDTO();
        PartidoDTO pseis = new PartidoDTO();
        PartidoDTO psiete = new PartidoDTO();
        PartidoDTO pocho = new PartidoDTO();
        
        //comienzo a insertar los partidos
        
        puno.setRonda(ronda);
        puno.setEquipo1(equipos.get(1).getEquipoCodigo());
        puno.setEquipo2(equipos.get(2).getEquipoCodigo());
        puno.setIdTorneo(idTorneo);
        int n1 = 1;//primer partido
        puno.setNumero(n1);
        puno.setEstado(estado);
        partido.insertarPartido(puno);
        
        pdos.setRonda(ronda);
        pdos.setEquipo1(equipos.get(3).getEquipoCodigo());
        pdos.setEquipo2(equipos.get(4).getEquipoCodigo());
        pdos.setIdTorneo(idTorneo);
        int n2 = 2;//segundo partido
        pdos.setNumero(n2);
        pdos.setEstado(estado);
        partido.insertarPartido(pdos);
        
        ptres.setRonda(ronda);
        ptres.setEquipo1(equipos.get(5).getEquipoCodigo());
        ptres.setEquipo2(equipos.get(6).getEquipoCodigo());
        ptres.setIdTorneo(idTorneo);
        int n3 = 3;
        ptres.setNumero(n3);
        ptres.setEstado(estado);
        partido.insertarPartido(ptres);
        
        pcuatro.setRonda(ronda);
        pcuatro.setEquipo1(equipos.get(7).getEquipoCodigo());
        pcuatro.setEquipo2(equipos.get(8).getEquipoCodigo());
        pcuatro.setIdTorneo(idTorneo);
        int n4 = 4;
        pcuatro.setNumero(n4);
        pcuatro.setEstado(estado);
        partido.insertarPartido(pcuatro);
        
        pcinco.setRonda(ronda);
        pcinco.setEquipo1(equipos.get(9).getEquipoCodigo());
        pcinco.setEquipo2(equipos.get(10).getEquipoCodigo());
        pcinco.setIdTorneo(idTorneo);
        int n5 = 5;
        pcinco.setNumero(n5);
        pcinco.setEstado(estado);
        partido.insertarPartido(pcinco);
        
        pseis.setRonda(ronda);
        pseis.setEquipo1(equipos.get(11).getEquipoCodigo());
        pseis.setEquipo2(equipos.get(12).getEquipoCodigo());
        pseis.setIdTorneo(idTorneo);
        int n6 = 6;
        pseis.setNumero(n6);
        pseis.setEstado(estado);
        partido.insertarPartido(pseis);
        
        psiete.setRonda(ronda);
        psiete.setEquipo1(equipos.get(13).getEquipoCodigo());
        psiete.setEquipo2(equipos.get(14).getEquipoCodigo());
        psiete.setIdTorneo(idTorneo);
        int n7 = 7;
        psiete.setNumero(n7);
        psiete.setEstado(estado);
        partido.insertarPartido(psiete);
        
        pocho.setRonda(ronda);
        pocho.setEquipo1(equipos.get(15).getEquipoCodigo());
        pocho.setEquipo2(equipos.get(16).getEquipoCodigo());
        pocho.setIdTorneo(idTorneo);
        int n8 = 8;
        pocho.setNumero(n8);
        pocho.setEstado(estado);
        partido.insertarPartido(pocho);
    }
    
    
    public void segundaRondaDiesciseis(List<EquiposdeltorneoDTO> arr) throws MiExcepcion{
        ArrayList<EquiposdeltorneoDTO> arrayeq = (ArrayList)arr;
        Map<Integer,EquiposdeltorneoDTO> equipos = new TreeMap<Integer,EquiposdeltorneoDTO>();
        int clave = 0;
        for(EquiposdeltorneoDTO eq : arrayeq){
            clave++;
            equipos.put(clave, eq);
        }
        int ronda = 2;
        int idTorneo = equipos.get(1).getTorneoIdTorneo();
        int estado = 0;//estado del partido 0=por jugar
        FachadaTorneos partido = new FachadaTorneos();
        //instancio la cantidad de partidos que necesito
        //para un torneo de 16 equipos seran 4 en segunda ronda
        PartidoDTO puno = new PartidoDTO();
        PartidoDTO pdos = new PartidoDTO();
        PartidoDTO ptres = new PartidoDTO();
        PartidoDTO pcuatro = new PartidoDTO();
        
        //comienzo a insertar los partidos
        
        puno.setRonda(ronda);
        puno.setEquipo1(equipos.get(1).getEquipoCodigo());
        puno.setEquipo2(equipos.get(2).getEquipoCodigo());
        puno.setIdTorneo(idTorneo);
        int n1 = 1;//primer partido
        puno.setNumero(n1);
        puno.setEstado(estado);
        partido.insertarPartido(puno);
        
        pdos.setRonda(ronda);
        pdos.setEquipo1(equipos.get(3).getEquipoCodigo());
        pdos.setEquipo2(equipos.get(4).getEquipoCodigo());
        pdos.setIdTorneo(idTorneo);
        int n2 = 2;//segundo partido
        pdos.setNumero(n2);
        pdos.setEstado(estado);
        partido.insertarPartido(pdos);
        
        ptres.setRonda(ronda);
        ptres.setEquipo1(equipos.get(5).getEquipoCodigo());
        ptres.setEquipo2(equipos.get(6).getEquipoCodigo());
        ptres.setIdTorneo(idTorneo);
        int n3 = 3;
        ptres.setNumero(n3);
        ptres.setEstado(estado);
        partido.insertarPartido(ptres);
        
        pcuatro.setRonda(ronda);
        pcuatro.setEquipo1(equipos.get(7).getEquipoCodigo());
        pcuatro.setEquipo2(equipos.get(8).getEquipoCodigo());
        pcuatro.setIdTorneo(idTorneo);
        int n4 = 4;
        pcuatro.setNumero(n4);
        pcuatro.setEstado(estado);
        partido.insertarPartido(pcuatro);
        
        
    }
    
    public void terceraRondaDiesciseis(List<EquiposdeltorneoDTO> arr) throws MiExcepcion{
        ArrayList<EquiposdeltorneoDTO> arrayeq = (ArrayList)arr;
        Map<Integer,EquiposdeltorneoDTO> equipos = new TreeMap<Integer,EquiposdeltorneoDTO>();
        int clave = 0;
        for(EquiposdeltorneoDTO eq : arrayeq){
            clave++;
            equipos.put(clave, eq);
        }
        int ronda = 3;
        int idTorneo = equipos.get(1).getTorneoIdTorneo();
        int estado = 0;//estado del partido 0=por jugar
        FachadaTorneos partido = new FachadaTorneos();
        //instancio la cantidad de partidos que necesito
        //para un torneo de 16 equipos seran 4 en segunda ronda
        PartidoDTO puno = new PartidoDTO();
        PartidoDTO pdos = new PartidoDTO();
        
        //comienzo a insertar los partidos
        
        puno.setRonda(ronda);
        puno.setEquipo1(equipos.get(1).getEquipoCodigo());
        puno.setEquipo2(equipos.get(2).getEquipoCodigo());
        puno.setIdTorneo(idTorneo);
        int n1 = 1;//primer partido
        puno.setNumero(n1);
        puno.setEstado(estado);
        partido.insertarPartido(puno);
        
        pdos.setRonda(ronda);
        pdos.setEquipo1(equipos.get(3).getEquipoCodigo());
        pdos.setEquipo2(equipos.get(4).getEquipoCodigo());
        pdos.setIdTorneo(idTorneo);
        int n2 = 2;//segundo partido
        pdos.setNumero(n2);
        pdos.setEstado(estado);
        partido.insertarPartido(pdos);
        
    }
    
    public void cuartaRondaDiesciseis(List<EquiposdeltorneoDTO> arr) throws MiExcepcion{
        ArrayList<EquiposdeltorneoDTO> arrayeq = (ArrayList)arr;
        Map<Integer,EquiposdeltorneoDTO> equipos = new TreeMap<Integer,EquiposdeltorneoDTO>();
        int clave = 0;
        for(EquiposdeltorneoDTO eq : arrayeq){
            clave++;
            equipos.put(clave, eq);
        }
        int ronda = 4;
        int idTorneo = equipos.get(1).getTorneoIdTorneo();
        int estado = 0;//estado del partido 0=por jugar
        FachadaTorneos partido = new FachadaTorneos();
        //instancio la cantidad de partidos que necesito
        //para un torneo de 16 equipos seran 4 en segunda ronda
        PartidoDTO puno = new PartidoDTO();
        
        //comienzo a insertar los partidos
        
        puno.setRonda(ronda);
        puno.setEquipo1(equipos.get(1).getEquipoCodigo());
        puno.setEquipo2(equipos.get(2).getEquipoCodigo());
        puno.setIdTorneo(idTorneo);
        int n1 = 1;//primer partido
        puno.setNumero(n1);
        puno.setEstado(estado);
        partido.insertarPartido(puno);
        //miro si hay necesidad de tercer puesto
        boolean hayTercerPuesto = partido.HayTercerPuestoEli(idTorneo);
        //si es true hago el tercer puesto
        if (hayTercerPuesto) {
            ArrayList<TercerosDTO> t = new ArrayList();
            t = (ArrayList<TercerosDTO>) partido.listarPorTercerPuesto(idTorneo);
            porTercerPuesto(t);
        }
        
    }
    
    public void porTercerPuesto(List<TercerosDTO> arr) throws MiExcepcion{
        ArrayList<TercerosDTO> arrayeq = (ArrayList)arr;
        Map<Integer,TercerosDTO> equipos = new TreeMap<Integer,TercerosDTO>();
        int clave = 0;
        for(TercerosDTO eq : arrayeq){
            clave++;
            equipos.put(clave, eq);
        }
        int ronda = 0;
        int idTorneo = equipos.get(1).getIdTorneo();
        int estado = 0;//estado del partido 0=por jugar
        FachadaTorneos partido = new FachadaTorneos();
        //instancio la cantidad de partidos que necesito
        //para un torneo de 16 equipos seran 4 en segunda ronda
        PartidoDTO puno = new PartidoDTO();
        
        //comienzo a insertar los partidos
        
        puno.setRonda(ronda);
        puno.setEquipo1(equipos.get(1).getCodigoEquipo());
        puno.setEquipo2(equipos.get(2).getCodigoEquipo());
        puno.setIdTorneo(idTorneo);
        int n1 = 1;//primer partido
        puno.setNumero(n1);
        puno.setEstado(estado);
        partido.insertarPartido(puno);
        
    }
}