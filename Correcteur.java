/**
 * Created by Marc-André Piché|783722 | 2017-12-17.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class Correcteur extends JFrame{
    private JTextArea texteZone = new JTextArea();
    private Font textFont =  new Font("Arial",Font.PLAIN,26);
    private Dictionnaire dictionnaire;

    private Correcteur() {
        super("Correcteur");
        JPanel textPanel = new JPanel();

        JScrollPane scroll = new JScrollPane(texteZone);
        texteZone.setWrapStyleWord(true);
        texteZone.setLineWrap(true);
        texteZone.setFont(textFont);
        textPanel.setLayout(new BorderLayout());
        textPanel.add(scroll, BorderLayout.CENTER);
        textPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        makeMenu();
        this.add(textPanel, BorderLayout.CENTER);
        this.pack();
    }

    class Boutton extends JButton implements ActionListener{

        Boutton(String name) {
            super(name);
            addActionListener(this);
            this.setPreferredSize(new Dimension(150,100));
            this.setMaximumSize(new Dimension(150,200));
        }
        // action listener
        public void actionPerformed(java.awt.event.ActionEvent e) {
            texteZone.append(getText());
        }
    }


    //---MENU BAR
    private void makeMenu(){
        UIManager.put("Menu.font", textFont);
        UIManager.put("MenuItem.font", textFont);
        JMenuBar menuBar = new JMenuBar();

        //menu fichier
        JMenu menuFichier = new JMenu("Fichier");
        menuBar.add(menuFichier);

        JMenuItem menuItemOuvrir = new JMenuItem("Ouvrir");
        menuFichier.add(menuItemOuvrir);
        menuItemOuvrir.getAccessibleContext().setAccessibleDescription("Ouvrir un fichier");
        Action openFileAction = new AbstractAction("Ouverture") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Ouvrir...");//todo LECTURE d'un fichier .txt avec JFilechooser et remplacer le contenu de texteZone
            }
        };
        openFileAction.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        menuItemOuvrir.setAction(openFileAction);

        JMenuItem menuItemEnreg = new JMenuItem("Enregistrer");
        menuFichier.add(menuItemEnreg);
        menuItemEnreg.getAccessibleContext().setAccessibleDescription("Enregistrer un fichier");
        Action saveAction = new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Saving...");//todo ÉCRITURE d'un fichier .txt avec JFilechooser et remplacer le contenu de texteZone
            }
        };
        saveAction.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        menuItemEnreg.setAction(saveAction);

        JMenuItem menuItemQuit = new JMenuItem("Quitter");
        menuFichier.add(menuItemQuit);
        menuItemQuit.getAccessibleContext().setAccessibleDescription("Quitter le correcteur");
        Action quitAction = new AbstractAction("Quitter") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Au revoir");
                System.exit(0);
            }
        };
        menuItemQuit.setAction(quitAction);
        //fin menu fichier

        //menu dictionnaire
        JMenu menuDict = new JMenu("Dictionnaire");
        JMenuItem menuItemCharger = new JMenuItem("Charger...");
        menuDict.add(menuItemCharger);
        menuItemCharger.getAccessibleContext().setAccessibleDescription("Charger un dictionnaire");
        Action loadAction = new AbstractAction("Dictionnaire...") {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    dictionnaire = new Dictionnaire(new File("dict.f.txt")); //todo : changer pour un JFileChooser et verifie si c'est un .txt
                    System.out.println(dictionnaire.querry("abricot"));//todo : a supprimer (fonction test)

                }catch (FileNotFoundException exception){
                    System.out.println("Erreur : Fichier dictionnaire introuvable"); //todo remplacer par un message a l'utilisateur
                }catch (IOException exception){
                    exception.printStackTrace();
                }
            }
        };
        menuItemCharger.setAction(loadAction);
        menuBar.add(menuDict);

        //menu verification
        JMenu menuVerif = new JMenu("Vérifier");
        JMenuItem menuItemOrth = new JMenuItem("Orthographe");
        menuVerif.add(menuItemOrth);
        menuItemOrth.getAccessibleContext().setAccessibleDescription("Vérifier l'orthographe");
        Action verifierAction = new AbstractAction("Vérifier") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dictionnaire != null && !(dictionnaire.isEmpty())) {
                    System.out.println("Vérifions");
                    TextAreaHighlight h = new TextAreaHighlight();
                    h.highlight(texteZone, "arbre");//todo : changer pour une fonction qui highlight les mots pas dans le dictionnaire -- probablement en modifiant textAreaHighlight
                }else {
                    System.out.println("Le dictionnaire est vide"); //todo remplacer par un message a l'utilisateur
                }
            }
        };
        menuItemOrth.setAction(verifierAction);
        menuBar.add(menuVerif);

        this.setJMenuBar(menuBar);
    }// -- END MENU

    //todo ajouter fonction qui renvoie un array de 5 mots à partir d'un right click sur un mot et du dictionnaire


    public static void main(String[] args) {
        Correcteur frame = new Correcteur();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width*10/20, screenSize.height*10/16);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
