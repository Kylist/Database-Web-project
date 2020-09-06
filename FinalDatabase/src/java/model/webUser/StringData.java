package model.webUser;

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

    public String userID = "";
    public String password = "";
    public String fName = "";
    public String lName = "";
    public String typeID = "";
    public String typeName = "";
    public String email = "";   
    public String country = "";
    public String state = "";
    public String avatarURL = "";
    public String offenceNo = "";
    public String isSuspended = "";

    public String errorMsg = "";

    // default constructor leaves all data members with empty string.
    public StringData() {
    }

    public void setNull() {
        this.userID = null;
        this.password = null;
        this.fName = null;
        this.lName = null;
        this.typeID = null;
        this.typeName = null;
        this.email = null;   
        this.country = null;
        this.state = null;
        this.avatarURL = null;
        this.offenceNo = null;
        this.isSuspended = null;
    }
    
    // overloaded constructor sets all data members by extracting from resultSet.
    public StringData(ResultSet results) {
        try {
            this.userID = FormatUtils.formatString(results.getObject("userID"));
            this.password = FormatUtils.formatString(results.getObject("password"));
            this.fName = FormatUtils.formatString(results.getObject("fName"));
            this.lName = FormatUtils.formatString(results.getObject("lName"));
            this.typeID = FormatUtils.formatInteger(results.getObject("user.typeID"));
            this.typeName = FormatUtils.formatString(results.getObject("user_type.typeName"));
            this.email = FormatUtils.formatString(results.getObject("email"));
            this.country = FormatUtils.formatString(results.getObject("country"));
            this.state = FormatUtils.formatString(results.getObject("state"));
            this.avatarURL = FormatUtils.formatString(results.getObject("avatarURL"));
            this.offenceNo = FormatUtils.formatInteger(results.getObject("offenceNo"));
            this.isSuspended = ((boolean) results.getObject("isSuspended"))?"Yes":"No";
            
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.webUser.StringData (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }


    public String toString() {
        return "User Id:" + this.userID
                + ", User Email: " + this.email
                + ", User Password: " + this.password
                + ", User Type: " + this.typeName
                + ", First Name: " + this.fName
                + ", Last Name: " + this.lName
                + ", Country: " + this.country
                + ", State: " + this.state
                + ", Avatar URL: " + this.avatarURL
                + ", Number of offences: " + this.offenceNo
                + ", Is Suspended: " + this.isSuspended;
    }
}
