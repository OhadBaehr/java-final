package UI;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class TableFactory {
    public static JTable createTable() {
        JButton removeButton = ButtonFactory.createButton("remove");

        String[] columnNames = {"", "", "", "", ""};
        Object[][] data =
                {
                        {"1", "$300", "3/7/2014", "Had to euthanize my hamster", ""},
                        {"1", "$300", "3/7/2014", "Had to euthanize my hamster", ""},
                        {"1", "$300", "3/7/2014", "Had to euthanize my hamster", ""},
                        {"1", "$300", "3/7/2014", "Had to euthanize my hamster", ""},
                        {"1", "$300", "3/7/2014", "Had to euthanize my hamster", ""},
                        {"1", "$300", "3/7/2014", "Had to euthanize my hamster", ""},
                        {"1", "$300", "3/7/2014", "Had to euthanize my hamster", ""},
                        {"1", "$300", "3/7/2014", "Had to euthanize my hamster", ""},
                        {"1", "$300", "3/7/2014", "Had to euthanize my hamster", ""},
                        {"1", "$300", "3/7/2014", "Had to euthanize my hamster", ""},
                        {"1", "$300", "3/7/2014", "Had to euthanize my hamster", ""},
                        {"1", "$300", "3/7/2014", "Had to euthanize my hamster", ""},
                        {"1", "$300", "3/7/2014", "Had to euthanize my hamster", ""},
                        {"1", "$300", "3/7/2014", "Had to euthanize my hamster", ""},
                        {"1", "$300", "3/7/2014", "Had to euthanize my hamster", ""}
                };
        Action remove = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable) e.getSource();
                int modelRow = Integer.valueOf(e.getActionCommand());
                ((DefaultTableModel) table.getModel()).removeRow(modelRow);
            }
        };
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        JTextField dummy = new JTextField();
        dummy.setFont(new Font("Courier", Font.BOLD, 16));
        dummy.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.white),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        DefaultCellEditor dce = new DefaultCellEditor( dummy );

        DefaultTableCellRenderer r = new DefaultTableCellRenderer() {
            Font font = new Font("Courier", Font.BOLD, 16);

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setFont(font);
                setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                return this;
            }
        };
        table.setRowSelectionAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS );



        for (int i = 0; i < table.getColumnCount(); i++) {
            TableColumn col = table.getColumnModel().getColumn(i);
            col.setCellRenderer(r);
            col.setCellEditor(dce);
            switch (i){
                case (0):
                case (4): {
                    col.setMaxWidth(60);
                    col.setPreferredWidth(60);
                    break;
                }
                case (1):
                case (2): {
                    col.setMaxWidth(120);
                    col.setPreferredWidth(120);
                    break;
                }
            }
//
        }
        table.setRowHeight(40);
        table.setFillsViewportHeight(true);
        ButtonColumn buttonColumn = new ButtonColumn(table, ButtonFactory.createButton(ViewManager.appColors[0], "remove"), remove, 4);
        buttonColumn.setMnemonic(KeyEvent.VK_D);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setBackground(ViewManager.appColors[1]);

        for (int i = 0; i < table.getModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        return table;
    }
}
