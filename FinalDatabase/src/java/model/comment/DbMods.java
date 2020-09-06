
package model.comment;

import dbUtils.DbConn;
import dbUtils.PrepStatement;
import dbUtils.ValidationUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class DbMods {
    public static StringData insert(StringData inputData, DbConn dbc) {
        
        StringData errorMsgs = new StringData();
        String sql = "INSERT INTO comment (commentID, commentDate, commentType, userID, articleID, parentCommentID, content) "
                + "values (?,?,?,?,?,?,?)";

        PrepStatement pStatement = new PrepStatement(dbc, sql);

        // Encode string values into the prepared statement (wrapper class).
        pStatement.setInt(1, ValidationUtils.integerConversion(inputData.commentID));
        pStatement.setString(2, inputData.commentDate); // string type is simple
        pStatement.setString(3, inputData.commentType);
        pStatement.setString(4, inputData.userID);
        pStatement.setInt(5, ValidationUtils.integerConversion(inputData.articleID));
        pStatement.setInt(6, ValidationUtils.integerConversion(inputData.parentCommentID));
        pStatement.setString(7, inputData.content);

        // here the SQL statement is actually executed
        int numRows = pStatement.executeUpdate();

        // This will return empty string if all went well, else all error messages.
        errorMsgs.errorMsg = pStatement.getErrorMsg();
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
    
    public static StringData insertDerogatory(StringData inputData, DbConn dbc) {
        ArrayList<String> reviewerID = new ArrayList<>();
        scanFile("txt/seniorID.txt", reviewerID, 200);
        Random rd = new Random();
        String sID = reviewerID.get(rd.nextInt(reviewerID.size()));
        StringData errorMsgs = new StringData();
        String sql = "INSERT INTO derogatory_comment (commentDate, userID, articleID, parentCommentID, content, reviewerID) "
                + "values (?,?,?,?,?,?)";

        PrepStatement pStatement = new PrepStatement(dbc, sql);

        // Encode string values into the prepared statement (wrapper class).
        pStatement.setString(1, inputData.commentDate);
        pStatement.setString(2, inputData.userID);
        pStatement.setInt(3, ValidationUtils.integerConversion(inputData.articleID));
        pStatement.setInt(4, ValidationUtils.integerConversion(inputData.parentCommentID));
        pStatement.setString(5, inputData.content);
        pStatement.setString(6, sID);

        // here the SQL statement is actually executed
        int numRows = pStatement.executeUpdate();

        // This will return empty string if all went well, else all error messages.
        errorMsgs.errorMsg = pStatement.getErrorMsg();
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
    
    public static void simulate(int num, int newArticleID, String articleDate, DbConn dbc){
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
        ArrayList<String> profanity = new ArrayList<>();
        ArrayList<String> userID = new ArrayList<>();
        ArrayList<Integer> commentIDs = new ArrayList<>();
        scanFile("txt/userID.txt", userID, 300);
        scanFile("txt/profanity.txt", profanity, 300);
        Random rd = new Random();
        commentIDs.add(0);
        HashMap<Integer, String> commentDates = new HashMap<>();
        commentDates.put(0, articleDate);
        int i = 0;
        int range = num*9/10;
        for (; i < range; i++){
            int parentCommentID = commentIDs.get(rd.nextInt(commentIDs.size()));
            String commentDate = laterDate(commentDates.get(parentCommentID));
            String ID = userID.get(rd.nextInt(userID.size()));
            String commentType = "comment";
            if(parentCommentID == 0){
                commentType = "comment";
                commentIDs.add(commentID);
                commentDates.put(commentID, commentDate);
            } else {
                switch(rd.nextInt(3)){
                    case 0:
                        commentType = "reply";
                        commentIDs.add(commentID);
                        commentDates.put(commentID, commentDate);
                        break;
                    case 1:
                        commentType = "like";
                        break;
                    case 2:
                        commentType = "dislike";
                        break;
                    default:
                }
            }
            StringData toAdd = new StringData(commentID, commentDate, commentType, ID, newArticleID, parentCommentID, ID);
            System.out.println(toAdd.toString());
            if(insert(toAdd, dbc).errorMsg.equals("")){
                commentID++;
            } else {
                System.out.println("An error has occured during the simulation (comment).");
                return;
            };
        }
        for (int j = i; j<num; j++) {
            int parentCommentID = commentIDs.get(rd.nextInt(commentIDs.size()));
            String commentDate = laterDate(commentDates.get(parentCommentID));
            String ID = userID.get(rd.nextInt(userID.size()));
            String commentType = "comment";
            if(parentCommentID == 0){
                commentType = "comment";
            } else {
                commentType = "reply";
            }
            StringData toAdd = new StringData(0, commentDate, commentType, ID, newArticleID, parentCommentID, profanity.get(rd.nextInt(profanity.size())));
            System.out.println(toAdd.toString());
            if(insertDerogatory(toAdd, dbc).errorMsg.equals("")){
            } else {
                System.out.println("An error has occured during the simulation (derogatory).");
                return;
            };
        }
        
    }
    
    public static String laterDate(String datetime){
        Random rd = new Random();
        int year = Integer.parseInt(datetime.substring(0,4));
        int month = Integer.parseInt(datetime.substring(5,7));
        int day = Integer.parseInt(datetime.substring(8,10));
        int hour = Integer.parseInt(datetime.substring(11,13));
        int val = rd.nextInt(10);
        //40% chance advance hour, 30% chance advance day, 20% month, 10% year
        if (val == 0) {
            year++;
        } else if (val<3) {
            month++;
        } else if (val<6) {
            day++;
        } else {
            hour++;
        }
        int arr[] = {year,month,day,hour};
        adjustDate(arr);
        return assembleDate(arr)+datetime.substring(13);
    }
    
    public static String assembleDate(int arr[]){
        String result = arr[0]+"-";
        result+= (arr[1]>=10)?arr[1]:"0"+arr[1];
        result+="-";
        result+= (arr[2]>=10)?arr[2]:"0"+arr[2];
        result+=" ";
        result+= (arr[3]>=10)?arr[3]:"0"+arr[3];
        if(arr.length>4){
            result+=":";
            result+= (arr[4]>=10)?arr[4]:"0"+arr[4];
            result+=":";
            result+= (arr[5]>=10)?arr[5]:"0"+arr[5];
        }
        return result;
    }
    
    public static void adjustDate(int arr[]){
        if(arr[3]>23){
            arr[3] = 0;
            arr[2]++;
        }
        //for simplicity (not worrying about febuary, months with 30 days, leap year, etc.)
        if(arr[2]>27){
            arr[2] = 1;
            arr[1]++;
        }
        if(arr[1]>12){
            arr[1] = 1;
            arr[0] ++;
        }
    }
    
    public static void scanFile(String filePath, ArrayList<String> list, int iter){
        File file = new File(filePath);
        try {
            Scanner scn = new Scanner(file);
            while(scn.hasNext()&&iter>=0){
                list.add(scn.nextLine());
                iter--;
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
