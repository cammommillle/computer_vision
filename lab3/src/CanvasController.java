import javax.swing.*;
import java.awt.*;

public class CanvasController {
    private CanvasPanel canvas;
    private JPanel panel;
    private JTextField startXField, startYField, endXField, endYField, rField;
    int x0, y0, x1, y1, r;

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
       // JButton setXY = new JButton("Set XYR");

        // Добавляем поля и кнопку в панель
        controlsPanel.add(new JLabel("Start X:"));
        controlsPanel.add(startXField);
        controlsPanel.add(new JLabel("Start Y:"));
        controlsPanel.add(startYField);
        controlsPanel.add(new JLabel("End X:"));
        controlsPanel.add(endXField);
        controlsPanel.add(new JLabel("End Y:"));
        controlsPanel.add(endYField);
        controlsPanel.add(new JLabel("End R:"));
        controlsPanel.add(rField);
        //controlsPanel.add(setXY);

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

    private void drawStepByStepLine() {
        setValues();
        Graphics2D g = canvas.getCanvasGraphics();
        StepByStepLine.drawLine(g,  x0, y0, x1, y1);
    }

    private void drawDDALine() {
        setValues();
        Graphics2D g = canvas.getCanvasGraphics();
        DDALine.drawLine(g,  x0, y0, x1, y1);
    }

    private void drawBresenhamLine() {
        setValues();
        Graphics2D g = canvas.getCanvasGraphics();
        BresenhamLine.drawLine(g,  x0, y0, x1, y1);
    }

    private void drawBresenhamCircle() {
        setValues();
        Graphics2D g = canvas.getCanvasGraphics();
        BresenhamCircle.drawCircle(g, x0, y0, r);
    }

    public JPanel getPanel() {
        return panel;
    }
}
