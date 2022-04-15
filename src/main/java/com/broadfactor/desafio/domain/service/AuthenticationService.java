package com.broadfactor.desafio.domain.service;

import com.broadfactor.desafio.application.payload.JwtAuthenticationResponse;
import com.broadfactor.desafio.application.payload.LoginRequest;
import com.broadfactor.desafio.application.payload.UserProfile;
import com.broadfactor.desafio.domain.security.JwtTokenProvider;
import com.broadfactor.desafio.resources.repositories.dao.PasswordTokenRepository;
import com.broadfactor.desafio.resources.repositories.dao.UserRepository;
import com.broadfactor.desafio.resources.repositories.model.PasswordResetToken;
import com.broadfactor.desafio.resources.repositories.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccessInformationService accessInformationService;


    public JwtAuthenticationResponse authenticateUser(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        Optional<User> username = userRepository.findByUsername(loginRequest.getUsernameOrEmail());
        if (username.isEmpty())
            username = userRepository.findByEmail(loginRequest.getUsernameOrEmail());

        UserProfile userProfile = accessInformationService.getUserProfile(username.get().getUsername());

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(jwt);
        jwtAuthenticationResponse.setUserProfile(userProfile);

        return jwtAuthenticationResponse;
    }

    public String validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenRepository.findByToken(token);

        return !isTokenFound(passToken) ? "invalidToken"
                : isTokenExpired(passToken) ? "expired"
                : null;
    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final Calendar cal = Calendar.getInstance();
        return passToken.getExpiryDate().before(cal.getTime());
    }
}
