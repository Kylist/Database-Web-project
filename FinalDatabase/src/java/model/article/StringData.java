
package model.article;

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

    public String articleID = "";
    public String articleTitle = "";
    public String author = "";
    public String aDateTime = "";
    public String topic = "";
    public String url = "";

    public String errorMsg = "";

    // default constructor leaves all data members with empty string.
    public StringData() {
    }

    public void setNull() {
        this.articleID = null;
        this.articleTitle = null;
        this.author = null;
        this.aDateTime = null;
        this.topic = null;
        this.url = null;
    }
    
    // overloaded constructor sets all data members by extracting from resultSet.
    public StringData(ResultSet results) {
        try {
            this.articleID = FormatUtils.formatInteger(results.getObject("articleID"));
            this.articleTitle = FormatUtils.formatString(results.getObject("articleTitle"));
            this.author = FormatUtils.formatString(results.getObject("author"));
            this.aDateTime = (String) results.getObject("aDateTime");
            this.topic = FormatUtils.formatString(results.getObject("topic"));;
            this.url = FormatUtils.formatString(results.getObject("url"));;
            
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.customer.StringData (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }


    public String toString() {
        return "articleID: " + this.articleID
                + ", articleTitle: " + this.articleTitle
                + ", author: " + this.author
                + ", aDateTime: " + this.aDateTime
                + ", topic: " + this.topic
                + ", url: " + this.url;
    }
}