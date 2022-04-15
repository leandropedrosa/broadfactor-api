package com.broadfactor.desafio.resources.repositories.dao;

import com.broadfactor.desafio.resources.repositories.model.PasswordResetToken;
import com.broadfactor.desafio.resources.repositories.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);
}
