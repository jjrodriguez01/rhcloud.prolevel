
package controlador;

import AbstractFactory.Eliminatoria;
import AbstractFactory.FabricaTorneo;
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
import modelo.CampeonesDTO;
import modelo.EquipoDTO;
import modelo.EquiposdeltorneoDTO;
import modelo.PartidoDTO;
import modelo.TorneoDTO;
import persistencia.TorneoDAO;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class GestionEliminatoria extends HttpServlet {

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
        if ( request.getParameter("eliminatoria")!=null && request.getParameter("enviareli")!=null) {
            int tipotorneo = 3;//en bd tres es una eliminatoria
            TorneoDTO elidto = new TorneoDTO();
            
            elidto.setIdTorneo(0);
            elidto.setCapacidadEquipos(Integer.parseInt(request.getParameter("capacidad")));
            elidto.setFechaFin(request.getParameter("fin"));
            elidto.setFechaInicio(request.getParameter("inicio"));
            elidto.setGenero(request.getParameter("tipo"));
            elidto.setTipo(tipotorneo);
            elidto.setIdaVuelta(true);
            elidto.setNombre(request.getParameter("nombreTorneo"));
            TorneoFactory fabrica = new TorneoFactory();
            Torneo cup = fabrica.crearTorneo(elidto);
            String crearelim = cup.crear(elidto);
               response.sendRedirect("paginas/torneos/crear_torneo.jsp?eliminatoria="+crearelim+"#ftorneos");
        }else if(request.getParameter("iniciar")!=null){//si se van a crearlos partidos de la eliminatoria

            FachadaTorneos facadeTorneos = new FachadaTorneos();
            TorneoDTO eliminatoria = new TorneoDTO();
            //creo un dto completo de esta eliminatoria
            eliminatoria.setIdTorneo(Integer.parseInt(request.getParameter("idTorneo")));
            eliminatoria.setNombre(request.getParameter("nombreTorneo"));
            eliminatoria.setFechaFin(request.getParameter("fechaFin"));
            eliminatoria.setFechaInicio(request.getParameter("fechaInicio"));
            eliminatoria.setCapacidadEquipos(Integer.parseInt(request.getParameter("capacidadEquipos")));
            eliminatoria.setTipo(Integer.parseInt(request.getParameter("tipo")));
            FabricaTorneo fabrica = new FabricaTorneo();
            Eliminatoria eli = fabrica.creaEliminatoria(eliminatoria);//creo la eliminatoria
            List<EquiposdeltorneoDTO> edt = new ArrayList();//arrayList con los equipos de este torneo
            //ese metodo me devuelve un List con todos los equiposn de este torneo
            edt = facadeTorneos.listarEquiposInscritos(eliminatoria.getIdTorneo());
            //ahora llamo al metodo de eliminatoria q crea los emparejamientos y le paso 
            //este List con los torneos
            eli.primeraRondaDiesciseis(edt);
            response.sendRedirect("paginas/torneos/calendario.jsp?idTorneo="+eliminatoria.getIdTorneo());
        }
        //
        //si se van a crear los cuartos
        //
        else if (request.getParameter("iniciarcuartos")!=null && request.getParameter("iniciarcuartos").equals("cuartos")){
            FachadaTorneos facadeTorneos = new FachadaTorneos();
            TorneoDTO eliminatoria = new TorneoDTO();
            //creo un dto completo de esta eliminatoria
            eliminatoria.setIdTorneo(Integer.parseInt(request.getParameter("idTorneo")));
            eliminatoria.setNombre(request.getParameter("nombreTorneo"));
            eliminatoria.setFechaFin(request.getParameter("fechaFin"));
            eliminatoria.setFechaInicio(request.getParameter("fechaInicio"));
            eliminatoria.setCapacidadEquipos(Integer.parseInt(request.getParameter("capacidadEquipos")));
            eliminatoria.setTipo(Integer.parseInt(request.getParameter("tipo")));
            FabricaTorneo fabrica = new FabricaTorneo();
            Eliminatoria eli = fabrica.creaEliminatoria(eliminatoria);//creo la eliminatoria
            // CREO EL LIST CON LOS EQUIPOS EN CUARTOS
            
            ArrayList<EquiposdeltorneoDTO> ecuartos = new ArrayList();
            ecuartos = (ArrayList) facadeTorneos.listarEquiposEnCuartos(eliminatoria.getIdTorneo());
            // llamo el metodo hacer cuartos 
            eli.segundaRondaDiesciseis(ecuartos);
            response.sendRedirect("paginas/torneos/resultadoseli.jsp?idTorneo="+eliminatoria.getIdTorneo());
        }
        
        //
        //si se van a iniciar las semifinales
        //
        else if (request.getParameter("iniciarsemi")!=null && request.getParameter("iniciarsemi").equals("semi")){
            FachadaTorneos facadeTorneos = new FachadaTorneos();
            TorneoDTO eliminatoria = new TorneoDTO();
            //creo un dto completo de esta eliminatoria
            eliminatoria.setIdTorneo(Integer.parseInt(request.getParameter("idTorneo")));
            eliminatoria.setNombre(request.getParameter("nombreTorneo"));
            eliminatoria.setFechaFin(request.getParameter("fechaFin"));
            eliminatoria.setFechaInicio(request.getParameter("fechaInicio"));
            eliminatoria.setCapacidadEquipos(Integer.parseInt(request.getParameter("capacidadEquipos")));
            eliminatoria.setTipo(Integer.parseInt(request.getParameter("tipo")));
            FabricaTorneo fabrica = new FabricaTorneo();
            Eliminatoria eli = fabrica.creaEliminatoria(eliminatoria);//creo la eliminatoria
            // CREO EL LIST CON LOS EQUIPOS EN CUARTOS
            
            ArrayList<EquiposdeltorneoDTO> esemi = new ArrayList();
            esemi = (ArrayList) facadeTorneos.listarEquiposEnSemi(eliminatoria.getIdTorneo());
            // llamo el metodo hacer cuartos 
            eli.terceraRondaDiesciseis(esemi);
            response.sendRedirect("paginas/torneos/resultadoseli.jsp?idTorneo="+eliminatoria.getIdTorneo());
        }
        //
        //si se van a iniciar la final
        //
        else if (request.getParameter("iniciarfinal")!=null && request.getParameter("iniciarfinal").equals("fin")){
            FachadaTorneos facadeTorneos = new FachadaTorneos();
            TorneoDTO eliminatoria = new TorneoDTO();
            //creo un dto completo de esta eliminatoria
            eliminatoria.setIdTorneo(Integer.parseInt(request.getParameter("idTorneo")));
            eliminatoria.setNombre(request.getParameter("nombreTorneo"));
            eliminatoria.setFechaFin(request.getParameter("fechaFin"));
            eliminatoria.setFechaInicio(request.getParameter("fechaInicio"));
            eliminatoria.setCapacidadEquipos(Integer.parseInt(request.getParameter("capacidadEquipos")));
            eliminatoria.setTipo(Integer.parseInt(request.getParameter("tipo")));
            FabricaTorneo fabrica = new FabricaTorneo();
            Eliminatoria eli = fabrica.creaEliminatoria(eliminatoria);//creo la eliminatoria
            // CREO EL LIST CON LOS EQUIPOS EN CUARTOS
            
            ArrayList<EquiposdeltorneoDTO> esemi = new ArrayList();
            esemi = (ArrayList) facadeTorneos.listarEquiposEnFinal(eliminatoria.getIdTorneo());
            // llamo el metodo hacer cuartos 
            eli.cuartaRondaDiesciseis(esemi);
            response.sendRedirect("paginas/torneos/resultadoseli.jsp?idTorneo="+eliminatoria.getIdTorneo());
        }
        //
        //inicio a insertar fechas de una eli de 16
        //
        else if(request.getParameter("asignarfechas")!=null && request.getParameter("foctavos")!=null){
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
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo2+"</strong>"
                    +"<br/>"
                    +"Sera el "+p1.getFecha()+" a las "+p1.getHora()
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
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo6+"</strong>"
                    +"<br/>"
                    +"Sera el "+p3.getFecha()+" a las "+p3.getHora()
                    +"<br/>"
                    +"En la cancha No "+p3.getCancha();
            Correo.sendMail(asunto, cuerpop3, emailsp3.toString());
            //fin del tercero   
            }
            //si hay parametros del cuarto partido
            if (!request.getParameter("fecha3").equals("") && !request.getParameter("hora3").equals("")) {
            //comienzo con el cuarto
            PartidoDTO p4 = new PartidoDTO();   
            p4.setRonda(ronda);
            p4.setEquipo1(Integer.parseInt(request.getParameter("3equipo1")));
            p4.setEquipo2(Integer.parseInt(request.getParameter("3equipo2")));
            p4.setFecha(request.getParameter("fecha3"));
            p4.setHora(request.getParameter("hora3"));
            p4.setCancha(Integer.parseInt(request.getParameter("cp3")));
            p4.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p4);//pilas aca se envia el dto con el partido no repetir
            StringBuilder emailsp4 = new StringBuilder("");//emails 
            ArrayList<String> correosp4eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp4eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("3equipo1")));
            correosp4eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("3equipo2"))));
            for (int i = 0; i < correosp4eq.size(); i++) {
                emailsp4.append(correosp4eq.get(i));
                if (i != correosp4eq.size() -1 && correosp4eq.size() > 0) {
                    emailsp4.append(" ,");
                }
            }
            //envio los correos
            String nequipo7 = request.getParameter("3nequipo1");
            String nequipo8 = request.getParameter("3nequipo2");
            String cuerpop4 = "El partido <strong>"+nequipo7+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo8+"</strong>"
                    +"<br/>"
                    +"Sera el "+p4.getFecha()+" a las "+p4.getHora()
                    +"<br/>"
                    +"En la cancha No "+p4.getCancha();
            Correo.sendMail(asunto, cuerpop4, emailsp4.toString());
            //fin del cuarto
            }
            //si hay parametros de quinto partido
            if (!request.getParameter("fecha4").equals("") && !request.getParameter("hora4").equals("")) {
            //comienzo con el quinto
            PartidoDTO p5 = new PartidoDTO();   
            p5.setRonda(ronda);
            p5.setEquipo1(Integer.parseInt(request.getParameter("4equipo1")));
            p5.setEquipo2(Integer.parseInt(request.getParameter("4equipo2")));
            p5.setFecha(request.getParameter("fecha4"));
            p5.setHora(request.getParameter("hora4"));
            p5.setCancha(Integer.parseInt(request.getParameter("cp4")));
            p5.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p5);//pilas aca se envia el dto con el partido no repetir
            StringBuilder emailsp5 = new StringBuilder("");//emails 
            ArrayList<String> correosp5eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp5eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("4equipo1")));
            correosp5eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("4equipo2"))));
            for (int i = 0; i < correosp5eq.size(); i++) {
                emailsp5.append(correosp5eq.get(i));
                if (i != correosp5eq.size() -1 && correosp5eq.size() > 0) {
                    emailsp5.append(" ,");
                }
            }
            //envio los correos
            String nequipo9 = request.getParameter("4nequipo1");
            String nequipo10 = request.getParameter("4nequipo2");
            String cuerpop5 = "El partido <strong>"+nequipo9+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo10+"</strong>"
                    +"<br/>"
                    +"Sera el "+p5.getFecha()+" a las "+p5.getHora()
                    +"<br/>"
                    +"En la cancha No "+p5.getCancha();
            Correo.sendMail(asunto, cuerpop5, emailsp5.toString());
            //fin del quinto
                }
            //si hay sexto partido
            if (!request.getParameter("fecha5").equals("") && !request.getParameter("hora5").equals("")) {
                //comienzo con el sexto
            PartidoDTO p6 = new PartidoDTO();   
            p6.setRonda(ronda);
            p6.setEquipo1(Integer.parseInt(request.getParameter("5equipo1")));
            p6.setEquipo2(Integer.parseInt(request.getParameter("5equipo2")));
            p6.setFecha(request.getParameter("fecha5"));
            p6.setHora(request.getParameter("hora5"));
            p6.setCancha(Integer.parseInt(request.getParameter("cp5")));
            p6.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p6);//pilas aca se envia el dto con el partido no repetir
            StringBuilder emailsp6 = new StringBuilder("");//emails 
            ArrayList<String> correosp6eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp6eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("5equipo1")));
            correosp6eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("5equipo2"))));
            for (int i = 0; i < correosp6eq.size(); i++) {
                emailsp6.append(correosp6eq.get(i));
                if (i != correosp6eq.size() -1 && correosp6eq.size() > 0) {
                    emailsp6.append(" ,");
                }
            }
            //envio los correos
            String nequipo11 = request.getParameter("5nequipo1");
            String nequipo12 = request.getParameter("5nequipo2");
            String cuerpop6 = "El partido <strong>"+nequipo11+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo12+"</strong>"
                    +"<br/>"
                    +"Sera el "+p6.getFecha()+" a las "+p6.getHora()
                    +"<br/>"
                    +"En la cancha No "+p6.getCancha();
            Correo.sendMail(asunto, cuerpop6, emailsp6.toString());
            //fin del sexto
                }
            //si hay septimo partido
            if (!request.getParameter("fecha6").equals("") && !request.getParameter("hora6").equals("")) {
            //comienzo con el septimo
            PartidoDTO p7 = new PartidoDTO();   
            p7.setRonda(ronda);
            p7.setEquipo1(Integer.parseInt(request.getParameter("6equipo1")));
            p7.setEquipo2(Integer.parseInt(request.getParameter("6equipo2")));
            p7.setFecha(request.getParameter("fecha6"));
            p7.setHora(request.getParameter("hora6"));
            p7.setCancha(Integer.parseInt(request.getParameter("cp6")));
            p7.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p7);//pilas aca se envia el dto con el partido no repetir
            StringBuilder emailsp7 = new StringBuilder("");//emails 
            ArrayList<String> correosp7eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp7eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("6equipo1")));
            correosp7eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("6equipo2"))));
            for (int i = 0; i < correosp7eq.size(); i++) {
                emailsp7.append(correosp7eq.get(i));
                if (i != correosp7eq.size() -1 && correosp7eq.size() > 0) {
                    emailsp7.append(" ,");
                }
            }
            //envio los correos
            String nequipo13 = request.getParameter("6nequipo1");
            String nequipo14 = request.getParameter("6nequipo2");
            String cuerpop7 = "El partido <strong>"+nequipo13+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo14+"<strong>"
                    +"<br/>"
                    +"Sera el "+p7.getFecha()+" a las "+p7.getHora()
                    +"<br/>"
                    +"En la cancha No "+p7.getCancha();
            Correo.sendMail(asunto, cuerpop7, emailsp7.toString());
            //fin del septimo
                }
            //pregunto si hay parametros de octavo partido
            if (!request.getParameter("fecha7").equals("") && !request.getParameter("hora7").equals("")) {
            //comienzo con el octavo
            PartidoDTO p8 = new PartidoDTO();   
            p8.setRonda(ronda);
            p8.setEquipo1(Integer.parseInt(request.getParameter("7equipo1")));
            p8.setEquipo2(Integer.parseInt(request.getParameter("7equipo2")));
            p8.setFecha(request.getParameter("fecha7"));
            p8.setHora(request.getParameter("hora7"));
            p8.setCancha(Integer.parseInt(request.getParameter("cp7")));
            p8.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p8);//pilas aca se envia el dto con el partido no repetir
            StringBuilder emailsp8 = new StringBuilder("");//emails 
            ArrayList<String> correosp8eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp8eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("7equipo1")));
            correosp8eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("7equipo2"))));
            for (int i = 0; i < correosp8eq.size(); i++) {
                emailsp8.append(correosp8eq.get(i));
                if (i != correosp8eq.size() -1 && correosp8eq.size() > 0) {
                    emailsp8.append(" ,");
                }
            }
            //envio los correos
            String nequipo15 = request.getParameter("7nequipo1");
            String nequipo16 = request.getParameter("7nequipo2");
            String cuerpop8 = "El partido <strong>"+nequipo15+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo16+"</strong>"
                    +"<br/>"
                    +"Sera el "+p8.getFecha()+" a las "+p8.getHora()
                    +"<br/>"
                    +"En la cancha No "+p8.getCancha();
            Correo.sendMail(asunto, cuerpop8, emailsp8.toString());
            //fin del octavo 
                }
          
            response.sendRedirect("paginas/torneos/calendario.jsp?idTorneo="+idTorneo+"&octavos=Se han asignado las fechas");
            
        }
        
        //
        //comienzo con los cuartos de final
        //
        
        else if(request.getParameter("asignarfechas")!=null && request.getParameter("fcuartos")!=null){
            int ronda = 2;
            int idTorneo= Integer.parseInt(request.getParameter("idTorneo"));
            String asunto = "Notificacion horarios de partidos";
            FachadaTorneos facadeTorneo = new FachadaTorneos();
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
                    +" <span>vs</span> <br/> "
                    + nequipo2
                    +"<br/>"
                    +"Sera el "+p1.getFecha()+" a las "+p1.getHora()
                    +"<br/>"
                    +"En la cancha No "+p1.getCancha();
            Correo.sendMail(asunto, cuerpop1, emailsp1.toString());
            //fin del primer partido
            }
            if (!request.getParameter("fecha1").equals("") && !request.getParameter("hora1").equals("")) {
               
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
                    + nequipo4
                    +"<br/>"
                    +"Sera el "+p2.getFecha()+" a las "+p2.getHora()
                    +"<br/>"
                    +"En la cancha No "+p2.getCancha();
            Correo.sendMail(asunto, cuerpop2, emailsp2.toString());
            //fin del segundo
            }
            if (!request.getParameter("fecha2").equals("") && !request.getParameter("hora2").equals("")) {
        
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
                    +" <span>vs</span> <br/> "
                    + nequipo6
                    +"<br/>"
                    +"Sera el "+p3.getFecha()+" a las "+p3.getHora()
                    +"<br/>"
                    +"En la cancha No "+p3.getCancha();
            Correo.sendMail(asunto, cuerpop3, emailsp3.toString());
            //fin del tercero
            }
            if (!request.getParameter("fecha3").equals("") && !request.getParameter("hora3").equals("")) {
                
            //comienzo con el cuarto
            PartidoDTO p4 = new PartidoDTO();   
            p4.setRonda(ronda);
            p4.setEquipo1(Integer.parseInt(request.getParameter("3equipo1")));
            p4.setEquipo2(Integer.parseInt(request.getParameter("3equipo2")));
            p4.setFecha(request.getParameter("fecha3"));
            p4.setHora(request.getParameter("hora3"));
            p4.setCancha(Integer.parseInt(request.getParameter("cp3")));
            p4.setIdTorneo(idTorneo);
            facadeTorneo.actualizarPartido(p4);//pilas aca se envia el dto con el partido no repetir
            StringBuilder emailsp4 = new StringBuilder("");//emails 
            ArrayList<String> correosp4eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp4eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("3equipo1")));
            correosp4eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("3equipo2"))));
            for (int i = 0; i < correosp4eq.size(); i++) {
                emailsp4.append(correosp4eq.get(i));
                if (i != correosp4eq.size() -1 && correosp4eq.size() > 0) {
                    emailsp4.append(" ,");
                }
            }
            //envio los correos
            String nequipo7 = request.getParameter("3nequipo1");
            String nequipo8 = request.getParameter("3nequipo2");
            String cuerpop4 = "El partido <strong>"+nequipo7+"</strong>"
                    +" <span>vs</span> <br/> "
                    + nequipo8
                    +"<br/>"
                    +"Sera el "+p4.getFecha()+" a las "+p4.getHora()
                    +"<br/>"
                    +"En la cancha No "+p4.getCancha();
            Correo.sendMail(asunto, cuerpop4, emailsp4.toString());
            //fin del cuarto
            }
            response.sendRedirect("paginas/torneos/calendario.jsp?idTorneo="+idTorneo+"&cuartos=Se han establecido las fechas#ccuartos");
        }
        
        //
        // fechas de semifinales
        //
        //
        
        
        else if(request.getParameter("asignarfechas")!=null && request.getParameter("fsemi")!=null){
            int ronda = 3;
            int idTorneo= Integer.parseInt(request.getParameter("idTorneo"));
            String asunto = "Notificacion horarios de partidos";
            FachadaTorneos facadeTorneo = new FachadaTorneos();
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
            String cuerpop1 = "El partido de la semifinal entre <strong>"+nequipo1+"</strong>"
                    +" <span>vs</span> <br/> "
                    + nequipo2
                    +"<br/>"
                    +"Sera el "+p1.getFecha()+" a las "+p1.getHora()
                    +"<br/>"
                    +"En la cancha No "+p1.getCancha();
            Correo.sendMail(asunto, cuerpop1, emailsp1.toString());
            //fin del primer partido
            }
            if (!request.getParameter("fecha1").equals("") && !request.getParameter("hora1").equals("")) {
                
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
            String cuerpop2 = "El partido por la semifinal entre <strong>"+nequipo3+"</strong>"
                    +" <span>vs</span> <br/> "
                    + nequipo4
                    +"<br/>"
                    +"Sera el "+p2.getFecha()+" a las "+p2.getHora()
                    +"<br/>"
                    +"En la cancha No "+p2.getCancha();
            Correo.sendMail(asunto, cuerpop2, emailsp2.toString());
            //fin del segundo
            }
            response.sendRedirect("paginas/torneos/calendario.jsp?idTorneo="+idTorneo+"&semi=Se han asignado las fechas#csemi");
        }
        //
        //FECHAS DEL TERCER PUESTO
        //
        else if(request.getParameter("asignarfechas")!=null && request.getParameter("ftercer")!=null){
            int ronda = 5;
            int idTorneo= Integer.parseInt(request.getParameter("idTorneo"));
            String asunto = "Notificacion horarios de partidos";
            FachadaTorneos facadeTorneo = new FachadaTorneos();
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
            String cuerpop1 = "El partido por el tercer puesto entre <br/>"
                    + " <strong>"+nequipo1+"</strong>"
                    +" <span>vs</span> <br/> "
                    + nequipo2
                    +"<br/>"
                    +"Sera el "+p1.getFecha()+" a las "+p1.getHora()
                    +"<br/>"
                    +"En la cancha No "+p1.getCancha();
            Correo.sendMail(asunto, cuerpop1, emailsp1.toString());
            //fin del primer partido
            
            response.sendRedirect("paginas/torneos/calendario.jsp?idTorneo="+idTorneo+"&tercer = Se han asignado las fechas#ctercerp");
        }
        
        //
        //FECHAS DE LA FINAL
        //
        
        else if(request.getParameter("asignarfechas")!=null && request.getParameter("ffinal")!=null){
            int ronda = 4;
            int idTorneo= Integer.parseInt(request.getParameter("idTorneo"));
            String asunto = "Notificacion horarios de partidos";
            FachadaTorneos facadeTorneo = new FachadaTorneos();
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
            String cuerpop1 = "El partido de la final <br/>"
                    + "<strong>"+nequipo1+"</strong>"
                    +" <span>vs</span> <br/> "
                    + nequipo2
                    +"<br/>"
                    +"Será el "+p1.getFecha()+" a las "+p1.getHora()
                    +"<br/>"
                    +"En la cancha No "+p1.getCancha();
            Correo.sendMail(asunto, cuerpop1, emailsp1.toString());
            //fin del primer partido
            
            response.sendRedirect("paginas/torneos/calendario.jsp?idTorneo="+idTorneo+"&final=Se han establecido las fechas#cfinal");
        }
        //
        //fin de fechas para eli de 16
        //
        
        
        //
        //
        //
        //AHORA VAMOS CON INGRESAR LOS MARCADORES DE LOS PARTIDOS
        //
        //
        //
        
        else if(request.getParameter("asignarMarcador")!=null && request.getParameter("foctavos")!=null){
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
            //valido fechas nulas
            //el metodo validar fechas devuelve true si no hay fechas
//                if (facadeTorneo.validarFechas(p1)) {
//                    response.sendRedirect("paginas/torneos/marcadores.jsp?idTorneo="+p1.getIdTorneo()+"&fechas=null");
//                }
            facadeTorneo.insertarMarcador(p1);
            //escojo al ganador ? si condicion = valor = al q esta despues de ? sino el q esta despues de :
            int ganador = p1.getMarcador1() > p1.getMarcador2()? p1.getEquipo1() : p1.getEquipo2();
            facadeTorneo.insertarACuartos(idTorneo, ganador);
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
            //valido fechas nulas
            //el metodo validar fechas devuelve true si no hay fechas
//                if (facadeTorneo.validarFechas(p2)) {
//                    response.sendRedirect("paginas/torneos/marcadores.jsp?idTorneo="+p2.getIdTorneo()+"&fechas=null");
//                }
            facadeTorneo.insertarMarcador(p2);
            //escojo al ganador ? si condicion = valor = al q esta despues de ? sino el q esta despues de :
            int ganador = p2.getMarcador1()> p2.getMarcador2()? p2.getEquipo1() : p2.getEquipo2();
            facadeTorneo.insertarACuartos(idTorneo, ganador);
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
            //valido fechas nulas
            //el metodo validar fechas devuelve true si no hay fechas
//                if (facadeTorneo.validarFechas(p3)) {
//                    response.sendRedirect("paginas/torneos/marcadores.jsp?idTorneo="+p3.getIdTorneo()+"&fechas=null");
//                }
            facadeTorneo.insertarMarcador(p3);
            //escojo al ganador ? si condicion = valor = al q esta despues de ? sino el q esta despues de :
            int ganador = p3.getMarcador1()>p3.getMarcador2()? p3.getEquipo1() : p3.getEquipo2();
            facadeTorneo.insertarACuartos(idTorneo, ganador);
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
            //si hay parametros del cuarto partido
            if (request.getParameter("3muno")!=null && !request.getParameter("3muno").equals("") && request.getParameter("3mdos")!=null && !request.getParameter("3mdos").equals("")) {
            //comienzo con el cuarto
            PartidoDTO p4 = new PartidoDTO();   
            int numeroPartido = 4;
            p4.setRonda(ronda);
            p4.setEstado(estado);
            p4.setNumero(numeroPartido);
            p4.setEquipo1(Integer.parseInt(request.getParameter("3equipo1")));
            p4.setEquipo2(Integer.parseInt(request.getParameter("3equipo2")));
            // m1 == marcador 1
            p4.setMarcador1(Integer.parseInt(request.getParameter("3muno")));
            p4.setMarcador2(Integer.parseInt(request.getParameter("3mdos")));
            p4.setIdTorneo(idTorneo);
            //valido fechas nulas
            //el metodo validar fechas devuelve true si no hay fechas
//                if (facadeTorneo.validarFechas(p4)) {
//                    response.sendRedirect("paginas/torneos/marcadores.jsp?idTorneo="+p4.getIdTorneo()+"&fechas=null");
//                }
            facadeTorneo.insertarMarcador(p4);
            //escojo al ganador ? si condicion = valor = al q esta despues de ? sino el q esta despues de :
            int ganador = p4.getMarcador1()>p4.getMarcador2()? p4.getEquipo1() : p4.getEquipo2();
            facadeTorneo.insertarACuartos(idTorneo, ganador);
            StringBuilder emailsp4 = new StringBuilder("");//emails 
            ArrayList<String> correosp4eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp4eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("3equipo1")));
            correosp4eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("3equipo2"))));
            for (int i = 0; i < correosp4eq.size(); i++) {
                emailsp4.append(correosp4eq.get(i));
                if (i != correosp4eq.size() -1 && correosp4eq.size() > 0) {
                    emailsp4.append(" ,");
                }
            }
            //envio los correos
            String nequipo7 = request.getParameter("3nequipo1");
            String nequipo8 = request.getParameter("3nequipo2");
            String cuerpop4 = "El resultado del partido <strong>"+nequipo7+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo8+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo7 + "<strong>"+" "+p4.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo8 + "<strong>"+" "+p4.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop4, emailsp4.toString());
            //fin del cuarto
            }
            //si hay parametros de quinto partido
            if (request.getParameter("4muno")!=null && !request.getParameter("4muno").equals("") && request.getParameter("4mdos")!=null && !request.getParameter("4mdos").equals("")) {
            //comienzo con el quinto
            PartidoDTO p5 = new PartidoDTO();   
            int numeroPartido = 5;
            p5.setRonda(ronda);
            p5.setEstado(estado);
            p5.setNumero(numeroPartido);
            p5.setEquipo1(Integer.parseInt(request.getParameter("4equipo1")));
            p5.setEquipo2(Integer.parseInt(request.getParameter("4equipo2")));
            // m1 == marcador 1
            p5.setMarcador1(Integer.parseInt(request.getParameter("4muno")));
            p5.setMarcador2(Integer.parseInt(request.getParameter("4mdos")));
            p5.setIdTorneo(idTorneo);
            //valido fechas nulas
            //el metodo validar fechas devuelve true si no hay fechas
//                if (facadeTorneo.validarFechas(p5)) {
//                    response.sendRedirect("paginas/torneos/marcadores.jsp?idTorneo="+p5.getIdTorneo()+"&fechas=null");
//                }
            facadeTorneo.insertarMarcador(p5);
            //escojo al ganador ? si condicion = valor = al q esta despues de ? sino el q esta despues de :
            int ganador = p5.getMarcador1()>p5.getMarcador2()? p5.getEquipo1() : p5.getEquipo2();
            facadeTorneo.insertarACuartos(idTorneo, ganador);
            StringBuilder emailsp5 = new StringBuilder("");//emails 
            ArrayList<String> correosp5eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp5eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("4equipo1")));
            correosp5eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("4equipo2"))));
            for (int i = 0; i < correosp5eq.size(); i++) {
                emailsp5.append(correosp5eq.get(i));
                if (i != correosp5eq.size() -1 && correosp5eq.size() > 0) {
                    emailsp5.append(" ,");
                }
            }
            //envio los correos
            String nequipo9 = request.getParameter("4nequipo1");
            String nequipo10 = request.getParameter("4nequipo2");
            String cuerpop5 = "El resultado del partido <strong>"+nequipo9+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo10+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo9 + "<strong>"+" "+p5.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo10 + "<strong>"+" "+p5.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop5, emailsp5.toString());
            //fin del quinto
                }
            //si hay sexto partido
            if (request.getParameter("5muno")!=null && !request.getParameter("5muno").equals("") && request.getParameter("5mdos")!=null && !request.getParameter("5mdos").equals("")) {
                //comienzo con el sexto
            PartidoDTO p6 = new PartidoDTO();   
            int numeroPartido = 6;
            p6.setRonda(ronda);
            p6.setEstado(estado);
            p6.setNumero(numeroPartido);
            p6.setEquipo1(Integer.parseInt(request.getParameter("5equipo1")));
            p6.setEquipo2(Integer.parseInt(request.getParameter("5equipo2")));
            // m1 == marcador 1
            p6.setMarcador1(Integer.parseInt(request.getParameter("5muno")));
            p6.setMarcador2(Integer.parseInt(request.getParameter("5mdos")));
            p6.setIdTorneo(idTorneo);
            //valido fechas nulas
            //el metodo validar fechas devuelve true si no hay fechas
//                if (facadeTorneo.validarFechas(p6)) {
//                    response.sendRedirect("paginas/torneos/marcadores.jsp?idTorneo="+p6.getIdTorneo()+"&fechas=null");
//                }
            facadeTorneo.insertarMarcador(p6);
            //escojo al ganador ? si condicion = valor = al q esta despues de ? sino el q esta despues de :
            int ganador = p6.getMarcador1()>p6.getMarcador2()? p6.getEquipo1() : p6.getEquipo2();
            facadeTorneo.insertarACuartos(idTorneo, ganador);
            StringBuilder emailsp6 = new StringBuilder("");//emails 
            ArrayList<String> correosp6eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp6eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("5equipo1")));
            correosp6eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("5equipo2"))));
            for (int i = 0; i < correosp6eq.size(); i++) {
                emailsp6.append(correosp6eq.get(i));
                if (i != correosp6eq.size() -1 && correosp6eq.size() > 0) {
                    emailsp6.append(" ,");
                }
            }
            //envio los correos
            String nequipo11 = request.getParameter("5nequipo1");
            String nequipo12 = request.getParameter("5nequipo2");
            String cuerpop6 = "El resultado del partido <strong>"+nequipo11+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo12+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo11 + "<strong>"+" "+p6.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo12 + "<strong>"+" "+p6.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop6, emailsp6.toString());
            //fin del sexto
                }
            //si hay septimo partido
            if (request.getParameter("6muno")!=null && !request.getParameter("6muno").equals("") && request.getParameter("6mdos")!=null && !request.getParameter("6mdos").equals("")) {
            //comienzo con el septimo
            PartidoDTO p7 = new PartidoDTO();   
            int numeroPartido = 7;
            p7.setRonda(ronda);
            p7.setEstado(estado);
            p7.setNumero(numeroPartido);
            p7.setEquipo1(Integer.parseInt(request.getParameter("6equipo1")));
            p7.setEquipo2(Integer.parseInt(request.getParameter("6equipo2")));
            // m1 == marcador 1
            p7.setMarcador1(Integer.parseInt(request.getParameter("6muno")));
            p7.setMarcador2(Integer.parseInt(request.getParameter("6mdos")));
            p7.setIdTorneo(idTorneo);
            //valido fechas nulas
            //el metodo validar fechas devuelve true si no hay fechas
//                if (facadeTorneo.validarFechas(p7)) {
//                    response.sendRedirect("paginas/torneos/marcadores.jsp?idTorneo="+p7.getIdTorneo()+"&fechas=null");
//                }
            facadeTorneo.insertarMarcador(p7);
            //escojo al ganador ? si condicion = valor = al q esta despues de ? sino el q esta despues de :
            int ganador = p7.getMarcador1()>p7.getMarcador2()? p7.getEquipo1() : p7.getEquipo2();
            facadeTorneo.insertarACuartos(idTorneo, ganador);
            StringBuilder emailsp7 = new StringBuilder("");//emails 
            ArrayList<String> correosp7eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp7eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("6equipo1")));
            correosp7eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("6equipo2"))));
            for (int i = 0; i < correosp7eq.size(); i++) {
                emailsp7.append(correosp7eq.get(i));
                if (i != correosp7eq.size() -1 && correosp7eq.size() > 0) {
                    emailsp7.append(" ,");
                }
            }
            //envio los correos
            String nequipo13 = request.getParameter("6nequipo1");
            String nequipo14 = request.getParameter("6nequipo2");
            String cuerpop7 = "El resultado del partido <strong>"+nequipo13+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo14+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo13 + "<strong>"+" "+p7.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo14 + "<strong>"+" "+p7.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop7, emailsp7.toString());
            //fin del septimo
                }
            //pregunto si hay parametros de octavo partido
            if (request.getParameter("7muno")!=null && !request.getParameter("7muno").equals("") && request.getParameter("7mdos")!=null && !request.getParameter("7mdos").equals("")) {
            //comienzo con el octavo
            PartidoDTO p8 = new PartidoDTO();   
            int numeroPartido = 8;
            p8.setRonda(ronda);
            p8.setEstado(estado);
            p8.setNumero(numeroPartido);
            p8.setEquipo1(Integer.parseInt(request.getParameter("7equipo1")));
            p8.setEquipo2(Integer.parseInt(request.getParameter("7equipo2")));
            // m1 == marcador 1
            p8.setMarcador1(Integer.parseInt(request.getParameter("7muno")));
            p8.setMarcador2(Integer.parseInt(request.getParameter("7mdos")));
            p8.setIdTorneo(idTorneo);
            //valido fechas nulas
            //el metodo validar fechas devuelve true si no hay fechas
//                if (facadeTorneo.validarFechas(p8)) {
//                    response.sendRedirect("paginas/torneos/marcadores.jsp?idTorneo="+p8.getIdTorneo()+"&fechas=null");
//                }
            facadeTorneo.insertarMarcador(p8);
            //escojo al ganador ? si condicion = valor = al q esta despues de ? sino el q esta despues de :
            int ganador = p8.getMarcador1()>p8.getMarcador2()? p8.getEquipo1() : p8.getEquipo2();
            facadeTorneo.insertarACuartos(idTorneo, ganador);
            StringBuilder emailsp8 = new StringBuilder("");//emails 
            ArrayList<String> correosp8eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp8eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("7equipo1")));
            correosp8eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("7equipo2"))));
            for (int i = 0; i < correosp8eq.size(); i++) {
                emailsp8.append(correosp8eq.get(i));
                if (i != correosp8eq.size() -1 && correosp8eq.size() > 0) {
                    emailsp8.append(" ,");
                }
            }
            //envio los correos
            String nequipo15 = request.getParameter("7nequipo1");
            String nequipo16 = request.getParameter("7nequipo2");
            String cuerpop8 = "El resultado del partido <strong>"+nequipo15+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo16+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo15 + "<strong>"+" "+p8.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo16 + "<strong>"+" "+p8.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop8, emailsp8.toString());
            //fin del octavo 
                }
            //reenvio
            response.sendRedirect("paginas/torneos/resultadoseli.jsp?idTorneo="+idTorneo+"&octavos=asignados");
        }//fin asignar marcador octavos    
        
        
            ///
            ///
            /// ASIGNACION MARCADOR DE CUARTOS
            
            //comienzo con los cuartos de final a asignar marcador
        else if(request.getParameter("asignarMarcadorCuartos")!=null && request.getParameter("fcuartos")!=null){
            int ronda = 2;
            int tipoTorneo = 3;//necesito el tipo de eliminatoria para poder crearla y llamar al metodo hacercuartos
            int idTorneo= Integer.parseInt(request.getParameter("idTorneo"));
            int estado = 1;//todos los partidos pasan a estado 1 q es jugado
            String asunto = "Notificacion resultado de partidos";
            FachadaTorneos facadeTorneo = new FachadaTorneos();
            
             //pregunto si hay datos de partido 1
            if (request.getParameter("0munoc")!=null && !request.getParameter("0munoc").equals("") && request.getParameter("0mdosc")!=null && !request.getParameter("0mdosc").equals("")) {
            //comienzo con el primer partido
            PartidoDTO p1 = new PartidoDTO();
            int numeroPartido = 1;
            p1.setRonda(ronda);
            p1.setEstado(estado);
            p1.setNumero(numeroPartido);
            p1.setEquipo1(Integer.parseInt(request.getParameter("0equipo1")));
            p1.setEquipo2(Integer.parseInt(request.getParameter("0equipo2")));
            // m1 == marcador 1
            p1.setMarcador1(Integer.parseInt(request.getParameter("0munoc")));
            p1.setMarcador2(Integer.parseInt(request.getParameter("0mdosc")));
            p1.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p1);
            //escojo al ganador ? si condicion = valor = al q esta despues de ? sino el q esta despues de :
            int ganador = p1.getMarcador1() > p1.getMarcador2()? p1.getEquipo1() : p1.getEquipo2();
            facadeTorneo.insertarASemi(idTorneo, ganador);
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
            if(request.getParameter("1munoc") != null && !request.getParameter("1munoc").equals("") && request.getParameter("1mdosc")!=null && !request.getParameter("1mdosc").equals("") ){
            //comienzo con el segundo
            PartidoDTO p2 = new PartidoDTO();   
            int numeroPartido = 2;
            p2.setRonda(ronda);
            p2.setEstado(estado);
            p2.setNumero(numeroPartido);
            p2.setEquipo1(Integer.parseInt(request.getParameter("1equipo1")));
            p2.setEquipo2(Integer.parseInt(request.getParameter("1equipo2")));
            // m1 == marcador 1
            p2.setMarcador1(Integer.parseInt(request.getParameter("1munoc")));
            p2.setMarcador2(Integer.parseInt(request.getParameter("1mdosc")));
            p2.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p2);
            //escojo al ganador ? si condicion = valor = al q esta despues de ? sino el q esta despues de :
            int ganador = p2.getMarcador1()> p2.getMarcador2()? p2.getEquipo1() : p2.getEquipo2();
            facadeTorneo.insertarASemi(idTorneo, ganador);
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
            if(request.getParameter("2munoc")!=null && !request.getParameter("2munoc").equals("") && request.getParameter("2mdosc")!=null && !request.getParameter("2mdosc").equals("")){
             //comienzo con el tercero
            PartidoDTO p3 = new PartidoDTO();   
            int numeroPartido = 3;
            p3.setRonda(ronda);
            p3.setEstado(estado);
            p3.setNumero(numeroPartido);
            p3.setEquipo1(Integer.parseInt(request.getParameter("2equipo1")));
            p3.setEquipo2(Integer.parseInt(request.getParameter("2equipo2")));
            // m1 == marcador 1
            p3.setMarcador1(Integer.parseInt(request.getParameter("2munoc")));
            p3.setMarcador2(Integer.parseInt(request.getParameter("2mdosc")));
            p3.setIdTorneo(idTorneo);
          
            facadeTorneo.insertarMarcador(p3);
            //escojo al ganador ? si condicion = valor = al q esta despues de ? sino el q esta despues de :
            int ganador = p3.getMarcador1()> p3.getMarcador2()? p3.getEquipo1() : p3.getEquipo2();
            facadeTorneo.insertarASemi(idTorneo, ganador);
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
            String nequipo3 = request.getParameter("2nequipo1");
            String nequipo4 = request.getParameter("2nequipo2");
            String cuerpop3 = "El resultado del partido <strong>"+nequipo3+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo4+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo3 + "<strong>"+" "+p3.getMarcador1() + "</strong>"
                    +"<br/>"
                    + nequipo4 + "<strong>"+" "+p3.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop3, emailsp2.toString());
            //fin del tercero 
            }
            //si hay parametros del cuarto partido
            if (request.getParameter("3munoc")!=null && !request.getParameter("3munoc").equals("") && request.getParameter("3mdosc")!=null && !request.getParameter("3mdosc").equals("")) {
            //comienzo con el cuarto
            PartidoDTO p4 = new PartidoDTO();   
            int numeroPartido = 4;
            p4.setRonda(ronda);
            p4.setEstado(estado);
            p4.setNumero(numeroPartido);
            p4.setEquipo1(Integer.parseInt(request.getParameter("3equipo1")));
            p4.setEquipo2(Integer.parseInt(request.getParameter("3equipo2")));
            // m1 == marcador 1
            p4.setMarcador1(Integer.parseInt(request.getParameter("3munoc")));
            p4.setMarcador2(Integer.parseInt(request.getParameter("3mdosc")));
            p4.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p4);
            //escojo al ganador ? si condicion = valor = al q esta despues de ? sino el q esta despues de :
            int ganador = p4.getMarcador1()>p4.getMarcador2()? p4.getEquipo1() : p4.getEquipo2();
            facadeTorneo.insertarASemi(idTorneo, ganador);
            StringBuilder emailsp4 = new StringBuilder("");//emails 
            ArrayList<String> correosp4eq = new ArrayList();
            //array list con los correos de los jugadores de estos dos equipos
            correosp4eq = (ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("3equipo1")));
            correosp4eq.addAll((ArrayList) facadeTorneo.correosJugadoresEquipo(idTorneo,Integer.parseInt(request.getParameter("3equipo2"))));
            for (int i = 0; i < correosp4eq.size(); i++) {
                emailsp4.append(correosp4eq.get(i));
                if (i != correosp4eq.size() -1 && correosp4eq.size() > 0) {
                    emailsp4.append(" ,");
                }
            }
            //envio los correos
            String nequipo7 = request.getParameter("3nequipo1");
            String nequipo8 = request.getParameter("3nequipo2");
            String cuerpop4 = "El resultado del partido <strong>"+nequipo7+"</strong>"
                    +" <span>vs</span> <br/> "
                    + "<strong>"+nequipo8+"</strong>"
                    +"<br/>"
                    +"Fue <br/>"
                    +"<hr>"
                    + nequipo7 + "<strong>"+" "+p4.getMarcador1() + "</strong>"
                    +"<br</>"
                    + nequipo8 + "<strong>"+" "+p4.getMarcador2() + "</strong>"
                    +"<br/>";
            Correo.sendMail(asunto, cuerpop4, emailsp4.toString());
            //fin del cuarto
            }
            response.sendRedirect("paginas/torneos/resultadoseli.jsp?idTorneo="+idTorneo);
        }
        
        //
        //Asignar marcadores de semifinal
        //
        //
        else if(request.getParameter("asignarMarcadorSemi")!=null && request.getParameter("fsemi")!=null){
            int ronda = 3;
            int tipoTorneo = 3;//necesito el tipo de eliminatoria para poder crearla y llamar al metodo hacercuartos
            int idTorneo= Integer.parseInt(request.getParameter("idTorneo"));
            int estado = 1;//todos los partidos pasan a estado 1 q es jugado
            String asunto = "Notificacion resultado de partidos";
            FachadaTorneos facadeTorneo = new FachadaTorneos();
            
             //pregunto si hay datos de partido 1
            if (request.getParameter("0munos")!=null && !request.getParameter("0munos").equals("") && request.getParameter("0mdoss")!=null && !request.getParameter("0mdoss").equals("")) {
            //comienzo con el primer partido
            PartidoDTO p1 = new PartidoDTO();
            int numeroPartido = 1;
            p1.setRonda(ronda);
            p1.setEstado(estado);
            p1.setNumero(numeroPartido);
            p1.setEquipo1(Integer.parseInt(request.getParameter("0equipo1")));
            p1.setEquipo2(Integer.parseInt(request.getParameter("0equipo2")));
            // m1 == marcador 1
            p1.setMarcador1(Integer.parseInt(request.getParameter("0munos")));
            p1.setMarcador2(Integer.parseInt(request.getParameter("0mdoss")));
            p1.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p1);
            //escojo al ganador ? si condicion = valor = al q esta despues de ? sino el q esta despues de :
            int ganador = p1.getMarcador1() > p1.getMarcador2()? p1.getEquipo1() : p1.getEquipo2();
            //busco al perderor para insertarlo en la tabla terceros
            int perdedor = p1.getMarcador1() < p1.getMarcador2()? p1.getEquipo1() : p1.getEquipo2();
            //inserto al ganador en la final
            facadeTorneo.insertarAFinal(idTorneo, ganador);
            //y al perdedor en terceros
            facadeTorneo.insertarATerceros(idTorneo, perdedor);
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
            if(request.getParameter("1munos")!=null && !request.getParameter("1munos").equals("") && request.getParameter("1mdoss")!=null && !request.getParameter("1mdoss").equals("")){
            //comienzo con el segundo
            PartidoDTO p2 = new PartidoDTO();   
            int numeroPartido = 2;
            p2.setRonda(ronda);
            p2.setEstado(estado);
            p2.setNumero(numeroPartido);
            p2.setEquipo1(Integer.parseInt(request.getParameter("1equipo1")));
            p2.setEquipo2(Integer.parseInt(request.getParameter("1equipo2")));
            // m1 == marcador 1
            p2.setMarcador1(Integer.parseInt(request.getParameter("1munos")));
            p2.setMarcador2(Integer.parseInt(request.getParameter("1mdoss")));
            p2.setIdTorneo(idTorneo);
            facadeTorneo.insertarMarcador(p2);
            //escojo al ganador ? si condicion = valor = al q esta despues de ? sino el q esta despues de :
            int ganador = p2.getMarcador1()> p2.getMarcador2()? p2.getEquipo1() : p2.getEquipo2();
            int perdedor = p2.getMarcador1() < p2.getMarcador2()? p2.getEquipo1() : p2.getEquipo2();
            facadeTorneo.insertarAFinal(idTorneo, ganador);
            facadeTorneo.insertarATerceros(idTorneo, perdedor);
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
            
            response.sendRedirect("paginas/torneos/resultadoseli.jsp?idTorneo="+idTorneo+"&marcadorsemi=Se ingresaron los marcadores&#psemi");
        }
        
        //
        //Marcador del tercer puesto
        //
        else if(request.getParameter("asignarMarcadorTercer")!=null && request.getParameter("ftercer")!=null){
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
            String cuerpop1 = "El resultado del partido por el tercer puesto entre <br/>"
                    + "<strong>"+nequipo1+" </strong>"
                    +" <span> vs </span>"
                    + "<strong> "+nequipo2+"</strong>"
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
            response.sendRedirect("paginas/torneos/resultadoseli.jsp?idTorneo="+idTorneo);
        }
        //
        //MARCADORES DE LA FINAL
        //
        else if(request.getParameter("asignarMarcadorFin")!=null && request.getParameter("ffinal")!=null){
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
            //busco al ganador
            int ganador = p1.getMarcador1() > p1.getMarcador2()? p1.getEquipo1() : p1.getEquipo2();
            //hago un obj equipodto para tener el nombre del ganador y guardarlo en el historial
            EquipoDTO campeon = new EquipoDTO();
            campeon = facadeTorneo.getEquipo(ganador);
            TorneoDTO tdto = new TorneoDTO();
            tdto = facadeTorneo.listarTorneo(idTorneo);
            //ahora inserto el historial de campeones
            CampeonesDTO historial = new CampeonesDTO();
            historial.setNombreTorneo(tdto.getNombre());
            historial.setNombreEquipo(campeon.getNombre());
            //inserto los datos a la tabla historial de campeones
            facadeTorneo.insertarCampeon(historial);
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
            String cuerpop1 = "El resultado del partido de la final del torneo <strong>"+tdto.getNombre()+"</strong> entre <br/>"
                    + "<strong>"+nequipo1+" </strong>"
                    +" <span> vs </span>"
                    + "<strong> "+nequipo2+"</strong>"
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
            response.sendRedirect("paginas/torneos/resultadoseli.jsp?idTorneo="+idTorneo);
        }
        //
        //fin de marcadores para eli de 16
        //
        
        
          else {
            response.sendRedirect("paginas/torneos/resultadoseli.jsp?action=noaction");
        }
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
            Logger.getLogger(GestionEliminatoria.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(GestionEliminatoria.class.getName()).log(Level.SEVERE, null, ex);
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
