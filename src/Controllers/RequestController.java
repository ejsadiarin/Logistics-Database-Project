package Controllers;

import Models.Customer;
import Models.Request;
import Services.CustomerDAO;
import Services.RequestDAO;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
  
public class RequestController {
    private final RequestDAO dao;
    private final CustomerDAO formDAO;

    public RequestController() {
        dao = new RequestDAO();
        formDAO = new CustomerDAO();
    }

    public Object[][] getFormTableData() {
        List<Customer> data;
        int dataRows;
        try {
            data = formDAO.getAllCustomers();
            dataRows = data.size();
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        }
        Object[][] tableData = new Object[dataRows][1];
        
        for(int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i).getCustomerID());
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

    public boolean createRecord(String requestedDate, String product, String origin, String destination, double loadWeight, int customerID) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse(requestedDate);
            java.sql.Date sqlDate = new Date(date.getTime());
            Request newRecord = new Request(0, sqlDate, product, origin, destination, loadWeight, customerID);
            dao.addRequestWithTransaction(newRecord);
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
