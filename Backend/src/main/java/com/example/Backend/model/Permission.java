package com.example.Backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private boolean can_create_users;
    private boolean can_read_users;
    private boolean can_update_users;
    private boolean can_delete_users;
    private boolean can_search_vacuum;
    private boolean can_start_vacuum;
    private boolean can_stop_vacuum;
    private boolean can_discharge_vacuum;
    private boolean can_add_vacuum;
    private boolean can_remove_vacuum;

    @OneToOne(mappedBy = "permission", fetch = FetchType.EAGER)
    @JsonIgnore
    private User user;
}
