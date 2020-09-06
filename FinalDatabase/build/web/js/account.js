/*
 * Code is based on Dr. Kyvernitis's implementation of account function
 */
var account={};
account.logon = function(userId, pwId, msgId){
    var UserInput = escape(document.getElementById(userId).value);
    var pwUserInput = escape(document.getElementById(pwId).value);
    
    ajax("webAPIs/logonAPI.jsp?userid=" + UserInput + "&password=" + pwUserInput, processLogon, msgId);
    
    function processLogon(httpRequest){
        console.log("starting process");
        console.log(httpRequest);
        
        var obj = JSON.parse(httpRequest.responseText);
        
        if (obj.errorMsg !== "") {
            document.getElementById(msgId).innerHTML = "<h4>"+obj.errorMsg+"</h4>";
        } else {
            document.getElementById(msgId).innerHTML = "<h4>WELCOME TO THE WEBSITE " + obj.userID + " " + "! <br>Your level of control:<br> " +obj.fName+ " "+ obj.lName + " "+ "the " + obj.typeName;            
        }
    }
};

account.logoff = function(msgId){
    ajax("webAPIs/logoffAPI.jsp", loggedOffMsg, msgId);
    
    function loggedOffMsg (httpRequest) {
        console.log("starting loggedOffMsg");
        console.log(httpRequest);
        
        document.getElementById(msgId).innerHTML = "<h4>"+httpRequest.responseText+"</h4>";
    }
};

account.getProfile = function(msgId){
    ajax("webAPIs/getProfileAPI.jsp", profileMsg, msgId);
    
    function profileMsg (httpRequest) {
        console.log("starting profileMsg");
        console.log(httpRequest);
        
        var obj = JSON.parse(httpRequest.responseText);
        
        if (obj.errorMsg !== "") {
            document.getElementById(msgId).innerHTML = "<h4>" + obj.errorMsg +"</h4>";
        } else {
            document.getElementById(msgId).innerHTML = "<h2>PROFILE INFORMATION:</h2>";
            document.getElementById(msgId).innerHTML += "USER ID:"+ "<h2>" + obj.userID + "</h2>";
            document.getElementById(msgId).innerHTML += "EMAIL: "+"<h2>" +obj.email +"</h2>";
            document.getElementById(msgId).innerHTML += "FIRST NAME"+"<h2>"+obj.fName+"</h2>";
            document.getElementById(msgId).innerHTML += "LAST NAME"+"<h2>"+obj.lName+"</h2>";
            document.getElementById(msgId).innerHTML += "USER TYPE"+ "<h2>"+obj.typeName+ "</h2>";
            document.getElementById(msgId).innerHTML += "COUNTRY"+ "<h2>"+obj.country+"</h2>";
            document.getElementById(msgId).innerHTML += "STATE"+ "<h2>"+obj.state+"</h2>";
            document.getElementById(msgId).innerHTML += "NUMBER OF OFFENDER"+ "<h2>"+obj.offenceNo+"</h2>";
        }
    }
};
