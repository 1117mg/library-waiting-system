package com.example.librarywaitingsystem.controller;

import com.example.librarywaitingsystem.model.Seat;
import com.example.librarywaitingsystem.service.SeatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeatController {

    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/api/seats")
    public List<Seat> getSeats() {
        return seatService.getAllSeats();
    }
}