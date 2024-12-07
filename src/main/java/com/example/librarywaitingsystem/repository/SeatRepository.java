package com.example.librarywaitingsystem.repository;

import com.example.librarywaitingsystem.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Integer> {
    Optional<Seat> findBySeatNumber(Integer seatNumber);
}