/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import dbUtils.DbConn;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Statistics {
    public static List<HashMap<String,Object>> mostLikes(DbConn dbc) {
        List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        
        try {
            String sql = "select count(*) as likeDislikeCount, userID from comment " +
                            "where commentType = 'like' or commentType = 'dislike' " +
                            "group by userID " +
                            "order by likeDislikeCount desc " +
                            "limit 20;";  // you always want to order by something, not just random order.
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            ResultSetMetaData md = results.getMetaData();
            int columns = md.getColumnCount();
            while (results.next()) {
            HashMap<String,Object> row = new HashMap<String, Object>(columns);
            for(int i=1; i<=columns; ++i) {
                row.put(md.getColumnName(i),results.getObject(i));
            }
                list.add(row);
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
        return list;
    }
    
    public static List<HashMap<String,Object>> mostComments(DbConn dbc) {
        List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        
        try {
            String sql = "select count(*) as commentCount, userID from comment " +
                        "where commentType = 'comment' or commentType='reply' " +
                        "group by userID " +
                        "order by commentCount desc " +
                        "limit 20;";  // you always want to order by something, not just random order.
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            ResultSetMetaData md = results.getMetaData();
            int columns = md.getColumnCount();
            while (results.next()) {
            HashMap<String,Object> row = new HashMap<String, Object>(columns);
            for(int i=1; i<=columns; ++i) {
                row.put(md.getColumnName(i),results.getObject(i));
            }
                list.add(row);
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
        return list;
    }
    
    public static List<HashMap<String,Object>> mostSales(DbConn dbc) {
        List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
        
        try {
            String sql = "select customerID, sum(amount) as totalSales from billed " +
                            "group by customerID " +
                            "order by totalSales desc " +
                            "limit 10;";  // you always want to order by something, not just random order.
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            ResultSetMetaData md = results.getMetaData();
            int columns = md.getColumnCount();
            while (results.next()) {
            HashMap<String,Object> row = new HashMap<String, Object>(columns);
            for(int i=1; i<=columns; ++i) {
                row.put(md.getColumnName(i),results.getObject(i));
            }
                list.add(row);
            }
        } catch (Exception e) {
                e.printStackTrace();
        }
        return list;
    }
}
