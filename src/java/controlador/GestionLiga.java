
package controlador;

import AbstractFactory.FabricaTorneo;
import AbstractFactory.Liga;
import AbstractFactory.Torneo;
import FactoryMethod.TorneoFactory;
import controlador.correo.Correo;
import facade.FachadaTorneos;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.EquiposdeltorneoDTO;
import modelo.PartidoDTO;
import modelo.TablaPosicionesDTO;
import modelo.TorneoDTO;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class GestionLiga extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, MiExcepcion {
        response.setContentType("text/html;charset=UTF-8");
        if ( request.getParameter("liga")!=null && request.getParameter("enviarliga")!=null) {
           TorneoDTO ligdto = new TorneoDTO();
            int tipotorneo = 2;//en bd dos es una liga
            ligdto.setIdTorneo(0);
            ligdto.setCapacidadEquipos(Integer.parseInt(request.getParameter("capacidad")));
            ligdto.setFechaFin(request.getParameter("fin"));
            ligdto.setFechaInicio(request.getParameter("inicio"));
            ligdto.setGenero(request.getParameter("tipo"));
            ligdto.setTipo(tipotorneo);
            ligdto.setIdaVuelta(true);
            ligdto.setNombre(request.getParameter("nombreTorneo"));
            TorneoFactory fabrica = new TorneoFactory();
            Torneo liga = fabrica.crearTorneo(ligdto);//la fabrica toma el tipo de torneo y como es dos me crea una liga
            String crearliga = liga.crear(ligdto);         
            response.sendRedirect("paginas/torneos/crear_torneo.jsp?liga="+crearliga+"#ftorneos");
        }
        //
        //si van a iniciar la liga
        //
        else if(request.getParameter("iniciar")!=null && request.getParameter("liga")!=null){
             TorneoDTO ligdto = new TorneoDTO();
             FachadaTorneos facadeTorneos = new FachadaTorneos();
            int tipotorneo = 2;//en bd dos es una liga
            ligdto.setIdTorneo(Integer.parseInt(request.getParameter("idTorneo")));
            ligdto.setCapacidadEquipos(Integer.parseInt(request.getParameter("capacidadEquipos")));
            ligdto.setFechaFin(request.getParameter("fechaFin"));
            ligdto.setFechaInicio(request.getParameter("fechaInicio"));
            ligdto.setGenero(request.getParameter("genero"));
            ligdto.setTipo(tipotorneo);
            ligdto.setIdaVuelta(true);
            ligdto.setNombre(request.getParameter("nombre"));
            FabricaTorneo fabrica = new FabricaTorneo();
            Liga liga = fabrica.creaLiga(ligdto);
            List<EquiposdeltorneoDTO> edt = new ArrayList();//arrayList con los equipos de este torneo
            //ese metodo me devuelve un List con todos los equiposn de este torneo
            edt = facadeTorneos.listarEquiposInscritos(ligdto.getIdTorneo());
            liga.ligaSeis(edt);
            response.sendRedirect("paginas/torneos/calendario.jsp?idTorneo="+ligdto.getIdTorneo());
        }
        else if(request.getParameter("campeonliga")!=null && request.getParameter("declarecampeon")!=null){
             TorneoDTO ligdto = new TorneoDTO();
            int tipotorneo = 2;//en bd dos es una liga
            ligdto.setIdTorneo(Integer.parseInt(request.getParameter("idTorneo")));
            ligdto.setCapacidadEquipos(Integer.parseInt(request.getParameter("capacidadEquipos")));
            ligdto.setFechaFin(request.getParameter("fechaFin"));
            ligdto.setFechaInicio(request.getParameter("fechaInicio"));
            ligdto.setGenero(request.getParameter("genero"));
            ligdto.setTipo(tipotorneo);
            ligdto.setIdaVuelta(true);
            ligdto.setNombre(request.getParameter("nombre"));
            FabricaTorneo fabrica = new FabricaTorneo();
            Liga liga = fabrica.creaLiga(ligdto);
            
            //llamo el metodo q declara al campeon 
            liga.declareCampeonLiga(ligdto.getIdTorneo());
            response.sendRedirect("paginas/inicio.jsp?campeon=declarado#3");
        }
        //
        //FECHA DE LA LIGA
        //
        
        else if(request.getParameter("asignarfechas")!=null && request.getParameter("primeraronda")!=null){
            int ronda = 1;
            int idTorneo= Integer.parseInt(request.getParameter("idTorneo"));
            String asunto = "Notificacion horarios de partidos";
            FachadaTorneos facadeTorneo = new FachadaTorneos();
            //pregunto si hay datos de partido 1 si las fechas no llegan vacias 
            if (!request.getParameter("fecha0").equals("") && !request.getParameter("hora0").equals("")) {
            //comienzo con el primer partido
            PartidoDTO p1 = new PartidoDTO();   
            p1.setRonda(ronda);
            p1.setEquipo1(Integer.parseInt(request.getParameter("0equipo1")));
            p1.setEquipo2(Integer.parseInt(request.getParameter("0equipo2")));
            p1.setFecha(request.getParameter("fecha0"));
            p1.setHora(request.getParameter("hora0"));
            p1.setCancha(Integer.parseInt(request.getParameter("cp0")));
            p1.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p1);
            StringBuilder emailsp1 = new StringBuilder("");
            ArrayList<String> correosp1eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp1eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo1")));
            correosp1eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo2"))));
            for (int i = 0; i < correosp1eq.size(); i++) {
                emailsp1.append(correosp1eq.get(i));
                if (i != correosp1eq.size() -1 && correosp1eq.size() > 0) {
                    emailsp1.append(" ,");
                }
            }
            //envio los correos
            String nequipo1 = request.getParameter("0nequipo1");
            String nequipo2 = request.getParameter("0nequipo2");
            String cuerpop1 = "El partido <strong>"+nequipo1+"</strong>"
                    +" <span> vs </span>"
                    + "<strong> "+nequipo2+"</strong>"
                    +"<br/>"
                    +"Será el "+p1.getFecha()+" a las "+p1.getHora()
                    +"<br/>"
                    +"En la cancha No "+p1.getCancha();
            Correo.sendMail(asunto, cuerpop1, emailsp1.toString());
            //fin del primer partido
            }
            //pregunto asi hay 2 p
            if(!request.getParameter("fecha1").equals("") && !request.getParameter("hora1").equals("") ){
            //comienzo con el segundo
            PartidoDTO p2 = new PartidoDTO();   
            p2.setRonda(ronda);
            p2.setEquipo1(Integer.parseInt(request.getParameter("1equipo1")));
            p2.setEquipo2(Integer.parseInt(request.getParameter("1equipo2")));
            p2.setFecha(request.getParameter("fecha1"));
            p2.setHora(request.getParameter("hora1"));
            p2.setCancha(Integer.parseInt(request.getParameter("cp1")));
            p2.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p2);//pilas aca se envia el dto con el partido no repetir
            StringBuilder emailsp2 = new StringBuilder("");//emails 
            ArrayList<String> correosp2eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp2eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo1")));
            correosp2eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo2"))));
            for (int i = 0; i < correosp2eq.size(); i++) {
                emailsp2.append(correosp2eq.get(i));
                if (i != correosp2eq.size() -1 && correosp2eq.size() > 0) {
                    emailsp2.append(" ,");
                }
            }
            //envio los correos
            String nequipo3 = request.getParameter("1nequipo1");
            String nequipo4 = request.getParameter("1nequipo2");
            String cuerpop2 = "El partido <strong>"+nequipo3+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo4+"</strong>"
                    +"<br/>"
                    +"Sera el "+p2.getFecha()+" a las "+p2.getHora()
                    +"<br/>"
                    +"En la cancha No "+p2.getCancha();
            Correo.sendMail(asunto, cuerpop2, emailsp2.toString());
            //fin del segundo
            }
            //pregunto si hay parametros del tercer partido
            if(!request.getParameter("fecha2").equals("") && !request.getParameter("hora2").equals("")){
            //comienzo con el tercero
            PartidoDTO p3 = new PartidoDTO();   
            p3.setRonda(ronda);
            p3.setEquipo1(Integer.parseInt(request.getParameter("2equipo1")));
            p3.setEquipo2(Integer.parseInt(request.getParameter("2equipo2")));
            p3.setFecha(request.getParameter("fecha2"));
            p3.setHora(request.getParameter("hora2"));
            p3.setCancha(Integer.parseInt(request.getParameter("cp2")));
            p3.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p3);//pilas aca se envia el dto con el partido no repetir
            StringBuilder emailsp3 = new StringBuilder("");//emails 
            ArrayList<String> correosp3eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp3eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo1")));
            correosp3eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo2"))));
            for (int i = 0; i < correosp3eq.size(); i++) {
                emailsp3.append(correosp3eq.get(i));
                if (i != correosp3eq.size() -1 && correosp3eq.size() > 0) {
                    emailsp3.append(" ,");
                }
            }
            //envio los correos
            String nequipo5 = request.getParameter("2nequipo1");
            String nequipo6 = request.getParameter("2nequipo2");
            String cuerpop3 = "El partido <strong>"+nequipo5+"</strong>"
                    +" <span> vs </span> "
                    + "<strong> "+nequipo6+"</strong>"
                    +"<br/>"
                    +"Será el "+p3.getFecha()+" a las "+p3.getHora()
                    +"<br/>"
                    +"En la cancha No "+p3.getCancha();
            Correo.sendMail(asunto, cuerpop3, emailsp3.toString());
            //fin del tercero   
            }
            response.sendRedirect("paginas/torneos/calendario.jsp?idTorneo="+idTorneo+"&primera=Se han asignado las fechas#primerar");
            
        }
        //segunda ronda
        
        else if(request.getParameter("asignarfechas")!=null && request.getParameter("segundaronda")!=null){
            int ronda = 2;
            int idTorneo= Integer.parseInt(request.getParameter("idTorneo"));
            String asunto = "Notificacion horarios de partidos";
            FachadaTorneos facadeTorneo = new FachadaTorneos();
            //pregunto si hay datos de partido 1 si las fechas no llegan vacias 
            if (!request.getParameter("fecha0").equals("") && !request.getParameter("hora0").equals("")) {
            //comienzo con el primer partido
            PartidoDTO p1 = new PartidoDTO();   
            p1.setRonda(ronda);
            p1.setEquipo1(Integer.parseInt(request.getParameter("0equipo1")));
            p1.setEquipo2(Integer.parseInt(request.getParameter("0equipo2")));
            p1.setFecha(request.getParameter("fecha0"));
            p1.setHora(request.getParameter("hora0"));
            p1.setCancha(Integer.parseInt(request.getParameter("cp0")));
            p1.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p1);
            StringBuilder emailsp1 = new StringBuilder("");
            ArrayList<String> correosp1eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp1eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo1")));
            correosp1eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo2"))));
            for (int i = 0; i < correosp1eq.size(); i++) {
                emailsp1.append(correosp1eq.get(i));
                if (i != correosp1eq.size() -1 && correosp1eq.size() > 0) {
                    emailsp1.append(" ,");
                }
            }
            //envio los correos
            String nequipo1 = request.getParameter("0nequipo1");
            String nequipo2 = request.getParameter("0nequipo2");
            String cuerpop1 = "El partido <strong>"+nequipo1+"</strong>"
                    +" <span> vs </span>"
                    + "<strong> "+nequipo2+"</strong>"
                    +"<br/>"
                    +"Será el "+p1.getFecha()+" a las "+p1.getHora()
                    +"<br/>"
                    +"En la cancha No "+p1.getCancha();
            Correo.sendMail(asunto, cuerpop1, emailsp1.toString());
            //fin del primer partido
            }
            //pregunto asi hay 2 p
            if(!request.getParameter("fecha1").equals("") && !request.getParameter("hora1").equals("") ){
            //comienzo con el segundo
            PartidoDTO p2 = new PartidoDTO();   
            p2.setRonda(ronda);
            p2.setEquipo1(Integer.parseInt(request.getParameter("1equipo1")));
            p2.setEquipo2(Integer.parseInt(request.getParameter("1equipo2")));
            p2.setFecha(request.getParameter("fecha1"));
            p2.setHora(request.getParameter("hora1"));
            p2.setCancha(Integer.parseInt(request.getParameter("cp1")));
            p2.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p2);//pilas aca se envia el dto con el partido no repetir
            StringBuilder emailsp2 = new StringBuilder("");//emails 
            ArrayList<String> correosp2eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp2eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo1")));
            correosp2eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo2"))));
            for (int i = 0; i < correosp2eq.size(); i++) {
                emailsp2.append(correosp2eq.get(i));
                if (i != correosp2eq.size() -1 && correosp2eq.size() > 0) {
                    emailsp2.append(" ,");
                }
            }
            //envio los correos
            String nequipo3 = request.getParameter("1nequipo1");
            String nequipo4 = request.getParameter("1nequipo2");
            String cuerpop2 = "El partido <strong>"+nequipo3+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo4+"</strong>"
                    +"<br/>"
                    +"Sera el "+p2.getFecha()+" a las "+p2.getHora()
                    +"<br/>"
                    +"En la cancha No "+p2.getCancha();
            Correo.sendMail(asunto, cuerpop2, emailsp2.toString());
            //fin del segundo
            }
            //pregunto si hay parametros del tercer partido
            if(!request.getParameter("fecha2").equals("") && !request.getParameter("hora2").equals("")){
            //comienzo con el tercero
            PartidoDTO p3 = new PartidoDTO();   
            p3.setRonda(ronda);
            p3.setEquipo1(Integer.parseInt(request.getParameter("2equipo1")));
            p3.setEquipo2(Integer.parseInt(request.getParameter("2equipo2")));
            p3.setFecha(request.getParameter("fecha2"));
            p3.setHora(request.getParameter("hora2"));
            p3.setCancha(Integer.parseInt(request.getParameter("cp2")));
            p3.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p3);//pilas aca se envia el dto con el partido no repetir
            StringBuilder emailsp3 = new StringBuilder("");//emails 
            ArrayList<String> correosp3eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp3eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo1")));
            correosp3eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo2"))));
            for (int i = 0; i < correosp3eq.size(); i++) {
                emailsp3.append(correosp3eq.get(i));
                if (i != correosp3eq.size() -1 && correosp3eq.size() > 0) {
                    emailsp3.append(" ,");
                }
            }
            //envio los correos
            String nequipo5 = request.getParameter("2nequipo1");
            String nequipo6 = request.getParameter("2nequipo2");
            String cuerpop3 = "El partido <strong>"+nequipo5+"</strong>"
                    +" <span> vs </span> "
                    + "<strong> "+nequipo6+"</strong>"
                    +"<br/>"
                    +"Será el "+p3.getFecha()+" a las "+p3.getHora()
                    +"<br/>"
                    +"En la cancha No "+p3.getCancha();
            Correo.sendMail(asunto, cuerpop3, emailsp3.toString());
            //fin del tercero   
            }
            response.sendRedirect("paginas/torneos/calendario.jsp?idTorneo="+idTorneo+"&segunda=Se han asignado las fechas#segundar");
            
        }
        //tercera ronda
        else if(request.getParameter("asignarfechas")!=null && request.getParameter("terceraronda")!=null){
            int ronda = 3;
            int idTorneo= Integer.parseInt(request.getParameter("idTorneo"));
            String asunto = "Notificacion horarios de partidos";
            FachadaTorneos facadeTorneo = new FachadaTorneos();
            //pregunto si hay datos de partido 1 si las fechas no llegan vacias 
            if (!request.getParameter("fecha0").equals("") && !request.getParameter("hora0").equals("")) {
            //comienzo con el primer partido
            PartidoDTO p1 = new PartidoDTO();   
            p1.setRonda(ronda);
            p1.setEquipo1(Integer.parseInt(request.getParameter("0equipo1")));
            p1.setEquipo2(Integer.parseInt(request.getParameter("0equipo2")));
            p1.setFecha(request.getParameter("fecha0"));
            p1.setHora(request.getParameter("hora0"));
            p1.setCancha(Integer.parseInt(request.getParameter("cp0")));
            p1.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p1);
            StringBuilder emailsp1 = new StringBuilder("");
            ArrayList<String> correosp1eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp1eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo1")));
            correosp1eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo2"))));
            for (int i = 0; i < correosp1eq.size(); i++) {
                emailsp1.append(correosp1eq.get(i));
                if (i != correosp1eq.size() -1 && correosp1eq.size() > 0) {
                    emailsp1.append(" ,");
                }
            }
            //envio los correos
            String nequipo1 = request.getParameter("0nequipo1");
            String nequipo2 = request.getParameter("0nequipo2");
            String cuerpop1 = "El partido <strong>"+nequipo1+"</strong>"
                    +" <span> vs </span>"
                    + "<strong> "+nequipo2+"</strong>"
                    +"<br/>"
                    +"Será el "+p1.getFecha()+" a las "+p1.getHora()
                    +"<br/>"
                    +"En la cancha No "+p1.getCancha();
            Correo.sendMail(asunto, cuerpop1, emailsp1.toString());
            //fin del primer partido
            }
            //pregunto asi hay 2 p
            if(!request.getParameter("fecha1").equals("") && !request.getParameter("hora1").equals("") ){
            //comienzo con el segundo
            PartidoDTO p2 = new PartidoDTO();   
            p2.setRonda(ronda);
            p2.setEquipo1(Integer.parseInt(request.getParameter("1equipo1")));
            p2.setEquipo2(Integer.parseInt(request.getParameter("1equipo2")));
            p2.setFecha(request.getParameter("fecha1"));
            p2.setHora(request.getParameter("hora1"));
            p2.setCancha(Integer.parseInt(request.getParameter("cp1")));
            p2.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p2);//pilas aca se envia el dto con el partido no repetir
            StringBuilder emailsp2 = new StringBuilder("");//emails 
            ArrayList<String> correosp2eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp2eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo1")));
            correosp2eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo2"))));
            for (int i = 0; i < correosp2eq.size(); i++) {
                emailsp2.append(correosp2eq.get(i));
                if (i != correosp2eq.size() -1 && correosp2eq.size() > 0) {
                    emailsp2.append(" ,");
                }
            }
            //envio los correos
            String nequipo3 = request.getParameter("1nequipo1");
            String nequipo4 = request.getParameter("1nequipo2");
            String cuerpop2 = "El partido <strong>"+nequipo3+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo4+"</strong>"
                    +"<br/>"
                    +"Sera el "+p2.getFecha()+" a las "+p2.getHora()
                    +"<br/>"
                    +"En la cancha No "+p2.getCancha();
            Correo.sendMail(asunto, cuerpop2, emailsp2.toString());
            //fin del segundo
            }
            //pregunto si hay parametros del tercer partido
            if(!request.getParameter("fecha2").equals("") && !request.getParameter("hora2").equals("")){
            //comienzo con el tercero
            PartidoDTO p3 = new PartidoDTO();   
            p3.setRonda(ronda);
            p3.setEquipo1(Integer.parseInt(request.getParameter("2equipo1")));
            p3.setEquipo2(Integer.parseInt(request.getParameter("2equipo2")));
            p3.setFecha(request.getParameter("fecha2"));
            p3.setHora(request.getParameter("hora2"));
            p3.setCancha(Integer.parseInt(request.getParameter("cp2")));
            p3.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p3);//pilas aca se envia el dto con el partido no repetir
            StringBuilder emailsp3 = new StringBuilder("");//emails 
            ArrayList<String> correosp3eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp3eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo1")));
            correosp3eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo2"))));
            for (int i = 0; i < correosp3eq.size(); i++) {
                emailsp3.append(correosp3eq.get(i));
                if (i != correosp3eq.size() -1 && correosp3eq.size() > 0) {
                    emailsp3.append(" ,");
                }
            }
            //envio los correos
            String nequipo5 = request.getParameter("2nequipo1");
            String nequipo6 = request.getParameter("2nequipo2");
            String cuerpop3 = "El partido <strong>"+nequipo5+"</strong>"
                    +" <span> vs </span> "
                    + "<strong> "+nequipo6+"</strong>"
                    +"<br/>"
                    +"Será el "+p3.getFecha()+" a las "+p3.getHora()
                    +"<br/>"
                    +"En la cancha No "+p3.getCancha();
            Correo.sendMail(asunto, cuerpop3, emailsp3.toString());
            //fin del tercero   
            }
            response.sendRedirect("paginas/torneos/calendario.jsp?idTorneo="+idTorneo+"&tercera=Se han asignado las fechas#tercerar");
            
        }
        //cuarta ronda
        else if(request.getParameter("asignarfechas")!=null && request.getParameter("cuartaronda")!=null){
            int ronda = 4;
            int idTorneo= Integer.parseInt(request.getParameter("idTorneo"));
            String asunto = "Notificacion horarios de partidos";
            FachadaTorneos facadeTorneo = new FachadaTorneos();
            //pregunto si hay datos de partido 1 si las fechas no llegan vacias 
            if (!request.getParameter("fecha0").equals("") && !request.getParameter("hora0").equals("")) {
            //comienzo con el primer partido
            PartidoDTO p1 = new PartidoDTO();   
            p1.setRonda(ronda);
            p1.setEquipo1(Integer.parseInt(request.getParameter("0equipo1")));
            p1.setEquipo2(Integer.parseInt(request.getParameter("0equipo2")));
            p1.setFecha(request.getParameter("fecha0"));
            p1.setHora(request.getParameter("hora0"));
            p1.setCancha(Integer.parseInt(request.getParameter("cp0")));
            p1.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p1);
            StringBuilder emailsp1 = new StringBuilder("");
            ArrayList<String> correosp1eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp1eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo1")));
            correosp1eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo2"))));
            for (int i = 0; i < correosp1eq.size(); i++) {
                emailsp1.append(correosp1eq.get(i));
                if (i != correosp1eq.size() -1 && correosp1eq.size() > 0) {
                    emailsp1.append(" ,");
                }
            }
            //envio los correos
            String nequipo1 = request.getParameter("0nequipo1");
            String nequipo2 = request.getParameter("0nequipo2");
            String cuerpop1 = "El partido <strong>"+nequipo1+"</strong>"
                    +" <span> vs </span>"
                    + "<strong> "+nequipo2+"</strong>"
                    +"<br/>"
                    +"Será el "+p1.getFecha()+" a las "+p1.getHora()
                    +"<br/>"
                    +"En la cancha No "+p1.getCancha();
            Correo.sendMail(asunto, cuerpop1, emailsp1.toString());
            //fin del primer partido
            }
            //pregunto asi hay 2 p
            if(!request.getParameter("fecha1").equals("") && !request.getParameter("hora1").equals("") ){
            //comienzo con el segundo
            PartidoDTO p2 = new PartidoDTO();   
            p2.setRonda(ronda);
            p2.setEquipo1(Integer.parseInt(request.getParameter("1equipo1")));
            p2.setEquipo2(Integer.parseInt(request.getParameter("1equipo2")));
            p2.setFecha(request.getParameter("fecha1"));
            p2.setHora(request.getParameter("hora1"));
            p2.setCancha(Integer.parseInt(request.getParameter("cp1")));
            p2.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p2);//pilas aca se envia el dto con el partido no repetir
            StringBuilder emailsp2 = new StringBuilder("");//emails 
            ArrayList<String> correosp2eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp2eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo1")));
            correosp2eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo2"))));
            for (int i = 0; i < correosp2eq.size(); i++) {
                emailsp2.append(correosp2eq.get(i));
                if (i != correosp2eq.size() -1 && correosp2eq.size() > 0) {
                    emailsp2.append(" ,");
                }
            }
            //envio los correos
            String nequipo3 = request.getParameter("1nequipo1");
            String nequipo4 = request.getParameter("1nequipo2");
            String cuerpop2 = "El partido <strong>"+nequipo3+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo4+"</strong>"
                    +"<br/>"
                    +"Sera el "+p2.getFecha()+" a las "+p2.getHora()
                    +"<br/>"
                    +"En la cancha No "+p2.getCancha();
            Correo.sendMail(asunto, cuerpop2, emailsp2.toString());
            //fin del segundo
            }
            //pregunto si hay parametros del tercer partido
            if(!request.getParameter("fecha2").equals("") && !request.getParameter("hora2").equals("")){
            //comienzo con el tercero
            PartidoDTO p3 = new PartidoDTO();   
            p3.setRonda(ronda);
            p3.setEquipo1(Integer.parseInt(request.getParameter("2equipo1")));
            p3.setEquipo2(Integer.parseInt(request.getParameter("2equipo2")));
            p3.setFecha(request.getParameter("fecha2"));
            p3.setHora(request.getParameter("hora2"));
            p3.setCancha(Integer.parseInt(request.getParameter("cp2")));
            p3.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p3);//pilas aca se envia el dto con el partido no repetir
            StringBuilder emailsp3 = new StringBuilder("");//emails 
            ArrayList<String> correosp3eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp3eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo1")));
            correosp3eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo2"))));
            for (int i = 0; i < correosp3eq.size(); i++) {
                emailsp3.append(correosp3eq.get(i));
                if (i != correosp3eq.size() -1 && correosp3eq.size() > 0) {
                    emailsp3.append(" ,");
                }
            }
            //envio los correos
            String nequipo5 = request.getParameter("2nequipo1");
            String nequipo6 = request.getParameter("2nequipo2");
            String cuerpop3 = "El partido <strong>"+nequipo5+"</strong>"
                    +" <span> vs </span> "
                    + "<strong> "+nequipo6+"</strong>"
                    +"<br/>"
                    +"Será el "+p3.getFecha()+" a las "+p3.getHora()
                    +"<br/>"
                    +"En la cancha No "+p3.getCancha();
            Correo.sendMail(asunto, cuerpop3, emailsp3.toString());
            //fin del tercero   
            }
            response.sendRedirect("paginas/torneos/calendario.jsp?idTorneo="+idTorneo+"&cuarta=Se han asignado las fechas#cuartar");
            
        }
        //quinta ronda
        else if(request.getParameter("asignarfechas")!=null && request.getParameter("quintaronda")!=null){
            int ronda = 5;
            int idTorneo= Integer.parseInt(request.getParameter("idTorneo"));
            String asunto = "Notificacion horarios de partidos";
            FachadaTorneos facadeTorneo = new FachadaTorneos();
            //pregunto si hay datos de partido 1 si las fechas no llegan vacias 
            if (!request.getParameter("fecha0").equals("") && !request.getParameter("hora0").equals("")) {
            //comienzo con el primer partido
            PartidoDTO p1 = new PartidoDTO();   
            p1.setRonda(ronda);
            p1.setEquipo1(Integer.parseInt(request.getParameter("0equipo1")));
            p1.setEquipo2(Integer.parseInt(request.getParameter("0equipo2")));
            p1.setFecha(request.getParameter("fecha0"));
            p1.setHora(request.getParameter("hora0"));
            p1.setCancha(Integer.parseInt(request.getParameter("cp0")));
            p1.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p1);
            StringBuilder emailsp1 = new StringBuilder("");
            ArrayList<String> correosp1eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp1eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo1")));
            correosp1eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo2"))));
            for (int i = 0; i < correosp1eq.size(); i++) {
                emailsp1.append(correosp1eq.get(i));
                if (i != correosp1eq.size() -1 && correosp1eq.size() > 0) {
                    emailsp1.append(" ,");
                }
            }
            //envio los correos
            String nequipo1 = request.getParameter("0nequipo1");
            String nequipo2 = request.getParameter("0nequipo2");
            String cuerpop1 = "El partido <strong>"+nequipo1+"</strong>"
                    +" <span> vs </span>"
                    + "<strong> "+nequipo2+"</strong>"
                    +"<br/>"
                    +"Será el "+p1.getFecha()+" a las "+p1.getHora()
                    +"<br/>"
                    +"En la cancha No "+p1.getCancha();
            Correo.sendMail(asunto, cuerpop1, emailsp1.toString());
            //fin del primer partido
            }
            //pregunto asi hay 2 p
            if(!request.getParameter("fecha1").equals("") && !request.getParameter("hora1").equals("") ){
            //comienzo con el segundo
            PartidoDTO p2 = new PartidoDTO();   
            p2.setRonda(ronda);
            p2.setEquipo1(Integer.parseInt(request.getParameter("1equipo1")));
            p2.setEquipo2(Integer.parseInt(request.getParameter("1equipo2")));
            p2.setFecha(request.getParameter("fecha1"));
            p2.setHora(request.getParameter("hora1"));
            p2.setCancha(Integer.parseInt(request.getParameter("cp1")));
            p2.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p2);//pilas aca se envia el dto con el partido no repetir
            StringBuilder emailsp2 = new StringBuilder("");//emails 
            ArrayList<String> correosp2eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp2eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo1")));
            correosp2eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo2"))));
            for (int i = 0; i < correosp2eq.size(); i++) {
                emailsp2.append(correosp2eq.get(i));
                if (i != correosp2eq.size() -1 && correosp2eq.size() > 0) {
                    emailsp2.append(" ,");
                }
            }
            //envio los correos
            String nequipo3 = request.getParameter("1nequipo1");
            String nequipo4 = request.getParameter("1nequipo2");
            String cuerpop2 = "El partido <strong>"+nequipo3+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo4+"</strong>"
                    +"<br/>"
                    +"Sera el "+p2.getFecha()+" a las "+p2.getHora()
                    +"<br/>"
                    +"En la cancha No "+p2.getCancha();
            Correo.sendMail(asunto, cuerpop2, emailsp2.toString());
            //fin del segundo
            }
            //pregunto si hay parametros del tercer partido
            if(!request.getParameter("fecha2").equals("") && !request.getParameter("hora2").equals("")){
            //comienzo con el tercero
            PartidoDTO p3 = new PartidoDTO();   
            p3.setRonda(ronda);
            p3.setEquipo1(Integer.parseInt(request.getParameter("2equipo1")));
            p3.setEquipo2(Integer.parseInt(request.getParameter("2equipo2")));
            p3.setFecha(request.getParameter("fecha2"));
            p3.setHora(request.getParameter("hora2"));
            p3.setCancha(Integer.parseInt(request.getParameter("cp2")));
            p3.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p3);//pilas aca se envia el dto con el partido no repetir
            StringBuilder emailsp3 = new StringBuilder("");//emails 
            ArrayList<String> correosp3eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp3eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo1")));
            correosp3eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo2"))));
            for (int i = 0; i < correosp3eq.size(); i++) {
                emailsp3.append(correosp3eq.get(i));
                if (i != correosp3eq.size() -1 && correosp3eq.size() > 0) {
                    emailsp3.append(" ,");
                }
            }
            //envio los correos
            String nequipo5 = request.getParameter("2nequipo1");
            String nequipo6 = request.getParameter("2nequipo2");
            String cuerpop3 = "El partido <strong>"+nequipo5+"</strong>"
                    +" <span> vs </span> "
                    + "<strong> "+nequipo6+"</strong>"
                    +"<br/>"
                    +"Será el "+p3.getFecha()+" a las "+p3.getHora()
                    +"<br/>"
                    +"En la cancha No "+p3.getCancha();
            Correo.sendMail(asunto, cuerpop3, emailsp3.toString());
            //fin del tercero   
            }
            response.sendRedirect("paginas/torneos/calendario.jsp?idTorneo="+idTorneo+"&quinta=Se han asignado las fechas#quintar");
            
        }//fin asignacion de fechas
        
        //
        //MARCADORES DE LA LIGA
        //
        //
        
        
        //PRIMERA RONDA
        
        else if(request.getParameter("asignarMarcador")!=null && request.getParameter("primera")!=null){
            int ronda = 1;
            int tipoTorneo = 3;//necesito el tipo de eliminatoria para poder crearla y llamar al metodo hacercuartos
            int idTorneo= Integer.parseInt(request.getParameter("idTorneo"));
            int estado = 1;//todos los partidos pasan a estado 1 q es jugado
            String asunto = "Notificacion resultado de partidos";
            FachadaTorneos facadeTorneo = new FachadaTorneos();
            //pregunto si hay datos de partido 1
            if (request.getParameter("0muno")!=null && !request.getParameter("0muno").equals("") && request.getParameter("0mdos")!=null && !request.getParameter("0mdos").equals("")) {
            //comienzo con el primer partido
            PartidoDTO p1 = new PartidoDTO();
            int numeroPartido = 1;
            p1.setRonda(ronda);
            p1.setEstado(estado);
            p1.setNumero(numeroPartido);
            p1.setEquipo1(Integer.parseInt(request.getParameter("0equipo1")));
            p1.setEquipo2(Integer.parseInt(request.getParameter("0equipo2")));
            // m1 == marcador 1
            p1.setMarcador1(Integer.parseInt(request.getParameter("0muno")));
            p1.setMarcador2(Integer.parseInt(request.getParameter("0mdos")));
            p1.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p1);
            
            //ahora actualizo la tabla de posiciones
            
            facadeTorneo.insertarPosicion(p1);
            
            
            //correos
            StringBuilder emailsp1 = new StringBuilder("");
            ArrayList<String> correosp1eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp1eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo1")));
            correosp1eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo2"))));
            for (int i = 0; i < correosp1eq.size(); i++) {
                emailsp1.append(correosp1eq.get(i));
                if (i != correosp1eq.size() -1 && correosp1eq.size() > 0) {
                    emailsp1.append(" ,");
                }
            }
            //envio los correos
            String nequipo1 = request.getParameter("0nequipo1");
            String nequipo2 = request.getParameter("0nequipo2");
            String cuerpop1 = "El resultado del partido <strong>"+nequipo1+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo2+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo1 + "<strong>"+" "+p1.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo2 + "<strong>"+" "+p1.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop1, emailsp1.toString());
            //fin del primer partido
            }
            //pregunto asi hay 2 p
            if(request.getParameter("1muno")!=null && !request.getParameter("1muno").equals("") && request.getParameter("1mdos")!=null && !request.getParameter("1mdos").equals("") ){
            //comienzo con el segundo
            PartidoDTO p2 = new PartidoDTO();   
            int numeroPartido = 2;
            p2.setRonda(ronda);
            p2.setEstado(estado);
            p2.setNumero(numeroPartido);
            p2.setEquipo1(Integer.parseInt(request.getParameter("1equipo1")));
            p2.setEquipo2(Integer.parseInt(request.getParameter("1equipo2")));
            // m1 == marcador 1
            p2.setMarcador1(Integer.parseInt(request.getParameter("1muno")));
            p2.setMarcador2(Integer.parseInt(request.getParameter("1mdos")));
            p2.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p2);
            
            //actualizo la tabla de posiciones
            facadeTorneo.insertarPosicion(p2);
            
            
            StringBuilder emailsp2 = new StringBuilder("");//emails 
            ArrayList<String> correosp2eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp2eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo1")));
            correosp2eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo2"))));
            for (int i = 0; i < correosp2eq.size(); i++) {
                emailsp2.append(correosp2eq.get(i));
                if (i != correosp2eq.size() -1 && correosp2eq.size() > 0) {
                    emailsp2.append(" ,");
                }
            }
            //envio los correos
            String nequipo3 = request.getParameter("1nequipo1");
            String nequipo4 = request.getParameter("1nequipo2");
            String cuerpop2 = "El resultado del partido <strong>"+nequipo3+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo4+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo3 + "<strong>"+" "+p2.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo4 + "<strong>"+" "+p2.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop2, emailsp2.toString());
            //fin del segundo
            }
            //pregunto si hay parametros del tercer partido
            if(request.getParameter("2muno")!=null && !request.getParameter("2muno").equals("") && request.getParameter("2mdos")!=null && !request.getParameter("2mdos").equals("")){
            //comienzo con el tercero
            PartidoDTO p3 = new PartidoDTO();
            int numeroPartido = 3;
            p3.setRonda(ronda);
            p3.setEstado(estado);
            p3.setNumero(numeroPartido);
            p3.setEquipo1(Integer.parseInt(request.getParameter("2equipo1")));
            p3.setEquipo2(Integer.parseInt(request.getParameter("2equipo2")));
            // m1 == marcador 1
            p3.setMarcador1(Integer.parseInt(request.getParameter("2muno")));
            p3.setMarcador2(Integer.parseInt(request.getParameter("2mdos")));
            p3.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p3);
            
            
            //actualizo la t posiciones
            facadeTorneo.insertarPosicion(p3);
            
            //envio de correos
            StringBuilder emailsp3 = new StringBuilder("");//emails 
            ArrayList<String> correosp3eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp3eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo1")));
            correosp3eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo2"))));
            for (int i = 0; i < correosp3eq.size(); i++) {
                emailsp3.append(correosp3eq.get(i));
                if (i != correosp3eq.size() -1 && correosp3eq.size() > 0) {
                    emailsp3.append(" ,");
                }
            }
            //envio los correos
            String nequipo5 = request.getParameter("2nequipo1");
            String nequipo6 = request.getParameter("2nequipo2");
            String cuerpop3 = "El resultado del partido <strong>"+nequipo5+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo6+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo5 + "<strong>"+" "+p3.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo6 + "<strong>"+" "+p3.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop3, emailsp3.toString());
            //fin del tercero   
            }
            
            //reenvio
            response.sendRedirect("paginas/torneos/misTorneos.jsp?idTorneo="+idTorneo+"&octavos=asignados");
        }//fin asignar marcador primera ronda 
        
        //segunda ronda
        else if(request.getParameter("asignarMarcador")!=null && request.getParameter("segundaronda")!=null){
            int ronda = 2;
            int tipoTorneo = 3;//necesito el tipo de eliminatoria para poder crearla y llamar al metodo hacercuartos
            int idTorneo= Integer.parseInt(request.getParameter("idTorneo"));
            int estado = 1;//todos los partidos pasan a estado 1 q es jugado
            String asunto = "Notificacion resultado de partidos";
            FachadaTorneos facadeTorneo = new FachadaTorneos();
            //pregunto si hay datos de partido 1
            if (request.getParameter("0muno")!=null && !request.getParameter("0muno").equals("") && request.getParameter("0mdos")!=null && !request.getParameter("0mdos").equals("")) {
            //comienzo con el primer partido
            PartidoDTO p1 = new PartidoDTO();
            TablaPosicionesDTO pos1 = new TablaPosicionesDTO();//posiciones del 1 equipo
            TablaPosicionesDTO pos2 = new TablaPosicionesDTO();//posiciones del 2 equipo
            int numeroPartido = 1;
            p1.setRonda(ronda);
            p1.setEstado(estado);
            p1.setNumero(numeroPartido);
            p1.setEquipo1(Integer.parseInt(request.getParameter("0equipo1")));
            p1.setEquipo2(Integer.parseInt(request.getParameter("0equipo2")));
            // m1 == marcador 1
            p1.setMarcador1(Integer.parseInt(request.getParameter("0muno")));
            p1.setMarcador2(Integer.parseInt(request.getParameter("0mdos")));
            p1.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p1);
            
            //ahora actualizo la tabla de posiciones
            
            facadeTorneo.insertarPosicion(p1);
            
            
            //correos
            StringBuilder emailsp1 = new StringBuilder("");
            ArrayList<String> correosp1eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp1eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo1")));
            correosp1eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo2"))));
            for (int i = 0; i < correosp1eq.size(); i++) {
                emailsp1.append(correosp1eq.get(i));
                if (i != correosp1eq.size() -1 && correosp1eq.size() > 0) {
                    emailsp1.append(" ,");
                }
            }
            //envio los correos
            String nequipo1 = request.getParameter("0nequipo1");
            String nequipo2 = request.getParameter("0nequipo2");
            String cuerpop1 = "El resultado del partido <strong>"+nequipo1+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo2+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo1 + "<strong>"+" "+p1.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo2 + "<strong>"+" "+p1.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop1, emailsp1.toString());
            //fin del primer partido
            }
            //pregunto asi hay 2 p
            if(request.getParameter("1muno")!=null && !request.getParameter("1muno").equals("") && request.getParameter("1mdos")!=null && !request.getParameter("1mdos").equals("") ){
            //comienzo con el segundo
            PartidoDTO p2 = new PartidoDTO();   
            int numeroPartido = 2;
            p2.setRonda(ronda);
            p2.setEstado(estado);
            p2.setNumero(numeroPartido);
            p2.setEquipo1(Integer.parseInt(request.getParameter("1equipo1")));
            p2.setEquipo2(Integer.parseInt(request.getParameter("1equipo2")));
            // m1 == marcador 1
            p2.setMarcador1(Integer.parseInt(request.getParameter("1muno")));
            p2.setMarcador2(Integer.parseInt(request.getParameter("1mdos")));
            p2.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p2);
            
            //actualizo la tabla de posiciones
            facadeTorneo.insertarPosicion(p2);
            
            
            StringBuilder emailsp2 = new StringBuilder("");//emails 
            ArrayList<String> correosp2eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp2eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo1")));
            correosp2eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo2"))));
            for (int i = 0; i < correosp2eq.size(); i++) {
                emailsp2.append(correosp2eq.get(i));
                if (i != correosp2eq.size() -1 && correosp2eq.size() > 0) {
                    emailsp2.append(" ,");
                }
            }
            //envio los correos
            String nequipo3 = request.getParameter("1nequipo1");
            String nequipo4 = request.getParameter("1nequipo2");
            String cuerpop2 = "El resultado del partido <strong>"+nequipo3+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo4+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo3 + "<strong>"+" "+p2.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo4 + "<strong>"+" "+p2.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop2, emailsp2.toString());
            //fin del segundo
            }
            //pregunto si hay parametros del tercer partido
            if(request.getParameter("2muno")!=null && !request.getParameter("2muno").equals("") && request.getParameter("2mdos")!=null && !request.getParameter("2mdos").equals("")){
            //comienzo con el tercero
            PartidoDTO p3 = new PartidoDTO();
            TablaPosicionesDTO pos1 = new TablaPosicionesDTO();//posiciones del 1 equipo
            TablaPosicionesDTO pos2 = new TablaPosicionesDTO();//posiciones del 2 equipo
            int numeroPartido = 3;
            p3.setRonda(ronda);
            p3.setEstado(estado);
            p3.setNumero(numeroPartido);
            p3.setEquipo1(Integer.parseInt(request.getParameter("2equipo1")));
            p3.setEquipo2(Integer.parseInt(request.getParameter("2equipo2")));
            // m1 == marcador 1
            p3.setMarcador1(Integer.parseInt(request.getParameter("2muno")));
            p3.setMarcador2(Integer.parseInt(request.getParameter("2mdos")));
            p3.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p3);
            
            
            //actualizo la t posiciones
            facadeTorneo.insertarPosicion(p3);
            
            //envio de correos
            StringBuilder emailsp3 = new StringBuilder("");//emails 
            ArrayList<String> correosp3eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp3eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo1")));
            correosp3eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo2"))));
            for (int i = 0; i < correosp3eq.size(); i++) {
                emailsp3.append(correosp3eq.get(i));
                if (i != correosp3eq.size() -1 && correosp3eq.size() > 0) {
                    emailsp3.append(" ,");
                }
            }
            //envio los correos
            String nequipo5 = request.getParameter("2nequipo1");
            String nequipo6 = request.getParameter("2nequipo2");
            String cuerpop3 = "El resultado del partido <strong>"+nequipo5+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo6+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo5 + "<strong>"+" "+p3.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo6 + "<strong>"+" "+p3.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop3, emailsp3.toString());
            //fin del tercero   
            }
            
            //reenvio
            response.sendRedirect("paginas/torneos/misTorneos.jsp?idTorneo="+idTorneo+"&segunda=asignados");
        }//fin asignar marcador segunda ronda
        
        
        //tercera ronda
        else if(request.getParameter("asignarMarcador")!=null && request.getParameter("terceraronda")!=null){
            int ronda = 3;
            int tipoTorneo = 3;//necesito el tipo de eliminatoria para poder crearla y llamar al metodo hacercuartos
            int idTorneo= Integer.parseInt(request.getParameter("idTorneo"));
            int estado = 1;//todos los partidos pasan a estado 1 q es jugado
            String asunto = "Notificacion resultado de partidos";
            FachadaTorneos facadeTorneo = new FachadaTorneos();
            //pregunto si hay datos de partido 1
            if (request.getParameter("0muno")!=null && !request.getParameter("0muno").equals("") && request.getParameter("0mdos")!=null && !request.getParameter("0mdos").equals("")) {
            //comienzo con el primer partido
            PartidoDTO p1 = new PartidoDTO();

            int numeroPartido = 1;
            p1.setRonda(ronda);
            p1.setEstado(estado);
            p1.setNumero(numeroPartido);
            p1.setEquipo1(Integer.parseInt(request.getParameter("0equipo1")));
            p1.setEquipo2(Integer.parseInt(request.getParameter("0equipo2")));
            // m1 == marcador 1
            p1.setMarcador1(Integer.parseInt(request.getParameter("0muno")));
            p1.setMarcador2(Integer.parseInt(request.getParameter("0mdos")));
            p1.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p1);
            
            //ahora actualizo la tabla de posiciones
            
            facadeTorneo.insertarPosicion(p1);
            
            
            //correos
            StringBuilder emailsp1 = new StringBuilder("");
            ArrayList<String> correosp1eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp1eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo1")));
            correosp1eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo2"))));
            for (int i = 0; i < correosp1eq.size(); i++) {
                emailsp1.append(correosp1eq.get(i));
                if (i != correosp1eq.size() -1 && correosp1eq.size() > 0) {
                    emailsp1.append(" ,");
                }
            }
            //envio los correos
            String nequipo1 = request.getParameter("0nequipo1");
            String nequipo2 = request.getParameter("0nequipo2");
            String cuerpop1 = "El resultado del partido <strong>"+nequipo1+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo2+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo1 + "<strong>"+" "+p1.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo2 + "<strong>"+" "+p1.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop1, emailsp1.toString());
            //fin del primer partido
            }
            //pregunto asi hay 2 p
            if(request.getParameter("1muno")!=null && !request.getParameter("1muno").equals("") && request.getParameter("1mdos")!=null && !request.getParameter("1mdos").equals("") ){
            //comienzo con el segundo
            PartidoDTO p2 = new PartidoDTO();   
            int numeroPartido = 2;
            p2.setRonda(ronda);
            p2.setEstado(estado);
            p2.setNumero(numeroPartido);
            p2.setEquipo1(Integer.parseInt(request.getParameter("1equipo1")));
            p2.setEquipo2(Integer.parseInt(request.getParameter("1equipo2")));
            // m1 == marcador 1
            p2.setMarcador1(Integer.parseInt(request.getParameter("1muno")));
            p2.setMarcador2(Integer.parseInt(request.getParameter("1mdos")));
            p2.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p2);
            
            //actualizo la tabla de posiciones
            facadeTorneo.insertarPosicion(p2);
            
            
            StringBuilder emailsp2 = new StringBuilder("");//emails 
            ArrayList<String> correosp2eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp2eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo1")));
            correosp2eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo2"))));
            for (int i = 0; i < correosp2eq.size(); i++) {
                emailsp2.append(correosp2eq.get(i));
                if (i != correosp2eq.size() -1 && correosp2eq.size() > 0) {
                    emailsp2.append(" ,");
                }
            }
            //envio los correos
            String nequipo3 = request.getParameter("1nequipo1");
            String nequipo4 = request.getParameter("1nequipo2");
            String cuerpop2 = "El resultado del partido <strong>"+nequipo3+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo4+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo3 + "<strong>"+" "+p2.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo4 + "<strong>"+" "+p2.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop2, emailsp2.toString());
            //fin del segundo
            }
            //pregunto si hay parametros del tercer partido
            if(request.getParameter("2muno")!=null && !request.getParameter("2muno").equals("") && request.getParameter("2mdos")!=null && !request.getParameter("2mdos").equals("")){
            //comienzo con el tercero
            PartidoDTO p3 = new PartidoDTO();
            int numeroPartido = 3;
            p3.setRonda(ronda);
            p3.setEstado(estado);
            p3.setNumero(numeroPartido);
            p3.setEquipo1(Integer.parseInt(request.getParameter("2equipo1")));
            p3.setEquipo2(Integer.parseInt(request.getParameter("2equipo2")));
            // m1 == marcador 1
            p3.setMarcador1(Integer.parseInt(request.getParameter("2muno")));
            p3.setMarcador2(Integer.parseInt(request.getParameter("2mdos")));
            p3.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p3);
            
            
            //actualizo la t posiciones
            facadeTorneo.insertarPosicion(p3);
            
            //envio de correos
            StringBuilder emailsp3 = new StringBuilder("");//emails 
            ArrayList<String> correosp3eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp3eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo1")));
            correosp3eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo2"))));
            for (int i = 0; i < correosp3eq.size(); i++) {
                emailsp3.append(correosp3eq.get(i));
                if (i != correosp3eq.size() -1 && correosp3eq.size() > 0) {
                    emailsp3.append(" ,");
                }
            }
            //envio los correos
            String nequipo5 = request.getParameter("2nequipo1");
            String nequipo6 = request.getParameter("2nequipo2");
            String cuerpop3 = "El resultado del partido <strong>"+nequipo5+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo6+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo5 + "<strong>"+" "+p3.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo6 + "<strong>"+" "+p3.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop3, emailsp3.toString());
            //fin del tercero   
            }
            
            //reenvio
            response.sendRedirect("paginas/torneos/misTorneos.jsp?idTorneo="+idTorneo+"&tercera=asignados");
        }//fin asignar marcador tercera ronda
        
        //cuarta ronda
        else if(request.getParameter("asignarMarcador")!=null && request.getParameter("cuartaronda")!=null){
            int ronda = 4;
            int tipoTorneo = 3;//necesito el tipo de eliminatoria para poder crearla y llamar al metodo hacercuartos
            int idTorneo= Integer.parseInt(request.getParameter("idTorneo"));
            int estado = 1;//todos los partidos pasan a estado 1 q es jugado
            String asunto = "Notificacion resultado de partidos";
            FachadaTorneos facadeTorneo = new FachadaTorneos();
            //pregunto si hay datos de partido 1
            if (request.getParameter("0muno")!=null && !request.getParameter("0muno").equals("") && request.getParameter("0mdos")!=null && !request.getParameter("0mdos").equals("")) {
            //comienzo con el primer partido
            PartidoDTO p1 = new PartidoDTO();

            int numeroPartido = 1;
            p1.setRonda(ronda);
            p1.setEstado(estado);
            p1.setNumero(numeroPartido);
            p1.setEquipo1(Integer.parseInt(request.getParameter("0equipo1")));
            p1.setEquipo2(Integer.parseInt(request.getParameter("0equipo2")));
            // m1 == marcador 1
            p1.setMarcador1(Integer.parseInt(request.getParameter("0muno")));
            p1.setMarcador2(Integer.parseInt(request.getParameter("0mdos")));
            p1.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p1);
            
            //ahora actualizo la tabla de posiciones
            
            facadeTorneo.insertarPosicion(p1);
            
            
            //correos
            StringBuilder emailsp1 = new StringBuilder("");
            ArrayList<String> correosp1eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp1eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo1")));
            correosp1eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo2"))));
            for (int i = 0; i < correosp1eq.size(); i++) {
                emailsp1.append(correosp1eq.get(i));
                if (i != correosp1eq.size() -1 && correosp1eq.size() > 0) {
                    emailsp1.append(" ,");
                }
            }
            //envio los correos
            String nequipo1 = request.getParameter("0nequipo1");
            String nequipo2 = request.getParameter("0nequipo2");
            String cuerpop1 = "El resultado del partido <strong>"+nequipo1+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo2+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo1 + "<strong>"+" "+p1.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo2 + "<strong>"+" "+p1.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop1, emailsp1.toString());
            //fin del primer partido
            }
            //pregunto asi hay 2 p
            if(request.getParameter("1muno")!=null && !request.getParameter("1muno").equals("") && request.getParameter("1mdos")!=null && !request.getParameter("1mdos").equals("") ){
            //comienzo con el segundo
            PartidoDTO p2 = new PartidoDTO();   
            int numeroPartido = 2;
            p2.setRonda(ronda);
            p2.setEstado(estado);
            p2.setNumero(numeroPartido);
            p2.setEquipo1(Integer.parseInt(request.getParameter("1equipo1")));
            p2.setEquipo2(Integer.parseInt(request.getParameter("1equipo2")));
            // m1 == marcador 1
            p2.setMarcador1(Integer.parseInt(request.getParameter("1muno")));
            p2.setMarcador2(Integer.parseInt(request.getParameter("1mdos")));
            p2.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p2);
            
            //actualizo la tabla de posiciones
            facadeTorneo.insertarPosicion(p2);
            
            
            StringBuilder emailsp2 = new StringBuilder("");//emails 
            ArrayList<String> correosp2eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp2eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo1")));
            correosp2eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo2"))));
            for (int i = 0; i < correosp2eq.size(); i++) {
                emailsp2.append(correosp2eq.get(i));
                if (i != correosp2eq.size() -1 && correosp2eq.size() > 0) {
                    emailsp2.append(" ,");
                }
            }
            //envio los correos
            String nequipo3 = request.getParameter("1nequipo1");
            String nequipo4 = request.getParameter("1nequipo2");
            String cuerpop2 = "El resultado del partido <strong>"+nequipo3+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo4+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo3 + "<strong>"+" "+p2.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo4 + "<strong>"+" "+p2.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop2, emailsp2.toString());
            //fin del segundo
            }
            //pregunto si hay parametros del tercer partido
            if(request.getParameter("2muno")!=null && !request.getParameter("2muno").equals("") && request.getParameter("2mdos")!=null && !request.getParameter("2mdos").equals("")){
            //comienzo con el tercero
            PartidoDTO p3 = new PartidoDTO();
            int numeroPartido = 3;
            p3.setRonda(ronda);
            p3.setEstado(estado);
            p3.setNumero(numeroPartido);
            p3.setEquipo1(Integer.parseInt(request.getParameter("2equipo1")));
            p3.setEquipo2(Integer.parseInt(request.getParameter("2equipo2")));
            // m1 == marcador 1
            p3.setMarcador1(Integer.parseInt(request.getParameter("2muno")));
            p3.setMarcador2(Integer.parseInt(request.getParameter("2mdos")));
            p3.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p3);
            
            
            //actualizo la t posiciones
            facadeTorneo.insertarPosicion(p3);
            
            //envio de correos
            StringBuilder emailsp3 = new StringBuilder("");//emails 
            ArrayList<String> correosp3eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp3eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo1")));
            correosp3eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo2"))));
            for (int i = 0; i < correosp3eq.size(); i++) {
                emailsp3.append(correosp3eq.get(i));
                if (i != correosp3eq.size() -1 && correosp3eq.size() > 0) {
                    emailsp3.append(" ,");
                }
            }
            //envio los correos
            String nequipo5 = request.getParameter("2nequipo1");
            String nequipo6 = request.getParameter("2nequipo2");
            String cuerpop3 = "El resultado del partido <strong>"+nequipo5+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo6+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo5 + "<strong>"+" "+p3.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo6 + "<strong>"+" "+p3.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop3, emailsp3.toString());
            //fin del tercero   
            }
            
            //reenvio
            response.sendRedirect("paginas/torneos/misTorneos.jsp?idTorneo="+idTorneo+"&cuarta=asignados");
        }//fin asignar marcador cuarta ronda
        
        
        //quinta ronda
        else if(request.getParameter("asignarMarcador")!=null && request.getParameter("quintaronda")!=null){
            int ronda = 5;
            int tipoTorneo = 3;//necesito el tipo de eliminatoria para poder crearla y llamar al metodo hacercuartos
            int idTorneo= Integer.parseInt(request.getParameter("idTorneo"));
            int estado = 1;//todos los partidos pasan a estado 1 q es jugado
            String asunto = "Notificacion resultado de partidos";
            FachadaTorneos facadeTorneo = new FachadaTorneos();
            //pregunto si hay datos de partido 1
            if (request.getParameter("0muno")!=null && !request.getParameter("0muno").equals("") && request.getParameter("0mdos")!=null && !request.getParameter("0mdos").equals("")) {
            //comienzo con el primer partido
            PartidoDTO p1 = new PartidoDTO();

            int numeroPartido = 1;
            p1.setRonda(ronda);
            p1.setEstado(estado);
            p1.setNumero(numeroPartido);
            p1.setEquipo1(Integer.parseInt(request.getParameter("0equipo1")));
            p1.setEquipo2(Integer.parseInt(request.getParameter("0equipo2")));
            // m1 == marcador 1
            p1.setMarcador1(Integer.parseInt(request.getParameter("0muno")));
            p1.setMarcador2(Integer.parseInt(request.getParameter("0mdos")));
            p1.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p1);
            
            //ahora actualizo la tabla de posiciones
            
            facadeTorneo.insertarPosicion(p1);
            
            
            //correos
            StringBuilder emailsp1 = new StringBuilder("");
            ArrayList<String> correosp1eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp1eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo1")));
            correosp1eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("0equipo2"))));
            for (int i = 0; i < correosp1eq.size(); i++) {
                emailsp1.append(correosp1eq.get(i));
                if (i != correosp1eq.size() -1 && correosp1eq.size() > 0) {
                    emailsp1.append(" ,");
                }
            }
            //envio los correos
            String nequipo1 = request.getParameter("0nequipo1");
            String nequipo2 = request.getParameter("0nequipo2");
            String cuerpop1 = "El resultado del partido <strong>"+nequipo1+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo2+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo1 + "<strong>"+" "+p1.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo2 + "<strong>"+" "+p1.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop1, emailsp1.toString());
            //fin del primer partido
            }
            //pregunto asi hay 2 p
            if(request.getParameter("1muno")!=null && !request.getParameter("1muno").equals("") && request.getParameter("1mdos")!=null && !request.getParameter("1mdos").equals("") ){
            //comienzo con el segundo
            PartidoDTO p2 = new PartidoDTO();   
            int numeroPartido = 2;
            p2.setRonda(ronda);
            p2.setEstado(estado);
            p2.setNumero(numeroPartido);
            p2.setEquipo1(Integer.parseInt(request.getParameter("1equipo1")));
            p2.setEquipo2(Integer.parseInt(request.getParameter("1equipo2")));
            // m1 == marcador 1
            p2.setMarcador1(Integer.parseInt(request.getParameter("1muno")));
            p2.setMarcador2(Integer.parseInt(request.getParameter("1mdos")));
            p2.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p2);
            
            //actualizo la tabla de posiciones
            facadeTorneo.insertarPosicion(p2);
            
            
            StringBuilder emailsp2 = new StringBuilder("");//emails 
            ArrayList<String> correosp2eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp2eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo1")));
            correosp2eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("1equipo2"))));
            for (int i = 0; i < correosp2eq.size(); i++) {
                emailsp2.append(correosp2eq.get(i));
                if (i != correosp2eq.size() -1 && correosp2eq.size() > 0) {
                    emailsp2.append(" ,");
                }
            }
            //envio los correos
            String nequipo3 = request.getParameter("1nequipo1");
            String nequipo4 = request.getParameter("1nequipo2");
            String cuerpop2 = "El resultado del partido <strong>"+nequipo3+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo4+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo3 + "<strong>"+" "+p2.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo4 + "<strong>"+" "+p2.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop2, emailsp2.toString());
            //fin del segundo
            }
            //pregunto si hay parametros del tercer partido
            if(request.getParameter("2muno")!=null && !request.getParameter("2muno").equals("") && request.getParameter("2mdos")!=null && !request.getParameter("2mdos").equals("")){
            //comienzo con el tercero
            PartidoDTO p3 = new PartidoDTO();
            int numeroPartido = 3;
            p3.setRonda(ronda);
            p3.setEstado(estado);
            p3.setNumero(numeroPartido);
            p3.setEquipo1(Integer.parseInt(request.getParameter("2equipo1")));
            p3.setEquipo2(Integer.parseInt(request.getParameter("2equipo2")));
            // m1 == marcador 1
            p3.setMarcador1(Integer.parseInt(request.getParameter("2muno")));
            p3.setMarcador2(Integer.parseInt(request.getParameter("2mdos")));
            p3.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p3);
            
            
            //actualizo la t posiciones
            facadeTorneo.insertarPosicion(p3);
            
            //envio de correos
            StringBuilder emailsp3 = new StringBuilder("");//emails 
            ArrayList<String> correosp3eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp3eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo1")));
            correosp3eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("2equipo2"))));
            for (int i = 0; i < correosp3eq.size(); i++) {
                emailsp3.append(correosp3eq.get(i));
                if (i != correosp3eq.size() -1 && correosp3eq.size() > 0) {
                    emailsp3.append(" ,");
                }
            }
            //envio los correos
            String nequipo5 = request.getParameter("2nequipo1");
            String nequipo6 = request.getParameter("2nequipo2");
            String cuerpop3 = "El resultado del partido <strong>"+nequipo5+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo6+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo5 + "<strong>"+" "+p3.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo6 + "<strong>"+" "+p3.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop3, emailsp3.toString());
            //fin del tercero   
            }
            
            //reenvio
            response.sendRedirect("paginas/torneos/misTorneos.jsp?idTorneo="+idTorneo+"&quinta=asignados");
        }//fin asignar marcador quinta ronda
          else {
            response.sendRedirect("paginas/torneos/crear_torneo.jsp");
        }
    }
    
public String cuerpoNotificacionHorarios(String nombreEq1,String nombreq2, String fecha,String hora, int numcancha){
       String cuerpo = "";
       return cuerpo;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (MiExcepcion ex) {
            response.sendError(500, ex.getMessage());
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (MiExcepcion ex) {
            response.sendError(500, ex.getMessage());
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
