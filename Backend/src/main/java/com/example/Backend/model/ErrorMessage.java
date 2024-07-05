package com.example.Backend.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "error")
public class ErrorMessage {
    public ErrorMessage(LocalDate date, String vacuum, Long userId, String operation, String message) {
        this.date = date;
        this.vacuum = vacuum;
        this.userId = userId;
        this.operation = operation;
        this.message = message;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private String vacuum;

    private Long userId;

    private String operation;

    private String message;

    public ErrorMessage() {}
}
