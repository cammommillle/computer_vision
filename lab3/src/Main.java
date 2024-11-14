import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rasterization Algorithms");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 800);
            frame.setResizable(false);

            // Создаем контроллер и добавляем в окно
            CanvasController controller = null;
            controller = new CanvasController();

            frame.add(controller.getPanel());

            frame.setVisible(true);
        });
    }
}
