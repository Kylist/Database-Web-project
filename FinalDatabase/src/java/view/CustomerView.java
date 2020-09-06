package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.customer.*;

// classes in my project
import dbUtils.*;

public class CustomerView {

    public static StringDataList allCustomersAPI(DbConn dbc) {

        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT customerID, customerName "+
                    "FROM customer " + 
                    "ORDER BY customerID;";  // you always want to order by something, not just random order.
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sdl.add(results);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            StringData sd = new StringData();
            sd.errorMsg = "Exception thrown in CustomerView.allCustomersAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        return sdl;
    }

}