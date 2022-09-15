package UI;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.metal.MetalComboBoxButton;
import java.awt.*;

public class ComboBoxFactory {
    public static JComboBox createComboBox(String[] options) {
        // create checkbox
        JComboBox comboBox = new JComboBox(options);
//        comboBox.setEditable(true);
        comboBox.setFont(Constants.font);
        comboBox.setUI(new BasicComboBoxUI(){
            @Override protected JButton createArrowButton() {
                return ButtonFactory.createButton(Constants.appColors[2],"dropdown");
            }
        });
        comboBox.setRenderer(new BasicComboBoxRenderer() {
            private boolean isOpaque=true;

            @Override
            public void setOpaque(boolean isOpaque) {
                isOpaque=isOpaque;
            }

            @Override
            public boolean isOpaque() {
                return isOpaque;
            }

            @Override
            public void setBackground(Color bg) {
                super.setBackground(bg); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public Color getBackground() {
                return Constants.appColors[1];
            }
        });
//        comboBox.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        comboBox.setPreferredSize(new Dimension(230, 35));
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Constants.appColors[1],4,true),
                BorderFactory.createEmptyBorder(2, 10, 2, 5)));
        return comboBox;
    }
}
