import javax.swing.*;
import java.io.*;
import java.nio.file.*;

public class NoteManager {
    private final NotatnikFrame frame;
    private final File dir = new File("notatki");

    public NoteManager(NotatnikFrame frame) {
        this.frame = frame;
        if (!dir.exists()) dir.mkdir();
    }

    public void attachNoteListeners(JButton newBtn, JButton deleteBtn, JMenuItem openItem, JMenuItem saveItem) {
        newBtn.addActionListener(e -> frame.textArea.setText(""));
        deleteBtn.addActionListener(e -> deleteNote());
        openItem.addActionListener(e -> loadNote());
        saveItem.addActionListener(e -> saveNote());

        frame.notesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) loadSelectedNote();
        });
    }

    public void loadNoteTitles() {
        frame.notesModel.clear();
        File[] files = dir.listFiles((d, name) -> name.endsWith(".txt"));
        if (files != null)
            for (File file : files)
                frame.notesModel.addElement(file.getName().replace(".txt", ""));
    }

    public void saveNote() {
        String title = JOptionPane.showInputDialog("Nazwa notatki:");
        if (title == null || title.isBlank()) return;
        try {
            Files.writeString(new File(dir, title + ".txt").toPath(), frame.textArea.getText());
            loadNoteTitles();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void loadNote() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(dir);
        int result = chooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                String content = Files.readString(chooser.getSelectedFile().toPath());
                frame.textArea.setText(content);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void deleteNote() {
        String selected = frame.notesList.getSelectedValue();
        if (selected != null) {
            File file = new File(dir, selected + ".txt");
            if (file.delete()) {
                loadNoteTitles();
                frame.textArea.setText("");
            }
        }
    }

    public void loadSelectedNote() {
        String selected = frame.notesList.getSelectedValue();
        if (selected != null) {
            File file = new File(dir, selected + ".txt");
            try {
                frame.textArea.setText(Files.readString(file.toPath()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
