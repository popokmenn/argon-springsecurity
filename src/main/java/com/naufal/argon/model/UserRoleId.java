package com.naufal.argon.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class UserRoleId implements Serializable {

    @Column(name = "id_user")
    private Long userId;

    @Column(name = "id_role")
    private String roleId;

}