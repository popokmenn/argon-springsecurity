package com.naufal.argon.service;

import javax.servlet.http.HttpServletRequest;

import com.naufal.argon.dto.UserDto;
import com.naufal.argon.model.User;

public interface UserService {

    public User saveUser(User user);

    public User saveUserAndRole(UserDto user, HttpServletRequest request);

    public void deleteUser(Long idUser);

}