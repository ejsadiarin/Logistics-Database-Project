package Views;

import javax.swing.ListSelectionModel;

import Controllers.LogisticsController;

public class LogisticsPanel extends javax.swing.JPanel {
    private LogisticsController controller;

    /**
     * Creates new form LogisticsPanel
     */
    public LogisticsPanel() {
        this.controller = new LogisticsController();
        initComponents();
    }
                    
    private void initComponents() {

        tablePane = new javax.swing.JScrollPane();
        logisticsTable = new javax.swing.JTable();
        newRecordButton = new javax.swing.JButton();
        updateRecordButton = new javax.swing.JButton();

        logisticsTable.setModel(new javax.swing.table.DefaultTableModel(
            controller.getLogisticsTableData(),
            new String [] {
                "LogisticsID", "Distance (km)", "Normal Cost", "Status", "ScheduleID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Integer.class
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
        logisticsTable.getTableHeader().setReorderingAllowed(false);
        logisticsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePane.setViewportView(logisticsTable);
        if (logisticsTable.getColumnModel().getColumnCount() > 0) {
            logisticsTable.getColumnModel().getColumn(1).setResizable(false);
        }

        newRecordButton.setText("New Record");
        newRecordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newRecordButton(evt);
            }
        });

        updateRecordButton.setText("Update Record");
        updateRecordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateRecordButton(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(newRecordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(updateRecordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tablePane, javax.swing.GroupLayout.DEFAULT_SIZE, 765, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(tablePane, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateRecordButton)
                    .addComponent(newRecordButton))
                .addContainerGap())
        );
    }                     

    public void refresh() {
        removeAll();
        initComponents();
        revalidate();
        repaint();
    }

    private void newRecordButton(java.awt.event.ActionEvent evt) {
        NewLogisticsForm dialog = new NewLogisticsForm(new javax.swing.JFrame(), true);
        dialog.setParentPanel(this);
        dialog.setVisible(true);
    }                                                  

    private void updateRecordButton(java.awt.event.ActionEvent evt) {
        int selectedRow = logisticsTable.getSelectedRow();
        String selectedRowStatus = logisticsTable.getValueAt(selectedRow, 3).toString();
        if (selectedRow == -1) {
            javax.swing.JOptionPane.showMessageDialog(
                this, 
                "Please select a record to update.", 
                "No Selection", 
                javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }
        if (selectedRowStatus.equals("ARRIVED") || selectedRowStatus.equals("CANCELLED")) {
            javax.swing.JOptionPane.showMessageDialog(
                this, 
                "You cannot update records with status " + selectedRowStatus, 
                "Update not allowed", 
                javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        } 

        UpdateLogisticsForm dialog = new UpdateLogisticsForm(new javax.swing.JFrame(), true);
        dialog.setParentPanel(this);
        dialog.setFields(
            (int)logisticsTable.getValueAt(selectedRow, 0), // logisticsid
            (int)logisticsTable.getValueAt(selectedRow, 4), // scheduleid
            (double)logisticsTable.getValueAt(selectedRow, 1), // distance
            (double)logisticsTable.getValueAt(selectedRow, 2), // normal cost
            (String)logisticsTable.getValueAt(selectedRow, 3).toString() // status
        );
        dialog.setVisible(true);
        
    }                                                     


    // Variables declaration - do not modify                     
    private javax.swing.JTable logisticsTable;
    private javax.swing.JButton newRecordButton;
    private javax.swing.JScrollPane tablePane;
    private javax.swing.JButton updateRecordButton;
    // End of variables declaration                   
}
