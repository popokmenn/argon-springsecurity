package com.naufal.argon.controller.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.naufal.argon.Repository.RoleRepository;
import com.naufal.argon.Repository.UserRepository;
import com.naufal.argon.model.Role;
import com.naufal.argon.model.User;
import com.naufal.argon.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping(value = "/reg")
    private User saveUser(@RequestBody User user) {
        return userService.saveUserAndRole(user);
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

}