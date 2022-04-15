CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `username` varchar(15) NOT NULL,
  `email` varchar(40) NOT NULL,
  `password` varchar(100) NOT NULL,
  `cnpj` varchar(100) NOT NULL,
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_users_username` (`username`),
  UNIQUE KEY `uk_users_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(60) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_roles_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


CREATE TABLE `user_roles` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `fk_user_roles_role_id` (`role_id`),
  CONSTRAINT `fk_user_roles_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `fk_user_roles_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `company` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `atividade_principal` bigint(20) NOT NULL,
  `atividades_secundarias` bigint(20) NOT NULL,
  `qsa` bigint(20) NOT NULL,
  `tipo` varchar(15) NOT NULL,
  `nome` varchar(300) NOT NULL,
  `porte` varchar(50) NOT NULL,
  `abertura` varchar(20) NOT NULL,
  `naturezaJuridica` varchar(300) NOT NULL,
  `cnpj` varchar(15) NOT NULL,
  `fantasia` varchar(300) NOT NULL,
  `efr` varchar(300) NOT NULL,
  `capitalSocial` varchar(300) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_company_cnpj` (`cnpj`),
  CONSTRAINT `fk_user_company_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `address` (
  `id` bigint(20) NOT NULL,
  `cnpj` varchar(15) NOT NULL,
  `complemento` varchar(300) NOT NULL,
  `uf` varchar(2) NOT NULL,
  `bairro` varchar(300) NOT NULL,
  `logradouro` varchar(300) NOT NULL,
  `numero` varchar(15) NOT NULL,
  `cep` varchar(10) NOT NULL,
  `municipio` varchar(300) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_address_cnpj` (`cnpj`),
  CONSTRAINT `fk_address_company_cnpj` FOREIGN KEY (`cnpj`) REFERENCES `company` (`cnpj`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `contact` (
  `id` bigint(20) NOT NULL,
  `cnpj` varchar(15) NOT NULL,
  `telefone` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_address_cnpj` (`cnpj`),
  CONSTRAINT `fk_contact_company_cnpj` FOREIGN KEY (`cnpj`) REFERENCES `company` (`cnpj`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `current_status` (
  `id` bigint(20) NOT NULL,
  `cnpj` varchar(15) NOT NULL,
  `data_situacao` varchar(10) NOT NULL,
  `situacao` varchar(30) NOT NULL,
  `ultima_atualizacao` varchar(10) NOT NULL,
  `status` varchar(30) NOT NULL,
  `motivo_situacao` varchar(300) NOT NULL,
  `situacao_especial` varchar(30) NOT NULL,
  `data_situacao_especial` varchar(10) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_address_cnpj` (`cnpj`),
  CONSTRAINT `fk_current_status_company_cnpj` FOREIGN KEY (`cnpj`) REFERENCES `company` (`cnpj`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `activity` (
  `id` bigint(20) NOT NULL,
  `code` varchar(50) NOT NULL,
  `text` varchar(300) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `qsa` (
  `id` bigint(20) NOT NULL,
  `qual` varchar(50) NOT NULL,
  `nome` varchar(300) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
