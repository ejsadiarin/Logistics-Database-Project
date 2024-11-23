package Views;

import Controllers.ReportController;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

public class ReportPanel extends JPanel implements ActionListener {
    private JComboBox<String> reportTypeComboBox;
    private JFormattedTextField yearField;
    private JFormattedTextField monthField;
    private JTextField idField;
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
            "Driver Completed Trips Report by Year", 
            "Driver Completed Trips Report by Month", 
            "Vehicle Completed Trips Report by Year", 
            "Vehicle Completed Trips Report by Month",
            "Completed Trips by Driver ID",
            "Completed Trips by Vehicle ID"
        });

        MaskFormatter monthMask, yearMask;
        try {
            monthMask = new MaskFormatter("##");
            yearMask = new MaskFormatter("####");
            yearField = new JFormattedTextField(yearMask);
            yearField.setColumns(4);
            monthField = new JFormattedTextField(monthMask);
            monthField.setColumns(2);
        } catch (ParseException e) {
            e.printStackTrace();
            yearField = new JFormattedTextField(4);
            monthField = new JFormattedTextField(2);
        }

        idField = new JTextField(10);

        controlPanel.add(reportTypeComboBox);
        
        controlPanel.add(new JLabel("Year:"));
        controlPanel.add(yearField);

        controlPanel.add(new JLabel("Month:"));
        controlPanel.add(monthField);

        controlPanel.add(new JLabel("ID:"));
        controlPanel.add(idField);

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
        String id = idField.getText();

        List<Object[]> data = null;
        String[] columnNames = null;
        if (reportType.equals("Sales Report by Year")) {
            data = reportController.getSalesReportByYear(year);
            columnNames = new String[] {"SalesDate", "TotalSales", "SalesCount", "TotalProfit"};
        } else if (reportType.equals("Sales Report by Month")) {
            data = reportController.getSalesReportByMonth(year, month);
            columnNames = new String[] {"SalesDate", "TotalSales", "SalesCount", "TotalProfit"};
        } else if (reportType.equals("Driver Completed Trips Report by Year")) {
            data = reportController.getDriverCompletedTripsReportByYear(year, id);
            columnNames = new String[] {"DriverID", "DriverName", "Year", "TotalTrips", "TotalKilometers"};
        } else if (reportType.equals("Driver Completed Trips Report by Month")) {
            data = reportController.getDriverCompletedTripsReportByMonth(year, month, id);
            columnNames = new String[] {"DriverID", "DriverName", "Month", "TotalTrips", "TotalKilometers"};
        } else if (reportType.equals("Vehicle Completed Trips Report by Year")) {
            data = reportController.getVehicleCompletedTripsReportByYear(year, id);
            columnNames = new String[] {"VehicleID", "VehicleName", "Year", "TotalTrips", "TotalKilometers"};
        } else if (reportType.equals("Vehicle Completed Trips Report by Month")) {
            data = reportController.getVehicleCompletedTripsReportByMonth(year, month, id);
            columnNames = new String[] {"VehicleID", "VehicleName", "Month", "TotalTrips", "TotalKilometers"};
        } else if (reportType.equals("Completed Trips by Driver ID")) {
            data = reportController.getCompletedTripsByDriver(id);
            columnNames = new String[] {"LogisticsID", "Date", "Distance", "Status"};
        } else if (reportType.equals("Completed Trips by Vehicle ID")) {
            data = reportController.getCompletedTripsByVehicle(id);
            columnNames = new String[] {"LogisticsID", "Date", "Distance", "Status"};
        }

        if (data != null && columnNames != null) {
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            for (Object[] row : data) {
                model.addRow(row);
            }
            addSummaryRow(model, columnNames, id, reportType, year, month);
            reportTable.setModel(model);
        } else {
            JOptionPane.showMessageDialog(this, "Error generating report", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addSummaryRow(DefaultTableModel model, String[] columnNames, String id, String reportType, String year, String month) {
        Object[] summaryData = null;
        if (reportType.equals("Sales Report by Year")) {
            summaryData = reportController.getSalesSummaryByYear(year);
        } else if (reportType.equals("Sales Report by Month")) {
            summaryData = reportController.getSalesSummaryByMonth(year, month);
        } else if (reportType.equals("Driver Completed Trips Report by Year")) {
            summaryData = reportController.getDriverSummaryYear(id);
        } else if (reportType.equals("Driver Completed Trips Report by Month")) {
            summaryData = reportController.getDriverSummaryMonth(id, year, month);
        } else if (reportType.equals("Vehicle Completed Trips Report by Year")) {
            summaryData = reportController.getVehicleSummaryYear(id);
        } else if (reportType.equals("Vehicle Completed Trips Report by Month")) {
            summaryData = reportController.getVehicleSummaryMonth(id, year, month);
        } else if (reportType.equals("Completed Trips by Driver ID")) {
            summaryData = reportController.getDriverSummaryYear(id);
        } else if (reportType.equals("Completed Trips by Vehicle ID")) {
            summaryData = reportController.getVehicleSummaryYear(id);
        }

        Object[] summaryRow = new Object[columnNames.length];
        for (int j = 0; j < columnNames.length; j++) {
            if (j == 0) {
                summaryRow[j] = "Total";
            } else if (columnNames[j].equalsIgnoreCase("TotalSales")) {
                summaryRow[j] = summaryData != null ? summaryData[0] : 0;
            } else if (columnNames[j].equalsIgnoreCase("SalesCount")) {
                summaryRow[j] = summaryData != null ? summaryData[1] : 0;
            } else if (columnNames[j].equalsIgnoreCase("TotalProfit")) {
                summaryRow[j] = summaryData != null ? summaryData[2] : 0;
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
