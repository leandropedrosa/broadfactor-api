package com.broadfactor.desafio.domain.service;

import com.broadfactor.desafio.application.payload.*;
import com.broadfactor.desafio.domain.exception.ResourceNotFoundException;
import com.broadfactor.desafio.domain.security.UserPrincipal;
import com.broadfactor.desafio.resources.repositories.dao.CompanyRepository;
import com.broadfactor.desafio.resources.repositories.dao.UserRepository;
import com.broadfactor.desafio.resources.repositories.model.Company;
import com.broadfactor.desafio.resources.repositories.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccessInformationService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    public UserSummary getCurrentUser(UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName(), currentUser.getCnpj());
        return userSummary;
    }

    public UserIdentityAvailability checkUsernameAvailability(String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }


    public UserIdentityAvailability checkEmailAvailability(String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    public CompanyAvailability checkCnpjAvailability(String cnpj) {
        Boolean isAvailable = !companyRepository.existsByCnpj(cnpj);
        return new CompanyAvailability(isAvailable);
    }

    public UserProfile getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        Optional<Company> company = companyRepository.findByCnpj(user.getCnpj());

        CompanyResponse companyResponse = modelMapper.map(company.get(), CompanyResponse.class);

        UserProfile userProfile = new UserProfile(user.getId(), user.getUsername(), user.getName(), companyResponse);

        return userProfile;
    }

    public User findUserByEmail(final String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        return userOptional.orElse(null);
    }
}
