package com.naufal.argon.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Data;

@Entity
@Table(name = Role.TABLE_NAME)
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Role {

    public static final String TABLE_NAME = "t_role";

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "sequence_role", strategy = GenerationType.AUTO)
    @GenericGenerator(name = "sequence_role", parameters = {
            @Parameter(name = "prefix", value = "ROLE_") }, strategy = "com.naufal.argon.configuration.PrefixGenerator")
    private String id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRole;

    // @Column(name = "description")
    // private String description;

}
