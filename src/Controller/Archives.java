/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Controller;


import Model.Molecule;
import View.ConstructionScreen;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Gabriel Tomio Nardes
 */
public class Archives 
{
    private ConstructionScreen owner;
    
    public Archives(ConstructionScreen owner)
    {
        this.owner = owner;
    }
    
    public void saveProjectFile(Molecule molecule)
    {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de Arquitetura Moleculer (.arqm)", "arqm");
        String baseDirectory = System.getProperty("user.home")+"Desktop";
        File dir = new File(baseDirectory);
        
        JFileChooser choose = new JFileChooser();
        choose.setCurrentDirectory(dir);
        choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
        choose.setAcceptAllFileFilterUsed(false);
        choose.addChoosableFileFilter(filter);
        
        int ret = choose.showSaveDialog(null);
        
        if(ret == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                FileOutputStream arc = new FileOutputStream(choose.getSelectedFile().getAbsolutePath() + ".arqm");
                 
                ObjectOutputStream writer = new ObjectOutputStream(arc);
                
                writer.writeObject(molecule);
                
                writer.close();
                arc.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public void openProjectFile(Molecule molecule)
    {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de Arquitetura Moleculer (.arqm)", "arqm");
        String baseDirectory = System.getProperty("user.home")+"Desktop";
        File dir = new File(baseDirectory);
        
        JFileChooser choose = new JFileChooser();
        choose.setCurrentDirectory(dir);
        choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
        choose.setAcceptAllFileFilterUsed(false);
        choose.addChoosableFileFilter(filter);
        
        int ret = choose.showOpenDialog(null);
        
        if(ret == JFileChooser.APPROVE_OPTION)
        {
            try
            {
                FileInputStream arc = new FileInputStream(choose.getSelectedFile().getAbsolutePath());
                ObjectInputStream reader = new ObjectInputStream(arc);
                
                molecule = (Molecule) reader.readObject();
                owner.getMolecule().setAtoms(molecule.getAtoms());
                owner.getMolecule().setLinks(molecule.getLinks());
                
                reader.close();
                arc.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            
        }
    }
    
    public void saveImage(Component c)
    {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo JPG (.jpg)", "jpg");
        String baseDirectory = System.getProperty("user.home")+"Desktop";
        File dir = new File(baseDirectory);
        
        JFileChooser choose = new JFileChooser();
        choose.setCurrentDirectory(dir);
        choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
        choose.setAcceptAllFileFilterUsed(false);
        choose.addChoosableFileFilter(filter);

        int ret = choose.showSaveDialog(null);
        
        if(ret == JFileChooser.APPROVE_OPTION)
        {
            int width = c.getWidth();  
            int height = c.getHeight();  
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  

            // obter contexto gráfico da imagem  
            Graphics graphics = image.getGraphics();  

            // desenhar o componente no contexto grafico  
            c.paintAll(graphics);  
            graphics.dispose();  

            try 
            {
                // salvar imagem
                ImageIO.write(image, "jpg" , new FileOutputStream(choose.getSelectedFile().getAbsolutePath()+ ".jpg") );
            } 
            catch (IOException ex)
            {
                Logger.getLogger(Archives.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public void saveImage3D(BufferedImage image)
    {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivo JPG (.jpg)", "jpg");
        String baseDirectory = System.getProperty("user.home")+"Desktop";
        File dir = new File(baseDirectory);
        
        JFileChooser choose = new JFileChooser();
        choose.setCurrentDirectory(dir);
        choose.setFileSelectionMode(JFileChooser.FILES_ONLY);
        choose.setAcceptAllFileFilterUsed(false);
        choose.addChoosableFileFilter(filter);

        int ret = choose.showSaveDialog(null);
        
        if(ret == JFileChooser.APPROVE_OPTION)
        {
            try 
            {
                // salvar imagem
                ImageIO.write(image, "jpg" , new FileOutputStream(choose.getSelectedFile().getAbsolutePath()+ ".jpg") );
            } 
            catch (IOException ex)
            {
                Logger.getLogger(Archives.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
