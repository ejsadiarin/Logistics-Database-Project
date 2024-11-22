package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import Controllers.DriverController;

/**
 *
 * @author Rafael
 */
public class UpdateDriverForm extends JDialog implements ActionListener {
    private JButton cancelButton;
    private JButton confirmButton;
    private JTextField contactField;
    private JLabel contactLabel;
    private JTextField firstnameField;
    private JTextField lastnameField;
    private JLabel firstnameLabel;
    private JLabel lastnameLabel;
    private JFormattedTextField rateField;
    private JLabel rateLabel;
    private DriverController controller;
    private DriverPanel parentPanel;
    private JLabel statusLabel;
    private JComboBox<String> statusComboBox;

    private int driverID;


    public UpdateDriverForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void setParentPanel(DriverPanel panel) {
        this.parentPanel = panel;
    }

    public void setFields(int driverID, String firstname, String lastname, String rate, String contactNumber) {
        this.driverID = driverID;
        firstnameField.setText(firstname);
        lastnameField.setText(lastname);
        contactField.setText(contactNumber);
        rateField.setValue(Double.parseDouble(rate));
    }

    private void initComponents() {
        controller = new DriverController();
        confirmButton = new JButton();
        firstnameField = new JTextField();
        lastnameField = new JTextField();
        contactField = new JTextField();
        rateField = new JFormattedTextField();
        firstnameLabel = new JLabel();
        lastnameLabel = new JLabel();
        contactLabel = new JLabel();
        rateLabel = new JLabel();
        cancelButton = new JButton();
        statusLabel = new JLabel();
        statusComboBox = new JComboBox<>(new String[] { "AVAILABLE", "IN_TRANSIT", "ON_LEAVE", "UNAVAILABLE" });

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Update Driver Record");
        setModal(true);
        setResizable(false);

        statusLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        statusLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        statusLabel.setText("Status");

        confirmButton.setText("Confirm");
        confirmButton.addActionListener(this);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(this);

        firstnameLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        firstnameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        firstnameLabel.setText("First Name");

        lastnameLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        lastnameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lastnameLabel.setText("Last Name");


        contactLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        contactLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        contactLabel.setText("Contact No.");

        rateLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        rateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        rateLabel.setText("Rate");
        
        rateField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(contactLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(contactField, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(rateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(rateField, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(confirmButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cancelButton)
                            .addGap(1, 1, 1))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(statusComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(firstnameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(firstnameField, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lastnameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lastnameField, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(7, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(firstnameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastnameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastnameLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(contactField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(contactLabel))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(statusLabel))
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(confirmButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }                    

    @Override
    // TODO: Add Prompts
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == confirmButton) {
            boolean success = controller.updateRecord(driverID, lastnameField.getText(), firstnameField.getText(), ((Number)rateField.getValue()).doubleValue(), contactField.getText(), statusComboBox.getSelectedIndex());
            if(success) {
                parentPanel.refresh();
                dispose();
            }
        }
        else if (event.getSource() == cancelButton) {
            dispose();
        }
    }               
}
