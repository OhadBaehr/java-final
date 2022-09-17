package UI;

import javax.swing.*;
import java.awt.*;

public class Input extends JPanel{
    public JLabel label;
    public JTextField textField;

    public Input(String placeholder, String description, String icon,Dimension preferedSizeTextField,Dimension preferedSizeLabel){
        super(new FlowLayout(FlowLayout.CENTER, 0, 0));
        textField=new JTextField(placeholder);
        textField.setFont(Constants.fonts[0]);
        textField.setBackground(Constants.appColors[3]);
        textField.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        textField.setPreferredSize(preferedSizeTextField);
        label=new JLabel(description,View.createIcon(icon),JLabel.LEFT);
        label.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
        label.setFont(Constants.fonts[0]);
        label.setOpaque(true);
        label.setBackground(Constants.appColors[1]);
        label.setPreferredSize(preferedSizeLabel);


        add(label);
        add(textField);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Constants.appColors[0],10),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)));
        setBackground(Constants.appColors[0]);
    }

    public Input(String placeholder, String description, String icon,Dimension preferedSizeTextField){
        this(placeholder, description, icon, preferedSizeTextField,new Dimension(140,46));
    }
}
