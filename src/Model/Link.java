package Model;

import java.awt.Point;
import java.io.Serializable;

public class Link implements Serializable
{
    private Atom a;
    private Atom b;
    private String type;
    private Point startingPoint;
    private Point endStop;
    
    public Link()
    {               
        startingPoint = new Point();
        endStop = new Point();
    }
    
    // Tipo da Ligação
    public void setType(String type)
    {
        this.type = type;
    }
    public String getType()
    {
        return type;
    }
    
    // Primeiro Átomo da Ligação
    public void setA (Atom a)
    {
        this.a = a;
    }
    public Atom getA()
    {
        return a;
    }
    
    // Segundo Átomo da Ligação
    public void setB (Atom b)
    {
        this.b = b;
    }
    public Atom getB ()
    {
        return b;
    }
    
    // Ponto Inicial da Ligação em 2D
    public void setStartingPoint(Point startingPoint)
    {
        this.startingPoint = startingPoint;
    }
    public Point getStartingPoint()
    {
        return startingPoint;
    }
    
    // Ponto Final da Ligação em 2D
    public void setEndStop(Point endStop)
    {
        this.endStop = endStop;
    }
    public Point getEndStop()
    {
        return endStop;
    }
}
