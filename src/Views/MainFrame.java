package Views;

import javax.swing.JPanel;

import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MainFrame extends javax.swing.JFrame implements java.awt.event.ActionListener{
    private javax.swing.JPanel MainPanel;
    private javax.swing.JPanel SidePanel;
    private javax.swing.JPanel content = new JPanel();
    private javax.swing.JButton selectCustomer;
    private javax.swing.JButton selectDriver;
    private javax.swing.JButton selectLogistics;
    private javax.swing.JButton selectRequest;
    private javax.swing.JButton selectScheduling;
    private javax.swing.JButton selectVehicle;
    private javax.swing.JButton viewReports;
    private ReportPanel reportPanel;
    private ScheduledExecutorService scheduler;
    
    public MainFrame() {
        initComponents();
        startCronJobs(); // Start scheduled tasks
    }

    private void initComponents() {

        SidePanel = new javax.swing.JPanel();
        selectDriver = new javax.swing.JButton();
        selectVehicle = new javax.swing.JButton();
        selectCustomer = new javax.swing.JButton();
        selectRequest = new javax.swing.JButton();
        selectScheduling = new javax.swing.JButton();
        selectLogistics = new javax.swing.JButton();
        viewReports = new javax.swing.JButton();
        MainPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Logistics Database Management System");
        setMinimumSize(new java.awt.Dimension(1080, 720));
        setPreferredSize(new java.awt.Dimension(1080, 720));
        setSize(new java.awt.Dimension(1080, 720));

        SidePanel.setMaximumSize(new java.awt.Dimension(100, 400));
        SidePanel.setMinimumSize(new java.awt.Dimension(100, 400));
        SidePanel.setPreferredSize(new java.awt.Dimension(100, 400));
        SidePanel.setLayout(new java.awt.GridLayout(7, 1, 0, 10));

        selectDriver.setText("<html>Driver<p>Records</html>");
        selectDriver.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        selectDriver.addActionListener(this);
        SidePanel.add(selectDriver);

        selectVehicle.setText("<html>Vehicle<br>Records</html>");
        selectVehicle.addActionListener(this);
        SidePanel.add(selectVehicle);

        selectCustomer.setText("<html>Customer<br>Records</html>");
        selectCustomer.addActionListener(this);
        SidePanel.add(selectCustomer);

        selectRequest.setText("<html>Request<br>Records</html>");
        selectRequest.addActionListener(this);
        SidePanel.add(selectRequest);

        selectScheduling.setText("<html>Scheduling<br>Records</html>");
        selectScheduling.addActionListener(this);
        SidePanel.add(selectScheduling);

        selectLogistics.setText("<html>Logistics<br>Records</html>");
        selectLogistics.addActionListener(this);
        SidePanel.add(selectLogistics);

        viewReports.setText("<html>View<br>Reports</html>");
        viewReports.addActionListener(this);
        SidePanel.add(viewReports);

        MainPanel.setMinimumSize(new java.awt.Dimension(600, 472));
        MainPanel.setPreferredSize(new java.awt.Dimension(600, 472));
        MainPanel.setLayout(new java.awt.GridLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(SidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SidePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        pack();
    }

    private void startCronJobs() {
        scheduler = Executors.newScheduledThreadPool(2);

        // vehicle maintenance job
        scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("Running vehicle maintenance job...");
                Services.Cronjobs.checkAndUpdateVehicleMaintenance();
            } catch (SQLException e) {
                System.err.println("Error during vehicle maintenance job:");
                e.printStackTrace();
            }
        }, 0, 5, TimeUnit.SECONDS);

        // auto in_transit job
        scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("Running auto-in-transit job...");
                Services.Cronjobs.autoInTransit();
            } catch (SQLException e) {
                System.err.println("Error during auto in-transit job:");
                e.printStackTrace();
            }
        }, 0, 10, TimeUnit.SECONDS);
    }





    @Override
    public void dispose() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
        super.dispose();
    }

    public void setMainPanel(JPanel panel) {
        MainPanel.removeAll();
        MainPanel.add(panel);
        MainPanel.revalidate();
        MainPanel.repaint();
    }
    
    @Override
    public void actionPerformed(java.awt.event.ActionEvent event) {
        if (event.getSource() == selectDriver) {
            setMainPanel(new DriverPanel());
        }
        else if (event.getSource() == selectVehicle) {
            setMainPanel(new VehiclePanel());
        }
        else if (event.getSource() == selectCustomer) {
            setMainPanel(new CustomerPanel());
        }
        else if (event.getSource() == selectRequest) {
            setMainPanel(new RequestPanel());
        }
        else if (event.getSource() == selectScheduling) {
            setMainPanel(new SchedulingPanel());
        }
        else if (event.getSource() == selectLogistics) {
            setMainPanel(new LogisticsPanel());
        }
        else if (event.getSource() == viewReports) {
            setMainPanel(new ReportPanel());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }          
}
