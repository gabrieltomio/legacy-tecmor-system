package View;

import Controller.GeometricCalculations;
import Controller.Rules;
import Model.Atom;
import Model.Link;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.Color;
import java.awt.GraphicsConfiguration;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author Gabriel
 */

public class Preview 
{
    private PreviewScreen owner;
    // Canvas 3D permite o colocação da CG no Frame    
    private Canvas3D canvas3D = createCanvas3D(); 
    // Criação de um universo para colocar todos os átomos
    private SimpleUniverse universe = new SimpleUniverse(canvas3D);
        
    // BranchGroup é uma estrutura para conter todos os átomos
    private BranchGroup group;    
    private TransformGroup transformMolecule;
    
    //private int selectedInteraction;    
    private ArrayList<Atom> atoms;
    private Rules rules;
    private ArrayList<Link> links;
    private ArrayList<Atom> visited;
    private ArrayList<Atom> nextCentralAtom;
    private ArrayList<Atom> circuit;
    private boolean circle;
    private ArrayList<Atom> centralAtoms;
    private ArrayList<Atom> positionsAtoms;
    private ArrayList<String> geometrys;
    private int atomsInCircle;
    
    public Preview(PreviewScreen owner) 
    {
        owner.getConstructionScreen().getDraw().calculateCentralAtom();
        circle = false;
        geometrys = new ArrayList<String>();
        positionsAtoms = new ArrayList<Atom>();
        nextCentralAtom = new ArrayList<Atom>();
        circuit = new ArrayList<Atom>();
        visited = new ArrayList<Atom>();
        group = new BranchGroup();
        group.setCapability(BranchGroup.ALLOW_DETACH);
        transformMolecule = new TransformGroup();
        links = owner.getConstructionScreen().getMolecule().getLinks();
        atoms = owner.getConstructionScreen().getMolecule().getAtoms();
        centralAtoms = owner.getConstructionScreen().getMolecule().getCentralAtom();
        
        this.owner = owner;
        rules = new Rules();
        
        /* Estes comandos permitem que a capacidade de informação do grupo
           seja ilimitada podendo ler e escrever nela sem erros*/
        transformMolecule.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformMolecule.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

        /* Este é o método mais importante, ele define a geometria */
        organize();
        boolean exit = verificationAtoms();
        if(!exit)
        {
            centralize();
            print();
        
            group.addChild(transformMolecule);   

            MouseRotate myMouseRotate = new MouseRotate();
            myMouseRotate.setTransformGroup(transformMolecule);
            myMouseRotate.setSchedulingBounds(new BoundingSphere(new Point3d(0,0,0), 10000));
            group.addChild(myMouseRotate);

            MouseWheelZoom myMouseZoom = new MouseWheelZoom();
            myMouseZoom.setTransformGroup(transformMolecule);
            myMouseZoom.setSchedulingBounds(new BoundingSphere(new Point3d(0,0,0), 10000));
            group.addChild(myMouseZoom);
        }
        else
        {
            geometrys.clear();
        }
                
        // Coloca a luz no BranchGroup
        group.addChild(createLighting());
        
        /* FINALIZAÇÕES */
        // Olha na esfera
        universe.getViewingPlatform().setNominalViewingTransform();
        
        // Adiciona o grupo de objetos para o universo
        universe.addBranchGraph(group);      
        
        
    }   
    
    private Canvas3D createCanvas3D()
    {
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D canvas = new Canvas3D(config);
        return canvas;
    }
    public Canvas3D getCanvas3D()
    {
        return canvas3D;
    }
    
    private DirectionalLight createLighting()
    {
        /* LUZ */
        // Criar uma luz vermelha
        Color3f colorLight = new Color3f(1.0f, 1.0f, 1.0f);
        
        // A luz alcança até 100 unidades da sua origem
        BoundingSphere bounds = new BoundingSphere(new Point3d(0,0,0), 10000);
        
        // Vetor da direção pela luz
        Vector3f lightDirection = new Vector3f(0.0f, 0.0f, -2.0f);
        
        // Criação da direção da luz
        DirectionalLight light = new DirectionalLight(colorLight, lightDirection);
        
        // Colocado oa area de influencia pela luz
        light.setInfluencingBounds(bounds);
        
        return light;
    }
            
