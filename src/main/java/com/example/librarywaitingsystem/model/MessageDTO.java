package com.example.librarywaitingsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageDTO {
    private String type; // "join" 또는 "chat"
    private String sender; // 보낸 사람
    private String content; // 메시지 내용
}