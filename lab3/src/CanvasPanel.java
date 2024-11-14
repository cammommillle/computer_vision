import javax.swing.*;
import java.awt.*;

public class CanvasPanel extends JPanel {
    private double scale = 10.0; // Масштабирование
    private int gridSize = 10;  // Размер клетки в сетке
    private int offsetX = 0, offsetY = 0; // Сдвиг координат

    public CanvasPanel() {
        setPreferredSize(new Dimension(1200, 750));
       // setBackground(Color.WHITE);
    }

    public void clearCanvas() {
        Graphics g = getGraphics();
        if (g != null) {
            g.clearRect(0, 0, getWidth(), getHeight());
            drawGrid(g); // Перерисовываем сетку и оси
        }
    }

    public Graphics2D getCanvasGraphics() {
        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.scale(scale, scale);
        return g2d;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGrid(g); // Рисуем сетку и координатные оси
    }

    // Метод для рисования сетки и осей
    private void drawGrid(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        int width = getWidth();
        int height = getHeight();

        // Рисуем вертикальные и горизонтальные линии сетки
        for (int x = 0; x <= width; x += gridSize * scale) {
            g.drawLine(x, 0, x, height);
        }
        for (int y = 0; y <= height; y += gridSize * scale) {
            g.drawLine(0, y, width, y);
        }

        // Ось X и ось Y проходят вдоль верхнего и левого краев
        g.setColor(Color.BLACK);
        g.drawLine(0, 0, width, 0); // Ось X
        g.drawLine(0, 0, 0, height); // Ось Y

        // Подписи на осях, с учетом текущего масштаба
        for (int x = 0; x <= width; x += gridSize * scale) {
            int scaledX = (int) (x / scale); // Корректируем значение для масштаба
            g.drawString(String.valueOf(scaledX), x + 2, 12);
        }
        for (int y = 0; y <= height; y += gridSize * scale) {
            int scaledY = (int) (y / scale); // Корректируем значение для масштаба
            g.drawString(String.valueOf(scaledY), 2, y + 12);
        }
    }

    // Метод для масштабирования
    public void zoomIn() {
        scale *= 1.2;
        repaint();
    }

    public void zoomOut() {
        scale /= 1.2;
        repaint();
    }

    // Привязка целочисленных координат к сетке
    public Point toGridCoordinates(int x, int y) {
        int gridX = (int) (x / (gridSize * scale));
        int gridY = (int) (y / (gridSize * scale));
        return new Point(gridX, gridY);
    }
}
