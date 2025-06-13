import javax.swing.*;
import java.awt.event.*;

public class ConfirmExit extends WindowAdapter {
    @Override
    public void windowClosing(WindowEvent e) {
        int result = JOptionPane.showConfirmDialog(
                null,
                "Czy na pewno chcesz zamknąć aplikację?",
                "Potwierdzenie zamknięcia",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}
