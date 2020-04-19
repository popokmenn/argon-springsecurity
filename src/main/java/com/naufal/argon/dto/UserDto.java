package com.naufal.argon.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String username;

    private String password;

    private String email;

    private Boolean enabled = true;

    private Boolean tokenExpired;

    private Long idBiodata;

    private String fullname;

    private String address;

    private String zipCode;

    private String profilePhoto;

    private List<BaseDto> roles;

}