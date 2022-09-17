package UI;

import App.AppController;
import App.AppState;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class ViewManager {
    public static JFrame f;

    public static JComboBox categoriesComboBox;

    public static ExpenseTable table;
    public static JTextField categoryNameTextField;

    public static Input username;

    public static PasswordInput password;

    public static JButton message;

    public static JButton openingActionButton;

    public static JTextField dateFilterTextFieldA;
    public static JTextField dateFilterTextFieldB;

    public static void refreshDates(){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        try{
            dateFilterTextFieldA.setText( new SimpleDateFormat("dd/MM/yyyy").format(Utils.earliestDate(format.parse( dateFilterTextFieldA.getText()),format.parse(AppState.earliestExpenseDate()))));
            dateFilterTextFieldB.setText( new SimpleDateFormat("dd/MM/yyyy").format(Utils.latestDate(format.parse( dateFilterTextFieldB.getText()),format.parse(AppState.latestExpenseDate()))));
        }catch (ParseException e){
        }

    }
    public static void refreshAppView(){

        refreshDates();
        Object [] categoriesObj=AppState.categories.toArray();
        String [] categoriesStr=Arrays.copyOf(categoriesObj, categoriesObj.length, String[].class);

        // remove listeners
        ActionListener[] listeners = categoriesComboBox.getActionListeners();
        ItemListener[] itemListeners= categoriesComboBox.getItemListeners();

        for (int i = 0; i < listeners.length; i++)
            categoriesComboBox.removeActionListener(listeners[i]);

        for (int i = 0; i < itemListeners.length; i++)
            categoriesComboBox.removeItemListener(itemListeners[i]);

        if (categoriesComboBox.getItemCount() > 0)
            categoriesComboBox.removeAllItems();// w  ww .  ja v a2  s . c  o  m
//
        // add contents
        for (int i = 0; i < categoriesStr.length; i++)
            categoriesComboBox.addItem(new Utils.Item(i,categoriesStr[i]));

        categoriesComboBox.getModel().setSelectedItem(AppState.categories.get(AppState.activeCategory));
        // restore listeners
        for (int i = 0; i < listeners.length; i++)
            categoriesComboBox.addActionListener(listeners[i]);

        for (int i = 0; i < itemListeners.length; i++)
            categoriesComboBox.addItemListener(itemListeners[i]);

        categoryNameTextField.setText(AppState.categories.get(AppState.activeCategory));

        table.replaceRows(AppState.getExpenses());
    }
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
        f = new JFrame("Expense Tracker");
        f.setMinimumSize(new Dimension(700, 700));
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

    private static void showFrame(JFrame frame){
        frame.pack();
        frame.setVisible(true);
    }

    private static void showFrame(){
        f.pack();
        f.setVisible(true);
    }

    private static void spawnDatePicker(JTextField textField){
        if(!DatePicker.isOpen){
            JFrame pickerFrame=View.createFrame("Date Picker");
            DatePicker picker= new DatePicker(pickerFrame,textField);
            showFrame(pickerFrame);
        }
    }



    public static void appView() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                cleanUp();

                ///////// date picker
                dateFilterTextFieldA = new JTextField(AppState.earliestExpenseDate());
                dateFilterTextFieldA.setCursor(new Cursor(Cursor.HAND_CURSOR));
                dateFilterTextFieldA.setEditable(false);
                dateFilterTextFieldA.setBorder(BorderFactory.createEmptyBorder(8, 0, 5, 10));
                dateFilterTextFieldA.setFont(Constants.fonts[0]);
                dateFilterTextFieldA.setBackground(Constants.appColors[1]);
                dateFilterTextFieldA.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mouseClicked(MouseEvent e){
                        spawnDatePicker(dateFilterTextFieldA);
                    }
                });
                dateFilterTextFieldA.getDocument().addDocumentListener(new Utils.SimpleDocumentListener() {
                    @Override
                    public void update(DocumentEvent e) {
                        table.dateFilter(dateFilterTextFieldA.getText(),dateFilterTextFieldB.getText());
                    }
                });
                JButton dateFilterButtonA = View.createButton("calendar");
                dateFilterButtonA.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        spawnDatePicker(dateFilterTextFieldA);
                    }
                });

                JLabel hyphen = new JLabel("-");
                hyphen.setFont(Constants.fonts[0]);
                hyphen.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));


                dateFilterTextFieldB = new JTextField( new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()));
                dateFilterTextFieldB.setCursor(new Cursor(Cursor.HAND_CURSOR));
                dateFilterTextFieldB.setEditable(false);
                dateFilterTextFieldB.setBorder(BorderFactory.createEmptyBorder(8, 0, 5, 10));
                dateFilterTextFieldB.setFont(Constants.fonts[0]);
                dateFilterTextFieldB.setBackground(Constants.appColors[1]);
                dateFilterTextFieldB.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mouseClicked(MouseEvent e){
                        spawnDatePicker(dateFilterTextFieldB);
                    }
                });
                dateFilterTextFieldB.getDocument().addDocumentListener(new Utils.SimpleDocumentListener() {
                    @Override
                    public void update(DocumentEvent e) {
                        table.dateFilter(dateFilterTextFieldA.getText(),dateFilterTextFieldB.getText());
                    }
                });
                JButton dateFilterButtonB = View.createButton("calendar");
                dateFilterButtonB.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        spawnDatePicker(dateFilterTextFieldB);
                    }
                });

                JPanel dateFilterPanel = new JPanel();
                dateFilterPanel.add(dateFilterButtonA);
                dateFilterPanel.add(dateFilterTextFieldA);
                dateFilterPanel.add(hyphen);
                dateFilterPanel.add(dateFilterButtonB);
                dateFilterPanel.add(dateFilterTextFieldB);

                // category name
                categoryNameTextField = new JTextField(AppState.categories.get(AppState.activeCategory));
                categoryNameTextField.setPreferredSize(new Dimension(200, 35));
                categoryNameTextField.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 0));
                categoryNameTextField.setEditable(false);
                categoryNameTextField.setFont(Constants.fonts[0]);
                categoryNameTextField.setBackground(Constants.appColors[1]);
                categoryNameTextField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if(e.getKeyCode() == KeyEvent.VK_ENTER){
                            categoryNameTextField.setFocusable(false);
                        }
                    }

                });
                categoryNameTextField.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusLost(FocusEvent e) {
                            categoryNameTextField.setEditable(false);
                            categoryNameTextField.setBackground(Constants.appColors[1]);
                            categoryNameTextField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                            AppController.updateActiveCategory(categoryNameTextField.getText());
                    }
                });

                JButton editCategoryNameButton = View.createButton("edit");
                editCategoryNameButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));
                editCategoryNameButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        categoryNameTextField.setEditable(true);
                        categoryNameTextField.setFocusable(true);
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


                // table
                JPanel p = new JPanel();
                p.setLayout(new BorderLayout());
                p.setBackground(Constants.appColors[0]);
                table = new ExpenseTable();
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
                JButton addTableRowButton = View.createButton(Constants.appColors[0], "add");
                addTableRowButton.setText("Add Expense");
                addTableRowButton.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 15));
                addTableRowButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        ViewManager.spawnExpenseView();
