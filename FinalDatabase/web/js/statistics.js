var statistics = {}; // globally available object

//Code for inserting is based on given sample code
(function () {  // This is an IIFE, an Immediately Invoked Function Expression
    //alert("I am an IIFE!");

    statistics.drawGraph = function (targetId) {
        
        document.getElementById(targetId).innerHTML = "";
        var graph1 = document.createElement("div");
        var graph2 = document.createElement("div");
        graph1.id = "graph1";
        graph2.id = "graph2";
        graph1.setAttribute("style","text-align: center;");
        graph2.setAttribute("style","text-align: center;");
        document.getElementById(targetId).appendChild(graph1);
        document.getElementById(targetId).appendChild(graph2);
        ajax('webAPIs/graphAPI.jsp?option=1', fgraph1, "graph1");
        ajax('webAPIs/graphAPI.jsp?option=2', fgraph2, "graph2");


        function fgraph1(httpRequest) {

            console.log("starting statistics.graph with this httpRequest object (next line)");
            console.log(httpRequest);

            obj1 = JSON.parse(httpRequest.responseText);
            console.log(obj1);
            if (obj1 === null) {
                document.getElementById("graph1").innerHTML += "derogatoryCRUD.list Error: JSON string evaluated to null.";
                return;
            }
            google.charts.load('current', {packages: ['corechart']});
            google.charts.setOnLoadCallback(drawChart);
            document.getElementById("graph1").innerHTML = "";

            function drawChart() {
                // Define the chart to be drawn.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'User ID');
                data.addColumn('number', 'Likes & Dislikes');
                var max = 0;
                for(var i = 0; i<obj1.length; i++){
                    data.addRows([[obj1[i].userID, obj1[i].likeDislikeCount]]);
                    if(obj1[i].likeDislikeCount>max){
                        max = obj1[i].likeDislikeCount;
                    }
                }
                
                var options = {'title':'Top 20 users with the most likes & dislikes',
                     'width':1500,
                     'height':1000,
                     vAxis: {
                       title: 'Count',
                       gridlines: { count: max+1}, //+1 is importent for the origingridline
                       viewWindow:{
                         min: 0,
                         max: max+1
                       }
                     },
                     hAxis: {title: 'user ID'}
                 };

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.ColumnChart(document.getElementById("graph1"));
                chart.draw(data, options);
            }
        }
        
        function fgraph2(httpRequest) {

            console.log("starting statistics.graph with this httpRequest object (next line)");
            console.log(httpRequest);

            obj2 = JSON.parse(httpRequest.responseText);
            console.log(obj2);
            if (obj2 === null) {
                document.getElementById("graph2").innerHTML += "derogatoryCRUD.list Error: JSON string evaluated to null.";
                return;
            }
            google.charts.load('current', {packages: ['corechart']});
            google.charts.setOnLoadCallback(drawChart);
            document.getElementById("graph2").innerHTML = "";

            function drawChart() {
                // Define the chart to be drawn.
                var data = new google.visualization.DataTable();
                data.addColumn('string', 'User ID');
                data.addColumn('number', 'Comments & Replies');
                var max = 0;
                for(var i = 0; i<obj2.length; i++){
                    data.addRows([[obj2[i].userID, obj2[i].commentCount]]);
                    if(obj1[i].likeDislikeCount>max){
                        max = obj1[i].likeDislikeCount;
                    }
                }
                
                var options = {'title':'Top 20 users with the most comments & replies',
                     'width':1500,
                     'height':1000,
                      vAxis: {
                       title: 'Count',
                       gridlines: { count: max+1}, //+1 is importent for the origingridline
                       viewWindow:{
                         min: 0,
                         max: max+1
                       }
                      },
                      hAxis: {title: 'user ID'}
                 };

                // Instantiate and draw our chart, passing in some options.
                var chart = new google.visualization.ColumnChart(document.getElementById("graph2"));
                chart.draw(data, options);
            }
        }
        
        
    };

}());  // the end of the IIFE
