/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractFactory;

import java.util.List;
import modelo.TorneoDTO;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public interface FabricaTorneos {
    
    Copa CreaCopa(TorneoDTO torneo)throws MiExcepcion;
    Liga creaLiga(TorneoDTO torneo)throws MiExcepcion;
    Eliminatoria creaEliminatoria(TorneoDTO torneo)throws MiExcepcion;
}
