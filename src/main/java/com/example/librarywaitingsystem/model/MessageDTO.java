package com.example.librarywaitingsystem.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageDTO {
    private String type; // "join", "chat", "system"
    private String sender;
    private String content;
    private String role;
}