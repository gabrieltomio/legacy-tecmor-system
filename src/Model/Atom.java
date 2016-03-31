/**
 *
 * @author Gabriel Tomio Nardes
 */
package Model;

import java.awt.Point;
import java.io.Serializable;
import javax.vecmath.Vector3d;

public abstract class Atom implements Serializable
{
    /*
     * ATRIBUTOS
     * O atributo valence tem como objetivo ver a quantidade de ligações que o
     * átomo pode fazer. Já o links são as ligações feitas no processo de 
     * construção da molécula.
     * 
     * Como não tem uma identificação exata de quem é o átomo o numberAtomic
     * será auxiliar, pois são únicos em cada átomo.
     * 
     * O electronegativity é a eletronegatividade deste átomo.
     * 
     * O coordinates condiz com a localização do átomo no plano de 
     * desenho.
     */
       
    
    private String name;
    private String color;
    private String symbol;
    private double mass;
    private byte layerValence;
    private byte valence;
    private byte links;
    private double electronegativity;
    private float radius;
    private boolean central;
    private Point coordinates;
    private Vector3d position;
    
    public Atom ()
    {
        this.links = 0;
        coordinates = new Point();
    }
    
    /**
     * COMPORTAMENTOS
     */
    
    // Nome para uso em dialogos com o usuário
    protected void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return name;
    }
    
    // Localização do átomo em 2D
    public void setCoordinates(Point coordinates)
    {
        this.coordinates = coordinates;
    }
    public Point getCoordinates()
    {
        return coordinates;
    }
    
    // Quando é feito uma ligação link quando é desfeito unlink
    public void link ()
    {
        ++ links;
    }
    public void unlink ()
    {
        -- links;
    }
    // Links
    public byte getLinks ()
    {
        return links;
    }
    public void setLinks(byte links)
    {
        this.links = links;
    }
    
    // Valência
    public byte getValence()
    {
        return valence;
    }
    protected void setValence(byte valence)
    {
        this.valence = valence;
    }
    
    // Eletronegatividade
    public double getEletronegativity()
    {
        return electronegativity;
    }
    protected void setEletronegativity(double electronegativity)
    {
        this.electronegativity = electronegativity;
    }
    
    // Raio Atômico
    public float getRadius()
    {
        return radius;
    }
    protected void setRadius(float radius)
    {
        this.radius = radius;
    }
    
    /* Vetor na posição tridimensional */
    public void setPosition(Vector3d vector)
    {
        this.position = vector;
    }
    public Vector3d getPosition()
    {
        return position;
    }
    
    // Camada de valência
    protected void setLayerValence(byte layerValence)
    {
        this.layerValence = layerValence;
    }
    public byte getLayerValence()
    {
        return layerValence;
    }
    
    // Massa atômica 
    protected void setMass(double mass)
    {
        this.mass = mass;
    }
    
    public double getMass()
    {
        return mass;
    }
    
    // Cor
    protected void setColor(String color)
    {
        this.color = color;
    }
    
    public String getColor()
    {
        return color;
    }
    
    // Simbolo
    protected void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }
    public String getSymbol()
    {
        return symbol;
    }
    
    // Central
    public void setCentral(boolean central)
    {
        this.central = central;
    }
    public boolean isCentral()
    {
        return central;
    }
}
