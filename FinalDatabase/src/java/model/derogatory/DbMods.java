
package model.derogatory;

import dbUtils.DbConn;
import dbUtils.PrepStatement;
import dbUtils.ValidationUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DbMods {
    public static StringData insert(StringData inputData, DbConn dbc) {
        
        StringData errorMsgs = new StringData();
        int commentID = 1;
        String sql = "SELECT max(commentID) as maxID "
                    + "FROM comment;";
        PreparedStatement pStatement; 
        try {
            pStatement = dbc.getConn().prepareStatement(sql); // compiles the SQL
            ResultSet results = pStatement.executeQuery();
            results.next();
            commentID = results.getInt("maxID")+1;
            results.close();
            pStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        String sql2 = "INSERT INTO comment (commentID, commentDate, commentType, userID, articleID, parentCommentID, content) "
                + "values (?,?,?,?,?,?,?)";

        PrepStatement pStatement2 = new PrepStatement(dbc, sql2);

        // Encode string values into the prepared statement (wrapper class).
        pStatement2.setInt(1, commentID);
        pStatement2.setString(2, inputData.commentDate); // string type is simple
        pStatement2.setString(3, ValidationUtils.integerConversion(inputData.parentCommentID)==0?"comment":"reply");
        pStatement2.setString(4, inputData.userID);
        pStatement2.setInt(5, ValidationUtils.integerConversion(inputData.articleID));
        pStatement2.setInt(6, ValidationUtils.integerConversion(inputData.parentCommentID));
        pStatement2.setString(7, inputData.content);
        
        int numRows = pStatement2.executeUpdate();

        // This will return empty string if all went well, else all error messages.
        errorMsgs.errorMsg = pStatement2.getErrorMsg();
        if (errorMsgs.errorMsg.length() == 0) {
            if (numRows == 1) {
                errorMsgs.errorMsg = ""; // This means SUCCESS. Let the user interface decide how to tell this to the user.
                
            } else {
                // probably never get here unless you forgot your WHERE clause and did a bulk sql update.
                errorMsgs.errorMsg = numRows + " records were inserted when exactly 1 was expected.";
            }
        }
        return errorMsgs;
    } // insert
    
    public static String delete(String userID, String commentDate, DbConn dbc) {
        if (userID == null || commentDate == null) {
            return "Programmer error: one or more params are null";
        }
        
        String result = "";
        try {

            String sql = "DELETE FROM derogatory_comment WHERE userID = ? AND commentDate = ?";

            // This line compiles the SQL statement (checking for syntax errors against your DB).
            PreparedStatement pStatement = dbc.getConn().prepareStatement(sql);

            // Encode user data into the prepared statement.
            pStatement.setString(1, userID);
            pStatement.setTimestamp(2, java.sql.Timestamp.valueOf(commentDate));

            int numRowsDeleted = pStatement.executeUpdate();

            if (numRowsDeleted == 0) {
                result = "Programmer Error: did not delete the record";
            } else if (numRowsDeleted > 1) {
                result = "Programmer Error: > 1 record deleted. Did you forget the WHERE clause?";
            }

        } catch (Exception e) {
            result = "Exception thrown in model.tournament.DbMods.delete(): " + e.getMessage();
        }

        return result;
    } //delete
    
    public static String disapprove(String userID, String commentDate, DbConn dbc) {
        String result = "";
        try {
            String sql = "UPDATE user SET offenceNo = offenceNo+1 WHERE userID = ?";
            PrepStatement pStatement = new PrepStatement(dbc, sql);
            pStatement.setString(1, userID);
            int numRowsDeleted = pStatement.executeUpdate();

            if (numRowsDeleted == 0) {
                result = "Programmer Error: did not update";
            } else if (numRowsDeleted > 1) {
                result = "Programmer Error: > 1 record updated. Did you forget the WHERE clause?";
            } else {
                result = delete(userID, commentDate, dbc);
            }
        } catch (Exception e) {
            result = "Exception thrown in model.tournament.DbMods.disapprove(): " + e.getMessage();
        }
        return result;
    }
}
