package com.broadfactor.desafio.resources.receitaws.service;


import com.broadfactor.desafio.resources.receitaws.model.CompanyReceita;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReceitawsService {
    @GET("/cnpj/{cnpj}")
    Call<CompanyReceita> getCompanyByCnpj(@Path("cnpj") String cnpj);
}
