const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/gs-guide-websocket'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    subscribeToGroup();
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

let groupCode = '';
let userName = '';

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    groupCode = $("#groupCode").val();
    userName = $("#name").val();
    if (!groupCode || !userName) {
        alert("Please enter a group code and your name.");
        return;
    }
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function subscribeToGroup() {
    // Subscribe to the group topic based on group code
    stompClient.subscribe(`/topic/group/${groupCode}`, (message) => {
        showGreeting(JSON.parse(message.body).content);
    });
}

function sendMessage() {
    const messageContent = $("#messageInput").val();
    if (messageContent && groupCode && userName) {
        stompClient.publish({
            destination: "/app/sendMessage",
            body: JSON.stringify({
                name: userName,
                groupCode: groupCode,
                content: messageContent
            })
        });
        $("#messageInput").val("");
    }
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendMessage());
});
