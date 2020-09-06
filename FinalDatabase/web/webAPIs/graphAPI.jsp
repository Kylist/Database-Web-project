<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@page language="java" import="dbUtils.DbConn" %>
<%@page language="java" import="view.Statistics" %>
<%@page import="java.util.HashMap"%>
<%@page import="org.json.simple.JSONObject"%>
<%@page language="java" import="com.google.gson.*" %>

<%
    // This is the object we get from the GSON library.
    // This object knows how to convert betweeb these two formats: 
    //    POJO (plain old java object) 
    //    JSON (JavaScript Object notation)
    Gson gson = new Gson();
    DbConn dbc = new DbConn();
    String errorMsg = new String();
    JSONArray arr = new JSONArray();
    List jsonList = new ArrayList();
    int option = 0;
    option = Integer.valueOf(request.getParameter("option"));
    if (option == 0) {
        errorMsg = "Cannot insert -- no data was received";
    } else {
        errorMsg = dbc.getErr();
        if (errorMsg.length() == 0) { // means db connection is ok
            System.out.println("ready to run graphAPI");
            
            // Must use gson to convert JSON (that the user provided as part of the url, the jsonInsertData. 
            // Convert from JSON (JS object notation) to POJO (plain old java object).
            switch(option){
                case 1:
                    jsonList =  Statistics.mostLikes(dbc); // this is the form level message
                    break;
                case 2:
                    jsonList =  Statistics.mostComments(dbc); // this is the form level message
                    break;
                case 4:
                    jsonList =  Statistics.mostSales(dbc); // this is the form level message
                    break;
                default:
            }
            
        }
        for(int i = 0; i< jsonList.size(); i++){
            arr.add(jsonList.get(i));
        }   
    }
    out.print(arr.toJSONString()) ;
    dbc.close();
%>
