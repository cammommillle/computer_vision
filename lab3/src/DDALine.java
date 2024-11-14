import java.awt.Graphics2D;

public class DDALine {
    public static long drawLine(Graphics2D g, int x0, int y0, int x1, int y1) {
        long startTime = System.nanoTime();
        // Если x0 > x1, меняем местами координаты x0, x1 и y0, y1
        if (x0 > x1) {
            int temp = x0;
            x0 = x1;
            x1 = temp;
            temp = y0;
            y0 = y1;
            y1 = temp;
        }

        // Начальные значения x и y
        float x = x0;
        float y = y0;

        // Разница по осям X и Y
        float dx = x1 - x0;
        float dy = y1 - y0;

        // Находим длину отрезка (максимальное значение между dx и dy)
        float l = Math.max(Math.abs(dx), Math.abs(dy));

        // Рисуем начальную точку
        g.drawRect((int) Math.round(x), (int) Math.round(y), 1, 1);

        // Индекс для цикла
        int i = 0;

        // Рисуем линию
        while (i < l) {
            // Нормализуем шаги по осям X и Y
            x += dx / l;
            y += dy / l;

            // Рисуем пиксель (округляем x и y до целых)
            g.drawRect((int) Math.round(x), (int) Math.round(y), 1, 1);

            // Добавляем шаг
            i++;
        }
        long endTime = System.nanoTime();
        return (endTime - startTime);
    }
}
