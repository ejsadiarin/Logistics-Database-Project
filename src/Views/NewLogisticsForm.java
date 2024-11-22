package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Controllers.LogisticsController;
import Controllers.ScheduleController;

public class NewLogisticsForm extends javax.swing.JDialog implements ActionListener {
    private LogisticsController logisticsController;
    private ScheduleController scheduleController;
    private LogisticsPanel parentPanel;

    /**
     * Creates new form NewLogisticsForm
     */
    public NewLogisticsForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.logisticsController = new LogisticsController();
        this.scheduleController = new ScheduleController();
        initComponents();
    }

    private void setNormalCost(double cost) {
        normalcostField.setText(String.valueOf(cost));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        schedulingIDTable = new javax.swing.JTable();
        distanceLabel = new javax.swing.JLabel();
        distanceField = new javax.swing.JFormattedTextField();
        normalcostLabel = new javax.swing.JLabel();
        normalcostField = new javax.swing.JTextField();
        confirmButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Logistics Record");

        schedulingIDTable.setModel(new javax.swing.table.DefaultTableModel(
            scheduleController.getScheduleTableID(),
            // scheduleController.getScheduleTableData -- filter to display only ids
            new String [] {
                "SchedulingID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollPane.setViewportView(schedulingIDTable);

        distanceLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        distanceLabel.setText("Distance (km)");

        normalcostLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        normalcostLabel.setText("Calculated Normal Cost");

        normalcostField.setEditable(false);

        distanceField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        // Add PropertyChangeListener to listen for changes in the formatted value
        distanceField.addPropertyChangeListener("value", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                setNormalCost(logisticsController.calculateNormalCost((int)schedulingIDTable.getValueAt(schedulingIDTable.getSelectedRow(), 0), ((Number)distanceField.getValue()).doubleValue()));
            }
        });
        
        confirmButton.setText("Confirm");
        confirmButton.addActionListener(this);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(distanceField)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(distanceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(normalcostField)
                    .addComponent(normalcostLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(confirmButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(distanceLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(distanceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(normalcostLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(normalcostField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(confirmButton))
                    .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>                        

    public void setParentPanel(LogisticsPanel panel) {
        this.parentPanel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == confirmButton) {
            boolean success = logisticsController.createRecord(((Number)distanceField.getValue()).doubleValue(),
                                                                normalcostField.getText(),
                                                                (int)schedulingIDTable.getValueAt(schedulingIDTable.getSelectedRow(), 0));
            if (success) {
                this.parentPanel.refresh();
                dispose();
            }
        }
    }

    // Variables declaration - do not modify
    private javax.swing.JButton confirmButton;
    private javax.swing.JFormattedTextField distanceField;
    private javax.swing.JLabel distanceLabel;
    private javax.swing.JTextField normalcostField;
    private javax.swing.JLabel normalcostLabel;
    private javax.swing.JTable schedulingIDTable;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration                   
}
