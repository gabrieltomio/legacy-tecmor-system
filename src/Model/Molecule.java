
/**
 *
 * @author Gabriel Tomio Nardes
 */
package Model;

// Imports
import java.io.Serializable;
import java.util.ArrayList;

/* Classe voltada para o armazenamento de dados do sistema, inclui os atomos, e 
as ligações entre eles*/
public class Molecule implements Serializable
{
    private ArrayList<Atom> atoms;
    private ArrayList<Link> links;
    private ArrayList<Atom> centralAtoms;
    
    public Molecule ()
    {
        atoms = new ArrayList<Atom>();
        links = new ArrayList<Link>();
        centralAtoms = new ArrayList<Atom>();
    }
    
    // Átomos
    public ArrayList<Atom> getAtoms ()
    {
        return atoms;
    }   
    
    public void setAtoms(ArrayList<Atom> newAtoms)
    {
        atoms.clear();
        
        for(int i = 0; i < newAtoms.size(); i++)
        {
            atoms.add(newAtoms.get(i));
        }
    }
    // Ligações
    public ArrayList<Link> getLinks()
    {
        return links;
    }
    public void setLinks(ArrayList<Link> newLinks)
    {
        links.clear();
        
        for(int i = 0; i < newLinks.size(); i++)
        {
            links.add(newLinks.get(i));
        }            
    }
    
    // Átomos centrais
    public ArrayList<Atom> getCentralAtom()
    {
        return centralAtoms;        
    } 
    
}