    public void organize()
    {       
        owner.getConstructionScreen().getDraw().calculateCentralAtom();
        int sizeCentralAtoms = centralAtoms.size();
        
        if(sizeCentralAtoms == 0)
        {
            atoms.get(0).setPosition(new Vector3d(0, 0.075, 0));
            atoms.get(1).setPosition(new Vector3d(0, -0.075, 0));
                        
            addGeometry("Linear");
        }
        else
        {
            GeometricCalculations gc = new GeometricCalculations(this);
            Atom centralAtom = centralAtoms.get(selectFirstCentralAtom(centralAtoms));
            Atom firstBinder = null;
            Atom reference = centralAtom;
            ArrayList<Atom> binders;
            ArrayList<Vector3d> positions;
            Random r = new Random();
            
            for(int i = 0; i < sizeCentralAtoms; i++)
            {
                binders = linkers(centralAtom);                                
                addNextCentralAtoms(binders);
                positions = rules.molecularGeometry(centralAtom, binders.size(), this, i, circle);
                removeNextCentralAtoms(centralAtom);

                
                if(!circle)
                {
                    if(numberOfCentralAtomsBonded(binders))
                    {
                        reference = centralAtom;

                        if(isCircle(centralAtom))
                        {
                            circle = true;
                        }
                    }
                }
                
                if(i == 0)
                {
                    centralAtom.setPosition(new Vector3d(0, 0, 0));
                    int ran = r.nextInt(binders.size()) - 1;
                    int pst;
                    if(ran == -1)
                    {
                        pst = 0;
                    }
                    else
                    {
                        pst = ran;
                    }
                    
                    firstBinder = binders.get(pst);
                                        
                    firstBinder.setPosition(new Vector3d( 0, 0.15, 0));   
                    
                    binders.remove(pst);                     
                }
                else
                {
                    for(int j = 0; j < visited.size(); j++)
                    {
                        for(int k = 0; k < binders.size(); k++)
                        {
                            if( visited.get(j) == binders.get(k))
                            {
                                binders.remove(k);                                
                            }
                        }
                    }
                }
                
                if(circuit.isEmpty())
                {
                    circle = false;
                }
                
                gc.advancedMolecularGeometry(firstBinder, centralAtom, reference, binders, positions, circle);
                visited.add(centralAtom);
                
                if(circle)
                {
                    for(int j = 0; j < nextCentralAtom.size(); j++)
                    {
                        for(int k = 0; k < circuit.size(); k++)
                        {
                            if(nextCentralAtom.get(j) == circuit.get(k))
                            {
                                centralAtom = circuit.get(k);
                                circuit.remove(k);
                            }
                        }
                    }
                } 
                else
                {
                    if(!nextCentralAtom.isEmpty())
                    {
                        int ran = r.nextInt(nextCentralAtom.size()) - 1;
                        if(ran == -1)
                        {
                            centralAtom = nextCentralAtom.get(0);
                        }
                        else
                        {
                            centralAtom = nextCentralAtom.get( ran );
                        }
                    }
                }
                
                firstBinder = addFirstBinder(centralAtom);
            }
        }
    }            
    
    private void print()
    {
        int sizeAtoms = atoms.size();
        int sizeLinks = links.size();
        double coord [] = new double [6];
        Transform3D trans = new Transform3D();
        
        for(int i = 0; i < sizeAtoms; i++)
        {
            trans.setTranslation(atoms.get(i).getPosition());
            transformMolecule.addChild(getStyleAtoms(atoms.get(i), trans));
        }            
        
        for(int i = 0; i < sizeLinks; i++)
        {
            coord[0] = links.get(i).getA().getPosition().x;
            coord[1] = links.get(i).getA().getPosition().y;
            coord[2] = links.get(i).getA().getPosition().z;
            coord[3] = links.get(i).getB().getPosition().x;
            coord[4] = links.get(i).getB().getPosition().y;
            coord[5] = links.get(i).getB().getPosition().z;
            
            transformMolecule.addChild(drawBonds(coord, links.get(i).getType()));
        }
    }
    
    
    private Atom addFirstBinder(Atom atom)
    {
        Atom firstBinder = null;
        int size = links.size();
        int sizeVisited = visited.size();
        int i = 0;
        int j;
        boolean strike = false;
        
        while( i  < size )
        {
            j = 0;
            if( links.get(i).getA() == atom )
            {
                while( j < sizeVisited )
                {
                    if( links.get(i).getB() == visited.get(j) )
                    {
                        firstBinder = links.get(i).getB();
                        j = sizeVisited + 1;
                        strike = true;
                    }
                    j++;
                }
            }
            else if( links.get(i).getB() == atom )
            {
                while( j < sizeVisited )
                {
                    if( links.get(i).getA() == visited.get(j) )
                    {
                        firstBinder = links.get(i).getA();
                        j = sizeVisited + 1;
                        strike = true;
                    }
                    j++;
                }
            }
            
            if(strike)
            {
                i = size + 1;
            }
            
            i++;
        }
        
        return firstBinder;
    }
    
