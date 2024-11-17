package Views;

import Controllers.DriverController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class DriverPanel extends JPanel implements ActionListener, ListSelectionListener {
    private JButton deleteRecordButton;
    private JTable driverTable;
    private JButton newRecordButton;
    private JScrollPane tablePane;
    private JButton updateRecordButton;
    private DriverController controller;

    public DriverPanel() {
        initComponents();
    }
    
    private void initComponents() {
        
        controller = new DriverController();
        tablePane = new JScrollPane();
        driverTable = new JTable();
        newRecordButton = new JButton();
        updateRecordButton = new JButton();
        deleteRecordButton = new JButton();

        // Create Table Model and populate with Data
        driverTable.setModel(new DefaultTableModel(
            //TODO: replace with actual table model
            controller.getDriverTableData(),
            new String [] {
                "DriverID", "Full Name", "Rate (cost/km)", "Contact Number", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });

        // Set selection model then add listener
        driverTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListSelectionModel selectionModel = driverTable.getSelectionModel();
        selectionModel.addListSelectionListener(this);

        driverTable.getTableHeader().setReorderingAllowed(false);
        tablePane.setViewportView(driverTable);

        newRecordButton.setText("New Record");
        newRecordButton.addActionListener(this);

        updateRecordButton.setText("Update Record");
        updateRecordButton.addActionListener(this);

        deleteRecordButton.setText("Delete Record");
        deleteRecordButton.addActionListener(this);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(newRecordButton, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(updateRecordButton, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteRecordButton, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE))
                    .addComponent(tablePane, GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(tablePane, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteRecordButton)
                    .addComponent(updateRecordButton)
                    .addComponent(newRecordButton))
                .addContainerGap())
        );
    }                

    @Override
    public void valueChanged(ListSelectionEvent event) {
        // TODO add your handling code here (change of table selection):
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        // TODO: ADD IMPLEMENTATION
        if (event.getSource() == newRecordButton) {
        }
        else if (event.getSource() == updateRecordButton) {
        }
        else if (event.getSource() == deleteRecordButton) {
        }
    }             
}
