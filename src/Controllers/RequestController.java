package Controllers;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;

import Services.RequestDAO;
import Models.Request;

public class RequestController {
    private final RequestDAO dao;

    public RequestController() {
        dao = new RequestDAO();
    }

    public Object[][] getFormTableData() {
        List<Request> data;
        int dataRows;
        try {
            data = dao.getAllRequests();
            dataRows = data.size();
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        Object[][] tableData = new Object[dataRows][1];
        
        for(int i = 0; i < data.size(); i++) {
            tableData[i] = new Object[] {
                data.get(i).getCustomerID()
            };
        }

        return tableData;
    }

    public Object[][] getRequestTableData() {
        List<Request> data;
        int dataRows;
        try {
            data = dao.getAllRequests();
            dataRows = data.size();
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        Object[][] tableData = new Object[dataRows][7];
        
        for(int i = 0; i < data.size(); i++) {
            tableData[i] = new Object[] {
                data.get(i).getRequestID(),
                data.get(i).getRequestedDate(),
                data.get(i).getProduct(),
                data.get(i).getOrigin(),
                data.get(i).getDestination(),
                data.get(i).getLoadWeight(),
                data.get(i).getCustomerID()
            };
        }

        return tableData;
    }

    public boolean createRecord(String requestedDate, String product, String origin, String destination, String loadWeight, int customerID) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse(requestedDate);
            java.sql.Date sqlDate = new Date(date.getTime());
            Request newRecord = new Request(0, sqlDate, product, origin, destination, Double.parseDouble(loadWeight), customerID);
            dao.addRequest(newRecord);
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }
}