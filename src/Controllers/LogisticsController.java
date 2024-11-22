package Controllers;

import java.sql.SQLException;
import java.util.List;

import Models.Logistics;
import Services.LogisticsDAO;
import Services.ScheduleDAO;

public class LogisticsController {
    private final LogisticsDAO dao;
    private final ScheduleDAO schedDAO;
    
    public LogisticsController() {
        dao = new LogisticsDAO();
        schedDAO = new ScheduleDAO();
    }

    public Object[][] getLogisticsTableData() {
        List<Logistics> data;
        int dataRows;
        try {
            data = dao.getAllLogistics();
            dataRows = data.size();
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        Object[][] tableData = new Object[dataRows][5];
        
        for(int i = 0; i < data.size(); i++) {
            tableData[i] = new Object[] {
                data.get(i).getLogisticsID(),
                data.get(i).getDistance(),
                data.get(i).getNormalCost(),
                data.get(i).getStatus(),
                data.get(i).getScheduleID()
            };
        }

        return tableData;
    }

    public boolean createRecord(double distance, String normalCost, int scheduleID ) {
        try {
            double dbNormalCost = Double.parseDouble(normalCost);
            Logistics newRecord = new Logistics(0, distance, dbNormalCost, Logistics.Status.PENDING, scheduleID);
            dao.addLogistics(newRecord);
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    public boolean updateRecord(int logisticsID, double distance, String normalCost, int scheduleID, int statusIndex) {
        try {
            Logistics.Status newStatus = Logistics.Status.values()[statusIndex];
            Logistics currentRecord = dao.getLogistics(logisticsID);

            System.out.println("Received statusIndex: " + statusIndex);
            System.out.println("Mapped to Status: " + Logistics.Status.values()[statusIndex]);

            if (currentRecord == null) {
                throw new SQLException("Logistics record not found.");
            }

            if (currentRecord.getStatus() == Logistics.Status.PENDING && newStatus == Logistics.Status.CANCELLED) {
                Logistics recordToCancel = new Logistics(
                    logisticsID, distance, Double.valueOf(normalCost), newStatus, scheduleID
                );
                dao.cancelLogisticsTransaction(recordToCancel);
            } else {
                Logistics updatedRecord = new Logistics(
                    logisticsID, distance, Double.valueOf(normalCost), newStatus, scheduleID
                );
                dao.updateLogistics(updatedRecord);
            }

            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        }
    }

    public double calculateNormalCost(int scheduleID, double distance) {
        try {
            double[] ratesPerKM = schedDAO.getRateAndEconomy(scheduleID);
            return (distance * ratesPerKM[0]) + (distance * ratesPerKM[1]);
        } catch (Exception e) {
            System.err.println(e);
            return -1;
        }
    }

}
