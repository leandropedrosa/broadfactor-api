package com.broadfactor.desafio.domain.service;

import com.broadfactor.desafio.application.payload.ApiResponse;
import com.broadfactor.desafio.application.payload.SignUpRequest;
import com.broadfactor.desafio.domain.exception.AppException;
import com.broadfactor.desafio.resources.receitaws.client.ReceitawsClient;
import com.broadfactor.desafio.resources.receitaws.model.CompanyReceita;
import com.broadfactor.desafio.resources.repositories.dao.CompanyRepository;
import com.broadfactor.desafio.resources.repositories.dao.RoleRepository;
import com.broadfactor.desafio.resources.repositories.dao.UserRepository;
import com.broadfactor.desafio.resources.repositories.model.Company;
import com.broadfactor.desafio.resources.repositories.model.Role;
import com.broadfactor.desafio.resources.repositories.model.RoleName;
import com.broadfactor.desafio.resources.repositories.model.User;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import retrofit2.Call;

import java.io.IOException;
import java.util.Collections;

@Service
public class RegisterService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ReceitawsClient receitaws;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String createUser(SignUpRequest signUpRequest) {

        logger.info("Criando Usuário com email: {}", signUpRequest.getEmail());

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(),
                signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getCnpj());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("Tipo de acesso de usuário não definido."));

        user.setRoles(Collections.singleton(userRole));

        createCompany(signUpRequest.getCnpj());

        User result = userRepository.save(user);

        logger.info("Usuário com email: {} criado", signUpRequest.getEmail());

        return result.getUsername();
    }

    public ResponseEntity validations(SignUpRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "O nome de usuário já foi usado!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Endereço de email já está em uso!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (companyRepository.existsByCnpj(signUpRequest.getCnpj())) {
            return new ResponseEntity(new ApiResponse(false, "CNPJ já cadastrado na base de dados!"),
                    HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void createCompany(String cnpj) {

        Call<CompanyReceita> companyResponse = receitaws.getService().getCompanyByCnpj(cnpj);

        CompanyReceita response;

        try {
            response = companyResponse.execute().body();
            if (response != null) {
                Company company = modelMapper.map(response, Company.class);
                companyRepository.save(company);
            }
        } catch (IOException e) {
            logger.error("Erro ao busca empresa {}", cnpj);
        }
    }
}
