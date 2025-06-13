public class UIHelper {
    public static void updateCharCount(javax.swing.JTextArea area, javax.swing.JLabel label) {
        String text = area.getText();
        int chars = text.length();
        int words = text.isBlank() ? 0 : text.trim().split("\\s+").length;
        label.setText("Znaki: " + chars + ", Słowa: " + words);
    }
}
