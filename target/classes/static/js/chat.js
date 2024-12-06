let userName;

// 사용자 이름 입력받기
while (!userName) {
    userName = prompt("이름을 입력해주세요:");
}

// WebSocket 연결
const socket = new WebSocket("ws://172.20.7.209:8082/ws/chat");

socket.onopen = () => {
    // 사용자 입장 메시지 생성
    const joinMessage = {
        type: "join", // 메시지 타입: 입장
        sender: userName,
        content: `${userName}님이 채팅방에 입장하셨습니다.`,
    };

    // 서버로 전송
    socket.send(JSON.stringify(joinMessage));
};

socket.onmessage = (event) => {
    const chatBox = document.getElementById("chat-box");
    const messageData = JSON.parse(event.data); // JSON 형식의 메시지 수신

    // 메시지 DOM 생성
    const message = document.createElement("div");
    message.classList.add("chat-message");

    // 보낸 사람과 현재 사용자 비교하여 위치 결정
    if (messageData.sender === userName) {
        message.classList.add("right"); // 본인이 보낸 메시지는 오른쪽
    } else {
        message.classList.add("left"); // 다른 사용자가 보낸 메시지는 왼쪽
    }

    // 메시지 내용 추가
    message.textContent = `${messageData.sender}: ${messageData.content}`;
    chatBox.appendChild(message);
    chatBox.scrollTop = chatBox.scrollHeight; // 스크롤을 최신 메시지로 이동
};

function sendMessage() {
    const input = document.getElementById("message-input");
    if (input.value.trim() !== "") {
        // 채팅 메시지 생성
        const chatMessage = {
            type: "chat", // 메시지 타입: 채팅
            sender: userName,
            content: input.value,
        };

        // 서버로 메시지 전송
        socket.send(JSON.stringify(chatMessage));
        input.value = ""; // 입력창 초기화
    }
}