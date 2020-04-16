package com.naufal.argon.service;

import com.naufal.argon.model.User;

public interface UserService {

    public User saveUser(User user);

    public User saveUserAndRole(User user);

}