//                        model.insertRow(0,new Object[]{"", "", "", "", ""});
                    }
                });




                ///////// topnav
                JPanel topnav = new JPanel();
                topnav.setLayout(new BorderLayout());
                topnav.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                topnav.add(dateFilterPanel, BorderLayout.LINE_START);
                topnav.add(addTableRowButton, BorderLayout.LINE_END);




                ///////// add category
                JButton addCategoryButton=View.createButton(Constants.appColors[0],"add");
                addCategoryButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                addCategoryButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                       AppController.addCategory();
                    }
                });

                ///////// categories
                Object [] categoriesObj=AppState.categories.toArray();
                String [] categoriesStr=Arrays.copyOf(categoriesObj, categoriesObj.length, String[].class);

                categoriesComboBox = View.createComboBox();
                for(int i=0;i<categoriesStr.length;i++){
                    categoriesComboBox.addItem(new Utils.Item(i,categoriesStr[i]));
                }
                categoriesComboBox.getModel().setSelectedItem(AppState.categories.get(AppState.activeCategory));
                categoriesComboBox.addActionListener (new ActionListener () {
                    public void actionPerformed(ActionEvent e) {
                        JComboBox comboBox = (JComboBox) e.getSource();
                        Utils.Item item = (Utils.Item) comboBox.getSelectedItem();
                        AppController.setActiveCategory(item.getId());
                    }
                });


                JPanel categoriesWrapper=new JPanel();
                categoriesWrapper.setBackground(Constants.appColors[0]);
                categoriesWrapper.add(addCategoryButton);
                categoriesWrapper.add(categoriesComboBox);

                JPanel header = new JPanel();
                header.setLayout(new BorderLayout());
                header.add(categoryNamePanel, BorderLayout.LINE_START);
                header.add(categoriesWrapper, BorderLayout.LINE_END);
                header.setBorder(BorderFactory.createEmptyBorder(10, 0, 2, 0));
                header.setBackground(Constants.appColors[0]);

                ///////// layout
                JPanel layout = new JPanel();
                layout.setLayout(new GridLayout(2, 3));
                layout.add(header);
                layout.add(topnav);



                ///////// summary
                JButton summaryOpenButton=View.createButton(Constants.appColors[0],"report");
