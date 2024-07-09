package com.example.Backend.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "error")
public class ErrorMessage {
    public ErrorMessage(LocalDateTime date, String vacuum, Long userId, String operation, String message) {
        this.date = date;
        this.vacuum = vacuum;
        this.userId = userId;
        this.operation = operation;
        this.message = message;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private String vacuum;

    private Long userId;

    private String operation;

    private String message;

    public ErrorMessage() {}
}
