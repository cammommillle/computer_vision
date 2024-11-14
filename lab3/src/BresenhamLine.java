import java.awt.Graphics2D;

public class BresenhamLine {
    public static void drawLine(Graphics2D g, int x0, int y0, int x1, int y1) {

        // Определяем, является ли линия крутой (steep)
        boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);

        // Если линия крутая, меняем местами x и y
        if (steep) {
            int temp = x0;
            x0 = y0;
            y0 = temp;
            temp = x1;
            x1 = y1;
            y1 = temp;
        }

        // Если x0 > x1, меняем местами координаты, чтобы рисовать слева направо
        if (x0 > x1) {
            int temp = x0;
            x0 = x1;
            x1 = temp;
            temp = y0;
            y0 = y1;
            y1 = temp;
        }

        // Вычисляем разницу по осям X и Y
        int dx = x1 - x0;
        int dy = Math.abs(y1 - y0);

        // Начальная ошибка
        int error = dx / 2;

        // Направление по Y
        int ystep = (y0 < y1) ? 1 : -1;
        int y = y0;

        // Рисуем линию
        for (int x = x0; x <= x1; x++) {
            // В зависимости от того, крутая ли линия, меняем местами x и y для рисования
            if (steep) {
                g.drawRect(y, x, 1, 1);
            } else {
                g.drawRect(x, y, 1, 1);
            }

            // Обновляем ошибку
            error -= dy;
            if (error < 0) {
                y += ystep;
                error += dx;
            }
        }
    }
}
