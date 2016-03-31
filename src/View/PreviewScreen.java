/**
 *
 * @author Gabriel Tomio Nardes
 */
package View;

import Controller.Archives;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.GraphicsContext3D;
import javax.media.j3d.ImageComponent;
import javax.media.j3d.ImageComponent2D;
import javax.media.j3d.Raster;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.vecmath.Point3f;

public class PreviewScreen extends JFrame implements ActionListener
{
    private BorderLayout layout;
    
    private Preview preview;
    private JToolBar interactions;
    private JButton legend;
    private JButton save;
    private Canvas3D canvas;
    private Cation  cation;
    private JButton geometrys;
    private ConstructionScreen owner;
    private boolean control = false;
    
    public PreviewScreen(ConstructionScreen owner)
    {
        this.owner = owner;
        startScreen();        
    }
    
    public void startScreen()
    {
        /* Look and Feel */
        lookAndFeel();
        
        /* Construção dos objetos */
        preview = new Preview(this);
        layout = new BorderLayout();
        interactions = new JToolBar();
        legend = new JButton();
        geometrys = new JButton();
        save = new JButton();
        canvas = preview.getCanvas3D();
                     
        /* Padrão de construção da janela */
        this.setTitle("Arquiteto Molecular");
        this.setSize(300,300);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
        this.setLayout(layout);
        this.setIconImage(new ImageIcon(getClass().getResource("/Images/icon_screen.png")).getImage());
        this.addWindowListener(new java.awt.event.WindowAdapter() 
        {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) 
            {
                if(control == true)
                {
                    cation.removeAll();
                }
                preview.removeChildren();
                owner.setControl(false);
            }
        });
        
        /* Personalização dos componentes */
        
        /* INTERAÇÕES */
        interactions.setBackground(Color.WHITE);
        interactions.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        interactions.setOrientation(SwingConstants.HORIZONTAL);
        interactions.setFloatable(false);
        
        /* LEGENDA */
        legend.setBackground(Color.WHITE);
        legend.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/legend.png")));
        legend.addActionListener(this);
        
        /* GEOMETRIAS */
        geometrys.setBackground(Color.WHITE);
        geometrys.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/geometry.png")));
        geometrys.addActionListener(this);
        
        /* SALVAR */
        save.setBackground(Color.WHITE);
        save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save.png")));
        save.addActionListener(this);
        
        /* Adicionamento de Componentes na Janela */
        this.add(canvas, BorderLayout.CENTER);
        this.add(interactions, BorderLayout.NORTH);
        
        /* Adcionando botões na barra de ferramentas de ligações */
        interactions.add(geometrys);
        interactions.add(legend);
        interactions.add(save);
        
        /* Tornando a Janela visivel */
        this.setVisible(true);       
    }

    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == legend)
        {
            if(control == false)
            {
                cation = new Cation(this);
                control = true;
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Já existe um janela legenda aberta");
            }
        }
        else if(e.getSource() == geometrys)
        {
            ArrayList<String> geo = preview.getGeometry();
            String pri = "";
            for(int i = 0; i < geo.size(); i++)
            {
                pri += (i + 1) + " - " + geo.get(i) + "\n";
            }
            
            JOptionPane.showMessageDialog(this, "Geometrias que a molécula construída possui: \n\n" + pri, "Geometria Molecular", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getResource("/Icons/geometry.png")));
        }
        else if(e.getSource() == save)
        {
            GraphicsContext3D ctx = canvas.getGraphicsContext3D();
            int w = canvas.getWidth();
            int h = canvas.getHeight();
            
            BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            
            ImageComponent2D im = new ImageComponent2D(ImageComponent.FORMAT_RGB, bi);
            
            Raster ras = new Raster(new Point3f( -1.0f, -1.0f, -1.0f ),
                            Raster.RASTER_COLOR, 0, 0, w, h, im, null );
            
            ctx.flush(true);

            ctx.readRaster( ras );
            
            BufferedImage image = ras.getImage().getImage();
            
            Archives arc = new Archives(owner);
            arc.saveImage3D(image);
        }
    }
    
    public void setControl(boolean control)
    {
        this.control = control;
    }
    
    /* Look and Feel */
    public void lookAndFeel()
    {
        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();  
        try 
        { 
            UIManager.setLookAndFeel(lookAndFeel);
        } 
        catch (ClassNotFoundException ex) 
        {
            Logger.getLogger(ConstructionScreen.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (InstantiationException ex) 
        {
            Logger.getLogger(ConstructionScreen.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IllegalAccessException ex) 
        {
            Logger.getLogger(ConstructionScreen.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (UnsupportedLookAndFeelException ex) 
        {
            Logger.getLogger(ConstructionScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void exit()
    {
        
        if(control == true)
        {
            cation.removeAll();
        }
        owner.setControl(false);
        
        JOptionPane.showMessageDialog(null, "Não foi possível calcular a geometria desta molécula!");
        
        this.dispose();
    }
    public ConstructionScreen getConstructionScreen()
    {
        return owner;
    }
}
