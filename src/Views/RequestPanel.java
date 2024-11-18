package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RequestPanel extends JPanel implements ActionListener {
    private JButton newRequestButton;
    private JTable requestTable;
    private JScrollPane tablePane;

    public RequestPanel() {
        initComponents();
    }
                         
    private void initComponents() {
        tablePane = new JScrollPane();
        requestTable = new JTable();
        newRequestButton = new JButton();

        requestTable.setModel(new DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "RequestID", "Requested Date", "Product", "Origin", "Destination", "Load Weight", "CustomerID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        requestTable.getTableHeader().setReorderingAllowed(false);
        tablePane.setViewportView(requestTable);

        newRequestButton.setText("New Delivery Request");
        newRequestButton.addActionListener(this);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(tablePane, GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(newRequestButton, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(tablePane, GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(newRequestButton)
                .addContainerGap())
        );
    }
    
    public void refresh() {
        removeAll();
        initComponents();
        revalidate();
        repaint();
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == newRequestButton) {
            NewRequestForm dialog = new NewRequestForm(new javax.swing.JFrame(), true);
            dialog.setParentPanel(this);
            dialog.setVisible(true);
        }
    }                                        
}
