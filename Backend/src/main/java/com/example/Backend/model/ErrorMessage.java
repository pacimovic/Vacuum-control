package com.example.Backend.model;

import com.example.Backend.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class ErrorMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Long vacuumId;

    private Status status;

    private String message;

}
