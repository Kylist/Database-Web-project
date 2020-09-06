var derogatoryCRUD = {}; // globally available object

//Code for inserting tournament is based on sample code for inserting user
(function () {
var obj;
derogatoryCRUD.list = function (targetId) {

    document.getElementById(targetId).innerHTML = "";
    var dataList = document.createElement("div");
    dataList.id = "dataList";
    document.getElementById(targetId).appendChild(dataList);

    ajax('webAPIs/listDerogatoryAPI.jsp', setListUI, 'listMsg');

    function setListUI(httpRequest) {

        console.log("starting derogatoryCRUD.list (setListUI) with this httpRequest object (next line)");
        console.log(httpRequest);
       
        obj = JSON.parse(httpRequest.responseText);
        
        if (obj === null) {
            document.getElementById(targetId).innerHTML = "derogatoryCRUD.list Error: JSON string evaluated to null.";
            return;
        } else if(obj.errorMsg != null) {
            document.getElementById(targetId).innerHTML = "<h4>"+obj.errorMsg.toString()+"</h4>";
            return;
        }

        for (var i = 0; i < obj.derogatoryList.length; i++) {
            
            obj.derogatoryList[i].articleID = obj.derogatoryList[i].articleID.replace(',',"");
            obj.derogatoryList[i].parentCommentID = obj.derogatoryList[i].parentCommentID.replace(',','');
            
            obj.derogatoryList[i].remove = "<img src='pics/delete.png' width='20'  onclick='derogatoryCRUD.delete(" + i + ", this)'  />";
            obj.derogatoryList[i].approve = "<img onclick='derogatoryCRUD.startInsert("+ i +", this)' src='pics/approve.png' width='20'/>";
            // how to delete properties
            delete obj.derogatoryList[i].reviewerID;
            delete obj.derogatoryList[i].errorMsg;
        }
        
        // buildTable Parameters: 
        // First:  array of objects that are to be built into an HTML table.
        // Second: string that is database error (if any) or empty string if all OK.
        // Third:  reference to DOM object where built table is to be stored. 
        buildTable(obj.derogatoryList, obj.dbError, dataList);
    }
};

derogatoryCRUD.delete = function (index, icon) {
        if (confirm("Disapprove the comment? Number of offences will be increased for user.")) {
            console.log("icon that was passed into JS function is printed on next line");
            console.log(icon);
            
            ajax("webAPIs/deleteDerogatoryAPI.jsp?mode=disapprove&userID="+escape(obj.derogatoryList[index].userID)+"&commentDate="+escape(obj.derogatoryList[index].commentDate), modify, null);
            // HERE YOU HAVE TO CALL THE DELETE API and the success function should run the 
            // following (delete the row that was clicked from the User Interface).
            function modify(httpRequest){
                // icon's parent is cell whose parent is row
                var jsonObj = JSON.parse(httpRequest.responseText);
                if(jsonObj.errorMsg.length>0){
                    alert(jsonObj.errorMsg.toString());
                } else {
                    alert("Success!");
                    var dataRow = icon.parentNode.parentNode;
                    var rowIndex = dataRow.rowIndex - 1; // adjust for oolumn header row?
                    var dataTable = dataRow.parentNode;
                    dataTable.deleteRow(rowIndex);
                }   
            }
        }

};

    derogatoryCRUD.startInsert = function (index, icon) {
        if (confirm("Approve the comment? It will be removed from the derogatory comment list and added to the \"comment\" data table.")) {
            console.log("icon that was passed into JS function is printed on next line");
            console.log(icon);
            
            ajax("webAPIs/insertCommentAPI.jsp?jsonData="+escape(JSON.stringify(obj.derogatoryList[index])), modify, null);
            // HERE YOU HAVE TO CALL THE DELETE API and the success function should run the 
            // following (delete the row that was clicked from the User Interface).
            function modify(httpRequest){
                // icon's parent is cell whose parent is row
                var jsonObj = JSON.parse(httpRequest.responseText);
                if(jsonObj.errorMsg.length>0){
                    alert(jsonObj.errorMsg.toString());
                } else {
                    alert("Success!");
                    ajax("webAPIs/deleteDerogatoryAPI.jsp?mode=approve&userID="+escape(obj.derogatoryList[index].userID)+"&commentDate="+escape(obj.derogatoryList[index].commentDate), print, null);
                    function print(httpRequest){
                        var jsonObj = JSON.parse(httpRequest.responseText);
                        if(jsonObj.errorMsg.length>0){
                            alert(jsonObj.errorMsg.toString());
                        } else {
                            alert("Success!");
                        }
                    }
                    var dataRow = icon.parentNode.parentNode;
                    var rowIndex = dataRow.rowIndex - 1; // adjust for oolumn header row?
                    var dataTable = dataRow.parentNode;
                    dataTable.deleteRow(rowIndex);
                }   
            }
        }
    };
    
    
}());
