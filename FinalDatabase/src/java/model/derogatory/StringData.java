
package model.derogatory;

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

    public String commentDate = "";
    public String userID = "";
    public String articleID = "";
    public String parentCommentID = "";
    public String content = "";
    public String reviewerID = "";

    public String errorMsg = "";

    // default constructor leaves all data members with empty string.
    public StringData() {
    }
    
    public StringData(String commentDate, String userID, int articleID, int parentCommentID, String content, String reviewerID) {

        this.commentDate = commentDate;
        this.userID = userID;
        this.articleID = String.valueOf(articleID);
        this.parentCommentID = String.valueOf(parentCommentID);
        this.content = content;
        this.reviewerID = reviewerID;
    }

    public void setNull() {
        this.commentDate = null;
        this.userID = null;
        this.articleID = null;
        this.parentCommentID = null;
        this.content = null;
        this.reviewerID = null;
    }
    
    // overloaded constructor sets all data members by extracting from resultSet.
    public StringData(ResultSet results) {
        try {
            this.commentDate = results.getObject("commentDate").toString().substring(0, 19);
            this.userID = FormatUtils.formatString(results.getObject("userID"));
            this.articleID = FormatUtils.formatInteger(results.getObject("articleID"));
            this.parentCommentID = FormatUtils.formatInteger(results.getObject("parentCommentID"));
            this.content = FormatUtils.formatString(results.getObject("content"));
            this.reviewerID = FormatUtils.formatString(results.getObject("reviewerID"));
            
        } catch (Exception e) {
            this.errorMsg = "Exception thrown in model.customer.StringData (the constructor that takes a ResultSet): " + e.getMessage();
        }
    }


    public String toString() {
        return "commentDate: " + this.commentDate
                + ", userID: " + this.userID
                + ", articleID: " + this.articleID
                + ", parentCommentID: " + this.parentCommentID
                + ", content: " + this.content
                + ", reviewerID: " + this.reviewerID;
    }
}