    private boolean isCircle(Atom centralAtom)
    {
        boolean yes = false;
        boolean soldOut = false;
        ArrayList<Atom> pasts = new ArrayList<Atom>();
        
        Atom nowAtom = centralAtom;
        int i = 0;
        
        while( (!yes) && (!soldOut))
        {
            if(isBinderCentralAtom(nowAtom, centralAtom, pasts, i))
            {
                yes = true;
            }         
            else if( atomsExclusiveBinders(nowAtom, pasts) != null )
            {
                pasts.add(nowAtom);
                nowAtom = atomsExclusiveBinders(nowAtom, pasts);
                i++;
            }  
            else
            {
                soldOut = true;
            }
        }
        
        if(yes)
        {
            circuit = pasts;
            atomsInCircle = i;
            yes = false;
        }
        
        return yes;
    }
    
    private boolean isBinderCentralAtom(Atom atomX, Atom centralAtom, ArrayList<Atom> pasts, int j)
    {
        boolean yes = false;
        boolean cheat = true;
        ArrayList<Link> lks = new ArrayList<Link>();
        
        for(int i = 0; i < links.size(); i++)
        {
            if(links.get(i).getA() == atomX)
            {
                lks.add(links.get(i));
            }
            else if (links.get(i).getB() == atomX)
            {
                lks.add(links.get(i));
            }
        }
        
        if(j < 2)
        {
            cheat = false;
        }
        
        for(int i = 0; i < lks.size(); i++)
        {
            if(lks.get(i).getA() == centralAtom && cheat )
            {
                yes = true;
            }
            else if (lks.get(i).getB() == centralAtom && cheat )
            {
                yes = true;
            }
        }
        
        return yes;
    }
            
    private Atom atomsExclusiveBinders(Atom at, ArrayList<Atom> pasts)
    {
        Atom bind = null;
        for(int i = 0; i < links.size(); i++)
        {
            if( (at == links.get(i).getA()) && (isCentralAtom(links.get(i).getB())) && (!isVisited(links.get(i).getB())) && (!isPast(pasts, links.get(i).getB())))
            {
                bind = links.get(i).getB();
            }
            else if((at == links.get(i).getB()) && (isCentralAtom(links.get(i).getA())) && (!isVisited(links.get(i).getA())) && (!isPast(pasts, links.get(i).getA())))
            {
                bind = links.get(i).getA();
            }
        }
        
        return bind;
    }
    private boolean isPast(ArrayList<Atom> past, Atom atom)
    {
        boolean yes = false;
        
        for(int i = 0; i < past.size(); i++)
        {
            if(past.get(i) == atom)
            {
                yes = true;
                i = past.size() + 1;
            }
        }     
        
        return yes;
    }
    
    private boolean numberOfCentralAtomsBonded(ArrayList<Atom> binders)
    {
        boolean greaterThanOne = false;
        int size = binders.size();
        int count = 0;
        
        for(int i = 0; i < size; i++)
        {
            if( (isCentralAtom(binders.get(i))) && (!isVisited(binders.get(i))))
            {
                count ++;
                
                if(count > 1)
                {
                    greaterThanOne = true;
                    
                    i = size + 1;
                }
            }
        }
        
        return greaterThanOne;
    }
    
    private boolean isVisited(Atom atom)
    {
        boolean yes = false;
        int size = visited.size();
        
        for(int i = 0; i < size; i++)
        {
            if(atom == visited.get(i))
            {
                yes = true;
            }
        }
        
        return yes;
    }
    
    private void removeNextCentralAtoms(Atom atom)
    {
        int size = nextCentralAtom.size();
        int i = 0;
        
        while(i < size)
        {
            if(atom == nextCentralAtom.get(i))
            {
                nextCentralAtom.remove(i);
                i = size + 1;
            }
            i++;
        }
    }
    private void addNextCentralAtoms(ArrayList<Atom> atoms)
    {
        int size = atoms.size();
        for(int i = 0; i < size; i++)
        {            
            if( (isCentralAtom(atoms.get(i))) && (!isVisited(atoms.get(i))) )
            {
                nextCentralAtom.add(atoms.get(i));
            }
        }
    }
    
    public boolean isVisitedAtom(Atom atom)
    {
        boolean yes = false;
        
        int size = visited.size();
        
        for(int i = 0; i < size; i++)
        {
            if(atom == visited.get(i))
            {
                yes = true;
            }
        }
        
        return yes;
    }
    public boolean isCentralAtom(Atom atom)
    {
        boolean yes = false;
        
        int size = centralAtoms.size();
        int i = 0;
        
        while(i < size)
        {
            if(atom == centralAtoms.get(i))
            {
                yes = true;
                i = size + 1;
            }
            i++;
        }
        
        return yes;
    }
        
