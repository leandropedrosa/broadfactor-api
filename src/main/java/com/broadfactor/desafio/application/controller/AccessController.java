package com.broadfactor.desafio.application.controller;

import com.broadfactor.desafio.application.payload.*;
import com.broadfactor.desafio.domain.security.CurrentUser;
import com.broadfactor.desafio.domain.security.UserPrincipal;
import com.broadfactor.desafio.domain.service.AccessInformationService;
import com.broadfactor.desafio.domain.service.AuthenticationService;
import com.broadfactor.desafio.domain.service.EmailService;
import com.broadfactor.desafio.domain.service.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
public class AccessController {

    @Autowired
    private AccessInformationService accessInformationService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UpdateService updateService;

    @Autowired
    private MessageSource messages;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return accessInformationService.getCurrentUser(currentUser);
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasRole('USER')")
    public UserProfile getUserProfile(@PathVariable(value = "username") String username) {

        return accessInformationService.getUserProfile(username);
    }

    @PutMapping("/user/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateRequest updateRequest) {

        var validations = updateService.validations(updateRequest);

        if (validations != null) {
            return validations;
        }

        String username = updateService.update(updateRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/users/{username}")
                .buildAndExpand(username).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, messages.getMessage("message.user.update.sucess", null, null)));
    }

    @PostMapping("/user/resetPassword")
    public GenericResponse resetPassword(final HttpServletRequest request, @RequestParam("email") final String userEmail) {
        emailService.sendPasswordReset(request, userEmail);
        return new GenericResponse(messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
    }

}
