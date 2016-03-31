/**
 *
 * @author Gabriel Tomio Nardes
 */

package Model.Atoms;

import Model.Atom;

public class Carbon extends Atom
{
   public Carbon() 
    {         
        byte valence = 4;
        byte layerValence = 4;
        
        /* Colocando os dados */
        super.setName("Carbono");
        super.setValence(valence);
        super.setLayerValence(layerValence);
        super.setEletronegativity(2.5);
        super.setRadius(.06f);
        super.setMass(12);
        super.setColor("gray");
        super.setSymbol("C");
    } 
}
