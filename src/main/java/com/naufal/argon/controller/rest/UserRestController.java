package com.naufal.argon.controller.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.JsonSerializable.Base;
import com.naufal.argon.Repository.RoleRepository;
import com.naufal.argon.Repository.UserRepository;
import com.naufal.argon.dto.BaseDto;
import com.naufal.argon.dto.UserDto;
import com.naufal.argon.model.Role;
import com.naufal.argon.model.User;
import com.naufal.argon.model.UserRole;
import com.naufal.argon.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserRestController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/reg")
    private User saveUser(@RequestBody UserDto user, HttpServletRequest request) {
        return userService.saveUserAndRole(user, request);
    }

    @PostMapping(value = "/login")
    private ResponseEntity<Boolean> validateUser(@RequestParam String username, @RequestParam String password,
            HttpServletRequest req) {
        User user = userRepository.findByUsernameAndPassword(username, password);

        if (user != null) {
            req.getSession().setAttribute("username", username);

            if (user.getBiodata().getProfilePhoto() != null) {
                req.getSession().setAttribute("profilephoto", user.getBiodata().getProfilePhoto());
            }

            return ResponseEntity.ok(Boolean.TRUE);
        } else
            return ResponseEntity.ok(Boolean.FALSE);
    }

    @PostMapping(value = "/role")
    private Role saveRole(@RequestBody Role role) {
        return roleRepository.save(role);
    }

    @GetMapping(value = "/role")
    private List<Role> getAllRole() {
        List<Role> roleList = roleRepository.findAll();
        for (int i = 0; i < roleList.size(); i++) {
            roleList.get(i).setUserRole(null);
        }
        return roleList;
    }

    @GetMapping(value = "/auto-add-role")
    private void addRoleIfNotExist() {
        String[] roleArr = { "Super Admin", "Admin", "User", "Karyawan Toko" };

        List<Role> roleList = new ArrayList<>();
        roleRepository.deleteAll();
        for (int i = 0; i < roleArr.length; i++) {
            Role role = new Role();
            role.setId("ROLE_" + roleArr[i].toUpperCase().replaceAll("\\s", ""));
            role.setName(roleArr[i]);
            roleList.add(role);
        }
        roleRepository.saveAll(roleList);
    }

    @GetMapping
    private List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();

        List<UserDto> userDtos = new ArrayList<>();
        for (User u : users) {
            UserDto userDtoObj = new UserDto();
            userDtoObj.setId(u.getId());
            userDtoObj.setAddress(u.getBiodata().getAddress());
            userDtoObj.setEmail(u.getEmail());
            userDtoObj.setEnabled(u.getEnabled());
            userDtoObj.setFullname(u.getBiodata().getFullname());
            userDtoObj.setIdBiodata(u.getBiodata().getId());
            userDtoObj.setPassword(u.getPassword());
            userDtoObj.setProfilePhoto(u.getBiodata().getProfilePhoto());

            List<BaseDto> userRoles = new ArrayList<>();
            for (UserRole ur : u.getUserRole()) {
                BaseDto roleObj = new BaseDto();
                roleObj.setId(ur.getRole().getId());
                roleObj.setName(ur.getRole().getName());
                userRoles.add(roleObj);
            }

            userDtoObj.setUsername(u.getUsername());
            userDtoObj.setZipCode(u.getBiodata().getZipCode());
            userDtoObj.setRoles(userRoles);
            userDtos.add(userDtoObj);
        }

        return userDtos;
    }

    @GetMapping("get/{id}")
    private UserDto getById(@PathVariable Long id) {
        User user = userRepository.findById(id).get();
     
        List<BaseDto> roles = new ArrayList<>();
        for(UserRole ur : user.getUserRole()) {
            Role getRole = roleRepository.findById(ur.getId().getRoleId()).get();
            BaseDto role = new BaseDto();
            role.setId(getRole.getId());
            role.setName(getRole.getName());
            roles.add(role);
        }

        UserDto userDtos = new UserDto();
        userDtos.setAddress(user.getBiodata().getAddress());
        userDtos.setUsername(user.getUsername());
        userDtos.setFullname(user.getBiodata().getFullname());
        userDtos.setEmail(user.getEmail());
        userDtos.setRoles(roles);
        userDtos.setId(user.getId());

        return userDtos;
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}