
/**
 *
 * @author Gabriel
 */

package Model.Atoms;

import Model.Atom;

public class Oxygen extends Atom
{
    public Oxygen()
    {
        byte valence = 2;
        byte layerValence = 6;
        
        /* Colocando os dados */
        super.setName("Oxigênio");
        super.setValence(valence);
        super.setLayerValence(layerValence);
        super.setEletronegativity(3.5);
        super.setMass(16);
        super.setRadius(0.05f);
        super.setColor("red");
        super.setSymbol("O");
    }
}
