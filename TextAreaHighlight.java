import java.awt.*;
import javax.swing.text.*;

class TextAreaHighlight {

    // Creates highlights around all occurrences of pattern in textComp
    public void highlight(JTextComponent textComp, Dictionnaire pattern) {
        // First remove all old highlights
        removeHighlights(textComp);
        int wordStart = 0;//positions du mot observé
        int wordEnd = 0;

        try {
            Highlighter hilite = textComp.getHighlighter();
            Document doc = textComp.getDocument();
            String text = doc.getText(0, doc.getLength());

            //recherche pour les mots du dictionnaire
            for (int i = 0; i <= text.length();){
                if(i == text.length() || pattern.isPunctuation(text.charAt(i))){ //vérifier le mot à la fin ou aux punctuations
                    //highlight si pas dans le dictionnaire
                    if(!(pattern.querry(text.substring(wordStart,wordEnd).toLowerCase())) & wordStart != wordEnd){
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

    // Removes only our private highlights
    public void removeHighlights(JTextComponent textComp) {
        Highlighter hilite = textComp.getHighlighter();
        Highlighter.Highlight[] hilites = hilite.getHighlights();

        for (int i=0; i<hilites.length; i++) {
            if (hilites[i].getPainter() instanceof MyHighlightPainter) {
                hilite.removeHighlight(hilites[i]);
            }
        }
    }

    // An instance of the private subclass of the default highlight painter
    private Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(Color.red);

    // A private subclass of the default highlight painter
    private class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
        MyHighlightPainter(Color color) {
            super(color);
        }
    }
}
