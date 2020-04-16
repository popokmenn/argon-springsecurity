package com.naufal.argon.util;

import com.naufal.argon.Repository.UserRepository;
import com.naufal.argon.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


public class UserAuth {
    @Autowired
    private static UserRepository userRepository;

    public static User getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        User user = userRepository.findByUsername(userDetail.getUsername());

        return user;
    }


}
