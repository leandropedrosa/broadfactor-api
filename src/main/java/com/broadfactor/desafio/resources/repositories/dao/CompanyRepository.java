package com.broadfactor.desafio.resources.repositories.dao;

import com.broadfactor.desafio.resources.repositories.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByCnpj(String companyId);

    Boolean existsByCnpj(String cnpj);

}
