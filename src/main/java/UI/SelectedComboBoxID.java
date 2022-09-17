import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

public class SelectedComboBoxID {

    private JComboBox combo = new JComboBox();
    private JFrame frame = new JFrame("MyComboEg");
    private JTextField txt = new JTextField(10);
    private JPanel panel = new JPanel();

    public SelectedComboBoxID() {
        combo.addItem(new Item(1, "-"));
        combo.addItem(new Item(2, "None of the above"));
        combo.addItem(new Item(3, "None of the above"));
        combo.addItem(new Item(4, "None of the above"));
        combo.addItem(new Item(5, "None of the above"));
        combo.addItem(new Item(6, "None of the above"));
        combo.addItem(new Item(7, "None of the above"));
        //comboBox.setMaximumRowCount(3);
        combo.setPrototypeDisplayValue(" None of the above ");
        combo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JComboBox comboBox = (JComboBox) e.getSource();
                Item item = (Item) comboBox.getSelectedItem();
                System.out.println(item.getId() + " : " + item.getDescription());
                txt.setText(String.valueOf(combo.getSelectedIndex()));
            }
        });
        //comboBox.setRenderer(new ItemRenderer());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.add(combo);
        panel.add(txt);
        frame.add(panel);
        frame.pack();
        //frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private class ItemRenderer extends BasicComboBoxRenderer {

        private static final long serialVersionUID = 1L;

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value != null) {
                Item item = (Item) value;
                setText(item.getDescription().toUpperCase());
            }
            if (index == -1) {
                Item item = (Item) value;
                setText("" + item.getId());
            }
            return this;
        }
    }

    private class Item {

        private int id;
        private String description;

        public Item(int id, String description) {
            this.id = id;
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return description;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                SelectedComboBoxID selectedComboBoxID = new SelectedComboBoxID();
            }
        });
    }
}