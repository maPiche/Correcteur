/**
 * Created by Marc-André Piché|783722 | 2017-12-17.
 */

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;


public class Correcteur extends JFrame{
    private JTextArea texteZone = new JTextArea("C'est fou ce Dictionnaire inncroyalbe.");
    private Font textFont =  new Font("Arial",Font.PLAIN,26);
    private Dictionnaire dictionnaire;
    private Boolean corrige = false;
    private JPopupMenu menuCor = new JPopupMenu();

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


    private void checkWord(String word, int wordStart, int wordEnd, MouseEvent event) {
        if (!dictionnaire.querry(word.toLowerCase())) {
            texteZone.select(wordStart, wordEnd);

            JMenuItem mot1 = new JMenuItem(word);
            JMenuItem mot2 = new JMenuItem(word);
            JMenuItem mot3 = new JMenuItem(word);
            JMenuItem mot4 = new JMenuItem(word);
            JMenuItem mot5 = new JMenuItem(word);

            //mot1.addActionListener(this);
            //mot2.addActionListener(this);
            //mot3.addActionListener(this);
            //mot4.addActionListener(this);
            //mot5.addActionListener(this);

            menuCor.add(mot1);
            menuCor.add(mot2);
            menuCor.add(mot3);
            menuCor.add(mot4);
            menuCor.add(mot5);
            menuCor.show(texteZone, event.getX(), event.getY() + 20);
        }
    }

    /*class Boutton extends JMenuItem implements ActionListener{

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
    }*/


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
        Action openFileAction = new AbstractAction("Ouvrir") {
            @Override
            public void actionPerformed(ActionEvent e) {
            	try {
            		JFileChooser choix = new JFileChooser();{
                        //verifie que l'extension du fichier est bien .txt
	            		FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers texte", "txt");
	            		choix.setFileFilter(filter); //applique le filtre
	             	if (choix.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	             		FileReader in = new FileReader(choix.getSelectedFile());
	             		BufferedReader buff = new BufferedReader(in);  
	             		String ligne;
	             		texteZone.setText("");
	             		
	             		while((ligne = buff.readLine()) != null) {
	             			texteZone.append(ligne + "\n");
	             		}
	             		buff.close();
	             	}
            		}
            }catch (FileNotFoundException exception){
            		JOptionPane.showMessageDialog(null,
                            "Fichier de lecture introuvable!", "Erreur", JOptionPane.ERROR_MESSAGE);
            }catch (IOException exception){
                exception.printStackTrace();
            }
            }
        };
        openFileAction.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        menuItemOuvrir.setAction(openFileAction);

        JMenuItem menuItemEnreg = new JMenuItem("Enregistrer");
        menuFichier.add(menuItemEnreg);
        menuItemEnreg.getAccessibleContext().setAccessibleDescription("Enregistrer un fichier");
        Action saveAction = new AbstractAction("Enregistrer") {
            @Override
            public void actionPerformed(ActionEvent e) {
	            	try {
	            		JFileChooser choix = new JFileChooser();{
                            //verifie que l'extension du fichier est bien .txt
		            		FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers texte", "txt");
		            		choix.setFileFilter(filter); //applique le filtre 
		             	if (choix.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		             		FileWriter out = new FileWriter(choix.getSelectedFile());
		             		BufferedWriter buff = new BufferedWriter(out);  
		             		out.write(texteZone.getText());	             		
		             		buff.close();
		             	}
	            		}	
	            }catch (FileNotFoundException exception){
	            		JOptionPane.showMessageDialog(null,
                                "Fichier d'écriture introuvable!", "Erreur", JOptionPane.ERROR_MESSAGE);
	            }catch (IOException exception){
	                exception.printStackTrace();
	            }
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
            		JOptionPane.showMessageDialog(null,
                            "Au revoir!", "Fermeture", JOptionPane.INFORMATION_MESSAGE);
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
	            		JFileChooser choix = new JFileChooser();{
                            //verifie que l'extension du fichier est bien .txt
		            		FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers texte", "txt");
		            		choix.setFileFilter(filter); //applique le filtre 
		             	if (choix.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		             		dictionnaire = new Dictionnaire(choix.getSelectedFile());
		             	}
	            		}
	            }catch (FileNotFoundException exception){
	            		JOptionPane.showMessageDialog(null,
                                "Fichier contenant le dictionnaire introuvable!", "Erreur", JOptionPane.ERROR_MESSAGE);
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
                    TextAreaHighlight h = new TextAreaHighlight();
                    h.highlight(texteZone, dictionnaire);//highlight les mots qui ne sont pas dans dictionnaire
                    texteZone.add(menuCor);

                    texteZone.addMouseListener(new MouseAdapter() {
                        public void mouseClicked(MouseEvent event) {
                            if (SwingUtilities.isRightMouseButton(event)) {
                                try {
                                    menuCor.removeAll();
                                    int offset = texteZone.viewToModel(event.getPoint());
                                    System.out.println(texteZone.modelToView(offset));
                                    int start = Utilities.getWordStart(texteZone, offset);
                                    int end = Utilities.getWordEnd(texteZone, offset);
                                    String word = texteZone.getDocument().getText(start, end - start);
                                    System.out.println("Selected word: " + word);
                                    checkWord(word, start, end, event);
                                } catch (Exception e2) {
                                }
                            }
                        }
                    });

                } else {
                    JOptionPane.showMessageDialog(null,
                            "Le dictionnaire est vide!", "Erreur", JOptionPane.ERROR_MESSAGE);
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
