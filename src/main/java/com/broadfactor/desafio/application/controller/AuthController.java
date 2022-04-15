package com.broadfactor.desafio.application.controller;

import com.broadfactor.desafio.application.payload.ApiResponse;
import com.broadfactor.desafio.application.payload.LoginRequest;
import com.broadfactor.desafio.application.payload.SignUpRequest;
import com.broadfactor.desafio.domain.service.AuthenticationService;
import com.broadfactor.desafio.domain.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MessageSource messages;

    @Autowired
    private RegisterService registerService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.authenticateUser(loginRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        var validations = registerService.validations(signUpRequest);

        if (validations != null) {
            return validations;
        }

        String username = registerService.createUser(signUpRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(username).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true,  messages.getMessage("message.user.create.sucess", null, null)));
    }
}
