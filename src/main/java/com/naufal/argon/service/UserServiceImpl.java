package com.naufal.argon.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import com.naufal.argon.Repository.BiodataRepository;
import com.naufal.argon.Repository.UserRepository;
import com.naufal.argon.Repository.UserRoleRepository;
import com.naufal.argon.dto.BaseDto;
import com.naufal.argon.dto.UserDto;
import com.naufal.argon.model.Biodata;
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
    public User saveUserAndRole(UserDto userDto, HttpServletRequest request) {
        Date currentDate = new Date();
        String createdBy = "Nopel Popokmen";

        HttpSession session = request.getSession();
        User userSession = (User) session.getAttribute("user");
        if (userSession != null) {
            createdBy = userSession.getBiodata().getFullname();
        }

        User user = new User();
        Biodata bio = new Biodata();

        if (userDto.getId() > -1 && userDto.getId() != null) {
            User userById = userRepository.findById(userDto.getId()).get();
            user.setId(userDto.getId());
            user.setModifiedBy(createdBy);
            user.setModifiedOn(currentDate);
            user.setPassword(userById.getPassword());
            user.setCreatedBy(userById.getCreatedBy());
            user.setCreatedOn(userById.getCreatedOn());
            bio.setId(userById.getBiodata().getId());
            bio.setCreatedBy(userById.getBiodata().getCreatedBy());
            bio.setCreatedOn(userById.getBiodata().getCreatedOn());
        } else {
            user.setPassword(userDto.getPassword());
            bio.setCreatedBy(createdBy);
            bio.setCreatedOn(currentDate);
            user.setCreatedOn(currentDate);
            user.setCreatedBy(createdBy);
        }

        bio.setFullname(userDto.getFullname());
        bio.setAddress(userDto.getAddress());
        bio.setZipCode(userDto.getZipCode());
        bio.setProfilePhoto(userDto.getProfilePhoto());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setTokenExpired(false);
        user.setBiodata(bio);
        User userSave = userRepository.save(user);

        List<UserRole> userRoles = new ArrayList<>();
        for (BaseDto d : userDto.getRoles()) {
            UserRoleId ur = new UserRoleId();
            UserRole u = new UserRole();
            ur.setUserId(userSave.getId());
            ur.setRoleId(d.getId());
            u.setId(ur);
            userRoles.add(u);
        }
        userRoleRepository.deleteAllByIdUserId(userDto.getId());
        List<UserRole> usersRole = userRoleRepository.saveAll(userRoles);
        userSave.setUserRole(usersRole);
        return userSave;
    }

    @Override
    public void deleteUser(Long idUser) {
        userRoleRepository.deleteAllByIdUserId(idUser);
        userRepository.deleteById(idUser);
    }

}