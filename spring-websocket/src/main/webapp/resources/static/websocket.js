let stompClient;

$(document).ready(() => {
    stompClient = new StompJs.Client({
        brokerURL: `ws://${window.location.host}/websocket`,
        debug: function (str) {
            console.log(str);
        },
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000,
    });

    stompClient.activate();

    stompClient.onConnect  = (frame) => {
        subscribeToChannel();
    };
});

function callWebSocket() {
    const intervalId = setInterval(function () {
        stompClient.publish({
            destination: '/request/payment',
            binaryBody: new TextEncoder().encode(getBody()),
        });
    }, 2000);

    setTimeout(function () {
        clearInterval(intervalId);
    }, 7000);
}

function getBody() {
    return JSON.stringify({
        userCode: userName,
        productCode: "78901000002",
        qty: 1
    });
}

function subscribeToChannel() {
    // convertAndSendToUser configuration
    // destination is /response/payment/{userCode}
    stompClient.subscribe(`/response/payment/${userName}`, (message) => {
        console.log("Event catch!")
        console.log(message.body);
    });
}