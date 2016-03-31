/**
 *
 * @author Gabriel Tomio Nardes
 */

package View;

// Imports
import Controller.Archives;
import Controller.Rules;
import Model.Atom;
import Model.Link;
import Model.Molecule;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class ConstructionScreen extends JFrame implements ActionListener
{
    /* Layout */
    private BorderLayout layout;
    private GridLayout layoutProperties;
    
    private JScrollPane scroll;
    /* Criação dos componentes */
    /* PROPRIEDADES */
    private JTextField symbols;
    private JTextField numberAtoms;
    private JTextField numberLinks;
    private JTextField validate;
    private JTextField molecularWeight;
    private JTextField polarity;
    
    /* VISÕES */
    private JToolBar visions;
    private JButton vision3D;
    
    private JToolBar links;
    private JButton selection;
    private JButton delete;
    private JButton simpleLink;
    private JButton doubleLink;
    private JButton tripleLink;
        
    private JToolBar atoms;
    private JButton hydrogen;
    private JButton carbon;
    private JButton oxygen;
    private JButton nitrogen;
    private JButton fluorine;
    private JButton bromine;
    private JButton chlorine;
    private JButton iodine;
    private JButton phosphorus;
    private JButton sulfur;
    
    private JPanel properties;
    
    private JMenuBar menuBar;
    private JMenu menuArchive;
    private JMenu menuEdition;
    private JMenu menuExibition;
    private JMenuItem newProject;
    private JMenuItem openProject;
    private JMenuItem saveProject;
    private JMenuItem export;
    private JMenuItem close;
    private JMenuItem deleteAll;
    private JMenuItem deleteLinks;
    private JMenuItem view3D;
    
    /* Sistema */
    private boolean control;
    private int selectedTool;
    private Rules rules;
    private Molecule molecule;
    private Draw drawArea;
    private PreviewScreen ps;
    private Archives archives;
    
    public ConstructionScreen ()
    {        
        control = false;
        molecule = new Molecule();
        rules = new Rules();
        archives = new Archives(this);
        startScreen();
        selectedTool = 13;        
    }
    
    public void startScreen()
    {
        //
        /* Lok and Feel*/
        lookAndFeel();
        
        /* Contrução dos objetos */
        layout = new BorderLayout(5, 5);  
        layoutProperties = new GridLayout(2, 3, 1, 1);
        drawArea = new Draw(this);
        scroll = new JScrollPane(drawArea);
        visions = new JToolBar();
        links = new JToolBar();
        simpleLink = new JButton();
        doubleLink = new JButton();
        tripleLink = new JButton();
        atoms = new JToolBar();
        properties = new JPanel();
        selection = new JButton();
        delete = new JButton();
        
        hydrogen = new JButton("H");
        carbon = new JButton("C");
        oxygen = new JButton("O");
        nitrogen = new JButton("N");
        fluorine = new JButton("F");
        bromine = new JButton("Br");
        chlorine = new JButton("Cl");
        iodine = new JButton("I");
        phosphorus = new JButton("P");
        sulfur = new JButton("S");
        
        vision3D = new JButton();
        symbols = new JTextField("Fórmula molecular: - ");
        validate = new JTextField("Inválida");
        numberAtoms = new JTextField("Número de átomos: 0");
        numberLinks = new JTextField("Número de ligações: 0");
        molecularWeight = new JTextField("Massa molar: 0 g/mol");
        polarity = new JTextField("Polaridade: - ");
        
        menuBar = new JMenuBar();
        menuArchive = new JMenu("Arquivo");
        menuEdition = new JMenu("Editar");
        menuExibition = new JMenu("Exibir");
        deleteAll = new JMenuItem("Deletar Molécula");
        deleteLinks = new JMenuItem("Deletar Ligações");
        newProject = new JMenuItem("Novo Projeto");
        openProject = new JMenuItem("Abrir Projeto");
        saveProject = new JMenuItem("Salvar Projeto");
        export = new JMenuItem("Salvar Imagem");
        view3D = new JMenuItem("Gerar Molécula em 3D");
        
        /* Padrão de construção da Janela */
        this.setTitle("Arquiteto Molecular");
        this.setIconImage(new ImageIcon(getClass().getResource("/Images/icon_screen.png")).getImage());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setSize(300,300);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setLayout(layout);
        
        this.add(scroll);
        this.addWindowListener(new java.awt.event.WindowAdapter() 
        {          
            public void windowClosing(java.awt.event.WindowEvent e) 
            {                  
                int change;
                change = JOptionPane.showConfirmDialog(null, "Você deseja salvar o projeto antes de sair?", "Saida",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/Images/icon_screen.png")));
                
                if(change == JOptionPane.YES_OPTION)
                {
                    archives.saveProjectFile(molecule);
                    System.exit(0);
                }   
                else if(change == JOptionPane.NO_OPTION)
                {
                    System.exit(0);
                }
            }
        });
        
        /* Pesonalização de componentes */ 
        // ÁREA DE DESENHO
        drawArea.setBackground(Color.WHITE);
        
        // Pega tamanho da tela
        Toolkit toolkit = Toolkit.getDefaultToolkit();  
        Dimension screenSize = toolkit.getScreenSize();  
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();
        
        drawArea.setPreferredSize(new Dimension(screenWidth, screenHeight));  
        // VISÕES
        visions.setBackground(Color.WHITE);
        //visions.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        visions.setFloatable(false);
        // VISÃO 3D
        vision3D.setBackground(Color.WHITE);
        vision3D.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/view_mol.png")));
        vision3D.addActionListener(this);
        // LIGAÇÕES
        links.setBackground(Color.WHITE);
        links.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        links.setOrientation(SwingConstants.VERTICAL);
        links.setFloatable(false);
        // ÁTOMOS
        atoms.setBackground(Color.WHITE);
        atoms.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        atoms.setOrientation(SwingConstants.VERTICAL);
        atoms.setFloatable(false);
        // PROPRIEDADES
        properties.setBackground(Color.BLACK);
        properties.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        properties.setLayout(layoutProperties);
        // SELEÇÃO
        selection.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/selection.png")));
        selection.setBackground(Color.WHITE);
        selection.addActionListener(this);
        // LIGAÇÃO SIMPLES
        simpleLink.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/simple.png")));
        simpleLink.setBackground(Color.WHITE);
        simpleLink.addActionListener(this);
        // LIGAÇÃO DUPLA
        doubleLink.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/double.png")));
        doubleLink.setBackground(Color.WHITE);
        doubleLink.addActionListener(this);
        // LIGAÇÃO TRIPLA
        tripleLink.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/triple.png")));
        tripleLink.setBackground(Color.WHITE);
        tripleLink.addActionListener(this);
        // EXCLUSÃO
        delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/delete.png")));
        delete.setBackground(Color.WHITE);
        delete.addActionListener(this);
        // HIDROGÊNIO
        hydrogen.addActionListener(this);
        hydrogen.setBackground(Color.WHITE);
        hydrogen.setFont(new java.awt.Font("Arial", 0, 30));
        // CARBONO 
        carbon.addActionListener(this);
        carbon.setBackground(Color.WHITE);
        carbon.setFont(new java.awt.Font("Arial",  0, 30));
        // OXIGÊNIO
        oxygen.addActionListener(this);
        oxygen.setBackground(Color.WHITE);
        oxygen.setFont(new java.awt.Font("Arial",  0, 30));
        // Nitrogênio
        nitrogen.addActionListener(this);
        nitrogen.setBackground(Color.WHITE);
        nitrogen.setFont(new java.awt.Font("Arial", 0, 30));
        // FLUOR
        fluorine.addActionListener(this);
        fluorine.setBackground(Color.WHITE);
        fluorine.setFont(new java.awt.Font("Arial",  0, 30));
        // FLUOR
        bromine.addActionListener(this);
        bromine.setBackground(Color.WHITE);
        bromine.setFont(new java.awt.Font("Arial",  0, 30));
        // FLUOR
        chlorine.addActionListener(this);
        chlorine.setBackground(Color.WHITE);
        chlorine.setFont(new java.awt.Font("Arial",  0, 30));
        // FLUOR
        iodine.addActionListener(this);
        iodine.setBackground(Color.WHITE);
        iodine.setFont(new java.awt.Font("Arial",  0, 30));
        // FLUOR
        phosphorus.addActionListener(this);
        phosphorus.setBackground(Color.WHITE);
        phosphorus.setFont(new java.awt.Font("Arial",  0, 30));
        // FLUOR
        sulfur.addActionListener(this);
        sulfur.setBackground(Color.WHITE);
        sulfur.setFont(new java.awt.Font("Arial",  0, 30));
        // SIMBOLOS
        symbols.setBorder(null);
        symbols.setBackground(Color.WHITE);
        symbols.setHorizontalAlignment(JTextField.CENTER);
        symbols.setFont(new java.awt.Font("Arial", 1, 14));
        symbols.setEditable(false);
        // VALIDADE
        validate.setBorder(null);
        validate.setBackground(Color.RED);
        validate.setHorizontalAlignment(JTextField.CENTER);
        validate.setFont(new java.awt.Font("Arial", 1, 16));
        validate.setForeground(Color.WHITE);
        validate.setEditable(false);
        
        // NÚMERO DE ÁTOMOS
        numberAtoms.setBorder(null);
        numberAtoms.setBackground(Color.WHITE);
        numberAtoms.setHorizontalAlignment(JTextField.CENTER);
        numberAtoms.setFont(new java.awt.Font("Arial", 1, 14));
        numberAtoms.setEditable(false);
        
        // NÚMERO DE LIGAÇÕES
        numberLinks.setBorder(null);
        numberLinks.setBackground(Color.WHITE);
        numberLinks.setHorizontalAlignment(JTextField.CENTER);
        numberLinks.setFont(new java.awt.Font("Arial", 1, 14));
        numberLinks.setEditable(false);
        
        // MASSA MOLECULAR
        molecularWeight.setBorder(null);
        molecularWeight.setBackground(Color.WHITE);
        molecularWeight.setHorizontalAlignment(JTextField.CENTER);
        molecularWeight.setFont(new java.awt.Font("Arial", 1, 14));
        molecularWeight.setEditable(false);
        
         // POLARIDADE
        polarity.setBorder(null);
        polarity.setBackground(Color.WHITE);
        polarity.setHorizontalAlignment(JTextField.CENTER);
        polarity.setFont(new java.awt.Font("Arial", 1, 14));
        polarity.setEditable(false);
        
        /* Adicionando localização das propriedades */
        properties.add(symbols);
        properties.add(numberAtoms);
        properties.add(molecularWeight);        
        properties.add(validate);
        properties.add(numberLinks);        
        properties.add(polarity);
        
        scroll.setViewportView(drawArea);
        /* Menu */
        menuBar.add(menuArchive);
        menuBar.add(menuEdition);
        menuBar.add(menuExibition);
        
        //Arquivo
        menuArchive.add(newProject);
        newProject.addActionListener(this);
        menuArchive.add(openProject);
        openProject.addActionListener(this);
        menuArchive.add(saveProject);
        saveProject.addActionListener(this);
        menuArchive.add(export);
        export.addActionListener(this);
        
        // Deletar todos os átomos
        menuEdition.add(deleteAll);
        deleteAll.addActionListener(this);
        
        menuEdition.add(deleteLinks);
        deleteLinks.addActionListener(this);
        
        // Gerar Molécula em 3D
        menuExibition.add(view3D);
        view3D.addActionListener(this);
        
        /* Adicionamento de Componentes na Janela */
        this.add(scroll, BorderLayout.CENTER);
        this.add(visions, BorderLayout.NORTH);
        this.add(links, BorderLayout.WEST);
        this.add(atoms, BorderLayout.EAST);
        this.add(properties, BorderLayout.SOUTH);
        this.setJMenuBar(menuBar);
        
        /* Adcionando botões na barra de ferramentas de ligações */
        links.add(selection);
        links.add(simpleLink);
        links.add(doubleLink);
        links.add(tripleLink);
        links.add(delete);

        atoms.add(hydrogen);
        atoms.add(carbon);
        atoms.add(oxygen);
        atoms.add(nitrogen);
        atoms.add(fluorine);
        atoms.add(bromine);
        atoms.add(chlorine);
        atoms.add(iodine);
        atoms.add(phosphorus);
        atoms.add(sulfur);
        
        visions.add(vision3D);
                
        
        /* Tornando a Janela visivel */
        this.setVisible(true);
    }
    
    /* Método utilizado para aos eventos dos botões e menus desta classe*/
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == hydrogen)
        {
            selectedTool = 1;
        }
        else if(e.getSource() == carbon)
        {
            selectedTool = 2;
        }
        else if(e.getSource() == oxygen)
        {
            selectedTool = 3;
        }
        else if(e.getSource() == nitrogen)
        {
            selectedTool = 4;
        }
        else if(e.getSource() == fluorine)
        {
            selectedTool = 5;
        }
        if(e.getSource() == bromine)
        {
            selectedTool = 6;
        }
        else if(e.getSource() == chlorine)
        {
            selectedTool = 7;
        }
        else if(e.getSource() == iodine)
        {
            selectedTool = 8;
        }
        else if(e.getSource() == phosphorus)
        {
            selectedTool = 9;
        }
        else if(e.getSource() == sulfur)
        {
            selectedTool = 10;
        }
        else if(e.getSource() == selection)
        {
            selectedTool = 13;
        }
        else if(e.getSource() == simpleLink)
        {
            selectedTool = 14;
        }
        else if(e.getSource() == doubleLink)
        {
            selectedTool = 15;
        }
        else if(e.getSource() == tripleLink)
        {
            selectedTool = 16;
        }
        else if(e.getSource() == delete)
        {
            selectedTool = 17;
        }
        else if( ( e.getSource() == vision3D ) || (e.getSource() == view3D))
        {
            if(molecule.getAtoms().isEmpty())
            {   
                JOptionPane.showMessageDialog(this, "Área de construção vázia, comece a construir a molécula :)");
               
            }
            else if(!rules.stabilityAtoms(molecule.getAtoms()))
            {
                JOptionPane.showMessageDialog(this , "Molécula Inválida, por favor verifique as estabilidades e tente novamente");
            }
            else if(control)
            {
                JOptionPane.showMessageDialog(this , "Já existe uma janela de visualização 3D aberta, por favor feche ela e tente novamente");
            }
            else
            {
                ps = new PreviewScreen(this);
                control = true;
            }
            
        }
        else if(e.getSource() == newProject)
        {
            
        }     
        else if(e.getSource() == openProject)
        {            
            archives.openProjectFile(molecule);
            drawArea.repaint();
        }
        else if(e.getSource() == saveProject)
        {
            archives.saveProjectFile(molecule);
        }
        else if(e.getSource() == export)
        {
            archives.saveImage(drawArea);
        }
        else if(e.getSource() == deleteAll)
        {
            if(!molecule.getAtoms().isEmpty())
            {
                int change = JOptionPane.showConfirmDialog(null, "Você deseja deletar a molécula?", "Deletar Molécula",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/Images/icon_screen.png")));

                if(change == JOptionPane.YES_OPTION)
                {
                    molecule.getLinks().clear();
                    molecule.getAtoms().clear();

                    drawArea.repaint();
                }
            }
        }    
        else if(e.getSource() == deleteLinks)
        {
            if(!molecule.getLinks().isEmpty())
            {
                int change = JOptionPane.showConfirmDialog(null, "Você deseja deletar as ligações?", "Deletar Ligações",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(getClass().getResource("/Images/icon_screen.png")));

                if(change == JOptionPane.YES_OPTION)
                {
                    molecule.getLinks().clear();
                    drawArea.repaint();
                }
                
                ArrayList<Atom> atms = molecule.getAtoms();
                
                int size = atms.size();
                byte links = 0;
                
                for(int i = 0; i < size; i++)
                {
                    atms.get(i).setLinks(links);
                }
            }
        }
        
    }
    
    public void setInformations()
    {
        verification();
        setNumberAtoms();
        setNumberLinks();
        setMass();
    }
    
    private void setSymbols()
    {
        String syms;
        int count = 0;
        ArrayList<Atom> atoms = molecule.getAtoms();
        int size = atoms.size();
        String s;
        String sym[] = {"H", "C", "O", "N", "P", "S", "F", "Cl", "Br", "I"};
        int countC = 0, countH = 0, countO = 0, countN = 0, countP = 0,
            countS = 0, countF = 0, countCl = 0, countBr = 0, countI = 0;
        String definitive1 [] = new String[10];
        String definitive2 [] = new String[10];
        if(!atoms.isEmpty())
        {
            for(int i = 0; i < size; i++)
            {
                s = atoms.get(i).getName();
                
                if(s.equalsIgnoreCase("Hidrogênio"))
                {
                    definitive1[0] = "H";
                    countH ++;
                }
                 else if(s.equalsIgnoreCase("Carbono"))
                {
                    definitive1[0] = "C";
                    countC ++;
                }                
                else if(s.equalsIgnoreCase("Oxigênio"))
                {
                    definitive1[0] = "O";
                    countO ++;
                }
                else if(s.equalsIgnoreCase("Fluor"))
                {
                    definitive1[0] = "F";
                    countF ++;
                }
                else if(s.equalsIgnoreCase("Cloro"))
                {
                    definitive1[0] = "Cl";
                    countCl ++;
                }
                else if(s.equalsIgnoreCase("Fósforo"))
                {
                    definitive1[0] = "F";
                    countP ++;
                }
                else if(s.equalsIgnoreCase("Nitrogênio"))
                {
                    definitive1[0] = "N";
                    countN ++;
                }
                else if(s.equalsIgnoreCase("Iodo"))
                {
                    definitive1[0] = "I";
                    countI ++;
                }
                else if(s.equalsIgnoreCase("Bromo"))
                {
                    definitive1[0] = "Br";
                    countBr ++;
                }
                else if(s.equalsIgnoreCase("Enxofre"))
                {
                    definitive1[0] = "S";
                    countS ++;
                }
            }
            
            int j = 0;
            for(int i = 0; i < 10; i++)
            {
                if(definitive1[i] != null)
                {
                    definitive2[j] = definitive1[i];
                    j++;
                }
            }
            
            
        }
        else
        {
            symbols.setText("Formula molecular:");
        }
        
    }
    
    private void verification()
    {
        if((molecule.getAtoms().isEmpty()) || !rules.stabilityAtoms(molecule.getAtoms()))
        {
            validate.setText("Inválida");
            validate.setBackground(new Color(142,35,35));
        }
        else
        {
            validate.setText("Válida");
            validate.setBackground(new Color(33,94,33));
        }            
    }
    
    private void setNumberAtoms()
    {
        numberAtoms.setText("Número de átomos: " + String.valueOf(molecule.getAtoms().size()));
    }
    
    private void setNumberLinks()
    {
        int count = 0;
        ArrayList<Link> links = molecule.getLinks();
        int size = links.size();
        
        for(int i = 0; i < size; i++)
        {
            String s = links.get(i).getType();
            
            if(s.equals("simple"))
            {
                count ++;
            }
            else if(s.equals("double"))
            {
                count += 2;
            }
            else
            {
                count += 3;
            }
                
        }
        
        numberLinks.setText("Número de ligações: " + String.valueOf(count));
    }
    
    private void setMass()
    {
        ArrayList<Atom> atoms = molecule.getAtoms();
        int size = atoms.size();
        double mass = 0;
        
        for(int i = 0; i < size; i++)
        {
            mass += atoms.get(i).getMass();
        }
        
        molecularWeight.setText("Massa molar: " + mass + " g/mol");
    }
    
    /* FERRAMENTA SELECIONADA */
    public int getSelectedTool()
    {
        return selectedTool;
    }
    public void setSelectedTool(int x)
    {
        selectedTool = x;
    }
    /* TRANSMISSÃO DE DADOS */
    public Molecule getMolecule()
    {
        return molecule;
    }
    
    /* VERIFICAÇÃO DE REGRAS */
    public Rules getRules()
    {
        return rules;
    }
    public Draw getDraw()
    {
        return drawArea;
    }
    
    public void setControl(boolean control)
    {
        this.control = control;
    }
    /* LOOK AND FEEL */
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