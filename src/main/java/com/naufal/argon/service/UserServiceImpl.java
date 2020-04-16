package com.naufal.argon.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.naufal.argon.Repository.BiodataRepository;
import com.naufal.argon.Repository.UserRepository;
import com.naufal.argon.Repository.UserRoleRepository;
import com.naufal.argon.model.User;
import com.naufal.argon.model.UserRole;
import com.naufal.argon.model.UserRoleId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private BiodataRepository biodataRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public User saveUser(User user) {
        user.getBiodata().setCreatedOn(new Date());
        biodataRepository.save(user.getBiodata());
        userRepository.save(user);

        return null;
    }

    @Override
    public User saveUserAndRole(User user) {
        Date currentDate = new Date();
        String createdBy = "Nopel Popokmen";

        user.setCreatedOn(currentDate);
        user.setCreatedBy(createdBy);
        user.getBiodata().setCreatedBy(createdBy);
        user.getBiodata().setCreatedOn(currentDate);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userSave = userRepository.save(user);

        List<UserRole> userRole = new ArrayList<>(); 
        for(UserRole ur : user.getUserRole()) {
            UserRole uRole = new UserRole();
            UserRoleId urId = new UserRoleId();
            urId.setUserId(user.getId());
            urId.setRoleId(ur.getId().getRoleId());
            uRole.setId(urId);
            userRole.add(uRole);
        }
        userRoleRepository.saveAll(userRole);

        return userSave;
    }

}