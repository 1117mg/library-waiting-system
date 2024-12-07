package com.example.librarywaitingsystem.controller;


import com.example.librarywaitingsystem.model.Seat;
import com.example.librarywaitingsystem.service.SeatService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@CrossOrigin // 프론트엔드 요청 허용
public class SeatController {
    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    // 모든 좌석 데이터 반환
    @GetMapping
    public List<Seat> getAllSeats() {
        return seatService.getAllSeats();
    }

    // 좌석 상태 업데이트
    @PutMapping("/{id}")
    public void updateSeatStatus(
            @PathVariable Integer id,
            @RequestBody Seat seat
    ) {
        seatService.updateSeatStatus(id, seat.getOccupied(), seat.getWaitingCount());
    }
}
