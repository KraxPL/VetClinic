let client = null;
let mostRecentMessage = '';
const vetId = document.getElementById('vetId').value;
const username = document.getElementById('username').value;
if (username === null){
    const vetName = document.getElementById('vetName').value;
}

function showMessage(content, sender, timestamp) {
    if (content === mostRecentMessage) {
        return;
    }

    const chatWindow = document.querySelector('.chat-window');
    const chatBubble = document.createElement('div');
    chatBubble.classList.add('chat-bubble');

    if (sender === username) {
        chatBubble.classList.add('user-bubble');
    } else {
        chatBubble.classList.add('vet-bubble');
    }

    const date = new Date(timestamp);
    const formattedTime = date.toLocaleTimeString('en-US', { hour: 'numeric', minute: 'numeric', hour12: true });
    chatBubble.innerText = `${sender}: ${content}\n${date.toLocaleDateString()} ${formattedTime}`;
    chatWindow.appendChild(chatBubble);

    mostRecentMessage = content;
}


function connect() {
    const chatRoomId = document.querySelector('#chatRoomId').value;
    const socket = new SockJS('/chat');
    client = Stomp.over(socket);
    client.connect({}, function(frame) {
        client.subscribe('/topic/messages/' + chatRoomId, function(message) {
            const data = JSON.parse(message.body);
            showMessage(data.content, data.sender, data.timestamp);
        });
    });
}

connect();

function sendMessage() {
    const messageInput = document.querySelector('#message');
    const sender = document.querySelector('#username').value;
    const message = messageInput.value.trim();
    const chatRoomId = document.querySelector('#chatRoomId').value;
    if (message !== '' && chatRoomId) {
        const chatMessage = {
            sender: sender,
            content: message
        };
        const payload = JSON.stringify(chatMessage);
        client.send('/chat/' + chatRoomId, {}, payload);
        messageInput.value = '';
    }
}


const submitButton = document.querySelector('.submit-button');
submitButton.addEventListener('click', sendMessage);
document.addEventListener('keydown', function (event) {
    if (event.key === 'Enter') {
        sendMessage();
    }
});