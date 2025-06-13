import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NotatnikFrame extends JFrame {
    public JTextArea textArea = new JTextArea();
    public JTextField searchField = new JTextField(15);
    public JLabel charCountLabel = new JLabel("Znaki: 0, Słowa: 0");
    public JCheckBox boldCheck = new JCheckBox("Pogrubienie");
    public JCheckBox italicCheck = new JCheckBox("Kursywa");
    public JCheckBox autosaveCheck = new JCheckBox("Autozapis");
    public JRadioButton lightMode = new JRadioButton("Jasny", true);
    public JRadioButton darkMode = new JRadioButton("Ciemny");
    public JComboBox<String> fontCombo = new JComboBox<>(new String[]{"Arial", "Serif", "Monospaced"});
    public JComboBox<String> colorCombo = new JComboBox<>(new String[]{"Czarny", "Czerwony", "Niebieski"});
    public DefaultListModel<String> notesModel = new DefaultListModel<>();
    public JList<String> notesList = new JList<>(notesModel);
    private final NoteManager noteManager = new NoteManager(this);
    private final AutosaveManager autosaveManager = new AutosaveManager(this);
    private final ThemeManager themeManager = new ThemeManager(this);

    public NotatnikFrame() {
        setTitle("Rozszerzony Notatnik");
        setSize(800, 600);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        lightMode = new JRadioButton("Jasny");
        darkMode = new JRadioButton("Ciemny");

// Группа для взаимного исключения
        ButtonGroup themeGroup = new ButtonGroup();
        themeGroup.add(lightMode);
        themeGroup.add(darkMode);

// По умолчанию светлая тема
        lightMode.setSelected(true);

        // --- Text Area ---
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        // --- Bottom Panel ---
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.add(boldCheck);
        bottomPanel.add(italicCheck);
        bottomPanel.add(fontCombo);
        bottomPanel.add(colorCombo);
        bottomPanel.add(autosaveCheck);
        bottomPanel.add(lightMode);
        bottomPanel.add(darkMode);
        bottomPanel.add(searchField);
        JButton searchButton = new JButton("Szukaj");
        bottomPanel.add(searchButton);
        bottomPanel.add(charCountLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- Left Panel: Notes list ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        JButton newNoteBtn = new JButton("Nowa notatka");
        JButton deleteNoteBtn = new JButton("Usuń notatkę");
        JPanel noteButtons = new JPanel(new GridLayout(2, 1));
        noteButtons.add(newNoteBtn);
        noteButtons.add(deleteNoteBtn);
        leftPanel.add(noteButtons, BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(notesList), BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);

        // --- Menu ---
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Plik");
        JMenuItem openItem = new JMenuItem("Otwórz");
        JMenuItem saveItem = new JMenuItem("Zapisz");
        JMenuItem exitItem = new JMenuItem("Zamknij");
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        JMenu editMenu = new JMenu("Edycja");
        menuBar.add(editMenu);
        setJMenuBar(menuBar);

        // --- Listeners ---
        FormatHelper.attachFormatListeners(this);
        themeManager.attachThemeListeners();
        SearchHelper.attachSearchListener(this, searchButton);
        noteManager.attachNoteListeners(newNoteBtn, deleteNoteBtn, openItem, saveItem);
        autosaveManager.startAutosave();

        textArea.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                UIHelper.updateCharCount(textArea, charCountLabel);
            }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(NotatnikFrame.this,
                        "Czy na pewno chcesz zamknąć?", "Zamknięcie",
                        JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) System.exit(0);
            }
        });

        noteManager.loadNoteTitles();
    }
}
