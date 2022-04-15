package com.broadfactor.desafio.resources.repositories.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CurrentStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cnpj;
    private String dataSituacao;
    private String situacao;
    private String ultimaAtualizacao;
    private String status;
    private String motivoSituacao;
    private String situacaoEspecial;
    private String dataSituacaoEspecial;
}
