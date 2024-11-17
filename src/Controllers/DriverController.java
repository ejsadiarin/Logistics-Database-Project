package Controllers;

import Models.Driver;
import Services.DriverDAO;
import java.sql.SQLException;
import java.util.List;

public class DriverController {
    private final DriverDAO dao;
    
    public DriverController() {
        dao = new DriverDAO();
    }

    public Object[][] getDriverTableData() {
        List<Driver> data;
        int dataRows;
        try {
            data = dao.getAllDrivers();
            dataRows = data.size();
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        Object[][] tableData = new Object[dataRows][5];
        
        for(int i = 0; i < data.size(); i++) {
            tableData[i] = new Object[] {
                data.get(i).getDriverID(),
                data.get(i).getFullName(),
                data.get(i).getRate(),
                data.get(i).getContactNumber(),
                data.get(i).getStatus()
            };
        }

        return tableData;
    }
}
