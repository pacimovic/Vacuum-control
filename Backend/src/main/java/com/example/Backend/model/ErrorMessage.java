package com.example.Backend.model;

import com.example.Backend.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "VACUUM_ID", referencedColumnName = "ID")
    private Vacuum vacuum;

    private Status status;

    private String message;

}