    private int selectFirstCentralAtom(ArrayList<Atom> centralAtoms)
    {
        int position = 0;
        
        int i = 0;
        int j ;
        int control;
        int size = centralAtoms.size();
        
        while( i < size )
        {
            j = 0;
            control = 0;
            while( j < size )
            {
                if(isLink(centralAtoms.get(i), centralAtoms.get(j)) && j != i)
                {
                    control ++;
                }
                j++;
            }
            
            if( control == 1 )
            {
                position = i;
                i =  size + 1;
            }
            i++;
        }
        
        return position;
    }
    private boolean isLink(Atom atom1, Atom atom2)
    {
        boolean yes = false;
        int i = 0;
        int size = links.size();
        
        while( i < size )
        {
            if( (links.get(i).getA() == atom1) && (links.get(i).getB() == atom2) )
            {
                yes = true;
                i = size + 1;
            }
            else if( (links.get(i).getA() == atom2) && (links.get(i).getB() == atom1) )
            {
                yes = true;
                i = size + 1;
            }
            i++;
        }
        
        return yes;
    }
    // Encontra os ligantes
    private ArrayList<Atom> linkers(Atom atom)
    {
        ArrayList<Atom> binders = new ArrayList<Atom>();
        ArrayList<Link> links = this.links;
        int size = links.size();
        
        for(int i = 0; i < size; i++)
        {
            if( atom == links.get(i).getA() )
            {
                binders.add(links.get(i).getB());
            }
            else if( atom == links.get(i).getB() )
            {
                binders.add(links.get(i).getA());
            }
        }
        
        return binders;
    }
    // Coloca as ligações entre os átomos
    private TransformGroup drawBonds(double[] coordsBond, String type)
    {
        Vector3f tempVec = new Vector3f();
        Vector3f crossVec = new Vector3f();
        Vector3f YAXIS = new Vector3f(0, 1, 0);
        Transform3D tempTrans = new Transform3D();
        Transform3D tempTrans2 = new Transform3D();
        AxisAngle4f tempAA = new AxisAngle4f();
        TransformGroup bondGroup = new TransformGroup();

        // coordsBond = (x1,y1,z1) (x2,y2,z2)
        float x, y, z;
        double dx, dy, dz, length;
               
        
        x = (float)(coordsBond[0] + coordsBond[3]);
        y = (float)(coordsBond[1] + coordsBond[4]);
        z = (float)(coordsBond[2] + coordsBond[5]);
        dx = (coordsBond[3] - coordsBond[0]);
        dy = (coordsBond[4] - coordsBond[1]);
        dz = (coordsBond[5] - coordsBond[2]);

        length = Math.sqrt(dx * dx + dy * dy + dz * dz);

        tempVec.set( (float)dx, (float)dy,(float) dz );
        
        tempVec.normalize();
        crossVec.cross(YAXIS, tempVec);

        tempAA.set(crossVec, (float)Math.acos(YAXIS.dot(tempVec)));
        tempTrans.set(tempAA);

        tempTrans2.setIdentity();
        tempTrans2.setTranslation(new Vector3f(x / 2, y / 2, z / 2));

        tempTrans2.mul(tempTrans);         

        Color color;
        String typeColor;
        float radius = 0;
        if(type.equals("simple"))
        {
            radius = 0.01f;
            typeColor = "white";
        }
        else if(type.equals("double"))
        {
            radius = 0.012f;
            typeColor = "yellow";
        }
        else
        {
            typeColor = "red";
            radius = 0.014f;
        }
        
        try 
        {
            Field field = Color.class.getField(typeColor);
            color = (Color)field.get(null);
        } 
        catch (Exception e) 
        {
            color = null; // Not defined
        }
        
        bondGroup = new TransformGroup(tempTrans2);          
        // Cria uma aparencia
        Appearance appearance = new Appearance();
        // Coloração na luz ambiente
        Color3f ambientColour = new Color3f(color); 
        // Coloração difusa
        Color3f diffuseColour = new Color3f(color);
        // Coloração especular 
        Color3f specularColour = new Color3f(color);
        // Coloração emissiva
        Color3f emissiveColour = new Color3f(0.0f, 0.0f, 0.0f);
        // Brilho
        float shininess = 20.0f;        
        // Adiciona os materiais
        appearance.setMaterial(new Material(ambientColour, emissiveColour,diffuseColour, specularColour, shininess));

        Color3f white = new Color3f(1, 1, 1);
        ColoringAttributes whiteColor = new ColoringAttributes(white, 1);
        appearance.setColoringAttributes(whiteColor);

        Cylinder bond = new Cylinder(radius, (float)length, 1, 300, 10, appearance);
        bondGroup.addChild(bond);
        
        return bondGroup;            
    }
        
