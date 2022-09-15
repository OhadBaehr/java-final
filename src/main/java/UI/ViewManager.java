package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.plaf.basic.BasicScrollBarUI;
public class ViewManager {
    public static JFrame f;



    public static void initStyles() {
        System.setProperty("sun.java2d.uiScale", "1");
        UIManager.put("control", Constants.appColors[1]);
        UIManager.put("info", new Color(128, 128, 128));
        UIManager.put("nimbusBase", Constants.appColors[0]);
        UIManager.put("nimbusAlertYellow", new Color(248, 187, 0));
        UIManager.put("nimbusDisabledText", new Color(128, 128, 128));
        UIManager.put("nimbusFocus", new Color(115, 164, 209));
        UIManager.put("nimbusGreen", Constants.appColors[1]);
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
        f.setMinimumSize(new Dimension(700, 700));
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
            f.setVisible(false);
        } else {
            initFrame();
        }
    }

    public static void appView() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                cleanUp();
                System.out.println("MAAAA");

                ///////// date picker
                JButton dateFilterButton = ButtonFactory.createButton("calendar");

                JTextField dateFilterTextField = new JTextField("03/07/2014 - 03/07/2014");
                dateFilterTextField.setEditable(false);
                dateFilterTextField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                dateFilterTextField.setFont(Constants.font);
                dateFilterTextField.setBackground(Constants.appColors[1]);

                JPanel dateFilterPanel = new JPanel();
                dateFilterPanel.add(dateFilterButton);
                dateFilterPanel.add(dateFilterTextField);


                ///////// category name
                JTextField categoryNameTextField = new JTextField("Category Name");
                categoryNameTextField.setPreferredSize(new Dimension(200, 35));
                categoryNameTextField.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 0));
                categoryNameTextField.setEditable(false);
                categoryNameTextField.setFont(Constants.font);
                categoryNameTextField.setBackground(Constants.appColors[1]);
                categoryNameTextField.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                        categoryNameTextField.setEditable(false);
                        categoryNameTextField.setBackground(Constants.appColors[1]);
                        categoryNameTextField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                    }
                });

                JButton editCategoryNameButton = ButtonFactory.createButton("edit");
                editCategoryNameButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
                editCategoryNameButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        categoryNameTextField.setEditable(true);
                        categoryNameTextField.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(Color.white),
                                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
                        categoryNameTextField.requestFocus(true);
                        categoryNameTextField.setBackground(Constants.appColors[0]);
                    }
                });

                JPanel categoryNamePanel = new JPanel();
                categoryNamePanel.add(editCategoryNameButton);
                categoryNamePanel.add(categoryNameTextField);


                ///////// table
                JPanel p = new JPanel();
                p.setLayout(new BorderLayout());
                p.setBackground(Constants.appColors[0]);
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
                        this.trackColor=Constants.appColors[1];
                        this.thumbColor = Constants.appColors[2];
                    }
                });
                p.add(scrollPane);


                ///////// add listings
                JButton addTableRowButton = ButtonFactory.createButton(Constants.appColors[0], "add");
                addTableRowButton.setText("Add Expense");
                addTableRowButton.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 15));
                addTableRowButton.setFont(Constants.font);
                addTableRowButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        ViewManager.addExpenseView();
