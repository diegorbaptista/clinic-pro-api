package com.clinicpro.api.infra.controllers;

import com.clinicpro.api.domain.user.User;
import com.clinicpro.api.infra.security.JSONWebTokenService;
import com.clinicpro.api.infra.security.dto.AuthenticationDTO;
import com.clinicpro.api.infra.security.dto.JSONWebTokenDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class AuthenticationController {

    @Autowired
    private final AuthenticationManager manager;

    @Autowired
    private final JSONWebTokenService service;

    @PostMapping
    public ResponseEntity<JSONWebTokenDTO> auth(@RequestBody @Valid AuthenticationDTO data) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var authentication = manager.authenticate(authenticationToken);
        var token = this.service.createToken((User) authentication.getPrincipal());
        return ResponseEntity.ok(token);
    }

}
