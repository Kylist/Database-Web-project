<%@page import="org.apache.commons.lang3.StringEscapeUtils"%>
<%@page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@page language="java" import="dbUtils.DbConn" %>
<%@page language="java" import="model.derogatory.*" %>

<%@page language="java" import="com.google.gson.*" %>

<%
    // This is the object we get from the GSON library.
    // This object knows how to convert betweeb these two formats: 
    //    POJO (plain old java object) 
    //    JSON (JavaScript Object notation)
    Gson gson = new Gson();
    DbConn dbc = new DbConn();
    StringData errorMsgs = new StringData();
    String mode = request.getParameter("mode");
    String userID = StringEscapeUtils.unescapeJava(request.getParameter("userID"));
    String commentDate = StringEscapeUtils.unescapeJava(request.getParameter("commentDate"));
    if (userID == null || mode == null || commentDate == null) {
        errorMsgs.errorMsg = "Cannot delete -- Missing Data";
        System.out.println(errorMsgs.errorMsg);
    } else {
        errorMsgs.errorMsg = dbc.getErr();
        if (errorMsgs.errorMsg.length() == 0) { // means db connection is ok
            System.out.println("Ready to delete derogatory");
            if(mode.equals("approve")){
                errorMsgs.errorMsg = DbMods.delete(userID, commentDate, dbc);
            } else {
                errorMsgs.errorMsg = DbMods.disapprove(userID, commentDate, dbc);    
            }
        }
    }
    out.print(gson.toJson(errorMsgs).trim());
    dbc.close();
%>
