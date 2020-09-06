
package model.customer;

import dbUtils.FormatUtils;
import java.sql.ResultSet;


/* The purpose of this class is just to "bundle together" all the 
 * character data that the user might type in when they want to 
 * add a new Customer or edit an existing customer.  This String
 * data is "pre-validated" data, meaning they might have typed 
 * in a character string where a number was expected.
 * 
 * There are no getter or setter methods since we are not trying to
 * protect this data in any way.  We want to let the JSP page have
 * free access to put data in or take it out. */
public class StringData {

    public String customerID = "";
    public String customerName = "";

    public String errorMsg = "";

    // default constructor leaves all data members with empty string.
    public StringData() {
    }

    public void setNull() {
        this.customerID = null;
        this.customerName = null;
    }
    
    // overloaded constructor sets all data members by extracting from resultSet.
    public StringData(ResultSet results) {
        try {
            this.customerID = FormatUtils.formatInteger(results.getObject("customerID"));
            this.customerName = FormatUtils.formatString(results.getObject("customerName"));
            
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.customer.StringData (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }


    public String toString() {
        return "customerID: " + this.customerID
                + ", customerName: " + this.customerName;
    }
}

