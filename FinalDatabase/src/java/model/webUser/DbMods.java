package model.webUser;

import dbUtils.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbMods {
    public static StringData logonFind(String email, String pw, DbConn dbc) {
        StringData foundData = new StringData();
        if ((email == null) || (pw == null)) {
            foundData.errorMsg = "model.webUser.DbMods.logonFind: email and pw must be both non-null.";
            return foundData;
        }
        try {
            // prepare (compiles) the SQL statement
            String sql = "SELECT userID, password, fName, lName, user.typeID, "
                    + "user_type.typeName, email, country, state, avatarURL, offenceNo, isSuspended "
                    + "FROM user, user_type "
                    + "WHERE user.typeID = user_type.typeID "
                    + "AND userID = ? and password = ? "
                    + "ORDER BY userID ";
            PreparedStatement pStatement = dbc.getConn().prepareStatement(sql); // compiles the SQL

            // Encode string values into the prepared statement (wrapper class).
            pStatement.setString(1, email);
            pStatement.setString(2, pw);

            ResultSet results = pStatement.executeQuery();  // run the SELECT statement
            if (results.next()) {
                // Record found in database, credentials are good.
                return new StringData(results);
            } else {
                // Returning null means that the username / pw were not found in the database
                return null;
            }
        } catch (Exception e) {
            foundData.errorMsg = "Exception in model.webUser.DbMods.logonFind(): " + e.getMessage();
            System.out.println("******" + foundData.errorMsg);
            return foundData;
        }
    } // logonFind
    
} // class