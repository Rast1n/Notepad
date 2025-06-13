import java.util.Timer;
import java.util.TimerTask;
import java.io.*;

public class AutosaveManager {
    private final NotatnikFrame frame;
    private final Timer timer = new Timer(true);
    private final File autosaveFile = new File("autosave.txt");

    public AutosaveManager(NotatnikFrame frame) {
        this.frame = frame;
    }

    public void startAutosave() {
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (frame.autosaveCheck.isSelected()) {
                    try (FileWriter fw = new FileWriter(autosaveFile)) {
                        fw.write(frame.textArea.getText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 5000, 5000); // каждые 5 секунд
    }
}
