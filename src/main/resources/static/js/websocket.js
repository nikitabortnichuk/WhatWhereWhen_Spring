usernameSession = sessionStorage.getItem("username");
gameIdSession = sessionStorage.getItem("gameId");

var username = usernameSession;
var gameId = gameIdSession;
var topic = `/game-www/game/${gameId}`;
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

    if (currentSubscription) {
        currentSubscription.unsubscribe();
    }
    currentSubscription = stompClient.subscribe(`/channel/${gameId}`, onMessageReceived);

    stompClient.send(`${topic}/addUser`,
        {},
        JSON.stringify({sender: `${username}`, type: 'JOIN', content: 'Connected'}));
}

function onMessageReceived(payload) {

    var message = JSON.parse(payload.body);

    if (message.type === 'JOIN') {
        printConnectedUsers(message);
        printMessage(message);
    } else if (message.type === 'LEAVE') {
        removeConnectedUser(message);
        printMessage(message);
    } else if (message.type === 'CHAT') {
        printMessage(message)
    } else if (message.type === 'START') {
        printStartMessage();
        processGameStart(message);
    } else if (message.type === 'ASK') {
        processGame(message);
    } else if (message.type === 'ANSWER') {
        printAnswerIsCorrect(message);
    } else if (message.type === 'END') {
        processGameEnd(message);
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

function printStartMessage() {
    var startMessageH1 = document.getElementById("start-message");
    startMessageH1.style.setProperty("color", "#6F3D2D");
    startMessageH1.innerHTML = "The game started!";
    setTimeout(removeStartQuestion, 3000);
}

function removeStartQuestion() {
    var startMessageH1 = document.getElementById("start-message");
    startMessageH1.innerHTML = "";
}

function processGameStart(message) {
    setTimeout(
        function () {
            sendRound(message, 0)
        }, 3000
    );
}

function sendRound(message, roundNumber) {
    var askMessage = {
        type: 'ASK',
        roundNumber: roundNumber
    };
    stompClient.send(`${topic}/sendQuestion`,
        {},
        JSON.stringify(askMessage)
    );
}

function processGame(message) {
    printQuestion(message);

    var roundNumber = message["roundNumber"] + 1;
    processRound(message, roundNumber);
}

function printQuestion(message) {
    var questionWrap = document.getElementById("question-wrap");
    questionWrap.style.setProperty("box-shadow", "0 0 5px 2px rgba(122, 122, 122, 0.5)");

    var questionDiv = questionWrap.children["question"];
    questionDiv.style.backgroundColor = "#fee0c1";
    questionDiv.children["variantList"].innerHTML = "";

    var questionNumber = questionDiv.children["question-number"];
    var questionText = questionDiv.children["question-text"];
    var number = message["roundNumber"] + 1;
    questionNumber.innerHTML = "Question #" + number + ":";
    questionText.innerHTML = message["question"]["questionText"];

    printVariants(message, questionDiv);
}

function printVariants(message, questionDiv) {
    var variantListDiv = questionDiv.children["variantList"];
    var variantList = message["question"]["variantList"];
    for (let i = 0; i < variantList.length; i++) {
        var variantP = document.createElement("p");
        variantP.innerHTML = (i + 1) + ". " + variantList[i].text;
        variantListDiv.appendChild(variantP);
    }
}

var isAnswered = false;

function processRound(message, roundNumber) {
    isAnswered = false;
    var timerElement = document.getElementById("round-timer");
    timerElement.style.textAlign = "center";

    var timerText = timerElement.children["timer-text"];
    timerText.innerHTML = "Read Question. Remaining time: ";
    var seconds = 10;
    timer(seconds,
        function (s) {
            var element = timerElement.children["timer-seconds"];
            element.innerHTML = s;
        },
        message,
        roundNumber,
        function (message, roundNumber) {
            processTimerForQuestionAnswer(message, roundNumber);
            printAnswerInputIfNull(message);
        }
    )
}

function processTimerForQuestionAnswer(message, roundNumber) {
    var timerElement = document.getElementById("round-timer");
    timerElement.style.textAlign = "center";

    var timerText = timerElement.children["timer-text"];
    timerText.innerHTML = "Round Started. Remaining time: ";

    timer(seconds,
        function (s) {
            var element = timerElement.children["timer-seconds"];
            element.innerHTML = s;
        },
        message,
        roundNumber,
        function (message) {
            processTimeIsOver(message);
        });
}

function processTimeIsOver(message) {
    sendQuestionAnswer(message);
}

function timer(seconds, tick, message, roundNumber, afterFunction) {
    if (seconds > 0) {
        seconds -= 1;
        tick(seconds);
        if (isAnswered === true) {
            return;
        }
        setTimeout(function () {
            timer(seconds, tick, message, roundNumber, afterFunction);
        }, 1000);
    } else {
        afterFunction(message, roundNumber);
    }
}

function printAnswerInputIfNull(json) {
    var inputDiv = document.getElementById("question-answer");
    if (inputDiv === null) {
        printAnswerInput(json);
    }
}

function printAnswerInput(json) {
    var mainWindow = document.getElementById("main-window");

    var answerInputDivWrap = document.createElement("div");
    answerInputDivWrap.id = "question-answer-div";
    answerInputDivWrap.style.display = "flex";

    var answerInputDiv = document.createElement("div");
    answerInputDiv.id = "question-answer";
    answerInputDiv.style.margin = "auto";
    answerInputDiv.style.width = "250px";
    answerInputDiv.style.height = "40px";
    answerInputDiv.style.border = "1px solid #6f3d2d";
    answerInputDiv.style.borderRadius = "5px";

    var answerInput = document.createElement("input");
    answerInput.type = "text";
    answerInput.name = "question-answer-input" + json["roundNumber"];
    answerInput.id = "question-answer-input" + json["roundNumber"];
    answerInput.placeholder = "Type answer ...";
    answerInput.style.border = "0px solid #6f3d2d";
    answerInput.style.marginLeft = "10px";
    answerInput.style.width = "150px";
    answerInputDiv.appendChild(answerInput);

    var button = document.createElement("button");
    button.type = "button";
    button.setAttribute("class", "btn btn-simple btn-info");
    button.innerHTML = "Send";
    button.onclick = function () {
        sendQuestionAnswer(json);
    };

    answerInputDiv.appendChild(button);
    answerInputDivWrap.appendChild(answerInputDiv);
    mainWindow.appendChild(answerInputDivWrap);
}

function removeElement(element) {
    element.innerHTML = "";
}

function sendQuestionAnswer(message) {
    var number = message["roundNumber"];
    var valueInput = document.getElementById("question-answer-input" + number);
    var value = "";

    if (valueInput != null) {
        value = valueInput.value;
    }

    var answerMessage = {
        type: 'ANSWER',
        answer: value,
        roundNumber: number
    };
    isAnswered = true;
    stompClient.send(`${topic}/sendAnswer`, {}, JSON.stringify(answerMessage));
}

function printAnswerIsCorrect(json) {

    var timerElement = document.getElementById("round-timer");
    var timerText = timerElement.children["timer-text"];
    var timerSeconds = timerElement.children["timer-seconds"];

    timerText.innerHTML = "";
    timerSeconds.innerHTML = "";

    var message = json["message"];
    var isCorrect = json["isCorrect"];
    var roundNumber = json["roundNumber"];

    document.getElementById("question-answer").remove();

    var answerDiv = document.getElementById("answer-is-correct");
    var isCorrectMessage = answerDiv.children["is-correct-message"];
    isCorrectMessage.style.fontSize = "24px";

    if (isCorrect === true) {
        isCorrectMessage.style.color = "green";
    } else if (isCorrect === false) {
        isCorrectMessage.style.color = "red";
    }
    isCorrectMessage.innerHTML = message;

    setTimeout(function () {
        removeElement(isCorrectMessage);
        sendRound(json, roundNumber + 1);
    }, 3000);
}

function processGameEnd(json) {
    document.getElementById("question-wrap").remove();
    document.getElementById("round-timer").remove();

    var endMessageH1 = document.getElementById("start-message");
    endMessageH1.innerHTML = "The game ended!";

    var mainWindow = document.getElementById("main-window");

    var scoreP = document.createElement("p");
    scoreP.style.fontSize = "24px";
    scoreP.innerHTML = "Score:";
    mainWindow.appendChild(scoreP);

    var correctP = document.createElement("p");
    correctP.style.fontSize = "18px";
    correctP.innerHTML = "Correct answers: " + json["correctAnswers"];
    mainWindow.appendChild(correctP);

    var incorrectP = document.createElement("p");
    incorrectP.style.fontSize = "18px";
    incorrectP.innerHTML = "Incorrect answers: " + json["incorrectAnswers"];
    mainWindow.appendChild(incorrectP);

    var homeRef = document.createElement("a");
    homeRef.href = "/game-www/home";
    homeRef.innerHTML = "Go to home page";
    mainWindow.appendChild(homeRef);
}


