package com.broadfactor.desafio.domain.service;

import com.broadfactor.desafio.application.payload.ApiResponse;
import com.broadfactor.desafio.application.payload.UpdateRequest;
import com.broadfactor.desafio.domain.exception.AppException;
import com.broadfactor.desafio.resources.repositories.dao.CompanyRepository;
import com.broadfactor.desafio.resources.repositories.dao.PasswordTokenRepository;
import com.broadfactor.desafio.resources.repositories.dao.UserRepository;
import com.broadfactor.desafio.resources.repositories.model.PasswordResetToken;
import com.broadfactor.desafio.resources.repositories.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UpdateService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    @Autowired
    private RegisterService registerService;

    @Autowired
    PasswordEncoder passwordEncoder;

    private User user;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String update(UpdateRequest updateRequest) {

        Optional<User> userOptional = userRepository.findById(updateRequest.getId());
        userOptional.orElseThrow(() -> new AppException("Usuário não existe."));
        user = userOptional.get();

        updateUser(updateRequest);

        userRepository.save(user);
        return user.getUsername();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void updateUser(UpdateRequest updateRequest) {

        if (updateRequest.getCnpj() != null) {
            registerService.createCompany(updateRequest.getCnpj());
            user.setCnpj(updateRequest.getCnpj());
        }

        if (updateRequest.getName() != null) {
            user.setName(updateRequest.getName());
        }

        if (updateRequest.getEmail() != null) {
            user.setEmail(updateRequest.getEmail());
        }

        if (updateRequest.getUsername() != null) {
            user.setUsername(updateRequest.getUsername());
        }

        if (updateRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
    }

    public ResponseEntity validations(UpdateRequest updateRequestt) {

        if (userRepository.existsByUsername(updateRequestt.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "O nome de usuário já foi usado!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(updateRequestt.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Endereço de email já está em uso!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (companyRepository.existsByCnpj(updateRequestt.getCnpj())) {
            return new ResponseEntity(new ApiResponse(false, "CNPJ já cadastrado na base de dados!"),
                    HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }
}
