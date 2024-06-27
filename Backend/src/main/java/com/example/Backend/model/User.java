package com.example.Backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Surname is mandatory")
    private String surname;

    @Column(unique = true, nullable = false)
    @NotBlank(message = "Email is mandatory")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Password is mandatory")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PERMISSION_ID", referencedColumnName = "ID")
    @NotNull
    private Permission permission;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Vacuum> vacuum;

}
