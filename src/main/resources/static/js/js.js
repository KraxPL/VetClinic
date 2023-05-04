let client = null;

let mostRecentMessage = '';

function showMessage(value, user, sender, dateTime) {
    if (value === mostRecentMessage) {
        return;
    }

    const chatWindow = document.querySelector('.chat-window');
    const chatBubble = document.createElement('div');
    chatBubble.classList.add('chat-bubble');

    if (sender === 'user') {
        chatBubble.classList.add('user-bubble');
    } else if (sender === 'vet') {
        chatBubble.classList.add('vet-bubble');
    }

    const formattedDateTime = new Date(dateTime).toLocaleString();
    chatBubble.innerText = `${user}: ${value}\n${formattedDateTime}`;
    chatWindow.appendChild(chatBubble);

    mostRecentMessage = value;
}


function connect() {
    client = Stomp.client('ws://localhost:2020/chat');
    client.connect({}, function (frame) {
        client.subscribe('/topic/messages', function (message) {
            const data = JSON.parse(message.body);
            showMessage(data.value, data.user, 'user', data.dateTime);
        });
    });
}

function sendMessage() {
    const usernameInput = document.querySelector('#username');
    const messageInput = document.querySelector('#message');
    const username = usernameInput.value.trim();
    const message = messageInput.value.trim();
    if (message !== '') {
        client.send('/app/chat', {}, JSON.stringify({ value: message, user: username }));
        messageInput.value = '';
    }
}

connect();

const submitButton = document.querySelector('.submit-button');
submitButton.addEventListener('click', sendMessage);
document.addEventListener('keydown', function (event) {
    if (event.key === 'Enter') {
        sendMessage();
    }
});