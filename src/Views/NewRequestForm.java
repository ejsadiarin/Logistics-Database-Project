package Views;

import Controllers.RequestController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;

public class NewRequestForm extends JDialog implements ActionListener {
    private JButton confirmButton;
    private JTable customerIDTable;
    private JFormattedTextField dateField;
    private JLabel dateLabel;
    private JTextField destinationField;
    private JLabel destinationLabel;
    private JTextField originField;
    private JLabel originLabel;
    private JTextField productField;
    private JLabel productLabel;
    private JScrollPane scrollPane;
    private JTextField weightField;
    private JLabel weightLabel;
    private RequestController controller;
    private RequestPanel parentPanel;

    public NewRequestForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        controller = new RequestController();
        initComponents();
    }
                      
    private void initComponents() {

        scrollPane = new JScrollPane();
        customerIDTable = new JTable();
        dateField = new JFormattedTextField();
        productField = new JTextField();
        originField = new JTextField();
        destinationField = new JTextField();
        weightField = new JTextField();
        dateLabel = new JLabel();
        productLabel = new JLabel();
        originLabel = new JLabel();
        destinationLabel = new JLabel();
        weightLabel = new JLabel();
        confirmButton = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("New Request Record");

        customerIDTable.setModel(new DefaultTableModel(
            controller.getFormTableData(),
            new String [] {
                "CustomerID"
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
        scrollPane.setViewportView(customerIDTable);
        if (customerIDTable.getColumnModel().getColumnCount() > 0) {
            customerIDTable.getColumnModel().getColumn(0).setResizable(false);
        }

        dateField.setFormatterFactory(new DefaultFormatterFactory(new DateFormatter(new SimpleDateFormat("yyyy-MM-dd"))));

        
        dateLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        dateLabel.setText("Requested Delivery Date");
        
        productLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        productLabel.setText("Product");
        
        originLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        originLabel.setText("Origin");
        
        destinationLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        destinationLabel.setText("Destination");

        weightLabel.setFont(new java.awt.Font("sansserif", 1, 12)); // NOI18N
        weightLabel.setText("Load Weight");

        confirmButton.setText("Confirm");
        confirmButton.addActionListener(this);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(dateLabel)
                    .addComponent(dateField, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
                    .addComponent(productLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                    .addComponent(productField, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
                    .addComponent(originLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                    .addComponent(originField, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
                    .addComponent(destinationLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                    .addComponent(weightLabel, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                    .addComponent(weightField, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
                    .addComponent(destinationField, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(confirmButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dateLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dateField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(productLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(productField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(originLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(originField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(destinationLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(destinationField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(weightLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(weightField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(confirmButton)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }
    
    public void setParentPanel(RequestPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == confirmButton) {
            boolean success = controller.createRecord(dateField.getText(),
            productField.getText(),
            originField.getText(),
            destinationField.getText(),
            weightField.getText(),
            (int)customerIDTable.getValueAt(customerIDTable.getSelectedRow(), 0));
            if(success) {
                this.parentPanel.refresh();
                dispose();
            } else {
                JOptionPane.showMessageDialog(
                null,                 // Parent component (null makes it centered on the screen)
                "Error: Cannot create request.",         // Message to display
                "Creation Failed",              // Title of the popup
                JOptionPane.ERROR_MESSAGE  // Type of message (error)
                );
            }
        }
    }                
}
