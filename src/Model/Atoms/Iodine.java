/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Model.Atoms;

import Model.Atom;

/**
 *
 * @author Gabriel
 */
public class Iodine extends Atom
{
    public Iodine() 
    {               
        byte valence = 1;
        byte layerValence = 7;
        
        /* Colocando os dados */
        super.setName("Bromo");
        super.setValence(valence);
        super.setLayerValence(layerValence);
        super.setEletronegativity(2.5);
        super.setMass(127);
        super.setRadius(0.04f);
        super.setColor("pink");
        super.setSymbol("I");
    }
}
