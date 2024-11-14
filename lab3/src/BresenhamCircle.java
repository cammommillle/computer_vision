import java.awt.Graphics2D;

public class BresenhamCircle {
    public static long drawCircle(Graphics2D g, int xc, int yc, int r) {
        long startTime = System.nanoTime();
        int x = 0, y = r;
        int d = 3 - 2 * r;
        drawCirclePoints(g, xc, yc, x, y);

        while (y >= x) {
            x++;
            if (d > 0) {
                y--;
                d = d + 4 * (x - y) + 10;
            } else {
                d = d + 4 * x + 6;
            }
            drawCirclePoints(g, xc, yc, x, y);
        }
        long endTime = System.nanoTime();
        return (endTime - startTime);
    }

    private static void drawCirclePoints(Graphics2D g, int xc, int yc, int x, int y) {
        g.drawRect(xc + x, yc + y, 1, 1);
        g.drawRect(xc - x, yc + y, 1, 1);
        g.drawRect(xc + x, yc - y, 1, 1);
        g.drawRect(xc - x, yc - y, 1, 1);
        g.drawRect(xc + y, yc + x, 1, 1);
        g.drawRect(xc - y, yc + x, 1, 1);
        g.drawRect(xc + y, yc - x, 1, 1);
        g.drawRect(xc - y, yc - x, 1, 1);
    }
}
