package com.example.Backend.model;

import com.example.Backend.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "error")
public class ErrorMessage {
    public ErrorMessage(LocalDate date, Long vacuumId, String operation, String message) {
        this.date = date;
        this.vacuumId = vacuumId;
        this.operation = operation;
        this.message = message;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Long vacuumId;

    private String operation;

    private String message;

    public ErrorMessage() {}
}
