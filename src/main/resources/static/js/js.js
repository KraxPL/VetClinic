let client = null;

let mostRecentMessage = '';

function showMessage(value, sender) {
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

    chatBubble.innerText = value;
    chatWindow.appendChild(chatBubble);

    mostRecentMessage = value;
}

function connect() {
    client = Stomp.client('ws://localhost:2020/chat');
    client.connect({}, function (frame) {
        client.subscribe('/topic/messages', function (message) {
            const data = JSON.parse(message.body);
            showMessage(data.value, 'user');
        });
    });
}

function sendMessage() {
    const textInput = document.querySelector('.text-input');
    const message = textInput.value.trim();
    if (message !== '') {
        client.send('/app/chat', {}, JSON.stringify({ value: message }));
        textInput.value = '';
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