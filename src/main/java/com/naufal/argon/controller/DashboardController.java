package com.naufal.argon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping(value = "/dashboard")
    public String dashboard() {
        return "Dashboard/index";
    }

    @GetMapping(value = { "/login", "/logout" })
    public String userSign() {
        return "User/login";
    }

    @GetMapping(value = "/register")
    public String userRegister() {
        return "User/register";
    }

    @GetMapping(value = "/manage-user")
    public String manageUser() {
        return "Manage/User";
    }

}