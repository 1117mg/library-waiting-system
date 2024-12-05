package com.example.librarywaitingsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seats")
@Data
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "seat_number", unique = true, nullable = false)
    private Integer seatNumber;

    @Column(nullable = false)
    private String occupied; // 'x' 또는 'o'

    @Column(name = "waiting_count", nullable = false)
    private Integer waitingCount;
}