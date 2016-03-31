package Model.Atoms;

import Model.Atom;

/**
 *
 * @author Gabriel Tomio Nardes
 */
public class Hydrogen extends Atom
{
    public Hydrogen() 
    {       
        byte valence = 1;
        byte layerValence = 1;
        
        /* Colocando os dados */
        super.setName("Hidrogênio");
        super.setValence(valence);
        super.setLayerValence(layerValence);
        super.setEletronegativity(2.2);
        super.setMass(1);
        super.setRadius(0.025f);
        super.setColor("white");
        super.setSymbol("H");
    }
}
