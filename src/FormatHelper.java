import java.awt.*;
import java.awt.event.*;

public class FormatHelper {
    public static void attachFormatListeners(NotatnikFrame frame) {
        ActionListener listener = e -> {
            int style = Font.PLAIN;
            if (frame.boldCheck.isSelected()) style |= Font.BOLD;
            if (frame.italicCheck.isSelected()) style |= Font.ITALIC;
            String fontName = (String) frame.fontCombo.getSelectedItem();
            frame.textArea.setFont(new Font(fontName, style, 14));

            String color = (String) frame.colorCombo.getSelectedItem();
            switch (color) {
                case "Czerwony" -> frame.textArea.setForeground(Color.RED);
                case "Niebieski" -> frame.textArea.setForeground(Color.BLUE);
                default -> frame.textArea.setForeground(Color.BLACK);
            }
        };
        frame.boldCheck.addActionListener(listener);
        frame.italicCheck.addActionListener(listener);
        frame.fontCombo.addActionListener(listener);
        frame.colorCombo.addActionListener(listener);
    }
}
