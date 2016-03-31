package Model.Atoms;

import Model.Atom;

/**
 *
 * @author Gabriel
 */
public class Phosphorus extends Atom
{
    public Phosphorus()
    {        
        byte valence = 3;
        byte layerValence = 5;
        /* Colocando os dados */
        super.setName("Fósforo");
        super.setValence(valence);
        super.setLayerValence(layerValence);
        super.setEletronegativity(2.1);
        super.setMass(31);
        super.setRadius(0.055f);
        super.setColor("orange");
        super.setSymbol("P");
    }
}
