import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CanvasController {
    private CanvasPanel canvas;
    private JPanel panel;
    private JTextField startXField, startYField, endXField, endYField, rField;
    int x0, y0, x1, y1, r;
    long time;

    public CanvasController() {
        canvas = new CanvasPanel();
        panel = new JPanel(new BorderLayout());
        panel.add(canvas, BorderLayout.CENTER);

        // Панель с кнопками и полями ввода
        JPanel controlsPanel = new JPanel();

        // Поля для ввода координат
        startXField = new JTextField(3);
        startYField = new JTextField(3);
        endXField = new JTextField(3);
        endYField = new JTextField(3);
        rField = new JTextField(3);

        // Добавляем поля и кнопку в панель
        controlsPanel.add(new JLabel("Start X:"));
        controlsPanel.add(startXField);
        controlsPanel.add(new JLabel("Start Y:"));
        controlsPanel.add(startYField);
        controlsPanel.add(new JLabel("End X:"));
        controlsPanel.add(endXField);
        controlsPanel.add(new JLabel("End Y:"));
        controlsPanel.add(endYField);
        controlsPanel.add(new JLabel("Radius:"));
        controlsPanel.add(rField);

        JButton btnStepByStep = new JButton("Step-by-Step Line");
        JButton btnDDA = new JButton("DDA Line");
        JButton btnBresenhamLine = new JButton("Bresenham Line");
        JButton btnBresenhamCircle = new JButton("Bresenham Circle");
        JButton btnClear = new JButton("Clear");
        JButton btnZoomIn = new JButton("Zoom In");
        JButton btnZoomOut = new JButton("Zoom Out");

        controlsPanel.add(btnStepByStep);
        controlsPanel.add(btnDDA);
        controlsPanel.add(btnBresenhamLine);
        controlsPanel.add(btnBresenhamCircle);
        controlsPanel.add(btnZoomIn);
        controlsPanel.add(btnZoomOut);
        controlsPanel.add(btnClear);
        panel.add(controlsPanel, BorderLayout.SOUTH);

        btnClear.addActionListener(e -> canvas.clearCanvas());
        btnZoomIn.addActionListener(e -> canvas.zoomIn());
        btnZoomOut.addActionListener(e -> canvas.zoomOut());

        // Слушатели для отрисовки линий на основе введенных координат
        btnStepByStep.addActionListener(e -> drawStepByStepLine());
        btnDDA.addActionListener(e -> drawDDALine());
        btnBresenhamLine.addActionListener(e -> drawBresenhamLine());
        btnBresenhamCircle.addActionListener(e -> drawBresenhamCircle());
    }

    private void setValues() {
        x0 = Integer.parseInt(startXField.getText());
        y0 = Integer.parseInt(startYField.getText());
        x1 = Integer.parseInt(endXField.getText());
        y1 = Integer.parseInt(endYField.getText());
        r = Integer.parseInt(rField.getText());
    }

    private void writeToFile(String message) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("report.txt", true)))) {
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void drawStepByStepLine() {
        setValues();
        Graphics2D g = canvas.getCanvasGraphics();
        time = StepByStepLine.drawLine(g, x0, y0, x1, y1);
        String message = "Step by Step Line Algorithm Time: " + time + " nanoseconds";
        writeToFile(message);
    }

    private void drawDDALine() {
        setValues();
        Graphics2D g = canvas.getCanvasGraphics();
        time = DDALine.drawLine(g, x0, y0, x1, y1);
        String message = "DDA Line Algorithm Time: " + time + " nanoseconds";
        writeToFile(message);
    }

    private void drawBresenhamLine() {
        setValues();
        Graphics2D g = canvas.getCanvasGraphics();
        time = BresenhamLine.drawLine(g, x0, y0, x1, y1);
        String message = "Bresenham Line Algorithm Time: " + time + " nanoseconds";
        writeToFile(message);
    }

    private void drawBresenhamCircle() {
        setValues();
        Graphics2D g = canvas.getCanvasGraphics();
        time = BresenhamCircle.drawCircle(g, x0, y0, r);
        String message = "Bresenham Circle Algorithm Time: " + time + " nanoseconds";
        writeToFile(message);
    }

    public JPanel getPanel() {
        return panel;
    }
}
