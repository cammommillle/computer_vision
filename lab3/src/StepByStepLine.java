import java.awt.Graphics2D;

public class StepByStepLine {
    public static void drawLine(Graphics2D g, int x0, int y0, int x1, int y1) {

        // Если x0 > x1, меняем местами координаты x0, x1 и y0, y1
        if (x0 > x1) {
            int temp = x0;
            x0 = x1;
            x1 = temp;
            temp = y0;
            y0 = y1;
            y1 = temp;
        }

        // Если линия вертикальная (x0 == x1)
        if (x0 == x1) {
            g.drawRect(x0, y0, 1, 1);
            g.drawRect(x1, y1, 1, 1);
        } else {
            // Вычисляем коэффициент наклона k и пересечение с осью Y (b)
            double k = (double)(y1 - y0) / (double)(x1 - x0);
            double b = y0 - k * x0;

            // Начальная координата x и y
            int x = x0;
            int y = y0;

            // Рисуем точку для начальной координаты
            g.drawRect(x, y, 1, 1);

            // Идем от x0 до x1
            while (x < x1) {
                x++; // Увеличиваем x
                y = (int) Math.round(k * x + b); // Вычисляем y для текущего x

                // Рисуем точку
                g.drawRect(x, y, 1, 1); // Рисуем пиксель (1x1)
            }
        }
    }
}
