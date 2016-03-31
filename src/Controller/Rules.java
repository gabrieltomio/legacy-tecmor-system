
package Controller;

import Model.Atom;
import View.Preview;
import java.util.ArrayList;
import javax.vecmath.Vector3d;

public class Rules 
{
    // Este método verifica se a ligação entre dois átomos, com a premissa
    // da camada de valência, é válida ou não
    public boolean valenceRule(Atom atom, int type)
    {
        int size = (atom.getLinks() + type) - 1;
        
        if( size >= atom.getValence())
        {
            return true;
        }
        else
        {
            return false;
        }
    }
 
    // Para impedir que o usuário crie uma molécula inválida, com átomos que 
    // não se ligam, será aplicada a regra da eletronegatividade
    // Essa regra é aplicada em dois átomos
    public boolean ruleEtronegativity(Atom firstAtom, Atom secondAtom)
    {
        double varidate = firstAtom.getEletronegativity() - secondAtom.getEletronegativity();
        
        varidate = Math.abs(varidate);
        
        if (varidate <= 1.9)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    /* Estabilidade de todos os átomos, tornam a molécula válida, este método
       verificará cada átomo e sua estabilidade */
    public boolean stabilityAtoms(ArrayList<Atom> atoms)
    {
        boolean validity = false;
        int i = 0, sizeMolecule = atoms.size(), yes = 0;
        
        while ( i < sizeMolecule )
        {
            if (atoms.get(i).getLinks()== atoms.get(i).getValence())
            {
                yes ++;
            }
            
            i++;
        }
        
        if(yes == sizeMolecule)
        {
            validity = true;
        }
        return validity;
    }    
    
    /* Aplicação da Geometria, apenas retorna um vetor. 
       Obedece a teoria VSEPR (valence shell electron pair repulsion) */
    public ArrayList<Vector3d> molecularGeometry(Atom centralAtom, int numberOfLinkedAtom, Preview owner, int i, boolean circle)
    {
        ArrayList<Vector3d> positions = new ArrayList<Vector3d>();
        double t = 0.150f;
        // Segundo Ligante
        double x2;
        double y2;
        double z2;
        // Terceiro Ligante
        double x3;
        double y3;
        double z3;
        // Quarto Ligante
        double x4;
        double y4;
        double z4;
        
        
        int multi = 1;

        if( (i % 2) == 0)
        {
            multi = - 1;
        }

        if( numberOfLinkedAtom == 2 )
        {
            if( centralAtom.getLayerValence() == centralAtom.getLinks() ) // Linear
            {
                x2 = 0;
                y2 = - t;
                z2 = 0;

                positions.add(new Vector3d(x2, y2, z2));

                owner.addGeometry("Linear");
            }
            else if( (centralAtom.getLayerValence() - centralAtom.getLinks()) == 2 ) // Angular 
            {
                x2 = t * Math.cos(Math.toRadians(19));
                y2 = - t * Math.sin(Math.toRadians(19));
                z2 = 0;

                positions.add(new Vector3d(x2 * multi, y2, z2));

                owner.addGeometry("Angular");
            }
            else if( (centralAtom.getLayerValence() - centralAtom.getLinks()) == 4 ) // Angular
            {
                x2 = t * Math.cos(Math.toRadians(14.5));
                y2 = - t * Math.sin(Math.toRadians(14.5));
                z2 = 0;

                positions.add(new Vector3d(x2 * multi, y2, z2));

                owner.addGeometry("Angular");
            }                
        }
        else if( numberOfLinkedAtom == 3 )
        {
            if( centralAtom.getLayerValence() == centralAtom.getLinks() ) // Trigonal Plana
            {
                x2 =  (float) Math.sqrt(3) * t/ 2;
                y2 = -t/2;
                z2 = 0;

                x3 = - x2;
                y3 = y2;
                z3 = 0;

                positions.add(new Vector3d(x2, y2, z2));
                positions.add(new Vector3d(x3, y3, z3));

                owner.addGeometry("Trigonal Plana");
            }
            else if( (centralAtom.getLayerValence() - centralAtom.getLinks()) == 2 )
            {
                x2 = t * Math.sin(Math.toRadians(107.5));
                y2 = t * Math.cos(Math.toRadians(107.5));
                z2 = 0;

                x3 = t * ( Math.cos(Math.toRadians(107.5)) / Math.sin(Math.toRadians(107.5)) * ( 1 - Math.cos(Math.toRadians(107.5))));
                y3 = y2;
                z3 = Math.sqrt( Math.pow(t, 2) - Math.pow(x3, 2) - Math.pow(y3, 2));

                positions.add(new Vector3d(x2, y2, z2));
                positions.add(new Vector3d(x3, y3, z3));

                owner.addGeometry("Piramidal");
            }
        }
        else if ( numberOfLinkedAtom == 4 )
        {
            if( centralAtom.getLayerValence() == centralAtom.getLinks())
            {
                x2 = t * Math.sin(Math.toRadians(109.47));
                y2 = t * Math.cos(Math.toRadians(109.47));
                z2 = 0;

                x3 = t * Math.cos(Math.toRadians(109.47)) / Math.sin(Math.toRadians(109.47)) * (1 - Math.cos(Math.toRadians(109.47)));
                y3 = y2;
                z3 = Math.sqrt( Math.pow(t, 2) - Math.pow(x3, 2) - Math.pow(y3, 2));               

                x4 = x3;   
                y4 = y3;
                z4 = - z3;

                positions.add(new Vector3d(x2, y2, z2));
                positions.add(new Vector3d(x3, y3, z3));      
                positions.add(new Vector3d(x4, y4, z4));    

                owner.addGeometry("Tetraédrica");
            }
        }
        
        return positions;
    }
}