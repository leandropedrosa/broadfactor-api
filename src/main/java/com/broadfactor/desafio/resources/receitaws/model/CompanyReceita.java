package com.broadfactor.desafio.resources.receitaws.model;

import lombok.Data;

import java.util.List;

@Data
public class CompanyReceita {
    private List<ActivityReceita> atividadePrincipal;
    private List<ActivityReceita> atividadesSecundarias;
    private List<QsaReceita> qsaReceita;
    private String dataSituacao;
    private String complemento;
    private String tipo;
    private String nome;
    private String uf;
    private String telefone;
    private String email;
    private String situacao;
    private String bairro;
    private String logradouro;
    private String numero;
    private String cep;
    private String municipio;
    private String porte;
    private String abertura;
    private String naturezaJuridica;
    private String cnpj;
    private String ultimaAtualizacao;
    private String status;
    private String fantasia;
    private String efr;
    private String motivoSituacao;
    private String situacaoEspecial;
    private String dataSituacaoEspecial;
    private String capitalSocial;
}
