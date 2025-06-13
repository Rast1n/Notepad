import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ThemeManager {
    private final NotatnikFrame frame;

    public ThemeManager(NotatnikFrame frame) {
        this.frame = frame;
    }

    public void attachThemeListeners() {
        ActionListener listener = e -> applyTheme(frame.darkMode.isSelected());
        frame.darkMode.addActionListener(listener);
        frame.lightMode.addActionListener(listener);
    }

    private void applyTheme(boolean dark) {
        Color bg = dark ? Color.DARK_GRAY : Color.WHITE;
        Color fg = dark ? Color.WHITE : Color.BLACK;

        // Основное окно
        frame.getContentPane().setBackground(bg);

        // Компоненты интерфейса
        Component[] components = {
                frame.textArea, frame.searchField, frame.charCountLabel,
                frame.boldCheck, frame.italicCheck, frame.autosaveCheck,
                frame.lightMode, frame.darkMode,
                frame.fontCombo, frame.colorCombo,
                frame.notesList
        };

        for (Component c : components) {
            if (c instanceof JComponent jc) {
                jc.setBackground(bg);
                jc.setForeground(fg);
            }
        }

        // Кнопки
        for (Component c : frame.getContentPane().getComponents()) {
            if (c instanceof JPanel panel) {
                for (Component sub : panel.getComponents()) {
                    if (sub instanceof JButton btn) {
                        btn.setBackground(dark ? new Color(70, 70, 70) : Color.LIGHT_GRAY);
                        btn.setForeground(fg);
                    }
                }
            }
        }

        // Меню
        JMenuBar menuBar = frame.getJMenuBar();
        if (menuBar != null) {
            menuBar.setBackground(bg);
            menuBar.setForeground(fg);
            for (MenuElement menuElement : menuBar.getSubElements()) {
                if (menuElement instanceof JMenu menu) {
                    menu.setBackground(bg);
                    menu.setForeground(fg);
                    for (Component mi : menu.getMenuComponents()) {
                        if (mi instanceof JMenuItem item) {
                            item.setBackground(bg);
                            item.setForeground(fg);
                        }
                    }
                }
            }
        }

        // Обновить отображение
        SwingUtilities.updateComponentTreeUI(frame);
    }
}
