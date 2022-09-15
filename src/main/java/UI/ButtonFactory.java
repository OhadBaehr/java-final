package UI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ButtonFactory {
    public static JButton createButton(){
        JButton b=new JButton();
//        b.setBounds(x,100,40, 40);
        setupButton(b);
        return b;
    }

    public static JButton createButton(Color color){
        JButton b=new JButton();
//        b.setBounds(x,100,40, 40);
        setupButton(b,color);
        return b;
    }
    public static JButton createButton(String icon){
        JButton b=new JButton(IconFactory.createIcon(icon));
//        b.setBounds(x,100,40, 40);
        setupButton(b);
        return b;
    }
    public static JButton createButton(Color color, String icon){
        JButton b=new JButton(IconFactory.createIcon(icon));
//        b.setBounds(x,100,40, 40);
        setupButton(b,color);
        return b;
    }
    private static void _setupButton(JButton b){
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
//        b.setFont(new Font("Tahoma", Font.BOLD, 12));
//        b.setText("BLAAA");
        b.setOpaque(true);
        b.setBorder(null);
    }
    private static void setupButton(JButton b){
        _setupButton(b);
    }
    private static void setupButton(JButton b, Color color){
        _setupButton(b);
        b.setBackground(color);
        b.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (b.getModel().isPressed()) {
                    b.setBackground(Utils.brighten(color,-0.1));
                } else if (b.getModel().isRollover()) {
                    b.setBackground(Utils.brighten(color,-0.05));
                } else {
                    b.setBackground(color);
                }
            }
        });
    }
}
