
package Model.Atoms;

import Model.Atom;

/**
 *
 * @author Gabriel
 */
public class Sulfur extends Atom
{
    public Sulfur()
    {
        byte valence = 2;
        byte layerValence = 6;
        /* Colocando os dados */
        super.setName("Enxofre");
        super.setValence(valence);
        super.setLayerValence(layerValence);
        super.setEletronegativity(2.5);
        super.setMass(32);
        super.setRadius(0.05f);
        super.setColor("yellow");
        super.setSymbol("S");
    }
}
