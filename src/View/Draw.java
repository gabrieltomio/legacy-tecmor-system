
/**
 *
 * @author Gabriel Tomio Nardes
 */

package View;

import Model.Atom;
import Model.Atoms.Bromine;
import Model.Atoms.Carbon;
import Model.Atoms.Chlorine;
import Model.Atoms.Fluorine;
import Model.Atoms.Hydrogen;
import Model.Atoms.Iodine;
import Model.Link;
import Model.Atoms.Nitrogen;
import Model.Atoms.Oxygen;
import Model.Atoms.Phosphorus;
import Model.Atoms.Sulfur;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Draw extends JPanel implements MouseListener, MouseMotionListener
{
    private static final int HIT_BOX_SIZE = 6;
    private Point endStop, startingPoint, perfumeryStart, endPerfumery, identification;
    private ConstructionScreen owner;
    private Atom currentAtom, firstConnectingAtomTo, secondConnectingAtomTo, undefinedAtom;
    private Link undefinedLink;
    private ArrayList<Link> currentLinks;
    private Graphics2D g2d;
    
    public Draw(ConstructionScreen owner)
    {
        this.owner = owner;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }
    
    /* CLICAR */
    public void mouseClicked(MouseEvent e) 
    {
        //-----------------------------------
    }
    
    /* PESSIONAR */
    public void mousePressed(MouseEvent e) 
    {
        if( owner.getSelectedTool() == 13 )
        {
            currentAtom = null;
            if(findAtom(e))
            {
                currentAtom = undefinedAtom;
                currentLinks = findAtomInConnections(currentAtom);
            }
        }
        else if( (owner.getSelectedTool() >= 14) && (owner.getSelectedTool() <= 16) )
        {            
            firstConnectingAtomTo = null;
            
            if(findAtom(e))
            {
                startingPoint = new Point();
                firstConnectingAtomTo = undefinedAtom;
                startingPoint.x = firstConnectingAtomTo.getCoordinates().x + 11;
                startingPoint.y = firstConnectingAtomTo.getCoordinates().y + 11;
            }
        }
        
        identification = new Point();
        identification.x = e.getX();
        identification.y = e.getY();
    }

    /* ********************** LIBERAR ********************
     * Quando o botão do mouse é solto dentro do JPanel então este método é 
     * ativado para executar pinturas no JPanel onde dependendo do botão 
     * selecionado ele vai executar em certas condições.
     */
    public void mouseReleased(MouseEvent e) 
    {        
        if(owner.getSelectedTool() <= 12)
        {
            startingPoint = new Point();
            startingPoint.x = e.getX() - 11;
            startingPoint.y = e.getY() - 11;        
                           
            if(owner.getSelectedTool() == 1)
            {                
                currentAtom = new Hydrogen();
            }
            else if(owner.getSelectedTool() == 2)
            {
                currentAtom = new Carbon();
            }            
            else if(owner.getSelectedTool() == 3)
            {
                currentAtom = new Oxygen();
            }
            else if(owner.getSelectedTool() == 4)
            {
                currentAtom = new Nitrogen();
            }
            else if(owner.getSelectedTool() == 5)
            {
                currentAtom = new Fluorine();
            }
            else if(owner.getSelectedTool() == 6)
            {
                currentAtom = new Bromine();
            }
            else if(owner.getSelectedTool() == 7)
            {
                currentAtom = new Chlorine();
            }
            else if(owner.getSelectedTool() == 8)
            {
                currentAtom = new Iodine();
            }
            else if(owner.getSelectedTool() == 9)
            {
                currentAtom = new Phosphorus();
            }
            else if(owner.getSelectedTool() == 10)
            {
                currentAtom = new Sulfur();
            }
            
            registering();
            repaint();
        }
        else if( (owner.getSelectedTool() >= 14) && (owner.getSelectedTool() <= 16) )
        {
            /* Nesta condição será aceito apanas as ligações */
            endStop = new Point();
            endStop.x = e.getX();
            endStop.y = e.getY();
            secondConnectingAtomTo = null;
            
            if(firstConnectingAtomTo != null)
            {
                boolean control = false;
                if ( findAtom(e) )
                {
                    secondConnectingAtomTo = undefinedAtom;
                    
                    boolean firstAtomValence = false;
                    boolean secondAtomValence = false;
                    String type;
                    
                    if( owner.getSelectedTool() == 14)
                    {
                        firstAtomValence = owner.getRules().valenceRule(firstConnectingAtomTo, transformType("simple"));
                        secondAtomValence = owner.getRules().valenceRule(secondConnectingAtomTo, transformType("simple"));
                        type = "simple";
                    }
                    else if( owner.getSelectedTool() == 15)
                    {
                        firstAtomValence = owner.getRules().valenceRule(firstConnectingAtomTo, transformType("double"));
                        secondAtomValence = owner.getRules().valenceRule(secondConnectingAtomTo, transformType("double"));
                        type = "double";
                    }
                    else
                    {
                        firstAtomValence = owner.getRules().valenceRule(firstConnectingAtomTo, transformType("triple"));
                        secondAtomValence = owner.getRules().valenceRule(secondConnectingAtomTo, transformType("triple"));
                        type = "triple";
                    }
                    
                    boolean eletronegativity = owner.getRules().ruleEtronegativity(firstConnectingAtomTo, secondConnectingAtomTo);
                    boolean repeat = false;
                    
                    // Verifica se pode ou não ser feita uma troca de ligação
                    if(linkExchange(firstConnectingAtomTo, secondConnectingAtomTo))
                    {
                        Link lk = findingBondingBetweenAtoms(firstConnectingAtomTo, secondConnectingAtomTo);
                        
                        if(lk.getType().equalsIgnoreCase("simple"))
                        {
                            if(owner.getSelectedTool() == 14)
                            {
                                if( (((firstConnectingAtomTo.getLinks() - 1) + 1) <= firstConnectingAtomTo.getValence())
                                     && (((secondConnectingAtomTo.getLinks() - 1) + 1) <= secondConnectingAtomTo.getValence()) )
                                {
                                    repeat = true;
                                }
                            }
                            else if(owner.getSelectedTool() == 15)
                            {
                                if( (((firstConnectingAtomTo.getLinks() - 1) + 2) <= firstConnectingAtomTo.getValence())
                                     && (((secondConnectingAtomTo.getLinks() - 1) + 2) <= secondConnectingAtomTo.getValence()) )
                                {
                                    repeat = true;
                                }
                            }
                            else if(owner.getSelectedTool() == 16)
                            {
                                if( (((firstConnectingAtomTo.getLinks() - 1) + 3) <= firstConnectingAtomTo.getValence())
                                     && (((secondConnectingAtomTo.getLinks() - 1) + 3) <= secondConnectingAtomTo.getValence()) )
                                {
                                    repeat = true;
                                }
                            }
                        }
                        else if(lk.getType().equalsIgnoreCase("double"))
                        {
                            if(owner.getSelectedTool() == 14)
                            {
                                if( (((firstConnectingAtomTo.getLinks() - 2) + 1) <= firstConnectingAtomTo.getValence())
                                     && (((secondConnectingAtomTo.getLinks() - 2) + 1) <= secondConnectingAtomTo.getValence()) )
                                {
                                    repeat = true;
                                }
                            }
                            else if(owner.getSelectedTool() == 15)
                            {
                                if( (((firstConnectingAtomTo.getLinks() - 2) + 2) <= firstConnectingAtomTo.getValence())
                                     && (((secondConnectingAtomTo.getLinks() - 2) + 2) <= secondConnectingAtomTo.getValence()) )
                                {
                                    repeat = true;
                                }
                            }
                            if(owner.getSelectedTool() == 16)
                            {
                                if( (((firstConnectingAtomTo.getLinks() - 2) + 3) <= firstConnectingAtomTo.getValence())
                                     && (((secondConnectingAtomTo.getLinks() - 2) + 3) <= secondConnectingAtomTo.getValence()) )
                                {
                                    repeat = true;
                                }
                            }
                        }
                        else
                        {
                            repeat = true;
                        }
                    }
                   
                    // Verifica se já há uma ligação, se sim troca ele pela atual
                    if(repeat)
                    {
                        Link lk = findingBondingBetweenAtoms(firstConnectingAtomTo, secondConnectingAtomTo);

                        if(lk.getType().equalsIgnoreCase("simple"))
                        {                            
                            firstConnectingAtomTo.unlink();
                            secondConnectingAtomTo.unlink();
                        }
                        else if(lk.getType().equalsIgnoreCase("double"))
                        {
                            for(int i = 0; i < 2; i++)
                            {
                                firstConnectingAtomTo.unlink();
                                secondConnectingAtomTo.unlink();
                            }
                        }
                        else
                        {
                            for(int i = 0; i < 3; i++)
                            {
                                firstConnectingAtomTo.unlink();
                                secondConnectingAtomTo.unlink();
                            }
                        }

                        owner.getMolecule().getLinks().remove(lk);
                    }
                    
                    // Fazer ligação
                    if( (firstConnectingAtomTo != secondConnectingAtomTo) 
                         && (secondConnectingAtomTo != null) 
                         && ( ((!firstAtomValence) && (!secondAtomValence)) || repeat) 
                         && (eletronegativity) 
                         && (true/*(firstConnectingAtomTo)*/)
                         && (true/*verificationCentralAtoms(secondConnectingAtomTo)*/))
                    {                              
                        Link link = new Link();
                        link.setA(firstConnectingAtomTo);
                        link.setB(secondConnectingAtomTo);
                        link.setStartingPoint(startingPoint);
                        link.setEndStop(endStop);

                        if(owner.getSelectedTool() == 14)
                        {
                            link.setType("simple");
                            connect();
                        }
                        else if(owner.getSelectedTool() == 15)
                        {
                            link.setType("double");
                            for(int i = 0; i < 2; i++)
                            {
                                connect();
                            }
                        }
                        else
                        {
                            link.setType("triple");
                            for(int i = 0; i < 3; i++)
                            {
                                connect();
                            }
                        }
                        owner.getMolecule().getLinks().add(link);    
                        
                        
                    }
                    else
                    {
                        control = true;
                        if(firstConnectingAtomTo != secondConnectingAtomTo)
                        {                            
                            if(firstAtomValence)
                            {
                                JOptionPane.showMessageDialog(owner, "O " + firstConnectingAtomTo.getName() + " se estabiliza com " + firstConnectingAtomTo.getValence() + " ligações","Ligação Excedida",JOptionPane.INFORMATION_MESSAGE , new ImageIcon(getClass().getResource("/Icons/link_error.png")));
                            }
                            else if(secondAtomValence)
                            {
                                JOptionPane.showMessageDialog(owner, "O " + secondConnectingAtomTo.getName() + " se estabiliza com " + secondConnectingAtomTo.getValence() + " ligações","Ligação Excedida",JOptionPane.INFORMATION_MESSAGE , new ImageIcon(getClass().getResource("/Icons/link_error.png")));
                            }
                            else if(!verificationCentralAtoms(firstConnectingAtomTo) || !verificationCentralAtoms(secondConnectingAtomTo))
                            {
                                JOptionPane.showMessageDialog(owner, "O sistema aceita no máximo 6 átomos centrais");
                            }                            
                            else if(!eletronegativity)
                            {
                                JOptionPane.showMessageDialog(owner, "A variação de eletronegatividade entre o " + firstConnectingAtomTo.getName() + 
                                        " e o " + secondConnectingAtomTo.getName() + " é maior do que 1,9");
                            }
                            
                        }
                    }
                }
                if( (secondConnectingAtomTo == null) || (control) )
                {
                    startingPoint.x = -1000;
                    startingPoint.y = -1000;
                    endStop.x = -1000;
                    endStop.y = -1000;
                }
                else
                {                    
                    endStop.x = secondConnectingAtomTo.getCoordinates().x + 11;
                    endStop.y = secondConnectingAtomTo.getCoordinates().y + 11;
                }
                
                repaint();
            }
        }
        else if( owner.getSelectedTool() == 17)
        {
            if( findAtom(e) )
            {
                currentLinks =  findAtomInConnections(undefinedAtom);
                for( int i = 0; i < currentLinks.size(); i++)
                {
                    if(undefinedAtom != currentLinks.get(i).getA())
                    {
                        secondConnectingAtomTo = currentLinks.get(i).getA();
                    }
                    else
                    {
                        secondConnectingAtomTo = currentLinks.get(i).getB();
                    }
                    
                    if( transformType(currentLinks.get(i).getType()) == 1)
                    {
                        desconnect();
                    }
                    else if( transformType(currentLinks.get(i).getType()) == 2)
                    {
                        for(int f = 0; f < 2; f++)
                        {
                            desconnect();
                        }
                    }
                    else
                    {
                        for(int f = 0; f < 3; f++)
                        {
                            desconnect();
                        }
                    }
                    owner.getMolecule().getLinks().remove(currentLinks.get(i));
                }
                owner.getMolecule().getAtoms().remove(undefinedAtom);
                
            }            
            else if(findLink(e.getX(), e.getY()))
            {
                Atom a = undefinedLink.getA();;
                Atom b = undefinedLink.getB();
                                                 
                if( transformType(undefinedLink.getType()) == 1)
                {
                    a.unlink();
                    b.unlink();
                }
                else if( transformType(undefinedLink.getType()) == 2)
                {
                    for(int f = 0; f < 2; f++)
                    {
                        a.unlink();
                        b.unlink();
                    }
                }
                else
                {
                    for(int f = 0; f < 3; f++)
                    {
                        a.unlink();
                        b.unlink();
                    }
                }
                owner.getMolecule().getLinks().remove(undefinedLink);                
               
            }
            
            repaint();
        }

    }

    /* ENTRAR NA ÁREA */
    public void mouseEntered(MouseEvent e) 
    {
        
        //-------------------------------
    }
    
    /* SAIR DA ÁREA */
    public void mouseExited(MouseEvent e) 
    {
        identification = null;
        repaint();
        //-------------------------------
    }

    /* ARRASTAR */
    public void mouseDragged(MouseEvent e) 
    {
        if( (owner.getSelectedTool() == 13) )
        {
            if( currentAtom != null)
            {               
                currentAtom.getCoordinates().x = e.getX() - 11;
                currentAtom.getCoordinates().y = e.getY() - 11;
                
                if(currentLinks != null)
                {
                    for( int i = 0; i < currentLinks.size(); i++)
                    {
                        if( currentAtom == currentLinks.get(i).getA() )
                        {
                            currentLinks.get(i).getStartingPoint().x = e.getX();
                            currentLinks.get(i).getStartingPoint().y = e.getY();
                        }
                        else
                        {
                            currentLinks.get(i).getEndStop().x = e.getX();
                            currentLinks.get(i).getEndStop().y = e.getY();
                        }
                    }
                }
                if(perfumeryStart != null && endPerfumery != null)
                {
                    perfumeryStart.x = currentAtom.getCoordinates().x - 1;
                    perfumeryStart.y = currentAtom.getCoordinates().y - 1;
                    endPerfumery.x = 25;
                    endPerfumery.y = 25;
                }
            }
        }
        else if( (owner.getSelectedTool() >= 14) && (owner.getSelectedTool() <= 16) )
        {
            if( findAtom(e) )
            {
                perfumeryStart = new Point();
                endPerfumery = new Point();
                perfumeryStart.x = undefinedAtom.getCoordinates().x - 1;
                perfumeryStart.y = undefinedAtom.getCoordinates().y - 1;
                endPerfumery.x = 25;
                endPerfumery.y = 25;
            }
            else
            {
                perfumeryStart = null;
                endPerfumery = null;
            }
            if(firstConnectingAtomTo != null)
            {
                endStop = new Point();
                endStop.x = e.getX();
                endStop.y = e.getY();
               
            }           
        }
                
        if(identification != null)
        {
            identification.x = e.getX();
            identification.y = e.getY();
        }
        repaint();
  }

    /* MOVIMENTAR */
    public void mouseMoved(MouseEvent e) 
    {
        identification = new Point();
        perfumeryStart = new Point();
        endPerfumery = new Point();
        boolean found = false;
        
        if(findAtom(e))
        {
            perfumeryStart.x = undefinedAtom.getCoordinates().x - 1;
            perfumeryStart.y = undefinedAtom.getCoordinates().y - 1;
            endPerfumery.x = 25;
            endPerfumery.y = 25;
            found = true;
            identification = null;
        }
            
        if(owner.getSelectedTool() == 17)
        {
            if(findLink(e.getX(), e.getY()))
            {
//                System.out.println("Entrei");
            }
        }
        
        /* 
         * Caso não é encontrado um átomo os pontos de perfumaria são 
         * desabilitados.
         *
         */
        if(!found)
        {
            perfumeryStart = null;
            startingPoint = null;
        }
        
        if(owner.getSelectedTool() != 13 && identification != null)
        {            
            identification.x = e.getX();
            identification.y = e.getY();
        }
        else
        {
            identification = null;
        }
        
        repaint();
    }
       
    /* PINTAR */
    public void paint(Graphics g) 
    {
        super.paint(g);
        g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        ArrayList<Atom> bridge = owner.getMolecule().getAtoms();
       
        for(int i = 0; i < bridge.size(); i++)
        {
            int fontSize = 28;

            g.setFont(new Font("Arial", Font.BOLD, fontSize));
     
            if(bridge.get(i) instanceof Hydrogen)
            {
                Hydrogen h = (Hydrogen) bridge.get(i);
                g.drawString(h.getSymbol(), h.getCoordinates().x + 2, h.getCoordinates().y + 22);
            }
            else if(bridge.get(i) instanceof Carbon)
            {
                Carbon c = (Carbon) bridge.get(i);
                g.drawString(c.getSymbol(), c.getCoordinates().x + 2, c.getCoordinates().y + 22);
            }
            
            else if(bridge.get(i) instanceof Oxygen)
            {
                Oxygen o = (Oxygen) bridge.get(i);
                g.drawString(o.getSymbol(), o.getCoordinates().x + 2, o.getCoordinates().y + 22);
            }
            else if(bridge.get(i) instanceof Nitrogen)
            {
                Nitrogen n = (Nitrogen) bridge.get(i);
                g.drawString(n.getSymbol(), n.getCoordinates().x + 2, n.getCoordinates().y + 22);
            }
            else if(bridge.get(i) instanceof Fluorine)
            {
                Fluorine f = (Fluorine) bridge.get(i);
                g.drawString(f.getSymbol(), f.getCoordinates().x + 2, f.getCoordinates().y + 22);
            }
            else if(bridge.get(i) instanceof Bromine)
            {
                Bromine br = (Bromine) bridge.get(i);
                g.drawString(br.getSymbol(), br.getCoordinates().x + 2, br.getCoordinates().y + 22);
            }
            else if(bridge.get(i) instanceof Chlorine)
            {
                Chlorine cl = (Chlorine) bridge.get(i);
                g.drawString(cl.getSymbol(), cl.getCoordinates().x + 2, cl.getCoordinates().y + 22);
            }
            else if(bridge.get(i) instanceof Iodine)
            {
                Iodine io = (Iodine) bridge.get(i);
                g.drawString(io.getSymbol(), io.getCoordinates().x + 8, io.getCoordinates().y + 22);
            }
            else if(bridge.get(i) instanceof Phosphorus)
            {
                Phosphorus p = (Phosphorus) bridge.get(i);
                g.drawString(p.getSymbol(), p.getCoordinates().x + 2, p.getCoordinates().y + 22);
            }
            else if(bridge.get(i) instanceof Sulfur)
            {
                Sulfur s = (Sulfur) bridge.get(i);
                g.drawString(s.getSymbol(), s.getCoordinates().x + 2, s.getCoordinates().y + 22);
            }
        }
       
        ArrayList<Link> links = owner.getMolecule().getLinks();
        for(int i = 0; i < links.size(); i++)
        {
            if(links.get(i) instanceof Link)
            {
                Link l = links.get(i);
                g2d.setStroke(new BasicStroke (2.0f));                  
                
                if(l.getType().equalsIgnoreCase("simple"))
                {
                    
                    g.drawLine(l.getStartingPoint().x + fixLink(l.getStartingPoint().x, l.getStartingPoint().y, l.getEndStop().x, l.getEndStop().y).x, 
                               l.getStartingPoint().y + fixLink(l.getStartingPoint().x, l.getStartingPoint().y, l.getEndStop().x, l.getEndStop().y).y, 
                               l.getEndStop().x       + fixLink(l.getEndStop().x, l.getEndStop().y, l.getStartingPoint().x, l.getStartingPoint().y).x, 
                               l.getEndStop().y       + fixLink(l.getEndStop().x, l.getEndStop().y, l.getStartingPoint().x, l.getStartingPoint().y).y);
                }
                else if(l.getType().equalsIgnoreCase("double"))
                {
                    g.drawLine(returnPoint(l, 1, -6).x  + fixLink(l.getStartingPoint().x, l.getStartingPoint().y, l.getEndStop().x, l.getEndStop().y).x, 
                               returnPoint(l, 1, -6).y  + fixLink(l.getStartingPoint().x, l.getStartingPoint().y, l.getEndStop().x, l.getEndStop().y).y, 
                               returnPoint(l, 2, -6).x        + fixLink(l.getEndStop().x,       l.getEndStop().y, l.getStartingPoint().x, l.getStartingPoint().y).x, 
                               returnPoint(l, 2, -6).y        + fixLink(l.getEndStop().x,       l.getEndStop().y, l.getStartingPoint().x, l.getStartingPoint().y).y);
                    
                    g.drawLine(returnPoint(l, 1, +6).x + fixLink(l.getStartingPoint().x, l.getStartingPoint().y, l.getEndStop().x, l.getEndStop().y).x, 
                               returnPoint(l, 1, +6).y + fixLink(l.getStartingPoint().x, l.getStartingPoint().y, l.getEndStop().x, l.getEndStop().y).y, 
                               returnPoint(l, 2, +6).x + fixLink(l.getEndStop().x, l.getEndStop().y, l.getStartingPoint().x, l.getStartingPoint().y).x, 
                               returnPoint(l, 2, +6).y + fixLink(l.getEndStop().x, l.getEndStop().y, l.getStartingPoint().x, l.getStartingPoint().y).y);
                }
                else
                {
                    g.drawLine(returnPoint(l, 1, -8).x  + fixLink(l.getStartingPoint().x, l.getStartingPoint().y, l.getEndStop().x, l.getEndStop().y).x, 
                               returnPoint(l, 1, -8).y  + fixLink(l.getStartingPoint().x, l.getStartingPoint().y, l.getEndStop().x, l.getEndStop().y).y, 
                               returnPoint(l, 2, -8).x        + fixLink(l.getEndStop().x,       l.getEndStop().y, l.getStartingPoint().x, l.getStartingPoint().y).x, 
                               returnPoint(l, 2, -8).y        + fixLink(l.getEndStop().x,       l.getEndStop().y, l.getStartingPoint().x, l.getStartingPoint().y).y);
                    
                    g.drawLine(l.getStartingPoint().x + fixLink(l.getStartingPoint().x, l.getStartingPoint().y, l.getEndStop().x, l.getEndStop().y).x, 
                               l.getStartingPoint().y + fixLink(l.getStartingPoint().x, l.getStartingPoint().y, l.getEndStop().x, l.getEndStop().y).y, 
                               l.getEndStop().x       + fixLink(l.getEndStop().x, l.getEndStop().y, l.getStartingPoint().x, l.getStartingPoint().y).x, 
                               l.getEndStop().y       + fixLink(l.getEndStop().x, l.getEndStop().y, l.getStartingPoint().x, l.getStartingPoint().y).y);
                    
                    g.drawLine(returnPoint(l, 1, +8).x + fixLink(l.getStartingPoint().x, l.getStartingPoint().y, l.getEndStop().x, l.getEndStop().y).x, 
                               returnPoint(l, 1, +8).y + fixLink(l.getStartingPoint().x, l.getStartingPoint().y, l.getEndStop().x, l.getEndStop().y).y, 
                               returnPoint(l, 2, +8).x + fixLink(l.getEndStop().x, l.getEndStop().y, l.getStartingPoint().x, l.getStartingPoint().y).x, 
                               returnPoint(l, 2, +8).y + fixLink(l.getEndStop().x, l.getEndStop().y, l.getStartingPoint().x, l.getStartingPoint().y).y);
                }
            }
        }
        if( (startingPoint != null) && (endStop != null) )
        {
            g2d.setStroke(new BasicStroke (2.0f));
            if(owner.getSelectedTool() == 14)
            {          
                
                g.drawLine(startingPoint.x, startingPoint.y , endStop.x, endStop.y);
            }
            else if(owner.getSelectedTool() == 15)
            {
                g.drawLine(startingPoint.x +3, startingPoint.y +3, endStop.x +3, endStop.y +3);
                g.drawLine(startingPoint.x, startingPoint.y -3, endStop.x, endStop.y -3);
            }
            else if(owner.getSelectedTool() == 16)
            {
                g.drawLine(startingPoint.x, startingPoint.y, endStop.x, endStop.y);
                g.drawLine(startingPoint.x +8, startingPoint.y +8, endStop.x +8, endStop.y +8);
                g.drawLine(startingPoint.x -8, startingPoint.y -8, endStop.x -8, endStop.y -8);
            }
        }
        
        if(perfumeryStart != null && endPerfumery != null)
        {
            g2d.setStroke(new BasicStroke (1.0f));
            g.drawRect(perfumeryStart.x, perfumeryStart.y, endPerfumery.x, endPerfumery.y);
        }
        
        if(identification != null)
        {
            
            int fontSize = 12;

            g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
            g2d.setStroke(new BasicStroke (2.0f));
            if(owner.getSelectedTool() == 1)
            {
                g.drawString("H", identification.x + 10, identification.y );
            }
            else if(owner.getSelectedTool() == 2)
            {
                g.drawString("C", identification.x + 10, identification.y);
            }
            else if(owner.getSelectedTool() == 3)
            {
                g.drawString("O", identification.x + 10, identification.y);
            }
            else if(owner.getSelectedTool() == 4)
            {
                g.drawString("N", identification.x + 10, identification.y);
            }
            else if(owner.getSelectedTool() == 5)
            {
                g.drawString("F", identification.x + 10, identification.y);
            }
            else if(owner.getSelectedTool() == 6)
            {
                g.drawString("Br", identification.x + 10, identification.y);
            }
            else if(owner.getSelectedTool() == 7)
            {
                g.drawString("Cl", identification.x + 10, identification.y);
            }
            else if(owner.getSelectedTool() == 8)
            {
                g.drawString("I", identification.x + 10, identification.y);
            }
            else if(owner.getSelectedTool() == 9)
            {
                g.drawString("P", identification.x + 10, identification.y);
            }
            else if(owner.getSelectedTool() == 10)
            {
                g.drawString("S", identification.x + 10, identification.y);
            }
            else if(owner.getSelectedTool() == 14)
            {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/PicturesForDrawing/simple.png")).getImage(), identification.x + 6, identification.y - 5, 15, 15, null);
            }
            else if(owner.getSelectedTool() == 15)
            {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/PicturesForDrawing/double.png")).getImage() , identification.x + 6, identification.y - 5, 19, 19, null);
            }
            else if(owner.getSelectedTool() == 16)
            {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/PicturesForDrawing/triple.png")).getImage() , identification.x + 6, identification.y - 5, 15, 15, null);
            }
            else if(owner.getSelectedTool() == 17)
            {
                g.drawImage(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete.png")).getImage(), identification.x + 7, identification.y + -5, 13, 13, null);
            }
        }
        
        owner.setInformations();
    }
    
    /* --------------------------- MÉTODOS EM GERAL ------------------------- */
    
    /* Encontra um átomo diante à um evento  */
    private boolean findAtom(MouseEvent e)
    {
        boolean discover = false;
        ArrayList<Atom> atoms = owner.getMolecule().getAtoms();
        int size = atoms.size();            
        int i = 0;
        
        while( i < size )
        {
            Atom atomNow = atoms.get(i);
            int xLeft = atomNow.getCoordinates().x,
            xRight = atomNow.getCoordinates().x + 23,
            yUpper = atomNow.getCoordinates().y,
            yLess = atomNow.getCoordinates().y + 23;

            if( e.getX() > xLeft && e.getX() < xRight && e.getY() > yUpper && e.getY() < yLess)
            {         
                // Monta um átomo indeterminado
                undefinedAtom = atomNow;
                discover = true;
                i = size + 1;
            }
            i++;
        }
        return discover;
    }
    
    /* Encontrar Ligação */
    private boolean findLink(int x, int y)
    {
        boolean discover = false;
        int boxX = x - HIT_BOX_SIZE / 2;
        int boxY = y - HIT_BOX_SIZE / 2;

        int width = HIT_BOX_SIZE;
        int height = HIT_BOX_SIZE;
        
        ArrayList<Link> links = owner.getMolecule().getLinks();
        
        int i = 0;
        while(i < links.size()) 
        {
            Link linkNow = links.get(i);
            Line2D.Double line = new Line2D.Double(linkNow.getStartingPoint(), linkNow.getEndStop());
            
            if(line.intersects(boxX, boxY, width, height)) 
            {
                undefinedLink = linkNow;
		discover = true;
            }
            i++;
	}	
        return discover;
    }
    
    /* Encontra as ligações que estão juntas à um átomo */
    private ArrayList<Link> findAtomInConnections(Atom atom)
    {
        ArrayList<Link> connections = new ArrayList<Link>();
        
        ArrayList<Link> links = owner.getMolecule().getLinks();
        int size = links.size();
        int i = 0;
        
        while ( i < size )
        {
            Link l = links.get(i);
            
            if( (atom == l.getA()) || (atom == l.getB()) )
            {
                connections.add(l);
                if( connections.size() == atom.getLinks())
                {
                    i = size + 1;
                }
            }
            i++;
        }
        
        return connections;
    }
    
    /* Encontrar o átomo pelo indice */
    private int findAtomInIndex(Atom atom)
    {
        int index = 0;
        ArrayList<Atom> atoms = owner.getMolecule().getAtoms();
        int size = atoms.size();
        int i = 0;
        
        while( i < size )
        {
            Atom a = atoms.get(i);
            if( a == atom )
            {
                index = i;
                i = size + 1;
            }
            i++;
        }
        return index;
    }
    
    /* Conecta átomo */
    private void connect()
    {
        owner.getMolecule().getAtoms().get(findAtomInIndex(firstConnectingAtomTo)).link();
        owner.getMolecule().getAtoms().get(findAtomInIndex(secondConnectingAtomTo)).link();
    }
    
    /* Desconecta átomo */
    private void desconnect()
    {
        owner.getMolecule().getAtoms().get(findAtomInIndex(undefinedAtom)).unlink();
        owner.getMolecule().getAtoms().get(findAtomInIndex(secondConnectingAtomTo)).unlink();
    }
    /* Um "cadastro" no sistema do átomo colocado no plano */
    private void registering()
    {
        currentAtom.setCoordinates(startingPoint);
        currentAtom.setCentral(false);
        owner.getMolecule().getAtoms().add(currentAtom);
    }
    
    /* Transformação do tipo de ligação*/
    private int transformType(String t)
    {
        int type = 0;
        
        if(t.equalsIgnoreCase("simple"))
        {
            type = 1;
        }
        else if(t.equalsIgnoreCase("double"))
        {
            type = 2;
        }
        else
        {
            type = 3;
        }
        return type;
    }
    
    /* Verifificação se há ligação entre dois átomos */
    private boolean linkExchange(Atom fAtom, Atom sAtom)
    {
        int i = 0, size = owner.getMolecule().getLinks().size();
        ArrayList<Link> links = owner.getMolecule().getLinks();
        boolean difference = false;
        
        while(i < size)
        {
            if( ((fAtom ==  links.get(i).getA()) && (sAtom == links.get(i).getB())) || ((fAtom == links.get(i).getB()) && (sAtom == links.get(i).getA())) )
            {
                difference = true;
                i = size + 1;
            }
            i++;
        }     
        
        return difference;
    }
    
    /* Encontrar ligação entre átomos */
    public Link findingBondingBetweenAtoms(Atom fAtom, Atom sAtom)
    {
        Link link = null;
        int i = 0, size = owner.getMolecule().getLinks().size();
        ArrayList<Link> links = owner.getMolecule().getLinks();
        
        while(i < size)
        {
            if( ((fAtom ==  links.get(i).getA()) && (sAtom == links.get(i).getB())) || ((fAtom == links.get(i).getB()) && (sAtom == links.get(i).getA())) )
            {
                link = links.get(i);
                i = size + 1;
            }
            i++;
        }
        
        return link;
    }
    
    /* Calcular os átomos centrais */
    public void calculateCentralAtom()
    {
        ArrayList<Atom> atoms = owner.getMolecule().getAtoms();
        ArrayList<Link> lks = owner.getMolecule().getLinks();
        ArrayList<Atom> centralAtom = owner.getMolecule().getCentralAtom();
        
        centralAtom.clear();
        int count = 0;
        for(int i = 0; i < atoms.size(); i++)
        {
            int numbers = 0;
            int j = 0;
            
            while( j < lks.size() )
            {
                if( (atoms.get(i) == lks.get(j).getA()) || (atoms.get(i) == lks.get(j).getB()))
                {
                    numbers ++;
                }
                j++;
            }
            
            if(numbers >= 2)
            {
                centralAtom.add(count, atoms.get(i));
                count ++;
            }
        }
    }
    
    private boolean verificationCentralAtoms(Atom atom)
    {
        boolean validate = false, contain = false;
        calculateCentralAtom();
        ArrayList<Atom> centralAtoms = owner.getMolecule().getCentralAtom();
        ArrayList<Link> lks = owner.getMolecule().getLinks();
        
        int i = 0;
        while( i < centralAtoms.size() )
        {
            if(atom == centralAtoms.get(i) )
            {
                validate = true;
                contain = true;
                i = centralAtoms.size() + 1;
            }
            i++;
        }
        
        if( !contain )            
        {
            int numbers = 0;
            int j = 0;
            
            while( j < lks.size() )
            {
                if( (atom == lks.get(j).getA()) || (atom == lks.get(j).getB()))
                {                    
                    numbers ++;
                }
                j++;
            }
            
            if( numbers + 1 >= 2 )
            {
                if(centralAtoms.size() < 5)
                {
                    validate = true;
                }
            }
            else
            {
                validate = true;
            }
        }
        
        return validate;
    }
    
    private Point fixLink(int x1, int y1, int x2, int y2)
    {          
        
        Point xAndY = new Point();
        int distanceY = y1 - y2;
        int distanceX = x1 - x2;
        
        double angle = Math.atan2(distanceY, distanceX) * 180 / Math.PI;
        
        xAndY.x = (int) (-17 * Math.cos(Math.toRadians(angle)));
        xAndY.y = (int) (-17 * Math.sin(Math.toRadians(angle)));
        
        return xAndY;
    } 
    
    private Point returnPoint(Link lTrue, int type, int distance)
    {
        Point xAndY = new Point();
        
        int x1 = lTrue.getStartingPoint().x;
        int x2 = lTrue.getEndStop().x;
        int y1 = lTrue.getStartingPoint().y;
        int y2 = lTrue.getEndStop().y;
        
        int distanceX = x1 - x2;
        int distanceY = y1 - y2;
        
        int l = (int) (Math.sqrt( distanceX * distanceX + distanceY * distanceY));
        
        int x1p = x1 + distance * (y2 - y1) / l;
        int x2p = x2 + distance * (y2 - y1) / l;
        int y1p = y1 + distance * (x1 - x2) / l;
        int y2p = y2 + distance * (x1 - x2) / l;
        
        if(type == 1)
        {
            xAndY.x = x1p;
            xAndY.y = y1p;
        }
        else
        {
            xAndY.x = x2p;
            xAndY.y = y2p;
        }
        
        return xAndY;
    }            
}