package com.example.librarywaitingsystem.service;

import com.example.librarywaitingsystem.model.Seat;
import com.example.librarywaitingsystem.repository.SeatRepository;
import org.springframework.stereotype.Service;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public String reserveSeat(Integer seatNumber) {
        Seat seat = seatRepository.findBySeatNumber(seatNumber).orElseGet(() -> {
            Seat newSeat = new Seat();
            newSeat.setSeatNumber(seatNumber);
            newSeat.setOccupied("x");
            newSeat.setWaitingCount(0);
            return seatRepository.save(newSeat);
        });

        if ("x".equals(seat.getOccupied())) {
            seat.setOccupied("o");
            seatRepository.save(seat);
            return String.format("%d번 자리는 바로 이용 가능합니다. 실시간 예약자수: 0명", seatNumber);
        } else {
            seat.setWaitingCount(seat.getWaitingCount() + 1);
            seatRepository.save(seat);
            return String.format("%d번 자리 웨이팅이 등록되었습니다. 실시간 예약자수: %d명", seatNumber, seat.getWaitingCount());
        }
    }
}