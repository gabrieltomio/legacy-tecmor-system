
/**
 *
 * @author Gabriel Tomio Nardes
 */
package Controller;

import View.SplashScreen;

public class General 
{    
    public static void main (String args [])
    {        
        // Mostra uma imagem com o título da aplicação 
        SplashScreen splash = new SplashScreen(1000);
        splash.showSplashAndExit();  
    }
}
