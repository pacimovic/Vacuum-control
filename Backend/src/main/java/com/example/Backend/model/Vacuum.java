package com.example.Backend.model;

import com.example.Backend.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Entity
public class Vacuum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Name is mandatory")
    private String name;

    private Status status;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    private LocalDate created;

    private boolean active;

    @Version
    @JsonIgnore
    private Integer version = 0;


}
