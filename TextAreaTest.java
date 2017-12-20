import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

public class TextAreaTest extends JFrame
{
    private JTextArea textArea;
    private JScrollPane scrollPane;

    private TextAreaTest()
    {
        textArea = new JTextArea( "one two\nthree four", 10, 30 )
        {
            //  the tab character is not inserted into the text area
            public boolean isManagingFocus()
            {
                return false;
            }
        };
        textArea.setLineWrap( true );
        textArea.setSize( textArea.getPreferredSize() );
        textArea.setSelectionColor( Color.red );
        textArea.setSelectedTextColor( Color.green );
        try
        {
            System.out.println( textArea.modelToView( 4 ) );
            System.out.println( Utilities.getRowStart(textArea, 4) );
        }
        catch (Exception e) {}

        textArea.addMouseListener( new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                if ( SwingUtilities.isRightMouseButton(e) )
                {
                    try
                    {
                        int offset = textArea.viewToModel( e.getPoint() );
                        System.out.println( textArea.modelToView( offset ) );
                        int start = Utilities.getWordStart(textArea,offset);
                        int end = Utilities.getWordEnd(textArea, offset);
                        String word = textArea.getDocument().getText(start, end-start);
                        System.out.println( "Selected word: " + word);
                        int rowStart = Utilities.getRowStart(textArea, offset);
                        int rowEnd = Utilities.getRowEnd(textArea, offset);
                        System.out.println( "Row start offset: " + rowStart );
                        System.out.println( "Row end   offset: " + rowEnd );
                        textArea.select(rowStart, rowEnd);
                    }
                    catch (Exception e2) {}
                }
            }
        });

        textArea.addCaretListener( new CaretListener(){
            public void caretUpdate(CaretEvent e)
            {
                int caretPosition = textArea.getCaretPosition();
                Element root = textArea.getDocument().getDefaultRootElement();
                int row = root.getElementIndex( caretPosition );
                int column = caretPosition - root.getElement( row ).getStartOffset();
                System.out.println( "Row   : " + ( row + 1 ) );
                System.out.println( "Column: " + ( column + 1 ) );
            }
        });


        textArea.addKeyListener( new KeyAdapter()
        {
            public void keyPressed(KeyEvent e)
            {
                System.out.println( textArea.getDocument().getDefaultRootElement().getElementCount() );
            }
        });

        scrollPane = new JScrollPane( textArea );
        getContentPane().add( scrollPane );
        getContentPane().add( new JTextField(10),BorderLayout.SOUTH );

        //textArea.getInputMap().put( KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "" );
    }

    public static void main(String[] args)
    {
        TextAreaTest frame = new TextAreaTest();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE );
        frame.pack();
        frame.setVisible(true);
    }
}