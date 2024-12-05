package com.example.librarywaitingsystem.controller;


import com.example.librarywaitingsystem.model.MessageDTO;
import com.example.librarywaitingsystem.service.SeatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChatHandler extends TextWebSocketHandler {

    private final Set<WebSocketSession> sessions = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SeatService seatService;

    public ChatHandler(SeatService seatService) {
        this.seatService = seatService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            MessageDTO messageDTO = objectMapper.readValue(message.getPayload(), MessageDTO.class);
            String content = messageDTO.getContent();

            if (content.matches("\\d+번 자리 예약")) {
                Integer seatNumber = Integer.parseInt(content.replaceAll("\\D", ""));
                String response = seatService.reserveSeat(seatNumber);

                MessageDTO responseMessage = new MessageDTO();
                responseMessage.setType("system");
                responseMessage.setContent(response);

                for (WebSocketSession s : sessions) {
                    s.sendMessage(new TextMessage(objectMapper.writeValueAsString(responseMessage)));
                }
            } else {
                for (WebSocketSession s : sessions) {
                    s.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageDTO)));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session);
    }
}