package com.example.librarywaitingsystem.service;

import com.example.librarywaitingsystem.model.Seat;
import com.example.librarywaitingsystem.repository.SeatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;


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
    public List<Seat> getAllSeats() {
        // 데이터베이스에서 모든 좌석 조회
        List<Seat> seats = seatRepository.findAll();

        // 좌석이 없으면 기본 좌석을 생성
        if (seats.isEmpty()) {
            for (int i = 1; i <= 50; i++) {
                Seat seat = new Seat();
                seat.setSeatNumber(i);
                seat.setOccupied("o"); // 기본 예약 가능 상태
                seat.setWaitingCount(0); // 기본 대기 인원: 0
                seatRepository.save(seat);
            }
            seats = seatRepository.findAll(); // 새로 생성된 좌석 리스트 반환
        }

        // 데이터베이스에 저장되지 않은 좌석 번호도 포함
        if (seats.size() < 50) {
            for (int i = 1; i <= 50; i++) {
                final int seatNumber = i;
                boolean exists = seats.stream().anyMatch(s -> s.getSeatNumber() == seatNumber);
                if (!exists) {
                    Seat seat = new Seat();
                    seat.setSeatNumber(i);
                    seat.setOccupied("o");
                    seat.setWaitingCount(0);
                    seatRepository.save(seat);
                    seats.add(seat);
                }
            }
        }

        return seats.stream()
                .filter(seat -> seat.getSeatNumber() <= 50)
                .sorted(Comparator.comparing(Seat::getSeatNumber)) // 정렬
                .collect(Collectors.toList());
    }


    // 좌석 상태 업데이트
    public void updateSeatStatus(Integer id, String occupied, Integer waitingCount) {
        Seat seat = seatRepository.findById(id).orElseThrow(() -> new RuntimeException("Seat not found"));
        seat.setOccupied(occupied);
        seat.setWaitingCount(waitingCount);
        seatRepository.save(seat);
    }
}