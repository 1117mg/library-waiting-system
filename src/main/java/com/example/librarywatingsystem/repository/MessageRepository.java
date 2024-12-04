package com.example.librarywatingsystem.repository;

import com.example.librarywatingsystem.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByUserId(Long userId);
}