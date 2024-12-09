# 도서관 웨이팅 시스템

<숙명여자대학교 2024-2 네트워크 캡스톤 과제>

도서관 내 층별 와이파이를 연결한 학생들이 각 층별 실시간 채팅을 통해 도서관 자리 예약 및 예약 상태를 확인할 수 있도록 설계되었습니다.
<br><br>


## 주요 기능

- **자리 예약 기능**: 사용자는 채팅을 통해 도서관 자리를 실시간으로 예약하거나 대기열에 추가될 수 있습니다.
- **채팅 기능**: WebSocket을 활용한 실시간 채팅으로 사용자 간 원활한 소통이 가능.
- **예약 상태 저장**: SQLite DB를 사용하여 자리 예약 상태 및 대기열 정보를 저장하고 관리.
- **관리자 기능**: 관리자 채팅 입력시 자동 상단 고정.
<br>

## 기술 스택

- **프레임워크**: Spring Boot
- **언어**: Java (JDK 21)
- **데이터베이스**: SQLite
- **실시간 통신**: WebSocket
- **ORM**: JPA (Java Persistence API)
<br>

## 서비스 영역 구조도
![서비스area](https://github.com/user-attachments/assets/d807a492-bfae-41dc-84e7-0f34f453871f)
![image](https://github.com/user-attachments/assets/1a5dcab2-2dcc-4c23-820b-53787edaa964)


