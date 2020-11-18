var ws;

function connect() {
    var username = document.getElementById("username").value;
    var usernameFriend = document.getElementById("friend").value;
    var url = "ws://coms-309-vb-02.cs.iastate.edu:8080/chat/" + username + "/" + usernameFriend;
    //var url = "ws://echo.websocket.org";

    ws = new WebSocket(url);

    ws.onmessage = function(event) { // Called when client receives a message from the server
        console.log(event.data);

        // display on browser
        var log = document.getElementById("log");
        log.innerHTML += "" + event.data + "\n";
    };

    ws.onopen = function(event) { // called when connection is opened
        var log = document.getElementById("log");
        log.innerHTML += "Connected to " + event.currentTarget.url + "\n";
    };
}

function send() {  // this is how to send messages
    var content = document.getElementById("msg").value;
    ws.send(content);
}
