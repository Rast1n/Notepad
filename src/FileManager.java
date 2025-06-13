import javax.swing.*;
import java.io.*;

public class FileManager {

    public static void saveToFile(String content, JFrame parent) {
        JFileChooser chooser = new JFileChooser();
        int res = chooser.showSaveDialog(parent);
        if (res == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(content);
                JOptionPane.showMessageDialog(parent, "Zapisano pomyślnie");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Błąd zapisu: " + ex.getMessage());
            }
        }
    }

    public static String openFromFile(JFrame parent) {
        JFileChooser chooser = new JFileChooser();
        int res = chooser.showOpenDialog(parent);
        if (res == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append("\n");
                }
                return sb.toString();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Błąd odczytu: " + ex.getMessage());
            }
        }
        return null;
    }
}
