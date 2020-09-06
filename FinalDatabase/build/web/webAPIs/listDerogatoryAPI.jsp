<%@page contentType="application/json; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@page language="java" import="dbUtils.*" %>
<%@page language="java" import="model.derogatory.*" %> 
<%@page language="java" import="view.DerogatoryView" %> 
<%@page language="java" import="com.google.gson.*" %>

<%
    // default constructor creates nice empty StringDataList with all fields "" (empty string, nothing null).
    model.webUser.StringData found = (model.webUser.StringData) session.getAttribute("webUser");
    Gson gson = new Gson();
    if (found  == null) {
        found = new model.webUser.StringData();
        found.errorMsg = "Cannot provide profile - you are not logged on.";
        out.print(gson.toJson(found).trim());
    } else if(Integer.valueOf(found.typeID)<2){
        found = new model.webUser.StringData();
        found.errorMsg = "Cannot provide profile - you are not an admin or a senior user.";
        out.print(gson.toJson(found).trim());
    } else {
    StringDataList list = new StringDataList();
    DbConn dbc = new DbConn();
    list.dbError = dbc.getErr(); // returns "" if connection is good, else db error msg.
    if (list.dbError.length() == 0) { // if got good DB connection,
        System.out.println("*** Ready to call allDerogatoryAPI");
        list = DerogatoryView.allDerogatoryAPI(dbc);
    }
      dbc.close(); // EVERY code path that opens a db connection, must also close it - no DB Conn leaks.
    // This object (from the GSON library) can to convert between JSON <-> POJO (plain old java object) 
    out.print(gson.toJson(list).trim());
    }
%>