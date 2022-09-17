package UI;

import App.AppState;

import java.awt.*;
import java.awt.event.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ChangeListener;

class DatePicker {
    static public boolean isOpen=false;
    int month;
    int year;
    JLabel l = new JLabel("", JLabel.CENTER);
    int day;
    JFrame d;
    JButton[] button = new JButton[49];
    JTextField textFieldRef;
    public DatePicker(JFrame parent, JTextField textField) {
        isOpen=true;
        textFieldRef=textField;
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

        try{
                Date timestamp= format.parse(textField.getText());
                Calendar cal = Calendar.getInstance();
                cal.setTime(timestamp);
                year= cal.get(Calendar.YEAR);
                month=  cal.get(Calendar.MONTH);
                day= cal.get(Calendar.DAY_OF_MONTH);
        }catch (ParseException e){
                year= Calendar.getInstance().get(Calendar.YEAR);
                month= Calendar.getInstance().get(Calendar.MONTH);
                day= Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }


        d=parent;
        d.setTitle(textField.getText());
        d.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent w)
            {
                isOpen=false;
            }
        });

        d.setResizable(false);
        l.setFont(Constants.fonts[0]);
        l.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        l.setPreferredSize(new Dimension(230,40));
        String[] header = { "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat" };


        JButton previous = View.createButton(Constants.appColors[0]);
        previous.setText("<");
        previous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month--;
                displayDate();
            }
        });
        previous.setPreferredSize(new Dimension(40,40));
        JButton veryPrevious = View.createButton(Constants.appColors[0]);
        veryPrevious.setText("<<");
        veryPrevious.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                year--;
                displayDate();
            }
        });
        veryPrevious.setPreferredSize(new Dimension(40,40));


        JButton next = View.createButton(Constants.appColors[0]);
        next.setText(">");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month++;
                displayDate();
            }
        });
        next.setPreferredSize(new Dimension(40,40));
        JButton veryNext = View.createButton(Constants.appColors[0]);
        veryNext.setText(">>");
        veryNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                year++;
                displayDate();
            }
        });
        veryNext.setPreferredSize(new Dimension(40,40));

        JPanel p2 = new JPanel(new FlowLayout());
        p2.add(veryPrevious);
        p2.add(previous);
        p2.add(l);
        p2.add(next);
        p2.add(veryNext);
        p2.setBackground(Constants.appColors[1]);

        JPanel p1 = new JPanel(new GridLayout(7, 7));
        p1.setPreferredSize(new Dimension(430, 430));
        p1.setBackground(Constants.appColors[0]);
        // for (int x = 0; x <button> 6) {
        for (int x = 0; x < button.length; x++) {
            final int selection = x;
            if (x < 7) {
                button[x] = View.createButton(Constants.appColors[0],false);
                button[x].setText(header[x]);
            }

            if (x > 6) {
                button[x] = View.createButton(Constants.appColors[0],false);
            }
            p1.add(button[x]);
        }

        parent.add(p1, BorderLayout.CENTER);
        parent.add(p2, BorderLayout.SOUTH);
        parent.pack();
        displayDate();
        parent.setVisible(true);
    }

    public void displayDate() {
        for (int x = 7; x < button.length; x++)
            button[x].setText("");
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "MMMM yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, 1);
        int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        for (int x = 7; x < button.length; x++) {
            button[x].setBackground(Constants.appColors[1]);
            for( ActionListener al : button[x].getActionListeners() ) {
                button[x].removeActionListener( al );
            }

            for( ChangeListener al : button[x].getChangeListeners() ) {
                button[x].removeChangeListener( al );
            }
        }
        for (int x = 6 + dayOfWeek, i = 1; i <= daysInMonth; x++, i++){
            final int selection = i;
            button[x].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    isOpen=false;
                    day =  selection;
                    textFieldRef.setText(""+day+"/"+month+"/"+year);

                    d.dispatchEvent(new WindowEvent(d, WindowEvent.WINDOW_CLOSING));
                }
            });
            button[x].setBackground(Constants.appColors[2]);
            View.setupButton(button[x],Constants.appColors[2]);
            button[x].setText("" + i);
        }

        l.setText(sdf.format(cal.getTime()));
    }
}
