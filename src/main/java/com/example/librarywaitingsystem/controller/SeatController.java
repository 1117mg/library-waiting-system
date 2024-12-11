package com.example.librarywaitingsystem.controller;


import com.example.librarywaitingsystem.model.Seat;
import com.example.librarywaitingsystem.service.SeatService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@CrossOrigin // 프론트엔드 요청 허용
public class SeatController {
    private final SeatService seatService;
    private static final Logger logger = LoggerFactory.getLogger(SeatController.class);

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    // 모든 좌석 데이터 반환
    @GetMapping
    public List<Seat> getAllSeats() {
        logger.info("모든 좌석 데이터를 요청했습니다.");
        return seatService.getAllSeats();
    }

    // 좌석 상태 업데이트
    @PutMapping("/{id}")
    public void updateSeatStatus(
            @PathVariable Integer id,
            @RequestBody Seat seat
    ) {
        logger.info("좌석 상태 업데이트 요청 - ID: {}, Occupied: {}, WaitingCount: {}",
                id, seat.getOccupied(), seat.getWaitingCount());
        seatService.updateSeatStatus(id, seat.getOccupied(), seat.getWaitingCount());
    }
}
