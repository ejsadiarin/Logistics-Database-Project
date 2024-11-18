package Controllers;

import java.sql.SQLException;
import java.util.List;

import Models.Logistics;
import Services.LogisticsDAO;

public class LogisticsController {
    private final LogisticsDAO dao;
    
    public LogisticsController() {
        dao = new LogisticsDAO();
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

    public boolean createRecord(String distance, String normalCost, int scheduleID ) {
        try {
            double dbDistance = Double.parseDouble(distance); 
            double dbNormalCost = Double.parseDouble(normalCost);
            Logistics newRecord = new Logistics(0, dbDistance, dbNormalCost, Logistics.Status.PENDING, scheduleID);
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

    // public boolean changeLogisticStatus(int statusIndex) {
    //     try {
    //         Logistics newRecord = new Logistics(0, dbDistance, dbNormalCost, Logistics.Status.PENDING, scheduleID);
    //         dao.addLogistics(newRecord);
    //         return true;
    //     } catch (SQLException e) {
    //         System.err.println(e);
    //         return false;
    //     } catch (Exception e) {
    //         System.err.println(e);
    //         return false;
    //     }
    // }


}
