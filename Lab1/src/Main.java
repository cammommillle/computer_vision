import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {
    ColorPanel color_panel;
    Color current_color;

    public static void main(String[] args) {
        Main application = new Main("Lab1 Color Converter");
        application.setSize(550, 500);
        application.setVisible(true);
        application.setResizable(false);
        application.setLocation(380,200);
    }

    public Main(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        current_color = new Color(255, 255, 255);
        color_panel = new ColorPanel(current_color);
        setLayout(new FlowLayout());
        JPanel swatchPanel = new JPanel();
        swatchPanel.setLayout(new GridLayout(4, 1));
        RGBSwatch rgbSwatch = new RGBSwatch(current_color, color_panel);
        HSLSwatch hslSwatch = new HSLSwatch(current_color, color_panel);
        CMYKSwatch cmykSwatch = new CMYKSwatch(current_color, color_panel);
        swatchPanel.add(color_panel);
        swatchPanel.add(rgbSwatch);
        swatchPanel.add(hslSwatch);
        swatchPanel.add(cmykSwatch);

        color_panel.registerObserver(rgbSwatch);
        color_panel.registerObserver(cmykSwatch);
        color_panel.registerObserver(hslSwatch);
        getContentPane().add(swatchPanel);
    }
}
