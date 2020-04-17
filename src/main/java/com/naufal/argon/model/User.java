package com.naufal.argon.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity(name = User.TABLE_NAME)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "user_username" }) })
public class User extends BaseEntity {

    public static final String TABLE_NAME = "T_USER";

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "user_username", unique = true)
    private String username;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_email")
    private String email;

    @Column(name = "enabled")
    private Boolean enabled = true;

    @Column(name = "token_expired")
    private Boolean tokenExpired;

    @OneToMany(mappedBy = "user")
    private List<UserRole> userRole;

    @OneToOne(cascade = { CascadeType.ALL })
    @JoinColumns(@JoinColumn(name = "BIO_ID", referencedColumnName = "id"))
    private Biodata biodata;

}