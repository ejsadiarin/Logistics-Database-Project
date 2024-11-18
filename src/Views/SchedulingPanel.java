package Views;

import Controllers.ScheduleController;

public class SchedulingPanel extends javax.swing.JPanel {
    private javax.swing.JButton newScheduleButton;
    private javax.swing.JTable schedulingTable;
    private javax.swing.JScrollPane tablePane;
    private ScheduleController controller;

    public SchedulingPanel() {
        controller = new ScheduleController();
        initComponents();
    }

    private void initComponents() {

        tablePane = new javax.swing.JScrollPane();
        schedulingTable = new javax.swing.JTable();
        newScheduleButton = new javax.swing.JButton();

        schedulingTable.setModel(new javax.swing.table.DefaultTableModel(
            controller.getScheduleTableData(),
            new String [] {
                "ScheduleID", "Date", "DriverID", "VehicleID", "RequestID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
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
        schedulingTable.getTableHeader().setReorderingAllowed(false);
        tablePane.setViewportView(schedulingTable);

        newScheduleButton.setText("New Schedule");
        newScheduleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newScheduleButtonDriverTableActions(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tablePane, javax.swing.GroupLayout.DEFAULT_SIZE, 718, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(newScheduleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(tablePane, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(newScheduleButton)
                .addContainerGap())
        );
    }// </editor-fold>
    
    public void refresh() {
        removeAll();
        initComponents();
        revalidate();
        repaint();
    }

    private void newScheduleButtonDriverTableActions(java.awt.event.ActionEvent evt) {                                                     
        NewSchedulingForm dialog = new NewSchedulingForm(new javax.swing.JFrame(), true);
        dialog.setParentPanel(this);
        dialog.setVisible(true);
    }                                                                   
}
