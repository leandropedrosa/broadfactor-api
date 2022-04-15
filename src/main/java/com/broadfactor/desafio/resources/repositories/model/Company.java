package com.broadfactor.desafio.resources.repositories.model;

import com.broadfactor.desafio.resources.repositories.model.audit.DateAudit;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Company extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="atividades_secundarias")
    private List<Activity> atividadePrincipal;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="atividades_secundarias")
    private List<Activity> atividadesSecundarias;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name="qsa")
    private List<Qsa> qsa;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cnpj", referencedColumnName = "cnpj")
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cnpj", referencedColumnName = "cnpj")
    private Contact contact;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cnpj", referencedColumnName = "cnpj")
    private CurrentStatus currentStatus;

    private String tipo;
    private String nome;
    private String porte;
    private String abertura;
    private String naturezaJuridica;
    private String cnpj;
    private String fantasia;
    private String efr;
    private String capitalSocial;
}