//                summaryOpenButton.setText("View Report");
//                summaryOpenButton.setFont(Constants.fonts[0]);
//                summaryOpenButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 15));


                ///////// delete category
                JButton deleteCategoryButton=View.createButton(Constants.appColors[0],"x");
                deleteCategoryButton.setText("Delete Category");
                deleteCategoryButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 15));
                deleteCategoryButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        AppController.removeCurrentCategory();
                    }
                });
                ///////// footer
                JPanel footer = new JPanel();
                footer.setLayout(new BorderLayout());
//                footer.add(summaryOpenButton, BorderLayout.LINE_START);
                footer.add(deleteCategoryButton, BorderLayout.LINE_END);
                footer.setBorder(BorderFactory.createEmptyBorder(20, 10, 12, 10));

                Container pane = f.getContentPane();


                pane.add(layout, BorderLayout.PAGE_START);
                pane.add(p, BorderLayout.CENTER);
                pane.add(footer, BorderLayout.PAGE_END);

                showFrame();
            }
        });
    }


    private static void openingScreenBase(){
        username=new Input("","Username","user",new Dimension(350,50),new Dimension(160,46));
        password=new PasswordInput("","Password","password",new Dimension(350,50),new Dimension(160,46));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.VERTICAL;
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Constants.appColors[0]);

        JLabel credits = new JLabel("Expense Tracker Pro");
        credits.setFont(Constants.fonts[0]);
        credits.setForeground(Constants.appColors[4]);
        JPanel footer = new JPanel();
        footer.add(credits);
        footer.setBackground(Constants.appColors[0]);
        footer.setBorder(BorderFactory.createEmptyBorder(20, 0, 12, 0));

        message = View.createButton();
        message.setFont(Constants.fonts[0]);
        message.setForeground(Constants.appColors[4]);
        message.setBackground(Constants.appColors[0]);

        openingActionButton = View.createButton(Constants.appColors[2]);

        openingActionButton.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));


        JPanel actionWrapper= new JPanel();
        actionWrapper.add(openingActionButton);
        actionWrapper.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        actionWrapper.setBackground(Constants.appColors[0]);
        form.add(message,gbc);
        form.add(username,gbc);
        form.add(password,gbc);
        form.add(actionWrapper,gbc);

        Container pane = f.getContentPane();
        pane.add(form, BorderLayout.CENTER);
        pane.add(footer, BorderLayout.PAGE_END);
    }
    public static void loginView() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                cleanUp();
                openingScreenBase();
                message.setText("Don’t have an account yet? register");
                openingActionButton.setText("Login");
                changeToRegisterEvents();
                showFrame();
            }
        });
    }
    private static void changeToLoginEvents(){
        for( ActionListener al : message.getActionListeners() ) {
            message.removeActionListener( al );
        }

        for( ActionListener al : openingActionButton.getActionListeners() ) {
            openingActionButton.removeActionListener( al );
        }

        message.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                message.setText("Don’t have an account yet? register");
                openingActionButton.setText("Login");
                changeToRegisterEvents();
            }
        });

        openingActionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                //TODO - register
                changeToLoginEvents();
            }
        });
    }

    private static void changeToRegisterEvents() {
        for( ActionListener al : message.getActionListeners() ) {
            message.removeActionListener( al );
        }

        for( ActionListener al : openingActionButton.getActionListeners() ) {
            openingActionButton.removeActionListener( al );
        }

        message.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                message.setText("Already have an account? login");
                openingActionButton.setText("Register");
                changeToLoginEvents();
            }
        });

        openingActionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                //TODO - login (and add if statement, checking username and password)
                appView();
            }
        });
    }

    public static void spawnExpenseView() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame expenseFrame=View.createFrame("New Expense");
                expenseFrame.setPreferredSize(new Dimension(505,600));
                expenseFrame.setResizable(false);
                JPanel header = new JPanel();
                header.setLayout(new BorderLayout());
