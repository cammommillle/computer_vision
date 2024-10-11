import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class ColorPanel extends JPanel implements Subject {
    private Color color;
    private ArrayList observers;

    public ColorPanel(Color color) {
        this.color = color;
        setPreferredSize(new Dimension(400, 100));
        observers = new ArrayList<>();
    }

    public void color_changed() {
        notifyObservers();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public void setColor(Color color) {
        this.color = color;
        color_changed();
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    @Override
    public void notifyObservers() {
        for (int i = 0 ; i <  observers.size(); i++) {
            Observer o = (Observer) observers.get(i);
            o.update(color);
        }
    }
}

