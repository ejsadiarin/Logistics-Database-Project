package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import Controllers.CustomerController;

public class UpdateCustomerForm extends JDialog implements ActionListener {

    private JButton cancelButton;
    private JButton confirmButton;
    private JTextField companyField;
    private JTextField customerField;
    private JTextField contactField;
    private JTextField billingField;
    private JTextField amountField;
    private JTextField dateField;
    private JLabel companyLabel;
    private JLabel customerLabel;
    private JLabel contactLabel;
    private JLabel billingLabel;
    private JLabel amountLabel;
    private JLabel dateLabel;
    private CustomerController controller;
    private CustomerPanel parentPanel;
    private int customerID;

    public UpdateCustomerForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void setParentPanel(CustomerPanel panel) {
        this.parentPanel = panel;
    }

    public void setFields(int customerID, String companyName, String customerName, String contactNumber, String billingAddress, String amountPaid, String datePaid) {
        this.customerID = customerID;
        companyField.setText(companyName);
        customerField.setText(customerName);
        contactField.setText(contactNumber);
        billingField.setText(billingAddress);
        amountField.setText(amountPaid);
        dateField.setText(datePaid);
    }

    private void initComponents() {
        controller = new CustomerController();
        confirmButton = new JButton();
        cancelButton = new JButton();
        companyField = new JTextField();
        customerField = new JTextField();
        contactField = new JTextField();
        billingField = new JTextField();
        amountField = new JTextField();
        dateField = new JTextField();
        companyLabel = new JLabel();
        customerLabel = new JLabel();
        contactLabel = new JLabel();
        billingLabel = new JLabel();
        amountLabel = new JLabel();
        dateLabel = new JLabel();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Update Customer Record");
        setModal(true);
        setResizable(false);

        companyLabel.setText("Company Name:");
        customerLabel.setText("Customer Name:");
        contactLabel.setText("Contact No:");
        billingLabel.setText("Billing Address:");
        amountLabel.setText("Amount Paid:");
        dateLabel.setText("Date Paid (yyyy-MM-dd):");

        confirmButton.setText("Confirm");
        confirmButton.addActionListener(this);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(this);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(confirmButton)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(dateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(dateField))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(amountLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(amountField))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(billingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(billingField))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(contactLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(contactField))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(customerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(customerField))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(companyLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(companyField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(companyLabel)
                    .addComponent(companyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(customerLabel)
                    .addComponent(customerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contactLabel)
                    .addComponent(contactField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(billingLabel)
                    .addComponent(billingField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(amountLabel)
                    .addComponent(amountField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dateLabel)
                    .addComponent(dateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == confirmButton) {
            try {
                String companyName = companyField.getText();
                String customerName = customerField.getText();
                String contactNumber = contactField.getText();
                String billingAddress = billingField.getText();
                double amountPaid = Double.parseDouble(amountField.getText());
                Date datePaid = Date.valueOf(dateField.getText().trim()); // Parse date (yyyy-MM-dd)

                boolean success = controller.updateRecord(customerID, companyName, customerName, contactNumber, billingAddress, String.valueOf(amountPaid), datePaid);

                if (success) {
                    parentPanel.refresh();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update customer.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please check your data and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (event.getSource() == cancelButton) {
            dispose();
        }
    }
}
