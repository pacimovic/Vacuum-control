package com.example.Backend.model;

import lombok.Data;


import javax.persistence.*;


@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "can_create_users", column = @Column(name = "can_create_users")),
            @AttributeOverride(name = "can_read_users", column = @Column(name = "can_read_users")),
            @AttributeOverride(name = "can_update_users", column = @Column(name = "can_update_users")),
            @AttributeOverride(name = "can_delete_users", column = @Column(name = "can_delete_users"))
    })
    private Permission permission;
}
