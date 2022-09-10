package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.plaf.basic.BasicScrollBarUI;
public class ViewManager {
    public static JFrame f;

    public static final Color[] appColors = {
            new Color(42, 46, 48),
            new Color(56, 61, 63),
            new Color(77, 81, 84)
    };

    public static void initStyles() {
        System.setProperty("sun.java2d.uiScale", "1");
        UIManager.put("control", appColors[1]);
        UIManager.put("info", new Color(128, 128, 128));
        UIManager.put("nimbusBase", appColors[0]);
        UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
        UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
        UIManager.put("nimbusFocus", new Color(115, 164, 209));
        UIManager.put("nimbusGreen", appColors[1]);
        UIManager.put("nimbusInfoBlue", new Color(66, 139, 221));
        UIManager.put("nimbusLightBackground", new Color(42, 46, 48));
        UIManager.put("nimbusOrange", new Color(191, 98, 4));
        UIManager.put("nimbusRed", new Color(169, 46, 34));
        UIManager.put("nimbusSelectedText", Color.white);
        UIManager.put("nimbusSelectionBackground",  new Color(230, 230, 230));
        UIManager.put("text", new Color(230, 230, 230));
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (javax.swing.UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initFrame() {
        f = new JFrame("Button Example");
//    f.setExtendedState(JFrame.MAXIMIZED_BOTH);
//      f.setUndecorated(true); // <-- the title bar is removed here
        f.setMinimumSize(new Dimension(600, 600));
//        f.setLayout(null);
//        f.setLocationByPlatform(true);
////        f.pack();
//        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private static void cleanUp() {
        if (f != null) {
            f.getContentPane().removeAll();
            f.revalidate();
            f.repaint();
        } else {
            initFrame();
        }
    }

    public static void appView() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                cleanUp();



                ///////// date picker
                JButton dateFilterButton = ButtonFactory.createButton("calendar");

                JTextField dateFilterTextField = new JTextField("03/07/2014 - 03/07/2014");
                dateFilterTextField.setEditable(false);
                dateFilterTextField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                dateFilterTextField.setFont(new Font("Courier", Font.BOLD, 16));
                dateFilterTextField.setBackground(appColors[1]);

                JPanel dateFilterPanel = new JPanel();
                dateFilterPanel.add(dateFilterButton);
                dateFilterPanel.add(dateFilterTextField);




                ///////// category name
                JTextField categoryNameTextField = new JTextField("Category Name");
                categoryNameTextField.setPreferredSize(new Dimension(200, 35));
                categoryNameTextField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                categoryNameTextField.setEditable(false);
                categoryNameTextField.setFont(new Font("Courier", Font.BOLD, 16));
                categoryNameTextField.setBackground(appColors[1]);
                categoryNameTextField.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        categoryNameTextField.setEditable(false);
                        categoryNameTextField.setBackground(appColors[1]);
                        categoryNameTextField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                    }
                });

                JButton editCategoryNameButton = ButtonFactory.createButton("edit");
                editCategoryNameButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        categoryNameTextField.setEditable(true);
                        categoryNameTextField.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(Color.white),
                                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
                        categoryNameTextField.requestFocus(true);
                        categoryNameTextField.setBackground(appColors[0]);
                    }
                });

                JPanel categoryNamePanel = new JPanel();
                categoryNamePanel.add(editCategoryNameButton);
                categoryNamePanel.add(categoryNameTextField);





                ///////// add listings
                JButton addTableRowButton = ButtonFactory.createButton(appColors[0], "add");
                addTableRowButton.setBounds(0, 0, 60, 60);
                addTableRowButton.setPreferredSize(new Dimension(45, 35));




                ///////// topnav
                JPanel topnav = new JPanel();
                topnav.setLayout(new BorderLayout());
                topnav.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                topnav.add(dateFilterPanel, BorderLayout.LINE_START);
                topnav.add(categoryNamePanel, BorderLayout.CENTER);
                topnav.add(addTableRowButton, BorderLayout.LINE_END);




                ///////// table
                JPanel p = new JPanel();
                p.setLayout(new BorderLayout());

                p.setBackground(appColors[0]);
                JTable table = TableFactory.createTable();
                JScrollPane scrollPane = new JScrollPane(table);

                scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
                    @Override
                    protected JButton createDecreaseButton(int orientation) {
                        return  new JButton(){
                            @Override
                            public Dimension getPreferredSize() {
                                return new Dimension(0, 0);
                            }
                        };
                    }

                    @Override
                    protected JButton createIncreaseButton(int orientation) {
                        return  new JButton(){
                            @Override
                            public Dimension getPreferredSize() {
                                return new Dimension(0, 0);
                            }
                        };
                    }
                    @Override
                    protected void configureScrollBarColors() {
                        this.trackColor=appColors[1];
                        this.thumbColor = appColors[2];
                    }
                });
                p.add(scrollPane);



                ///////// layout
                JPanel layout = new JPanel();
                layout.setLayout(new GridLayout(2, 3));
                JButton button = new JButton("Button 1 (PAGE_START)");
                layout.add(button);
                layout.add(topnav);



                ///////// summary
                JLabel sum=new JLabel("$300");


                Container pane = f.getContentPane();
                pane.add(layout, BorderLayout.PAGE_START);
                pane.add(p, BorderLayout.CENTER);
                pane.add(sum, BorderLayout.PAGE_END);

                f.setLocationByPlatform(true);
                f.pack();
                f.setVisible(true);
            }
        });
    }

    public static void loginView() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                cleanUp();
                final JLabel label = new JLabel();
                label.setBounds(20, 150, 200, 50);
                final JPasswordField value = new JPasswordField();
                value.setBounds(100, 75, 100, 30);
                JLabel l1 = new JLabel("Username:");
                l1.setBounds(20, 20, 80, 30);
                JLabel l2 = new JLabel("Password:");
                l2.setBounds(20, 75, 80, 30);
                JButton b = new JButton("Login");
                b.setBounds(100, 120, 80, 30);
                final JTextField text = new JTextField();
                text.setBounds(100, 20, 100, 30);
                f.add(value);
                f.add(l1);
                f.add(label);
                f.add(l2);
                f.add(b);
                f.add(text);
                b.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String data = "Username " + text.getText();
                        data += ", Password: "
                                + new String(value.getPassword());
                        label.setText(data);
                    }
                });
            }
        });
    }
}
