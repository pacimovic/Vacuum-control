package com.example.Backend.model;

import com.example.Backend.enums.Status;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Vacuum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Status status;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    private boolean active;
}
