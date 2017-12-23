import java.awt.*;
import javax.swing.text.*;

class TextAreaHighlight {

    //Surligne toutes les occurences de mots présents dans textComp qui sont absent dans le dictionnaire
    public void highlight(JTextComponent textComp, Dictionnaire pattern) {
        //Enleve le surlignement précédent des mots
        removeHighlights(textComp);
        int wordStart = 0;//Positions du mot observe
        int wordEnd = 0;

        try {
            Highlighter hilite = textComp.getHighlighter();
            Document doc = textComp.getDocument();
            String text = doc.getText(0, doc.getLength());

            //Recherche pour les mots du dictionnaire
            for (int i = 0; i <= text.length();){
                if(i == text.length() || pattern.isPunctuation(text.charAt(i))){ //vérifier le mot à la fin ou aux punctuations
                    //Highlight le mot sil nest pas dans le dictionnaire
                    if(!(pattern.querry(text.substring(wordStart,wordEnd).toLowerCase())) && wordStart != wordEnd){
                        hilite.addHighlight(wordStart, wordEnd, myHighlightPainter);
                    }
                    wordStart = ++i;
                    wordEnd = wordStart;
                }else{
                    wordEnd = ++i;
                }
            }
        } catch (BadLocationException e) {
        }
    }

    //Enleve seulement les surlignements precedents
    public void removeHighlights(JTextComponent textComp) {
        Highlighter hilite = textComp.getHighlighter();
        Highlighter.Highlight[] hilites = hilite.getHighlights();

        for (int i=0; i<hilites.length; i++) {	//Parcours le tableau de surlignements et les efface tous
            if (hilites[i].getPainter() instanceof MyHighlightPainter) {
                hilite.removeHighlight(hilites[i]);
            }
        }
    }

    // Instance de la sous-classe privee du painter par defaut de surlignement
    private Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.red);

    // Sous-classe privee du painter par defaut de surlignement
    private class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
        MyHighlightPainter(Color color) {
            super(color);
        }
    }
}
