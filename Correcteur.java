/**
 * Created by Marc-André Piché|783722 | 2017-12-17.
 * 			  Anthony Lemieux|20077762
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
    
    //Constructeur Correcteur
    private Correcteur() {
        super("Correcteur");
        JPanel textPanel = new JPanel();
        //Mise en forme du TextArea et du Panel
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

    //Action lorsqu'on sur un item du popup-menu
    private class distanceDeMot extends AbstractAction{
        private int distance;
        private String word;

        distanceDeMot(String word,int distance){
            super(word + " "+distance);
            this.distance = distance;
            this.word = word;
        }
        @Override
        public void actionPerformed(ActionEvent e) {

            JOptionPane.showMessageDialog(null,
                    word +" :"+distance, "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void checkWord(String word, int wordStart, int wordEnd, MouseEvent event) {
        //Genere un menu si le mot n'est pas dans le dictionnaire
        if (!(dictionnaire.querry(word.toLowerCase()) || dictionnaire.isPunctuation(word.charAt(0)))) {
            texteZone.select(wordStart, wordEnd);

            WordDistance wd = new WordDistance();
            String words[] = wd.correction(word,dictionnaire);


            System.out.println(words.toString());
            //Creation des mots proposes dans le JPopupMenu
            JMenuItem mot1 = new JMenuItem(new distanceDeMot(words[0],1));
            JMenuItem mot2 = new JMenuItem(new distanceDeMot(words[1],2));
            JMenuItem mot3 = new JMenuItem(new distanceDeMot(words[2],3));
            JMenuItem mot4 = new JMenuItem(new distanceDeMot(words[3],4));
            JMenuItem mot5 = new JMenuItem(new distanceDeMot(words[4],5));

            menuCor.add(mot1);
            menuCor.add(mot2);
            menuCor.add(mot3);
            menuCor.add(mot4);
            menuCor.add(mot5);
            menuCor.show(texteZone, event.getX(), event.getY() + 20); //affichage du JPopupMenu
        }
    }

    //---MENU BAR  : creation de tous les items menu -----//
    private void makeMenu(){
        UIManager.put("Menu.font", textFont);
        UIManager.put("MenuItem.font", textFont);
        JMenuBar menuBar = new JMenuBar();

        
        //Menu fichier
        JMenu menuFichier = new JMenu("Fichier");
        menuBar.add(menuFichier);

        //Sous-menu Ouvrir
        JMenuItem menuItemOuvrir = new JMenuItem("Ouvrir"); 
        menuFichier.add(menuItemOuvrir);
        menuItemOuvrir.getAccessibleContext().setAccessibleDescription("Ouvrir un fichier");
        
        Action openFileAction = new AbstractAction("Ouvrir") {
            @Override
            public void actionPerformed(ActionEvent e) { //Action lors d'une ouverture de fichier
            	try {
            		JFileChooser choix = new JFileChooser();{                        
	            		FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers texte", "txt"); //Verifie que l'extension du fichier choisi est bien .txt
	            		choix.setFileFilter(filter); //Applique le filtre
	            		
	             	if (choix.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	             		FileReader in = new FileReader(choix.getSelectedFile()); 	//Lecture du fichier
	             		BufferedReader buff = new BufferedReader(in);  
	             		String ligne;
	             		texteZone.setText(""); //Reinitialise le TextArea 
	             		
	             		while((ligne = buff.readLine()) != null) {
	             			texteZone.append(ligne + "\n");	//Affiche le contenu du fichier dans le TextArea
	             		}
	             		buff.close();  //Fermeture du tampon de lecture
	             	}
            		}
            }catch (FileNotFoundException exception){ //Exception
            		JOptionPane.showMessageDialog(null,
                            "Fichier de lecture introuvable!", "Erreur", JOptionPane.ERROR_MESSAGE);
            }catch (IOException exception){
                exception.printStackTrace();
            }
            }
        };
        openFileAction.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK));
        menuItemOuvrir.setAction(openFileAction);

        
        //Sous-menu Enregistrer
        JMenuItem menuItemEnreg = new JMenuItem("Enregistrer"); 
        menuFichier.add(menuItemEnreg);
        menuItemEnreg.getAccessibleContext().setAccessibleDescription("Enregistrer un fichier");
        
        Action saveAction = new AbstractAction("Enregistrer") {
            @Override
            public void actionPerformed(ActionEvent e) { 	//Action lors d'un enregistrement
	            	try {
	            		JFileChooser choix = new JFileChooser();{             
		            		FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers texte", "txt"); 	//verifie que l'extension du fichier est bien .txt
		            		choix.setFileFilter(filter); //Applique le filtre 
		            		
		             	if (choix.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		             		FileWriter out = new FileWriter(choix.getSelectedFile()); 	//Ecriture des caracteres du TextArea dans un fichier txt
		             		BufferedWriter buff = new BufferedWriter(out);  
		             		out.write(texteZone.getText());	             		
		             		buff.close(); //Fermeture du tampon d'ecriture
		             	}
	            		}	
	            }catch (FileNotFoundException exception){	 //Exceptions
	            		JOptionPane.showMessageDialog(null,
                                "Fichier d'écriture introuvable!", "Erreur", JOptionPane.ERROR_MESSAGE);
	            }catch (IOException exception){
	                exception.printStackTrace();
	            }
            }
        };
        saveAction.putValue(Action.ACCELERATOR_KEY,KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
        menuItemEnreg.setAction(saveAction);

        JMenuItem menuItemQuit = new JMenuItem("Quitter"); //Sous-menu Quitter
        menuFichier.add(menuItemQuit);
        menuItemQuit.getAccessibleContext().setAccessibleDescription("Quitter le correcteur");
        
        Action quitAction = new AbstractAction("Quitter") {
            @Override
            public void actionPerformed(ActionEvent e) {	//Action lors de la fermeture du programme
            		JOptionPane.showMessageDialog(null,
                            "Au revoir!", "Fermeture", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        };
        menuItemQuit.setAction(quitAction);
        //Fin menu fichier

        
        
        //Menu dictionnaire
        JMenu menuDict = new JMenu("Dictionnaire");
        JMenuItem menuItemCharger = new JMenuItem("Charger..."); //Sous-menu permettant de charger un dictionnaire
        menuDict.add(menuItemCharger);
        menuItemCharger.getAccessibleContext().setAccessibleDescription("Charger un dictionnaire");
        Action loadAction = new AbstractAction("Dictionnaire...") {
            @Override
            public void actionPerformed(ActionEvent e) { 	//Action au chargement d'un dictionnaire

	            	try {
	            		JFileChooser choix = new JFileChooser();{                        
		            		FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers texte", "txt"); //verifie que l'extension du fichier est bien .txt
		            		choix.setFileFilter(filter); //Applique le filtre 
		             	if (choix.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		             		dictionnaire = new Dictionnaire(choix.getSelectedFile());
		             	}
	            		}
	            }catch (FileNotFoundException exception){ //Exceptions
	            		JOptionPane.showMessageDialog(null,
                                "Fichier contenant le dictionnaire introuvable!", "Erreur", JOptionPane.ERROR_MESSAGE);
	            }catch (IOException exception){
	                exception.printStackTrace();
	            }
            }
        };
        menuItemCharger.setAction(loadAction);
        menuBar.add(menuDict);
        //Fin menu dictionnaire

        
        //Menu verification
        JMenu menuVerif = new JMenu("Vérifier");
        JMenuItem menuItemOrth = new JMenuItem("Orthographe"); //Sous-menu permettant de verifier le texte contenu dans le TextArea
        menuVerif.add(menuItemOrth);
        menuItemOrth.getAccessibleContext().setAccessibleDescription("Vérifier l'orthographe");
        
        Action verifierAction = new AbstractAction("Vérifier") {
            @Override
            public void actionPerformed(ActionEvent e) { //Action lors d'une demande de verification du texte
                if (dictionnaire != null && !(dictionnaire.isEmpty())) {
                	
                    TextAreaHighlight h = new TextAreaHighlight();
                    h.highlight(texteZone, dictionnaire);//Highlight les mots qui ne sont pas dans dictionnaire
                    corrige = true;
                    texteZone.add(menuCor);

                    texteZone.addMouseListener(new MouseAdapter() { //Ecouteur de souris
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
                                    checkWord(word, start, end, event); //Verification du mot dans le dictionnaire
                                } catch (Exception e2) {}                               
                            }
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null,"Le dictionnaire est vide!", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        menuItemOrth.setAction(verifierAction);
        menuBar.add(menuVerif);
        //Fin menu Verification       
        
        this.setJMenuBar(menuBar);
    }// -- END MENU

    //Main + Creation de la fenêtre JFrame
    public static void main(String[] args) {
        Correcteur frame = new Correcteur();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(screenSize.width*10/20, screenSize.height*10/16);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
