package com.example.Backend.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Permission {

    private boolean can_create_users;
    private boolean can_read_users;
    private boolean can_update_users;
    private boolean can_delete_users;
}
