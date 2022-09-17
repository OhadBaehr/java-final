package UI;

import App.AppController;
import App.AppState;
import jdk.jshell.execution.Util;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.*;

public class View {

    public static ImageIcon createIcon(String icon){
        return  new ImageIcon(System.getProperty("user.dir")+"/images/"+ icon + ".png");
    }

    public static JFrame createFrame(String title) {
        JFrame frame = new JFrame(title);
        frame.setLocationRelativeTo(ViewManager.f);  // *** this will center your app ***
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent w)
            {
                frame.dispose();
            }
        });
        return frame;
    }
    public static JComboBox createComboBox(Utils.Item [] options) {
        // create checkbox
        JComboBox comboBox = new JComboBox(options);
        setupComboBox(comboBox);
        return comboBox;
    }
    public static JComboBox createComboBox() {
        // create checkbox
        JComboBox comboBox = new JComboBox();
        setupComboBox(comboBox);
        return comboBox;
    }
    private static void  setupComboBox(JComboBox comboBox){
        comboBox.setEditable(false);
        comboBox.setFont(Constants.fonts[0]);
        comboBox.setUI(new BasicComboBoxUI(){
            @Override protected JButton createArrowButton() {
                return View.createButton(Constants.appColors[2],"dropdown");
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
//            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                JComponent comp = (JComponent) new DefaultListCellRenderer().getListCellRendererComponent(list,value, index, isSelected, cellHasFocus);
                comp.setBorder(new EmptyBorder(5, 15, 5, 0));
                return comp;
            }

        });
//        comboBox.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        comboBox.setPreferredSize(new Dimension(230, 35));
//        comboBox.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(Constants.appColors[1],4,true),
//                BorderFactory.createEmptyBorder(2, 10, 2, 5)));
    }
    public static JButton createButton() {
        JButton b=new JButton();
        setupButton(b,null);
        return b;
    }
    public static JButton createButton(Color color) {
        JButton b=new JButton();
        setupButton(b,color);
        b.setBackground(color);
        return b;
    }

    public static JButton createButton(Color color,boolean clickable) {
        JButton b=new JButton();
        setupButton(b,clickable?color:null);
        b.setBackground(color);
        return b;
    }
    public static JButton createButton(String icon){
        JButton b=new JButton(View.createIcon(icon));
        setupButton(b,null);
        return b;
    }
    public static JButton createButton(Color color, String icon){
        JButton b=new JButton(View.createIcon(icon));
        setupButton(b,color);
        b.setBackground(color);
        return b;
    }

    public static void setupButton(JButton b,Color color){
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setFont(Constants.fonts[0]);
//        b.setFont(new Font("Tahoma", Font.BOLD, 12));
//        b.setText("BLAAA");
        b.setOpaque(true);
        b.setBorder(null);
        if(color!=null){
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
}
