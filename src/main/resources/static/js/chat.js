// let userName;

// // 사용자 이름 입력받기
// while (!userName) {
//     userName = prompt("이름을 입력해주세요:");
// }

// // WebSocket 연결
// const socket = new WebSocket("ws://localhost:8082/ws/chat");

// socket.onopen = () => {
//     // 사용자 입장 메시지 생성
//     const joinMessage = {
//         type: "join", // 메시지 타입: 입장
//         sender: userName,
//         content: `${userName}님이 채팅방에 입장하셨습니다.`,
//     };

//     // 서버로 전송
//     socket.send(JSON.stringify(joinMessage));
// };

// socket.onmessage = (event) => {
//     const chatBox = document.getElementById("chat-box");
//     const messageData = JSON.parse(event.data); // JSON 형식의 메시지 수신

//     // 메시지 DOM 생성
//     const message = document.createElement("div");
//     message.classList.add("chat-message");

//     // 보낸 사람과 현재 사용자 비교하여 위치 결정
//     if (messageData.sender === userName) {
//         message.classList.add("right"); // 본인이 보낸 메시지는 오른쪽
//     } else {
//         message.classList.add("left"); // 다른 사용자가 보낸 메시지는 왼쪽
//     }

//     // 메시지 내용 추가
//     message.textContent = `${messageData.sender}: ${messageData.content}`;
//     chatBox.appendChild(message);
//     chatBox.scrollTop = chatBox.scrollHeight; // 스크롤을 최신 메시지로 이동
// };

// function sendMessage() {
//     const input = document.getElementById("message-input");
//     if (input.value.trim() !== "") {
//         // 채팅 메시지 생성
//         const chatMessage = {
//             type: "chat", // 메시지 타입: 채팅
//             sender: userName,
//             content: input.value,
//         };

//         // 서버로 메시지 전송
//         socket.send(JSON.stringify(chatMessage));
//         input.value = ""; // 입력창 초기화
//     }
// }

let userName = "";
let userRole = "";
let chatFloor = "";
let socket;

// 층 선택
function selectFloor(floor) {
    chatFloor = `${floor}층`;
    document.getElementById("floor-selection").classList.add("hidden");
    document.getElementById("role-selection").classList.remove("hidden");
}

// 역할 선택
function selectRole(role) {
    userRole = role;
    document.getElementById("role-selection").classList.add("hidden");
    document.getElementById("login-screen").classList.remove("hidden");

    const loginHeader = document.getElementById("login-header");
    const loginInput = document.getElementById("login-input");

    if (role === "user") {
        loginHeader.innerText = "일반 사용자 로그인";
        loginInput.placeholder = "이름을 입력하세요";
    } else if (role === "admin") {
        loginHeader.innerText = "관리자 로그인";
        loginInput.placeholder = "비밀번호를 입력하세요";
    }
}

// 로그인 처리
function handleLogin() {
    const loginInput = document.getElementById("login-input").value.trim();

    if (userRole === "user") {
        if (!loginInput) {
            alert("이름을 입력하세요.");
            return;
        }
        userName = loginInput;
        alert(`일반 이용자로 ${chatFloor}에 접속합니다.`);
    } else if (userRole === "admin") {
        if (loginInput !== "admin123") { // 관리자 비밀번호
            alert("비밀번호가 일치하지 않습니다.");
            return;
        }
        userName = "관리자";
        alert(`관리자로 ${chatFloor}에 접속합니다.`);
    }

    document.getElementById("login-screen").classList.add("hidden");
    document.getElementById("chat-container").classList.remove("hidden");
    connectWebSocket();
}

