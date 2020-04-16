package com.naufal.argon.service;

import com.naufal.argon.model.User;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserSecurityService extends UserDetailsService {
    User save(User user);
}
