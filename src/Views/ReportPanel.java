package Views;

import Controllers.ReportController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ReportPanel extends JPanel implements ActionListener {
    private JComboBox<String> reportTypeComboBox;
    private JTextField yearField;
    private JTextField monthField;
    private JButton generateButton;
    private JTable reportTable;
    private JScrollPane scrollPane;
    private ReportController reportController;

    public ReportPanel() {
        reportController = new ReportController();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        reportTypeComboBox = new JComboBox<>(new String[] {
            "Sales Report by Year", 
            "Sales Report by Month", 
            "Driver Completed Trips Report", 
            "Vehicle Completed Trips Report"
        });
        controlPanel.add(reportTypeComboBox);

        controlPanel.add(new JLabel("Year:"));
        yearField = new JTextField(4);
        controlPanel.add(yearField);

        controlPanel.add(new JLabel("Month:"));
        monthField = new JTextField(2);
        controlPanel.add(monthField);

        generateButton = new JButton("Generate Report");
        generateButton.addActionListener(this);
        controlPanel.add(generateButton);

        add(controlPanel, BorderLayout.NORTH);

        reportTable = new JTable();
        scrollPane = new JScrollPane(reportTable);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String reportType = (String) reportTypeComboBox.getSelectedItem();
        String year = yearField.getText();
        String month = monthField.getText();

        List<Object[]> data = null;
        String[] columnNames = null;
        if (reportType.equals("Sales Report by Year")) {
            data = reportController.getSalesReportByYear(year);
            columnNames = new String[] {"SalesDate", "TotalSales", "SalesCount"};
        } else if (reportType.equals("Sales Report by Month")) {
            data = reportController.getSalesReportByMonth(year, month);
            columnNames = new String[] {"SalesDate", "TotalSales", "SalesCount"};
        } else if (reportType.equals("Driver Completed Trips Report")) {
            data = reportController.getDriverCompletedTripsReport(year, month);
            columnNames = new String[] {"DriverID", "DriverName", "Month", "TotalTrips", "TotalKilometers"};
        } else if (reportType.equals("Vehicle Completed Trips Report")) {
            data = reportController.getVehicleCompletedTripsReport(year, month);
            columnNames = new String[] {"VehicleID", "VehicleName", "Month", "TotalTrips", "TotalKilometers"};
        }

        if (data != null && columnNames != null) {
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            for (Object[] row : data) {
                model.addRow(row);
            }
            addSummaryRow(model, columnNames, year, month, reportType);
            reportTable.setModel(model);
        } else {
            JOptionPane.showMessageDialog(this, "Error generating report", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addSummaryRow(DefaultTableModel model, String[] columnNames, String year, String month, String reportType) {
        Object[] summaryData = null;
        if (reportType.equals("Driver Completed Trips Report")) {
            summaryData = reportController.getDriverSummary(year, month);
        } else if (reportType.equals("Vehicle Completed Trips Report")) {
            summaryData = reportController.getVehicleSummary(year, month);
        }

        Object[] summaryRow = new Object[columnNames.length];
        for (int j = 0; j < columnNames.length; j++) {
            if (j == 0) {
                summaryRow[j] = "Total";
            } else if (columnNames[j].equalsIgnoreCase("TotalSales")) {
                summaryRow[j] = null; // Not applicable for driver/vehicle reports
            } else if (columnNames[j].equalsIgnoreCase("SalesCount")) {
                summaryRow[j] = null; // Not applicable for driver/vehicle reports
            } else if (columnNames[j].equalsIgnoreCase("TotalKilometers")) {
                summaryRow[j] = summaryData != null ? summaryData[0] : 0;
            } else if (columnNames[j].equalsIgnoreCase("TotalTrips")) {
                summaryRow[j] = summaryData != null ? summaryData[1] : 0;
            } else {
                summaryRow[j] = null;
            }
        }
        model.addRow(summaryRow);
    }
}
