package com.hubzlo.hubzlo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/public/test")
    public String publicEndpoint() {
        return "Este é um endpoint público!";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/api/admin/test")
    public String adminEndpoint() {
        return "Este é um endpoint de admin!";
    }
}
