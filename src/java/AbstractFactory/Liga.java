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
import modelo.CampeonesDTO;
import modelo.EquiposdeltorneoDTO;
import modelo.PartidoDTO;
import modelo.TorneoDTO;
import persistencia.CampeonesDAO;
import persistencia.LigaDAO;
import utilidades.Conexion;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class Liga extends Torneo{

    LigaDAO liga;
    Connection conexion;

    public Liga() throws MiExcepcion {
        liga = new LigaDAO();
        conexion = Conexion.getInstance();
    }
    
    
    @Override
    public String crear(TorneoDTO torneo) {
        return liga.insertar(torneo, conexion);
    }

    @Override
    public String modificar(TorneoDTO torneo) {
        return liga.actualizar(torneo, conexion);
    }

    @Override
    public String eliminar(int id) {
        return liga.eliminar(id, conexion);
    }

    @Override
    public List listarTodo() throws MiExcepcion {
        return liga.listarTodo(conexion);
    }

    @Override
    public TorneoDTO listarUno(int id) throws MiExcepcion {
        return liga.listarUno(id, conexion);
    }
    
    /**
     * Crea los emparejamientos de una liga de 6 equipos
     * 
     * @param arr Equipos inscritos al torneo
     * @throws MiExcepcion 
     */
    public void ligaSeis(List<EquiposdeltorneoDTO> arr) throws MiExcepcion{
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
        
        //comienzo a insertar los partidos
        
        puno.setRonda(ronda);
        puno.setEquipo1(equipos.get(1).getEquipoCodigo());
        puno.setEquipo2(equipos.get(2).getEquipoCodigo());
        puno.setIdTorneo(idTorneo);
        int n1 = 1;//primer partido
        puno.setNumero(n1);
        puno.setEstado(estado);
        partido.insertarPartido(puno);
        
        //inserto al equipo 1 en tabla posiciones
        partido.posEquipo1(puno);
        //ahora inserto al equipo 2 en tabla posiciones
        partido.posEquipo2(puno);
        
        
        
        
        pdos.setRonda(ronda);
        pdos.setEquipo1(equipos.get(3).getEquipoCodigo());
        pdos.setEquipo2(equipos.get(4).getEquipoCodigo());
        pdos.setIdTorneo(idTorneo);
        int n2 = 2;//segundo partido
        pdos.setNumero(n2);
        pdos.setEstado(estado);
        partido.insertarPartido(pdos);
        
        //inserto al equipo 1 en tabla posiciones
        partido.posEquipo1(pdos);
        //ahora inserto al equipo 2 en tabla posiciones
        partido.posEquipo2(pdos);
        
        
        
        
        ptres.setRonda(ronda);
        ptres.setEquipo1(equipos.get(5).getEquipoCodigo());
        ptres.setEquipo2(equipos.get(6).getEquipoCodigo());
        ptres.setIdTorneo(idTorneo);
        int n3 = 3;
        ptres.setNumero(n3);
        ptres.setEstado(estado);
        partido.insertarPartido(ptres);
        
        //inserto al equipo 1 en tabla posiciones
        partido.posEquipo1(ptres);
        //ahora inserto al equipo 2 en tabla posiciones
        partido.posEquipo2(ptres);
        //solo se insertan una vez en la tabla posiciones en las demas rondas no hay q hacerlo
        
        
        
        //ronda 2 
        int ronda2 = 2;
        PartidoDTO puno2 = new PartidoDTO();
        PartidoDTO pdos2 = new PartidoDTO();
        PartidoDTO ptres2 = new PartidoDTO();
        
        //comienzo a insertar los partidos
        
        puno2.setRonda(ronda2);
        puno2.setEquipo1(equipos.get(1).getEquipoCodigo());
        puno2.setEquipo2(equipos.get(5).getEquipoCodigo());
        puno2.setIdTorneo(idTorneo);
        int n12 = 1;//primer partido
        puno2.setNumero(n12);
        puno2.setEstado(estado);
        partido.insertarPartido(puno2);
        
        pdos2.setRonda(ronda2);
        pdos2.setEquipo1(equipos.get(2).getEquipoCodigo());
        pdos2.setEquipo2(equipos.get(4).getEquipoCodigo());
        pdos2.setIdTorneo(idTorneo);
        int n22 = 2;//segundo partido
        pdos2.setNumero(n22);
        pdos2.setEstado(estado);
        partido.insertarPartido(pdos2);
        
        ptres2.setRonda(ronda2);
        ptres2.setEquipo1(equipos.get(3).getEquipoCodigo());
        ptres2.setEquipo2(equipos.get(6).getEquipoCodigo());
        ptres2.setIdTorneo(idTorneo);
        int n32 = 3;
        ptres2.setNumero(n32);
        ptres2.setEstado(estado);
        partido.insertarPartido(ptres2);
        
        
        
        
        //tercera ronda
        int ronda3 = 3;
        PartidoDTO puno3 = new PartidoDTO();
        PartidoDTO pdos3 = new PartidoDTO();
        PartidoDTO ptres3 = new PartidoDTO();
        
        //comienzo a insertar los partidos
        
        puno3.setRonda(ronda3);
        puno3.setEquipo1(equipos.get(2).getEquipoCodigo());
        puno3.setEquipo2(equipos.get(5).getEquipoCodigo());
        puno3.setIdTorneo(idTorneo);
        int n13 = 1;//primer partido
        puno3.setNumero(n13);
        puno3.setEstado(estado);
        partido.insertarPartido(puno3);
        
        pdos3.setRonda(ronda3);
        pdos3.setEquipo1(equipos.get(1).getEquipoCodigo());
        pdos3.setEquipo2(equipos.get(3).getEquipoCodigo());
        pdos3.setIdTorneo(idTorneo);
        int n23 = 2;//segundo partido
        pdos3.setNumero(n23);
        pdos3.setEstado(estado);
        partido.insertarPartido(pdos3);
        
        ptres3.setRonda(ronda3);
        ptres3.setEquipo1(equipos.get(6).getEquipoCodigo());
        ptres3.setEquipo2(equipos.get(4).getEquipoCodigo());
        ptres3.setIdTorneo(idTorneo);
        int n33 = 3;
        ptres3.setNumero(n33);
        ptres3.setEstado(estado);
        partido.insertarPartido(ptres3);
        
        
        
        //ronda 4
        
        int ronda4 = 4;
        PartidoDTO puno4 = new PartidoDTO();
        PartidoDTO pdos4 = new PartidoDTO();
        PartidoDTO ptres4 = new PartidoDTO();
        
        //comienzo a insertar los partidos
        
        puno4.setRonda(ronda4);
        puno4.setEquipo1(equipos.get(3).getEquipoCodigo());
        puno4.setEquipo2(equipos.get(2).getEquipoCodigo());
        puno4.setIdTorneo(idTorneo);
        int n14 = 1;//primer partido
        puno4.setNumero(n14);
        puno4.setEstado(estado);
        partido.insertarPartido(puno4);
        
        pdos4.setRonda(ronda4);
        pdos4.setEquipo1(equipos.get(6).getEquipoCodigo());
        pdos4.setEquipo2(equipos.get(1).getEquipoCodigo());
        pdos4.setIdTorneo(idTorneo);
        int n24 = 2;//segundo partido
        pdos4.setNumero(n24);
        pdos4.setEstado(estado);
        partido.insertarPartido(pdos4);
        
        ptres4.setRonda(ronda4);
        ptres4.setEquipo1(equipos.get(5).getEquipoCodigo());
        ptres4.setEquipo2(equipos.get(4).getEquipoCodigo());
        ptres4.setIdTorneo(idTorneo);
        int n34 = 3;
        ptres4.setNumero(n34);
        ptres4.setEstado(estado);
        partido.insertarPartido(ptres4);
        
        
        //ronda 5
        
        int ronda5 = 5;
        PartidoDTO puno5 = new PartidoDTO();
        PartidoDTO pdos5 = new PartidoDTO();
        PartidoDTO ptres5 = new PartidoDTO();
        
        //comienzo a insertar los partidos
        
        puno5.setRonda(ronda5);
        puno5.setEquipo1(equipos.get(4).getEquipoCodigo());
        puno5.setEquipo2(equipos.get(1).getEquipoCodigo());
        puno5.setIdTorneo(idTorneo);
        int n15 = 1;//primer partido
        puno5.setNumero(n15);
        puno5.setEstado(estado);
        partido.insertarPartido(puno5);
        
        pdos5.setRonda(ronda5);
        pdos5.setEquipo1(equipos.get(2).getEquipoCodigo());
        pdos5.setEquipo2(equipos.get(6).getEquipoCodigo());
        pdos5.setIdTorneo(idTorneo);
        int n25 = 2;//segundo partido
        pdos5.setNumero(n25);
        pdos5.setEstado(estado);
        partido.insertarPartido(pdos5);
        
        ptres5.setRonda(ronda5);
        ptres5.setEquipo1(equipos.get(3).getEquipoCodigo());
        ptres5.setEquipo2(equipos.get(5).getEquipoCodigo());
        ptres5.setIdTorneo(idTorneo);
        int n35 = 3;
        ptres5.setNumero(n35);
        ptres5.setEstado(estado);
        partido.insertarPartido(ptres5);
    }
    
    public void declareCampeonLiga(int idTorneo) throws MiExcepcion{
        LigaDAO liga = new LigaDAO();
        CampeonesDAO campeondao = new CampeonesDAO();
        CampeonesDTO campeon = new CampeonesDTO();
        campeon = liga.declararCampeon(idTorneo, conexion);
        campeondao.insertar(campeon, conexion);
    }
}
