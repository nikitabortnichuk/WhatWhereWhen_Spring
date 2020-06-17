
usernameSession = sessionStorage.getItem("username");
gameIdSession = sessionStorage.getItem("gameId");

var username = usernameSession;
var gameId = gameIdSession;
var topic = topic = `/game-www/game/${gameId}`;;
var currentSubscription;

var socket = new SockJS('/game-www/ws');
stompClient = Stomp.over(socket);

stompClient.connect({}, onConnected, onError);

function onConnected() {
    enterGame(window.gameId);
}

function onError() {

}

function enterGame(gameId) {

    if(currentSubscription){
        currentSubscription.unsubscribe();
    }
    currentSubscription = stompClient.subscribe(`/channel/${gameId}`, onMessageReceived);

    stompClient.send(`${topic}/addUser`,
        {},
        JSON.stringify({sender: `${username}`, type: 'JOIN', content: 'Connected'}));
}

function onMessageReceived(payload) {

    var message = JSON.parse(payload.body);

    if(message.type === 'JOIN') {
        printConnectedUsers(message);
        printMessage(message);
    }
    else if(message.type === 'LEAVE') {
        removeConnectedUser(message);
        printMessage(message);
    }
    else if(message.type === 'CHAT') {
        printMessage(message)
    }
    else if(message.type === 'START') {

    }
    else if(message.type === 'ASK') {

    }
    else if(message.type === 'ANSWER') {

    }
    else if(message.type === 'END') {

    }

}

function printConnectedUsers(message) {
    var usersField = document.getElementById("connected_users");
    usersField.innerHTML = "";

    var userList = message["usernameSet"];
    userList.forEach(username => printConnectedUser(username, usersField))

}

function printConnectedUser(username, usersField) {
    var connectedUserDiv = document.createElement("div");
    connectedUserDiv.setAttribute("id", username);
    connectedUserDiv.setAttribute("class", "connected_user");
    var usernameSpan = document.createElement("span");

    usernameSpan.innerHTML = "• Expert: " + username;
    connectedUserDiv.appendChild(usernameSpan);
    usersField.appendChild(connectedUserDiv);
}



function removeConnectedUser(message) {
    document.getElementById(message.sender).remove();
}

function printMessage(message) {
    var messagesField = document.getElementById("messageList");

    messagesField.innerHTML += `${message.sender}: ${message.content}\n`;
}

function sendMessage() {
    var messageInput = document.getElementById("messageInput");
    var message = {
        sender: `${username}`,
        type: 'CHAT',
        content: messageInput.value
    };
    stompClient.send(`${topic}/sendMessage`,
        {},
        JSON.stringify(message)
    );
    messageInput.value = '';
}

