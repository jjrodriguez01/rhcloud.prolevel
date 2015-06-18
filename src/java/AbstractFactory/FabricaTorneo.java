/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AbstractFactory;

import modelo.TorneoDTO;
import utilidades.MiExcepcion;

/**
 *
 * @author jeisson
 */
public class FabricaTorneo implements FabricaTorneos{

    @Override
    public Copa CreaCopa(TorneoDTO torneo) throws MiExcepcion {
         return new Copa();
    }

    @Override
    public Liga creaLiga(TorneoDTO torneo) throws MiExcepcion {
        return new Liga();
    }

    @Override
    public Eliminatoria creaEliminatoria(TorneoDTO torneo) throws MiExcepcion {
       return new Eliminatoria();
    }
    
}
