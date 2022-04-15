package com.broadfactor.desafio.application.controller;

import com.broadfactor.desafio.application.payload.CompanyAvailability;
import com.broadfactor.desafio.application.payload.UserIdentityAvailability;
import com.broadfactor.desafio.domain.service.AccessInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InformationController {

    @Autowired
    private AccessInformationService accessInformationService;

    @GetMapping("/user/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        return accessInformationService.checkUsernameAvailability(username);
    }

    @GetMapping("/company/checkCnpjAvailability")
    public CompanyAvailability checkCnpjAvailability(@RequestParam(value = "cnpj") String cnpj) {
        return accessInformationService.checkCnpjAvailability(cnpj);
    }

    @GetMapping("/user/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        return accessInformationService.checkEmailAvailability(email);
    }

}
