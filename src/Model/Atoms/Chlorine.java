
package Model.Atoms;

import Model.Atom;
/**
 *
 * @author Gabriel
 */
public class Chlorine extends Atom 
{
     public Chlorine() 
    {               
        byte valence = 1;
        byte layerValence = 7;
        /* Colocando os dados */
        super.setName("Cloro");
        super.setValence(valence);
        super.setLayerValence(layerValence);
        super.setEletronegativity(3.0);
        super.setMass(35.5);
        super.setRadius(0.04f);
        super.setColor("cyan");
        super.setSymbol("Cl");
    }
}
