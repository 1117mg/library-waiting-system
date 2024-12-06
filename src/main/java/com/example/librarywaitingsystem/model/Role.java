package com.example.librarywaitingsystem.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 기본 키

    @Column(nullable = false)
    private Integer role; // 역할: 1 (일반 사용자), 2 (관리자)

    @Column(nullable = false, unique = true)
    private String name; // 사용자 이름 (유니크)

    @Column(nullable = false)
    private String password; // 비밀번호 (관리자용)
}
