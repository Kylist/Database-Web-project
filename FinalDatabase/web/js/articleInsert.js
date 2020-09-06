var articleInsert = {}; // globally available object

//Code for inserting is based on given sample code
(function () {  // This is an IIFE, an Immediately Invoked Function Expression
    //alert("I am an IIFE!");

    articleInsert.startInsert = function () {

        ajax('htmlPartials/articleRequest.html', setInsertUI, 'content');

        function setInsertUI(httpRequest) {

            // Place the inserttUser html snippet into the content area.
            console.log("Ajax call was successful.");
            document.getElementById("content").innerHTML = httpRequest.responseText;

            ajax("webAPIs/getCustomerAPI.jsp", setCustomerList, "customerError");

            function setCustomerList(httpRequest) {

                console.log("setCustomerList was called, see next line for object holding list of roles");
                var jsonObj = JSON.parse(httpRequest.responseText); // convert from JSON to JS Object.
                console.log(jsonObj);

                if (jsonObj.dbError.length > 0) {
                    document.getElementById("customerError").innerHTML = jsonObj.dbError;
                    return;
                }

                // function makePickList(list, keyProp, valueProp, selectListId) {
                makePickList(jsonObj.customerList, "customerID", "customerName", "customerList");
            }
        }
    };
    
    articleInsert.insertSave = function () {

        console.log ("articleInsert.insertSave was called");

        var ddList = document.getElementById("customerList");

        // create a user object from the values that the user has typed into the page.
        var userInputObj = {
            "articleID": "",
            "articleTitle": document.getElementById("articleTitle").value,
            "author": document.getElementById("author").value,
            "aDateTime": new Date().toISOString().slice(0, 19).replace('T', ' '),
            "topic": document.getElementById("topic").value,
            "url": ddList.options[ddList.selectedIndex].text+document.getElementById("articleTitle").value.trim(),
            "errorMsg": ""
        };
        console.log(userInputObj);

        // build the url for the ajax call. Remember to escape the user input object or else 
        // you'll get a security error from the server. JSON.stringify converts the javaScript
        // object into JSON format (the reverse operation of what gson does on the server side).
        var myData = escape(JSON.stringify(userInputObj));
        var url = "webAPIs/insertArticleAPI.jsp?jsonData=" + myData;
        ajax(url, processInsert, "recordError");

        function processInsert(httpRequest) {
            console.log("processInsert was called here is httpRequest.");
            console.log(httpRequest);

            // the server prints out a JSON string of an object that holds field level error 
            // messages. The error message object (conveniently) has its fiels named exactly 
            // the same as the input data was named. 
            var jsonObj = JSON.parse(httpRequest.responseText); // convert from JSON to JS Object.
            console.log("here is JSON object (holds error messages.");
            console.log(jsonObj);

            if (jsonObj.errorMsg.length === 0) { // success
                jsonObj.errorMsg = "INSERTED SUCCESSFULY";
            }
            document.getElementById('content').innerHTML += jsonObj.errorMsg;
        }
    };

}());  // the end of the IIFE