// WebSocket 연결
function connectWebSocket() {
    socket = new WebSocket(`ws://localhost:8082/ws/chat?floor=${chatFloor}`);

    socket.onopen = () => {
        const joinMessage = {
            type: "join",
            role: userRole,
            sender: userName,
            content: `${userName}님이 ${chatFloor}에 입장하셨습니다.`,
        };
        socket.send(JSON.stringify(joinMessage));
    };

    socket.onmessage = (event) => {
        const chatBox = document.getElementById("chat-box");
        const messageData = JSON.parse(event.data);

        const message = document.createElement("div");
        message.classList.add("chat-message");

        if (messageData.type === "join") {
            message.classList.add("join-message");
            message.textContent = messageData.content;
        } else if (messageData.type === "system") {
            // 입장 메시지 처리
            message.classList.add("system-message");
            message.textContent = messageData.content;
        } else if (messageData.type === "admin") {
            // 관리자가 보낸 메시지 처리
            message.classList.add("admin");
            message.textContent = `${messageData.content}`;
        } else if (messageData.sender === userName) {
            // 본인이 보낸 메시지
            message.classList.add("right");
            message.textContent = messageData.content;
        } else {
            // 일반 사용자가 보낸 메시지
            message.classList.add("left");
            message.textContent = `${messageData.sender}: ${messageData.content}`;
        }

        chatBox.appendChild(message);
        chatBox.scrollTop = chatBox.scrollHeight;
    };

    socket.onerror = (error) => {
        console.error("WebSocket 에러:", error);
    };

    socket.onclose = () => {
        console.log("WebSocket 연결 종료");
    };
}

// 메시지 전송
function sendMessage() {
    const input = document.getElementById("message-input");
    if (input.value.trim() === "") return;

    const chatMessage = {
        type: "chat",
        role: userRole,
        sender: userName,
        content: input.value,
    };

    socket.send(JSON.stringify(chatMessage));
    input.value = "";
}

// 좌석 배치도 화면 전환
function showSeatLayout() {
    document.getElementById('chat-container').classList.add('hidden');
    document.getElementById('seat-layout').classList.remove('hidden');
    loadSeatData();
}

// 채팅 화면으로 돌아가기
function goBackToChat() {
    document.getElementById('seat-layout').classList.add('hidden');
    document.getElementById('chat-container').classList.remove('hidden');
}

// 좌석 데이터 불러오기 및 렌더링
// 좌석 데이터 불러오기 및 렌더링
// 좌석 데이터 불러오기 및 렌더링
function loadSeatData() {
    fetch('/api/seats') // 서버에서 좌석 데이터를 가져옴
        .then(response => response.json())
        .then(data => {
            // 좌석 데이터에서 51번 이상의 좌석 제외
            const filteredData = data.filter(seat => seat.seatNumber <= 50);

            // 좌석 데이터를 seatNumber 기준으로 정렬
            filteredData.sort((a, b) => a.seatNumber - b.seatNumber);

            const seatBox = document.getElementById('seat-box');
            seatBox.innerHTML = ''; // 기존 좌석 초기화

            // 좌석 데이터를 4개씩 묶어서 그룹 생성
            for (let i = 0; i < filteredData.length; i += 4) {
                const group = document.createElement('div');
                group.className = 'seat-group'; // CSS에서 그룹 스타일 적용

                // 그룹 내 좌석 배치 (최대 4개)
                const positions = ['top-left', 'top-right', 'bottom-left', 'bottom-right'];
                for (let j = 0; j < 4; j++) {
                    const seatData = filteredData[i + j]; // 현재 좌석 데이터
                    if (seatData) {
                        const seatElement = document.createElement('div');

                        // 예약 여부 판단: 예약자 수가 0 이상이거나 'occupied' 상태가 'x'
                        const isReserved = seatData.waitingCount > 0 || seatData.occupied === 'x';
                        seatElement.className = `seat ${positions[j]} ${isReserved ? 'reserved' : 'available'}`;

                        // 예약자 수 조건에 따라 렌더링
                        const seatNumberHTML = `<div class="seat-number">${seatData.seatNumber}</div>`;
                        let seatCountHTML = '';
                        if (seatData.waitingCount >= 2) {
                            seatCountHTML = `<div class="seat-count">${seatData.waitingCount - 1}명</div>`;
                        }

                        seatElement.innerHTML = seatNumberHTML + seatCountHTML;
                        group.appendChild(seatElement); // 그룹에 좌석 추가
                    }
                }

                seatBox.appendChild(group); // 컨테이너에 그룹 추가
            }
        })
        .catch(error => console.error('Error loading seat data:', error));
}



