
package Model.Atoms;

import Model.Atom;

public class Fluorine extends Atom
{
    public Fluorine()
    {
        byte valence = 1;
        byte layerValence = 7;
        
        /* Colocando os dados */
        super.setName("Fluor");
        super.setValence(valence);
        super.setEletronegativity(4.0);
        super.setLayerValence(layerValence);
        super.setMass(19);
        super.setRadius(0.04f);
        super.setColor("green");
        super.setSymbol("F");
    }
}
