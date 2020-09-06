
package model.article;

import dbUtils.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DbMods {
    public static StringData insert(StringData inputData, DbConn dbc) {
        int articleID = 1;
        String sql2 = "SELECT max(articleID) as maxID "
                    + "FROM article;";
        PreparedStatement pStatement2; 
        try {
            pStatement2 = dbc.getConn().prepareStatement(sql2); // compiles the SQL
            ResultSet results = pStatement2.executeQuery();
            results.next();
            articleID = results.getInt("maxID")+1;
            results.close();
            pStatement2.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        StringData errorMsgs = new StringData();
        String sql = "INSERT INTO article (articleID, articleTitle, author, aDateTime, topic, url) "
                + "values (?,?,?,?,?,?)";

        PrepStatement pStatement = new PrepStatement(dbc, sql);

        // Encode string values into the prepared statement (wrapper class).
        pStatement.setInt(1, articleID);
        pStatement.setString(2, inputData.articleTitle); // string type is simple
        pStatement.setString(3, inputData.author);
        pStatement.setString(4, inputData.aDateTime);
        pStatement.setString(5, inputData.topic);
        pStatement.setString(6, inputData.url);

        // here the SQL statement is actually executed
        int numRows = pStatement.executeUpdate();

        // This will return empty string if all went well, else all error messages.
        errorMsgs.errorMsg = pStatement.getErrorMsg();
        if (errorMsgs.errorMsg.length() == 0) {
            if (numRows == 1) {
                errorMsgs.errorMsg = ""; // This means SUCCESS. Let the user interface decide how to tell this to the user.
                model.comment.DbMods.simulate(100, articleID, inputData.aDateTime, dbc);
                
            } else {
                // probably never get here unless you forgot your WHERE clause and did a bulk sql update.
                errorMsgs.errorMsg = numRows + " records were inserted when exactly 1 was expected.";
            }
        }
        return errorMsgs;
    } // insert
}
