

package Model.Atoms;

import Model.Atom;

/**
 *
 * @author Gabriel
 */
public class Nitrogen extends Atom
{
    public Nitrogen()
    {
        byte valence = 3;
        byte layerValence = 5;
        
        /* Colocando os dados */
        super.setName("Nitrogênio");
        super.setValence(valence);
        super.setLayerValence(layerValence);
        super.setEletronegativity(3.0);
        super.setMass(14);
        super.setRadius(0.055f);
        super.setColor("blue");
        super.setSymbol("N");
    }
}
