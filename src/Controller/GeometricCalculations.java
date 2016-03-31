
package Controller;

import Model.Atom;
import View.Preview;
import java.util.ArrayList;
import javax.vecmath.Vector3d;

public class GeometricCalculations 
{
    Preview owner;
    
    public GeometricCalculations(Preview owner)
    {
        this.owner = owner;
    }
    
    public void advancedMolecularGeometry(Atom firstBinder, Atom centralAtom, Atom reference, ArrayList<Atom> binders, ArrayList<Vector3d> positions, boolean circle)
    {
        Vector3d vectors [] = VectorsCalculations(centralAtom.getPosition(), firstBinder.getPosition());
        
        Vector3d vectorX = vectors[0];
        Vector3d vectorY = vectors[1];
        Vector3d vectorZ = vectors[2];
        int numberOfCentralAtoms = 0;
        if(circle)
        {
                    
        }
        else
        {
            // Quantidade de átomos centrais ligantes
            
            for(int i = 0; i < binders.size(); i++)
            {
                if(owner.isCentralAtom(binders.get(i)))
                {
                    numberOfCentralAtoms++;
                }
            }
                
            // Posionamentos 
            if( (numberOfCentralAtoms == 0) || (numberOfCentralAtoms == binders.size()))
            {
                for(int i = 0; i < binders.size(); i++)
                {
                    binders.get(i).setPosition( transformCoordinate( vectorX, vectorY, vectorZ, positions.get(i), centralAtom.getPosition()));
                    
                }                    
            }
            else 
            {
                double bigVector;
                int position = 0;
                Vector3d vector;
                Vector3d vectorFar = null;
                
                for(int i = 0; i < binders.size(); i++)
                {
                    if(owner.isCentralAtom(binders.get(i)))
                    {
                        bigVector = 0;
                        for(int j = 0; j < positions.size(); j++)
                        {                            
                            vector = transformCoordinate(vectorX, vectorY, vectorZ, positions.get(j), centralAtom.getPosition());
                            
                            if( distance(vector, reference.getPosition()) > bigVector)
                            {
                                bigVector = distance(vector, reference.getPosition());
                                vectorFar = vector;
                                position = j;
                            }
                        }
                        
                        positions.remove(position);
                        binders.get(i).setPosition(vectorFar);
                    }
                }
                
                for(int i = 0; i < binders.size(); i++)
                {
                    for(int j = 0; j < positions.size(); j++)
                    {
                        if(!owner.isCentralAtom(binders.get(i)))
                        {
                            binders.get(i).setPosition( transformCoordinate(vectorX, vectorY, vectorZ, positions.get(j), centralAtom.getPosition()));
                            positions.remove(j);
                        }
                    }           
                }                
            }
        }
    }
    private Vector3d transformCoordinate(Vector3d vectX, Vector3d vectY, Vector3d vectZ, Vector3d vectL, Vector3d vectO)
    {
        // Vector da posição real do átomo
        Vector3d natural = new Vector3d();
       
        // Cálcula-se então a posição do átomo ligante em relação ao central
        double qX = vectL.x * vectX.x + vectL.y * vectY.x + vectL.z * vectZ.x;
        double qY = vectL.x * vectX.y + vectL.y * vectY.y + vectL.z * vectZ.y;
        double qZ = vectL.x * vectX.z + vectL.y * vectY.z + vectL.z * vectZ.z;
        
        double pX = qX + vectO.x;
        double pY = qY + vectO.y;
        double pZ = qZ + vectO.z;
        
        // Posições do átomo ligante
        natural.x = pX;
        natural.y = pY;
        natural.z = pZ;
        
        return natural;
    }
    
    private Vector3d[] VectorsCalculations(Vector3d vectO, Vector3d vectQ)
    {         
        Vector3d [] vectors = new Vector3d[3];
        
        // Vetor Y
        Vector3d vectY = new Vector3d(vectQ.x - vectO.x, vectQ.y - vectO.y, vectQ.z - vectO.z);
        // Vetor X
        Vector3d vectX = new Vector3d(vectY.y, - vectY.x, 0);
        
        // Caso o vetor y for perpendicular ao plano XY
        if( (vectX.x == 0) && (vectX.y == 0))
        {
            vectX.x = vectY.z;
            vectX.y = 0;
            vectX.z = 0;
        }        
        
        // Vetor Z
        Vector3d vectZ = new Vector3d();
        vectZ.x = vectX.y * vectY.z - vectX.z * vectY.y;
        vectZ.y = vectX.z * vectY.y - vectX.x * vectY.z;
        vectZ.z = vectX.x * vectY.y - vectX.y * vectY.x;
        
        // Normas dos vetores Y, X e Z     
        double standardY =  Math.sqrt(Math.pow(vectY.x, 2) + Math.pow(vectY.y, 2) + Math.pow(vectY.z, 2));
        double standardX =  Math.sqrt(Math.pow(vectX.x, 2) + Math.pow(vectX.y, 2) + Math.pow(vectX.z, 2));
        double standardZ =  Math.sqrt(Math.pow(vectZ.x, 2) + Math.pow(vectZ.y, 2) + Math.pow(vectZ.z, 2));
        
        // Normalização dos vetores, respectivamente, Y, X e Z
        
        vectY.x = vectY.x / standardY;
        vectY.y = vectY.y / standardY;
        vectY.z = vectY.z / standardY;
        
        vectX.y = vectX.y / standardX;
        vectX.x = vectX.x / standardX;
        vectX.z = vectX.z / standardX;
        
        vectZ.x = vectZ.x / standardZ;
        vectZ.y = vectZ.y / standardZ;
        vectZ.z = vectZ.z / standardZ;
        
        vectors[0] = vectX;
        vectors[1] = vectY;
        vectors[2] = vectZ;
        
        return vectors;
    }
    
    public double distance(Vector3d vect1, Vector3d vect2)
    {
        double distance = Math.sqrt( 
                          Math.pow((vect2.x - vect1.x), 2) + 
                          Math.pow((vect2.y - vect1.y), 2) +
                          Math.pow((vect2.z - vect1.z), 2));
        
        return distance;
    }
    
}
