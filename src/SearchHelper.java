import java.awt.event.*;
import javax.swing.text.*;

public class SearchHelper {
    public static void attachSearchListener(NotatnikFrame frame, javax.swing.JButton button) {
        button.addActionListener(e -> {
            String text = frame.textArea.getText();
            String query = frame.searchField.getText();
            if (query.isEmpty()) return;

            Highlighter highlighter = frame.textArea.getHighlighter();
            highlighter.removeAllHighlights();
            int index = text.indexOf(query);
            while (index >= 0) {
                try {
                    highlighter.addHighlight(index, index + query.length(),
                            new DefaultHighlighter.DefaultHighlightPainter(java.awt.Color.YELLOW));
                    index = text.indexOf(query, index + 1);
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
