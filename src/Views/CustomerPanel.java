package Views;

import javax.swing.JPanel;
import Controllers.CustomerController;

public class CustomerPanel extends JPanel {
    private CustomerController controller;

    /**
     * Creates new form CustomerPanel
     */
    public CustomerPanel() {
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

        controller = new CustomerController();
        tablePane = new javax.swing.JScrollPane();
        customerTable = new javax.swing.JTable();
        newRecordButton = new javax.swing.JButton();
        updateRecordButton = new javax.swing.JButton();
        deleteRecordButton = new javax.swing.JButton();

        customerTable.setModel(new javax.swing.table.DefaultTableModel(
            controller.getCustomersTableData(),
            new String [] {
                "CustomerID", "Company Name", "Customer Name", "Company Contact", "Billing Address", "Amount Paid", "Date Paid"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        customerTable.getTableHeader().setReorderingAllowed(false);
        tablePane.setViewportView(customerTable);

        newRecordButton.setText("New Record");
        newRecordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newRecordButtonDriverTableActions(evt);
            }
        });

        updateRecordButton.setText("Update Record");
        updateRecordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateRecordButtonActionPerformed(evt);
            }
        });

        deleteRecordButton.setText("Delete Record");
        deleteRecordButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteRecordButtonActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                        .addComponent(updateRecordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(deleteRecordButton, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(tablePane))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(tablePane, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deleteRecordButton)
                    .addComponent(updateRecordButton)
                    .addComponent(newRecordButton))
                .addContainerGap())
        );
    }// </editor-fold>                        

    public void refresh() {
        removeAll();
        initComponents();
        revalidate();
        repaint();
    }

    private void newRecordButtonDriverTableActions(java.awt.event.ActionEvent evt) {
        NewCustomerForm dialog = new NewCustomerForm(new javax.swing.JFrame(), true);
        dialog.setParentPanel(this);
        dialog.setVisible(true);
    }                                                  

    private void updateRecordButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow >= 0) {
            UpdateCustomerForm dialog = new UpdateCustomerForm(new javax.swing.JFrame(), true);
            dialog.setParentPanel(this);
            dialog.setFields(
                (int) customerTable.getValueAt(selectedRow, 0),
                (String) customerTable.getValueAt(selectedRow, 1),
                (String) customerTable.getValueAt(selectedRow, 2),
                (String) customerTable.getValueAt(selectedRow, 3),
                (String) customerTable.getValueAt(selectedRow, 4),
                String.valueOf(customerTable.getValueAt(selectedRow, 5)),
                (String) customerTable.getValueAt(selectedRow, 6)
            );
            dialog.setVisible(true);
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a record to update.", "No Selection", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteRecordButtonActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow >= 0) {
            int customerID = (int) customerTable.getValueAt(selectedRow, 0);
            controller.deleteRecord(customerID);
            refresh();
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Please select a record to delete.", "No Selection", javax.swing.JOptionPane.WARNING_MESSAGE);
        }
    }


    // Variables declaration - do not modify                     
    private javax.swing.JButton deleteRecordButton;
    private javax.swing.JButton newRecordButton;
    private javax.swing.JScrollPane tablePane;
    private javax.swing.JButton updateRecordButton;
    private javax.swing.JTable customerTable;
    // End of variables declaration                   
}