    public void setRotation()
    {
        Alpha rotationAlpha = new Alpha(-1, 4000);

        RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, transformMolecule);
   
        BoundingSphere bounds = new BoundingSphere();
        rotator.setSchedulingBounds(bounds);
        transformMolecule.addChild(rotator);
    }
    
    private TransformGroup getStyleAtoms(Atom atom, Transform3D trans)
    {
        TransformGroup tg = new TransformGroup();
        
        // Cria uma aparencia
        Appearance appearance = new Appearance();
        // Cor
        Color color;
        try 
        {
            Field field = Color.class.getField(atom.getColor());
            color = (Color)field.get(null);
        } 
        catch (Exception e) 
        {
            color = null; // Not defined
        }
        
        // Coloração na luz ambiente
        Color3f ambientColour = new Color3f(color); 
        
        // Coloração difusa
        Color3f diffuseColour = new Color3f(color);
        
        // Coloração especular 
        Color3f specularColour = new Color3f(color);
        
        // Coloração emissiva
        Color3f emissiveColour = new Color3f(0.0f, 0.0f, 0.0f);
        
        // Brilho
        float shininess = 20.0f;      
        
        // Adiciona os materiais
        appearance.setMaterial(new Material(ambientColour, emissiveColour,diffuseColour, specularColour, shininess));        
        Color3f color3 = new Color3f(1, 1, 1);
        ColoringAttributes atColor = new ColoringAttributes(color3, 1);
        appearance.setColoringAttributes(atColor);
        
        /* Criação da Esfera */
        Sphere sphere = new Sphere(atom.getRadius(), Sphere.GENERATE_NORMALS, 300, appearance);
        
        tg.addChild(sphere);
        tg.setTransform(trans);
        
        return tg;
    }
    private boolean verificationAtoms()
    {
        int i = 0, size = atoms.size();
        boolean exit = false;
        
        
        
        while(i < size)
        {
            if(atoms.get(i).getPosition() == null ||
               atoms.get(i).getPosition().x == Double.NaN || 
               atoms.get(i).getPosition().y == Double.NaN ||
               atoms.get(i).getPosition().z == Double.NaN)
            {
                exit = true;
                i = size + 1;
            }
            i++;
        }
        
        if(exit)
        {
            owner.exit();
        }
        
        return exit;
    }
    
    private void centralize()
    {
        owner.getConstructionScreen().getDraw().calculateCentralAtom();
        int size = centralAtoms.size();
        double bigX = 0, bigY = 0, bigZ = 0;
        
        for(int i = 0; i < size; i++)
        {   
            for(int j = 0; j < size; j++)
            {        
                if( (Math.sqrt(Math.pow(centralAtoms.get(i).getPosition().x - centralAtoms.get(j).getPosition().x, 2))) > bigX)
                {
                    bigX = Math.sqrt(Math.pow(centralAtoms.get(i).getPosition().x - centralAtoms.get(j).getPosition().x, 2));
                }
                
                if( (Math.sqrt(Math.pow(centralAtoms.get(i).getPosition().y - centralAtoms.get(j).getPosition().y, 2))) > bigY)
                {
                    bigY = Math.sqrt(Math.pow(centralAtoms.get(i).getPosition().y - centralAtoms.get(j).getPosition().y, 2));
                }
                
                if( (Math.sqrt(Math.pow(centralAtoms.get(i).getPosition().z - centralAtoms.get(j).getPosition().z, 2))) > bigZ)
                {
                    bigZ = Math.sqrt(Math.pow(centralAtoms.get(i).getPosition().z - centralAtoms.get(j).getPosition().z, 2));
                }
            }
        }
        
        double deslocationX = bigX / 2;
        double deslocationY = bigY / 2;
        double deslocationZ = bigZ / 2;
        
        for(int i = 0; i < atoms.size(); i++)
        {
            atoms.get(i).getPosition().x -= deslocationX;
            atoms.get(i).getPosition().y -= deslocationY;
            atoms.get(i).getPosition().z -= deslocationZ;
        }
    }
    
    public void addGeometry(String geometry)
    {
        geometrys.add(geometry);
    }
    
    public ArrayList<String> getGeometry()
    {
        return geometrys;
    }
    
    public void removeChildren()
    {
        group.detach();
        transformMolecule.removeAllChildren();
    }
}