//                header.add(label,BorderLayout.CENTER);
                header.setBorder(BorderFactory.createEmptyBorder(15, 10, 5, 10));
                header.setBackground(Constants.appColors[0]);



                /////// Date
                String now = Utils.now();
                Input datePanel=new Input(now,"Date","calendar",new Dimension(300,50));
                datePanel.textField.setCursor(new Cursor(Cursor.HAND_CURSOR));
                datePanel.textField.setEditable(false);
                datePanel.textField.addMouseListener(new MouseAdapter(){
                    @Override
                    public void mouseClicked(MouseEvent e){
                        spawnDatePicker(datePanel.textField);
                    }
                });


                /////// amount
                Input amountPanel=new Input("0","Amount","transfer",new Dimension(200,50));

                ///////// currency
                String currencies[] = { "₪","$", "€", "£", "₿", "₹","₽","₺"};
                JComboBox currenciesComboBox = View.createComboBox();
                for(int i=0;i<currencies.length;i++){
                    currenciesComboBox.addItem(new Utils.Item(i,currencies[i]));
                }
                currenciesComboBox.setPreferredSize(new Dimension(100,46));
                amountPanel.add(currenciesComboBox);



                /////// description
                JTextArea descriptionTextField=new JTextArea();
                descriptionTextField.setFont(Constants.fonts[0]);
                descriptionTextField.setBackground(Constants.appColors[3]);
                descriptionTextField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                JLabel descriptionLabel=new JLabel("Description",View.createIcon("words"),JLabel.LEFT);
                descriptionLabel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
                descriptionLabel.setFont(Constants.fonts[0]);
                descriptionLabel.setOpaque(true);
                descriptionLabel.setBackground(Constants.appColors[1]);
                descriptionLabel.setPreferredSize(new Dimension(100,46));

                JPanel descriptionPanel=new JPanel(new BorderLayout());
                descriptionPanel.add(descriptionLabel, BorderLayout.PAGE_START);
                descriptionPanel.add(descriptionTextField, BorderLayout.CENTER);
                descriptionPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                descriptionPanel.setBackground(Constants.appColors[0]);
                descriptionPanel.setMaximumSize( new Dimension(datePanel.getPreferredSize().width,descriptionPanel.getMaximumSize().height) );
                descriptionPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Constants.appColors[0],10),
                        BorderFactory.createEmptyBorder(0, 10, 0, 10)));


                /////// confirm
                JButton confirmButton=View.createButton(Constants.appColors[2],"checked");
                confirmButton.setText("Confirm");
                confirmButton.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                confirmButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        try {
                            Integer.parseInt(amountPanel.textField.getText());
                            AppController.addExpense(new String[]{datePanel.textField.getText(),currenciesComboBox.getSelectedItem().toString() + " " + amountPanel.textField.getText(),descriptionTextField.getText()});
                            expenseFrame.dispatchEvent(new WindowEvent(expenseFrame, WindowEvent.WINDOW_CLOSING));
                        }
                        catch (NumberFormatException e) {
                            //Not an integer
                        }

                    }
                });
                JPanel footer = new JPanel(new BorderLayout());
                footer.add(confirmButton);
                footer.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
                footer.setBackground(Constants.appColors[0]);

                JPanel form = new JPanel();
                form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
                form.setBackground(Constants.appColors[0]);
                form.add(datePanel);
                form.add(amountPanel);
                form.add(descriptionPanel);

                Container pane = expenseFrame.getContentPane();
                pane.add(header, BorderLayout.PAGE_START);
                pane.add(form, BorderLayout.CENTER);
                pane.add(footer, BorderLayout.PAGE_END);

                showFrame(expenseFrame);
//
//                pane.add(footer, BorderLayout.PAGE_END);
            }
        });
    }
}
