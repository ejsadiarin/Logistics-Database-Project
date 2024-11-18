/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Controllers.ScheduleController;

/**
 *
 * @author Rafael
 */
public class NewSchedulingForm extends javax.swing.JDialog implements ActionListener {
    private SchedulingPanel parentPanel;
    private ScheduleController controller;
    /**
     * Creates new form NewSchedulingForm
     */
    public NewSchedulingForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        controller = new ScheduleController();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        datetimeLabel = new javax.swing.JLabel();
        datetimeSpinner = new javax.swing.JSpinner();
        scrollPane = new javax.swing.JScrollPane();
        requestIDTable = new javax.swing.JTable();
        scrollPane1 = new javax.swing.JScrollPane();
        driverIDTable = new javax.swing.JTable();
        scrollPane2 = new javax.swing.JScrollPane();
        vehicleIDTable = new javax.swing.JTable();
        confirmButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        datetimeLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        datetimeLabel.setText("Date and Time Of Delivery");

        datetimeSpinner.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1731893728449L), null, null, java.util.Calendar.HOUR));

        requestIDTable.setModel(new javax.swing.table.DefaultTableModel(
            controller.getRequestID(),
            new String [] {
                "RequestID"
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
        scrollPane.setViewportView(requestIDTable);

        driverIDTable.setModel(new javax.swing.table.DefaultTableModel(
            controller.getAvailableDrivers(),
            new String [] {
                "DriverID"
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
        scrollPane1.setViewportView(driverIDTable);

        vehicleIDTable.setModel(new javax.swing.table.DefaultTableModel(
            controller.getAvailableVehicles(),
            new String [] {
                "VehicleID"
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
        scrollPane2.setViewportView(vehicleIDTable);

        confirmButton.setText("Confirm");
        confirmButton.addActionListener(this);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(datetimeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(datetimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(confirmButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(scrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(12, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(datetimeLabel)
                    .addComponent(datetimeSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addComponent(confirmButton)
                .addContainerGap())
        );

        pack();
    }

    public void setParentPanel(SchedulingPanel panel) {
        this.parentPanel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == confirmButton) {
            boolean success = controller.createRecord((java.util.Date)datetimeSpinner.getValue(),
                            (int)driverIDTable.getValueAt(driverIDTable.getSelectedRow(), 0),
                            (int)vehicleIDTable.getValueAt(vehicleIDTable.getSelectedRow(), 0),
                            (int)requestIDTable.getValueAt(requestIDTable.getSelectedRow(), 0)
            );
            if(success) {
                this.parentPanel.refresh();
                dispose();
            }
        }
    }

    // Variables declaration - do not modify                     
    private javax.swing.JButton confirmButton;
    private javax.swing.JLabel datetimeLabel;
    private javax.swing.JSpinner datetimeSpinner;
    private javax.swing.JTable driverIDTable;
    private javax.swing.JTable requestIDTable;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JScrollPane scrollPane1;
    private javax.swing.JScrollPane scrollPane2;
    private javax.swing.JTable vehicleIDTable;
    // End of variables declaration                   
}
