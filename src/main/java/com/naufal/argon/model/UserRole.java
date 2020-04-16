package com.naufal.argon.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "t_userrole")
@Data
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne
    @JoinColumn(name = "id_user", updatable = false, insertable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_role", updatable = false, insertable = false)
    private Role role;

}