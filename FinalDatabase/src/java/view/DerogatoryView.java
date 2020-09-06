package view;

// classes imported from java.sql.*
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.derogatory.*;

// classes in my project
import dbUtils.*;

public class DerogatoryView {

    public static StringDataList allDerogatoryAPI(DbConn dbc) {

        //PreparedStatement stmt = null;
        //ResultSet results = null;
        StringDataList sdl = new StringDataList();
        try {
            String sql = "SELECT commentDate, userID, articleID, parentCommentID, content, reviewerID "+
                    "FROM derogatory_comment " + 
                    "ORDER BY commentDate;";  // you always want to order by something, not just random order.
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {
                sdl.add(results);
            }
            results.close();
            stmt.close();
        } catch (Exception e) {
            StringData sd = new StringData();
            sd.errorMsg = "Exception thrown in DerogatoryView.allDerogatoryAPI(): " + e.getMessage();
            sdl.add(sd);
        }
        return sdl;
    }

}