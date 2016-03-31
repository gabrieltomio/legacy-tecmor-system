

package Model.Atoms;

import Model.Atom;

/**
 *
 * @author Gabriel
 */
public class Bromine extends Atom
{
     public Bromine() 
    {               
        byte valence = 1;
        byte layerValence = 7;
        
        /* Colocando os dados */
        super.setName("Bromo");
        super.setValence(valence);
        super.setLayerValence(layerValence);
        super.setEletronegativity(3.0);
        super.setMass(80);
        super.setRadius(0.04f);
        super.setColor("magenta");
        super.setSymbol("Br");
    }
}
