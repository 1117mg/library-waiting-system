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

            broadcastMessage(messageDTO);

            if (isSeatReservationRequest(content)) {
                handleSeatReservation(session, content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isSeatReservationRequest(String content) {
        return content.matches("\\d+번 자리 예약");
    }

    private void handleSeatReservation(WebSocketSession session, String content) throws Exception {
        Integer seatNumber = Integer.parseInt(content.replaceAll("\\D", ""));
        String response = seatService.reserveSeat(seatNumber);

        MessageDTO responseMessage = new MessageDTO();
        responseMessage.setType("system");
        responseMessage.setContent(response);
        responseMessage.setSender(null); 

        broadcastMessage(responseMessage);
    }

    private void broadcastMessage(MessageDTO messageDTO) throws Exception {
        for (WebSocketSession s : sessions) {
            s.sendMessage(new TextMessage(objectMapper.writeValueAsString(messageDTO)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
        sessions.remove(session);
    }
}