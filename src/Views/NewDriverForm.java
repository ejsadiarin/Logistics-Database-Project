package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import Controllers.DriverController;

/**
 *
 * @author Rafael
 */
public class NewDriverForm extends JDialog implements ActionListener {
    private JButton cancelButton;
    private JButton confirmButton;
    private JTextField contactField;
    private JLabel contactLabel;
    private JTextField nameField;
    private JLabel nameLabel;
    private JTextField rateField;
    private JLabel rateLabel;
    private DriverController controller;
    private DriverPanel parentPanel;


    public NewDriverForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void setParentPanel(DriverPanel panel) {
        this.parentPanel = panel;
    }

    private void initComponents() {
        controller = new DriverController();
        confirmButton = new JButton();
        nameField = new JTextField();
        contactField = new JTextField();
        rateField = new JTextField();
        nameLabel = new JLabel();
        contactLabel = new JLabel();
        rateLabel = new JLabel();
        cancelButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Driver Record");
        setModal(true);
        setResizable(false);

        confirmButton.setText("Confirm");
        confirmButton.addActionListener(this);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(this);

        nameLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nameLabel.setText("Full Name");

        contactLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        contactLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        contactLabel.setText("Contact No.");

        rateLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        rateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        rateLabel.setText("Rate");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(nameField, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(contactLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(contactField, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(rateLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rateField, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(confirmButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)
                        .addGap(1, 1, 1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(contactField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(contactLabel))
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(rateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(rateLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
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
            boolean success = controller.createRecord(nameField.getText(), rateField.getText(), contactField.getText());
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
