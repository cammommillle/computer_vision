import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class RGBSwatch extends JPanel implements Observer {
    static final int MIN_VALUE = 0;
    static final int MAX_VALUE = 255;
    static Color own_color;
    static ColorPanel colorArea;

    JSlider RComponent;
    JSlider GComponent;
    JSlider BComponent;
    JTextField red_value;
    JTextField green_value;
    JTextField blue_value;

    private boolean updatingFromSlider = false;
    private boolean updatingFromTextField = false;

    public RGBSwatch(Color color, ColorPanel colorpanel) {
        super();
        own_color = color;
        colorArea = colorpanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        RComponent = createSlider(color.getRed(), e -> updateColorFromSlider(RComponent));
        GComponent = createSlider(color.getGreen(), e -> updateColorFromSlider(GComponent));
        BComponent = createSlider(color.getBlue(), e -> updateColorFromSlider(BComponent));

        red_value = createTextField(color.getRed(), e -> updateColorFromTextField(red_value));
        green_value = createTextField(color.getGreen(), e -> updateColorFromTextField(green_value));
        blue_value = createTextField(color.getBlue(), e -> updateColorFromTextField(blue_value));

        JButton change_color = new JButton("Change Color");
        change_color.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(null, "Select a color", own_color);
            if (newColor != null) {
                own_color = newColor;
                updateColor();
            } else {
                JOptionPane.showMessageDialog(this, "Цвет не был выбран.");
            }
        });

        add(createRow(change_color));
        add(createRow(new JLabel("RGB:")));
        add(createRow(new JLabel("R"), red_value, RComponent));
        add(createRow(new JLabel("G"), green_value, GComponent));
        add(createRow(new JLabel("B"), blue_value, BComponent));
    }

    private JSlider createSlider(int initialValue, ChangeListener listener) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, MIN_VALUE, MAX_VALUE, initialValue);
        slider.addChangeListener(listener);
        return slider;
    }

    private JTextField createTextField(int initialValue, ActionListener listener) {
        JTextField textField = new JTextField(3);
        textField.setText(String.valueOf(initialValue));
        textField.addActionListener(listener);
        return textField;
    }

    private JPanel createRow(Component... components) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        for (Component component : components) {
            row.add(component);
        }
        return row;
    }

    private void updateColorFromSlider(JSlider slider) {
        updatingFromSlider = true;
        if (slider == RComponent) {
            own_color = new Color(slider.getValue(), own_color.getGreen(), own_color.getBlue());
        } else if (slider == GComponent) {
            own_color = new Color(own_color.getRed(), slider.getValue(), own_color.getBlue());
        } else if (slider == BComponent) {
            own_color = new Color(own_color.getRed(), own_color.getGreen(), slider.getValue());
        }
        updateColor();
        updatingFromSlider = false;
    }

    private void updateColorFromTextField(JTextField textField) {
        updatingFromTextField = true;
        try {
            int value = Integer.parseInt(textField.getText());
            if (value < MIN_VALUE || value > MAX_VALUE) {
                throw new NumberFormatException("Value must be between 0 and 255");
            }
            if (textField == red_value) {
                own_color = new Color(value, own_color.getGreen(), own_color.getBlue());
            } else if (textField == green_value) {
                own_color = new Color(own_color.getRed(), value, own_color.getBlue());
            } else if (textField == blue_value) {
                own_color = new Color(own_color.getRed(), own_color.getGreen(), value);
            }

            if (!updatingFromSlider) {
                RComponent.setValue(own_color.getRed());
                GComponent.setValue(own_color.getGreen());
                BComponent.setValue(own_color.getBlue());
            }
            updateColor();
        } catch (NumberFormatException e) {
            if (textField == red_value) {
                textField.setText(String.valueOf(own_color.getRed()));
            } else if (textField == green_value) {
                textField.setText(String.valueOf(own_color.getGreen()));
            } else if (textField == blue_value) {
                textField.setText(String.valueOf(own_color.getBlue()));
            }
            JOptionPane.showMessageDialog(this, "Please enter a value between 0 and 255", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
        updatingFromTextField = false;
    }

    @Override
    public void update(Color color) {
        own_color = color;
        colorArea.removeObserver(this);
        RComponent.setValue(color.getRed());
        GComponent.setValue(color.getGreen());
        BComponent.setValue(color.getBlue());
        red_value.setText(String.valueOf(color.getRed()));
        green_value.setText(String.valueOf(color.getGreen()));
        blue_value.setText(String.valueOf(color.getBlue()));
        colorArea.registerObserver(this);
    }

    public void updateColor() {
        colorArea.removeObserver(this);
        colorArea.setColor(own_color);
        colorArea.repaint();
        if (!updatingFromTextField) {
            red_value.setText(String.valueOf(own_color.getRed()));
            green_value.setText(String.valueOf(own_color.getGreen()));
            blue_value.setText(String.valueOf(own_color.getBlue()));
        }
        colorArea.registerObserver(this);
    }
}
