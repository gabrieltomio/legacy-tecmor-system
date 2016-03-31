/**
 *
 * @author Gabriel Tomio Nardes
 */

package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;


public class SplashScreen extends JWindow
{
    private int duration;
    
    public SplashScreen(int d) 
    {
        duration = d;
    }
    
    // Este é um método simples para mostrar uma tela de apresentção

    // no centro da tela durante a quantidade de tempo passada no construtor

    public void showSplash() 
    {
        JPanel content = (JPanel)getContentPane();
        content.setBackground(new Color(0,0,0,0));  
        
        // Configura a posição e o tamanho da janela
        int width = 612;
        int height = 612;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width-width)/2;
        int y = (screen.height-height)/2;
        setBounds(x,y,width,height);
        setBackground(new Color(0, 0, 0, 0));
        // Constrói o splash screen
        JLabel label = new JLabel(new javax.swing.ImageIcon(getClass().getResource("/Images/icon.png")));
        content.add(label, BorderLayout.CENTER);
                
        //Color color = new Color(0, 0, 0,  0);
        //content.setBorder(BorderFactory.createLineBorder(color, 5));        
        // Torna visível
        setVisible(true);
        
        // Espera ate que os recursos estejam carregados
        try 
        { 
            Thread.sleep(duration); 
        } 
        catch (Exception e) 
        {}        
        this.setVisible(false);
    }
    
    public void showSplashAndExit() {        
        showSplash();
        ConstructionScreen c = new ConstructionScreen();
        //System.exit(0);
        
    }    
}
