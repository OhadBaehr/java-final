package UI;

import App.AppController;
import App.AppState;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class ExpenseTable extends JTable {

    public void removeAllRows(){
        DefaultTableModel model= (DefaultTableModel) getModel();
        model.setRowCount(0);
    }


    public void dateFilter(String dateA,String dateB){
        Object[] expenses=AppController.dateFilteredExpenses(dateA,dateB).toArray();
        replaceRows(Arrays.copyOf(expenses,expenses.length,String[][].class));
    }
    public void replaceRows(Object [][] data){
        removeAllRows();
        DefaultTableModel model= (DefaultTableModel) getModel();
        for(int i=0;i<data.length;i++){
            model.addRow(data[i]);
        }
    }
    public ExpenseTable() {
        super( new DefaultTableModel(AppState.getExpenses(),  new String[] {"", "", "", ""}));

        JTextField dummy = new JTextField();
        dummy.setFont(Constants.fonts[0]);
        dummy.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.white),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        DefaultCellEditor dce = new DefaultCellEditor( dummy );

        DefaultTableCellRenderer r = new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setFont(Constants.fonts[0]);
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return this;
            }
        };
        setRowSelectionAllowed(false);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS );



        for (int i = 0; i < getColumnCount(); i++) {
            TableColumn col = getColumnModel().getColumn(i);
            col.setCellRenderer(r);
            col.setCellEditor(dce);
            switch (i){
                case (3): {
                    col.setMaxWidth(60);
                    col.setPreferredWidth(60);
                    break;
                }
                case (0):
                case (1): {
                    col.setMaxWidth(120);
                    col.setPreferredWidth(120);
                    break;
                }
            }
        }

        setRowHeight(40);
        setFillsViewportHeight(true);
        Action remove = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int modelRow = Integer.valueOf(e.getActionCommand());
                AppController.removeExpense(modelRow);
            }
        };
        ButtonColumn buttonColumn = new ButtonColumn(this, View.createButton(Constants.appColors[0], "remove"), remove, 3);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(Constants.appColors[1]);

        for (int i = 0; i < getModel().getColumnCount(); i++) {
            getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }
    }
}
