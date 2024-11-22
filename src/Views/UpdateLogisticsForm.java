package Views;

import Controllers.LogisticsController;

public class UpdateLogisticsForm extends javax.swing.JDialog {

    /**
     * Creates new form UpdateLogisticsForm
     */
    public UpdateLogisticsForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.controller = new LogisticsController();
        initComponents();
    }

    public void setFields(int logisticsID, int scheduleID, double distance, double normalCost, String status) {
        this.logisticsID = logisticsID;
        this.scheduleID = scheduleID;
        distanceField.setValue(distance);
        normalcostField.setText(String.valueOf(normalCost));
        statusComboBox.setSelectedItem(status);
    }

    public void setParentPanel(LogisticsPanel panel) {
        this.parentPanel = panel;
    }
                    
    private void initComponents() {

        distanceLabel = new javax.swing.JLabel();
        distanceField = new javax.swing.JFormattedTextField();
        normalcostLabel = new javax.swing.JLabel();
        normalcostField = new javax.swing.JTextField();
        confirmButton = new javax.swing.JButton();
        statusComboBox = new javax.swing.JComboBox<>();
        statusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Logistics Record");

        distanceLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        distanceLabel.setText("Distance (km)");

        normalcostLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        normalcostLabel.setText("Calculated Normal Cost");

        normalcostField.setEditable(false);

        distanceField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));

        confirmButton.setText("Confirm");
        confirmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmButtonActionPerformed(evt);
            }
        });

        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ARRIVED", "IN_TRANSIT", "CANCELLED", "PENDING" }));

        statusLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        statusLabel.setText("Status");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(distanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(confirmButton, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(normalcostField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(statusComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(normalcostLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(distanceField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(statusLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(distanceLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(distanceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(normalcostLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(normalcostField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(confirmButton)
                .addContainerGap())
        );

        pack();
    }                               

    private void confirmButtonActionPerformed(java.awt.event.ActionEvent evt) {
        boolean success = this.controller.updateRecord(logisticsID,
                                                        ((Number)distanceField.getValue()).doubleValue(),
                                                        normalcostField.getText(),
                                                        scheduleID,
                                                        statusComboBox.getSelectedIndex());
        if (success) {
            this.parentPanel.refresh();
            dispose();
        }
    }                                             

    // Variables declaration - do not modify                     
    private javax.swing.JButton confirmButton;
    private javax.swing.JFormattedTextField distanceField;
    private javax.swing.JLabel distanceLabel;
    private javax.swing.JTextField normalcostField;
    private javax.swing.JLabel normalcostLabel;
    private javax.swing.JComboBox<String> statusComboBox;
    private javax.swing.JLabel statusLabel;
    private LogisticsPanel parentPanel;
    private int scheduleID;
    private int logisticsID;
    private LogisticsController controller;
    // End of variables declaration                   
}
