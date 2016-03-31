/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Gabriel
 */
public class Cation extends JFrame
{
    private PreviewScreen owner;
    
    private GridLayout layout;
    private JTextField colorHydrogen;
    private JTextField colorCarbon;
    private JTextField colorOxygen;
    private JTextField colorNitrogen;
    private JTextField colorFluorine;
    private JTextField colorBromine;
    private JTextField colorChlorine;
    private JTextField colorIodine;
    private JTextField colorPhosphorus;
    private JTextField colorSulfur;
    private JTextField colorSimpleLink;
    private JTextField colorDoubleLink;
    private JTextField colorTripleLink;
    public Cation(PreviewScreen owner)
    {
        this.owner = owner;
        startScreen();
    }
    
    private void startScreen()
    {
        lookAndFeel();
        layout = new GridLayout(14, 2, 2, 2);
        colorHydrogen = new JTextField();
        colorCarbon =  new JTextField();
        colorOxygen =  new JTextField();
        colorNitrogen =  new JTextField();
        colorFluorine =  new JTextField();
        colorBromine =  new JTextField();
        colorChlorine =  new JTextField();
        colorIodine =  new JTextField();
        colorPhosphorus =  new JTextField();
        colorSulfur =  new JTextField();
        colorSimpleLink = new JTextField();
        colorDoubleLink = new JTextField();
        colorTripleLink = new JTextField();
        
        JLabel hydrogen = new JLabel("Hidrogênio");
        JLabel carbon = new JLabel("Carbono");
        JLabel oxygen = new JLabel("Oxigênio");
        JLabel nitrogen = new JLabel("Nitrogênio");
        JLabel fluorine = new JLabel("Fluor");
        JLabel bromine = new JLabel("Bromo");
        JLabel chlorine = new JLabel("Cloro");
        JLabel iodine = new JLabel("Iodo");
        JLabel phosphorus = new JLabel("Fósforo");
        JLabel sulfur = new JLabel("Enxofre");
        JLabel simpleLink = new JLabel("Ligação Simples");
        JLabel doubleLink = new JLabel("Ligação Dupla");
        JLabel tripleLink = new JLabel("Ligação Tripla");
        JTextField nu = new JTextField();
        JTextField nus = new JTextField();
        
        this.setTitle("Legenda");
        this.setIconImage(new ImageIcon(getClass().getResource("/Images/icon_screen.png")).getImage());
        this.setSize(250, 350);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(layout);
        this.setResizable(false);
        this.addWindowListener(new java.awt.event.WindowAdapter() 
        {          
            public void windowClosing(java.awt.event.WindowEvent e) 
            {                  
                owner.setControl(false);
            }
        });
        
        colorHydrogen.setBackground(Color.WHITE);
        colorHydrogen.setForeground(Color.WHITE);
        colorHydrogen.setEditable(false);   
        
        colorCarbon.setBackground(Color.GRAY);
        colorCarbon.setForeground(Color.WHITE);
        colorCarbon.setEditable(false);   
        
        colorOxygen.setBackground(Color.RED);
        colorOxygen.setForeground(Color.WHITE);
        colorOxygen.setEditable(false);   
        
        colorNitrogen.setBackground(Color.BLUE);
        colorNitrogen.setForeground(Color.WHITE);
        colorNitrogen.setEditable(false);   
        
        colorFluorine.setBackground(Color.GREEN);
        colorFluorine.setForeground(Color.WHITE);
        colorFluorine.setEditable(false);   
        
        colorBromine.setBackground(Color.MAGENTA);
        colorBromine.setForeground(Color.WHITE);
        colorBromine.setEditable(false);   
        
        colorChlorine.setBackground(Color.CYAN);
        colorChlorine.setForeground(Color.WHITE);
        colorChlorine.setEditable(false);   
        
        colorIodine.setBackground(Color.PINK);
        colorIodine.setForeground(Color.WHITE);
        colorIodine.setEditable(false);   
        
        colorPhosphorus.setBackground(Color.ORANGE);
        colorPhosphorus.setForeground(Color.WHITE);
        colorPhosphorus.setEditable(false);   
        
        colorSulfur.setBackground(Color.YELLOW);
        colorSulfur.setForeground(Color.WHITE);
        colorSulfur.setEditable(false);   
        
        colorSimpleLink.setBackground(Color.WHITE);
        colorSimpleLink.setForeground(Color.WHITE);
        colorSimpleLink.setEditable(false);
        
        colorDoubleLink.setBackground(Color.YELLOW);
        colorDoubleLink.setForeground(Color.WHITE);
        colorDoubleLink.setEditable(false);
        
        colorTripleLink.setBackground(Color.RED);
        colorTripleLink.setForeground(Color.WHITE);
        colorTripleLink.setEditable(false);
        
        nu.setVisible(false);
        nus.setVisible(false);
        
        hydrogen.setBackground(Color.WHITE);
        hydrogen.setHorizontalAlignment(JTextField.CENTER);
        hydrogen.setFont(new java.awt.Font("Arial",  1, 12));
        
        carbon.setBackground(Color.WHITE);
        carbon.setHorizontalAlignment(JTextField.CENTER);
        carbon.setFont(new java.awt.Font("Arial",  1, 12));
        // OXIGÊNIO
        oxygen.setBackground(Color.WHITE);
        oxygen.setHorizontalAlignment(JTextField.CENTER);
        oxygen.setFont(new java.awt.Font("Arial",  1, 12));
        // Nitrogênio
        nitrogen.setBackground(Color.WHITE);
        nitrogen.setHorizontalAlignment(JTextField.CENTER);
        nitrogen.setFont(new java.awt.Font("Arial", 1, 12));
        // FLUOR
        fluorine.setBackground(Color.WHITE);
        fluorine.setHorizontalAlignment(JTextField.CENTER);
        fluorine.setFont(new java.awt.Font("Arial",  1, 12));
        // FLUOR
        bromine.setBackground(Color.WHITE);
        bromine.setHorizontalAlignment(JTextField.CENTER);
        bromine.setFont(new java.awt.Font("Arial",  1, 12));
        // FLUOR
        chlorine.setBackground(Color.WHITE);
        chlorine.setHorizontalAlignment(JTextField.CENTER);
        chlorine.setFont(new java.awt.Font("Arial",  1, 12));
        // FLUOR
        iodine.setBackground(Color.WHITE);
        iodine.setHorizontalAlignment(JTextField.CENTER);
        iodine.setFont(new java.awt.Font("Arial",  1, 12));
        // FLUOR
        phosphorus.setBackground(Color.WHITE);
        phosphorus.setHorizontalAlignment(JTextField.CENTER);
        phosphorus.setFont(new java.awt.Font("Arial",  1, 12));
        // FLUOR
        sulfur.setBackground(Color.WHITE);
        sulfur.setHorizontalAlignment(JTextField.CENTER);
        sulfur.setFont(new java.awt.Font("Arial",  1, 12));
        
        simpleLink.setBackground(Color.WHITE);
        simpleLink.setHorizontalAlignment(JTextField.CENTER);
        simpleLink.setFont(new java.awt.Font("Arial",  1, 12));
        
        doubleLink.setBackground(Color.WHITE);
        doubleLink.setHorizontalAlignment(JTextField.CENTER);
        doubleLink.setFont(new java.awt.Font("Arial",  1, 12));
       
        tripleLink.setBackground(Color.WHITE);
        tripleLink.setHorizontalAlignment(JTextField.CENTER);
        tripleLink.setFont(new java.awt.Font("Arial",  1, 12));
        
        this.add(hydrogen);
        this.add(colorHydrogen);
        this.add(carbon);
        this.add(colorCarbon);
        this.add(oxygen);
        this.add(colorOxygen);
        this.add(nitrogen);
        this.add(colorNitrogen);
        this.add(fluorine);
        this.add(colorFluorine);
        this.add(bromine);
        this.add(colorBromine);
        this.add(chlorine);
        this.add(colorChlorine);
        this.add(iodine);
        this.add(colorIodine);
        this.add(phosphorus);
        this.add(colorPhosphorus);
        this.add(sulfur);
        this.add(colorSulfur);
        this.add(nu);
        this.add(nus);
        this.add(simpleLink);
        this.add(colorSimpleLink);
        this.add(doubleLink);
        this.add(colorDoubleLink);
        this.add(tripleLink);
        this.add(colorTripleLink);
        this.setVisible(true);
    }
    
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
}
