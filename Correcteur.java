/**
 * Created by Marc-André Piché|783722 | 2017-12-17.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public class Correcteur extends JFrame{
    private JTextArea texteZone = new JTextArea();
    private Font textFont =  new Font("Arial",10,26);

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
        menuItemOuvrir.getAccessibleContext().setAccessibleDescription(
                "Ouvrir un fichier");
        Action openFileAction = new AbstractAction("Save") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Ouvrir...");
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
                System.out.println("Saving...");
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
        menuBar.add(menuDict);

        //menu verification
        JMenu menuVerif = new JMenu("Vérifier");
        JMenuItem menuItemOrth = new JMenuItem("Orthographe");
        menuVerif.add(menuItemOrth);
        menuItemOrth.getAccessibleContext().setAccessibleDescription("Vérifier l'orthographe");
        Action verifierAction = new AbstractAction("Vérifier") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo if disctionaire is empty message
                System.out.println("Vérifions");
                TextAreaHighlight h = new TextAreaHighlight();
                h.highlight(texteZone,"arbre");

            }
        };
        menuItemOrth.setAction(verifierAction);
        menuBar.add(menuVerif);

        this.setJMenuBar(menuBar);
    }// -- END MENU


    public static void main(String[] args) {
        Correcteur frame = new Correcteur();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width*10/20, screenSize.height*10/16);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
