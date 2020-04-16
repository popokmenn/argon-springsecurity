package com.naufal.argon.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = Biodata.TABLE_NAME)
public class Biodata extends BaseEntity implements Serializable {
    
    private static final long serialVersionUID = -1246238172073489461L;

    public static final String TABLE_NAME = "T_BIODATA";

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "bio_fullname")
    private String fullname;

    @Column(name = "bio_address")
    private String address;

    @Column(name = "bio_zipcode")
    private String zipCode;

    @Column(name = "bio_profilephoto")
    private String profilePhoto;

}