//                        model.insertRow(0,new Object[]{"", "", "", "", ""});
                    }
                });




                ///////// topnav
                JPanel topnav = new JPanel();
                topnav.setLayout(new BorderLayout());
                topnav.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                topnav.add(dateFilterPanel, BorderLayout.LINE_START);
                topnav.add(addTableRowButton, BorderLayout.LINE_END);



                ///////// categories
                String categories[] = { "Jalpaiguri", "Mumbai", "Noida", "Kolkata", "New Delhi" };
                JComboBox  categoriesComboBox = ComboBoxFactory.createComboBox(categories);



                JPanel header = new JPanel();
                header.setLayout(new BorderLayout());
                header.add(categoryNamePanel, BorderLayout.LINE_START);
                header.add(categoriesComboBox, BorderLayout.LINE_END);
                header.setBorder(BorderFactory.createEmptyBorder(10, 0, 2, 0));
                header.setBackground(Constants.appColors[0]);

                ///////// layout
                JPanel layout = new JPanel();
                layout.setLayout(new GridLayout(2, 3));
                layout.add(header);
                layout.add(topnav);



                ///////// summary
                JButton summaryOpenButton=ButtonFactory.createButton(Constants.appColors[0],"report");
                summaryOpenButton.setText("View Report");
                summaryOpenButton.setFont(Constants.font);
                summaryOpenButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 15));


                ///////// delete category
                JButton deleteCategoryButton=ButtonFactory.createButton(Constants.appColors[0],"x");
                deleteCategoryButton.setText("Delete Category");
                deleteCategoryButton.setFont(Constants.font);
                deleteCategoryButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 15));



                ///////// footer
                JPanel footer = new JPanel();
                footer.setLayout(new BorderLayout());
                footer.add(summaryOpenButton, BorderLayout.LINE_START);
                footer.add(deleteCategoryButton, BorderLayout.LINE_END);
                footer.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

                Container pane = f.getContentPane();
                pane.add(layout, BorderLayout.PAGE_START);
                pane.add(p, BorderLayout.CENTER);
                pane.add(footer, BorderLayout.PAGE_END);

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

    public static void addExpenseView() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                cleanUp();

                JLabel label = new JLabel("Add Expense");
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setFont(Constants.font);

                JPanel header = new JPanel();
                header.setLayout(new BorderLayout());
                header.add(label,BorderLayout.CENTER);
                header.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));






//
                /////// Date
                String now = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
                JTextField dateTextField=new JTextField(now);
                dateTextField.setFont(Constants.font);
                dateTextField.setBackground(Constants.appColors[3]);
                dateTextField.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                dateTextField.setPreferredSize(new Dimension(300,50));
                JLabel dateLabel=new JLabel("Date",IconFactory.createIcon("calendar"),JLabel.CENTER);
                dateLabel.setFont(Constants.font);
                dateLabel.setOpaque(true);
                dateLabel.setBackground(Constants.appColors[1]);
                dateLabel.setPreferredSize(new Dimension(100,46));

                JPanel datePanel=new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
                datePanel.add(dateLabel);
                datePanel.add(dateTextField);
                datePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                datePanel.setBackground(Constants.appColors[0]);



                /////// amount
                JTextField amountTextField=new JTextField("0");
                amountTextField.setFont(Constants.font);
                amountTextField.setBackground(Constants.appColors[3]);
                amountTextField.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                amountTextField.setPreferredSize(new Dimension(300,50));
                JLabel amountLabel=new JLabel("Date",IconFactory.createIcon("transfer"),JLabel.CENTER);
                amountLabel.setFont(Constants.font);
                amountLabel.setOpaque(true);
                amountLabel.setBackground(Constants.appColors[1]);
                amountLabel.setPreferredSize(new Dimension(100,46));

                JPanel amountPanel=new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
                amountPanel.add(amountLabel);
                amountPanel.add(amountTextField);
                amountPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                amountPanel.setBackground(Constants.appColors[0]);



                /////// description
                JTextArea descriptionTextField=new JTextArea("0");
                descriptionTextField.setFont(Constants.font);
                descriptionTextField.setBackground(Constants.appColors[3]);
                descriptionTextField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                JLabel descriptionLabel=new JLabel("Description",IconFactory.createIcon("words"),JLabel.CENTER);
                descriptionLabel.setFont(Constants.font);
                descriptionLabel.setOpaque(true);
                descriptionLabel.setBackground(Constants.appColors[1]);
                descriptionLabel.setPreferredSize(new Dimension(100,46));

                JPanel descriptionPanel=new JPanel(new BorderLayout());
                descriptionPanel.add(descriptionLabel, BorderLayout.PAGE_START);
                descriptionPanel.add(descriptionTextField, BorderLayout.CENTER);
                descriptionPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                descriptionPanel.setBackground(Constants.appColors[0]);



                JPanel form = new JPanel();
                form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
                form.setBackground(Constants.appColors[0]);
                form.add(datePanel);
                form.add(amountPanel);
                form.add(descriptionPanel);

                Container pane = f.getContentPane();
                pane.add(header, BorderLayout.PAGE_START);
                pane.add(form, BorderLayout.CENTER);

                f.setLocationByPlatform(true);
                f.pack();
                f.setVisible(true);
//
//                pane.add(footer, BorderLayout.PAGE_END);
            }
        });
    }
}
